package demo3.weaver

import cats.effect.Resource
import com.dimafeng.testcontainers.KafkaContainer
import cats.effect.IO
import demo3.AdminClientResource
import org.apache.kafka.clients.admin.AdminClientConfig.CLIENT_ID_CONFIG
import org.apache.kafka.clients.admin.AdminClient
import org.testcontainers.utility.DockerImageName
import weaver.{GlobalRead, GlobalResource, GlobalWrite}

import scala.util.Random

object KafkaResources extends GlobalResource {
  case class KafkaBrokers(bootstrapServers: String)
  case class KafkaResource(bootstrapServers: String, adminClient: AdminClient) derives CanEqual

  private val kafkaResource: Resource[IO, KafkaBrokers] = {
    // You will need to cross reference the confluent platform to kafka versions here https://docs.confluent.io/current/installation/versions-interoperability.html
    // This version is on Kafka 2.8.x
    val kafkaContainer: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.7"))

    for {
      _ <- Resource.make {
        IO(println("starting kafka")) >>
          IO(kafkaContainer.start())
      }(_ =>
        IO(println("stopping kafka")) >>
          IO(kafkaContainer.stop()) >>
          IO(println("kafka stopped"))
      )
      bootstrapServers <- Resource.eval(IO {
        val bootstrapServers = kafkaContainer.bootstrapServers
        println(s"kafka started on $bootstrapServers")
        bootstrapServers
      }) // not available until container started
    } yield KafkaBrokers(bootstrapServers)
  }

  private val kafkaAndAdminServiceResource: Resource[IO, AdminClient] = {
    for {
      kafkaConfig <- kafkaResource
      adminClient <- AdminClientResource.apply(kafkaConfig.bootstrapServers)
    } yield adminClient
  }

  override def sharedResources(global: GlobalWrite): Resource[IO, Unit] = kafkaAndAdminServiceResource.flatMap(global.putR(_))

  def sharedKafkaResourcesOrFallback(read: GlobalRead): Resource[IO, AdminClient] = read.getR[AdminClient]().flatMap {
    case Some(value) => Resource.eval(IO(value))
    case None => kafkaAndAdminServiceResource
  }
}
