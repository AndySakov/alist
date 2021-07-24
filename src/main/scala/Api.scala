import DB.inMemory
import commons.OS._
import commons._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.io.StdIn.readLine
import scala.language.postfixOps
import scala.sys.process._


object Api {

  val listHeading: String =
    "    ID |    NAME    |  DESCRIPTION  |  DUE DATE  |    REMINDER " +
      "\n    ------------------------------------------------------------"

  def parse(string: String, limit: Int = 10): String =
    s"$string...${" " * ((limit - 3) - string.length)}"

  def listTodoItem(x: TodoItem): Unit =
    println(s"    ${x.id}  | ${parse(x.name.take(7))} | ${parse(x.desc.take(10))} | ${parse(x.dueBy.getOrElse("N/A").toString.take(5))} | ${if (x.remind) "     Yes" else "      No"}")

  def viewTodoItem(x: TodoItem): Unit =
    println(
      s"""    ID: ${x.id}
         |    Name: ${x.name}
         |    Description: ${x.desc}
         |    Due Date: ${x.dueBy.getOrElse("N/A")}
         |    Remind Me: ${if (x.remind) "Yes" else "No"} """.stripMargin
    )

  def cls(): Unit = _OS_NAME_ match {
    case LINUX => "clear".!
    case MAC_OS_X => "clear".!
    case WINDOWS => "cmd.exe -c cls".!
    case SUN_OS => "clear".!
    case FREEBSD => "clear".!
    case UNKNOWN =>
      println("Unrecognized OS\nExiting...")
      System.exit(0)
  }

  def readVar(identifier: String): String = {
    print(identifier)
    readLine
  }

  def add(): Unit = {
    println("    Add a new todo list item")
    val name = readVar("\tName: ")
    val desc = readVar("\tDescription: ")
    val dueBy = readVar("\tDue Date[yyyy-MM-dd][Press Enter to skip]: ") match {
      case "" => None
      case date => Some(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    }
    val remind = readVar("\tRemind[y/n]: ") match {
      case "y" => true
      case _ => false
    }
    inMemory = inMemory.appended(TodoItem(if (inMemory.isEmpty) 1 else inMemory.maxBy(_.id).id + 1, name, desc, dueBy, remind = remind))
  }

  def list(): Unit = {
    if (inMemory.isEmpty) {
      println("    You've not added any items yet!")
    } else {
      println(listHeading)
      inMemory foreach listTodoItem
    }
  }

  def edit(id: Int): Unit = {
    if (inMemory.isEmpty) {
      println("    No todo list item matches id " + id)
    } else {
      println("\tPress enter to leave unchanged")
      inMemory = inMemory.filterNot(_.id == id) ::: inMemory.filter(_.id == id).map(x => {
        println(s"\tOld Name: ${x.name}")
        val newName = readVar("\tNew Name: ")
        println(s"\tOld Description: ${x.desc}")
        val newDesc = readVar("\tNew Description: ")
        println(s"\tOld Due Date: ${x.dueBy}")
        val newDueBy = readVar("\tNew Due Date[yyyy-MM-dd]: ")
        println(s"\tOld Remind: ${x.remind}")
        val newRemind = readVar("\tRemind[y/n]: ")
        x.copy(
          x.id,
          {
            if (newName == "") {
              x.name
            } else {
              newName
            }
          },
          {
            if (newDesc == "") {
              x.desc
            } else {
              newDesc
            }
          },
          {
            if (newDueBy == "") {
              x.dueBy
            } else {
              Some(LocalDate.parse(newDueBy, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            }
          },
          {
            if (newRemind == "") {
              x.remind
            } else if (newRemind == "y") {
              true
            } else {
              false
            }
          }
        )
      })
    }
  }
}
