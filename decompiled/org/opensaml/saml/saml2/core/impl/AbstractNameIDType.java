/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NameIDType;

public class AbstractNameIDType
extends AbstractSAMLObject
implements NameIDType {
    private String name;
    private String nameQualifier;
    private String spNameQualifier;
    private String format;
    private String spProvidedID;

    protected AbstractNameIDType(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return this.name;
    }

    @Override
    public void setValue(String newName) {
        this.name = this.prepareForAssignment(this.name, newName);
    }

    @Override
    public String getNameQualifier() {
        return this.nameQualifier;
    }

    @Override
    public void setNameQualifier(String newNameQualifier) {
        this.nameQualifier = this.prepareForAssignment(this.nameQualifier, newNameQualifier);
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
    public String getFormat() {
        return this.format;
    }

    @Override
    public void setFormat(String newFormat) {
        this.format = this.prepareForAssignment(this.format, newFormat);
    }

    @Override
    public String getSPProvidedID() {
        return this.spProvidedID;
    }

    @Override
    public void setSPProvidedID(String newSPProvidedID) {
        this.spProvidedID = this.prepareForAssignment(this.spProvidedID, newSPProvidedID);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

