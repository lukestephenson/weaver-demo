package demo3

import cats.effect.Resource
import cats.effect.IO
import org.apache.kafka.clients.admin.AdminClientConfig.{BOOTSTRAP_SERVERS_CONFIG, CLIENT_ID_CONFIG}
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig}

import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters.MapHasAsJava

object AdminClientResource {
  def apply(brokerUrl: String): Resource[IO, AdminClient] = {
    val config = Map(
      BOOTSTRAP_SERVERS_CONFIG -> brokerUrl,
      CLIENT_ID_CONFIG -> "tests"
    )

    val props = new Properties()
    props.putAll(config.asJava)

    AdminClientResource(props)
  }

  private def apply(properties: Properties): Resource[IO, AdminClient] = {
    Resource.make {
      IO(AdminClient.create(properties))
    } { adminClient =>
      IO {
        println("Closing KafkaSupervisor AdminClient")
        adminClient.close(Duration.ofSeconds(10))
      }
    }
  }
}
