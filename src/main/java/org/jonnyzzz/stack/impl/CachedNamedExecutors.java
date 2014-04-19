package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class CachedNamedExecutors implements NamedExecutorsGenerator {
    private final ConcurrentHashMap<String, NamedExecutor> myLazyCache = new ConcurrentHashMap<String, NamedExecutor>();
    private final NamedExecutorsGenerator myLoader;

    public CachedNamedExecutors(@NotNull final NamedExecutorsGenerator loader) {
        myLoader = loader;
    }

    @NotNull
    public NamedExecutor generate(@NotNull final String name) {
        NamedExecutor executor = myLazyCache.get(name);
        if (executor != null) return executor;

        synchronized (myLazyCache) {
            executor = myLazyCache.get(name);
            if (executor != null) return executor;

            final NamedExecutor newExecutor = myLoader.generate(name);
            myLazyCache.put(name, newExecutor);
            return newExecutor;
        }
    }
}
