/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.Objects;

public class HeaderParameter
extends Parameter {
    private String in = "header";

    @Override
    public String getIn() {
        return this.in;
    }

    @Override
    public void setIn(String in) {
        this.in = in;
    }

    @Override
    public HeaderParameter in(String in) {
        this.in = in;
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
        HeaderParameter headerParameter = (HeaderParameter)o;
        return Objects.equals(this.in, headerParameter.in) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.in, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HeaderParameter {\n");
        sb.append("    ").append(HeaderParameter.toIndentedString(super.toString())).append("\n");
        sb.append("    in: ").append(HeaderParameter.toIndentedString(this.in)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

