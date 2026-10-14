/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.VectorDTO;
import java.util.List;

public final class InsertVectorDTO {
    @JSONField(name="points")
    private final List<VectorDTO> points;

    public InsertVectorDTO(long id, List<Float> vector, Object payload) {
        this(List.of(new VectorDTO(id, vector, payload)));
    }

    public List<VectorDTO> getPoints() {
        return this.points;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InsertVectorDTO)) {
            return false;
        }
        InsertVectorDTO other = (InsertVectorDTO)o;
        List<VectorDTO> this$points = this.getPoints();
        List<VectorDTO> other$points = other.getPoints();
        return !(this$points == null ? other$points != null : !((Object)this$points).equals(other$points));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<VectorDTO> $points = this.getPoints();
        result = result * 59 + ($points == null ? 43 : ((Object)$points).hashCode());
        return result;
    }

    public String toString() {
        return "InsertVectorDTO(points=" + String.valueOf(this.getPoints()) + ")";
    }

    public InsertVectorDTO(List<VectorDTO> points) {
        this.points = points;
    }
}

