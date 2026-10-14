/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Internal;
import java.util.List;

@Internal
public interface MetricInstrument {
    public int getIndex();

    public String getName();

    public String getDescription();

    public String getUnit();

    public List<String> getRequiredLabelKeys();

    public List<String> getOptionalLabelKeys();

    public boolean isEnableByDefault();
}

