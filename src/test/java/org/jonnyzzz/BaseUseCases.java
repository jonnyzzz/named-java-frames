package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.jonnyzzz.Stack.stackTrace;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public abstract class BaseUseCases {
    @Test
    public void test_names_clash_execute() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().frame("execute", new NamedStackFrame.FrameAction<Throwable>() {
            public void execute() throws Throwable {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("execute"));
    }

    @Test
    public void test_names_clash_toString() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().frame("toString", new NamedStackFrame.FrameAction<Throwable>() {
            public void execute() throws Throwable {
                result.set(stackTrace());
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("toString"));
    }

    @Test
    public void test_action() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        call().frame("someMagicTestName", new NamedStackFrame.FrameAction<Throwable>() {
            public void execute() throws Throwable {
                result.set(stackTrace());

            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_runnable() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        call().frame("someMagicTestName", new Runnable() {
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
        call().frame("someMagicTestName", new Callable<Object>() {
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
        call().frame("someMagicTestName", new NamedStackFrame.FrameFunction<Object, Throwable>() {
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
        call().frame("someMagicTestName", new Runnable() {
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
        call().frame("вот это вот тут так получилось", new NamedStackFrame.FrameFunction<Object, Throwable>() {
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
            call.frame("вот это вот тут так получилось", new NamedStackFrame.FrameFunction<Object, Throwable>() {
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
