package org.jonnyzzz.stack;

/**
 * Helper interface to workaround checked exceptions in Java
 *
 * @since 1.1
 */
public interface FrameAction<E extends Throwable> {
    void action() throws E;
}
