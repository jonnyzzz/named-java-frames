package org.jonnyzzz.stack

import org.junit.Assert
import org.junit.Test
import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.atomic.AtomicReference

class KotlinExtensionTest {
  @Test
  fun testAPI() {
    val x =
            NamedStackFrame.newInstance().wrap("aaa") {
              42
            }

    Assert.assertEquals(x, 42)
  }


  @Test
  fun testExecute() {
    val result = AtomicReference<String>()
    NamedStackFrame.newInstance().wrap("execute") {
      result.set(stackTrace())
    }

    val s = result.get()
    Assert.assertTrue(s, s.contains("execute"))
  }
}


fun stackTrace(): String {
  try {
    throw Exception()
  } catch (e: Exception) {
    val sw = StringWriter()
    e.printStackTrace(PrintWriter(sw))
    System.out.printf(sw.toString())
    return sw.toString()
  }

}
