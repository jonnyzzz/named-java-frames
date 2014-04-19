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
import org.jonnyzzz.stack.impl.UTF;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class UTFTest {

    @NotNull
    public static String decode(@NotNull byte[] data) {
        int cnt = 0;

        final StringBuilder sb = new StringBuilder();
        while (cnt < data.length) {
            final int b = data[cnt++];

            if ( (b & 0xe0) == 0xe0 ) {
                final int x = b;
                final int y = data[cnt++];
                final int z = data[cnt++];
                final char c = (char)(((x & 0xf) << 12) + ((y & 0x3f) << 6) + (z & 0x3f));
                sb.append(c);
                continue;
            }

            if ((b & 0xc0) == 0xc0) {
                final int x = b;
                final int y = data[cnt++];
                final char c = (char)(((x & 0x1f) << 6) + (y & 0x3f));
                sb.append(c);
                continue;
            }

            if ((b & 0x80) == 0) {
                sb.append((char)b);
                continue;
            }

            throw new RuntimeException("Invalid code: " + Integer.toHexString(b));
        }

        return sb.toString();
    }


    @Test
    public void test_UTF() {
        for(char c = '\u0000'; c <= '\u1000'; c++) {
            final byte[] encode = UTF.encode("" + c);
            final String decoded = decode(encode);

            Assert.assertEquals("Size for Code: " + Integer.toHexString((int)c), 1, decoded.length());
            Assert.assertEquals("Code: " + Integer.toHexString((int)c), (int)c, (int)decoded.charAt(0));
        }
    }
}
