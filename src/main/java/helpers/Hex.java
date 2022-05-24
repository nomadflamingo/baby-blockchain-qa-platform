package helpers;

import java.nio.charset.StandardCharsets;

public class Hex {
    private static final byte[] HEX_CHARS = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    // No need to instantiate this class
    private Hex() {}

    /**
     * Converts byte array to hex string of characters from start (inclusive) to end (exclusive)
     * @param bytes The array of bytes to convert from
     * @param start Starting position in the array (inclusive)
     * @param end End position in the array (exclusive)
     * @return String of hex characters
     */
    public static String hexFromBytes(byte[] bytes, int start, int end) {
        byte[] resultArr = new byte[(end - start) * 2];
        for (int i = start; i < end; ++i) {
            int v = bytes[i] & 0xFF;
            resultArr[i*2] = HEX_CHARS[v >>> 4];  // Get first 4 bits from byte
            resultArr[i*2+1] = HEX_CHARS[v & 0x0F];  // Get last 4 bits from byte
        }
        return new String(resultArr, StandardCharsets.UTF_8);
    }
}

