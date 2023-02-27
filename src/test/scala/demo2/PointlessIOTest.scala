package demo2

import cats.effect.IO
import org.scalatest.Assertions.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class PointlessIOTest extends AnyFunSpec with Matchers {
  describe("Scalatest - PointlessIO") {
    it("example 1") {
      PointlessIO.add(1, 2) should be (3)
    }
  }
}
