package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedExecutor;
import org.jonnyzzz.stack.NamedStackFrame;

/**
 * Created by eugene.petrenko@gmail.com
 */
public final class CachedNamedStackFrames extends NamedStackFrame {
    private final CachedNamedExecutors myLoaders = new CachedNamedExecutors(new NamedExecutors());

    @NotNull
    @Override
    protected final NamedExecutor executor(@NotNull String name) {
        return myLoaders.generate(name);
    }
}
