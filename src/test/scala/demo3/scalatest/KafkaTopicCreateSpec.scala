package demo3.scalatest

import cats.effect.IO
import com.dimafeng.testcontainers.KafkaContainer
import demo3.KafkaFutureToIoOps.toIO
import demo3.{AdminClientResource, KafkaTopicCreate}
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName

import java.time.Duration
import java.util.Collections
import scala.concurrent.duration.*

class KafkaTopicCreateSpec extends AnyFunSpec with Matchers with BeforeAndAfterAll {

  private var adminClient: AdminClient = null
  private var kafkaContainer: KafkaContainer = null

  override def beforeAll(): Unit = {
    super.beforeAll()
    println("Starting kafka")
    kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.7"))
    kafkaContainer.start()

    val brokerUrl = kafkaContainer.bootstrapServers
    println(s"kafka started on $brokerUrl")
    adminClient = AdminClient.create(Collections.singletonMap(BOOTSTRAP_SERVERS_CONFIG, brokerUrl))
  }

  override def afterAll(): Unit = {
    super.afterAll()
    println("stopping admin client")
    if (adminClient != null) adminClient.close(Duration.ofSeconds(10))
    println("stopping kafka")
    if (kafkaContainer != null) kafkaContainer.stop()
  }

  describe("Scalatest - KafkaTopicCreate") {
    it("creates a topic") {
      val topicCreateService = new KafkaTopicCreate(adminClient)
      val program = for {
        _ <- topicCreateService.createTopic("foo")
        createdTopic <- toIO(adminClient.describeTopics(Collections.singleton("foo")).all())
      } yield createdTopic

      import cats.effect.unsafe.implicits.global
      program.unsafeRunSync().size() should be(1)
    }
  }
}
