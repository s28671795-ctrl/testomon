/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.policy;

import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.policy.MetadataConstants;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertionBuilder;

public class UsingAddressingAssertionBuilder
extends PrimitiveAssertionBuilder {
    private static final QName[] KNOWN_ELEMENTS = new QName[]{MetadataConstants.USING_ADDRESSING_2004_QNAME, MetadataConstants.USING_ADDRESSING_2005_QNAME, MetadataConstants.USING_ADDRESSING_2006_QNAME};

    public UsingAddressingAssertionBuilder() {
        super(KNOWN_ELEMENTS);
    }
}

