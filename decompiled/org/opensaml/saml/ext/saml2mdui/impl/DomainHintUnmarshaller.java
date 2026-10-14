/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.DomainHint;

public class DomainHintUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        DomainHint hint = (DomainHint)samlObject;
        hint.setHint(elementContent);
    }
}

