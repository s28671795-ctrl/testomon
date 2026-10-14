/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.converter;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

public interface ModelConverterContext {
    public void defineModel(String var1, Schema var2);

    public void defineModel(String var1, Schema var2, AnnotatedType var3, String var4);

    public void defineModel(String var1, Schema var2, Type var3, String var4);

    public Schema resolve(AnnotatedType var1);

    public Map<String, Schema> getDefinedModels();

    public Iterator<ModelConverter> getConverters();
}

