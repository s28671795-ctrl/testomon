/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.Objects;

public class CookieParameter
extends Parameter {
    private String in = "cookie";

    @Override
    public String getIn() {
        return this.in;
    }

    @Override
    public void setIn(String in) {
        this.in = in;
    }

    @Override
    public CookieParameter in(String in) {
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
        CookieParameter cookieParameter = (CookieParameter)o;
        return Objects.equals(this.in, cookieParameter.in) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.in, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CookieParameter {\n");
        sb.append("    ").append(CookieParameter.toIndentedString(super.toString())).append("\n");
        sb.append("    in: ").append(CookieParameter.toIndentedString(this.in)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

