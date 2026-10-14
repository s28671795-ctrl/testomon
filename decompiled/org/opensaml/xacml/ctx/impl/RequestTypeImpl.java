/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class RequestTypeImpl
extends AbstractXACMLObject
implements RequestType {
    private XMLObjectChildrenList<SubjectType> subjects = new XMLObjectChildrenList(this);
    private XMLObjectChildrenList<ResourceType> resources = new XMLObjectChildrenList(this);
    private EnvironmentType environment;
    private ActionType action;

    protected RequestTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<SubjectType> getSubjects() {
        return this.subjects;
    }

    @Override
    public List<ResourceType> getResources() {
        return this.resources;
    }

    @Override
    public EnvironmentType getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(EnvironmentType env) {
        this.environment = this.prepareForAssignment(this.environment, env);
    }

    @Override
    public ActionType getAction() {
        return this.action;
    }

    @Override
    public void setAction(ActionType act) {
        this.action = this.prepareForAssignment(this.action, act);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        children.addAll(this.subjects);
        children.addAll(this.resources);
        if (this.action != null) {
            children.add(this.action);
        }
        if (this.environment != null) {
            children.add(this.environment);
        }
        return Collections.unmodifiableList(children);
    }
}

