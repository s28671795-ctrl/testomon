/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface Metadata
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSAddressingObject {
    public static final String ELEMENT_LOCAL_NAME = "Metadata";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "Metadata", "wsa");
    public static final String TYPE_LOCAL_NAME = "MetadataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "MetadataType", "wsa");
}

