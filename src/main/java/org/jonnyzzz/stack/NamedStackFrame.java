package org.jonnyzzz.stack;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.CachedNamedExecutors;
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
    public <V> V frame(@NotNull final String name,
                       @NotNull final Callable<V> fun) throws Exception {
        return executor(name)._(fun);
    }

    public void frame(@NotNull final String name,
                      @NotNull final Runnable fun) {
        executor(name)._(fun);
    }

    public <E extends Throwable> void frame(@NotNull final String name,
                                            @NotNull final FrameAction<E> fun) throws E {
        executor(name)._(fun);
    }

    public <R, E extends Throwable> R frame(@NotNull final String name,
                                            @NotNull final FrameFunction<R, E> fun) throws E {

        return executor(name)._(fun);
    }

    public interface FrameAction<E extends Throwable> {
        void execute() throws E;
    }

    public interface FrameFunction<R, E extends Throwable> {
        R execute() throws E;
    }

    @NotNull
    protected abstract NamedExecutor executor(@NotNull final String name);

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
     *
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
