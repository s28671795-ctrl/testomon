/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Action;

public class ActionImpl
extends AbstractSAMLObject
implements Action {
    private String namespace;
    private String contents;

    protected ActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public void setNamespace(String ns) {
        this.namespace = this.prepareForAssignment(this.namespace, ns);
    }

    @Override
    public String getContents() {
        return this.contents;
    }

    @Override
    public void setContents(String c) {
        this.contents = this.prepareForAssignment(this.contents, c);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

