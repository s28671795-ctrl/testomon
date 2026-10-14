/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NameIDPolicy;

public class NameIDPolicyImpl
extends AbstractSAMLObject
implements NameIDPolicy {
    private String format;
    private String spNameQualifier;
    private XSBooleanValue allowCreate;

    protected NameIDPolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public void setFormat(String newFormat) {
        this.format = this.prepareForAssignment(this.format, newFormat);
    }

    @Override
    public String getSPNameQualifier() {
        return this.spNameQualifier;
    }

    @Override
    public void setSPNameQualifier(String newSPNameQualifier) {
        this.spNameQualifier = this.prepareForAssignment(this.spNameQualifier, newSPNameQualifier);
    }

    @Override
    public Boolean getAllowCreate() {
        if (this.allowCreate != null) {
            return this.allowCreate.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue getAllowCreateXSBoolean() {
        return this.allowCreate;
    }

    @Override
    public void setAllowCreate(Boolean newAllowCreate) {
        this.allowCreate = newAllowCreate != null ? this.prepareForAssignment(this.allowCreate, new XSBooleanValue(newAllowCreate, false)) : (XSBooleanValue)this.prepareForAssignment(this.allowCreate, null);
    }

    @Override
    public void setAllowCreate(XSBooleanValue newAllowCreate) {
        this.allowCreate = this.prepareForAssignment(this.allowCreate, newAllowCreate);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

