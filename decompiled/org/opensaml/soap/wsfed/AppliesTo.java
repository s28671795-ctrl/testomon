/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsfed.EndPointReference;
import org.opensaml.soap.wsfed.WSFedObject;

public interface AppliesTo
extends WSFedObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AppliesTo";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesTo", "wsp");
    public static final String TYPE_LOCAL_NAME = "AppliesToType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesToType", "wsp");

    public EndPointReference getEndPointReference();

    public void setEndPointReference(EndPointReference var1);
}

