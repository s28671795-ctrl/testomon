/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.w3c.dom.Element;

public class KeyDescriptorMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        KeyDescriptor keyDescriptor = (KeyDescriptor)xmlObject;
        if (keyDescriptor.getUse() != null) {
            UsageType use = keyDescriptor.getUse();
            if (use.equals((Object)UsageType.SIGNING) || use.equals((Object)UsageType.ENCRYPTION)) {
                domElement.setAttributeNS(null, "use", use.toString().toLowerCase());
            } else if (!use.equals((Object)UsageType.UNSPECIFIED)) {
                throw new MarshallingException("KeyDescriptor had illegal value for use attribute: " + use.toString());
            }
        }
    }
}

