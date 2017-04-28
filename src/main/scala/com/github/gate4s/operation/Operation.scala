package com.github.gate4s.operation

sealed trait RunnableOperation[T] {
  def run(): Either[Throwable, T]
}

abstract case class Operation[T](filterType: OperationType,
                                 filterPriorityLevel: OperationPriorityLevel,
                                 isDisabled: Boolean
                                ) extends RunnableOperation[T] {
  def shouldOperation: Boolean = true

  def runOperation: OperationResult[_ >: None.type with T] = {
    if (!isDisabled) {
      if (shouldOperation) {
        run().map(res => DefaultOperationResult(Option(res), ExecutionStatus.SUCCESS))
          .getOrElse(DefaultOperationResult(ExecutionStatus.FAILED))
      } else
        DefaultOperationResult(ExecutionStatus.SKIPPED)
    } else
      DefaultOperationResult()
  }
}

object Operation {
  implicit def ordering = new Ordering[Operation[_]] {
    override def compare(x: Operation[_], y: Operation[_]): Int = {
      OperationPriorityLevel.ordering.compare(x.filterPriorityLevel, y.filterPriorityLevel)
    }
  }
}

