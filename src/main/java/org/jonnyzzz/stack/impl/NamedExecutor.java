package org.jonnyzzz.stack.impl;

/**
* @author Eugene Petrenko (eugene.petrenko@gmail.com)
*/
public interface NamedExecutor {
    Object execute(InternalAction action) throws Throwable;
}
