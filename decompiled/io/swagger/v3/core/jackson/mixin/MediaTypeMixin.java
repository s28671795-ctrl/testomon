/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

public abstract class MediaTypeMixin {
    @JsonAnyGetter
    public abstract Map<String, Object> getExtensions();

    @JsonAnySetter
    public abstract void addExtension(String var1, Object var2);

    @JsonIgnore
    public abstract boolean getExampleSetFlag();

    @JsonInclude(value=JsonInclude.Include.NON_NULL, content=JsonInclude.Include.ALWAYS)
    public abstract Object getExample();
}

