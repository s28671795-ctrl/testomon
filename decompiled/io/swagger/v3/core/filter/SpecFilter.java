/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.filter;

import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.core.util.RefUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class SpecFilter {
    public OpenAPI filter(OpenAPI openAPI, OpenAPISpecFilter filter2, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        OpenAPI filteredOpenAPI = this.filterOpenAPI(filter2, openAPI, params, cookies, headers);
        if (filteredOpenAPI == null) {
            return filteredOpenAPI;
        }
        OpenAPI clone = new OpenAPI();
        clone.info(filteredOpenAPI.getInfo());
        clone.openapi(filteredOpenAPI.getOpenapi());
        clone.jsonSchemaDialect(filteredOpenAPI.getJsonSchemaDialect());
        clone.setSpecVersion(filteredOpenAPI.getSpecVersion());
        clone.setExtensions(filteredOpenAPI.getExtensions());
        clone.setExternalDocs(filteredOpenAPI.getExternalDocs());
        clone.setSecurity(filteredOpenAPI.getSecurity());
        clone.setServers(filteredOpenAPI.getServers());
        clone.tags((List<Tag>)(filteredOpenAPI.getTags() == null ? null : new ArrayList<Tag>(openAPI.getTags())));
        HashSet<String> allowedTags = new HashSet<String>();
        HashSet<String> filteredTags = new HashSet<String>();
        Paths clonedPaths = new Paths();
        if (filteredOpenAPI.getPaths() != null) {
            for (String resourcePath : filteredOpenAPI.getPaths().keySet()) {
                PathItem pathItem = (PathItem)filteredOpenAPI.getPaths().get(resourcePath);
                PathItem filteredPathItem = this.filterPathItem(filter2, pathItem, resourcePath, params, cookies, headers);
                PathItem clonedPathItem = this.cloneFilteredPathItem(filter2, filteredPathItem, resourcePath, params, cookies, headers, allowedTags, filteredTags);
                if (clonedPathItem == null || clonedPathItem.readOperations().isEmpty()) continue;
                clonedPaths.addPathItem(resourcePath, clonedPathItem);
            }
            clone.paths(clonedPaths);
        }
        filteredTags.removeAll(allowedTags);
        List<Tag> tags = clone.getTags();
        if (tags != null && !filteredTags.isEmpty()) {
            tags.removeIf(tag -> filteredTags.contains(tag.getName()));
            if (clone.getTags().isEmpty()) {
                clone.setTags(null);
            }
        }
        if (filteredOpenAPI.getWebhooks() != null) {
            for (String resourcePath : filteredOpenAPI.getWebhooks().keySet()) {
                PathItem pathItem = (PathItem)filteredOpenAPI.getPaths().get(resourcePath);
                PathItem filteredPathItem = this.filterPathItem(filter2, pathItem, resourcePath, params, cookies, headers);
                PathItem clonedPathItem = this.cloneFilteredPathItem(filter2, filteredPathItem, resourcePath, params, cookies, headers, allowedTags, filteredTags);
                if (clonedPathItem == null || clonedPathItem.readOperations().isEmpty()) continue;
                clone.addWebhooks(resourcePath, clonedPathItem);
            }
        }
        if (filteredOpenAPI.getComponents() != null) {
            clone.components(new Components());
            clone.getComponents().setSchemas(this.filterComponentsSchema(filter2, filteredOpenAPI.getComponents().getSchemas(), params, cookies, headers));
            clone.getComponents().setSecuritySchemes(filteredOpenAPI.getComponents().getSecuritySchemes());
            clone.getComponents().setCallbacks(filteredOpenAPI.getComponents().getCallbacks());
            clone.getComponents().setExamples(filteredOpenAPI.getComponents().getExamples());
            clone.getComponents().setExtensions(filteredOpenAPI.getComponents().getExtensions());
            clone.getComponents().setHeaders(filteredOpenAPI.getComponents().getHeaders());
            clone.getComponents().setLinks(filteredOpenAPI.getComponents().getLinks());
            clone.getComponents().setParameters(filteredOpenAPI.getComponents().getParameters());
            clone.getComponents().setRequestBodies(filteredOpenAPI.getComponents().getRequestBodies());
            clone.getComponents().setResponses(filteredOpenAPI.getComponents().getResponses());
            clone.getComponents().setPathItems(filteredOpenAPI.getComponents().getPathItems());
        }
        if (filter2.isRemovingUnreferencedDefinitions()) {
            clone = this.removeBrokenReferenceDefinitions(clone);
        }
        return clone;
    }

    protected OpenAPI filterOpenAPI(OpenAPISpecFilter filter2, OpenAPI openAPI, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        Optional<OpenAPI> filteredOpenAPI;
        if (openAPI != null && (filteredOpenAPI = filter2.filterOpenAPI(openAPI, params, cookies, headers)).isPresent()) {
            return filteredOpenAPI.get();
        }
        return null;
    }

    protected Operation filterOperation(OpenAPISpecFilter filter2, Operation operation, String resourcePath, String key, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        ApiDescription description;
        Optional<Operation> filteredOperation;
        if (operation != null && (filteredOperation = filter2.filterOperation(operation, description = new ApiDescription(resourcePath, key), params, cookies, headers)).isPresent()) {
            ApiResponses responses;
            RequestBody requestBody;
            ArrayList<Parameter> filteredParameters = new ArrayList<Parameter>();
            Operation filteredOperationGet = filteredOperation.get();
            Operation clone = new Operation();
            clone.setCallbacks(filteredOperationGet.getCallbacks());
            clone.setDeprecated(filteredOperationGet.getDeprecated());
            clone.setDescription(filteredOperationGet.getDescription());
            clone.setExtensions(filteredOperationGet.getExtensions());
            clone.setExternalDocs(filteredOperationGet.getExternalDocs());
            clone.setOperationId(filteredOperationGet.getOperationId());
            clone.setSecurity(filteredOperationGet.getSecurity());
            clone.setServers(filteredOperationGet.getServers());
            clone.setSummary(filteredOperationGet.getSummary());
            clone.setTags(filteredOperationGet.getTags());
            List<Parameter> parameters = filteredOperationGet.getParameters();
            if (parameters != null) {
                for (Parameter parameter : parameters) {
                    Parameter filteredParameter = this.filterParameter(filter2, operation, parameter, resourcePath, key, params, cookies, headers);
                    if (filteredParameter == null) continue;
                    filteredParameters.add(filteredParameter);
                }
                clone.setParameters(filteredParameters);
            }
            if ((requestBody = filteredOperation.get().getRequestBody()) != null) {
                RequestBody filteredRequestBody = this.filterRequestBody(filter2, operation, requestBody, resourcePath, key, params, cookies, headers);
                clone.setRequestBody(filteredRequestBody);
            }
            ApiResponses clonedResponses = responses = filteredOperation.get().getResponses();
            if (responses != null) {
                responses.forEach((responseKey, response) -> {
                    ApiResponse filteredResponse = this.filterResponse(filter2, operation, (ApiResponse)response, resourcePath, key, params, cookies, headers);
                    if (filteredResponse != null) {
                        clonedResponses.addApiResponse((String)responseKey, filteredResponse);
                    }
                });
                clone.setResponses(clonedResponses);
            }
            return clone;
        }
        return null;
    }

    protected PathItem filterPathItem(OpenAPISpecFilter filter2, PathItem pathItem, String resourcePath, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        ApiDescription description = new ApiDescription(resourcePath, null);
        Optional<PathItem> filteredPathItem = filter2.filterPathItem(pathItem, description, params, cookies, headers);
        if (filteredPathItem.isPresent()) {
            return filteredPathItem.get();
        }
        return null;
    }

    protected Parameter filterParameter(OpenAPISpecFilter filter2, Operation operation, Parameter parameter, String resourcePath, String key, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        ApiDescription description;
        Optional<Parameter> filteredParameter;
        if (parameter != null && (filteredParameter = filter2.filterParameter(parameter, operation, description = new ApiDescription(resourcePath, key), params, cookies, headers)).isPresent()) {
            return filteredParameter.get();
        }
        return null;
    }

    protected RequestBody filterRequestBody(OpenAPISpecFilter filter2, Operation operation, RequestBody requestBody, String resourcePath, String key, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        ApiDescription description;
        Optional<RequestBody> filteredRequestBody;
        if (requestBody != null && (filteredRequestBody = filter2.filterRequestBody(requestBody, operation, description = new ApiDescription(resourcePath, key), params, cookies, headers)).isPresent()) {
            return filteredRequestBody.get();
        }
        return null;
    }

    protected ApiResponse filterResponse(OpenAPISpecFilter filter2, Operation operation, ApiResponse response, String resourcePath, String key, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        ApiDescription description;
        Optional<ApiResponse> filteredResponse;
        if (response != null && (filteredResponse = filter2.filterResponse(response, operation, description = new ApiDescription(resourcePath, key), params, cookies, headers)).isPresent()) {
            return filteredResponse.get();
        }
        return null;
    }

    protected Map<String, Schema> filterComponentsSchema(OpenAPISpecFilter filter2, Map<String, Schema> schemasMap, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        if (schemasMap == null) {
            return null;
        }
        LinkedHashMap<String, Schema> clonedComponentsSchema = new LinkedHashMap<String, Schema>();
        for (Map.Entry<String, Schema> entry : schemasMap.entrySet()) {
            String key = entry.getKey();
            Schema definition = entry.getValue();
            Optional<Schema> filteredDefinition = filter2.filterSchema(definition, params, cookies, headers);
            if (!filteredDefinition.isPresent()) continue;
            LinkedHashMap<String, Schema> clonedProperties = new LinkedHashMap<String, Schema>();
            if (filteredDefinition.get().getProperties() != null) {
                for (String propName : filteredDefinition.get().getProperties().keySet()) {
                    Optional<Schema> filteredProperty;
                    Schema property = filteredDefinition.get().getProperties().get(propName);
                    if (property == null || !(filteredProperty = filter2.filterSchemaProperty(property, definition, propName, params, cookies, headers)).isPresent()) continue;
                    clonedProperties.put(propName, filteredProperty.get());
                }
            }
            try {
                Schema clonedModel = filter2.isOpenAPI31Filter() ? Json31.mapper().readValue(Json31.pretty(definition), Schema.class) : Json.mapper().readValue(Json.pretty(definition), Schema.class);
                if (clonedModel.getProperties() != null) {
                    clonedModel.getProperties().clear();
                }
                if (!clonedProperties.isEmpty()) {
                    clonedModel.setProperties(clonedProperties);
                }
                clonedComponentsSchema.put(key, clonedModel);
            }
            catch (IOException iOException) {}
        }
        return clonedComponentsSchema;
    }

    private void addSchemaRef(Schema schema, Set<String> referencedDefinitions) {
        if (schema == null) {
            return;
        }
        if (!StringUtils.isBlank(schema.get$ref())) {
            referencedDefinitions.add(schema.get$ref());
            return;
        }
        if (schema.getDiscriminator() != null && schema.getDiscriminator().getMapping() != null) {
            for (Map.Entry entry : schema.getDiscriminator().getMapping().entrySet()) {
                referencedDefinitions.add((String)entry.getValue());
            }
        }
        if (schema.getProperties() != null) {
            for (Object object : schema.getProperties().keySet()) {
                Schema property = schema.getProperties().get(object);
                this.addSchemaRef(property, referencedDefinitions);
            }
        }
        if (schema.getAdditionalProperties() instanceof Schema) {
            this.addSchemaRef((Schema)schema.getAdditionalProperties(), referencedDefinitions);
        }
        if (schema instanceof ArraySchema && ((ArraySchema)schema).getItems() != null) {
            this.addSchemaRef(((ArraySchema)schema).getItems(), referencedDefinitions);
        } else if (schema instanceof ComposedSchema) {
            ComposedSchema composedSchema = (ComposedSchema)schema;
            if (composedSchema.getAllOf() != null) {
                for (Schema ref : composedSchema.getAllOf()) {
                    this.addSchemaRef(ref, referencedDefinitions);
                }
            }
            if (composedSchema.getAnyOf() != null) {
                for (Schema ref : composedSchema.getAnyOf()) {
                    this.addSchemaRef(ref, referencedDefinitions);
                }
            }
            if (composedSchema.getOneOf() != null) {
                for (Schema ref : composedSchema.getOneOf()) {
                    this.addSchemaRef(ref, referencedDefinitions);
                }
            }
        }
    }

    private void addContentSchemaRef(Content content, Set<String> referencedDefinitions) {
        if (content != null) {
            for (MediaType mediaType : content.values()) {
                this.addSchemaRef(mediaType.getSchema(), referencedDefinitions);
            }
        }
    }

    private void addPathItemSchemaRef(PathItem pathItem, Set<String> referencedDefinitions) {
        if (pathItem.getParameters() != null) {
            for (Parameter parameter : pathItem.getParameters()) {
                this.addSchemaRef(parameter.getSchema(), referencedDefinitions);
                this.addContentSchemaRef(parameter.getContent(), referencedDefinitions);
            }
        }
        Map<PathItem.HttpMethod, Operation> ops = pathItem.readOperationsMap();
        for (Operation op : ops.values()) {
            if (op.getRequestBody() != null) {
                this.addContentSchemaRef(op.getRequestBody().getContent(), referencedDefinitions);
            }
            if (op.getResponses() != null) {
                for (String keyResponses : op.getResponses().keySet()) {
                    ApiResponse response = (ApiResponse)op.getResponses().get(keyResponses);
                    if (response.getHeaders() != null) {
                        for (String keyHeaders : response.getHeaders().keySet()) {
                            Header header = response.getHeaders().get(keyHeaders);
                            this.addSchemaRef(header.getSchema(), referencedDefinitions);
                            this.addContentSchemaRef(header.getContent(), referencedDefinitions);
                        }
                    }
                    this.addContentSchemaRef(response.getContent(), referencedDefinitions);
                }
            }
            if (op.getParameters() != null) {
                for (Parameter parameter : op.getParameters()) {
                    this.addSchemaRef(parameter.getSchema(), referencedDefinitions);
                    this.addContentSchemaRef(parameter.getContent(), referencedDefinitions);
                }
            }
            if (op.getCallbacks() == null) continue;
            for (String keyCallback : op.getCallbacks().keySet()) {
                Callback callback = op.getCallbacks().get(keyCallback);
                for (PathItem callbackPathItem : callback.values()) {
                    this.addPathItemSchemaRef(callbackPathItem, referencedDefinitions);
                }
            }
        }
    }

    protected OpenAPI removeBrokenReferenceDefinitions(OpenAPI openApi) {
        if (openApi == null || openApi.getComponents() == null || openApi.getComponents().getSchemas() == null) {
            return openApi;
        }
        TreeSet<String> referencedDefinitions = new TreeSet<String>();
        if (openApi.getPaths() != null) {
            for (String resourcePath : openApi.getPaths().keySet()) {
                PathItem pathItem = (PathItem)openApi.getPaths().get(resourcePath);
                this.addPathItemSchemaRef(pathItem, referencedDefinitions);
            }
        }
        referencedDefinitions.addAll(this.resolveAllNestedRefs(referencedDefinitions, referencedDefinitions, openApi));
        openApi.getComponents().getSchemas().keySet().retainAll(referencedDefinitions.stream().map(s2 -> (String)RefUtils.extractSimpleName(s2).getLeft()).collect(Collectors.toSet()));
        return openApi;
    }

    protected Set<String> resolveAllNestedRefs(Set<String> refs, Set<String> accumulatedRefs, OpenAPI openApi) {
        TreeSet<String> justDiscoveredReferencedDefinitions = new TreeSet<String>();
        for (String ref : refs) {
            this.locateReferencedDefinitions(ref, justDiscoveredReferencedDefinitions, openApi);
        }
        if (accumulatedRefs.containsAll(justDiscoveredReferencedDefinitions)) {
            return Collections.emptySet();
        }
        justDiscoveredReferencedDefinitions.removeAll(accumulatedRefs);
        accumulatedRefs.addAll(justDiscoveredReferencedDefinitions);
        return this.resolveAllNestedRefs(justDiscoveredReferencedDefinitions, accumulatedRefs, openApi);
    }

    protected void locateReferencedDefinitions(String ref, Set<String> nestedReferencedDefinitions, OpenAPI openAPI) {
        nestedReferencedDefinitions.add(ref);
        String simpleName = (String)RefUtils.extractSimpleName(ref).getLeft();
        Schema model = openAPI.getComponents().getSchemas().get(simpleName);
        if (model != null) {
            this.addSchemaRef(model, nestedReferencedDefinitions);
        }
    }

    private PathItem cloneFilteredPathItem(OpenAPISpecFilter filter2, PathItem filteredPathItem, String resourcePath, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers, Set<String> allowedTags, Set<String> filteredTags) {
        if (filteredPathItem == null) {
            return null;
        }
        PathItem clonedPathItem = new PathItem();
        clonedPathItem.set$ref(filteredPathItem.get$ref());
        clonedPathItem.setDescription(filteredPathItem.getDescription());
        clonedPathItem.setSummary(filteredPathItem.getSummary());
        clonedPathItem.setExtensions(filteredPathItem.getExtensions());
        clonedPathItem.setParameters(filteredPathItem.getParameters());
        clonedPathItem.setServers(filteredPathItem.getServers());
        Map<PathItem.HttpMethod, Operation> ops = filteredPathItem.readOperationsMap();
        for (Map.Entry<PathItem.HttpMethod, Operation> entry : ops.entrySet()) {
            PathItem.HttpMethod key = entry.getKey();
            Operation op = entry.getValue();
            ArrayList<Object> opTagsBeforeFilter = op.getTags() != null ? new ArrayList<String>(op.getTags()) : new ArrayList();
            op = this.filterOperation(filter2, op, resourcePath, key.toString(), params, cookies, headers);
            clonedPathItem.operation(key, op);
            if (op == null) {
                filteredTags.addAll(opTagsBeforeFilter);
                continue;
            }
            if (op.getTags() != null) {
                opTagsBeforeFilter.removeAll(op.getTags());
                allowedTags.addAll(op.getTags());
            }
            filteredTags.addAll(opTagsBeforeFilter);
        }
        return clonedPathItem;
    }
}

