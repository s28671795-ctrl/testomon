/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsfed.Address;
import org.opensaml.soap.wsfed.WSFedObject;

public interface EndPointReference
extends WSFedObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EndPointReference";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndPointReference", "wsa");
    public static final String TYPE_LOCAL_NAME = "EndPointReferenceType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndPointReferenceType", "wsa");

    public Address getAddress();

    public void setAddress(Address var1);
}

