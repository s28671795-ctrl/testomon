/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsfed.WSFedObject;

public interface Address
extends WSFedObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Address";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Address", "wsa");
    public static final String TYPE_LOCAL_NAME = "AddressType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "AddressType", "wsa");

    public String getValue();

    public void setValue(String var1);
}

