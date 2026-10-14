/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.converter;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class AnnotatedType {
    private Type type;
    private String name;
    private Schema parent;
    private Function<AnnotatedType, Schema> jsonUnwrappedHandler;
    private boolean skipOverride;
    private boolean schemaProperty;
    private Annotation[] ctxAnnotations;
    private boolean resolveAsRef;
    private JsonView jsonViewAnnotation;
    private boolean includePropertiesWithoutJSONView = true;
    private boolean skipSchemaName;
    private boolean skipJsonIdentity;
    private String propertyName;
    private Components components;

    public AnnotatedType() {
    }

    public AnnotatedType(Type type) {
        this.type = type;
    }

    public boolean isSkipOverride() {
        return this.skipOverride;
    }

    public void setSkipOverride(boolean skipOverride) {
        this.skipOverride = skipOverride;
    }

    public AnnotatedType skipOverride(boolean skipOverride) {
        this.skipOverride = skipOverride;
        return this;
    }

    public boolean isSkipJsonIdentity() {
        return this.skipJsonIdentity;
    }

    public void setSkipJsonIdentity(boolean skipJsonIdentity) {
        this.skipJsonIdentity = skipJsonIdentity;
    }

    public AnnotatedType skipJsonIdentity(boolean skipJsonIdentity) {
        this.skipJsonIdentity = skipJsonIdentity;
        return this;
    }

    public boolean isSkipSchemaName() {
        return this.skipSchemaName;
    }

    public void setSkipSchemaName(boolean skipSchemaName) {
        this.skipSchemaName = skipSchemaName;
    }

    public AnnotatedType skipSchemaName(boolean skipSchemaName) {
        this.skipSchemaName = skipSchemaName;
        return this;
    }

    public boolean isResolveAsRef() {
        return this.resolveAsRef;
    }

    public void setResolveAsRef(boolean resolveAsRef) {
        this.resolveAsRef = resolveAsRef;
    }

    public AnnotatedType resolveAsRef(boolean resolveAsRef) {
        this.resolveAsRef = resolveAsRef;
        return this;
    }

    public boolean isSchemaProperty() {
        return this.schemaProperty;
    }

    public void setSchemaProperty(boolean schemaProperty) {
        this.schemaProperty = schemaProperty;
    }

    public AnnotatedType schemaProperty(boolean schemaProperty) {
        this.schemaProperty = schemaProperty;
        return this;
    }

    public Function<AnnotatedType, Schema> getJsonUnwrappedHandler() {
        return this.jsonUnwrappedHandler;
    }

    public void setJsonUnwrappedHandler(Function<AnnotatedType, Schema> jsonUnwrappedHandler) {
        this.jsonUnwrappedHandler = jsonUnwrappedHandler;
    }

    public AnnotatedType jsonUnwrappedHandler(Function<AnnotatedType, Schema> jsonUnwrappedHandler) {
        this.jsonUnwrappedHandler = jsonUnwrappedHandler;
        return this;
    }

    public Schema getParent() {
        return this.parent;
    }

    public void setParent(Schema parent) {
        this.parent = parent;
    }

    public AnnotatedType parent(Schema parent) {
        this.parent = parent;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnnotatedType name(String name) {
        this.name = name;
        return this;
    }

    public Annotation[] getCtxAnnotations() {
        return this.ctxAnnotations;
    }

    public void setCtxAnnotations(Annotation[] ctxAnnotations) {
        this.ctxAnnotations = ctxAnnotations;
    }

    public AnnotatedType ctxAnnotations(Annotation[] ctxAnnotations) {
        this.setCtxAnnotations(ctxAnnotations);
        return this;
    }

    public Components getComponents() {
        return this.components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    public AnnotatedType components(Components components) {
        this.setComponents(components);
        return this;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AnnotatedType type(Type type) {
        this.setType(type);
        return this;
    }

    public JsonView getJsonViewAnnotation() {
        return this.jsonViewAnnotation;
    }

    public void setJsonViewAnnotation(JsonView jsonViewAnnotation) {
        this.jsonViewAnnotation = jsonViewAnnotation;
    }

    public AnnotatedType jsonViewAnnotation(JsonView jsonViewAnnotation) {
        this.jsonViewAnnotation = jsonViewAnnotation;
        return this;
    }

    public boolean isIncludePropertiesWithoutJSONView() {
        return this.includePropertiesWithoutJSONView;
    }

    public void setIncludePropertiesWithoutJSONView(boolean includePropertiesWithoutJSONView) {
        this.includePropertiesWithoutJSONView = includePropertiesWithoutJSONView;
    }

    public AnnotatedType includePropertiesWithoutJSONView(boolean includePropertiesWithoutJSONView) {
        this.includePropertiesWithoutJSONView = includePropertiesWithoutJSONView;
        return this;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public AnnotatedType propertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotatedType)) {
            return false;
        }
        AnnotatedType that = (AnnotatedType)o;
        if (this.type == null && that.type != null || this.type != null && that.type == null) {
            return false;
        }
        if (this.type != null && that.type != null && !this.type.equals(that.type)) {
            return false;
        }
        return Arrays.equals(this.ctxAnnotations, that.ctxAnnotations);
    }

    public int hashCode() {
        if (this.ctxAnnotations == null || this.ctxAnnotations.length == 0) {
            return Objects.hash(this.type, "fixed");
        }
        ArrayList<Annotation> meaningfulAnnotations = new ArrayList<Annotation>();
        boolean hasDifference = false;
        for (Annotation a : this.ctxAnnotations) {
            if (!a.annotationType().getName().startsWith("sun") && !a.annotationType().getName().startsWith("jdk")) {
                meaningfulAnnotations.add(a);
                continue;
            }
            hasDifference = true;
        }
        int result = 1;
        result = 31 * result + (this.type == null ? 0 : Objects.hash(this.type, "fixed"));
        result = hasDifference ? 31 * result + meaningfulAnnotations.hashCode() : 31 * result + Arrays.hashCode(this.ctxAnnotations);
        return result;
    }
}

