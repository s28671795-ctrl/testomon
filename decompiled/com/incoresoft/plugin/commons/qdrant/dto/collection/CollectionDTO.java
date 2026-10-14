/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.collection.CollectionInfoDTO;

public final class CollectionDTO {
    @JSONField(name="status")
    private final String status;
    @JSONField(name="result")
    private final CollectionInfoDTO result;
    @JSONField(name="time")
    private final float time;

    public CollectionDTO(String status, CollectionInfoDTO result, float time) {
        this.status = status;
        this.result = result;
        this.time = time;
    }

    public String getStatus() {
        return this.status;
    }

    public CollectionInfoDTO getResult() {
        return this.result;
    }

    public float getTime() {
        return this.time;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CollectionDTO)) {
            return false;
        }
        CollectionDTO other = (CollectionDTO)o;
        if (Float.compare(this.getTime(), other.getTime()) != 0) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        CollectionInfoDTO this$result = this.getResult();
        CollectionInfoDTO other$result = other.getResult();
        return !(this$result == null ? other$result != null : !((Object)this$result).equals(other$result));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getTime());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        CollectionInfoDTO $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : ((Object)$result).hashCode());
        return result;
    }

    public String toString() {
        return "CollectionDTO(status=" + this.getStatus() + ", result=" + String.valueOf(this.getResult()) + ", time=" + this.getTime() + ")";
    }
}

