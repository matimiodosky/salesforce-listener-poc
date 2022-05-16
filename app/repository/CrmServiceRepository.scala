package repository

import model.AuthInfo
import play.api.Configuration
import play.api.libs.ws.WSClient

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._

import scala.concurrent.Future

@Singleton
class CrmServiceRepository @Inject() (ws: WSClient, config: Configuration) {

  private implicit val authInfoReads: Reads[AuthInfo] = Json.reads[AuthInfo]

  val crmServiceUrl: String = config.get[String]("crmService.url")

  def getSalesforceAuthInfo: Future[AuthInfo] = ws
    .url(s"$crmServiceUrl/crm-api/v1/sfdc/auth")
    .get()
    .map(_.json)
    .map(_.validate[AuthInfo])
    .map(_.get)



}
