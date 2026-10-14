/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsResponseResultItemDTO;
import java.util.List;

public final class GetVectorsResponseDTO {
    @JSONField(name="time")
    private final float time;
    @JSONField(name="status")
    private final String status;
    @JSONField(name="result")
    private final List<GetVectorsResponseResultItemDTO> result;

    public GetVectorsResponseDTO(float time, String status, List<GetVectorsResponseResultItemDTO> result) {
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

    public List<GetVectorsResponseResultItemDTO> getResult() {
        return this.result;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GetVectorsResponseDTO)) {
            return false;
        }
        GetVectorsResponseDTO other = (GetVectorsResponseDTO)o;
        if (Float.compare(this.getTime(), other.getTime()) != 0) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        List<GetVectorsResponseResultItemDTO> this$result = this.getResult();
        List<GetVectorsResponseResultItemDTO> other$result = other.getResult();
        return !(this$result == null ? other$result != null : !((Object)this$result).equals(other$result));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Float.floatToIntBits(this.getTime());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        List<GetVectorsResponseResultItemDTO> $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : ((Object)$result).hashCode());
        return result;
    }

    public String toString() {
        return "GetVectorsResponseDTO(time=" + this.getTime() + ", status=" + this.getStatus() + ", result=" + String.valueOf(this.getResult()) + ")";
    }
}

