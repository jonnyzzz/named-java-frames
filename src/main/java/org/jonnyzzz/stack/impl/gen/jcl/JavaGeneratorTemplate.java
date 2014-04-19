package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.NamedExecutor;

import java.util.concurrent.Callable;

import static org.jonnyzzz.stack.NamedStackFrame.FrameAction;
import static org.jonnyzzz.stack.NamedStackFrame.FrameFunction;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class JavaGeneratorTemplate implements NamedExecutor {
    public final <V> V _(@NotNull Callable<V> fun) throws Exception {
        return fun.call();
    }

    public final void _(@NotNull Runnable fun) {
        fun.run();
    }

    public final <E extends Throwable> void _(@NotNull FrameAction<E> fun) throws E {
        fun.execute();
    }

    public final <R, E extends Throwable> R _(@NotNull FrameFunction<R, E> fun) throws E {
        return fun.execute();
    }
}
