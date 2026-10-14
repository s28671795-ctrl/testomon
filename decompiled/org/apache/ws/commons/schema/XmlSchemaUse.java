/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.EnumUtil;

public enum XmlSchemaUse {
    NONE,
    OPTIONAL,
    PROHIBITED,
    REQUIRED;


    public static XmlSchemaUse schemaValueOf(String name) {
        return EnumUtil.valueOf(XmlSchemaUse.class, name);
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}

