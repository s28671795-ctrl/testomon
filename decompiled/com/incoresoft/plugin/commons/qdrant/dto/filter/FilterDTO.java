/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.filter;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.filter.MatchFilterValueDTO;
import com.incoresoft.plugin.commons.qdrant.dto.filter.RangeFilterValueDTO;
import java.util.Collection;

public final class FilterDTO {
    @JSONField(name="key")
    private final String key;
    @JSONField(name="match")
    private final MatchFilterValueDTO match;
    @JSONField(name="range")
    private final RangeFilterValueDTO range;

    public static <T> FilterDTO match(String key, T match) {
        return new FilterDTO(key, MatchFilterValueDTO.of(match), null);
    }

    public static <T> FilterDTO match(String key, Collection<T> values2) {
        return new FilterDTO(key, MatchFilterValueDTO.of(values2), null);
    }

    public static FilterDTO rangeIncluding(String key, long gte, long lte) {
        return new FilterDTO(key, null, RangeFilterValueDTO.including(gte, lte));
    }

    public static FilterDTO rangeExcluding(String key, long gt, long lt) {
        return new FilterDTO(key, null, RangeFilterValueDTO.excluding(gt, lt));
    }

    public FilterDTO(String key, MatchFilterValueDTO match, RangeFilterValueDTO range) {
        this.key = key;
        this.match = match;
        this.range = range;
    }

    public String getKey() {
        return this.key;
    }

    public MatchFilterValueDTO getMatch() {
        return this.match;
    }

    public RangeFilterValueDTO getRange() {
        return this.range;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FilterDTO)) {
            return false;
        }
        FilterDTO other = (FilterDTO)o;
        String this$key = this.getKey();
        String other$key = other.getKey();
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) {
            return false;
        }
        MatchFilterValueDTO this$match = this.getMatch();
        MatchFilterValueDTO other$match = other.getMatch();
        if (this$match == null ? other$match != null : !((Object)this$match).equals(other$match)) {
            return false;
        }
        RangeFilterValueDTO this$range = this.getRange();
        RangeFilterValueDTO other$range = other.getRange();
        return !(this$range == null ? other$range != null : !((Object)this$range).equals(other$range));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        MatchFilterValueDTO $match = this.getMatch();
        result = result * 59 + ($match == null ? 43 : ((Object)$match).hashCode());
        RangeFilterValueDTO $range = this.getRange();
        result = result * 59 + ($range == null ? 43 : ((Object)$range).hashCode());
        return result;
    }

    public String toString() {
        return "FilterDTO(key=" + this.getKey() + ", match=" + String.valueOf(this.getMatch()) + ", range=" + String.valueOf(this.getRange()) + ")";
    }
}

