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

import javax.servlet.ServletContext

import nl.knaw.dans.lib.logging.DebugEnhancedLogging
import org.eclipse.jetty.ajp.Ajp13SocketConnector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.scalatra._
import org.scalatra.servlet.ScalatraListener

import scala.util.Try

class EasyTestService extends EasyTestApp with DebugEnhancedLogging {
  import logger._
  validateSettings()

  private val port = properties.getInt("daemon.http.port")
  private val server = new Server(port)
  private val context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS)
  context.setInitParameter(ScalatraListener.LifeCycleKey, "nl.knaw.dans.easy.test.ServletMounter")
  context.setAttribute(CONTEXT_ATTRIBUTE_APPLICATION, this)
  context.addEventListener(new ScalatraListener())
  server.setHandler(context)
  info(s"HTTP port is $port")

  if (properties.containsKey("daemon.ajp.port")) {
    val ajp = new Ajp13SocketConnector()
    val ajpPort = properties.getInt("daemon.ajp.port")
    ajp.setPort(ajpPort)
    server.addConnector(ajp)
    info(s"AJP port is $ajpPort")
  }

  def start(): Try[Unit] = Try {
    info("Starting HTTP service ...")
    server.start()
  }

  def stop(): Try[Unit] = Try {
    info("Stopping HTTP service ...")
    server.stop()
  }

  def destroy(): Try[Unit] = Try {
    server.destroy()
  }
}

class ServletMounter extends LifeCycle {
  override def init(context: ServletContext): Unit = {
    context.getAttribute(CONTEXT_ATTRIBUTE_APPLICATION) match {
      case app: EasyTestApp => context.mount(EasyTestServlet(app), "/")
      case _ => throw new IllegalStateException("Service not configured: no EasyTestApp found")
    }
  }
}
