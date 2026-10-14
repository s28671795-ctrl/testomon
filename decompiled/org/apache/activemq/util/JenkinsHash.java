/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class JenkinsHash {
    private static final long INT_MASK = 0xFFFFFFFFL;
    private static final long BYTE_MASK = 255L;
    private static final JenkinsHash _instance = new JenkinsHash();

    public static JenkinsHash getInstance() {
        return _instance;
    }

    private static long rot(long val, int pos) {
        return (long)Integer.rotateLeft((int)(val & 0xFFFFFFFFL), pos) & 0xFFFFFFFFL;
    }

    public int hash(byte[] bytes) {
        return this.hash(bytes, bytes.length, -1);
    }

    public int hash(byte[] bytes, int initVal) {
        return this.hash(bytes, bytes.length, initVal);
    }

    public int hash(byte[] key, int nbytes, int initval) {
        int length;
        long c;
        long b = c = 3735928559L + (long)length + (long)initval & 0xFFFFFFFFL;
        long a = c;
        int offset = 0;
        for (length = nbytes; length > 12; length -= 12) {
            a = a + ((long)key[offset + 0] & 0xFFL) & 0xFFFFFFFFL;
            a = a + (((long)key[offset + 1] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            a = a + (((long)key[offset + 2] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            a = a + (((long)key[offset + 3] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            b = b + ((long)key[offset + 4] & 0xFFL) & 0xFFFFFFFFL;
            b = b + (((long)key[offset + 5] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            b = b + (((long)key[offset + 6] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            b = b + (((long)key[offset + 7] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            c = c + ((long)key[offset + 8] & 0xFFL) & 0xFFFFFFFFL;
            c = c + (((long)key[offset + 9] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            c = c + (((long)key[offset + 10] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            c = c + (((long)key[offset + 11] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            a = a - c & 0xFFFFFFFFL;
            a ^= JenkinsHash.rot(c, 4);
            c = c + b & 0xFFFFFFFFL;
            b = b - a & 0xFFFFFFFFL;
            b ^= JenkinsHash.rot(a, 6);
            a = a + c & 0xFFFFFFFFL;
            c = c - b & 0xFFFFFFFFL;
            c ^= JenkinsHash.rot(b, 8);
            b = b + a & 0xFFFFFFFFL;
            a = a - c & 0xFFFFFFFFL;
            a ^= JenkinsHash.rot(c, 16);
            c = c + b & 0xFFFFFFFFL;
            b = b - a & 0xFFFFFFFFL;
            b ^= JenkinsHash.rot(a, 19);
            a = a + c & 0xFFFFFFFFL;
            c = c - b & 0xFFFFFFFFL;
            c ^= JenkinsHash.rot(b, 4);
            b = b + a & 0xFFFFFFFFL;
            offset += 12;
        }
        switch (length) {
            case 12: {
                c = c + (((long)key[offset + 11] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 11: {
                c = c + (((long)key[offset + 10] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 10: {
                c = c + (((long)key[offset + 9] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 9: {
                c = c + ((long)key[offset + 8] & 0xFFL) & 0xFFFFFFFFL;
            }
            case 8: {
                b = b + (((long)key[offset + 7] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 7: {
                b = b + (((long)key[offset + 6] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 6: {
                b = b + (((long)key[offset + 5] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 5: {
                b = b + ((long)key[offset + 4] & 0xFFL) & 0xFFFFFFFFL;
            }
            case 4: {
                a = a + (((long)key[offset + 3] & 0xFFL) << 24 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 3: {
                a = a + (((long)key[offset + 2] & 0xFFL) << 16 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 2: {
                a = a + (((long)key[offset + 1] & 0xFFL) << 8 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            case 1: {
                a = a + ((long)key[offset + 0] & 0xFFL) & 0xFFFFFFFFL;
                break;
            }
            case 0: {
                return (int)(c & 0xFFFFFFFFL);
            }
        }
        c ^= b;
        c = c - JenkinsHash.rot(b, 14) & 0xFFFFFFFFL;
        a ^= c;
        a = a - JenkinsHash.rot(c, 11) & 0xFFFFFFFFL;
        b ^= a;
        b = b - JenkinsHash.rot(a, 25) & 0xFFFFFFFFL;
        c ^= b;
        c = c - JenkinsHash.rot(b, 16) & 0xFFFFFFFFL;
        a ^= c;
        a = a - JenkinsHash.rot(c, 4) & 0xFFFFFFFFL;
        b ^= a;
        b = b - JenkinsHash.rot(a, 14) & 0xFFFFFFFFL;
        c ^= b;
        c = c - JenkinsHash.rot(b, 24) & 0xFFFFFFFFL;
        return (int)(c & 0xFFFFFFFFL);
    }
}

