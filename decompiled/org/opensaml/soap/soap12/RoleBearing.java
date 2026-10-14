/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface RoleBearing {
    public static final String SOAP12_ROLE_ATTR_LOCAL_NAME = "role";
    public static final QName SOAP12_ROLE_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "role", "soap12");

    @Nullable
    public String getSOAP12Role();

    public void setSOAP12Role(@Nullable String var1);
}

