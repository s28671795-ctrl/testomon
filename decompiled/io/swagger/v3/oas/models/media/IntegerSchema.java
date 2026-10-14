/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.media.Schema;
import java.text.NumberFormat;
import java.util.Objects;

public class IntegerSchema
extends Schema<Number> {
    public IntegerSchema() {
        super("integer", "int32");
    }

    @Override
    public IntegerSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public IntegerSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public IntegerSchema _default(Number _default) {
        super.setDefault(_default);
        return this;
    }

    @Override
    protected Number cast(Object value) {
        if (value != null) {
            try {
                Number casted = NumberFormat.getInstance().parse(value.toString());
                if (Integer.MIN_VALUE <= casted.longValue() && casted.longValue() <= Integer.MAX_VALUE) {
                    return Integer.parseInt(value.toString());
                }
                return Long.parseLong(value.toString());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    public IntegerSchema addEnumItem(Number _enumItem) {
        super.addEnumItemObject(_enumItem);
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
        sb.append("class IntegerSchema {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

