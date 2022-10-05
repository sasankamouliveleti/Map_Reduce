package HelperUtils

import MapReduceJobs.MRJob1.logger
import com.typesafe.config.{Config, ConfigFactory}

import scala.util.matching.Regex

object Constants {

  val messageLeveInfo = "INFO"
  val messageLevelError = "ERROR"
  val messageLevelDebug = "DEBUG"
  val messageLevelWarn = "WARN"

  val MRJob1 = "MessageLevelFrequencyInTimeIntervals"
  val MRJob2 = "SortedErrorLevelLogs"
  val MRJob2_Final = "DescSortErrorTypes"
  val MRJob3 = "MessageLevelFrequency"
  val MRJob4 = "LongestMatchLog"

  val fileSystemType =  "fs.defaultFS"
  val fileSystemTypeVal = "file:///"
  val noOfMappers = "mapreduce.job.maps"
  val noOfReducers = "mapreduce.job.reduces"
  val noOfMappersVal = "1"
  val noOfReducersVal = "1"

  val config: Config = ConfigFactory.load("application.conf").getConfig("userDefinedInputs")
  val mainPattern: Regex = Constants.config.getString("patternToSearch").r
  val definedTimeInterval: Int = config.getInt("timeInterval")

  def generateTimeInterval(timeSplit: Array[String]): String = {
    logger.debug("the time interval set by user is" + definedTimeInterval.toString)
    val hours = timeSplit(0).toInt
    val lowerBoundMin = (timeSplit(1).toInt / definedTimeInterval) * definedTimeInterval
    val upperBoundMin = lowerBoundMin + definedTimeInterval
    if(lowerBoundMin == 0 && upperBoundMin == 60){
      (hours%24).toString + ":" + "00" + " " + ((hours + 1)%24).toString + ":" + "00"
    }
    else if (upperBoundMin == 60) {
      (hours%24).toString + ":" + lowerBoundMin.toString + " " + ((hours + 1)%24).toString + ":" + "00"
    } else if (upperBoundMin > 60) {
      (hours%24).toString + ":" + lowerBoundMin.toString + " " + ((hours + 1)%24).toString + ":" + (upperBoundMin - 60).toString
    } else {
      (hours%24).toString + ":" + lowerBoundMin.toString + " " + (hours%24).toString + ":" + upperBoundMin.toString
    }
  }
}
