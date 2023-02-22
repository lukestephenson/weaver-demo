package demo3.scalatest

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

abstract class KafkaSpecHelper extends AnyFunSpec with Matchers with BeforeAndAfterAll {

  var adminClient: AdminClient = null
  var kafkaContainer: KafkaContainer = null

  override def beforeAll(): Unit = {
    super.beforeAll()
    println("Starting kafka")
    kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.7"))
    kafkaContainer.start()

    val brokerUrl = kafkaContainer.bootstrapServers
    println(brokerUrl)
    adminClient = AdminClient.create(Collections.singletonMap(BOOTSTRAP_SERVERS_CONFIG, brokerUrl))
  }

  override def afterAll(): Unit = {
    super.afterAll()
    println("stopping admin client")
    if (adminClient != null) adminClient.close(Duration.ofSeconds(10))
    println("stopping kafka")
    if (kafkaContainer != null) kafkaContainer.stop()
    println("kafka stopped")
    Thread.sleep(10000)
    println("kafka stopped - post sleep")
  }

}
