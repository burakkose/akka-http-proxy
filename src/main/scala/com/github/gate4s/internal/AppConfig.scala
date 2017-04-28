package com.github.gate4s.internal

case class Route(base: String, port: Int)

// TODO Add reading functionality from configuration
object AppConfig {
  val routes = Map[String, Route]()
}
