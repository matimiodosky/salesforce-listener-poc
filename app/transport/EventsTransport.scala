package transport


import consumer.SalesforceConsumer
import producer.KafkaProducer

import javax.inject.{Inject, Singleton}

@Singleton
class EventsTransport @Inject() (consumer: SalesforceConsumer, producer: KafkaProducer){

  println("starting transport")
  consumer.subscribe(event => producer.produce(event.toString))

}
