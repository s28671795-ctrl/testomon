/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface BinaryExchange
extends XSString,
AttributeExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "BinaryExchange";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchange", "wst");
    public static final String TYPE_LOCAL_NAME = "BinaryExchangeType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchangeType", "wst");
    public static final String VALUE_TYPE_ATTRIB_NAME = "ValueType";
    public static final String ENCODING_TYPE_ATTRIB_NAME = "EncodingType";

    public String getValueType();

    public void setValueType(String var1);

    public String getEncodingType();

    public void setEncodingType(String var1);
}

