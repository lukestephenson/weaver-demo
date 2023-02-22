package demo3

import org.apache.kafka.clients.admin.*
import demo3.KafkaFutureToIoOps.toIO
import java.util.Collections

class KafkaTopicCreate(adminClient: AdminClient) {
  def createTopic(topicName: String) = {
    val newTopic = new NewTopic(topicName, 1, 1.toShort)
    toIO(adminClient.createTopics(Collections.singleton(newTopic)).all()).void
  }
}
