/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import org.ldaptive.schema.SchemaElement;
import org.ldaptive.schema.SchemaParseException;

public interface DefinitionFunction<T extends SchemaElement> {
    public T parse(String var1) throws SchemaParseException;
}

