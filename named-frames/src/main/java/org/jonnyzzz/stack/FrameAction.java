package org.jonnyzzz.stack;

/**
 * @since 1.1
 */
public interface FrameAction<E extends Throwable> {
    void execute() throws E;
}
