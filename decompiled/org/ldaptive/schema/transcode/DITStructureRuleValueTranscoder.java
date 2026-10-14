/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema.transcode;

import org.ldaptive.schema.DITStructureRule;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.transcode.AbstractSchemaElementValueTranscoder;

public class DITStructureRuleValueTranscoder
extends AbstractSchemaElementValueTranscoder<DITStructureRule> {
    @Override
    public DITStructureRule decodeStringValue(String value) {
        try {
            return DITStructureRule.parse(value);
        }
        catch (SchemaParseException e) {
            throw new IllegalArgumentException("Could not transcode DIT structure rule", e);
        }
    }

    @Override
    public Class<DITStructureRule> getType() {
        return DITStructureRule.class;
    }
}

