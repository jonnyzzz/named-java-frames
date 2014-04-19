package org.jonnyzzz.stack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jonnyzzz.stack.impl.CachedNamedExecutors;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;
import org.jonnyzzz.stack.impl.NamedExecutors;

import java.util.concurrent.Callable;

/**
 * Updates execution stack of the thread to contain a frame
 * with a given name.
 *
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public abstract class NamedStackFrame {
    public <V> V stackLine(@NotNull final String name,
                           @NotNull final Callable<V> fun) throws Exception {
        try {
            //noinspection unchecked
            return (V) executor(name)._(new InternalAction() {
                @Nullable
                public Object execute() throws Exception {
                    return fun.call();
                }
            });
        } catch (Throwable th) {
            if (th instanceof Exception) {
                throw (Exception) th;
            } else {
                return wrapUnexpectedException(th);
            }
        }
    }

    public void stackLine(@NotNull final String name,
                          @NotNull final Runnable fun) {

        try {
            executor(name)._(new InternalAction() {
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

    public <E extends Throwable> void stackLine(@NotNull final String name,
                                                @NotNull final Class<E> exception,
                                                @NotNull final UnderStackAction<E> fun) throws E {
        try {
            executor(name)._(new InternalAction() {
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

    public <R, E extends Throwable> void stackLine(@NotNull final String name,
                                                   @NotNull final Class<E> exception,
                                                   @NotNull final UnderStackFunction<R, E> fun) throws E {
        try {
            executor(name)._(new InternalAction() {
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
    protected abstract NamedExecutor executor(@NotNull final String name);

    @NotNull
    protected <V> V wrapUnexpectedException(@NotNull final Throwable th) {
        throw new RuntimeException("Undeclared exception. " + th.getMessage(), th);
    }

    /**
     * Allows to re-use all generated NamedStackFrame proxies
     * for all used names. Caches are stored in static variable
     * and never cleaned up.
     * <br />
     * This method could be used when app possible used method names
     * set is limited
     * <br />
     * Too many different names may consume to much of perm-gen space
     *
     * @return globally-cached factory instance
     */
    @NotNull
    public static NamedStackFrame global() {
        return GlobalCachedNamedStackFrames.INSTANCE;
    }

    /**
     * This instance of the factory does not have static caches.
     * Meanwhile all proxies for each name are cached and could
     * only be cleaned up with the object instance
     * <br />
     * Generated proxies cleanup depends upon JVM classes unloading
     * @return fresh instance
     */
    @NotNull
    public static NamedStackFrame newInstance() {
        return new CachedNamedStackFrames();
    }

    private static class CachedNamedStackFrames extends NamedStackFrame {
        private final CachedNamedExecutors myLoaders = new CachedNamedExecutors(new NamedExecutors());

        @NotNull
        @Override
        protected NamedExecutor executor(@NotNull String name) {
            return myLoaders.generate(name);
        }
    }

    private static class GlobalCachedNamedStackFrames extends CachedNamedStackFrames {
        private static final CachedNamedStackFrames INSTANCE = new GlobalCachedNamedStackFrames();
    }
}
