package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.InternalAction;
import org.jonnyzzz.stack.impl.NamedExecutor;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class JavaGeneratorTemplate implements NamedExecutor {
    public Object _(@NotNull final InternalAction action) throws Throwable {
        return action.execute();
    }
}
