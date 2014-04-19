package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class Stack {
    @NotNull
    public static String stackTrace() {
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
