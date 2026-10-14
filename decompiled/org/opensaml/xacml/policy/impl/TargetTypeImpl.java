/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ActionsType;
import org.opensaml.xacml.policy.EnvironmentsType;
import org.opensaml.xacml.policy.ResourcesType;
import org.opensaml.xacml.policy.SubjectsType;
import org.opensaml.xacml.policy.TargetType;

public class TargetTypeImpl
extends AbstractXACMLObject
implements TargetType {
    private ActionsType actions;
    private EnvironmentsType environments;
    private SubjectsType subjects;
    private ResourcesType resources;

    protected TargetTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.subjects != null) {
            children.add(this.subjects);
        }
        if (this.resources != null) {
            children.add(this.resources);
        }
        if (this.actions != null) {
            children.add(this.actions);
        }
        if (this.environments != null) {
            children.add(this.environments);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public SubjectsType getSubjects() {
        return this.subjects;
    }

    @Override
    public ResourcesType getResources() {
        return this.resources;
    }

    @Override
    public ActionsType getActions() {
        return this.actions;
    }

    @Override
    public EnvironmentsType getEnvironments() {
        return this.environments;
    }

    @Override
    public void setActions(ActionsType newActions) {
        this.actions = this.prepareForAssignment(this.actions, newActions);
    }

    @Override
    public void setEnvironments(EnvironmentsType newEnvironments) {
        this.environments = this.prepareForAssignment(this.environments, newEnvironments);
    }

    @Override
    public void setResources(ResourcesType newResources) {
        this.resources = this.prepareForAssignment(this.resources, newResources);
    }

    @Override
    public void setSubjects(SubjectsType newSubjects) {
        this.subjects = this.prepareForAssignment(this.subjects, newSubjects);
    }
}

