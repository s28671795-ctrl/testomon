/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.ResourceContentType;

public interface ResourceType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Resource";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Resource", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "ResourceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResourceType", "xacml-context");

    public ResourceContentType getResourceContent();

    public void setResourceContent(ResourceContentType var1);

    public List<AttributeType> getAttributes();
}

