package com.github.aproxy.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.RequestContext
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.github.aproxy.internal.AppConfig

import scala.concurrent.{ExecutionContext, Future}

trait ProxyServer {
  implicit val actorSystem: ActorSystem

  def doRequest(path: RequestContext, backOff: Int = 2)
               (implicit ec: ExecutionContext): Future[HttpResponse]

  protected def http: HttpExt

  protected def routerFlowMapper: Map[String, Flow[_, _, Future[_]]]
}

// TODO: Add backoff
class DefaultProxyServer(implicit val actorSystem: ActorSystem,
                         implicit val mat: ActorMaterializer) extends ProxyServer {
  override protected val http: HttpExt = Http(actorSystem)

  override protected val routerFlowMapper = AppConfig.routes.mapValues(route => http.outgoingConnection(route.base, route.port))

  override def doRequest(ctx: RequestContext, backoff: Int = 2)(implicit ec: ExecutionContext): Future[HttpResponse] = {
    routerFlowMapper.get(ctx.request.uri.path.toString) match {
      case Some(flow) => Source.single(ctx.request).via(flow).runWith(Sink.head)
      case _ => throw new Exception()
    }
  }
}