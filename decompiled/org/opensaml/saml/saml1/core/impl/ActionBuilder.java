/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.impl.ActionImpl;

public class ActionBuilder
extends AbstractSAMLObjectBuilder<Action> {
    @Override
    public Action buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "Action", "saml1");
    }

    @Override
    public Action buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ActionImpl(namespaceURI, localName, namespacePrefix);
    }
}

