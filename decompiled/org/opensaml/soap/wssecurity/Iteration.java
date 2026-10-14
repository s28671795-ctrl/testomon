/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface Iteration
extends XSInteger,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "Iteration";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Iteration", "wsse11");
    public static final String TYPE_LOCAL_NAME = "unsignedInt";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt", "xsd");
}

