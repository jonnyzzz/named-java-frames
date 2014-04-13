package org.jonnyzzz;

import java.io.UnsupportedEncodingException;

/**
 * @author Eugene Petrenko (eugene.petrenko@gmail.com)
 */
public class UTF {
    public static void main(String[] args) throws UnsupportedEncodingException {
        int[] data = {             0x28, 0x29, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a,
                0x65, 0x63, 0x74, 0x3b,
        };

        byte[] bd = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bd[i] = (byte) data[i];
        }

        System.out.println(new String(bd, "utf-8"));
    }
}
