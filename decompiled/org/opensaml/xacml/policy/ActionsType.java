/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ActionType;

public interface ActionsType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Actions";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Actions", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "ActionsType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ActionsType", "xacml");

    public List<ActionType> getActions();
}

