/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface Salt
extends XSBase64Binary,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "Salt";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Salt", "wsse11");
}

