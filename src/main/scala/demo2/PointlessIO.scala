package demo2

import cats.effect.IO

object PointlessIO {
  def add(a: Int, b: Int): IO[Int] = IO(a - b)
}
