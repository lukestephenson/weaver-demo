package demo1

import weaver.{Expectations, FunSuite}

import scala.util.Success

object WeaverListUtilsTest extends FunSuite {

  test("weaver - reverse always has the same number of elements") {
    expect(ListUtils.reverse(List(1, 2, 3)).length == 3)
  }

}
