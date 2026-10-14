/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.xacml.XACMLObjectBuilder;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;
import org.opensaml.xacml.profile.saml.impl.XACMLAuthzDecisionQueryTypeImpl;

public class XACMLAuthzDecisionQueryTypeImplBuilder
extends AbstractSAMLObjectBuilder<XACMLAuthzDecisionQueryType>
implements XACMLObjectBuilder<XACMLAuthzDecisionQueryType> {
    @Override
    public XACMLAuthzDecisionQueryType buildObject() {
        return null;
    }

    @Override
    public XACMLAuthzDecisionQueryType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new XACMLAuthzDecisionQueryTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

