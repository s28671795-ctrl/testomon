/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ObligationType;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationsTypeImpl
extends AbstractXACMLObject
implements ObligationsType {
    private XMLObjectChildrenList<ObligationType> obligations = new XMLObjectChildrenList(this);

    protected ObligationsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ObligationType> getObligations() {
        return this.obligations;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ObligationType> children = new ArrayList<ObligationType>();
        children.addAll(this.obligations);
        return Collections.unmodifiableList(children);
    }
}

