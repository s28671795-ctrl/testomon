/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2mdquery.AuthnQueryDescriptorType;
import org.opensaml.saml.ext.saml2mdquery.impl.QueryDescriptorTypeImpl;
import org.opensaml.saml.saml2.metadata.Endpoint;

public class AuthnQueryDescriptorTypeImpl
extends QueryDescriptorTypeImpl
implements AuthnQueryDescriptorType {
    protected AuthnQueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Endpoint> getEndpoints() {
        return new ArrayList<Endpoint>();
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        return null;
    }
}

