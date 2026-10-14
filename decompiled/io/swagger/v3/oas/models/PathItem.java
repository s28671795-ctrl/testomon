/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PathItem {
    private String summary = null;
    private String description = null;
    private Operation get = null;
    private Operation put = null;
    private Operation post = null;
    private Operation delete = null;
    private Operation options = null;
    private Operation head = null;
    private Operation patch = null;
    private Operation trace = null;
    private List<Server> servers = null;
    private List<Parameter> parameters = null;
    private String $ref = null;
    private Map<String, Object> extensions = null;

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PathItem summary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PathItem description(String description) {
        this.description = description;
        return this;
    }

    public Operation getGet() {
        return this.get;
    }

    public void setGet(Operation get2) {
        this.get = get2;
    }

    public PathItem get(Operation get2) {
        this.get = get2;
        return this;
    }

    public Operation getPut() {
        return this.put;
    }

    public void setPut(Operation put) {
        this.put = put;
    }

    public PathItem put(Operation put) {
        this.put = put;
        return this;
    }

    public Operation getPost() {
        return this.post;
    }

    public void setPost(Operation post) {
        this.post = post;
    }

    public PathItem post(Operation post) {
        this.post = post;
        return this;
    }

    public Operation getDelete() {
        return this.delete;
    }

    public void setDelete(Operation delete) {
        this.delete = delete;
    }

    public PathItem delete(Operation delete) {
        this.delete = delete;
        return this;
    }

    public Operation getOptions() {
        return this.options;
    }

    public void setOptions(Operation options) {
        this.options = options;
    }

    public PathItem options(Operation options) {
        this.options = options;
        return this;
    }

    public Operation getHead() {
        return this.head;
    }

    public void setHead(Operation head) {
        this.head = head;
    }

    public PathItem head(Operation head) {
        this.head = head;
        return this;
    }

    public Operation getPatch() {
        return this.patch;
    }

    public void setPatch(Operation patch) {
        this.patch = patch;
    }

    public PathItem patch(Operation patch) {
        this.patch = patch;
        return this;
    }

    public Operation getTrace() {
        return this.trace;
    }

    public void setTrace(Operation trace) {
        this.trace = trace;
    }

    public PathItem trace(Operation trace) {
        this.trace = trace;
        return this;
    }

    public List<Operation> readOperations() {
        ArrayList<Operation> allOperations = new ArrayList<Operation>();
        if (this.get != null) {
            allOperations.add(this.get);
        }
        if (this.put != null) {
            allOperations.add(this.put);
        }
        if (this.head != null) {
            allOperations.add(this.head);
        }
        if (this.post != null) {
            allOperations.add(this.post);
        }
        if (this.delete != null) {
            allOperations.add(this.delete);
        }
        if (this.patch != null) {
            allOperations.add(this.patch);
        }
        if (this.options != null) {
            allOperations.add(this.options);
        }
        if (this.trace != null) {
            allOperations.add(this.trace);
        }
        return allOperations;
    }

    public void operation(HttpMethod method2, Operation operation) {
        switch (method2) {
            case PATCH: {
                this.patch = operation;
                break;
            }
            case POST: {
                this.post = operation;
                break;
            }
            case PUT: {
                this.put = operation;
                break;
            }
            case GET: {
                this.get = operation;
                break;
            }
            case OPTIONS: {
                this.options = operation;
                break;
            }
            case TRACE: {
                this.trace = operation;
                break;
            }
            case HEAD: {
                this.head = operation;
                break;
            }
            case DELETE: {
                this.delete = operation;
                break;
            }
        }
    }

    public Map<HttpMethod, Operation> readOperationsMap() {
        LinkedHashMap<HttpMethod, Operation> result = new LinkedHashMap<HttpMethod, Operation>();
        if (this.get != null) {
            result.put(HttpMethod.GET, this.get);
        }
        if (this.put != null) {
            result.put(HttpMethod.PUT, this.put);
        }
        if (this.post != null) {
            result.put(HttpMethod.POST, this.post);
        }
        if (this.delete != null) {
            result.put(HttpMethod.DELETE, this.delete);
        }
        if (this.patch != null) {
            result.put(HttpMethod.PATCH, this.patch);
        }
        if (this.head != null) {
            result.put(HttpMethod.HEAD, this.head);
        }
        if (this.options != null) {
            result.put(HttpMethod.OPTIONS, this.options);
        }
        if (this.trace != null) {
            result.put(HttpMethod.TRACE, this.trace);
        }
        return result;
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public PathItem servers(List<Server> servers) {
        this.servers = servers;
        return this;
    }

    public PathItem addServersItem(Server serversItem) {
        if (this.servers == null) {
            this.servers = new ArrayList<Server>();
        }
        this.servers.add(serversItem);
        return this;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public PathItem parameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public PathItem addParametersItem(Parameter parametersItem) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<Parameter>();
        }
        this.parameters.add(parametersItem);
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

    public PathItem extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public PathItem $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathItem)) {
            return false;
        }
        PathItem pathItem = (PathItem)o;
        if (this.summary != null ? !this.summary.equals(pathItem.summary) : pathItem.summary != null) {
            return false;
        }
        if (this.description != null ? !this.description.equals(pathItem.description) : pathItem.description != null) {
            return false;
        }
        if (this.get != null ? !this.get.equals(pathItem.get) : pathItem.get != null) {
            return false;
        }
        if (this.put != null ? !this.put.equals(pathItem.put) : pathItem.put != null) {
            return false;
        }
        if (this.post != null ? !this.post.equals(pathItem.post) : pathItem.post != null) {
            return false;
        }
        if (this.delete != null ? !this.delete.equals(pathItem.delete) : pathItem.delete != null) {
            return false;
        }
        if (this.options != null ? !this.options.equals(pathItem.options) : pathItem.options != null) {
            return false;
        }
        if (this.head != null ? !this.head.equals(pathItem.head) : pathItem.head != null) {
            return false;
        }
        if (this.patch != null ? !this.patch.equals(pathItem.patch) : pathItem.patch != null) {
            return false;
        }
        if (this.trace != null ? !this.trace.equals(pathItem.trace) : pathItem.trace != null) {
            return false;
        }
        if (this.servers != null ? !this.servers.equals(pathItem.servers) : pathItem.servers != null) {
            return false;
        }
        if (this.parameters != null ? !this.parameters.equals(pathItem.parameters) : pathItem.parameters != null) {
            return false;
        }
        if (this.$ref != null ? !this.$ref.equals(pathItem.$ref) : pathItem.$ref != null) {
            return false;
        }
        return this.extensions != null ? this.extensions.equals(pathItem.extensions) : pathItem.extensions == null;
    }

    public int hashCode() {
        int result = this.summary != null ? this.summary.hashCode() : 0;
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.get != null ? this.get.hashCode() : 0);
        result = 31 * result + (this.put != null ? this.put.hashCode() : 0);
        result = 31 * result + (this.post != null ? this.post.hashCode() : 0);
        result = 31 * result + (this.delete != null ? this.delete.hashCode() : 0);
        result = 31 * result + (this.options != null ? this.options.hashCode() : 0);
        result = 31 * result + (this.head != null ? this.head.hashCode() : 0);
        result = 31 * result + (this.patch != null ? this.patch.hashCode() : 0);
        result = 31 * result + (this.trace != null ? this.trace.hashCode() : 0);
        result = 31 * result + (this.servers != null ? this.servers.hashCode() : 0);
        result = 31 * result + (this.parameters != null ? this.parameters.hashCode() : 0);
        result = 31 * result + (this.$ref != null ? this.$ref.hashCode() : 0);
        result = 31 * result + (this.extensions != null ? this.extensions.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PathItem {\n");
        sb.append("    summary: ").append(this.toIndentedString(this.summary)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    get: ").append(this.toIndentedString(this.get)).append("\n");
        sb.append("    put: ").append(this.toIndentedString(this.put)).append("\n");
        sb.append("    post: ").append(this.toIndentedString(this.post)).append("\n");
        sb.append("    delete: ").append(this.toIndentedString(this.delete)).append("\n");
        sb.append("    options: ").append(this.toIndentedString(this.options)).append("\n");
        sb.append("    head: ").append(this.toIndentedString(this.head)).append("\n");
        sb.append("    patch: ").append(this.toIndentedString(this.patch)).append("\n");
        sb.append("    trace: ").append(this.toIndentedString(this.trace)).append("\n");
        sb.append("    servers: ").append(this.toIndentedString(this.servers)).append("\n");
        sb.append("    parameters: ").append(this.toIndentedString(this.parameters)).append("\n");
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

    public static enum HttpMethod {
        POST,
        GET,
        PUT,
        PATCH,
        DELETE,
        HEAD,
        OPTIONS,
        TRACE;

    }
}

