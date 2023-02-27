package demo3

import cats.effect.IO
import org.apache.kafka.clients.admin.*
import demo3.KafkaFutureToIoOps.toIO

import java.util.Collections

class KafkaTopicCreate(adminClient: AdminClient) {
  def createTopic(topicName: String): IO[Unit] = {
    val newTopic = new NewTopic(topicName, 1, 1.toShort)
    toIO(adminClient.createTopics(Collections.singleton(newTopic)).all()).void
  }
}
