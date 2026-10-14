/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public final class DeletePointsDTO {
    @JSONField(name="points")
    private final List<Long> points;

    public DeletePointsDTO(List<Long> points) {
        this.points = points;
    }

    public List<Long> getPoints() {
        return this.points;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DeletePointsDTO)) {
            return false;
        }
        DeletePointsDTO other = (DeletePointsDTO)o;
        List<Long> this$points = this.getPoints();
        List<Long> other$points = other.getPoints();
        return !(this$points == null ? other$points != null : !((Object)this$points).equals(other$points));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<Long> $points = this.getPoints();
        result = result * 59 + ($points == null ? 43 : ((Object)$points).hashCode());
        return result;
    }

    public String toString() {
        return "DeletePointsDTO(points=" + String.valueOf(this.getPoints()) + ")";
    }
}

