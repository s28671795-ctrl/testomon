/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.QuotedPrintableEncoder;

public class QuotedPrintable {
    public static byte[] encode(byte[] data) {
        return QuotedPrintable.encode(data, 0, data.length);
    }

    public static byte[] encode(byte[] data, int off, int length) {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            encoder.encode(data, off, length, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception encoding Q-P encoded string: " + e);
        }
        return bOut.toByteArray();
    }

    public static int encode(byte[] data, OutputStream out) throws IOException {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        return encoder.encode(data, 0, data.length, out);
    }

    public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        return encoder.encode(data, 0, data.length, out);
    }

    public static byte[] decode(byte[] data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        try {
            encoder.decode(data, 0, data.length, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception decoding Q-P encoded string: " + e);
        }
        return bOut.toByteArray();
    }

    public static byte[] decode(String data) {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            encoder.decode(data, bOut);
        }
        catch (IOException e) {
            throw new RuntimeException("exception decoding Q-P encoded string: " + e);
        }
        return bOut.toByteArray();
    }

    public static int decode(String data, OutputStream out) throws IOException {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        return encoder.decode(data, out);
    }

    public static int decode(byte[] data, OutputStream out) throws IOException {
        QuotedPrintableEncoder encoder = new QuotedPrintableEncoder();
        return encoder.decode(data, 0, data.length, out);
    }
}

