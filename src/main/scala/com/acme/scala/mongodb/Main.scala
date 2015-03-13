package com.acme.scala.mongodb

import com.mongodb.ServerAddress
import com.mongodb.casbah.Imports._

import scala.util.Try

object Main extends App {
  //println("Hello, world!")

  val mongoHost = Try(System.getProperty("host").toString).toOption.getOrElse("localhost")
  val mongoPort = Try(System.getProperty("port").toInt).toOption.getOrElse(27017)
  val server = new ServerAddress(mongoHost, mongoPort)

  val username = Try(System.getProperty("username").toString).toOption.getOrElse("guest")
  val authdb = Try(System.getProperty("authdb").toString).toOption.getOrElse("admin")
  val password = Try(System.getProperty("password").toString).toOption.getOrElse("")
  val credentials = MongoCredential.createMongoCRCredential(userName = username, database = authdb,
    password = password.toCharArray)

  println(s"Connecting to MongoDB $mongoHost:$mongoPort...")
  val mongoClient = MongoClient(server, List(credentials))

  val mongoDatabaseName = Try(System.getProperty("database").toString).toOption.getOrElse("db")
  println(s"Using database $mongoDatabaseName...")
  val mongoDb = mongoClient(mongoDatabaseName)

  val mongoCollectionName = Try(System.getProperty("collection").toString).toOption.getOrElse("collection")
  println(s"Opening collection $mongoCollectionName...")
  val mongoCollection = mongoDb(mongoCollectionName)

  val n = mongoCollection.find().count()
  println(n)

  mongoClient.close()

}
