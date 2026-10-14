/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.media.Schema;

public class ArraySchema
extends Schema<Object> {
    public ArraySchema() {
        super("array", null);
    }

    @Override
    public ArraySchema type(String type) {
        super.setType(type);
        return this;
    }

    public ArraySchema items(Schema items) {
        super.setItems(items);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ArraySchema {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

