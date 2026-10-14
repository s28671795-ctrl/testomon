/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.ext;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.jaxrs2.ResolvedParameter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;

public interface OpenAPIExtension {
    public String extractOperationMethod(Method var1, Iterator<OpenAPIExtension> var2);

    public ResolvedParameter extractParameters(List<Annotation> var1, Type var2, Set<Type> var3, Components var4, Consumes var5, Consumes var6, boolean var7, JsonView var8, Iterator<OpenAPIExtension> var9);

    public void decorateOperation(Operation var1, Method var2, Iterator<OpenAPIExtension> var3);

    default public void setOpenAPI31(boolean openapi31) {
    }
}

