package modules

import com.google.inject.AbstractModule
import consumer.SalesforceConsumer
import play.api.Logging
import transport.EventsTransport

class TransportModule extends AbstractModule with Logging {

  override def configure(): Unit = {
    bind(classOf[EventsTransport]).asEagerSingleton()
  }
}