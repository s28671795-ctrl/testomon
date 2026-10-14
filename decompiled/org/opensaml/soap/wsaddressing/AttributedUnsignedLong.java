/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface AttributedUnsignedLong
extends AttributeExtensibleXMLObject,
WSAddressingObject {
    public static final String TYPE_LOCAL_NAME = "AttributedUnsignedLongType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedUnsignedLongType", "wsa");

    public Long getValue();

    public void setValue(Long var1);
}

