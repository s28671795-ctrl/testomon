/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Readable;
import io.r2dbc.spi.RowMetadata;

public interface Row
extends Readable {
    public RowMetadata getMetadata();
}

