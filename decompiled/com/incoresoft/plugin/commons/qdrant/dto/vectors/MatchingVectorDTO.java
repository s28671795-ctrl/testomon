/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;

public final class MatchingVectorDTO {
    @JSONField(name="id")
    private final long id;
    @JSONField(name="score")
    private final float score;
    @JSONField(name="payload")
    private final String payload;

    public MatchingVectorDTO(long id, float score, String payload) {
        this.id = id;
        this.score = score;
        this.payload = payload;
    }

    public long getId() {
        return this.id;
    }

    public float getScore() {
        return this.score;
    }

    public String getPayload() {
        return this.payload;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MatchingVectorDTO)) {
            return false;
        }
        MatchingVectorDTO other = (MatchingVectorDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (Float.compare(this.getScore(), other.getScore()) != 0) {
            return false;
        }
        String this$payload = this.getPayload();
        String other$payload = other.getPayload();
        return !(this$payload == null ? other$payload != null : !this$payload.equals(other$payload));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.getId();
        result = result * 59 + (int)($id >>> 32 ^ $id);
        result = result * 59 + Float.floatToIntBits(this.getScore());
        String $payload = this.getPayload();
        result = result * 59 + ($payload == null ? 43 : $payload.hashCode());
        return result;
    }

    public String toString() {
        return "MatchingVectorDTO(id=" + this.getId() + ", score=" + this.getScore() + ", payload=" + this.getPayload() + ")";
    }
}

