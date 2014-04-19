package org.jonnyzzz;

import org.jetbrains.annotations.NotNull;
import org.jonnyzzz.stack.impl.gen.jcl.NamedExecutorImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class DumpClass {
    public static void main(String[] args) throws IOException {

        System.out.println();


        System.out.println("Hello World!");


        final Class<NamedExecutorImpl> aClass = NamedExecutorImpl.class;
        InputStream stream = aClass.getResourceAsStream(aClass.getSimpleName() + ".class");
        int sz;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((sz = stream.read()) >= 0) {
            System.out.print(hex(sz));
            System.out.print(", ");
            bos.write((byte) sz);
        }

        System.out.printf("");

        final ByteIterator d = new ByteIterator(bos.toByteArray());

        System.out.println();

        System.out.println();
        System.out.println("Size: " + d.myData.length);
        line("0xCAFEBABE", hex(d.next()), hex(d.next()), hex(d.next()), hex(d.next()));
        line("version", hex(d.next()), hex(d.next()), hex(d.next()), hex(d.next()));
        final int constants = (d.next()<<8) + d.next() - 1;
        System.out.println("Constants count: " + constants);
        for (int i = 0; i < constants; i++) {
            System.out.flush();
            final int b = d.next();
            System.out.println("  " + i + ". => " + hex(b));
            switch (b) {
                case 0x1:
                    final int textSize =  (d.next()<<8) + d.next();
                    System.out.print("     UTFTest-8[" + textSize + "] ");
                    final byte[] text = d.next(textSize);
                    System.out.println(new String(text, "utf-8")); //TODO: fix UTFTest parse
                    break;
                case 0x3:
                    System.out.println("     INTEGER: ");
                    d.next(4);
                    break;
                case 0x4:
                    System.out.println("     FLOAT: ");
                    d.next(4);
                    break;
                case 0x5:
                    System.out.println("     LONG: ");
                    d.next(8);
                    break;
                case 0x6:
                    System.out.println("     DOUBLE: ");
                    d.next(8);
                    break;
                case 0x7:
                    System.out.println("     CLASS_REF: ");
                    d.next(2);
                    break;
                case 0x8:
                    System.out.println("     STRING: ");
                    d.next(2);
                    break;
                case 0x9:
                    System.out.println("     FIELD_REF: ");
                    d.next(4);
                    break;
                case 0xa: //METHOD_REF
                    System.out.println("     METHOD_REF: ");
                    d.next(4);
                    break;
                case 0xb:
                    System.out.println("    INTERFACE_METHOD_REF: ");
                    d.next(4);
                    break;
                case 0xc:
                    System.out.println("    NAME_AND_TYPE: ");
                    d.next(4);
                    break;
                default:
                    throw new RuntimeException("Unknown attribute type: " + hex(b));
            }
        }

    }

    private static void line(@NotNull final String comment, @NotNull String... data) {
        for (String s : data) {
            System.out.print(s);
            System.out.print(", ");
        }
        System.out.println("// " + comment);
    }

    private static class ByteIterator {
        private final byte[] myData;
        private int myCount = 0;

        private ByteIterator(@NotNull final byte[] data) {
            myData = data;
        }

        public int next() {
            return myData[myCount++];
        }

        @NotNull
        public byte[] next(final int sz) {
            byte[] r = new byte[sz];
            for(int i = 0; i < sz; i++) r[i] = myData[myCount++];
            return r;
        }
    }

    @NotNull
    private static String hex(final int sz) {
        if (sz <= 0xf) {
            return "0x0" + Integer.toHexString(sz);
        } else {
            return "0x" + Integer.toHexString(sz);
        }
    }
}
