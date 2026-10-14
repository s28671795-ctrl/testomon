/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.XACMLObject;

public interface IdReferenceType
extends XACMLObject,
XSString {
    public static final String POLICY_SET_ID_REFERENCE_ELEMENT_LOCAL_NAME = "PolicySetIdReference";
    public static final QName POLICY_SET_ID_REFERENCE_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetIdReference", "xacml");
    public static final String POLICY_ID_REFERENCE_ELEMENT_LOCAL_NAME = "PolicyIdReference";
    public static final QName POLICY_ID_REFERENCE_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyIdReference", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "IdReferenceType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "IdReferenceType", "xacml");
    public static final String VERSION_ATTRIB_NAME = "Version";
    public static final String EARLIEST_VERSION_ATTRIB_NAME = "EarliestVersion";
    public static final String LATEST_VERSION_ATTRIB_NAME = "LatestVersion";

    public String getVersion();

    public void setVersion(String var1);

    public String getEarliestVersion();

    public void setEarliestVersion(String var1);

    public String getLatestVersion();

    public void setLatestVersion(String var1);
}

