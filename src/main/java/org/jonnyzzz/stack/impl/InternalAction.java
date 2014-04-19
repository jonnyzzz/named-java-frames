package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.Nullable;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public interface InternalAction {
    @Nullable
    Object execute() throws Throwable;
}
