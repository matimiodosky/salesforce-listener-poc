package producer

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.adapter.TypedActorSystemOps
import akka.kafka.ProducerSettings
import org.apache.kafka.clients.producer.{ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import akka.actor.typed.ActorSystem
import akka.kafka.scaladsl.SendProducer
import play.api.Configuration

import javax.inject.{Inject, Singleton}


@Singleton
class KafkaProducer @Inject()(config: Configuration) {

  private implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "kafkaProducer")
  private val kafkaServer = config.get[String]("kafka.server")
  private val producerSettings = ProducerSettings(system.toClassic, new StringSerializer, new StringSerializer).withBootstrapServers(kafkaServer)

  private val producer = SendProducer(producerSettings)

  println("Connected to kafka")

  def produce(message: String) = {
    try {
      val send: Future[RecordMetadata] = producer
        .send(new ProducerRecord("contacts",message))
      Await.result(send, 2.seconds)
    } finally {
      Await.result(producer.close(), 1.minute)
    }
  }



}
