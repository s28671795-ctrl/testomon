/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OpenAPI {
    private String openapi = "3.0.1";
    private Info info = null;
    private ExternalDocumentation externalDocs = null;
    private List<Server> servers = null;
    private List<SecurityRequirement> security = null;
    private List<Tag> tags = null;
    private Paths paths = null;
    private Components components = null;
    private Map<String, Object> extensions = null;
    @OpenAPI31
    private String jsonSchemaDialect;
    private SpecVersion specVersion = SpecVersion.V30;
    @OpenAPI31
    private Map<String, PathItem> webhooks = null;

    public OpenAPI() {
    }

    public OpenAPI(SpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    @JsonIgnore
    public SpecVersion getSpecVersion() {
        return this.specVersion;
    }

    public void setSpecVersion(SpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    public OpenAPI specVersion(SpecVersion specVersion) {
        this.setSpecVersion(specVersion);
        return this;
    }

    public String getOpenapi() {
        return this.openapi;
    }

    public void setOpenapi(String openapi) {
        this.openapi = openapi;
    }

    public OpenAPI openapi(String openapi) {
        this.openapi = openapi;
        return this;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public OpenAPI info(Info info) {
        this.info = info;
        return this;
    }

    public ExternalDocumentation getExternalDocs() {
        return this.externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public OpenAPI externalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public OpenAPI servers(List<Server> servers) {
        this.servers = servers;
        return this;
    }

    public OpenAPI addServersItem(Server serversItem) {
        if (this.servers == null) {
            this.servers = new ArrayList<Server>();
        }
        this.servers.add(serversItem);
        return this;
    }

    public List<SecurityRequirement> getSecurity() {
        return this.security;
    }

    public void setSecurity(List<SecurityRequirement> security) {
        this.security = security;
    }

    public OpenAPI security(List<SecurityRequirement> security) {
        this.security = security;
        return this;
    }

    public OpenAPI addSecurityItem(SecurityRequirement securityItem) {
        if (this.security == null) {
            this.security = new ArrayList<SecurityRequirement>();
        }
        this.security.add(securityItem);
        return this;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public OpenAPI tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public OpenAPI addTagsItem(Tag tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<Tag>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    public Paths getPaths() {
        return this.paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    public OpenAPI paths(Paths paths) {
        this.paths = paths;
        return this;
    }

    public Components getComponents() {
        return this.components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    public OpenAPI components(Components components) {
        this.components = components;
        return this;
    }

    public OpenAPI path(String name, PathItem path) {
        if (this.paths == null) {
            this.paths = new Paths();
        }
        this.paths.addPathItem(name, path);
        return this;
    }

    public OpenAPI schema(String name, Schema schema) {
        if (this.components == null) {
            this.components = new Components();
        }
        this.components.addSchemas(name, schema);
        return this;
    }

    public OpenAPI schemaRequirement(String name, SecurityScheme securityScheme) {
        if (this.components == null) {
            this.components = new Components();
        }
        this.components.addSecuritySchemes(name, securityScheme);
        return this;
    }

    @OpenAPI31
    public Map<String, PathItem> getWebhooks() {
        return this.webhooks;
    }

    @OpenAPI31
    public void setWebhooks(Map<String, PathItem> webhooks) {
        this.webhooks = webhooks;
    }

    @OpenAPI31
    public OpenAPI webhooks(Map<String, PathItem> webhooks) {
        this.webhooks = webhooks;
        return this;
    }

    @OpenAPI31
    public OpenAPI addWebhooks(String key, PathItem pathItem) {
        if (this.webhooks == null) {
            this.webhooks = new LinkedHashMap<String, PathItem>();
        }
        this.webhooks.put(key, pathItem);
        return this;
    }

    @OpenAPI31
    public String getJsonSchemaDialect() {
        return this.jsonSchemaDialect;
    }

    @OpenAPI31
    public void setJsonSchemaDialect(String jsonSchemaDialect) {
        this.jsonSchemaDialect = jsonSchemaDialect;
    }

    @OpenAPI31
    public OpenAPI jsonSchemaDialect(String jsonSchemaDialect) {
        this.jsonSchemaDialect = jsonSchemaDialect;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OpenAPI openAPI = (OpenAPI)o;
        return Objects.equals(this.openapi, openAPI.openapi) && Objects.equals(this.info, openAPI.info) && Objects.equals(this.externalDocs, openAPI.externalDocs) && Objects.equals(this.servers, openAPI.servers) && Objects.equals(this.security, openAPI.security) && Objects.equals(this.tags, openAPI.tags) && Objects.equals(this.paths, openAPI.paths) && Objects.equals(this.components, openAPI.components) && Objects.equals(this.webhooks, openAPI.webhooks) && Objects.equals(this.extensions, openAPI.extensions) && Objects.equals(this.jsonSchemaDialect, openAPI.jsonSchemaDialect);
    }

    public int hashCode() {
        return Objects.hash(this.openapi, this.info, this.externalDocs, this.servers, this.security, this.tags, this.paths, this.components, this.webhooks, this.extensions, this.jsonSchemaDialect);
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

    public OpenAPI extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpenAPI {\n");
        sb.append("    openapi: ").append(this.toIndentedString(this.openapi)).append("\n");
        sb.append("    info: ").append(this.toIndentedString(this.info)).append("\n");
        sb.append("    externalDocs: ").append(this.toIndentedString(this.externalDocs)).append("\n");
        sb.append("    servers: ").append(this.toIndentedString(this.servers)).append("\n");
        sb.append("    security: ").append(this.toIndentedString(this.security)).append("\n");
        sb.append("    tags: ").append(this.toIndentedString(this.tags)).append("\n");
        sb.append("    paths: ").append(this.toIndentedString(this.paths)).append("\n");
        sb.append("    components: ").append(this.toIndentedString(this.components)).append("\n");
        if (this.specVersion == SpecVersion.V31) {
            sb.append("    webhooks: ").append(this.toIndentedString(this.webhooks)).append("\n");
        }
        if (this.specVersion == SpecVersion.V31) {
            sb.append("    jsonSchemaDialect: ").append(this.toIndentedString(this.jsonSchemaDialect)).append("\n");
        }
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

