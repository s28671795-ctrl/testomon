/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface ActorBearing {
    public static final String SOAP11_ACTOR_ATTR_LOCAL_NAME = "actor";
    public static final QName SOAP11_ACTOR_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "actor", "soap11");
    public static final String SOAP11_ACTOR_NEXT = "http://schemas.xmlsoap.org/soap/actor/next";

    @Nullable
    public String getSOAP11Actor();

    public void setSOAP11Actor(@Nullable String var1);
}

