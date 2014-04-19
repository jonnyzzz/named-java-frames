package org.jonnyzzz.stack.impl;

import com.sun.istack.internal.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;
import org.jonnyzzz.stack.impl.gen.jcl.JavaClassLoaderGenerator;
import org.jonnyzzz.stack.impl.gen.stub.EmptyExecutorsGenerator;
import org.jonnyzzz.stack.impl.gen.stub.StubExecutorImpl;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutors implements NamedExecutorsGenerator {
    private final NamedExecutorsGenerator myLoader = init();

    private NamedExecutorsGenerator init() {
        try {
            return new JavaClassLoaderGenerator();
        } catch (Throwable t) {
            Logger.getLogger(NamedStackFrame.class).warning("Failed to initialize proxy for NamedStackFrame. " + t.getMessage() + ". Default proxy will be used", t);
            return new EmptyExecutorsGenerator();
        }
    }

    @NotNull
    public NamedExecutor generate(@NotNull final String name) {
        try {
            return myLoader.generate(name);
        } catch (Throwable t) {
            //failed to generate the proxy, so we fallback
            Logger.getLogger(NamedStackFrame.class).warning("Failed to generate proxy for NamedStackFrame with name '" + name + "'. " + t.getMessage() + ". Default proxy will be used", t);
            return new StubExecutorImpl();
        }
    }
}
