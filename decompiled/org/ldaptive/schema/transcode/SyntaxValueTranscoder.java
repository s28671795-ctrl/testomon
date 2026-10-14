/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.Syntax;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class SyntaxValueTranscoder
extends AbstractSchemaElementValueTranscoder<Syntax> {
    @Override
    public Syntax decodeStringValue(String value) {
        try {
            return Syntax.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode attribute syntax", e);
        }
    }

    @Override
    public Class<Syntax> getType() {
        return Syntax.class;
    }
}

