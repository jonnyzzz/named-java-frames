package org.jonnyzzz.stack.impl;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutorImpl implements NamedExecutor {
    public Object execute(InternalAction action) throws Throwable {
        return this_is_a_special_name_placeholder(action);
    }

    //do not rename the method. It's used for code-generation
    private Object this_is_a_special_name_placeholder(InternalAction action) throws Throwable {
        return action.execute();
    }
}
