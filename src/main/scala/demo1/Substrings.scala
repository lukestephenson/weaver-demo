package demo1

import scala.util.{Failure, Success, Try}

object Substrings {
  def split(source: String, character: String): Try[List[Int]] = {
    val results: List[Option[Int]] = source.split(character).toList.map(_.toIntOption)
    results.foldLeft[Try[List[Int]]](Success(List.empty)) { case (agg, elem) =>
      agg match
        case Failure(exception) => Failure(exception)
        case Success(aggVals) =>
          elem match
            case Some(value) => Success(value :: aggVals)
            case None => Failure(new RuntimeException("invalid element"))
    }.map(_.reverse)
  }
}
