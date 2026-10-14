/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record AnalyticsIdListDTO(@JsonProperty(value="ids") @JSONField(name="ids") Set<Integer> ids) {
}

