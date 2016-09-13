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

import java.util.concurrent.Callable;

import static org.jonnyzzz.stack.NamedStackFrame.FrameAction;
import static org.jonnyzzz.stack.NamedStackFrame.FrameFunction;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public interface NamedExecutor {
    <V> V __(@NotNull final Callable<V> fun) throws Exception;
    void __(@NotNull final Runnable fun);
    <E extends Throwable> void __(@NotNull final FrameAction<E> fun) throws E;
    <R, E extends Throwable> R __(@NotNull final FrameFunction<R, E> fun) throws E;
}
