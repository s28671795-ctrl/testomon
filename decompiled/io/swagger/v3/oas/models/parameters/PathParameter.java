/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.Objects;

public class PathParameter
extends Parameter {
    private String in = "path";
    private Boolean required = true;

    @Override
    public String getIn() {
        return this.in;
    }

    @Override
    public void setIn(String in) {
        this.in = in;
    }

    @Override
    public PathParameter in(String in) {
        this.in = in;
        return this;
    }

    @Override
    public Boolean getRequired() {
        return this.required;
    }

    @Override
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public PathParameter required(Boolean required) {
        this.required = required;
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
        PathParameter pathParameter = (PathParameter)o;
        return Objects.equals(this.in, pathParameter.in) && Objects.equals(this.required, pathParameter.required) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.in, this.required, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PathParameter {\n");
        sb.append("    ").append(PathParameter.toIndentedString(super.toString())).append("\n");
        sb.append("    in: ").append(PathParameter.toIndentedString(this.in)).append("\n");
        sb.append("    required: ").append(PathParameter.toIndentedString(this.required)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

