package org.jonnyzzz.stack.impl.gen;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.NamedExecutor;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public interface NamedExecutorsGenerator {
    @NotNull
    NamedExecutor generate(@NotNull String name);
}
