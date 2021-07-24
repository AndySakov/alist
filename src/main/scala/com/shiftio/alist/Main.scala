package com.shiftio.alist

//import Api.nextID

import com.shiftio.alist.api.Api.{add, cls, edit, list, _}
import com.shiftio.alist.api.DB.{commit, inMemory}
import com.shiftio.alist.api.commons.{header, helpText, promptText}

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
          case Failure(err) => println(s"View process failed with exception: ${err.getCause}")
          case Success(id) =>
            inMemory filter (_.id == id) match {
              case x if x.isEmpty =>
                println("    No todo list item matches id " + id)
              case x =>
                x foreach viewTodoItem
            }
        }

      case v if v take 4 equalsIgnoreCase "edit" =>
        Try(
          v.split(" ").last.toInt
        ) match {
          case Failure(err) => println(s"Edit process failed with exception: ${err.getCause}")
          case Success(id) =>
            edit(id)
        }

      case v if v take 3 equalsIgnoreCase "drop" =>
        Try(
          v.split(" ").last.toInt
        ) match {
          case Failure(err) => println(s"View process failed with exception: ${err.getCause}")
          case Success(id) =>
            if (inMemory.isEmpty) {
              println("    No todo list item matches id " + id)
            } else {
              inMemory = inMemory.filterNot(_.id == id)
            }
        }
        list()

      case q if q take 4 equalsIgnoreCase "quit" =>
        println("Goodbye!")
        System.exit(0)

      case _ => println("Unrecognized Input!")

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
  prompt()
}