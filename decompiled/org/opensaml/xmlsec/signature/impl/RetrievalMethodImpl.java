/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.Transforms;

public class RetrievalMethodImpl
extends AbstractXMLObject
implements RetrievalMethod {
    private String uri;
    private String type;
    private Transforms transforms;

    protected RetrievalMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getURI() {
        return this.uri;
    }

    @Override
    public void setURI(String newURI) {
        this.uri = this.prepareForAssignment(this.uri, newURI);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String newType) {
        this.type = this.prepareForAssignment(this.type, newType);
    }

    @Override
    public Transforms getTransforms() {
        return this.transforms;
    }

    @Override
    public void setTransforms(Transforms newTransforms) {
        this.transforms = this.prepareForAssignment(this.transforms, newTransforms);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Transforms> children = new ArrayList<Transforms>();
        if (this.transforms != null) {
            children.add(this.transforms);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

