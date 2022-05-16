package modules

import com.google.inject.AbstractModule
import consumer.SalesforceConsumer
import play.api.Logging
import producer.KafkaProducer
import repository.CrmServiceRepository
import transport.EventsTransport

class Modules extends AbstractModule with Logging {

  override def configure(): Unit = {
    bind(classOf[EventsTransport]).asEagerSingleton()
    bind(classOf[KafkaProducer]).asEagerSingleton()
    bind(classOf[SalesforceConsumer]).asEagerSingleton()
    bind(classOf[CrmServiceRepository]).asEagerSingleton()
  }
}