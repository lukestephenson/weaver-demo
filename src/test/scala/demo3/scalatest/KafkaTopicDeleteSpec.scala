package demo3.scalatest

import cats.effect.IO
import com.dimafeng.testcontainers.KafkaContainer
import demo3.KafkaFutureToIoOps.toIO
import demo3.{AdminClientResource, KafkaTopicCreate, KafkaTopicDelete}
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName

import java.util.Collections
import scala.concurrent.duration.*

class KafkaTopicDeleteSpec extends KafkaSpecHelper {

  describe("Scalatest - KafkaTopicDelete") {
    it("deletes a topic") {
      val topicCreateService = new KafkaTopicCreate(adminClient)
      val topicDeleteService = new KafkaTopicDelete(adminClient)
      val program = for {
//        _ <- IO.sleep(1.minute)
        _ <- topicCreateService.createTopic("to.delete")
        _ <- topicDeleteService.deleteTopic("to.delete")
        describeAttempt <- toIO(adminClient.describeTopics(Collections.singleton("to.delete")).all()).attempt
      } yield describeAttempt

      import cats.effect.unsafe.implicits.global
      program.unsafeRunSync() should be(Symbol("left"))
    }
  }
}
