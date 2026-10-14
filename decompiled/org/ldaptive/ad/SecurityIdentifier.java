/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.StringTokenizer;

public final class SecurityIdentifier {
    private SecurityIdentifier() {
    }

    public static String toString(byte[] sid) {
        ByteBuffer sidBuffer = ByteBuffer.wrap(sid);
        StringBuilder sb = new StringBuilder("S");
        sb.append("-").append(sidBuffer.get() & 0xFF);
        int countSubAuth = sidBuffer.get() & 0xFF;
        sidBuffer.limit(8);
        sb.append("-").append(SecurityIdentifier.getLong(sidBuffer, true));
        for (int i = 0; i < countSubAuth; ++i) {
            sidBuffer.limit(sidBuffer.position() + 4);
            sb.append("-").append(SecurityIdentifier.getLong(sidBuffer, false));
        }
        return sb.toString();
    }

    public static byte[] toBytes(String sid) {
        StringTokenizer st = new StringTokenizer(sid, "-");
        st.nextToken();
        int revision = Integer.valueOf(st.nextToken());
        long authority = Long.valueOf(st.nextToken());
        ArrayList<String> subAuthorities = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            subAuthorities.add(st.nextToken());
        }
        int size = 8 + 4 * subAuthorities.size();
        ByteBuffer sidBuffer = ByteBuffer.allocate(size);
        sidBuffer.put((byte)(revision & 0xFF));
        sidBuffer.put((byte)(subAuthorities.size() & 0xFF));
        sidBuffer.limit(8);
        SecurityIdentifier.putLong(sidBuffer, authority, true);
        for (String subAuthority : subAuthorities) {
            sidBuffer.limit(sidBuffer.position() + 4);
            SecurityIdentifier.putLong(sidBuffer, Long.valueOf(subAuthority), false);
        }
        return sidBuffer.array();
    }

    private static long getLong(ByteBuffer buffer, boolean bigEndian) {
        long value = buffer.get() & 0xFF;
        if (bigEndian) {
            while (buffer.hasRemaining()) {
                value <<= 8;
                value |= (long)(buffer.get() & 0xFF);
            }
        } else {
            int offset = 8;
            while (buffer.hasRemaining()) {
                value |= (long)((buffer.get() & 0xFF) << offset);
                offset += 8;
            }
        }
        return value & 0xFFFFFFFFL;
    }

    private static void putLong(ByteBuffer buffer, long value, boolean bigEndian) {
        if (bigEndian) {
            int offset = 8 * (buffer.limit() - buffer.position() - 1);
            while (buffer.hasRemaining()) {
                buffer.put((byte)(value >> offset & 0xFFL));
                offset -= 8;
            }
        } else {
            int offset = 0;
            while (buffer.hasRemaining()) {
                buffer.put((byte)(value >> offset & 0xFFL));
                offset += 8;
            }
        }
    }
}

