/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;
import org.opensaml.saml.ext.saml2mdquery.AuthzDecisionQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.QueryDescriptorTypeUnmarshaller;

public class AuthzDecisionQueryDescriptorTypeUnmarshaller
extends QueryDescriptorTypeUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AuthzDecisionQueryDescriptorType descriptor = (AuthzDecisionQueryDescriptorType)parentSAMLObject;
        if (childSAMLObject instanceof ActionNamespace) {
            descriptor.getActionNamespaces().add((ActionNamespace)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}

