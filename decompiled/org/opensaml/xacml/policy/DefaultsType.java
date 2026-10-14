/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.XACMLObject;

public interface DefaultsType
extends XACMLObject {
    public static final String POLICY_SET_DEFAULTS_ELEMENT_LOCAL_NAME = "PolicySetDefaults";
    public static final QName POLICY_SET_DEFAULTS_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetDefaults", "xacml");
    public static final String POLICY_DEFAULTS_ELEMENT_LOCAL_NAME = "PolicyDefaults";
    public static final QName POLICY_DEFAULTS_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyDefaults", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "DefaultsType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "DefaultsType", "xacml");

    public XSString getXPathVersion();

    public void setXPathVersion(XSString var1);
}

