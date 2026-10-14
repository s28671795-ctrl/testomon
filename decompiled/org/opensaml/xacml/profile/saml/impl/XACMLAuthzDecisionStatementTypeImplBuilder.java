/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionStatementType;
import org.opensaml.xacml.profile.saml.impl.XACMLAuthzDecisionStatementTypeImpl;

public class XACMLAuthzDecisionStatementTypeImplBuilder
extends AbstractSAMLObjectBuilder<XACMLAuthzDecisionStatementType> {
    @Override
    public XACMLAuthzDecisionStatementType buildObject() {
        return (XACMLAuthzDecisionStatementType)this.buildObject(Statement.DEFAULT_ELEMENT_NAME, XACMLAuthzDecisionStatementType.TYPE_NAME);
    }

    @Override
    public XACMLAuthzDecisionStatementType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new XACMLAuthzDecisionStatementTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

