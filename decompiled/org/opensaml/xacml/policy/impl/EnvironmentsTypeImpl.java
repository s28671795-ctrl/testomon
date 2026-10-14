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
import org.opensaml.xacml.policy.EnvironmentType;
import org.opensaml.xacml.policy.EnvironmentsType;

public class EnvironmentsTypeImpl
extends AbstractXACMLObject
implements EnvironmentsType {
    private XMLObjectChildrenList<EnvironmentType> environments = new XMLObjectChildrenList(this);

    protected EnvironmentsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<EnvironmentType> getEnvironments() {
        return this.environments;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<EnvironmentType> children = new ArrayList<EnvironmentType>();
        children.addAll(this.environments);
        return Collections.unmodifiableList(children);
    }
}

