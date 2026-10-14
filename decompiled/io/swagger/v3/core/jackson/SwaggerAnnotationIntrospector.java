/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import io.swagger.v3.core.jackson.PackageVersion;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class SwaggerAnnotationIntrospector
extends AnnotationIntrospector {
    private static final long serialVersionUID = 1L;

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public Boolean hasRequiredMarker(AnnotatedMember m4) {
        XmlElement elem = m4.getAnnotation(XmlElement.class);
        if (elem != null && elem.required()) {
            return true;
        }
        JsonProperty jsonProperty = m4.getAnnotation(JsonProperty.class);
        if (jsonProperty != null && jsonProperty.required()) {
            return true;
        }
        Schema ann = m4.getAnnotation(Schema.class);
        if (ann != null && ann.required()) {
            return ann.required();
        }
        ArraySchema arraySchema = m4.getAnnotation(ArraySchema.class);
        if (arraySchema != null) {
            if (arraySchema.arraySchema().required()) {
                return arraySchema.arraySchema().required();
            }
            if (arraySchema.schema().required()) {
                return arraySchema.schema().required();
            }
        }
        return null;
    }

    @Override
    public String findPropertyDescription(Annotated a) {
        Schema model = a.getAnnotation(Schema.class);
        if (model != null && !"".equals(model.description())) {
            return model.description();
        }
        return null;
    }

    @Override
    public List<NamedType> findSubtypes(Annotated a) {
        ArraySchema arraySchema;
        Schema schema = a.getAnnotation(Schema.class);
        if (schema == null && (arraySchema = a.getAnnotation(ArraySchema.class)) != null) {
            schema = arraySchema.schema();
        }
        if (AnnotationsUtils.hasSchemaAnnotation(schema)) {
            Class<?>[] classes = schema.subTypes();
            ArrayList<NamedType> names = new ArrayList<NamedType>(classes.length);
            for (Class<?> subType : classes) {
                names.add(new NamedType(subType));
            }
            if (!names.isEmpty()) {
                return names;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String findTypeName(AnnotatedClass ac) {
        Schema mp = AnnotationsUtils.getSchemaAnnotation(ac);
        if (mp != null && !mp.name().isEmpty()) {
            return mp.name();
        }
        return null;
    }
}

