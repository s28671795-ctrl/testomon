/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdui.UIInfo;
import org.opensaml.saml.ext.saml2mdui.impl.UIInfoImpl;

public class UIInfoBuilder
extends AbstractSAMLObjectBuilder<UIInfo> {
    @Override
    public UIInfo buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:metadata:ui", "UIInfo", "mdui");
    }

    @Override
    public UIInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new UIInfoImpl(namespaceURI, localName, namespacePrefix);
    }
}

