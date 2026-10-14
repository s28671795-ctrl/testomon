/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;

public final class CreateCollectionResponseDTO {
    @JSONField(name="status")
    private final String status;
    @JSONField(name="result")
    private final boolean result;
    @JSONField(name="time")
    private final float time;

    public CreateCollectionResponseDTO(String status, boolean result, float time) {
        this.status = status;
        this.result = result;
        this.time = time;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isResult() {
        return this.result;
    }

    public float getTime() {
        return this.time;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateCollectionResponseDTO)) {
            return false;
        }
        CreateCollectionResponseDTO other = (CreateCollectionResponseDTO)o;
        if (this.isResult() != other.isResult()) {
            return false;
        }
        if (Float.compare(this.getTime(), other.getTime()) != 0) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isResult() ? 79 : 97);
        result = result * 59 + Float.floatToIntBits(this.getTime());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "CreateCollectionResponseDTO(status=" + this.getStatus() + ", result=" + this.isResult() + ", time=" + this.getTime() + ")";
    }
}

