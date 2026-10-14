/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.links;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.servers.Server;
import java.util.LinkedHashMap;
import java.util.Map;

public class Link {
    private String operationRef = null;
    private String operationId = null;
    private Map<String, String> parameters = null;
    private Object requestBody = null;
    @Deprecated
    private Map<String, Header> headers = null;
    private String description = null;
    private String $ref = null;
    private Map<String, Object> extensions = null;
    private Server server;

    public Server getServer() {
        return this.server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Link server(Server server) {
        this.setServer(server);
        return this;
    }

    public String getOperationRef() {
        return this.operationRef;
    }

    public void setOperationRef(String operationRef) {
        this.operationRef = operationRef;
    }

    public Link operationRef(String operationRef) {
        this.operationRef = operationRef;
        return this;
    }

    public Object getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public Link requestBody(Object requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getOperationId() {
        return this.operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Link operationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Deprecated
    public Link parameters(String name, String parameter) {
        return this.addParameter(name, parameter);
    }

    public Link addParameter(String name, String parameter) {
        if (this.parameters == null) {
            this.parameters = new LinkedHashMap<String, String>();
        }
        this.parameters.put(name, parameter);
        return this;
    }

    @Deprecated
    public Map<String, Header> getHeaders() {
        return this.headers;
    }

    @Deprecated
    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    @Deprecated
    public Link headers(Map<String, Header> headers) {
        this.headers = headers;
        return this;
    }

    @Deprecated
    public Link addHeaderObject(String name, Header header) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, Header>();
        }
        this.headers.put(name, header);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Link description(String description) {
        this.description = description;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }
        Link link = (Link)o;
        if (this.operationRef != null ? !this.operationRef.equals(link.operationRef) : link.operationRef != null) {
            return false;
        }
        if (this.operationId != null ? !this.operationId.equals(link.operationId) : link.operationId != null) {
            return false;
        }
        if (this.parameters != null ? !this.parameters.equals(link.parameters) : link.parameters != null) {
            return false;
        }
        if (this.requestBody != null ? !this.requestBody.equals(link.requestBody) : link.requestBody != null) {
            return false;
        }
        if (this.headers != null ? !this.headers.equals(link.headers) : link.headers != null) {
            return false;
        }
        if (this.description != null ? !this.description.equals(link.description) : link.description != null) {
            return false;
        }
        if (this.$ref != null ? !this.$ref.equals(link.$ref) : link.$ref != null) {
            return false;
        }
        if (this.extensions != null ? !this.extensions.equals(link.extensions) : link.extensions != null) {
            return false;
        }
        return this.server != null ? this.server.equals(link.server) : link.server == null;
    }

    public int hashCode() {
        int result = this.operationRef != null ? this.operationRef.hashCode() : 0;
        result = 31 * result + (this.operationId != null ? this.operationId.hashCode() : 0);
        result = 31 * result + (this.parameters != null ? this.parameters.hashCode() : 0);
        result = 31 * result + (this.requestBody != null ? this.requestBody.hashCode() : 0);
        result = 31 * result + (this.headers != null ? this.headers.hashCode() : 0);
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.$ref != null ? this.$ref.hashCode() : 0);
        result = 31 * result + (this.extensions != null ? this.extensions.hashCode() : 0);
        result = 31 * result + (this.server != null ? this.server.hashCode() : 0);
        return result;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/links/" + $ref;
        }
        this.$ref = $ref;
    }

    public Link $ref(String $ref) {
        this.set$ref($ref);
        return this;
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

    public Link extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Link {\n");
        sb.append("    operationRef: ").append(this.toIndentedString(this.operationRef)).append("\n");
        sb.append("    operationId: ").append(this.toIndentedString(this.operationId)).append("\n");
        sb.append("    parameters: ").append(this.toIndentedString(this.parameters)).append("\n");
        sb.append("    requestBody: ").append(this.toIndentedString(this.requestBody)).append("\n");
        sb.append("    headers: ").append(this.toIndentedString(this.headers)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    $ref: ").append(this.toIndentedString(this.$ref)).append("\n");
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

