/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import org.ldaptive.schema.SchemaElement;
import org.ldaptive.schema.SchemaParseException;

public interface SchemaFunction {
    public <T extends SchemaElement> T parse(Class<? extends T> var1, String var2) throws SchemaParseException;
}

