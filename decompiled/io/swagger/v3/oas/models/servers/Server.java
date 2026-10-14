/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.servers;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.servers.ServerVariables;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Server {
    private String url = null;
    private String description = null;
    private ServerVariables variables = null;
    private Map<String, Object> extensions = null;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public Server url(String url2) {
        this.url = url2;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Server description(String description) {
        this.description = description;
        return this;
    }

    public ServerVariables getVariables() {
        return this.variables;
    }

    public void setVariables(ServerVariables variables) {
        this.variables = variables;
    }

    public Server variables(ServerVariables variables) {
        this.variables = variables;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Server server = (Server)o;
        return Objects.equals(this.url, server.url) && Objects.equals(this.description, server.description) && Objects.equals(this.variables, server.variables) && Objects.equals(this.extensions, server.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.url, this.description, this.variables, this.extensions);
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

    public Server extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Server {\n");
        sb.append("    url: ").append(this.toIndentedString(this.url)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    variables: ").append(this.toIndentedString(this.variables)).append("\n");
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

