/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.NameForm;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class NameFormValueTranscoder
extends AbstractSchemaElementValueTranscoder<NameForm> {
    @Override
    public NameForm decodeStringValue(String value) {
        try {
            return NameForm.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode name form", e);
        }
    }

    @Override
    public Class<NameForm> getType() {
        return NameForm.class;
    }
}

