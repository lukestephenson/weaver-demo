package demo1

import org.scalatest.Assertions.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class ListUtilsTest extends AnyFunSpec with Matchers {
  describe("Scalatest - ListUtils") {
    it("using matchers - reverse always has the same number of elements") {
      (ListUtils.reverse(List(1, 2, 3)) should have).length(3)
    }

    it("using assert - reverse always has the same number of elements") {
      assert(ListUtils.reverse(List(1, 2, 3)).length == 3)
    }
  }
}
