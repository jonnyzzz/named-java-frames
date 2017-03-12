@file:JvmName("NamedStackFrameKotlin")
package org.jonnyzzz.stack

fun <T> NamedStackFrame.wrap(name: String, action: () -> T) : T {
  return executor(name).__(FrameFunction<T, Throwable> { action() })
}

