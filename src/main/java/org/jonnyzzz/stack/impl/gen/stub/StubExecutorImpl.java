package org.jonnyzzz.stack.impl.gen.stub;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class StubExecutorImpl implements NamedExecutor {
    @Nullable
    public Object execute(@NotNull InternalAction action) throws Throwable {
        return action.execute();
    }
}
