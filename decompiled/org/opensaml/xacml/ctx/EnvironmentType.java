/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.AttributeType;

public interface EnvironmentType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Environment";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Environment", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "EnvironmentType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "EnvironmentType", "xacml-context");

    public List<AttributeType> getAttributes();
}

