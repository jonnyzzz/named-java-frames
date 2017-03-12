package org.jonnyzzz.stack;

/**
 * @since 1.1
 */
public interface FrameFunction<R, E extends Throwable> {
    R execute() throws E;
}
