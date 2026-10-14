/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.EnumUtil;

public enum XmlSchemaForm {
    NONE,
    QUALIFIED,
    UNQUALIFIED;


    public static XmlSchemaForm schemaValueOf(String name) {
        return EnumUtil.valueOf(XmlSchemaForm.class, name);
    }

    public String toString() {
        switch (this) {
            case QUALIFIED: {
                return "qualified";
            }
            case UNQUALIFIED: {
                return "unqualified";
            }
        }
        return "none";
    }
}

