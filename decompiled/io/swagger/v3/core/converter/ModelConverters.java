/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.converter;

import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContextImpl;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelConverters {
    private static ModelConverters SINGLETON = null;
    private static ModelConverters SINGLETON31 = null;
    static Logger LOGGER = LoggerFactory.getLogger(ModelConverters.class);
    private final List<ModelConverter> converters;
    private final Set<String> skippedPackages = new HashSet<String>();
    private final Set<String> skippedClasses = new HashSet<String>();

    public ModelConverters() {
        this.converters = new CopyOnWriteArrayList<ModelConverter>();
        this.converters.add(new ModelResolver(Json.mapper()));
    }

    public ModelConverters(boolean openapi31) {
        this.converters = new CopyOnWriteArrayList<ModelConverter>();
        if (openapi31) {
            this.converters.add(new ModelResolver(Json31.mapper()).openapi31(true));
        } else {
            this.converters.add(new ModelResolver(Json.mapper()));
        }
    }

    public static ModelConverters getInstance(boolean openapi31) {
        if (openapi31) {
            if (SINGLETON31 == null) {
                SINGLETON31 = new ModelConverters(openapi31);
                ModelConverters.init(SINGLETON31);
            }
            return SINGLETON31;
        }
        if (SINGLETON == null) {
            SINGLETON = new ModelConverters(openapi31);
            ModelConverters.init(SINGLETON);
        }
        return SINGLETON;
    }

    private static void init(ModelConverters converter) {
        converter.skippedPackages.add("java.lang");
        ServiceLoader<ModelConverter> loader = ServiceLoader.load(ModelConverter.class);
        for (ModelConverter ext : loader) {
            if (ext == null) {
                LOGGER.error("failed to load extension {}", (Object)ext);
                continue;
            }
            converter.addConverter(ext);
            LOGGER.debug("adding ModelConverter: {}", (Object)ext);
        }
    }

    public static ModelConverters getInstance() {
        return ModelConverters.getInstance(false);
    }

    public void addConverter(ModelConverter converter) {
        this.converters.add(0, converter);
    }

    public void removeConverter(ModelConverter converter) {
        this.converters.remove(converter);
    }

    public List<ModelConverter> getConverters() {
        return Collections.unmodifiableList(this.converters);
    }

    public void addPackageToSkip(String pkg) {
        this.skippedPackages.add(pkg);
    }

    public void addClassToSkip(String cls) {
        LOGGER.warn("skipping class {}", (Object)cls);
        this.skippedClasses.add(cls);
    }

    public Map<String, Schema> read(Type type) {
        return this.read(new AnnotatedType().type(type));
    }

    public Map<String, Schema> read(AnnotatedType type) {
        HashMap<String, Schema> modelMap = new HashMap<String, Schema>();
        if (this.shouldProcess(type.getType())) {
            ModelConverterContextImpl context = new ModelConverterContextImpl(this.converters);
            Schema resolve = context.resolve(type);
            for (Map.Entry<String, Schema> entry : context.getDefinedModels().entrySet()) {
                if (!entry.getValue().equals(resolve)) continue;
                modelMap.put(entry.getKey(), entry.getValue());
            }
        }
        return modelMap;
    }

    public Map<String, Schema> readAll(Type type) {
        return this.readAll(new AnnotatedType().type(type));
    }

    public Map<String, Schema> readAll(AnnotatedType type) {
        if (this.shouldProcess(type.getType())) {
            ModelConverterContextImpl context = new ModelConverterContextImpl(this.converters);
            LOGGER.debug("ModelConverters readAll from {}", (Object)type);
            context.resolve(type);
            return context.getDefinedModels();
        }
        return new HashMap<String, Schema>();
    }

    public ResolvedSchema readAllAsResolvedSchema(Type type) {
        return this.readAllAsResolvedSchema(new AnnotatedType().type(type));
    }

    public ResolvedSchema readAllAsResolvedSchema(AnnotatedType type) {
        if (this.shouldProcess(type.getType())) {
            return this.resolveAsResolvedSchema(type);
        }
        return null;
    }

    public ResolvedSchema resolveAsResolvedSchema(AnnotatedType type) {
        ModelConverterContextImpl context = new ModelConverterContextImpl(this.converters);
        ResolvedSchema resolvedSchema = new ResolvedSchema();
        resolvedSchema.schema = context.resolve(type);
        resolvedSchema.referencedSchemas = context.getDefinedModels();
        return resolvedSchema;
    }

    public boolean isRegisteredAsSkippedClass(String className) {
        return this.skippedClasses.contains(className);
    }

    private boolean shouldProcess(Type type) {
        Class<?> cls = TypeFactory.defaultInstance().constructType(type).getRawClass();
        if (cls.isPrimitive()) {
            return false;
        }
        String className = cls.getName();
        for (String packageName : this.skippedPackages) {
            if (!className.startsWith(packageName)) continue;
            return false;
        }
        return !this.skippedClasses.contains(className);
    }
}

