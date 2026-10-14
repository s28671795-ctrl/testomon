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
import org.opensaml.saml.saml2.core.AudienceRestriction;

public class AudienceRestrictionImpl
extends AbstractSAMLObject
implements AudienceRestriction {
    private final XMLObjectChildrenList<Audience> audience = new XMLObjectChildrenList(this);

    protected AudienceRestrictionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Audience> getAudiences() {
        return this.audience;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Audience> children = new ArrayList<Audience>();
        children.addAll(this.audience);
        return Collections.unmodifiableList(children);
    }
}

