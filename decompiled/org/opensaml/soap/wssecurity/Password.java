/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.AttributedString;

public interface Password
extends AttributedString {
    public static final String ELEMENT_LOCAL_NAME = "Password";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password", "wsse");
    public static final String TYPE_ATTRIB_NAME = "Type";
    public static final String TYPE_PASSWORD_TEXT = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordText";
    public static final String TYPE_PASSWORD_DIGEST = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordDigest";

    public String getType();

    public void setType(String var1);
}

