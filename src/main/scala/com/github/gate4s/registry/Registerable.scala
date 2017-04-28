package com.github.gate4s.registry

trait Registerable[A] {
  type B

  def ref(a: A): B
}

object Registerable {
  type Aux[A0, B0] = Registerable[A0] {
    type B = B0
  }

  def apply[A, B0](ref0: A => B0): Aux[A, B0] = new Registerable[A] {
    type B = B0

    override def ref(a: A): B0 = ref0(a)
  }
}
