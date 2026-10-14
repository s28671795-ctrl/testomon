/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.impl.AudienceRestrictionConditionImpl;

public class AudienceRestrictionConditionBuilder
extends AbstractSAMLObjectBuilder<AudienceRestrictionCondition> {
    @Override
    public AudienceRestrictionCondition buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition", "saml1");
    }

    @Override
    public AudienceRestrictionCondition buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AudienceRestrictionConditionImpl(namespaceURI, localName, namespacePrefix);
    }
}

