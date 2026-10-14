/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public class ConditionsImpl
extends AbstractSAMLObject
implements Conditions {
    private final IndexedXMLObjectChildrenList<Condition> conditions = new IndexedXMLObjectChildrenList(this);
    private DateTime notBefore;
    private DateTime notOnOrAfter;

    protected ConditionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public List<AudienceRestriction> getAudienceRestrictions() {
        QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml2");
        return this.conditions.subList(conditionQName);
    }

    @Override
    public OneTimeUse getOneTimeUse() {
        QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
        List<Condition> list = this.conditions.subList(conditionQName);
        if (list == null || list.size() == 0) {
            return null;
        }
        return (OneTimeUse)list.get(0);
    }

    @Override
    public ProxyRestriction getProxyRestriction() {
        QName conditionQName = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction", "saml2");
        List<Condition> list = this.conditions.subList(conditionQName);
        if (list == null || list.size() == 0) {
            return null;
        }
        return (ProxyRestriction)list.get(0);
    }

    @Override
    public DateTime getNotBefore() {
        return this.notBefore;
    }

    @Override
    public void setNotBefore(DateTime newNotBefore) {
        this.notBefore = this.prepareForAssignment(this.notBefore, newNotBefore);
    }

    @Override
    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    @Override
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Condition> children = new ArrayList<Condition>();
        children.addAll(this.conditions);
        return Collections.unmodifiableList(children);
    }
}

