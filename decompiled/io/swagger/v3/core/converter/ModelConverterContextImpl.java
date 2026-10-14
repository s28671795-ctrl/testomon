/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.converter;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.util.ReferenceTypeUtils;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelConverterContextImpl
implements ModelConverterContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelConverterContextImpl.class);
    private final List<ModelConverter> converters;
    private final Map<String, Schema> modelByName;
    private final HashMap<AnnotatedType, Schema> modelByType;
    private final Set<AnnotatedType> processedTypes;

    public ModelConverterContextImpl(List<ModelConverter> converters2) {
        this.converters = converters2;
        this.modelByName = new TreeMap<String, Schema>();
        this.modelByType = new HashMap();
        this.processedTypes = new HashSet<AnnotatedType>();
    }

    public ModelConverterContextImpl(ModelConverter converter) {
        this(new ArrayList<ModelConverter>());
        this.converters.add(converter);
    }

    @Override
    public Iterator<ModelConverter> getConverters() {
        return this.converters.iterator();
    }

    @Override
    public void defineModel(String name, Schema model) {
        AnnotatedType aType = null;
        this.defineModel(name, model, aType, null);
    }

    @Override
    public void defineModel(String name, Schema model, Type type, String prevName) {
        this.defineModel(name, model, new AnnotatedType().type(type), prevName);
    }

    @Override
    public void defineModel(String name, Schema model, AnnotatedType type, String prevName) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(String.format("defineModel %s %s", name, model));
        }
        this.modelByName.put(name, model);
        if (StringUtils.isNotBlank(prevName) && !prevName.equals(name)) {
            this.modelByName.remove(prevName);
        }
        if (type != null && type.getType() != null) {
            this.modelByType.put(type, model);
        }
    }

    @Override
    public Map<String, Schema> getDefinedModels() {
        return Collections.unmodifiableMap(this.modelByName);
    }

    @Override
    public Schema resolve(AnnotatedType type) {
        AnnotatedType aType = ReferenceTypeUtils.unwrapReference(type);
        if (aType != null) {
            return this.resolve(aType);
        }
        if (this.processedTypes.contains(type)) {
            return this.modelByType.get(type);
        }
        this.processedTypes.add(type);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("resolve %s", type.getType()));
        }
        Iterator<ModelConverter> converters2 = this.getConverters();
        Schema resolved = null;
        if (converters2.hasNext()) {
            ModelConverter converter = converters2.next();
            LOGGER.trace("trying extension {}", (Object)converter);
            resolved = converter.resolve(type, this, converters2);
        }
        if (resolved != null) {
            this.modelByType.put(type, resolved);
            Schema resolvedImpl = resolved;
            if (resolvedImpl.getName() != null) {
                this.modelByName.put(resolvedImpl.getName(), resolved);
            }
        } else {
            this.processedTypes.remove(type);
        }
        return resolved;
    }
}

