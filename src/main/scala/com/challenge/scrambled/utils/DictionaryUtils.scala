package com.challenge.scrambled.utils

import com.challenge.scrambled.config.Config.DictionaryConfig
import com.challenge.scrambled.config.Loggie
import com.challenge.scrambled.model.{ApplicationError, Dictionary, DictionaryLengthTooLong}

object DictionaryUtils extends Loggie {

  /**
    * Checks and validates against provided config the sum of all lengths in the dictionary
    *
    * @param dictionary the provided dictionary (seq of words) to validate
    * @param dictionaryConfig configuration of dictionary that contain limits
    * @return
    */
  def validate(dictionary: Dictionary)(
      dictionaryConfig: DictionaryConfig): Either[ApplicationError, Dictionary] = {
    logger.info(s"Validating total length for ${dictionary.words.size} words")
    val dictionaryAgg: Int = dictionary.words.mkString("").sizeIs
    dictionaryAgg > dictionaryConfig.totalLength match {
      case true =>
        Left(DictionaryLengthTooLong(dictionaryAgg, dictionaryConfig.totalLength))
      case false =>
        logger.info(
          s"Total dictionary length $dictionaryAgg is within set threshold (${dictionaryConfig.totalLength})")
        Right(dictionary)
    }
  }

}
