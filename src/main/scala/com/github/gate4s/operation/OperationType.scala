package com.github.gate4s.operation

sealed trait OperationType

object OperationType {

  case object Pre extends OperationType

  case object Post extends OperationType

  case object Error extends OperationType

}
