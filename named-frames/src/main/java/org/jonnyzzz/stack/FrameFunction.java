package org.jonnyzzz.stack;

/**
 * Helper interface to workaround checked exceptions in Java
 *
 * @since 1.1
 */
public interface FrameFunction<R, E extends Throwable> {
    R execute() throws E;
}
