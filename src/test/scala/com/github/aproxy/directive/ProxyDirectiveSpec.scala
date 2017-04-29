package com.github.aproxy.directive

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

// Todo Add test
class ProxyDirectiveSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val smallRoute =
    get {
      path("status") {
        complete {
          "OK"
        }
      }
    }

  "Proxy Directive" should {

    "return a OK for GET requests to status path" in {
      Get("/status") ~> smallRoute ~> check {
        responseAs[String] shouldEqual "OK"
      }
    }
  }
}
