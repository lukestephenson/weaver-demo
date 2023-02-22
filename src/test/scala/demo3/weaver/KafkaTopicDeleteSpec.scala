package demo3.weaver

import cats.effect.{IO, Resource}
import com.dimafeng.testcontainers.KafkaContainer
import demo3.KafkaFutureToIoOps.toIO
import demo3.{AdminClientResource, KafkaTopicCreate, KafkaTopicDelete}
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName
import weaver.{GlobalRead, IOSuite}

import java.util.Collections

class KafkaTopicDeleteSpec(global: GlobalRead) extends IOSuite {

  override type Res = AdminClient

  override def sharedResource: Resource[IO, AdminClient] = KafkaResources.sharedKafkaResourcesOrFallback(global)

  test("deletes a topic") { adminClient =>
    val topicCreateService = new KafkaTopicCreate(adminClient)
    val topicDeleteService = new KafkaTopicDelete(adminClient)

    for {
      _ <- topicCreateService.createTopic("to.delete")
      _ <- topicDeleteService.deleteTopic("to.delete")
      describeAttempt <- toIO(adminClient.describeTopics(Collections.singleton("to.delete")).all()).attempt
    } yield expect(describeAttempt.isLeft)
  }

}
