/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;

public final class HNSWConfigDTO {
    @JSONField(name="on_disk")
    private final boolean onDisk;

    public HNSWConfigDTO(boolean onDisk) {
        this.onDisk = onDisk;
    }

    public boolean isOnDisk() {
        return this.onDisk;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HNSWConfigDTO)) {
            return false;
        }
        HNSWConfigDTO other = (HNSWConfigDTO)o;
        return this.isOnDisk() == other.isOnDisk();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isOnDisk() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "HNSWConfigDTO(onDisk=" + this.isOnDisk() + ")";
    }
}

