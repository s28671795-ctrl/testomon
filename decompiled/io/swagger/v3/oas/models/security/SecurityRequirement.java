/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class SecurityRequirement
extends LinkedHashMap<String, List<String>> {
    public SecurityRequirement addList(String name, String item) {
        this.put(name, Arrays.asList(item));
        return this;
    }

    public SecurityRequirement addList(String name, List<String> item) {
        this.put(name, item);
        return this;
    }

    public SecurityRequirement addList(String name) {
        this.put(name, new ArrayList());
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
        sb.append("class SecurityRequirement {\n");
        sb.append("    ").append(this.toIndentedString(super.toString())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

