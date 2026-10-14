/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.PGPKeyPacket;

public interface PGPData
extends XMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PGPData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
    public static final String TYPE_LOCAL_NAME = "PGPDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPDataType", "ds");

    @Nullable
    public PGPKeyID getPGPKeyID();

    public void setPGPKeyID(@Nullable PGPKeyID var1);

    @Nullable
    public PGPKeyPacket getPGPKeyPacket();

    public void setPGPKeyPacket(@Nullable PGPKeyPacket var1);
}

