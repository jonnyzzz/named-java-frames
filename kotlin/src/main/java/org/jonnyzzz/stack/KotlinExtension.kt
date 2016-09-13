@file:JvmName("NamedStackFrameKotlin")
package org.jonnyzzz.stack

inline fun <T> NamedStackFrame.wrap(name: String, crossinline action: () -> T) : T {
  return executor(name).__(NamedStackFrame.FrameFunction<T, Throwable> { action() })
}

