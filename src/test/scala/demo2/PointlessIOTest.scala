package demo2

import org.scalatest.Assertions.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class PointlessIOTest extends AnyFunSpec with Matchers {
  describe("Scalatest - PointlessIO") {
    it("example 1") {
      import cats.effect.unsafe.implicits.global

      PointlessIO.add(1, 2).map(sum => sum should be(3)).unsafeRunSync()
    }
  }
}
