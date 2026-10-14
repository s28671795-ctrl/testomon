/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface AttributedURI
extends XSURI,
AttributeExtensibleXMLObject,
WSAddressingObject {
    public static final String TYPE_LOCAL_NAME = "AttributedURIType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedURIType", "wsa");
}

