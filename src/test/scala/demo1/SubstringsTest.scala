package demo1

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Assertions.*

import scala.util.Success

class SubstringsTest extends AnyFunSpec with Matchers {
  describe("Scalatest - Substrings") {
    it("example 1") {
      Substrings.split("123 456", " ") should be(Success(List(1235, 456)))
    }

    it("example 2") {
      assert(Substrings.split("123 456", " ") == Success(List(1235, 456)))
    }

    it("example 3") {
      assert(Substrings.split("123 abc", " ").isSuccess)
    }
  }
}
