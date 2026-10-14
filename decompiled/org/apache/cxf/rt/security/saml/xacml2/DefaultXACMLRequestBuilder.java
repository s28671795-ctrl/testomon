/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.saml.xacml.CXFMessageParser;
import org.apache.cxf.rt.security.saml.xacml2.RequestComponentBuilder;
import org.apache.cxf.rt.security.saml.xacml2.XACMLRequestBuilder;
import org.joda.time.DateTime;
import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResourceType;
import org.opensaml.xacml.ctx.SubjectType;

public class DefaultXACMLRequestBuilder
implements XACMLRequestBuilder {
    private boolean sendDateTime = true;
    private String action = "execute";
    private boolean sendFullRequestURL = true;

    @Override
    public RequestType createRequest(Principal principal, List<String> roles, Message message) throws Exception {
        CXFMessageParser messageParser = new CXFMessageParser(message);
        String issuer = messageParser.getIssuer();
        String actionToUse = messageParser.getAction(this.action);
        SubjectType subjectType = this.createSubjectType(principal, roles, issuer);
        ResourceType resourceType = this.createResourceType(messageParser);
        AttributeType actionAttribute = this.createAttribute("urn:oasis:names:tc:xacml:1.0:action:action-id", "http://www.w3.org/2001/XMLSchema#string", null, actionToUse);
        ActionType actionType = RequestComponentBuilder.createActionType(Collections.singletonList(actionAttribute));
        return RequestComponentBuilder.createRequestType(Collections.singletonList(subjectType), Collections.singletonList(resourceType), actionType, this.createEnvironmentType());
    }

    private ResourceType createResourceType(CXFMessageParser messageParser) {
        ArrayList<AttributeType> attributes = new ArrayList<AttributeType>();
        String resourceId = null;
        boolean isSoapService = messageParser.isSOAPService();
        if (isSoapService) {
            QName serviceName = messageParser.getWSDLService();
            QName operationName = messageParser.getWSDLOperation();
            if (serviceName != null) {
                resourceId = serviceName.toString() + "#";
                resourceId = serviceName.getNamespaceURI() != null && serviceName.getNamespaceURI().equals(operationName.getNamespaceURI()) ? resourceId + operationName.getLocalPart() : resourceId + operationName.toString();
            } else {
                resourceId = operationName.toString();
            }
        } else {
            resourceId = messageParser.getResourceURI(this.sendFullRequestURL);
        }
        attributes.add(this.createAttribute("urn:oasis:names:tc:xacml:1.0:resource:resource-id", "http://www.w3.org/2001/XMLSchema#string", null, resourceId));
        if (isSoapService) {
            QName wsdlService = messageParser.getWSDLService();
            if (wsdlService != null) {
                attributes.add(this.createAttribute("urn:cxf:apache:org:wsdl:service-id", "http://www.w3.org/2001/XMLSchema#string", null, wsdlService.toString()));
            }
            QName wsdlOperation = messageParser.getWSDLOperation();
            attributes.add(this.createAttribute("urn:cxf:apache:org:wsdl:operation-id", "http://www.w3.org/2001/XMLSchema#string", null, wsdlOperation.toString()));
            String endpointURI = messageParser.getResourceURI(this.sendFullRequestURL);
            attributes.add(this.createAttribute("urn:cxf:apache:org:wsdl:endpoint", "http://www.w3.org/2001/XMLSchema#string", null, endpointURI));
        }
        return RequestComponentBuilder.createResourceType(attributes, null);
    }

    private EnvironmentType createEnvironmentType() {
        if (this.sendDateTime) {
            ArrayList<AttributeType> attributes = new ArrayList<AttributeType>();
            AttributeType environmentAttribute = this.createAttribute("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime", "http://www.w3.org/2001/XMLSchema#dateTime", null, new DateTime().toString());
            attributes.add(environmentAttribute);
            return RequestComponentBuilder.createEnvironmentType(attributes);
        }
        List<AttributeType> attributes = Collections.emptyList();
        return RequestComponentBuilder.createEnvironmentType(attributes);
    }

    private SubjectType createSubjectType(Principal principal, List<String> roles, String issuer) {
        ArrayList<AttributeType> attributes = new ArrayList<AttributeType>();
        attributes.add(this.createAttribute("urn:oasis:names:tc:xacml:1.0:subject:subject-id", "http://www.w3.org/2001/XMLSchema#string", issuer, principal.getName()));
        if (roles != null) {
            ArrayList<AttributeValueType> roleAttributes = new ArrayList<AttributeValueType>();
            for (String role : roles) {
                if (role == null) continue;
                AttributeValueType subjectRoleAttributeValue = RequestComponentBuilder.createAttributeValueType(role);
                roleAttributes.add(subjectRoleAttributeValue);
            }
            if (!roleAttributes.isEmpty()) {
                AttributeType subjectRoleAttribute = this.createAttribute("urn:oasis:names:tc:xacml:2.0:subject:role", "http://www.w3.org/2001/XMLSchema#anyURI", issuer, roleAttributes);
                attributes.add(subjectRoleAttribute);
            }
        }
        return RequestComponentBuilder.createSubjectType(attributes, null);
    }

    private AttributeType createAttribute(String id, String type, String issuer, List<AttributeValueType> values2) {
        return RequestComponentBuilder.createAttributeType(id, type, issuer, values2);
    }

    private AttributeType createAttribute(String id, String type, String issuer, String value) {
        return this.createAttribute(id, type, issuer, Collections.singletonList(RequestComponentBuilder.createAttributeValueType(value)));
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setSendDateTime(boolean sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public void setSendFullRequestURL(boolean sendFullRequestURL) {
        this.sendFullRequestURL = sendFullRequestURL;
    }
}

