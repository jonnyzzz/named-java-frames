package org.jonnyzzz.stack.impl;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public class GeneratedClassLoader extends ClassLoader {
    private final AtomicInteger myCounter = new AtomicInteger();
    public GeneratedClassLoader() {
        super(NamedExecutor.class.getClassLoader());
    }

    public NamedExecutor generate(final String name) {
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

    private Class<?> defineClazz(final String name, final byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }

    private void definePackage(final String name) {
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
