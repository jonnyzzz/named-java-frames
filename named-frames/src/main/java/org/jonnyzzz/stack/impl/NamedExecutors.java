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
import org.jonnyzzz.stack.NamedStackFrame;
import org.jonnyzzz.stack.impl.gen.NamedExecutorsGenerator;
import org.jonnyzzz.stack.impl.gen.jcl.JavaClassLoaderGenerator;
import org.jonnyzzz.stack.impl.gen.stub.FailedToCreateNamedExecutorsGenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class NamedExecutors implements NamedExecutorsGenerator {
    private final NamedExecutorsGenerator myLoader = init();

    @NotNull
    private NamedExecutorsGenerator init() {
        try {
            return new JavaClassLoaderGenerator();
        } catch (Throwable t) {
            final String massage = "Failed to initialize proxy for NamedStackFrame. " + t.getMessage() + ". Default proxy will be used";
            logError(massage, t);
            return FailedToCreateNamedExecutorsGenerator.INSTANCE;
        }
    }

    @NotNull
    public NamedExecutor generate(@NotNull final String name) {
        try {
            return myLoader.generate(name);
        } catch (Throwable t) {
            //failed to generate the proxy, so we fallback
            logError("Failed to generate proxy for NamedStackFrame with name '" + name + "'. " + t.getMessage() + ". Default proxy will be used", t);
            return FailedToCreateNamedExecutorsGenerator.INSTANCE.generate(name);
        }
    }

    private static void logError(@NotNull final String massage, @NotNull final Throwable t) {
        Logger.getLogger(NamedStackFrame.class.getName()).log(Level.SEVERE, massage, t);
    }
}
