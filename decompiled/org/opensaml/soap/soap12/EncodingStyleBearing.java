/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface EncodingStyleBearing {
    public static final String SOAP12_ENCODING_STYLE_ATTR_LOCAL_NAME = "encodingStyle";
    public static final QName SOAP12_ENCODING_STYLE_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "encodingStyle", "soap12");

    @Nullable
    public String getSOAP12EncodingStyle();

    public void setSOAP12EncodingStyle(@Nullable String var1);
}

