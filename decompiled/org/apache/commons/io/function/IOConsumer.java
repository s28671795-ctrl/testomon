/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.function;

import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
public interface IOConsumer<T> {
    public void accept(T var1) throws IOException;

    default public IOConsumer<T> andThen(IOConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return t2 -> {
            this.accept(t2);
            after.accept(t2);
        };
    }
}

