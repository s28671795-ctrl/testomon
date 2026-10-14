/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

public class XSAnyImpl
extends AbstractXMLObject
implements XSAny {
    @Nonnull
    private IndexedXMLObjectChildrenList<XMLObject> unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
    @Nonnull
    private AttributeMap unknownAttributes = new AttributeMap(this);
    @Nullable
    private String textContent;

    protected XSAnyImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getTextContent() {
        return this.textContent;
    }

    @Override
    public void setTextContent(@Nullable String newContent) {
        this.textContent = this.prepareForAssignment(this.textContent, newContent);
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownXMLObjects;
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects(@Nonnull QName typeOrName) {
        return this.unknownXMLObjects.subList(typeOrName);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.unknownXMLObjects);
    }

    @Override
    @Nonnull
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }
}

