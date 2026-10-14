/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.ObjectClass;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class ObjectClassValueTranscoder
extends AbstractSchemaElementValueTranscoder<ObjectClass> {
    @Override
    public ObjectClass decodeStringValue(String value) {
        try {
            return ObjectClass.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode object class", e);
        }
    }

    @Override
    public Class<ObjectClass> getType() {
        return ObjectClass.class;
    }
}

