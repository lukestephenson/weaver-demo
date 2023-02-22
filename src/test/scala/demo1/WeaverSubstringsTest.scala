package demo1

import weaver.FunSuite

import scala.util.Success

object WeaverSubstringsTest extends FunSuite {

  test("weaver - example 1") {
    expect(Substrings.split("123 456", " ") == Success(List(1235, 456)))
  }

  test("weaver - example 2") {
    expect(Substrings.split("123 abc", " ").isSuccess)
  }

}
