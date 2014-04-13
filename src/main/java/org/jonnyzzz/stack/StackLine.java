package org.jonnyzzz.stack;

import com.sun.istack.internal.NotNull;
import org.jonnyzzz.stack.impl.GeneratedClassLoader;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;

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


    @NotNull
    private static NamedExecutor executor(@NotNull final String name) {
        return myLoader.generate(name);
    }

    private static final GeneratedClassLoader myLoader = new GeneratedClassLoader();

}
