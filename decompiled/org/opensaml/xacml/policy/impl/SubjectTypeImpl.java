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
import org.opensaml.xacml.policy.SubjectMatchType;
import org.opensaml.xacml.policy.SubjectType;

public class SubjectTypeImpl
extends AbstractXACMLObject
implements SubjectType {
    private XMLObjectChildrenList<SubjectMatchType> subjectMatch = new XMLObjectChildrenList(this);

    protected SubjectTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<SubjectMatchType> getSubjectMatches() {
        return this.subjectMatch;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SubjectMatchType> children = new ArrayList<SubjectMatchType>();
        children.addAll(this.subjectMatch);
        return Collections.unmodifiableList(children);
    }
}

