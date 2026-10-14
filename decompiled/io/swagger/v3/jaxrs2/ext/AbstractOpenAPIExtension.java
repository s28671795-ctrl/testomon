/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.ext;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.jaxrs2.ResolvedParameter;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;

public abstract class AbstractOpenAPIExtension
implements OpenAPIExtension {
    protected boolean openapi31;

    @Override
    public String extractOperationMethod(Method method2, Iterator<OpenAPIExtension> chain) {
        if (chain.hasNext()) {
            return chain.next().extractOperationMethod(method2, chain);
        }
        return null;
    }

    @Override
    public ResolvedParameter extractParameters(List<Annotation> annotations, Type type, Set<Type> typesToSkip, Components components, Consumes classConsumes, Consumes methodConsumes, boolean includeRequestBody, JsonView jsonViewAnnotation, Iterator<OpenAPIExtension> chain) {
        if (chain.hasNext()) {
            return chain.next().extractParameters(annotations, type, typesToSkip, components, classConsumes, methodConsumes, includeRequestBody, jsonViewAnnotation, chain);
        }
        return new ResolvedParameter();
    }

    @Override
    public void decorateOperation(Operation operation, Method method2, Iterator<OpenAPIExtension> chain) {
        if (chain.hasNext()) {
            chain.next().decorateOperation(operation, method2, chain);
        }
    }

    protected boolean shouldIgnoreClass(Class<?> cls) {
        return false;
    }

    protected boolean shouldIgnoreType(Type type, Set<Type> typesToSkip) {
        if (typesToSkip.contains(type)) {
            return true;
        }
        if (this.shouldIgnoreClass(this.constructType(type).getRawClass())) {
            typesToSkip.add(type);
            return true;
        }
        return false;
    }

    protected JavaType constructType(Type type) {
        return TypeFactory.defaultInstance().constructType(type);
    }

    @Override
    public void setOpenAPI31(boolean openapi31) {
        this.openapi31 = openapi31;
    }
}

