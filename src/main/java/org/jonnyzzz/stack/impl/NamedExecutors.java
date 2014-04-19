package org.jonnyzzz.stack.impl;

import com.sun.istack.internal.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.StackLine;
import org.jonnyzzz.stack.impl.gen.jcl.JavaClassLoaderGenerator;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;
import org.jonnyzzz.stack.impl.gen.stub.EmptyExecutorsGenerator;
import org.jonnyzzz.stack.impl.gen.stub.StubExecutorImpl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutors {
    private static final NamedExecutorsGenerator myLoader = init();
    private static final ConcurrentHashMap<String, NamedExecutor> myLazyCache = new ConcurrentHashMap<String, NamedExecutor>();

    private static NamedExecutorsGenerator init() {
        try {
            return new JavaClassLoaderGenerator();
        } catch (Throwable t) {
            Logger.getLogger(StackLine.class).warning("Failed to initialize proxy for StackLine. " + t.getMessage() + ". Default proxy will be used", t);
            return new EmptyExecutorsGenerator();
        }
    }

    @NotNull
    public static NamedExecutor executor(@NotNull final String name) {
        NamedExecutor executor = myLazyCache.get(name);
        if (executor != null) return executor;

        synchronized (myLazyCache) {
            executor = myLazyCache.get(name);
            if (executor != null) return executor;

            final NamedExecutor newExecutor = generateImpl(name);
            myLazyCache.put(name, newExecutor);
            return newExecutor;
        }
    }

    @NotNull
    private static NamedExecutor generateImpl(@NotNull final String name) {
        //avoid bytecode error
        if ("execute".equals(name)) {
            return new StubExecutorImpl();
        }


        try {
            return myLoader.generate(name);
        } catch (Throwable t) {
            //failed to generate the proxy, so we fallback
            Logger.getLogger(StackLine.class).warning("Failed to generate proxy for StackLine with name '" + name + "'. " + t.getMessage() + ". Default proxy will be used", t);
            return new StubExecutorImpl();
        }
    }
}
