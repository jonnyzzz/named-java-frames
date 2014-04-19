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

package org.jonnyzzz.stack.impl.gen.jcl;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class JavaClassGenerator {
    public static final Class<?> TEMPLATE = JavaGeneratorTemplate.class;
    public static final byte[] CLASS_NAME_BYTES = UTF.encode(TEMPLATE.getName().replace('.', '/'));

    @NotNull
    private static byte[] loadClazzTemplate() {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final InputStream stream = TEMPLATE.getResourceAsStream(TEMPLATE.getSimpleName() + ".class");
        if (stream == null) {
            throw new RuntimeException("Failed to find class " + TEMPLATE);
        }

        try {
            int sz;
            while ((sz = stream.read()) >= 0) {
                bos.write((byte) sz);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template class");
        }
        return bos.toByteArray();
    }

    @NotNull
    public static byte[] generateWrapper(@NotNull final String className, @NotNull final String methodName) {
        final ByteIterator tmpl = new ByteIterator(loadClazzTemplate());
        final ByteWriter result = new ByteWriter(tmpl.size() + 6 * className.length() + 6 * methodName.length());

        result.addBytes(tmpl.next(4)); //0xCAFEBABE
        result.addBytes(tmpl.next(4)); //version

        final int constantsTableSize_high = tmpl.next();
        final int constantsTableSize_low = tmpl.next();
        final int constantsTableSize = (constantsTableSize_high << 8) + constantsTableSize_low;
        result.addBytes(constantsTableSize_high, constantsTableSize_low);

        for (int i = 0; i < constantsTableSize - 1; i++) {
            final int b = tmpl.next();
            result.addBytes(b);

            switch (b) {
                case 0x1:
                    byte[] text = tmpl.next((tmpl.next() << 8) + tmpl.next());

                    if (Arrays.equals(text, CLASS_NAME_BYTES)) {
                        text = UTF.encode(className.replace('.', '/'));
                    }

                    result.addBytes(text.length >> 8, text.length & 0xff);
                    result.addBytes(text);
                    break;
                case 0x3: //integer
                case 0x4: //float
                    result.addBytes(tmpl.next(4));
                    break;
                case 0x5: //long
                case 0x6: //double
                    result.addBytes(tmpl.next(8));
                    break;
                case 0x7: //class_ref
                case 0x8: //method_ref
                    result.addBytes(tmpl.next(2));
                    break;
                case 0x9: //FIELD_REF
                case 0xa: //METHOD_REF
                case 0xb: //INTERFACE_METHOD_REF
                case 0xc: //NAME_AND_TYPE
                    result.addBytes(tmpl.next(4));
                    break;
                default:
                    throw new RuntimeException("Unknown attribute type: " + Integer.toHexString(b));
            }
        }

        while (tmpl.hasNext()) result.addBytes(tmpl.next());

        return result.toByteArray();
    }

    private static class ByteIterator {
        private final byte[] myData;
        private int myCount = 0;

        public ByteIterator(@NotNull final byte[] data) {
            myData = data;
        }

        public int next() {
            return myData[myCount++];
        }

        @NotNull
        public byte[] next(final int sz) {
            final int from = myCount;
            final int to = from + sz;
            myCount += sz;
            return Arrays.copyOfRange(myData, from, to);
        }

        public boolean hasNext() {
            return myCount < myData.length;
        }

        public int size() {
            return myData.length;
        }
    }

    private static class ByteWriter {
        private final byte[] myBuff;
        private int myCount = 0;

        public ByteWriter(final int maxSize) {
            myBuff = new byte[maxSize];
        }

        public void addBytes(@NotNull byte[] data) {
            for (byte b : data) {
                myBuff[myCount++] = b;
            }
        }

        public void addBytes(final int a) {
            myBuff[myCount++] = (byte) a;
        }

        public void addBytes(final int a, final int b) {
            myBuff[myCount++] = (byte) a;
            myBuff[myCount++] = (byte) b;
        }

        @NotNull
        public byte[] toByteArray() {
            return Arrays.copyOf(myBuff, myCount);
        }
    }
}
