/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionIDRequest;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeUnmarshaller;

public class AssertionIDRequestUnmarshaller
extends RequestAbstractTypeUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AssertionIDRequest idRequest = (AssertionIDRequest)parentSAMLObject;
        if (childSAMLObject instanceof AssertionIDRef) {
            idRequest.getAssertionIDRefs().add((AssertionIDRef)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}

