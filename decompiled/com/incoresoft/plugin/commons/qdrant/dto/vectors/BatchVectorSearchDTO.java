/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.VectorSearchDTO;
import java.util.List;

public final class BatchVectorSearchDTO {
    @JSONField(name="searches")
    private final List<VectorSearchDTO> searches;

    public BatchVectorSearchDTO(List<VectorSearchDTO> searches) {
        this.searches = searches;
    }

    public List<VectorSearchDTO> getSearches() {
        return this.searches;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BatchVectorSearchDTO)) {
            return false;
        }
        BatchVectorSearchDTO other = (BatchVectorSearchDTO)o;
        List<VectorSearchDTO> this$searches = this.getSearches();
        List<VectorSearchDTO> other$searches = other.getSearches();
        return !(this$searches == null ? other$searches != null : !((Object)this$searches).equals(other$searches));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        List<VectorSearchDTO> $searches = this.getSearches();
        result = result * 59 + ($searches == null ? 43 : ((Object)$searches).hashCode());
        return result;
    }

    public String toString() {
        return "BatchVectorSearchDTO(searches=" + String.valueOf(this.getSearches()) + ")";
    }
}

