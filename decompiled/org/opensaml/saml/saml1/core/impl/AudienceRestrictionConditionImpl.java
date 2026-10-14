/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Audience;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;

public class AudienceRestrictionConditionImpl
extends AbstractSAMLObject
implements AudienceRestrictionCondition {
    private final XMLObjectChildrenList<Audience> audiences = new XMLObjectChildrenList(this);

    protected AudienceRestrictionConditionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Audience> getAudiences() {
        return this.audiences;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.audiences.size() == 0) {
            return null;
        }
        ArrayList<Audience> children = new ArrayList<Audience>();
        children.addAll(this.audiences);
        return Collections.unmodifiableList(children);
    }
}

