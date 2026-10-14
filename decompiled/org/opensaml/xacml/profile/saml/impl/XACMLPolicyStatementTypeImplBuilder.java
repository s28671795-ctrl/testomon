/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.xacml.profile.saml.XACMLPolicyStatementType;
import org.opensaml.xacml.profile.saml.impl.XACMLPolicyStatementTypeImpl;

public class XACMLPolicyStatementTypeImplBuilder
extends AbstractSAMLObjectBuilder<XACMLPolicyStatementType> {
    @Override
    public XACMLPolicyStatementType buildObject() {
        return (XACMLPolicyStatementType)this.buildObject(Statement.DEFAULT_ELEMENT_NAME, XACMLPolicyStatementType.TYPE_NAME);
    }

    @Override
    public XACMLPolicyStatementType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new XACMLPolicyStatementTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

