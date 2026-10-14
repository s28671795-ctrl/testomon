/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.CallbackMetricInstrument;
import io.grpc.Internal;
import io.grpc.PartialMetricInstrument;
import java.util.List;

@Internal
public final class LongGaugeMetricInstrument
extends PartialMetricInstrument
implements CallbackMetricInstrument {
    public LongGaugeMetricInstrument(int index, String name, String description, String unit, List<String> requiredLabelKeys, List<String> optionalLabelKeys, boolean enableByDefault) {
        super(index, name, description, unit, requiredLabelKeys, optionalLabelKeys, enableByDefault);
    }
}

