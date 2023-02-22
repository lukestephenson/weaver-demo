package demo3

import cats.effect.IO
import org.apache.kafka.common.KafkaFuture
import scala.annotation.nowarn

object KafkaFutureToIoOps {
  def toIO[T](kafkaFuture: => KafkaFuture[T], mayInterruptIfRunning: Boolean = true): IO[T] = {
    IO.async[T] { callback =>
      IO {
        kafkaFuture.whenComplete { (result, error) =>
          if (error != null) {
            callback(Left(error))
          } else {
            callback(Right(result))
          }
        }: @nowarn("msg=discarded expression")
        Some(IO(kafkaFuture.cancel(mayInterruptIfRunning)).void)
      }
    }
  }
}
