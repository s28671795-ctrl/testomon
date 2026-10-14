/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.filter;

import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OpenAPISpecFilter {
    public Optional<OpenAPI> filterOpenAPI(OpenAPI var1, Map<String, List<String>> var2, Map<String, String> var3, Map<String, List<String>> var4);

    public Optional<PathItem> filterPathItem(PathItem var1, ApiDescription var2, Map<String, List<String>> var3, Map<String, String> var4, Map<String, List<String>> var5);

    public Optional<Operation> filterOperation(Operation var1, ApiDescription var2, Map<String, List<String>> var3, Map<String, String> var4, Map<String, List<String>> var5);

    public Optional<Parameter> filterParameter(Parameter var1, Operation var2, ApiDescription var3, Map<String, List<String>> var4, Map<String, String> var5, Map<String, List<String>> var6);

    public Optional<RequestBody> filterRequestBody(RequestBody var1, Operation var2, ApiDescription var3, Map<String, List<String>> var4, Map<String, String> var5, Map<String, List<String>> var6);

    public Optional<ApiResponse> filterResponse(ApiResponse var1, Operation var2, ApiDescription var3, Map<String, List<String>> var4, Map<String, String> var5, Map<String, List<String>> var6);

    public Optional<Schema> filterSchema(Schema var1, Map<String, List<String>> var2, Map<String, String> var3, Map<String, List<String>> var4);

    public Optional<Schema> filterSchemaProperty(Schema var1, Schema var2, String var3, Map<String, List<String>> var4, Map<String, String> var5, Map<String, List<String>> var6);

    public boolean isRemovingUnreferencedDefinitions();

    default public boolean isOpenAPI31Filter() {
        return false;
    }
}

