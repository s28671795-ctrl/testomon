/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;

public final class SearchParamsDTO {
    @JSONField(name="exact")
    private final boolean exact;

    public SearchParamsDTO(boolean exact) {
        this.exact = exact;
    }

    public boolean isExact() {
        return this.exact;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SearchParamsDTO)) {
            return false;
        }
        SearchParamsDTO other = (SearchParamsDTO)o;
        return this.isExact() == other.isExact();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isExact() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "SearchParamsDTO(exact=" + this.isExact() + ")";
    }
}

