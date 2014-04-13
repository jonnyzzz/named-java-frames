package org.jonnyzzz.stack;

import org.jonnyzzz.stack.impl.GeneratedClassLoader;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class StackLine {
    public static <E extends Throwable> void stackLine(final String name,
                                                       final Class<E> exception,
                                                       final UnderStackAction<E> fun) throws E {
        try {
            executor(name).execute(new InternalAction() {
                public Object execute() throws Throwable {
                    fun.execute();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            if (exception.isInstance(throwable)) {
                throw exception.cast(throwable);
            } else {
                throw new RuntimeException(throwable);
            }
        }
    }

    public static void stackLine(final String name, final Runnable fun) {

        try {
            executor(name).execute(new InternalAction() {
                public Object execute() throws Throwable {
                    fun.run();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


    public static <R, E extends Throwable> void stackLine(final String name,
                                                          final Class<E> exception,
                                                          final UnderStackFunction<R, E> fun) throws E {
        try {
            executor(name).execute(new InternalAction() {
                public Object execute() throws Throwable {
                    fun.execute();
                    return null;
                }
            });
        } catch (Throwable throwable) {
            if (exception.isInstance(throwable)) {
                throw exception.cast(throwable);
            } else {
                throw new RuntimeException(throwable);
            }
        }
    }

    public interface UnderStackAction<E extends Throwable> {
        void execute() throws E;
    }

    public interface UnderStackFunction<R, E extends Throwable> {
        R execute() throws E;
    }

    private static NamedExecutor executor(final String name) {
        //lazy & non-blocking cache created executors to avoid too
        //may classes from loading

        final NamedExecutor executor = myLazyCache.get(name);
        if (executor != null) return executor;

        final NamedExecutor newExecutor = myLoader.generate(name);
        final NamedExecutor oldExecutor = myLazyCache.putIfAbsent(name, newExecutor);
        return oldExecutor != null ? oldExecutor : newExecutor;
    }

    private static final GeneratedClassLoader myLoader = new GeneratedClassLoader();
    private static final ConcurrentHashMap<String, NamedExecutor> myLazyCache = new ConcurrentHashMap<String, NamedExecutor>();

}
