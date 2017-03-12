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

package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedExecutor;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public final class CachedNamedExecutors implements NamedExecutorsGenerator {
    private final ConcurrentHashMap<String, NamedExecutor> myLazyCache = new ConcurrentHashMap<String, NamedExecutor>();
    private final NamedExecutorsGenerator myLoader;

    public CachedNamedExecutors(@NotNull final NamedExecutorsGenerator loader) {
        myLoader = loader;
    }

    @NotNull
    public final NamedExecutor generate(@NotNull final String name) {
        NamedExecutor executor = myLazyCache.get(name);
        if (executor != null) return executor;

        synchronized (myLazyCache) {
            executor = myLazyCache.get(name);
            if (executor != null) return executor;

            final NamedExecutor newExecutor = myLoader.generate(name);
            myLazyCache.put(name, newExecutor);
            return newExecutor;
        }
    }
}
