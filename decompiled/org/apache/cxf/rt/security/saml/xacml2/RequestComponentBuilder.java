/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import java.util.List;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.xacml.XACMLObjectBuilder;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.SubjectType;

public final class RequestComponentBuilder {
    private static volatile XACMLObjectBuilder<AttributeValueType> attributeValueTypeBuilder;
    private static volatile XACMLObjectBuilder<AttributeType> attributeTypeBuilder;
    private static volatile XACMLObjectBuilder<SubjectType> subjectTypeBuilder;
    private static volatile XACMLObjectBuilder<ResourceType> resourceTypeBuilder;
    private static volatile XACMLObjectBuilder<ActionType> actionTypeBuilder;
    private static volatile XACMLObjectBuilder<EnvironmentType> environmentTypeBuilder;
    private static volatile XACMLObjectBuilder<RequestType> requestTypeBuilder;
    private static volatile XMLObjectBuilderFactory builderFactory;

    private RequestComponentBuilder() {
    }

    public static AttributeValueType createAttributeValueType(String value) {
        if (attributeValueTypeBuilder == null) {
            attributeValueTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(AttributeValueType.DEFAULT_ELEMENT_NAME);
        }
        AttributeValueType attributeValue = attributeValueTypeBuilder.buildObject();
        attributeValue.setValue(value);
        return attributeValue;
    }

    public static AttributeType createAttributeType(String attributeId, String dataType, String issuer, List<AttributeValueType> attributeValues) {
        if (attributeTypeBuilder == null) {
            attributeTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(AttributeType.DEFAULT_ELEMENT_NAME);
        }
        AttributeType attributeType = attributeTypeBuilder.buildObject();
        attributeType.setAttributeID(attributeId);
        attributeType.setDataType(dataType);
        attributeType.setIssuer(issuer);
        attributeType.getAttributeValues().addAll(attributeValues);
        return attributeType;
    }

    public static SubjectType createSubjectType(List<AttributeType> attributes, String subjectCategory) {
        if (subjectTypeBuilder == null) {
            subjectTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(SubjectType.DEFAULT_ELEMENT_NAME);
        }
        SubjectType subject = subjectTypeBuilder.buildObject();
        if (attributes != null) {
            subject.getAttributes().addAll(attributes);
        }
        subject.setSubjectCategory(subjectCategory);
        return subject;
    }

    public static ResourceType createResourceType(List<AttributeType> attributes, ResourceContentType resourceContent) {
        if (resourceTypeBuilder == null) {
            resourceTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(ResourceType.DEFAULT_ELEMENT_NAME);
        }
        ResourceType resource = resourceTypeBuilder.buildObject();
        if (attributes != null) {
            resource.getAttributes().addAll(attributes);
        }
        resource.setResourceContent(resourceContent);
        return resource;
    }

    public static ActionType createActionType(List<AttributeType> attributes) {
        if (actionTypeBuilder == null) {
            actionTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(ActionType.DEFAULT_ELEMENT_NAME);
        }
        ActionType action = actionTypeBuilder.buildObject();
        if (attributes != null) {
            action.getAttributes().addAll(attributes);
        }
        return action;
    }

    public static EnvironmentType createEnvironmentType(List<AttributeType> attributes) {
        if (environmentTypeBuilder == null) {
            environmentTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(EnvironmentType.DEFAULT_ELEMENT_NAME);
        }
        EnvironmentType enviroment = environmentTypeBuilder.buildObject();
        if (attributes != null) {
            enviroment.getAttributes().addAll(attributes);
        }
        return enviroment;
    }

    public static RequestType createRequestType(List<SubjectType> subjects, List<ResourceType> resources, ActionType action, EnvironmentType environment) {
        if (requestTypeBuilder == null) {
            requestTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(RequestType.DEFAULT_ELEMENT_NAME);
        }
        RequestType request = requestTypeBuilder.buildObject();
        request.getSubjects().addAll(subjects);
        request.getResources().addAll(resources);
        request.setAction(action);
        request.setEnvironment(environment);
        return request;
    }

    static {
        builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
    }
}

