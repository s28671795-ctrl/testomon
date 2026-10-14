/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;
import org.opensaml.xacml.policy.impl.AttributeDesignatorTypeImpl;

public class SubjectAttributeDesignatorTypeImpl
extends AttributeDesignatorTypeImpl
implements SubjectAttributeDesignatorType {
    private String subjectCategory;

    protected SubjectAttributeDesignatorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getSubjectCategory() {
        return this.subjectCategory;
    }

    @Override
    public void setSubjectCategory(String category) {
        this.subjectCategory = this.prepareForAssignment(this.subjectCategory, category);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return new LazyList<XMLObject>();
    }
}

