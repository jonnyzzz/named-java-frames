package org.jonnyzzz.stack.impl;

import com.sun.istack.internal.NotNull;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutorImpl implements NamedExecutor {
    public Object execute(@NotNull InternalAction action) throws Throwable {
        return action.execute();
    }
}
