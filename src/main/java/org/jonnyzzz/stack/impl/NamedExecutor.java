package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public interface NamedExecutor {
    @Nullable
    Object execute(@NotNull InternalAction action) throws Throwable;
}
