/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;
import org.opensaml.saml.ext.saml2mdui.impl.GeolocationHintImpl;

public class GeolocationHintBuilder
extends AbstractSAMLObjectBuilder<GeolocationHint> {
    @Override
    public GeolocationHint buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "GeolocationHint", "mdui");
    }

    @Override
    public GeolocationHint buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GeolocationHintImpl(namespaceURI, localName, namespacePrefix);
    }
}

