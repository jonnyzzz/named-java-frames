package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

import static org.jonnyzzz.stack.NamedStackFrame.FrameAction;
import static org.jonnyzzz.stack.NamedStackFrame.FrameFunction;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public interface NamedExecutor {
    <V> V _(@NotNull final Callable<V> fun) throws Exception;
    void _(@NotNull final Runnable fun);
    <E extends Throwable> void _(@NotNull final FrameAction<E> fun) throws E;
    <R, E extends Throwable> R _(@NotNull final FrameFunction<R, E> fun) throws E;
}
