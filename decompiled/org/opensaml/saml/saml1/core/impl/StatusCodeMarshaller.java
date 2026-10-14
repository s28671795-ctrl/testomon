/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.StatusCode;
import org.w3c.dom.Element;

public class StatusCodeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        StatusCode statusCode = (StatusCode)samlElement;
        QName statusValue = statusCode.getValue();
        if (statusValue != null) {
            domElement.setAttributeNS(null, "Value", QNameSupport.qnameToContentString(statusValue));
        }
    }
}

