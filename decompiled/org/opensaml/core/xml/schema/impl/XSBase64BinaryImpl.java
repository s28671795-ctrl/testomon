/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBase64Binary;

public class XSBase64BinaryImpl
extends AbstractXMLObject
implements XSBase64Binary {
    @Nullable
    private String value;

    protected XSBase64BinaryImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nullable
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(@Nullable String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

