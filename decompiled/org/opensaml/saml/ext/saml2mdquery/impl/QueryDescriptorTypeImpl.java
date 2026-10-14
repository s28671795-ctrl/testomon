/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorImpl;

public abstract class QueryDescriptorTypeImpl
extends RoleDescriptorImpl
implements QueryDescriptorType {
    private XSBooleanValue wantAssertionsSigned;
    private XMLObjectChildrenList<NameIDFormat> nameIDFormats = new XMLObjectChildrenList(this);

    protected QueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Boolean getWantAssertionsSigned() {
        if (this.wantAssertionsSigned != null) {
            return this.wantAssertionsSigned.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public void setWantAssertionsSigned(Boolean newWantAssertionsSigned) {
        this.wantAssertionsSigned = newWantAssertionsSigned != null ? this.prepareForAssignment(this.wantAssertionsSigned, new XSBooleanValue(newWantAssertionsSigned, false)) : (XSBooleanValue)this.prepareForAssignment(this.wantAssertionsSigned, null);
    }

    @Override
    public XSBooleanValue getWantAssertionsSignedXSBoolean() {
        return this.wantAssertionsSigned;
    }

    @Override
    public void setWantAssertionsSigned(XSBooleanValue wantAssertionSigned) {
        this.wantAssertionsSigned = this.prepareForAssignment(this.wantAssertionsSigned, wantAssertionSigned);
    }

    @Override
    public List<NameIDFormat> getNameIDFormat() {
        return this.nameIDFormats;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.nameIDFormats);
        return Collections.unmodifiableList(children);
    }
}

