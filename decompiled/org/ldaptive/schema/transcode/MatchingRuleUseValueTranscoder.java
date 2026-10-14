/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.MatchingRuleUse;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class MatchingRuleUseValueTranscoder
extends AbstractSchemaElementValueTranscoder<MatchingRuleUse> {
    @Override
    public MatchingRuleUse decodeStringValue(String value) {
        try {
            return MatchingRuleUse.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode matching rule use", e);
        }
    }

    @Override
    public Class<MatchingRuleUse> getType() {
        return MatchingRuleUse.class;
    }
}

