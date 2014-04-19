package org.jonnyzzz.stack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jonnyzzz.stack.impl.InternalAction;

import java.util.concurrent.Callable;

import static org.jonnyzzz.stack.impl.NamedExecutors.executor;

/**
 * Updates execution stack of the thread to contain a frame
 * with a given name.
 *
 *
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class StackLine {
    public static <V> V stackLine(@NotNull final String name,
                                  @NotNull final Callable<V> fun) throws Exception {
        try {
            //noinspection unchecked
            return (V) executor(name).execute(new InternalAction() {
                @Nullable
                public Object execute() throws Exception {
                    return fun.call();
                }
            });
        } catch (Throwable th) {
            if (th instanceof Exception) {
                throw (Exception)th;
            } else {
                return wrapUnexpectedException(th);
            }
        }
    }

    public static void stackLine(@NotNull final String name,
                                 @NotNull final Runnable fun) {

        try {
            executor(name).execute(new InternalAction() {
                @Nullable
                public Object execute() throws Throwable {
                    fun.run();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            wrapUnexpectedException(throwable);
        }
    }

    public static <E extends Throwable> void stackLine(@NotNull final String name,
                                                       @NotNull final Class<E> exception,
                                                       @NotNull final UnderStackAction<E> fun) throws E {
        try {
            executor(name).execute(new InternalAction() {
                @Nullable
                public Object execute() throws Throwable {
                    fun.execute();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            if (exception.isInstance(throwable)) {
                throw exception.cast(throwable);
            } else {
                wrapUnexpectedException(throwable);
            }
        }
    }

    public static <R, E extends Throwable> void stackLine(@NotNull final String name,
                                                          @NotNull final Class<E> exception,
                                                          @NotNull final UnderStackFunction<R, E> fun) throws E {
        try {
            executor(name).execute(new InternalAction() {
                @Nullable
                public Object execute() throws Throwable {
                    fun.execute();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            if (exception.isInstance(throwable)) {
                throw exception.cast(throwable);
            } else {
                wrapUnexpectedException(throwable);
            }
        }
    }

    public interface UnderStackAction<E extends Throwable> {
        void execute() throws E;
    }

    public interface UnderStackFunction<R, E extends Throwable> {
        R execute() throws E;
    }

    @NotNull
    private static <V> V wrapUnexpectedException(@NotNull final Throwable th) {
        throw new RuntimeException("Undeclared exception. " + th.getMessage(), th);
    }

}
