/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public final class GetVectorsRequestDTO {
    @JSONField(name="ids")
    private final List<Long> ids;
    @JSONField(name="with_vector")
    private final boolean withVector;

    public GetVectorsRequestDTO(List<Long> ids, boolean withVector) {
        this.ids = ids;
        this.withVector = withVector;
    }

    public List<Long> getIds() {
        return this.ids;
    }

    public boolean isWithVector() {
        return this.withVector;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GetVectorsRequestDTO)) {
            return false;
        }
        GetVectorsRequestDTO other = (GetVectorsRequestDTO)o;
        if (this.isWithVector() != other.isWithVector()) {
            return false;
        }
        List<Long> this$ids = this.getIds();
        List<Long> other$ids = other.getIds();
        return !(this$ids == null ? other$ids != null : !((Object)this$ids).equals(other$ids));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isWithVector() ? 79 : 97);
        List<Long> $ids = this.getIds();
        result = result * 59 + ($ids == null ? 43 : ((Object)$ids).hashCode());
        return result;
    }

    public String toString() {
        return "GetVectorsRequestDTO(ids=" + String.valueOf(this.getIds()) + ", withVector=" + this.isWithVector() + ")";
    }
}

