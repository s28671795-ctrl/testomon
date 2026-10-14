/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Option;

public class NoSuchOptionException
extends IllegalStateException {
    private final Option<?> option;

    public NoSuchOptionException(String reason, Option<?> option) {
        super(reason);
        this.option = option;
    }

    public Option<?> getOption() {
        return this.option;
    }
}

