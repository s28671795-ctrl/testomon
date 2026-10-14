/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.Base64Encoder;
import org.apache.geronimo.mail.util.Encoder;

public class Base64 {
    private static final Encoder encoder = new Base64Encoder();

    public static byte[] encode(byte[] data) {
        return Base64.encode(data, 0, data.length);
    }

    public static byte[] encode(byte[] data, int offset, int length) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            encoder.encode(data, 0, data.length, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception encoding base64 string: " + e);
        }
        return bOut.toByteArray();
    }

    public static int encode(byte[] data, OutputStream out) throws IOException {
        return encoder.encode(data, 0, data.length, out);
    }

    public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        return encoder.encode(data, off, length, out);
    }

    public static byte[] decode(byte[] data) {
        return Base64.decode(data, 0, data.length);
    }

    public static byte[] decode(byte[] data, int offset, int length) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            encoder.decode(data, offset, length, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }
        return bOut.toByteArray();
    }

    public static byte[] decode(String data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            encoder.decode(data, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception decoding base64 string: " + e);
        }
        return bOut.toByteArray();
    }

    public static int decode(String data, OutputStream out) throws IOException {
        return encoder.decode(data, out);
    }

    public static int decode(byte[] data, OutputStream out) throws IOException {
        return encoder.decode(data, 0, data.length, out);
    }
}

