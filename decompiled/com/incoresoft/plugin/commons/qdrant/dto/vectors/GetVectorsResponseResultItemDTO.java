/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public final class GetVectorsResponseResultItemDTO {
    @JSONField(name="id")
    private final long id;
    @JSONField(name="vector")
    private final List<Float> vector;

    public GetVectorsResponseResultItemDTO(long id, List<Float> vector) {
        this.id = id;
        this.vector = vector;
    }

    public long getId() {
        return this.id;
    }

    public List<Float> getVector() {
        return this.vector;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GetVectorsResponseResultItemDTO)) {
            return false;
        }
        GetVectorsResponseResultItemDTO other = (GetVectorsResponseResultItemDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        List<Float> this$vector = this.getVector();
        List<Float> other$vector = other.getVector();
        return !(this$vector == null ? other$vector != null : !((Object)this$vector).equals(other$vector));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.getId();
        result = result * 59 + (int)($id >>> 32 ^ $id);
        List<Float> $vector = this.getVector();
        result = result * 59 + ($vector == null ? 43 : ((Object)$vector).hashCode());
        return result;
    }

    public String toString() {
        return "GetVectorsResponseResultItemDTO(id=" + this.getId() + ", vector=" + String.valueOf(this.getVector()) + ")";
    }
}

