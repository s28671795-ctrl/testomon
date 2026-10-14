/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;

final class ChunkedUtil {
    private ChunkedUtil() {
    }

    public static InputStream getPartialResponse(HttpURLConnection connection, int responseCode) throws IOException {
        InputStream in = null;
        if (responseCode == 202 || responseCode == 200) {
            if (connection.getContentLength() > 0) {
                in = connection.getInputStream();
            } else if (ChunkedUtil.hasChunkedResponse(connection) || ChunkedUtil.hasEofTerminatedResponse(connection)) {
                in = ChunkedUtil.getNonEmptyContent(connection);
            }
        }
        return in;
    }

    private static boolean hasChunkedResponse(HttpURLConnection connection) {
        return "chunked".equalsIgnoreCase(connection.getHeaderField("Transfer-Encoding"));
    }

    private static boolean hasEofTerminatedResponse(HttpURLConnection connection) {
        return "close".equalsIgnoreCase(connection.getHeaderField("Connection"));
    }

    private static InputStream getNonEmptyContent(HttpURLConnection connection) {
        PushbackInputStream in = null;
        try {
            PushbackInputStream pin = new PushbackInputStream(connection.getInputStream());
            int c = pin.read();
            if (c != -1) {
                pin.unread((byte)c);
                in = pin;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return in;
    }
}

