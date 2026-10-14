/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ActionsType;
import org.opensaml.xacml.policy.EnvironmentsType;
import org.opensaml.xacml.policy.ResourcesType;
import org.opensaml.xacml.policy.SubjectsType;

public interface TargetType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Target";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Target", "xacml");
    public static final String SCHEMA_TYPE_LOCAL_NAME = "TargetType";
    public static final QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "TargetType", "xacml");

    public SubjectsType getSubjects();

    public ResourcesType getResources();

    public ActionsType getActions();

    public EnvironmentsType getEnvironments();

    public void setSubjects(SubjectsType var1);

    public void setActions(ActionsType var1);

    public void setResources(ResourcesType var1);

    public void setEnvironments(EnvironmentsType var1);
}

