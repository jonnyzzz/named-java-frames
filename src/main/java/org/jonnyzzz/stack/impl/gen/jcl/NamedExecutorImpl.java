package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutorImpl implements NamedExecutor {
    public Object execute(@NotNull final InternalAction action) throws Throwable {
        return this_is_a_special_name_placeholder(action);
    }

    //do not rename the method. It's used for code-generation
    private Object this_is_a_special_name_placeholder(@NotNull final InternalAction action) throws Throwable {
        return action.execute();
    }
}
