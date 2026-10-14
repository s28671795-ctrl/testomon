/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.filter;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FilterDTO;
import java.util.Collection;

public final class FiltersDTO {
    @JSONField(name="must")
    private final Collection<FilterDTO> filters;

    public FiltersDTO(Collection<FilterDTO> filters) {
        this.filters = filters;
    }

    public Collection<FilterDTO> getFilters() {
        return this.filters;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FiltersDTO)) {
            return false;
        }
        FiltersDTO other = (FiltersDTO)o;
        Collection<FilterDTO> this$filters = this.getFilters();
        Collection<FilterDTO> other$filters = other.getFilters();
        return !(this$filters == null ? other$filters != null : !((Object)this$filters).equals(other$filters));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Collection<FilterDTO> $filters = this.getFilters();
        result = result * 59 + ($filters == null ? 43 : ((Object)$filters).hashCode());
        return result;
    }

    public String toString() {
        return "FiltersDTO(filters=" + String.valueOf(this.getFilters()) + ")";
    }
}

