/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.servers;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServerVariable {
    private List<String> _enum = null;
    private String _default = null;
    private String description = null;
    private Map<String, Object> extensions = null;

    public List<String> getEnum() {
        return this._enum;
    }

    public void setEnum(List<String> _enum) {
        this._enum = _enum;
    }

    public ServerVariable _enum(List<String> _enum) {
        this._enum = _enum;
        return this;
    }

    public ServerVariable addEnumItem(String _enumItem) {
        if (this._enum == null) {
            this._enum = new ArrayList<String>();
        }
        this._enum.add(_enumItem);
        return this;
    }

    public String getDefault() {
        return this._default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public ServerVariable _default(String _default) {
        this._default = _default;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServerVariable description(String description) {
        this.description = description;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ServerVariable serverVariable = (ServerVariable)o;
        return Objects.equals(this._enum, serverVariable._enum) && Objects.equals(this._default, serverVariable._default) && Objects.equals(this.description, serverVariable.description) && Objects.equals(this.extensions, serverVariable.extensions);
    }

    public int hashCode() {
        return Objects.hash(this._enum, this._default, this.description, this.extensions);
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        this.addExtension(name, value);
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public ServerVariable extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServerVariable {\n");
        sb.append("    _enum: ").append(this.toIndentedString(this._enum)).append("\n");
        sb.append("    _default: ").append(this.toIndentedString(this._default)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
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

