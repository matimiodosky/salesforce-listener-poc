package modules

import com.google.inject.AbstractModule
import consumer.SalesforceConsumer
import play.api.Logging
import producer.KafkaProducer

class KafkaProducerModule extends AbstractModule with Logging {

  override def configure(): Unit = {
    logger.info("Starting Salesforce consumer")
    bind(classOf[KafkaProducer]).asEagerSingleton()
  }
}