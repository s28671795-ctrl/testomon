/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;
import org.opensaml.saml.ext.saml2mdquery.impl.ActionNamespaceImpl;

public class ActionNamespaceBuilder
extends AbstractSAMLObjectBuilder<ActionNamespace> {
    @Override
    public ActionNamespace buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ext:query", "ActionNamespace", "query");
    }

    @Override
    public ActionNamespace buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ActionNamespaceImpl(namespaceURI, localName, namespacePrefix);
    }
}

