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
import org.opensaml.xacml.policy.CombinerParameterType;
import org.opensaml.xacml.policy.CombinerParametersType;

public class CombinerParametersTypeImpl
extends AbstractXACMLObject
implements CombinerParametersType {
    private XMLObjectChildrenList<CombinerParameterType> combinerParameters = new XMLObjectChildrenList(this);

    protected CombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<CombinerParameterType> getCombinerParameters() {
        return this.combinerParameters;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<CombinerParameterType> children = new ArrayList<CombinerParameterType>();
        children.addAll(this.combinerParameters);
        return Collections.unmodifiableList(children);
    }
}

