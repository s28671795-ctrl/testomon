/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.MatchingRule;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class MatchingRuleValueTranscoder
extends AbstractSchemaElementValueTranscoder<MatchingRule> {
    @Override
    public MatchingRule decodeStringValue(String value) {
        try {
            return MatchingRule.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode matching rule", e);
        }
    }

    @Override
    public Class<MatchingRule> getType() {
        return MatchingRule.class;
    }
}

