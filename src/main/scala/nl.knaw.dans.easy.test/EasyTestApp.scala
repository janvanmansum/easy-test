/**
 * Copyright (C) 2017 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.easy.test

import java.nio.file.{ Files, Paths }

import org.apache.commons.configuration.PropertiesConfiguration

import scala.io.Source

trait EasyTestApp {
  // extends ...
  // with ...
  // Mix in the traits that provided the application's functionality
  private val home = Paths.get(System.getProperty("app.home"))
  private val cfg = Seq(
    Paths.get("/etc/opt/dans.knaw.nl/easy-test/"),
    home.resolve("cfg")).find(Files.exists(_)).getOrElse { throw new IllegalStateException("No configuration directory found")}

  val version: String = resource.managed(scala.io.Source.fromFile(home.resolve("bin/version").toFile)).acquireAndGet {
    _.mkString
  }
  val properties = new PropertiesConfiguration(cfg.resolve("application.properties").toFile)


  // Fill the fields required by the traits mixed in above
  // val settingForTraitX = properties.getString("setting.x")

  def validateSettings(): Unit = {
    // Validate the settings read in above. This methods should cause the application to halt if settings are found to be invalid.
  }
}