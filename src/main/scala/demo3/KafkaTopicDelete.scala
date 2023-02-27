package demo3

import cats.effect.IO
import demo3.KafkaFutureToIoOps.toIO
import org.apache.kafka.clients.admin.*

import java.util.Collections

class KafkaTopicDelete(adminClient: AdminClient) {
  def deleteTopic(topicName: String): IO[Unit] = {
    toIO(adminClient.deleteTopics(Collections.singleton(topicName)).all()).void
  }
}
