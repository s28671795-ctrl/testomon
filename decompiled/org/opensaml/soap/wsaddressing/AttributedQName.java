/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSQName;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface AttributedQName
extends XSQName,
AttributeExtensibleXMLObject,
WSAddressingObject {
    public static final String TYPE_LOCAL_NAME = "AttributedQNameType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedQNameType", "wsa");
}

