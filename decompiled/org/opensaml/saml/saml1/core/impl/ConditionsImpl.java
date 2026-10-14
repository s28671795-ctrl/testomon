/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.Condition;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.DoNotCacheCondition;

public class ConditionsImpl
extends AbstractSAMLObject
implements Conditions {
    private DateTime notBefore;
    private DateTime notOnOrAfter;
    private final IndexedXMLObjectChildrenList<Condition> conditions = new IndexedXMLObjectChildrenList(this);

    protected ConditionsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public DateTime getNotBefore() {
        return this.notBefore;
    }

    @Override
    public void setNotBefore(DateTime dt) {
        this.notBefore = this.prepareForAssignment(this.notBefore, dt);
    }

    @Override
    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    @Override
    public void setNotOnOrAfter(DateTime dt) {
        this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, dt);
    }

    @Override
    public List<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public List<Condition> getConditions(QName typeOrName) {
        return this.conditions;
    }

    @Override
    public List<AudienceRestrictionCondition> getAudienceRestrictionConditions() {
        QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition");
        return this.conditions.subList(qname);
    }

    @Override
    public List<DoNotCacheCondition> getDoNotCacheConditions() {
        QName qname = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition");
        return this.conditions.subList(qname);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.conditions.size() == 0) {
            return null;
        }
        ArrayList<Condition> children = new ArrayList<Condition>();
        children.addAll(this.conditions);
        return Collections.unmodifiableList(children);
    }
}

