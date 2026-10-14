/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.PGPKeyPacket;

public class PGPDataImpl
extends AbstractXMLObject
implements PGPData {
    private PGPKeyID pgpKeyID;
    private PGPKeyPacket pgpKeyPacket;
    private final IndexedXMLObjectChildrenList xmlChildren = new IndexedXMLObjectChildrenList(this);

    protected PGPDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public PGPKeyID getPGPKeyID() {
        return this.pgpKeyID;
    }

    @Override
    public void setPGPKeyID(PGPKeyID newPGPKeyID) {
        this.pgpKeyID = this.prepareForAssignment(this.pgpKeyID, newPGPKeyID);
    }

    @Override
    public PGPKeyPacket getPGPKeyPacket() {
        return this.pgpKeyPacket;
    }

    @Override
    public void setPGPKeyPacket(PGPKeyPacket newPGPKeyPacket) {
        this.pgpKeyPacket = this.prepareForAssignment(this.pgpKeyPacket, newPGPKeyPacket);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.xmlChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.xmlChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XSBase64Binary> children = new ArrayList<XSBase64Binary>();
        if (this.pgpKeyID != null) {
            children.add(this.pgpKeyID);
        }
        if (this.pgpKeyPacket != null) {
            children.add(this.pgpKeyPacket);
        }
        children.addAll(this.xmlChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

