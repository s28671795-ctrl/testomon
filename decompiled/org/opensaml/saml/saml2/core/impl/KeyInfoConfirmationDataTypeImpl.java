/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.KeyInfoConfirmationDataType;
import org.opensaml.saml.saml2.core.impl.SubjectConfirmationDataImpl;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyInfoConfirmationDataTypeImpl
extends SubjectConfirmationDataImpl
implements KeyInfoConfirmationDataType {
    protected KeyInfoConfirmationDataTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getKeyInfos() {
        return this.getUnknownXMLObjects(KeyInfo.DEFAULT_ELEMENT_NAME);
    }
}

