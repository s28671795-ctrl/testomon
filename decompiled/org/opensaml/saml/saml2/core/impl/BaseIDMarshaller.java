/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.BaseID;
import org.w3c.dom.Element;

public abstract class BaseIDMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        BaseID baseID = (BaseID)samlObject;
        if (baseID.getNameQualifier() != null) {
            domElement.setAttributeNS(null, "NameQualifier", baseID.getNameQualifier());
        }
        if (baseID.getSPNameQualifier() != null) {
            domElement.setAttributeNS(null, "SPNameQualifier", baseID.getSPNameQualifier());
        }
    }
}

