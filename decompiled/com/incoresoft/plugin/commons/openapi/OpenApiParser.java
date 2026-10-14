/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.openapi;

import io.swagger.v3.jaxrs2.Reader;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

public class OpenApiParser {
    private static final Collection<Class<? extends Annotation>> defaultAnnotations = List.of(OpenAPIDefinition.class);

    public OpenAPI parse(String packagePrefix, ClassLoader classLoader) {
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackage(packagePrefix, classLoader).addClassLoaders(classLoader);
        Reflections reflections = new Reflections(configuration);
        Set<Class<?>> types = this.typesAnnotatedWith(reflections, defaultAnnotations);
        Reader reader = new Reader();
        return reader.read(types);
    }

    private Set<Class<?>> typesAnnotatedWith(Reflections reflections, Collection<Class<? extends Annotation>> annotations) {
        HashSet types = new HashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            types.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return types;
    }
}

