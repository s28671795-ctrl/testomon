/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;

public class NameIdentifierImpl
extends AbstractSAMLObject
implements NameIdentifier {
    private String nameQualifier;
    private String format;
    private String nameIdentifier;

    protected NameIdentifierImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getNameQualifier() {
        return this.nameQualifier;
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    @Deprecated
    public String getNameIdentifier() {
        return this.getValue();
    }

    @Override
    public String getValue() {
        return this.nameIdentifier;
    }

    @Override
    public void setNameQualifier(String qualifier) {
        this.nameQualifier = this.prepareForAssignment(this.nameQualifier, qualifier);
    }

    @Override
    public void setFormat(String fmt) {
        this.format = this.prepareForAssignment(this.format, fmt);
    }

    @Override
    @Deprecated
    public void setNameIdentifier(String id) {
        this.setValue(id);
    }

    @Override
    public void setValue(String id) {
        this.nameIdentifier = this.prepareForAssignment(this.nameIdentifier, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

