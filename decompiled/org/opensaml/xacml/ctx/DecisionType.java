/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface DecisionType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Decision";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Decision", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "DecisionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "DecisionType", "xacml-context");

    public DECISION getDecision();

    public void setDecision(DECISION var1);

    public static enum DECISION {
        Deny,
        Permit,
        Indeterminate,
        NotApplicable;

    }
}

