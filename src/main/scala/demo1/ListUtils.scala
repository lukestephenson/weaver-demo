package demo1

object ListUtils {

  def reverse[A](list: List[A]): List[A] = list.reverse.drop(1)
}
