/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public class ProxyRestrictionImpl
extends AbstractSAMLObject
implements ProxyRestriction {
    private final XMLObjectChildrenList<Audience> audiences = new XMLObjectChildrenList(this);
    private Integer proxyCount;

    protected ProxyRestrictionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Audience> getAudiences() {
        return this.audiences;
    }

    @Override
    public Integer getProxyCount() {
        return this.proxyCount;
    }

    @Override
    public void setProxyCount(Integer newProxyCount) {
        if (newProxyCount < 0) {
            throw new IllegalArgumentException("Count must be a non-negative integer.");
        }
        this.proxyCount = this.prepareForAssignment(this.proxyCount, newProxyCount);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Audience> children = new ArrayList<Audience>();
        children.addAll(this.audiences);
        return Collections.unmodifiableList(children);
    }
}

