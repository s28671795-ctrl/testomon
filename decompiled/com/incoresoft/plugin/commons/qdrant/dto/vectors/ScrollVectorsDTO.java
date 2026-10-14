/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FiltersDTO;

public final class ScrollVectorsDTO {
    @JSONField(name="filter")
    private final FiltersDTO filter;
    @JSONField(name="limit")
    private final Integer limit;
    @JSONField(name="offset")
    private final Integer offset;
    @JSONField(name="with_payload")
    private final boolean withPayload = false;
    @JSONField(name="with_vector")
    private final boolean withVector;

    public ScrollVectorsDTO(FiltersDTO filter2, Integer limit, Integer offset, boolean withVector) {
        this.filter = filter2;
        this.limit = limit;
        this.offset = offset;
        this.withVector = withVector;
    }

    public FiltersDTO getFilter() {
        return this.filter;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public boolean isWithPayload() {
        return this.withPayload;
    }

    public boolean isWithVector() {
        return this.withVector;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScrollVectorsDTO)) {
            return false;
        }
        ScrollVectorsDTO other = (ScrollVectorsDTO)o;
        if (this.isWithPayload() != other.isWithPayload()) {
            return false;
        }
        if (this.isWithVector() != other.isWithVector()) {
            return false;
        }
        Integer this$limit = this.getLimit();
        Integer other$limit = other.getLimit();
        if (this$limit == null ? other$limit != null : !((Object)this$limit).equals(other$limit)) {
            return false;
        }
        Integer this$offset = this.getOffset();
        Integer other$offset = other.getOffset();
        if (this$offset == null ? other$offset != null : !((Object)this$offset).equals(other$offset)) {
            return false;
        }
        FiltersDTO this$filter = this.getFilter();
        FiltersDTO other$filter = other.getFilter();
        return !(this$filter == null ? other$filter != null : !((Object)this$filter).equals(other$filter));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isWithPayload() ? 79 : 97);
        result = result * 59 + (this.isWithVector() ? 79 : 97);
        Integer $limit = this.getLimit();
        result = result * 59 + ($limit == null ? 43 : ((Object)$limit).hashCode());
        Integer $offset = this.getOffset();
        result = result * 59 + ($offset == null ? 43 : ((Object)$offset).hashCode());
        FiltersDTO $filter = this.getFilter();
        result = result * 59 + ($filter == null ? 43 : ((Object)$filter).hashCode());
        return result;
    }

    public String toString() {
        return "ScrollVectorsDTO(filter=" + String.valueOf(this.getFilter()) + ", limit=" + this.getLimit() + ", offset=" + this.getOffset() + ", withPayload=" + this.isWithPayload() + ", withVector=" + this.isWithVector() + ")";
    }
}

