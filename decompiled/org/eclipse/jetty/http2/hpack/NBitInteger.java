/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

import java.nio.ByteBuffer;

public class NBitInteger {
    public static int octectsNeeded(int n, int i) {
        if (n == 8) {
            int nbits = 255;
            if ((i -= nbits) < 0) {
                return 1;
            }
            if (i == 0) {
                return 2;
            }
            int lz = Integer.numberOfLeadingZeros(i);
            int log = 32 - lz;
            return 1 + (log + 6) / 7;
        }
        int nbits = 255 >>> 8 - n;
        if ((i -= nbits) < 0) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        int lz = Integer.numberOfLeadingZeros(i);
        int log = 32 - lz;
        return (log + 6) / 7;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void encode(ByteBuffer buf, int n, int i) {
        int bits;
        int p;
        if (n != 8) {
            p = buf.position() - 1;
            bits = 255 >>> 8 - n;
            if (i < bits) {
                buf.put(p, (byte)(buf.get(p) & ~bits | i));
                return;
            }
        } else {
            if (i < 255) {
                buf.put((byte)i);
                return;
            }
            buf.put((byte)-1);
            int length = i - 255;
            while (true) {
                if ((length & 0xFFFFFF80) == 0) {
                    buf.put((byte)length);
                    return;
                }
                buf.put((byte)(length & 0x7F | 0x80));
                length >>>= 7;
            }
        }
        buf.put(p, (byte)(buf.get(p) | bits));
        int length = i - bits;
        while (true) {
            if ((length & 0xFFFFFF80) == 0) {
                buf.put((byte)length);
                return;
            }
            buf.put((byte)(length & 0x7F | 0x80));
            length >>>= 7;
        }
    }

    public static int decode(ByteBuffer buffer, int n) {
        if (n == 8) {
            int nbits = 255;
            int i = buffer.get() & 0xFF;
            if (i == nbits) {
                int b;
                int m4 = 1;
                do {
                    b = 0xFF & buffer.get();
                    i += (b & 0x7F) * m4;
                    m4 *= 128;
                } while ((b & 0x80) == 128);
            }
            return i;
        }
        int nbits = 255 >>> 8 - n;
        int i = buffer.get(buffer.position() - 1) & nbits;
        if (i == nbits) {
            int b;
            int m5 = 1;
            do {
                b = 0xFF & buffer.get();
                i += (b & 0x7F) * m5;
                m5 *= 128;
            } while ((b & 0x80) == 128);
        }
        return i;
    }
}

