/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.SubjectType;
import org.opensaml.xacml.policy.SubjectsType;

public class SubjectsTypeImpl
extends AbstractXACMLObject
implements SubjectsType {
    private XMLObjectChildrenList<SubjectType> subject = new XMLObjectChildrenList(this);

    protected SubjectsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<SubjectType> getSubjects() {
        return this.subject;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.subject);
        return children;
    }
}

