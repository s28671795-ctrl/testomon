/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.ScrollVectorsResultDTO;

public final class ScrollVectorsResponseDTO {
    @JSONField(name="time")
    private final float time;
    @JSONField(name="status")
    private final String status;
    @JSONField(name="result")
    private final ScrollVectorsResultDTO result;

    public ScrollVectorsResponseDTO(float time, String status, ScrollVectorsResultDTO result) {
        this.time = time;
        this.status = status;
        this.result = result;
    }

    public float getTime() {
        return this.time;
    }

    public String getStatus() {
        return this.status;
    }

    public ScrollVectorsResultDTO getResult() {
        return this.result;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScrollVectorsResponseDTO)) {
            return false;
        }
        ScrollVectorsResponseDTO other = (ScrollVectorsResponseDTO)o;
        if (Float.compare(this.getTime(), other.getTime()) != 0) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        ScrollVectorsResultDTO this$result = this.getResult();
        ScrollVectorsResultDTO other$result = other.getResult();
        return !(this$result == null ? other$result != null : !((Object)this$result).equals(other$result));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getTime());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        ScrollVectorsResultDTO $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : ((Object)$result).hashCode());
        return result;
    }

    public String toString() {
        return "ScrollVectorsResponseDTO(time=" + this.getTime() + ", status=" + this.getStatus() + ", result=" + String.valueOf(this.getResult()) + ")";
    }
}

