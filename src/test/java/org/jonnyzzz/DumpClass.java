package org.jonnyzzz;

import org.jonnyzzz.stack.impl.NamedExecutorImpl;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class DumpClass {
    public static void main(String[] args) throws IOException {

        System.out.println();


        System.out.println( "Hello World!" );


        final Class<NamedExecutorImpl> aClass = NamedExecutorImpl.class;
        InputStream stream = aClass.getResourceAsStream(aClass.getSimpleName() + ".class");
        int sz;
        while((sz = stream.read()) >= 0) {
            System.out.print("0x" + Integer.toHexString(sz));
            System.out.print(", ");
        }

    }
}
