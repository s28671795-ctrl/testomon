/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.w3c.dom.Element;

public class SigningMethodMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        SigningMethod signingMethod = (SigningMethod)xmlObject;
        if (signingMethod.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", signingMethod.getAlgorithm());
        }
        if (signingMethod.getMinKeySize() != null) {
            domElement.setAttributeNS(null, "MinKeySize", signingMethod.getMinKeySize().toString());
        }
        if (signingMethod.getMaxKeySize() != null) {
            domElement.setAttributeNS(null, "MaxKeySize", signingMethod.getMaxKeySize().toString());
        }
    }
}

