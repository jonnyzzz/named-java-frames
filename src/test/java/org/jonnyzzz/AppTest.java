package org.jonnyzzz;

import com.sun.istack.internal.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_action() throws Throwable {

        final AtomicReference<String> result = new AtomicReference<String>();
        StackLine.stackLine("someMagicTestName", new StackLine.UnderStackAction<Throwable>() {
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
        StackLine.stackLine("someMagicTestName", new StackLine.UnderStackFunction<Object, Throwable>() {
            public Object execute() throws Throwable {
                result.set(stackTrace());
                return 42;
            }
        });

        final String s = result.get();
        Assert.assertTrue(s, s.contains("someMagicTestName"));
    }


    @NotNull
    private static String stackTrace() {
        try {
            throw new Exception();
        } catch (Exception e) {
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        }
    }
}
