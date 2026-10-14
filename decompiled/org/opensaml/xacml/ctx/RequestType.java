/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.SubjectType;

public interface RequestType
extends XACMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Request", "xacml-context");
    public static final String TYPE_LOCAL_NAME = "RequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "RequestType", "xacml-context");

    public List<SubjectType> getSubjects();

    public List<ResourceType> getResources();

    public ActionType getAction();

    public void setAction(ActionType var1);

    public EnvironmentType getEnvironment();

    public void setEnvironment(EnvironmentType var1);
}

