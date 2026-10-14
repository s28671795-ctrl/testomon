/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;

public class ActionMatchTypeImpl
extends AbstractXACMLObject
implements ActionMatchType {
    private AttributeValueType attributeValue;
    private IndexedXMLObjectChildrenList<XACMLObject> attributeChoice = new IndexedXMLObjectChildrenList(this);
    private String matchId;

    public ActionMatchTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public AttributeSelectorType getAttributeSelector() {
        List<XACMLObject> selectors = this.attributeChoice.subList(AttributeSelectorType.DEFAULT_ELEMENT_NAME);
        if (selectors != null && !selectors.isEmpty()) {
            return (AttributeSelectorType)selectors.get(0);
        }
        return null;
    }

    @Override
    public AttributeValueType getAttributeValue() {
        return this.attributeValue;
    }

    @Override
    public AttributeDesignatorType getActionAttributeDesignator() {
        List<XACMLObject> selectors = this.attributeChoice.subList(AttributeDesignatorType.ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME);
        if (selectors != null && !selectors.isEmpty()) {
            return (AttributeDesignatorType)selectors.get(0);
        }
        return null;
    }

    @Override
    public String getMatchId() {
        return this.matchId;
    }

    @Override
    public void setAttributeSelector(AttributeSelectorType selector) {
        AttributeSelectorType currentSelector = this.getAttributeSelector();
        if (currentSelector != null) {
            this.attributeChoice.remove(currentSelector);
        }
        this.attributeChoice.add(selector);
    }

    @Override
    public void setAttributeValue(AttributeValueType value) {
        this.attributeValue = this.prepareForAssignment(this.attributeValue, value);
    }

    @Override
    public void setActionAttributeDesignator(AttributeDesignatorType attribute) {
        AttributeDesignatorType currentDesignator = this.getActionAttributeDesignator();
        if (currentDesignator != null) {
            this.attributeChoice.remove(currentDesignator);
        }
        this.attributeChoice.add(attribute);
    }

    @Override
    public void setMatchId(String id) {
        this.matchId = this.prepareForAssignment(this.matchId, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.add(this.attributeValue);
        if (!this.attributeChoice.isEmpty()) {
            children.addAll(this.attributeChoice);
        }
        return children;
    }
}

