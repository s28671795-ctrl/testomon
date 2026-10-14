/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.policy.ObligationsType;

public interface ResultType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Result";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Result", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "ResultType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResultType", "xacml-context");
    public static final String RESOURCE_ID_ATTTRIB_NAME = "ResourceId";

    public DecisionType getDecision();

    public ObligationsType getObligations();

    public void setObligations(ObligationsType var1);

    public String getResourceId();

    public StatusType getStatus();

    public void setStatus(StatusType var1);

    public void setDecision(DecisionType var1);

    public void setResourceId(String var1);
}

