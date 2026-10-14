/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.Transforms;

public class TransformsImpl
extends AbstractXMLObject
implements Transforms {
    private final XMLObjectChildrenList transforms = new XMLObjectChildrenList(this);

    protected TransformsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Transform> getTransforms() {
        return this.transforms;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList children = new ArrayList();
        children.addAll(this.transforms);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

