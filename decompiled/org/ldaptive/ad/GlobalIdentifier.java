/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad;

import java.nio.ByteBuffer;
import java.util.StringTokenizer;
import org.ldaptive.LdapUtils;

public final class GlobalIdentifier {
    private GlobalIdentifier() {
    }

    public static String toString(byte[] guid) {
        ByteBuffer guidBuffer = ByteBuffer.wrap(guid);
        StringBuilder sb = new StringBuilder("{");
        guidBuffer.limit(4);
        sb.append(LdapUtils.hexEncode(GlobalIdentifier.getBytes(guidBuffer, true)));
        guidBuffer.limit(6);
        sb.append("-").append(LdapUtils.hexEncode(GlobalIdentifier.getBytes(guidBuffer, true)));
        guidBuffer.limit(8);
        sb.append("-").append(LdapUtils.hexEncode(GlobalIdentifier.getBytes(guidBuffer, true)));
        guidBuffer.limit(10);
        sb.append("-").append(LdapUtils.hexEncode(GlobalIdentifier.getBytes(guidBuffer, false)));
        guidBuffer.limit(guidBuffer.capacity());
        sb.append("-").append(LdapUtils.hexEncode(GlobalIdentifier.getBytes(guidBuffer, false)));
        sb.append("}");
        return sb.toString();
    }

    public static byte[] toBytes(String guid) {
        StringTokenizer st = new StringTokenizer(guid.substring(1, guid.length() - 1), "-");
        String data1 = st.nextToken();
        String data2 = st.nextToken();
        String data3 = st.nextToken();
        String data4 = st.nextToken();
        String data5 = st.nextToken();
        ByteBuffer guidBuffer = ByteBuffer.allocate(16);
        GlobalIdentifier.putBytes(guidBuffer, LdapUtils.hexDecode(data1.toCharArray()), true);
        GlobalIdentifier.putBytes(guidBuffer, LdapUtils.hexDecode(data2.toCharArray()), true);
        GlobalIdentifier.putBytes(guidBuffer, LdapUtils.hexDecode(data3.toCharArray()), true);
        GlobalIdentifier.putBytes(guidBuffer, LdapUtils.hexDecode(data4.toCharArray()), false);
        GlobalIdentifier.putBytes(guidBuffer, LdapUtils.hexDecode(data5.toCharArray()), false);
        return guidBuffer.array();
    }

    private static byte[] getBytes(ByteBuffer buffer, boolean bigEndian) {
        byte[] bytes = new byte[buffer.limit() - buffer.position()];
        if (bigEndian) {
            int offset = bytes.length - 1;
            while (buffer.hasRemaining()) {
                bytes[offset--] = (byte)(buffer.get() & 0xFF);
            }
        } else {
            int offset = 0;
            while (buffer.hasRemaining()) {
                bytes[offset++] = (byte)(buffer.get() & 0xFF);
            }
        }
        return bytes;
    }

    private static void putBytes(ByteBuffer buffer, byte[] bytes, boolean bigEndian) {
        if (bigEndian) {
            for (int i = bytes.length - 1; i >= 0; --i) {
                buffer.put((byte)(bytes[i] & 0xFF));
            }
        } else {
            for (byte b : bytes) {
                buffer.put((byte)(b & 0xFF));
            }
        }
    }
}

