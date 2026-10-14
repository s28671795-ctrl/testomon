/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionType;
import org.opensaml.xacml.policy.ActionsType;

public class ActionsTypeImpl
extends AbstractXACMLObject
implements ActionsType {
    private XMLObjectChildrenList<ActionType> action = new XMLObjectChildrenList(this);

    protected ActionsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ActionType> getActions() {
        return this.action;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.action);
        return children;
    }
}

