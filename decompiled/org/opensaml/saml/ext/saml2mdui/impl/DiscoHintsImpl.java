/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.DiscoHints;
import org.opensaml.saml.ext.saml2mdui.DomainHint;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public class DiscoHintsImpl
extends AbstractSAMLObject
implements DiscoHints {
    private final IndexedXMLObjectChildrenList<XMLObject> discoHintsChildren = new IndexedXMLObjectChildrenList(this);

    protected DiscoHintsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getXMLObjects() {
        return this.discoHintsChildren;
    }

    @Override
    public List<XMLObject> getXMLObjects(QName typeOrName) {
        return this.discoHintsChildren.subList(typeOrName);
    }

    @Override
    public List<DomainHint> getDomainHints() {
        return this.discoHintsChildren.subList(DomainHint.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<GeolocationHint> getGeolocationHints() {
        return this.discoHintsChildren.subList(GeolocationHint.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<IPHint> getIPHints() {
        return this.discoHintsChildren.subList(IPHint.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.discoHintsChildren);
        return children;
    }
}

