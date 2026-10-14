/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.servers;

import io.swagger.v3.oas.models.servers.ServerVariable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ServerVariables
extends LinkedHashMap<String, ServerVariable> {
    private Map<String, Object> extensions = null;

    public ServerVariables addServerVariable(String name, ServerVariable item) {
        this.put(name, item);
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
        ServerVariables serverVariables = (ServerVariables)o;
        return Objects.equals(this.extensions, serverVariables.extensions) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.extensions, super.hashCode());
    }

    @Deprecated
    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    @Deprecated
    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    @Deprecated
    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    @Deprecated
    public ServerVariables extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServerVariables {\n");
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

