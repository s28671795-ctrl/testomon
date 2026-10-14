/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.DITContentRule;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class DITContentRuleValueTranscoder
extends AbstractSchemaElementValueTranscoder<DITContentRule> {
    @Override
    public DITContentRule decodeStringValue(String value) {
        try {
            return DITContentRule.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode DIT content rule", e);
        }
    }

    @Override
    public Class<DITContentRule> getType() {
        return DITContentRule.class;
    }
}

