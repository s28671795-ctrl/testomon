/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import javax.validation.constraints.Min;

public class PaginationParams {
    @JSONField(name="offset", defaultValue="0")
    @JsonProperty(value="offset")
    @Min(value=0L)
    private @Min(value=0L) int offset;
    @JSONField(name="limit")
    @JsonProperty(value="limit")
    @Min(value=0L)
    private @Min(value=0L) Integer limit;

    public void setOffset(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset must be greater than 0");
        }
        this.offset = offset;
    }

    public void setLimit(Integer limit) {
        if (limit != null && limit < 0) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }
        this.limit = limit;
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(this.limit);
    }

    public int getOffset() {
        return this.offset;
    }

    public String toString() {
        return "PaginationParams(offset=" + this.getOffset() + ", limit=" + String.valueOf(this.getLimit()) + ")";
    }
}

