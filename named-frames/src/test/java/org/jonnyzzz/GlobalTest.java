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

package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class GlobalTest {
    @NotNull
    protected NamedStackFrame call() {
        return NamedStackFrame.global();
    }

    @Test(timeout = 500)
    public void test_should_not_generate_too_many_same_classes() throws Throwable {
        ///this test should not slowdown
        final NamedStackFrame call = call();
        for(int i = 0 ; i < 10 * 1000; i++) {
            //noinspection RedundantStringConstructorCall
            call.forName(new String("same text"));
        }
    }
}
