/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Entropy
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Entropy";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Entropy", "wst");
    public static final String TYPE_LOCAL_NAME = "EntropyType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EntropyType", "wst");
    public static final String DIALECT_ATTRIB_NAME = "EntropyType";
}

