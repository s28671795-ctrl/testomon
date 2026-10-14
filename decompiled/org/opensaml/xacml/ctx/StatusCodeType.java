/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface StatusCodeType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusCode", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "StatusCodeType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusCodeType", "xacml-context");
    public static final String VALUE_ATTTRIB_NAME = "Value";
    public static final String SC_MISSING_ATTRIBUTE = "urn:oasis:names:tc:xacml:1.0:status:missing-attribute";
    public static final String SC_OK = "urn:oasis:names:tc:xacml:1.0:status:ok";
    public static final String SC_PROCESSING_ERROR = "urn:oasis:names:tc:xacml:1.0:status:processing-error";
    public static final String SC_SYNTAX_ERROR = "urn:oasis:names:tc:xacml:1.0:status:syntax-error";

    public StatusCodeType getStatusCode();

    public void setStatusCode(StatusCodeType var1);

    public String getValue();

    public void setValue(String var1);
}

