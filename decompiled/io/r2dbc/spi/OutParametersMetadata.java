/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.OutParameterMetadata;
import java.util.List;

public interface OutParametersMetadata {
    public OutParameterMetadata getParameterMetadata(int var1);

    public OutParameterMetadata getParameterMetadata(String var1);

    public List<? extends OutParameterMetadata> getParameterMetadatas();
}

