package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public interface NamedExecutor {
    /**
     * The wired name allows to simplify further renaming tasks to
     * avoid changing more in the bytecode
     */
    @Nullable
    Object _(@NotNull InternalAction action) throws Throwable;
}
