/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface RelayBearing {
    public static final String SOAP12_RELAY_ATTR_LOCAL_NAME = "relay";
    public static final QName SOAP12_RELAY_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "relay", "soap12");

    @Nullable
    public Boolean isSOAP12Relay();

    @Nullable
    public XSBooleanValue isSOAP12RelayXSBoolean();

    public void setSOAP12Relay(@Nullable Boolean var1);

    public void setSOAP12Relay(@Nullable XSBooleanValue var1);
}

