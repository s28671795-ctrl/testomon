/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAnnotated;

public abstract class XmlSchemaParticle
extends XmlSchemaAnnotated {
    public static final int DEFAULT_MAX_OCCURS = 1;
    public static final int DEFAULT_MIN_OCCURS = 1;
    private long maxOccurs = 1L;
    private long minOccurs = 1L;

    public void setMaxOccurs(long maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public long getMaxOccurs() {
        return this.maxOccurs;
    }

    public void setMinOccurs(long minOccurs) {
        this.minOccurs = minOccurs;
    }

    public long getMinOccurs() {
        return this.minOccurs;
    }
}

