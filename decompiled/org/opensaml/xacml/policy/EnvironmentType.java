/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.EnvironmentMatchType;

public interface EnvironmentType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Environment";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Environment", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "EnvironmentType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentType", "xacml");

    public List<EnvironmentMatchType> getEnvrionmentMatches();
}

