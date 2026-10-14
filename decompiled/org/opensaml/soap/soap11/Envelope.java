/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.common.SOAPObject;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Header;

public interface Envelope
extends SOAPObject,
ElementExtensibleXMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Envelope";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap11");
    public static final String TYPE_LOCAL_NAME = "Envelope";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap11");

    @Nullable
    public Header getHeader();

    public void setHeader(@Nullable Header var1);

    @Nullable
    public Body getBody();

    public void setBody(@Nullable Body var1);
}

