/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FiltersDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.SearchParamsDTO;
import java.util.List;

public final class VectorSearchDTO {
    @JSONField(name="limit")
    private final int limit;
    @JSONField(name="offset")
    private final int offset;
    @JSONField(name="vector")
    private final List<Float> vector;
    @JSONField(name="score_threshold")
    private final float scoreThreshold;
    @JSONField(name="filter")
    private final FiltersDTO filter;
    @JSONField(name="params")
    private final SearchParamsDTO params;
    @JSONField(name="with_payload")
    private final boolean withPayload = true;

    public VectorSearchDTO(int limit, int offset, List<Float> vector, float scoreThreshold, FiltersDTO filter2, SearchParamsDTO params) {
        this.limit = limit;
        this.offset = offset;
        this.vector = vector;
        this.scoreThreshold = scoreThreshold;
        this.filter = filter2;
        this.params = params;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public List<Float> getVector() {
        return this.vector;
    }

    public float getScoreThreshold() {
        return this.scoreThreshold;
    }

    public FiltersDTO getFilter() {
        return this.filter;
    }

    public SearchParamsDTO getParams() {
        return this.params;
    }

    public boolean isWithPayload() {
        return this.withPayload;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VectorSearchDTO)) {
            return false;
        }
        VectorSearchDTO other = (VectorSearchDTO)o;
        if (this.getLimit() != other.getLimit()) {
            return false;
        }
        if (this.getOffset() != other.getOffset()) {
            return false;
        }
        if (Float.compare(this.getScoreThreshold(), other.getScoreThreshold()) != 0) {
            return false;
        }
        if (this.isWithPayload() != other.isWithPayload()) {
            return false;
        }
        List<Float> this$vector = this.getVector();
        List<Float> other$vector = other.getVector();
        if (this$vector == null ? other$vector != null : !((Object)this$vector).equals(other$vector)) {
            return false;
        }
        FiltersDTO this$filter = this.getFilter();
        FiltersDTO other$filter = other.getFilter();
        if (this$filter == null ? other$filter != null : !((Object)this$filter).equals(other$filter)) {
            return false;
        }
        SearchParamsDTO this$params = this.getParams();
        SearchParamsDTO other$params = other.getParams();
        return !(this$params == null ? other$params != null : !((Object)this$params).equals(other$params));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getLimit();
        result = result * 59 + this.getOffset();
        result = result * 59 + Float.floatToIntBits(this.getScoreThreshold());
        result = result * 59 + (this.isWithPayload() ? 79 : 97);
        List<Float> $vector = this.getVector();
        result = result * 59 + ($vector == null ? 43 : ((Object)$vector).hashCode());
        FiltersDTO $filter = this.getFilter();
        result = result * 59 + ($filter == null ? 43 : ((Object)$filter).hashCode());
        SearchParamsDTO $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : ((Object)$params).hashCode());
        return result;
    }

    public String toString() {
        return "VectorSearchDTO(limit=" + this.getLimit() + ", offset=" + this.getOffset() + ", vector=" + String.valueOf(this.getVector()) + ", scoreThreshold=" + this.getScoreThreshold() + ", filter=" + String.valueOf(this.getFilter()) + ", params=" + String.valueOf(this.getParams()) + ", withPayload=" + this.isWithPayload() + ")";
    }
}

