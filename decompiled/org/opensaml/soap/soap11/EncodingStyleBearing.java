/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import java.util.List;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface EncodingStyleBearing {
    public static final String SOAP11_ENCODING_STYLE_ATTR_LOCAL_NAME = "encodingStyle";
    public static final QName SOAP11_ENCODING_STYLE_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle", "soap11");

    @Nullable
    public List<String> getSOAP11EncodingStyles();

    public void setSOAP11EncodingStyles(@Nullable List<String> var1);
}

