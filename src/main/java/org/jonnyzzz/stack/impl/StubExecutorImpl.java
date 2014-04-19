package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class StubExecutorImpl implements NamedExecutor {
    @Nullable
    public Object execute(@NotNull InternalAction action) throws Exception {
        return action.execute();
    }
}
