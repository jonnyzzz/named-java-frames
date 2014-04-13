package org.jonnyzzz.stack.impl;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutorImpl implements NamedExecutor {
    public Object execute(InternalAction action) throws Throwable {
        return proxy(action);
    }

    private Object proxy(InternalAction action) throws Throwable {
        return action.execute();
    }
}
