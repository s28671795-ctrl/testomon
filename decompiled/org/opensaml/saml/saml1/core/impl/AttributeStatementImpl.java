/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.impl.SubjectStatementImpl;

public class AttributeStatementImpl
extends SubjectStatementImpl
implements AttributeStatement {
    private final XMLObjectChildrenList<Attribute> attributes = new XMLObjectChildrenList(this);

    protected AttributeStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(this.attributes.size() + 1);
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        list.addAll(this.attributes);
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

