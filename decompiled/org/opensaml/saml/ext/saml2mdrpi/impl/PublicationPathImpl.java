/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.Publication;
import org.opensaml.saml.ext.saml2mdrpi.PublicationPath;

public class PublicationPathImpl
extends AbstractSAMLObject
implements PublicationPath {
    private XMLObjectChildrenList<Publication> publications = new IndexedXMLObjectChildrenList<Publication>(this);

    protected PublicationPathImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Publication> getPublications() {
        return this.publications;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.publications);
        return children;
    }
}

