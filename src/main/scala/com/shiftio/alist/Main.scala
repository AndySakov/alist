package com.shiftio.alist

//import Api.nextID

import com.shiftio.alist.api.Api.{add, cls, edit, list, _}
import com.shiftio.alist.api.DB.{commit, inMemory}
import com.shiftio.alist.api.commons._

import scala.annotation.tailrec
import scala.io.StdIn._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object Main extends App {

  def run(input: String): Unit = {
    cls()

    println(header)

    input match {
      case h if h take 4 equalsIgnoreCase "help" => println(helpText)

      case v if v take 4 equalsIgnoreCase "add" => add()

      case x if x take 4 equalsIgnoreCase "list" => list()

      case v if v take 4 equalsIgnoreCase "view" =>
        Try(
          v.split(" ").last.toInt
        ) match {
          case Failure(err) =>
            err match {
              case _ =>
                println(error("\tPlease include the id in the command!"))
            }
          case Success(id) =>
            inMemory filter (_.id == id) match {
              case x if x.isEmpty =>
                println("\tNo todo list item matches id " + id)
              case x =>
                x foreach viewTodoItem
            }
        }

      case v if v take 4 equalsIgnoreCase "edit" =>
        Try(
          v.split(" ").last.toInt
        ) match {
          case Failure(err) =>
            err match {
              case _ =>
                println(error("\tPlease include the id in the command!"))
            }
          case Success(id) =>
            edit(id)
        }

      case v if v take 4 equalsIgnoreCase "drop" =>
        Try(
          v.split(" ").last.toInt
        ) match {
          case Failure(err) =>
            err match {
              case _ =>
                println(error("\tPlease include the id in the command!"))
            }
          case Success(id) =>
            if (inMemory.isEmpty) {
              println(error("\tNo todo list item matches id " + id))
            } else {
              inMemory = inMemory.filterNot(_.id == id)
            }
        }
        list()

      case q if q take 4 equalsIgnoreCase "quit" =>
        println(success("\tGoodbye!"))
        System.exit(0)

      case _ => println(error("\t\tInvalid Input!"))

    }
    commit()
  }

  @tailrec
  def prompt(): Unit = {
    print(promptText)

    run(readLine)

    prompt()
  }

  cls()

  println(
    header
  )
  run("list")
  prompt()
}