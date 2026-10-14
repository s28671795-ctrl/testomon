/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.opensaml.xacml.policy.EffectType;

public interface ObligationType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Obligation";
    public static final QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Obligation", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ObligationType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ObligationType", "xacml");
    public static final String OBLIGATION_ID_ATTRIB_NAME = "ObligationId";
    public static final String FULFILL_ON_ATTRIB_NAME = "FulfillOn";

    public List<AttributeAssignmentType> getAttributeAssignments();

    public String getObligationId();

    public void setObligationId(String var1);

    public EffectType getFulfillOn();

    public void setFulfillOn(EffectType var1);
}

