/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

public abstract class LicenseMixin {
    @JsonAnyGetter
    public abstract Map<String, Object> getExtensions();

    @JsonAnySetter
    public abstract void addExtension(String var1, Object var2);

    @JsonIgnore
    public abstract String getIdentifier();
}

