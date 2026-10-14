/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.DefaultLob;
import org.reactivestreams.Publisher;

public interface Clob {
    public static Clob from(Publisher<? extends CharSequence> p) {
        Assert.requireNonNull(p, "Publisher must not be null");
        final DefaultLob<? extends CharSequence> lob = new DefaultLob<CharSequence>(p);
        return new Clob(){

            @Override
            public Publisher<CharSequence> stream() {
                return lob.stream();
            }

            @Override
            public Publisher<Void> discard() {
                return lob.discard();
            }
        };
    }

    public Publisher<CharSequence> stream();

    public Publisher<Void> discard();
}

