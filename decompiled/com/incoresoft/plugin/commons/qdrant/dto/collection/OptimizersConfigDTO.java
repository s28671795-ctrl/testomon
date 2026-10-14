/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;

public final class OptimizersConfigDTO {
    @JSONField(name="memmap_threshold")
    private final int memmapThreshold;

    public OptimizersConfigDTO() {
        this(100000);
    }

    public int getMemmapThreshold() {
        return this.memmapThreshold;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OptimizersConfigDTO)) {
            return false;
        }
        OptimizersConfigDTO other = (OptimizersConfigDTO)o;
        return this.getMemmapThreshold() == other.getMemmapThreshold();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getMemmapThreshold();
        return result;
    }

    public String toString() {
        return "OptimizersConfigDTO(memmapThreshold=" + this.getMemmapThreshold() + ")";
    }

    public OptimizersConfigDTO(int memmapThreshold) {
        this.memmapThreshold = memmapThreshold;
    }
}

