/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.xacml.profile.saml.XACMLPolicyQueryType;
import org.opensaml.xacml.profile.saml.impl.XACMLPolicyQueryTypeImpl;

public class XACMLPolicyQueryTypeImplBuilder
extends AbstractSAMLObjectBuilder<XACMLPolicyQueryType> {
    @Override
    public XACMLPolicyQueryType buildObject() {
        return null;
    }

    @Override
    public XACMLPolicyQueryType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new XACMLPolicyQueryTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

