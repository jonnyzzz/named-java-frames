package org.jonnyzzz;

import org.jonnyzzz.stack.StackLine;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_action() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        StackLine.stackLine("someMagicTestName", Throwable.class, new StackLine.UnderStackAction<Throwable>() {
            public void execute() throws Throwable {
                result.set(stackTrace());

            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }

    @Test
    public void test_function() throws Throwable {
        final AtomicReference<String> result = new AtomicReference<String>();
        StackLine.stackLine("someMagicTestName", Throwable.class, new StackLine.UnderStackFunction<Object, Throwable>() {
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
        StackLine.stackLine("вот это вот тут так получилось", Throwable.class, new StackLine.UnderStackFunction<Object, Throwable>() {
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
            StackLine.stackLine("вот это вот тут так получилось", Throwable.class, new StackLine.UnderStackFunction<Object, Throwable>() {
                public Object execute() throws Throwable {
                    result.incrementAndGet();
                    return 42;
                }
            });
        }
    }


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
