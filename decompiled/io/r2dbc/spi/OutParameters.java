/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.OutParametersMetadata;
import io.r2dbc.spi.Readable;

public interface OutParameters
extends Readable {
    public OutParametersMetadata getMetadata();
}

