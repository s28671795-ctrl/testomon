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
import org.opensaml.xacml.policy.EnvironmentMatchType;
import org.opensaml.xacml.policy.EnvironmentType;

public class EnvironmentTypeImpl
extends AbstractXACMLObject
implements EnvironmentType {
    private XMLObjectChildrenList<EnvironmentMatchType> environmentMatches = new XMLObjectChildrenList(this);

    protected EnvironmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<EnvironmentMatchType> getEnvrionmentMatches() {
        return this.environmentMatches;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<EnvironmentMatchType> children = new ArrayList<EnvironmentMatchType>();
        children.addAll(this.environmentMatches);
        return Collections.unmodifiableList(children);
    }
}

