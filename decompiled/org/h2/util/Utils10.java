/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;

public final class Utils10 {
    public static String byteArrayOutputStreamToString(ByteArrayOutputStream byteArrayOutputStream, Charset charset) {
        try {
            return byteArrayOutputStream.toString(charset.name());
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static boolean getTcpQuickack(Socket socket) throws IOException {
        throw new UnsupportedOperationException();
    }

    public static boolean setTcpQuickack(Socket socket, boolean bl) {
        return false;
    }

    private Utils10() {
    }
}

