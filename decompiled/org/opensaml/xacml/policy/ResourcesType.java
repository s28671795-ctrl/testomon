/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ResourceType;

public interface ResourcesType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Resources";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Resources", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ResourcesType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourcesType", "xacml");

    public List<ResourceType> getResources();
}

