package com.challenge.scrambled.model

import scala.util.control.NoStackTrace

sealed trait ApplicationError extends NoStackTrace {
  def errorMessage: String
  override def getMessage: String = errorMessage
}

case class DictionaryLengthTooLong(total: Int, allowed: Int) extends ApplicationError {
  override def errorMessage: String = s"Dictionary length $total exceeds allowed threshold $allowed"
}

case object InvalidCliArgumentsError extends ApplicationError {
  override def errorMessage: String =
    s"Cli arguments (dictionary, input) are invalid. Please check the provided values"
}
