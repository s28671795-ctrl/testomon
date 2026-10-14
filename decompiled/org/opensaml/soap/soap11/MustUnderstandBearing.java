/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface MustUnderstandBearing {
    public static final String SOAP11_MUST_UNDERSTAND_ATTR_LOCAL_NAME = "mustUnderstand";
    public static final QName SOAP11_MUST_UNDERSTAND_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "soap11");

    @Nullable
    public Boolean isSOAP11MustUnderstand();

    @Nullable
    public XSBooleanValue isSOAP11MustUnderstandXSBoolean();

    public void setSOAP11MustUnderstand(@Nullable Boolean var1);

    public void setSOAP11MustUnderstand(@Nullable XSBooleanValue var1);
}

