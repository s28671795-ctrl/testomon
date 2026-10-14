/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.xacml.XACMLObjectBuilder;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;
import org.opensaml.xacml.profile.saml.impl.ReferencedPoliciesTypeImpl;

public class ReferencedPoliciesTypeImplBuilder
extends AbstractSAMLObjectBuilder<ReferencedPoliciesType>
implements XACMLObjectBuilder<ReferencedPoliciesType> {
    @Override
    public ReferencedPoliciesType buildObject() {
        return null;
    }

    @Override
    public ReferencedPoliciesType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ReferencedPoliciesTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

