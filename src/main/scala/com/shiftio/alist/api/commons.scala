package com.shiftio.alist.api

import com.colofabrix.scala.figlet4s.unsafe.{FIGureOps, Figlet4s, OptionsBuilderOps}
import fansi.{Attrs, Str}

import java.time.LocalDate
import scala.language.postfixOps
import scala.util.Random

object commons {

  case class TodoItem(id: Int, name: String, desc: String, dueBy: Option[LocalDate], remind: Boolean)

  val asciiStyle: Attrs = fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val captionStyle: Attrs = fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val help: Attrs = fansi.Underlined.On ++ fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val error: Attrs = fansi.Underlined.On ++ fansi.Bold.On ++ fansi.Color.Red
  val info: Attrs = fansi.Color.LightBlue
  val success: Attrs = fansi.Color.Green ++ fansi.Bold.On
  val promptText: Str = asciiStyle("~❯ ")

  val header =
    s"${
      asciiStyle(Figlet4s
        .builder("       ALIST")
        .render
        .asSeq
        .map(x => "       " + x)
        .mkString("\n"))
    } ${captionStyle("\n\t\tGet your life organized!")} ${help("\n\t\tType help to view options\n")}"

  val helpText: String =
    """    add: Add a new todo list item
      |    list: Lists all available todo list items
      |    view [x]: View todo list item with id x
      |    edit [x]: Edit todo list item with id x
      |    drop [x]: Drop todo list item with id x
      |    help: Prints this message
      |    quit: Exit the program
    """ stripMargin

  object Operation extends Enumeration {
    type Operation = Value
    val UPDATE, DELETE, ADD = Value
  }

  object OS extends Enumeration {
    type OS = Value
    val LINUX, MAC_OS_X, WINDOWS, SUN_OS, FREEBSD, UNKNOWN = Value

    def _OS_NAME_ : OS = {
      java.lang.System.getProperty("os.name").toLowerCase() match {
        case "linux" => OS.LINUX
        case "mac os x" => OS.MAC_OS_X
        case other =>
          if (other.contains("windows")) {
            OS.WINDOWS
          } else if (other.contains("sun")) {
            OS.SUN_OS
          } else if (other.contains("freebsd")) {
            OS.FREEBSD
          } else {
            OS.UNKNOWN
          }
      }
    }
  }

  object Result extends Enumeration {
    type Result = Value
    val SUCCESS, FAILED = Value
  }

}
