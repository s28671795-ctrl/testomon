/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2delrestrict.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.impl.DelegateImpl;

public class DelegateBuilder
extends AbstractSAMLObjectBuilder<Delegate> {
    @Override
    public Delegate buildObject() {
        return (Delegate)this.buildObject(Delegate.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public Delegate buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DelegateImpl(namespaceURI, localName, namespacePrefix);
    }
}

