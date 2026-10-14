/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.Transforms;

public class CipherReferenceImpl
extends AbstractXMLObject
implements CipherReference {
    private String uri;
    private Transforms transforms;

    protected CipherReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

