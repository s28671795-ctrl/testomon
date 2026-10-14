/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;

public interface ResourceMatchType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ResourceMatch";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceMatch", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ResourceMatchType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceMatchType", "xacml");
    public static final String MATCH_ID_ATTRIB_NAME = "MatchId";

    public AttributeValueType getAttributeValue();

    public void setAttributeValue(AttributeValueType var1);

    public AttributeDesignatorType getResourceAttributeDesignator();

    public void setResourceAttributeDesignator(AttributeDesignatorType var1);

    public AttributeSelectorType getAttributeSelector();

    public void setAttributeSelector(AttributeSelectorType var1);

    public String getMatchId();

    public void setMatchId(String var1);
}

