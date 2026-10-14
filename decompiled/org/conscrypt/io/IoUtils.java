/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.io;

import java.io.Closeable;
import java.io.InterruptedIOException;
import java.net.Socket;

public final class IoUtils {
    private IoUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (RuntimeException rethrown) {
                throw rethrown;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void throwInterruptedIoException() throws InterruptedIOException {
        Thread.currentThread().interrupt();
        throw new InterruptedIOException();
    }
}

