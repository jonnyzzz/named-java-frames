package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;

/**
 * Created by eugene.petrenko@gmail.com
 */
public class CachedNamedStackFrames extends NamedStackFrame {
    private final CachedNamedExecutors myLoaders = new CachedNamedExecutors(new NamedExecutors());

    @NotNull
    @Override
    protected NamedExecutor executor(@NotNull String name) {
        return myLoaders.generate(name);
    }
}
