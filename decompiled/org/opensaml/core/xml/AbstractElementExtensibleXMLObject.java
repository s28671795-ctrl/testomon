/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;

public abstract class AbstractElementExtensibleXMLObject
extends AbstractXMLObject
implements ElementExtensibleXMLObject {
    @Nonnull
    private final IndexedXMLObjectChildrenList<XMLObject> anyXMLObjects = new IndexedXMLObjectChildrenList(this);

    public AbstractElementExtensibleXMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.anyXMLObjects);
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects() {
        return this.anyXMLObjects;
    }

    @Override
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects(@Nonnull QName typeOrName) {
        return this.anyXMLObjects.subList(typeOrName);
    }
}

