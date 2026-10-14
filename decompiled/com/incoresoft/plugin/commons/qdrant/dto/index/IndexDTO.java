/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.index;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.index.QDrantIndex;

public final class IndexDTO {
    @JSONField(name="field_name")
    private final String name;
    @JSONField(name="field_type")
    private final String type;

    public IndexDTO(@JSONField QDrantIndex index) {
        this.name = index.getName();
        this.type = index.getFieldType().getName();
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IndexDTO)) {
            return false;
        }
        IndexDTO other = (IndexDTO)o;
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        return !(this$type == null ? other$type != null : !this$type.equals(other$type));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "IndexDTO(name=" + this.getName() + ", type=" + this.getType() + ")";
    }
}

