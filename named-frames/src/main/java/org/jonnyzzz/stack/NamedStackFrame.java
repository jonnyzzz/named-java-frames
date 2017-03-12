/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Eugene Petrenko (eugene.petrenko@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jonnyzzz.stack;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.CachedNamedStackFrames;
import org.jonnyzzz.stack.impl.GlobalCachedNamedStackFrames;

/**
 * Updates execution stack of the thread to contain a frame
 * with a given name.
 *
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public abstract class NamedStackFrame {
    /**
     * Returns executor for a given name
     * @param name string to include into as a stack frame
     * @return executor
     * @since 1.1
     */
    @NotNull
    public final NamedExecutor forName(@NotNull final String name) {
        return executor(name);
    }

    @NotNull
    protected abstract NamedExecutor executor(@NotNull final String name);

    /**
     * Allows to re-use all generated NamedStackFrame proxies
     * for all used names. Caches are stored in static variable
     * and never cleaned up.
     * <p>
     * This method could be used when app possible used method names
     * set is limited
     * <p>
     * Too many different names may consume to much of perm-gen space
     *
     * @return globally-cached factory instance
     */

    @NotNull
    public static NamedStackFrame global() {
        return GlobalCachedNamedStackFrames.INSTANCE;
    }

    /**
     * This instance of the factory does not have static caches.
     * Meanwhile all proxies for each name are cached and could
     * only be cleaned up with the object instance
     * <p>
     * Generated proxies cleanup depends upon JVM classes unloading
     *
     * @return fresh instance
     */
    @NotNull
    public static NamedStackFrame newInstance() {
        return new CachedNamedStackFrames();
    }
}
