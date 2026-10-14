/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.media.Schema;
import java.util.Objects;

public class MapSchema
extends Schema<Object> {
    public MapSchema() {
        super("object", null);
    }

    @Override
    public MapSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MapSchema {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

