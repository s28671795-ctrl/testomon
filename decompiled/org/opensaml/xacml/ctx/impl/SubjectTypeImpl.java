/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class SubjectTypeImpl
extends AbstractXACMLObject
implements SubjectType {
    private String subjectCategory = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
    private XMLObjectChildrenList<AttributeType> attributes = new XMLObjectChildrenList(this);

    protected SubjectTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getSubjectCategory() {
        return this.subjectCategory;
    }

    @Override
    public void setSubjectCategory(String newSubjectCategory) {
        this.subjectCategory = this.prepareForAssignment(this.subjectCategory, newSubjectCategory);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeType> children = new ArrayList<AttributeType>();
        children.addAll(this.attributes);
        return Collections.unmodifiableList(children);
    }

    @Override
    public List<AttributeType> getAttributes() {
        return this.attributes;
    }
}

