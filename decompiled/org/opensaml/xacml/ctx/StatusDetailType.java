/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.xacml.XACMLObject;

public interface StatusDetailType
extends XACMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusDetail";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusDetail", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "StatusDetailType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusDetailType", "xacml-context");
}

