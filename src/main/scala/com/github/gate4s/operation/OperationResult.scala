package com.github.gate4s.operation

sealed trait ExecutionStatus

object ExecutionStatus {

  case object SUCCESS extends ExecutionStatus

  case object SKIPPED extends ExecutionStatus

  case object DISABLED extends ExecutionStatus

  case object FAILED extends ExecutionStatus

}

sealed trait OperationResult[T] {
  def result: Option[T]

  def status: ExecutionStatus
}

case class DefaultOperationResult[T](result: Option[T], status: ExecutionStatus) extends OperationResult[T]

object DefaultOperationResult {
  def apply(status: ExecutionStatus): OperationResult[None.type] = new DefaultOperationResult(status = status, result = None)

  def apply(): OperationResult[None.type] = new DefaultOperationResult(status = ExecutionStatus.DISABLED, result = None)
}