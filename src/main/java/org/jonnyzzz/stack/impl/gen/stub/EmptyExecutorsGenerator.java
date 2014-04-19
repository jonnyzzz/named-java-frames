package org.jonnyzzz.stack.impl.gen.stub;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.NamedExecutor;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class EmptyExecutorsGenerator implements NamedExecutorsGenerator {
    @NotNull
    public NamedExecutor generate(@NotNull String name) {
        return new StubExecutorImpl();
    }
}
