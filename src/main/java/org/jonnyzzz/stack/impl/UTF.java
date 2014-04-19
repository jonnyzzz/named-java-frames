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
