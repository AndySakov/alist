import OS._OS_NAME_
import com.colofabrix.scala.figlet4s.unsafe.{FIGureOps, Figlet4s, OptionsBuilderOps}

import java.time.LocalDateTime
import scala.annotation.tailrec
import scala.io.StdIn._
import scala.language.postfixOps
import scala.sys.process._
import scala.util.{Failure, Random, Success, Try}

object Main extends App {

  case class TodoItem(id: Int, name: String, desc: String, dueBy: Option[LocalDateTime], remind: Boolean)

  val asciiStyle = fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val captionStyle = fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val helpStyle = fansi.Underlined.On ++ fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val promptText = "> "
  val listHeading =
    "\tID |    NAME    |  DESCRIPTION  |   DUE BY   | REMINDER SET" +
      "\n\t------------------------------------------------------------"

  val helpText =
    """    add: Add a new todo list item
      |    list: Lists all available todo list items
      |    view [x]: View todo list item with id x
      |    edit [x]: Edit todo list item with id x
      |    del [x]: Delete todo list item with id x
      |    help: Prints this message
    """ stripMargin

  def todoList: List[TodoItem] =
    List(
      TodoItem(1, "Dishes", "Remember to wash the dishes", None, remind = false),
      TodoItem(2, "Sweep", "Sweep the living room and kitchen", None, remind = false),
      TodoItem(3, "Wash", "Wash all the dirty clothes available", None, remind = true),
      TodoItem(4, "Homework", "Do PHY101 homework with the internet", Some(LocalDateTime.now()), remind = true),
      TodoItem(5, "Submit", "Submit PHY101 homework", Some(LocalDateTime.now()), remind = true)
    )

  def parse(string: String, limit: Int = 10): String =
    s"$string...${" " * ((limit - 3) - string.length)}"

  def listTodoItem(x: TodoItem): Unit =
    println(s"\t${x.id}  | ${parse(x.name.take(7))} | ${parse(x.desc.take(10))} | ${parse(x.dueBy.getOrElse("N/A").toString.take(5))} | ${if (x.remind) "     Yes" else "      No"}")

  def viewTodoItem(x: TodoItem): Unit =
    println(
      s"""    ID: ${x.id}
         |    Name: ${x.name}
         |    Description: ${x.desc}
         |    Due By: ${x.dueBy.getOrElse("N/A")}
         |    Remind Me: ${if (x.remind) "Yes" else "No"} """.stripMargin
    )

  def cls(): Unit =
    _OS_NAME_ match {
      case OS.LINUX => "clear".!
      case OS.MAC_OS_X => "clear".!
      case OS.WINDOWS => "cmd.exe -c cls".!
      case OS.SUN_OS => "clear".!
      case OS.FREEBSD => "clear".!
      case OS.UNKNOWN =>
        println("Unrecognized OS\nExiting...")
        System.exit(0)
    }


  def handle(input: String): Unit = {
    cls()

    println(
      asciiStyle(Figlet4s
        .builder("AList   1 . 0 !")
        .render
        .asSeq
        .mkString("\n"))
        ++ captionStyle("\nGet your life organized!")
        ++ helpStyle("\nType help to view options\n")
    )

    input match {
      case h if h take 4 equalsIgnoreCase "help" => println(helpText)

      case v if v take 4 equalsIgnoreCase "add" => ???

      case x if x take 4 equalsIgnoreCase "list" =>
        println(listHeading)
        todoList foreach listTodoItem

      case v if v take 4 equalsIgnoreCase "view" =>
        Try(
          v.last.toString.toInt
        ) match {
          case Failure(err) => println(s"View process failed with exception: ${err.getCause}")
          case Success(id) =>
            todoList filter (_.id == id) foreach viewTodoItem
        }

      case v if v take 4 equalsIgnoreCase "edit" => ???

      case v if v take 4 equalsIgnoreCase "del" => ???

      case _ => println("Unrecognized Input!")

    }
  }

  @tailrec
  def prompt(): Unit = {
    print(promptText)

    handle(readLine)

    prompt()
  }

  cls()
  println(
    asciiStyle(Figlet4s
      .builder("AList   1 . 0 !")
      .render
      .asSeq
      .mkString("\n"))
      ++ captionStyle("\nGet your life organized!")
      ++ helpStyle("\nType help to view options\n")
  )
  prompt()
}