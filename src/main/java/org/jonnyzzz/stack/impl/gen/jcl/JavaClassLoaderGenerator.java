package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.NamedExecutor;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public class JavaClassLoaderGenerator extends ClassLoader implements NamedExecutorsGenerator {
    private final AtomicInteger myCounter = new AtomicInteger();
    public JavaClassLoaderGenerator() {
        super(NamedExecutor.class.getClassLoader());
    }

    @NotNull
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
