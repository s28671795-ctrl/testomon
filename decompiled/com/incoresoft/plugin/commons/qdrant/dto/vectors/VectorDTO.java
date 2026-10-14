/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.vectors;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public final class VectorDTO {
    @JSONField(name="id")
    private final long id;
    @JSONField(name="vector")
    private final List<Float> vector;
    @JSONField(name="payload")
    private final Object payload;

    public VectorDTO(long id, List<Float> vector, Object payload) {
        this.id = id;
        this.vector = vector;
        this.payload = payload;
    }

    public long getId() {
        return this.id;
    }

    public List<Float> getVector() {
        return this.vector;
    }

    public Object getPayload() {
        return this.payload;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof VectorDTO)) {
            return false;
        }
        VectorDTO other = (VectorDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        List<Float> this$vector = this.getVector();
        List<Float> other$vector = other.getVector();
        if (this$vector == null ? other$vector != null : !((Object)this$vector).equals(other$vector)) {
            return false;
        }
        Object this$payload = this.getPayload();
        Object other$payload = other.getPayload();
        return !(this$payload == null ? other$payload != null : !this$payload.equals(other$payload));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.getId();
        result = result * 59 + (int)($id >>> 32 ^ $id);
        List<Float> $vector = this.getVector();
        result = result * 59 + ($vector == null ? 43 : ((Object)$vector).hashCode());
        Object $payload = this.getPayload();
        result = result * 59 + ($payload == null ? 43 : $payload.hashCode());
        return result;
    }

    public String toString() {
        return "VectorDTO(id=" + this.getId() + ", vector=" + String.valueOf(this.getVector()) + ", payload=" + String.valueOf(this.getPayload()) + ")";
    }
}

