/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.SchemaElement;
import org.ldaptive.transcode.AbstractStringValueTranscoder;

public abstract class AbstractSchemaElementValueTranscoder<T extends SchemaElement>
extends AbstractStringValueTranscoder<T> {
    @Override
    public String encodeStringValue(T value) {
        return value.format();
    }
}

