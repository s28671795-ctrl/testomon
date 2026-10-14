/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.EmailAddress;

public class EmailAddressUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        EmailAddress address = (EmailAddress)samlObject;
        address.setAddress(elementContent);
    }
}

