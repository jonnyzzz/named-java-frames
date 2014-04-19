package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.NamedStackFrame;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_names_clash_execute() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        NamedStackFrame.stackLine("execute", Throwable.class, new NamedStackFrame.UnderStackAction<Throwable>() {
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
        NamedStackFrame.stackLine("toString", Throwable.class, new NamedStackFrame.UnderStackAction<Throwable>() {
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
        NamedStackFrame.stackLine("someMagicTestName", Throwable.class, new NamedStackFrame.UnderStackAction<Throwable>() {
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
        NamedStackFrame.stackLine("someMagicTestName", new Runnable() {
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
        NamedStackFrame.stackLine("someMagicTestName", new Callable<Object>() {
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
        NamedStackFrame.stackLine("someMagicTestName", Throwable.class, new NamedStackFrame.UnderStackFunction<Object, Throwable>() {
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
    public void test_long_names_function() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        NamedStackFrame.stackLine("вот это вот тут так получилось", Throwable.class, new NamedStackFrame.UnderStackFunction<Object, Throwable>() {
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
    public void test_should_not_generate_too_may_classes() throws Throwable {
        ///this test should not slowdown
        final AtomicInteger result = new AtomicInteger();
        for(int i = 0 ; i < 10 * 1000; i++) {
            NamedStackFrame.stackLine("вот это вот тут так получилось", Throwable.class, new NamedStackFrame.UnderStackFunction<Object, Throwable>() {
                @NotNull
                public Object execute() throws Throwable {
                    result.incrementAndGet();
                    return 42;
                }
            });
        }
    }


    @NotNull
    private static String stackTrace() {
        try {
            throw new Exception();
        } catch (Exception e) {
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.printf(sw.toString());
            return sw.toString();
        }
    }
}
