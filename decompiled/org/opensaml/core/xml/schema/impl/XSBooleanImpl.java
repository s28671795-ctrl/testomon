/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBoolean;
import org.opensaml.core.xml.schema.XSBooleanValue;

public class XSBooleanImpl
extends AbstractXMLObject
implements XSBoolean {
    @Nullable
    private XSBooleanValue value;

    protected XSBooleanImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nullable
    public XSBooleanValue getValue() {
        return this.value;
    }

    @Override
    public void setValue(@Nullable XSBooleanValue newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

