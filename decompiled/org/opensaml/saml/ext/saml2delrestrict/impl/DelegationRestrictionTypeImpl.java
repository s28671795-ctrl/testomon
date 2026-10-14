/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2delrestrict.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;

public class DelegationRestrictionTypeImpl
extends AbstractSAMLObject
implements DelegationRestrictionType {
    private XMLObjectChildrenList<Delegate> delegates = new XMLObjectChildrenList(this);

    protected DelegationRestrictionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Delegate> getDelegates() {
        return this.delegates;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Delegate> children = new ArrayList<Delegate>();
        children.addAll(this.delegates);
        return Collections.unmodifiableList(children);
    }
}

