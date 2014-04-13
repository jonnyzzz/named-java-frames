package org.jonnyzzz.stack;

import com.sun.istack.internal.NotNull;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;
import org.jonnyzzz.stack.impl.NamedExecutorGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class StackLine {
    public static <E extends Throwable> void stackLine(@NotNull final String name,
                                                       @NotNull final Class<E> exception,
                                                       @NotNull final UnderStackAction<E> fun) throws E {

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


    public static <R, E extends Throwable> void stackLine(@NotNull final String name,
                                                          @NotNull final Class<E> exception,
                                                          @NotNull final UnderStackFunction<R, E> fun) throws E {
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
    private static class GeneratedClassLoader extends ClassLoader {
        private final AtomicInteger myCounter = new AtomicInteger();
        private GeneratedClassLoader() {
            super(NamedExecutor.class.getClassLoader());
        }

        public NamedExecutor generate(@NotNull final String name) {
            final String clazz = "com.jonnyzzz.generated.names.generated_class_" + myCounter.incrementAndGet();
            final byte[] bytes = NamedExecutorGenerator.generateWrapper(clazz, name);

            definePackage(clazz);
            final Class<?> aClass = defineClazz(clazz, bytes);

            try {
                return (NamedExecutor) aClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException("Failed to generate class for name: " + name + ". " + e.getMessage(), e);
            }
        }

        @NotNull
        private Class<?> defineClazz(@NotNull final String name, @NotNull final byte[] bytes) {
            return defineClass(name, bytes, 0, bytes.length);
        }

        private void definePackage(@NotNull final String name) {
            final int i = name.lastIndexOf('.');
            if (i >= 0) {
                final String pkgName = name.substring(0, i);
                // Check if package already loaded.
                final Package pkg = getPackage(pkgName);
                if (pkg == null) {
                    try {
                        definePackage(pkgName, null, null, null, null, null, null, null);
                    } catch (IllegalArgumentException e) {
                        // do nothing, package already defined by some other thread
                    }
                }
            }
        }
    }

}
