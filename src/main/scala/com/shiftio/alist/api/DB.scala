package com.shiftio.alist.api

import better.files.Dsl.pwd
import better.files.File
import com.shiftio.alist.api.commons.TodoItem

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DB {

  val db: File = pwd / "db.csv"

  var inMemory: List[TodoItem] = selectAll

  def selectAll: List[TodoItem] = {
    if (!db.exists) {
      println("[INFO] DB file not found!\n[INFO] Creating new db in present working directory")
      db.createFile()
    }
    read()
  }

  def read(): List[TodoItem] = {
    val data = db.contentAsString
    if (data == "") {
      List()
    } else {
      data.split("\n").map(_.split(",")).map(
        item => {
          val n = item(3) match {
            case "N/A" => None
            case _ => Some(LocalDate.parse(item(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
          }
          TodoItem(item.head.toInt, item(1), item(2), n, if (item(4).last.toInt == 1) true else false)
        }
      ).toList
    }
  }

  def commit(): Unit = {
    db.write(
      inMemory.map(item => {
        List(item.id, item.name, item.desc, item.dueBy.getOrElse("N/A"), item.remind).mkString(",")
      }).mkString("\n"))
  }
}
