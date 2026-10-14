/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.converter;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Iterator;

public interface ModelConverter {
    public Schema resolve(AnnotatedType var1, ModelConverterContext var2, Iterator<ModelConverter> var3);

    default public boolean isOpenapi31() {
        return false;
    }
}

