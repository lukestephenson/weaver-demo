package demo3

import demo3.KafkaFutureToIoOps.toIO
import org.apache.kafka.clients.admin.*

import java.util.Collections

class KafkaTopicDelete(adminClient: AdminClient) {
  def deleteTopic(topicName: String) = {
    toIO(adminClient.deleteTopics(Collections.singleton(topicName)).all()).void
  }
}
