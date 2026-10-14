/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Condition;
import org.opensaml.saml.saml1.core.Conditions;
import org.w3c.dom.Attr;

public class ConditionsUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        Conditions conditions = (Conditions)parentSAMLObject;
        if (childSAMLObject instanceof Condition) {
            conditions.getConditions().add((Condition)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        Conditions conditions = (Conditions)samlObject;
        if (attribute.getNamespaceURI() == null) {
            if ("NotBefore".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
                conditions.setNotBefore(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
            } else if ("NotOnOrAfter".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
                conditions.setNotOnOrAfter(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}

