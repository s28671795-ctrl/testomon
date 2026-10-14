/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.eventmanager;

import com.alibaba.fastjson.annotation.JSONField;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EventCopyDTO(@JSONField(name="title") @NotBlank String title, @JSONField(name="description") @NotNull String description) {
}

