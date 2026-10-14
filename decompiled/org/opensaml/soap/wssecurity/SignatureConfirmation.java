/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface SignatureConfirmation
extends IdBearing,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "SignatureConfirmation";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation", "wsse11");
    public static final String TYPE_LOCAL_NAME = "SignatureConfirmationType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmationType", "wsse11");
    public static final String VALUE_ATTRIB_NAME = "Value";

    public String getValue();

    public void setValue(String var1);
}

