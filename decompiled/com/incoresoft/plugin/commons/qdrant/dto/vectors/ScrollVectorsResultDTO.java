/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsResponseResultItemDTO;
import java.util.List;

public final class ScrollVectorsResultDTO {
    @JSONField(name="points")
    private final List<GetVectorsResponseResultItemDTO> points;
    @JSONField(name="next_page_offset")
    private final Integer nextPageOffset;

    public ScrollVectorsResultDTO(List<GetVectorsResponseResultItemDTO> points, Integer nextPageOffset) {
        this.points = points;
        this.nextPageOffset = nextPageOffset;
    }

    public List<GetVectorsResponseResultItemDTO> getPoints() {
        return this.points;
    }

    public Integer getNextPageOffset() {
        return this.nextPageOffset;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScrollVectorsResultDTO)) {
            return false;
        }
        ScrollVectorsResultDTO other = (ScrollVectorsResultDTO)o;
        Integer this$nextPageOffset = this.getNextPageOffset();
        Integer other$nextPageOffset = other.getNextPageOffset();
        if (this$nextPageOffset == null ? other$nextPageOffset != null : !((Object)this$nextPageOffset).equals(other$nextPageOffset)) {
            return false;
        }
        List<GetVectorsResponseResultItemDTO> this$points = this.getPoints();
        List<GetVectorsResponseResultItemDTO> other$points = other.getPoints();
        return !(this$points == null ? other$points != null : !((Object)this$points).equals(other$points));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $nextPageOffset = this.getNextPageOffset();
        result = result * 59 + ($nextPageOffset == null ? 43 : ((Object)$nextPageOffset).hashCode());
        List<GetVectorsResponseResultItemDTO> $points = this.getPoints();
        result = result * 59 + ($points == null ? 43 : ((Object)$points).hashCode());
        return result;
    }

    public String toString() {
        return "ScrollVectorsResultDTO(points=" + String.valueOf(this.getPoints()) + ", nextPageOffset=" + this.getNextPageOffset() + ")";
    }
}

