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

import org.rogach.scallop.{ScallopConf, ScallopOption, Subcommand, singleArgConverter}

case class CommandLineOptions(override val args: Seq[String], app: EasyTestApp) extends ScallopConf(args) {
  appendDefaultToDescription = true
  editBuilder(_.setHelpWidth(110))
  printedName = "easy-test"
  private val _________ = " " * printedName.length
  private val SUBCOMMAND_SEPARATOR = "---\n"
  val description: String = s"""x"""
  val synopsis: String = s"""$printedName \\
           |      <synopsis of command line parameters> \\
           |      <...possibly continued again, or all joined on one line>""".stripMargin

  version(s"$printedName v${app.version}")
  banner(s"""
           |  $description
           |
           |Usage:
           |
           |  $synopsis
           |
           |Options:
           |""".stripMargin)
  //val url = opt[String]("someOption", noshort = true, descr = "Description of the option", default = app.someProperty)

  val runService = new Subcommand("run-service") {
    descr(
      "Starts EASY Test as a daemon that services HTTP requests")
    footer(SUBCOMMAND_SEPARATOR)
  }
  addSubcommand(runService)

  footer("")
}
