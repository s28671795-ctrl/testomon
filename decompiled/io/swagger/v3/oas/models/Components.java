/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Components {
    public static final String COMPONENTS_SCHEMAS_REF = "#/components/schemas/";
    private Map<String, Schema> schemas = null;
    private Map<String, ApiResponse> responses = null;
    private Map<String, Parameter> parameters = null;
    private Map<String, Example> examples = null;
    private Map<String, RequestBody> requestBodies = null;
    private Map<String, Header> headers = null;
    private Map<String, SecurityScheme> securitySchemes = null;
    private Map<String, Link> links = null;
    private Map<String, Callback> callbacks = null;
    private Map<String, Object> extensions = null;
    @OpenAPI31
    private Map<String, PathItem> pathItems;

    public Map<String, Schema> getSchemas() {
        return this.schemas;
    }

    public void setSchemas(Map<String, Schema> schemas) {
        this.schemas = schemas;
    }

    public Components schemas(Map<String, Schema> schemas) {
        this.schemas = schemas;
        return this;
    }

    public Components addSchemas(String key, Schema schemasItem) {
        if (this.schemas == null) {
            this.schemas = new LinkedHashMap<String, Schema>();
        }
        this.schemas.put(key, schemasItem);
        return this;
    }

    public Map<String, ApiResponse> getResponses() {
        return this.responses;
    }

    public void setResponses(Map<String, ApiResponse> responses) {
        this.responses = responses;
    }

    public Components responses(Map<String, ApiResponse> responses) {
        this.responses = responses;
        return this;
    }

    public Components addResponses(String key, ApiResponse responsesItem) {
        if (this.responses == null) {
            this.responses = new LinkedHashMap<String, ApiResponse>();
        }
        this.responses.put(key, responsesItem);
        return this;
    }

    public Map<String, Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    }

    public Components parameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Components addParameters(String key, Parameter parametersItem) {
        if (this.parameters == null) {
            this.parameters = new LinkedHashMap<String, Parameter>();
        }
        this.parameters.put(key, parametersItem);
        return this;
    }

    public Map<String, Example> getExamples() {
        return this.examples;
    }

    public void setExamples(Map<String, Example> examples) {
        this.examples = examples;
    }

    public Components examples(Map<String, Example> examples) {
        this.examples = examples;
        return this;
    }

    public Components addExamples(String key, Example examplesItem) {
        if (this.examples == null) {
            this.examples = new LinkedHashMap<String, Example>();
        }
        this.examples.put(key, examplesItem);
        return this;
    }

    public Map<String, RequestBody> getRequestBodies() {
        return this.requestBodies;
    }

    public void setRequestBodies(Map<String, RequestBody> requestBodies) {
        this.requestBodies = requestBodies;
    }

    public Components requestBodies(Map<String, RequestBody> requestBodies) {
        this.requestBodies = requestBodies;
        return this;
    }

    public Components addRequestBodies(String key, RequestBody requestBodiesItem) {
        if (this.requestBodies == null) {
            this.requestBodies = new LinkedHashMap<String, RequestBody>();
        }
        this.requestBodies.put(key, requestBodiesItem);
        return this;
    }

    public Map<String, Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    public Components headers(Map<String, Header> headers) {
        this.headers = headers;
        return this;
    }

    public Components addHeaders(String key, Header headersItem) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, Header>();
        }
        this.headers.put(key, headersItem);
        return this;
    }

    public Map<String, SecurityScheme> getSecuritySchemes() {
        return this.securitySchemes;
    }

    public void setSecuritySchemes(Map<String, SecurityScheme> securitySchemes) {
        this.securitySchemes = securitySchemes;
    }

    public Components securitySchemes(Map<String, SecurityScheme> securitySchemes) {
        this.securitySchemes = securitySchemes;
        return this;
    }

    public Components addSecuritySchemes(String key, SecurityScheme securitySchemesItem) {
        if (this.securitySchemes == null) {
            this.securitySchemes = new LinkedHashMap<String, SecurityScheme>();
        }
        this.securitySchemes.put(key, securitySchemesItem);
        return this;
    }

    public Map<String, Link> getLinks() {
        return this.links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Components links(Map<String, Link> links) {
        this.links = links;
        return this;
    }

    public Components addLinks(String key, Link linksItem) {
        if (this.links == null) {
            this.links = new LinkedHashMap<String, Link>();
        }
        this.links.put(key, linksItem);
        return this;
    }

    public Map<String, Callback> getCallbacks() {
        return this.callbacks;
    }

    public void setCallbacks(Map<String, Callback> callbacks) {
        this.callbacks = callbacks;
    }

    public Components callbacks(Map<String, Callback> callbacks) {
        this.callbacks = callbacks;
        return this;
    }

    public Components addCallbacks(String key, Callback callbacksItem) {
        if (this.callbacks == null) {
            this.callbacks = new LinkedHashMap<String, Callback>();
        }
        this.callbacks.put(key, callbacksItem);
        return this;
    }

    @OpenAPI31
    public Map<String, PathItem> getPathItems() {
        return this.pathItems;
    }

    @OpenAPI31
    public void setPathItems(Map<String, PathItem> pathItems) {
        this.pathItems = pathItems;
    }

    @OpenAPI31
    public Components pathItems(Map<String, PathItem> pathItems) {
        this.pathItems = pathItems;
        return this;
    }

    @OpenAPI31
    public Components addPathItem(String key, PathItem pathItem) {
        if (this.pathItems == null) {
            this.pathItems = new LinkedHashMap<String, PathItem>();
        }
        this.pathItems.put(key, pathItem);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Components components = (Components)o;
        return Objects.equals(this.schemas, components.schemas) && Objects.equals(this.responses, components.responses) && Objects.equals(this.parameters, components.parameters) && Objects.equals(this.examples, components.examples) && Objects.equals(this.requestBodies, components.requestBodies) && Objects.equals(this.headers, components.headers) && Objects.equals(this.securitySchemes, components.securitySchemes) && Objects.equals(this.links, components.links) && Objects.equals(this.callbacks, components.callbacks) && Objects.equals(this.extensions, components.extensions) && Objects.equals(this.pathItems, components.pathItems);
    }

    public int hashCode() {
        return Objects.hash(this.schemas, this.responses, this.parameters, this.examples, this.requestBodies, this.headers, this.securitySchemes, this.links, this.callbacks, this.extensions, this.pathItems);
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

    public Components extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Components {\n");
        sb.append("    schemas: ").append(this.toIndentedString(this.schemas)).append("\n");
        sb.append("    responses: ").append(this.toIndentedString(this.responses)).append("\n");
        sb.append("    parameters: ").append(this.toIndentedString(this.parameters)).append("\n");
        sb.append("    examples: ").append(this.toIndentedString(this.examples)).append("\n");
        sb.append("    requestBodies: ").append(this.toIndentedString(this.requestBodies)).append("\n");
        sb.append("    headers: ").append(this.toIndentedString(this.headers)).append("\n");
        sb.append("    securitySchemes: ").append(this.toIndentedString(this.securitySchemes)).append("\n");
        sb.append("    links: ").append(this.toIndentedString(this.links)).append("\n");
        sb.append("    callbacks: ").append(this.toIndentedString(this.callbacks)).append("\n");
        sb.append("    pathItems: ").append(this.toIndentedString(this.pathItems)).append("\n");
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

