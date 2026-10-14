/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Operation {
    private List<String> tags = null;
    private String summary = null;
    private String description = null;
    private ExternalDocumentation externalDocs = null;
    private String operationId = null;
    private List<Parameter> parameters = null;
    private RequestBody requestBody = null;
    private ApiResponses responses = null;
    private Map<String, Callback> callbacks = null;
    private Boolean deprecated = null;
    private List<SecurityRequirement> security = null;
    private List<Server> servers = null;
    private Map<String, Object> extensions = null;

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Operation tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Operation addTagsItem(String tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<String>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Operation summary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Operation description(String description) {
        this.description = description;
        return this;
    }

    public ExternalDocumentation getExternalDocs() {
        return this.externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public Operation externalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public String getOperationId() {
        return this.operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Operation operationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Operation parameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Operation addParametersItem(Parameter parametersItem) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<Parameter>();
        }
        this.parameters.add(parametersItem);
        return this;
    }

    public RequestBody getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Operation requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public ApiResponses getResponses() {
        return this.responses;
    }

    public void setResponses(ApiResponses responses) {
        this.responses = responses;
    }

    public Operation responses(ApiResponses responses) {
        this.responses = responses;
        return this;
    }

    public Map<String, Callback> getCallbacks() {
        return this.callbacks;
    }

    public void setCallbacks(Map<String, Callback> callbacks) {
        this.callbacks = callbacks;
    }

    public Operation callbacks(Map<String, Callback> callbacks) {
        this.callbacks = callbacks;
        return this;
    }

    public Operation addCallback(String key, Callback callback) {
        if (this.callbacks == null) {
            this.callbacks = new LinkedHashMap<String, Callback>();
        }
        this.callbacks.put(key, callback);
        return this;
    }

    public Boolean getDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
    }

    public Operation deprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
        return this;
    }

    public List<SecurityRequirement> getSecurity() {
        return this.security;
    }

    public void setSecurity(List<SecurityRequirement> security) {
        this.security = security;
    }

    public Operation security(List<SecurityRequirement> security) {
        this.security = security;
        return this;
    }

    public Operation addSecurityItem(SecurityRequirement securityItem) {
        if (this.security == null) {
            this.security = new ArrayList<SecurityRequirement>();
        }
        this.security.add(securityItem);
        return this;
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public Operation servers(List<Server> servers) {
        this.servers = servers;
        return this;
    }

    public Operation addServersItem(Server serversItem) {
        if (this.servers == null) {
            this.servers = new ArrayList<Server>();
        }
        this.servers.add(serversItem);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Operation operation = (Operation)o;
        return Objects.equals(this.tags, operation.tags) && Objects.equals(this.summary, operation.summary) && Objects.equals(this.description, operation.description) && Objects.equals(this.externalDocs, operation.externalDocs) && Objects.equals(this.operationId, operation.operationId) && Objects.equals(this.parameters, operation.parameters) && Objects.equals(this.requestBody, operation.requestBody) && Objects.equals(this.responses, operation.responses) && Objects.equals(this.callbacks, operation.callbacks) && Objects.equals(this.deprecated, operation.deprecated) && Objects.equals(this.security, operation.security) && Objects.equals(this.servers, operation.servers) && Objects.equals(this.extensions, operation.extensions);
    }

    public int hashCode() {
        return Objects.hash(this.tags, this.summary, this.description, this.externalDocs, this.operationId, this.parameters, this.requestBody, this.responses, this.callbacks, this.deprecated, this.security, this.servers, this.extensions);
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

    public Operation extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Operation {\n");
        sb.append("    tags: ").append(this.toIndentedString(this.tags)).append("\n");
        sb.append("    summary: ").append(this.toIndentedString(this.summary)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    externalDocs: ").append(this.toIndentedString(this.externalDocs)).append("\n");
        sb.append("    operationId: ").append(this.toIndentedString(this.operationId)).append("\n");
        sb.append("    parameters: ").append(this.toIndentedString(this.parameters)).append("\n");
        sb.append("    requestBody: ").append(this.toIndentedString(this.requestBody)).append("\n");
        sb.append("    responses: ").append(this.toIndentedString(this.responses)).append("\n");
        sb.append("    callbacks: ").append(this.toIndentedString(this.callbacks)).append("\n");
        sb.append("    deprecated: ").append(this.toIndentedString(this.deprecated)).append("\n");
        sb.append("    security: ").append(this.toIndentedString(this.security)).append("\n");
        sb.append("    servers: ").append(this.toIndentedString(this.servers)).append("\n");
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

