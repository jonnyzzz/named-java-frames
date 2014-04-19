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
