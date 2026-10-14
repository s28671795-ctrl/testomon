/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface TokenTypeBearing {
    public static final String WSSE11_TOKEN_TYPE_ATTR_LOCAL_NAME = "TokenType";
    public static final QName WSSE11_TOKEN_TYPE_ATTR_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType", "wsse11");

    public String getWSSE11TokenType();

    public void setWSSE11TokenType(String var1);
}

