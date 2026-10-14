/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.smartva;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ActionType", description="Action type enum", example="in_zone")
public enum ActionType {
    in_zone,
    out_zone;

}

