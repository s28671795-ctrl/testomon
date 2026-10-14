/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class NumberSchema
extends Schema<BigDecimal> {
    public NumberSchema() {
        super("number", null);
    }

    @Override
    public NumberSchema type(String type) {
        super.setType(type);
        return this;
    }

    public NumberSchema _default(BigDecimal _default) {
        super.setDefault(_default);
        return this;
    }

    public NumberSchema _enum(List<BigDecimal> _enum) {
        super.setEnum(_enum);
        return this;
    }

    public NumberSchema addEnumItem(BigDecimal _enumItem) {
        super.addEnumItemObject(_enumItem);
        return this;
    }

    @Override
    protected BigDecimal cast(Object value) {
        if (value != null) {
            try {
                return new BigDecimal(value.toString());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
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
        sb.append("class NumberSchema {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

