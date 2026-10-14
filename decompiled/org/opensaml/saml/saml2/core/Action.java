/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Action
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Action";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Action", "saml2");
    public static final String TYPE_LOCAL_NAME = "ActionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ActionType", "saml2");
    public static final String NAMEPSACE_ATTRIB_NAME = "Namespace";
    public static final String RWEDC_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:rwedc";
    public static final String RWEDC_NEGATION_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:rwedc-negation";
    public static final String GHPP_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:ghpp";
    public static final String UNIX_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:unix";
    public static final String READ_ACTION = "Read";
    public static final String WRITE_ACTION = "Write";
    public static final String EXECUTE_ACTION = "Execute";
    public static final String DELETE_ACTION = "Delete";
    public static final String CONTROL_ACTION = "Control";
    public static final String NEG_READ_ACTION = "~Read";
    public static final String NEG_WRITE_ACTION = "~Write";
    public static final String NEG_EXECUTE_ACTION = "~Execute";
    public static final String NEG_DELETE_ACTION = "~Delete";
    public static final String NEG_CONTROL_ACTION = "~Control";
    public static final String HTTP_GET_ACTION = "GET";
    public static final String HTTP_HEAD_ACTION = "HEAD";
    public static final String HTTP_PUT_ACTION = "PUT";
    public static final String HTTP_POST_ACTION = "POST";

    public String getNamespace();

    public void setNamespace(String var1);

    public String getAction();

    public void setAction(String var1);
}

