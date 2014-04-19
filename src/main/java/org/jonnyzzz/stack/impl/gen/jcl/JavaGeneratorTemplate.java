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

package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.NamedExecutor;

import java.util.concurrent.Callable;

import static org.jonnyzzz.stack.NamedStackFrame.FrameAction;
import static org.jonnyzzz.stack.NamedStackFrame.FrameFunction;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class JavaGeneratorTemplate implements NamedExecutor {
    public final <V> V _(@NotNull Callable<V> fun) throws Exception {
        return fun.call();
    }

    public final void _(@NotNull Runnable fun) {
        fun.run();
    }

    public final <E extends Throwable> void _(@NotNull FrameAction<E> fun) throws E {
        fun.execute();
    }

    public final <R, E extends Throwable> R _(@NotNull FrameFunction<R, E> fun) throws E {
        return fun.execute();
    }
}
