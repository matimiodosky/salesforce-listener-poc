package consumer

import javax.inject.{Inject, Singleton}
import com.salesforce.emp.connector.EmpConnector

import java.util.concurrent.TimeUnit
import com.salesforce.emp.connector.TopicSubscription
import play.api.Configuration

import java.net.URL
import java.util.function.Consumer
import java.util.{Map => JMAp}
import com.salesforce.emp.connector.BayeuxParameters
import model.AuthInfo
import repository.CrmServiceRepository

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

@Singleton
class SalesforceConsumer @Inject() (config: Configuration, crmRepository: CrmServiceRepository) {

  println("getting auth info")
  private val authInfo: AuthInfo = Await.result(crmRepository.getSalesforceAuthInfo, 10 seconds)
  println("getting auth info - success")

  val params: BayeuxParameters = new BayeuxParameters() {
    override def bearerToken: String = authInfo.access_token
    override def host: URL = new URL(authInfo.instance_url)
  }

  private val connector = new EmpConnector(params)
  private var subscribers: List[Consumer[JMAp[String, AnyRef]]] = List(println(_))

  connector.setBearerTokenProvider(_ => params.bearerToken)

  connector.start.get(5, TimeUnit.SECONDS)

  private val subscription: TopicSubscription = connector
    .subscribe("/topic/ContactUpdates", EmpConnector.REPLAY_FROM_TIP, event => {
      subscribers.foreach(consumer => consumer.accept(event))
    })
    .get(5, TimeUnit.SECONDS)

  System.out.println(String.format("Subscribed: %s", subscription))

  def subscribe(subscriber: Consumer[JMAp[String, AnyRef]]): Unit = {
    subscribers = subscriber :: subscribers
  }

}


