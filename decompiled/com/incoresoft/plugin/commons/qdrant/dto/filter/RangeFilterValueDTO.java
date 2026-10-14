/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.filter;

import com.alibaba.fastjson.annotation.JSONField;

public final class RangeFilterValueDTO {
    @JSONField(name="gte")
    private final Long gte;
    @JSONField(name="lte")
    private final Long lte;
    @JSONField(name="gt")
    private final Long gt;
    @JSONField(name="lt")
    private final Long lt;

    public static RangeFilterValueDTO including(Long gte, Long lte) {
        return new RangeFilterValueDTO(gte, lte, null, null);
    }

    public static RangeFilterValueDTO excluding(Long gt, Long lt) {
        return new RangeFilterValueDTO(null, null, gt, lt);
    }

    public RangeFilterValueDTO(Long gte, Long lte, Long gt, Long lt) {
        this.gte = gte;
        this.lte = lte;
        this.gt = gt;
        this.lt = lt;
    }

    public Long getGte() {
        return this.gte;
    }

    public Long getLte() {
        return this.lte;
    }

    public Long getGt() {
        return this.gt;
    }

    public Long getLt() {
        return this.lt;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RangeFilterValueDTO)) {
            return false;
        }
        RangeFilterValueDTO other = (RangeFilterValueDTO)o;
        Long this$gte = this.getGte();
        Long other$gte = other.getGte();
        if (this$gte == null ? other$gte != null : !((Object)this$gte).equals(other$gte)) {
            return false;
        }
        Long this$lte = this.getLte();
        Long other$lte = other.getLte();
        if (this$lte == null ? other$lte != null : !((Object)this$lte).equals(other$lte)) {
            return false;
        }
        Long this$gt = this.getGt();
        Long other$gt = other.getGt();
        if (this$gt == null ? other$gt != null : !((Object)this$gt).equals(other$gt)) {
            return false;
        }
        Long this$lt = this.getLt();
        Long other$lt = other.getLt();
        return !(this$lt == null ? other$lt != null : !((Object)this$lt).equals(other$lt));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $gte = this.getGte();
        result = result * 59 + ($gte == null ? 43 : ((Object)$gte).hashCode());
        Long $lte = this.getLte();
        result = result * 59 + ($lte == null ? 43 : ((Object)$lte).hashCode());
        Long $gt = this.getGt();
        result = result * 59 + ($gt == null ? 43 : ((Object)$gt).hashCode());
        Long $lt = this.getLt();
        result = result * 59 + ($lt == null ? 43 : ((Object)$lt).hashCode());
        return result;
    }

    public String toString() {
        return "RangeFilterValueDTO(gte=" + this.getGte() + ", lte=" + this.getLte() + ", gt=" + this.getGt() + ", lt=" + this.getLt() + ")";
    }
}

