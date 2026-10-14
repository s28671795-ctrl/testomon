/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface MustUnderstandBearing {
    public static final String SOAP12_MUST_UNDERSTAND_ATTR_LOCAL_NAME = "mustUnderstand";
    public static final QName SOAP12_MUST_UNDERSTAND_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "mustUnderstand", "soap12");

    @Nullable
    public Boolean isSOAP12MustUnderstand();

    @Nullable
    public XSBooleanValue isSOAP12MustUnderstandXSBoolean();

    public void setSOAP12MustUnderstand(@Nullable Boolean var1);

    public void setSOAP12MustUnderstand(@Nullable XSBooleanValue var1);
}

