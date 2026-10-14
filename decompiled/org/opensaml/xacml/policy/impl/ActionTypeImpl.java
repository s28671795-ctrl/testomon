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
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.ActionType;

public class ActionTypeImpl
extends AbstractXACMLObject
implements ActionType {
    private XMLObjectChildrenList<ActionMatchType> actionMatch = new XMLObjectChildrenList(this);

    protected ActionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ActionMatchType> getActionMatches() {
        return this.actionMatch;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ActionMatchType> children = new ArrayList<ActionMatchType>();
        children.addAll(this.actionMatch);
        return Collections.unmodifiableList(children);
    }
}

