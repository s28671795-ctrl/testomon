/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.AttributeType;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class AttributeTypeValueTranscoder
extends AbstractSchemaElementValueTranscoder<AttributeType> {
    @Override
    public AttributeType decodeStringValue(String value) {
        try {
            return AttributeType.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode attribute type", e);
        }
    }

    @Override
    public Class<AttributeType> getType() {
        return AttributeType.class;
    }
}

