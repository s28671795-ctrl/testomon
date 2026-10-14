/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.common.SOAPObject;

public abstract class AbstractExtensibleSOAPObject
extends AbstractXMLObject
implements SOAPObject,
AttributeExtensibleXMLObject,
ElementExtensibleXMLObject {
    private IndexedXMLObjectChildrenList<XMLObject> unknownXMLObjects;
    private AttributeMap attributes = new AttributeMap(this);

    protected AbstractExtensibleSOAPObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownXMLObjects = new IndexedXMLObjectChildrenList(this);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.unknownXMLObjects);
        return Collections.unmodifiableList(children);
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownXMLObjects;
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownXMLObjects.subList(typeOrName);
    }

    @Override
    @Nonnull
    public AttributeMap getUnknownAttributes() {
        return this.attributes;
    }
}

