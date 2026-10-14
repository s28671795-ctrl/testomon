/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.EnumUtil;

public enum XmlSchemaContentProcessing {
    LAX,
    NONE,
    SKIP,
    STRICT;


    public static XmlSchemaContentProcessing schemaValueOf(String name) {
        return EnumUtil.valueOf(XmlSchemaContentProcessing.class, name);
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}

