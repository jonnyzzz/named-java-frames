package org.jonnyzzz.stack.impl;

import com.sun.istack.internal.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.StackLine;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutors {
    private static final GeneratedClassLoader myLoader = new GeneratedClassLoader();
    private static final ConcurrentHashMap<String, NamedExecutor> myLazyCache = new ConcurrentHashMap<String, NamedExecutor>();

    @NotNull
    public static NamedExecutor executor(@NotNull final String name) {
        try {
            //lazy & non-blocking cache created executors to avoid too
            //may classes from loading

            final NamedExecutor executor = myLazyCache.get(name);
            if (executor != null) return executor;

            final NamedExecutor newExecutor = myLoader.generate(name);
            final NamedExecutor oldExecutor = myLazyCache.putIfAbsent(name, newExecutor);
            return oldExecutor != null ? oldExecutor : newExecutor;
        } catch (Throwable t) {
            //failed to generate the proxy, so we fallback
            Logger.getLogger(StackLine.class).warning("Failed to generate proxy for StackLine with name '" + name + "'. " + t.getMessage() + ". Default proxy will be used", t);
            return new StubExecutorImpl();
        }
    }

}
