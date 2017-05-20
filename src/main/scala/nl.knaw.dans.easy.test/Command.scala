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

import nl.knaw.dans.lib.logging.DebugEnhancedLogging

import scala.language.reflectiveCalls
import scala.util.control.NonFatal
import scala.util.{Failure, Try}

object Command extends App with EasyTestApp with DebugEnhancedLogging {
  import logger._
  type FeedBackMessage = String

  debug("Starting command line interface")
  private val opts = CommandLineOptions(args, this)
  opts.verify()

  private val result: Try[FeedBackMessage] = opts.subcommand match {
    // Translate command line into calls to application API.

    case Some(cmd @ opts.runService) => runAsService()
    case _ => Failure(new IllegalArgumentException(s"Unknown command: ${opts.subcommand}"))
  }

  result.map(msg => println(s"OK: $msg"))
    .recover { case NonFatal(e) => println(s"FAILED: ${e.getMessage}") }

  private def runAsService(): Try[FeedBackMessage] = Try {
    import logger._
    val service = new EasyTestService()
    Runtime.getRuntime.addShutdownHook(new Thread("service-shutdown") {
      override def run(): Unit = {
        info("Stopping service ...")
        service.stop()
        info("Cleaning up ...")
        service.destroy()
        info("Service stopped.")
      }
    })
    service.start()
    info("Service started ...")
    Thread.currentThread.join()
    "Service terminated normally."
  }
}
