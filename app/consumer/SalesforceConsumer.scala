package consumer

import javax.inject.{Inject, Singleton}
import com.salesforce.emp.connector.LoginHelper.login
import com.salesforce.emp.connector.EmpConnector

import java.util.concurrent.TimeUnit
import com.salesforce.emp.connector.TopicSubscription
import play.api.Configuration

import java.net.URL
import java.util.function.Consumer
import java.util.{Map => JMAp}

@Singleton
class SalesforceConsumer @Inject() (config: Configuration) {

  private val url: URL = new URL(config.get[String]("salesforce.url"))
  private val password = config.get[String]("salesforce.password")
  private val token = config.get[String]("salesforce.security_token")
  private val username = config.get[String]("salesforce.username")
  private val params = login(url, username, s"$password$token")
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


