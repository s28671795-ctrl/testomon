/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.NamedCurve;

public class NamedCurveImpl
extends AbstractXMLObject
implements NamedCurve {
    private String uri;

    protected NamedCurveImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

