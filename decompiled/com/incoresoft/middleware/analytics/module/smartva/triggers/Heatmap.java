/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.triggers;

import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;

public class Heatmap
implements Trigger {
    @Override
    public TriggerType getType() {
        return TriggerType.HEATMAP;
    }
}

