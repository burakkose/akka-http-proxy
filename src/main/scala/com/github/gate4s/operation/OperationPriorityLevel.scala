package com.github.gate4s.operation

sealed trait OperationPriorityLevel {
  protected def level: Int
}

object OperationPriorityLevel {

  case object High extends OperationPriorityLevel {
    override protected def level: Int = 3
  }

  case object Mid extends OperationPriorityLevel {
    override protected def level: Int = 2
  }

  case object Low extends OperationPriorityLevel {
    override protected def level: Int = 1
  }

  implicit def ordering[A <: OperationPriorityLevel] = new Ordering[A] {
    override def compare(x: A, y: A): Int = x.level.compareTo(y.level)
  }

}