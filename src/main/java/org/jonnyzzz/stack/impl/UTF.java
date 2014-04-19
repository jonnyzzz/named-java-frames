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

package org.jonnyzzz.stack.impl;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class UTF {
    @NotNull
    public static byte[] encode(@NotNull final String str) {
        final byte[] result = new byte[6 * 64 * 1024]; //class-file strings are limited with 64k
        int cnt = 0;
        for (char c : str.toCharArray()) {
            final int z = (int) c;
            if (c >= '\u0001' && c <= '\u007f') {
                result[cnt++] = (byte) c;
            } else if (c == '\u0000' || (c >= '\u0080' && c <= '\u07FF')) {
                result[cnt++] = (byte) (0xc0 | (0x1f & (z >> 6)));
                result[cnt++] = (byte) (0x80 | (0x3f & z));
            } else {
                result[cnt++] = (byte) ( 0xe0 | (0x0f & (z >>12)));
                result[cnt++] = (byte) ( 0x80 | (0x3f & (z>>6)));
                result[cnt++] = (byte) ( 0x80 | (0x3f & z));
            }
        }
        return Arrays.copyOf(result, cnt);
    }

}
