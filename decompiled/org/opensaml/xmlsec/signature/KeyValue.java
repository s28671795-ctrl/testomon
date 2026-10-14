/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public interface KeyValue
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "KeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValueType", "ds");

    @Nullable
    public DSAKeyValue getDSAKeyValue();

    public void setDSAKeyValue(@Nullable DSAKeyValue var1);

    @Nullable
    public RSAKeyValue getRSAKeyValue();

    public void setRSAKeyValue(@Nullable RSAKeyValue var1);

    @Nullable
    public ECKeyValue getECKeyValue();

    public void setECKeyValue(@Nullable ECKeyValue var1);

    @Nullable
    public XMLObject getUnknownXMLObject();

    public void setUnknownXMLObject(@Nullable XMLObject var1);
}

