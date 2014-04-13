package org.jonnyzzz;

import org.jonnyzzz.stack.impl.UTF;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class UTFTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        int[] data = {             0x28, 0x29, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a,
                0x65, 0x63, 0x74, 0x3b,
        };

        byte[] bd = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bd[i] = (byte) data[i];
        }

        System.out.println(decode(bd));
    }

    public static String decode(byte[] data) {
        int cnt = 0;

        final StringBuilder sb = new StringBuilder();
        while (cnt < data.length) {
            final int b = data[cnt++];

            if ( (b & 0xe0) == 0xe0 ) {
                int x = b;
                int y = data[cnt++];
                int z = data[cnt++];
                char c = (char)(((x & 0xf) << 12) + ((y & 0x3f) << 6) + (z & 0x3f));
                sb.append(c);
                continue;
            }

            if ((b & 0xc0) == 0xc0) {
                int x = b;
                int y = data[cnt++];
                char c = (char)(((x & 0x1f) << 6) + (y & 0x3f));
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
