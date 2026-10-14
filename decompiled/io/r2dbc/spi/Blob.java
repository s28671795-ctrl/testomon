/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.DefaultLob;
import java.nio.ByteBuffer;
import org.reactivestreams.Publisher;

public interface Blob {
    public static Blob from(Publisher<ByteBuffer> p) {
        Assert.requireNonNull(p, "Publisher must not be null");
        final DefaultLob<ByteBuffer> lob = new DefaultLob<ByteBuffer>(p);
        return new Blob(){

            @Override
            public Publisher<ByteBuffer> stream() {
                return lob.stream();
            }

            @Override
            public Publisher<Void> discard() {
                return lob.discard();
            }
        };
    }

    public Publisher<ByteBuffer> stream();

    public Publisher<Void> discard();
}

