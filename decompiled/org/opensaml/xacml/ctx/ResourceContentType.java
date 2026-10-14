/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.xacml.XACMLObject;

public interface ResourceContentType
extends XACMLObject,
ElementExtensibleXMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ResourceContent";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResourceContent", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "ResourceContentType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResourceContentType", "xacml-context");

    public String getValue();

    public void setValue(String var1);
}

