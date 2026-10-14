/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.w3c.dom.Element;

public class DigestMethodMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        DigestMethod digestMethod = (DigestMethod)xmlObject;
        if (digestMethod.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", digestMethod.getAlgorithm());
        }
    }
}

