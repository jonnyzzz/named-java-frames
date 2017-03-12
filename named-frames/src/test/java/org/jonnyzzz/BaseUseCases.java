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
import org.jonnyzzz.stack.FrameAction;
import org.jonnyzzz.stack.FrameFunction;
import org.jonnyzzz.stack.NamedStackFrame;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.jonnyzzz.Stack.stackTrace;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public abstract class BaseUseCases {
    @Test
    public void test_names_invalid() throws Throwable {
        // https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html

        final String brokenName = " . ; [ / < >";

        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName(brokenName).action(new FrameAction<Throwable>() {
            public void action() throws Throwable {
                RRR();
            }

            private void RRR() {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertThat(s, not(containsString("__FailedToCreateNamedExecutor__")));
        Assert.assertThat(s, containsString("RRR"));
    }

    @Test
    public void test_names_clash_execute() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("execute").action(new FrameAction<Throwable>() {
            public void action() throws Throwable {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("execute"));
    }

    @Test
    public void test_names_clash_toString() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("toString").action(new FrameAction<Throwable>() {
            public void action() throws Throwable {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("toString"));
    }

    @Test
    public void test_action() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("someMagicTestName").action(new FrameAction<Throwable>() {
            public void action() throws Throwable {
                result.set(stackTrace());

            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_runnable() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("someMagicTestName").run(new Runnable() {
            public void run() {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_callable() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("someMagicTestName").call(new Callable<Object>() {
            public Object call() {
                result.set(stackTrace());
                return 42;
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_function() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("someMagicTestName").execute(new FrameFunction<Object, Throwable>() {
            @NotNull
            public Object execute() throws Throwable {
                result.set(stackTrace());
                return 42;
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_generated_name_is_used_only_once() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("someMagicTestName").run(new Runnable() {
            public void run() {
                result.set(stackTrace());
            }
        });

        final String s = result.get();

        final int i1 = s.indexOf("someMagicTestName");
        Assert.assertTrue(i1 >= 0);

        final int i2 = s.indexOf("someMagicTestName", i1+2);
        Assert.assertTrue(i2 < 0);
    }

    @Test
    public void test_long_names_function() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        call().forName("вот это вот тут так получилось").execute(new FrameFunction<Object, Throwable>() {
            @NotNull
            public Object execute() throws Throwable {
                result.set(stackTrace());
                return 42;
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("вот это вот тут так получилось"));
    }

    @Test(timeout = 500)
    public void test_should_not_generate_too_many_same_classes() throws Throwable {
        ///this test should not slowdown
        final AtomicInteger result = new AtomicInteger();
        final NamedStackFrame call = call();
        for(int i = 0 ; i < 10 * 1000; i++) {
            call.forName("вот это вот тут так получилось").execute(new FrameFunction<Object, Throwable>() {
                @NotNull
                public Object execute() throws Throwable {
                    result.incrementAndGet();
                    return 42;
                }
            });
        }
    }

    @NotNull
    protected abstract NamedStackFrame call();
}
