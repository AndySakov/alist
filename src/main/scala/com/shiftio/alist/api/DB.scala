package com.shiftio.alist.api

import better.files.File
import better.files.File.home
import com.shiftio.alist.api.commons.{TodoItem, error}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DB {

  val db: File = home / ".alist" / "~.db"
  val dir: File = home / ".alist"

  var inMemory: List[TodoItem] = selectAll

  def selectAll: List[TodoItem] = {
    if (!db.exists) {
      println(error("[INFO] DB file not found!\n[INFO] Creating new db in present working directory"))
      dir.createDirectoryIfNotExists(createParents = true)
      db.createFileIfNotExists()
    }
    read()
  }

  def read(): List[TodoItem] = {
    val data = Golem.decrypt(db.contentAsString)
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
      Golem.encrypt(inMemory.map(item => {
        List(item.id, item.name, item.desc, item.dueBy.getOrElse("N/A"), item.remind).mkString(",")
      }).mkString("\n")))
  }
}
