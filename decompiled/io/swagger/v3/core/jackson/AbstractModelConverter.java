/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.jackson.SwaggerAnnotationIntrospector;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.core.util.ReflectionUtils;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractModelConverter
implements ModelConverter {
    protected final ObjectMapper _mapper;
    protected final AnnotationIntrospector _intr;
    protected final TypeNameResolver _typeNameResolver;
    protected Map<JavaType, String> _resolvedTypeNames = new ConcurrentHashMap<JavaType, String>();

    protected AbstractModelConverter(ObjectMapper mapper2) {
        this(mapper2, TypeNameResolver.std);
    }

    protected AbstractModelConverter(ObjectMapper mapper2, TypeNameResolver typeNameResolver) {
        mapper2.registerModule(new SimpleModule("swagger", Version.unknownVersion()){

            @Override
            public void setupModule(Module.SetupContext context) {
                context.insertAnnotationIntrospector(new SwaggerAnnotationIntrospector());
            }
        });
        this._mapper = mapper2;
        this._typeNameResolver = typeNameResolver;
        this._intr = mapper2.getSerializationConfig().getAnnotationIntrospector();
    }

    @Override
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (chain.hasNext()) {
            return chain.next().resolve(type, context, chain);
        }
        return null;
    }

    protected String _typeName(JavaType type) {
        return this._typeName(type, null);
    }

    protected String _typeName(JavaType type, BeanDescription beanDesc) {
        String name = this._resolvedTypeNames.get(type);
        if (name != null) {
            return name;
        }
        name = this._findTypeName(type, beanDesc);
        this._resolvedTypeNames.put(type, name);
        return name;
    }

    protected String _findTypeName(JavaType type, BeanDescription beanDesc) {
        PropertyName rootName;
        if (type.isArrayType()) {
            return "Array";
        }
        if (type.isMapLikeType() && ReflectionUtils.isSystemType(type)) {
            return "Map";
        }
        if (type.isContainerType() && ReflectionUtils.isSystemType(type)) {
            if (Set.class.isAssignableFrom(type.getRawClass())) {
                return "Set";
            }
            return "List";
        }
        if (beanDesc == null) {
            beanDesc = this._mapper.getSerializationConfig().introspectClassAnnotations(type);
        }
        if ((rootName = this._intr.findRootName(beanDesc.getClassInfo())) != null && rootName.hasSimpleName()) {
            return rootName.getSimpleName();
        }
        return this._typeNameResolver.nameForType(type, new TypeNameResolver.Options[0]);
    }

    protected String _typeQName(JavaType type) {
        return type.getRawClass().getName();
    }

    protected String _subTypeName(NamedType type) {
        return type.getType().getName();
    }

    protected boolean _isSetType(Class<?> cls) {
        if (cls != null) {
            if (Set.class.equals(cls)) {
                return true;
            }
            for (Class<?> a : cls.getInterfaces()) {
                if (!Set.class.equals(a) && !"interface scala.collection.Set".equals(a.toString())) continue;
                return true;
            }
        }
        return false;
    }
}

