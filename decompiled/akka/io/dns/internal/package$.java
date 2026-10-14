/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import java.nio.ByteOrder;

public final class package$ {
    public static final package$ MODULE$ = new package$();
    @InternalApi
    private static final ByteOrder networkByteOrder = ByteOrder.BIG_ENDIAN;

    public ByteOrder networkByteOrder() {
        return networkByteOrder;
    }

    private package$() {
    }
}

