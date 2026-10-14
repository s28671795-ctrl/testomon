/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.EnumUtil;
import org.apache.ws.commons.schema.XmlSchemaContentProcessing;

public enum XmlSchemaContentType {
    ELEMENT_ONLY,
    EMPTY,
    MIXED,
    TEXT_ONLY;


    public static XmlSchemaContentProcessing schemaValueOf(String name) {
        return EnumUtil.valueOf(XmlSchemaContentProcessing.class, name);
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}

