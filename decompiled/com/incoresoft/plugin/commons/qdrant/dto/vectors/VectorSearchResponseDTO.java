/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.MatchingVectorDTO;
import java.util.List;

public final class VectorSearchResponseDTO {
    @JSONField(name="result")
    private final List<List<MatchingVectorDTO>> result;
    @JSONField(name="status")
    private final String status;
    @JSONField(name="time")
    private final float time;

    public VectorSearchResponseDTO(List<List<MatchingVectorDTO>> result, String status, float time) {
        this.result = result;
        this.status = status;
        this.time = time;
    }

    public List<List<MatchingVectorDTO>> getResult() {
        return this.result;
    }

    public String getStatus() {
        return this.status;
    }

    public float getTime() {
        return this.time;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VectorSearchResponseDTO)) {
            return false;
        }
        VectorSearchResponseDTO other = (VectorSearchResponseDTO)o;
        if (Float.compare(this.getTime(), other.getTime()) != 0) {
            return false;
        }
        List<List<MatchingVectorDTO>> this$result = this.getResult();
        List<List<MatchingVectorDTO>> other$result = other.getResult();
        if (this$result == null ? other$result != null : !((Object)this$result).equals(other$result)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getTime());
        List<List<MatchingVectorDTO>> $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : ((Object)$result).hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "VectorSearchResponseDTO(result=" + String.valueOf(this.getResult()) + ", status=" + this.getStatus() + ", time=" + this.getTime() + ")";
    }
}

