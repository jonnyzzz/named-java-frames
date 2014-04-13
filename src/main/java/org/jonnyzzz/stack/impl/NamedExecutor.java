package org.jonnyzzz.stack.impl;

import com.sun.istack.internal.NotNull;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public interface NamedExecutor {
    Object execute(@NotNull final InternalAction action);
}
