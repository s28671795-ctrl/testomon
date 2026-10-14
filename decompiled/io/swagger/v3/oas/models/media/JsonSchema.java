/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.media.Schema;

public class JsonSchema
extends Schema<Object> {
    public JsonSchema() {
        this.specVersion(SpecVersion.V31);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class JsonSchema {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

