/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractElementExtensibleXMLObject;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.util.AttributeMap;

public abstract class AbstractExtensibleXMLObject
extends AbstractElementExtensibleXMLObject
implements AttributeExtensibleXMLObject,
ElementExtensibleXMLObject {
    @Nonnull
    private final AttributeMap anyAttributes = new AttributeMap(this);

    public AbstractExtensibleXMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nonnull
    public AttributeMap getUnknownAttributes() {
        return this.anyAttributes;
    }
}

