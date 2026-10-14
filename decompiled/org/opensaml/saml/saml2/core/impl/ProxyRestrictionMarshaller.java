/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.w3c.dom.Element;

public class ProxyRestrictionMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        ProxyRestriction proxyRestriction = (ProxyRestriction)samlObject;
        if (proxyRestriction.getProxyCount() != null) {
            domElement.setAttributeNS(null, "Count", Integer.toString(proxyRestriction.getProxyCount()));
        }
    }
}

