/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;

public final class CollectionCreationVectorsDTO {
    @JSONField(name="distance")
    private final String distance;
    @JSONField(name="size")
    private final int size;

    public CollectionCreationVectorsDTO() {
        this("Dot", 512);
    }

    public String getDistance() {
        return this.distance;
    }

    public int getSize() {
        return this.size;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CollectionCreationVectorsDTO)) {
            return false;
        }
        CollectionCreationVectorsDTO other = (CollectionCreationVectorsDTO)o;
        if (this.getSize() != other.getSize()) {
            return false;
        }
        String this$distance = this.getDistance();
        String other$distance = other.getDistance();
        return !(this$distance == null ? other$distance != null : !this$distance.equals(other$distance));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getSize();
        String $distance = this.getDistance();
        result = result * 59 + ($distance == null ? 43 : $distance.hashCode());
        return result;
    }

    public String toString() {
        return "CollectionCreationVectorsDTO(distance=" + this.getDistance() + ", size=" + this.getSize() + ")";
    }

    public CollectionCreationVectorsDTO(String distance, int size) {
        this.distance = distance;
        this.size = size;
    }
}

