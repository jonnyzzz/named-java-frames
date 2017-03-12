@file:JvmName("NamedStackFrameKotlin")
package org.jonnyzzz.stack

fun <T> NamedStackFrame.wrap(name: String, action: () -> T) : T {
  return executor(name).__(NamedStackFrame.FrameFunction<T, Throwable> { action() })
}

