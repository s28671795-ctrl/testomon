/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.LimitedInputStream;

class StreamUtil {
    private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();

    StreamUtil() {
    }

    static int findLimit(InputStream inputStream2) {
        if (inputStream2 instanceof LimitedInputStream) {
            return ((LimitedInputStream)inputStream2).getLimit();
        }
        if (inputStream2 instanceof ASN1InputStream) {
            return ((ASN1InputStream)inputStream2).getLimit();
        }
        if (inputStream2 instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream)inputStream2).available();
        }
        if (inputStream2 instanceof FileInputStream) {
            try {
                long l;
                FileChannel fileChannel = ((FileInputStream)inputStream2).getChannel();
                long l2 = l = fileChannel != null ? fileChannel.size() : Integer.MAX_VALUE;
                if (l < Integer.MAX_VALUE) {
                    return (int)l;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (MAX_MEMORY > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)MAX_MEMORY;
    }
}

