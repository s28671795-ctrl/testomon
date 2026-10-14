/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public interface AppliesTo
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject,
WSPolicyObject {
    public static final String ELEMENT_LOCAL_NAME = "AppliesTo";
    public static final QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesTo", "wsp");
}

