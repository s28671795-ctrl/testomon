/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.ConfirmationMethod;

public class ConfirmationMethodUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        ConfirmationMethod confirmationMethod = (ConfirmationMethod)samlObject;
        confirmationMethod.setConfirmationMethod(elementContent);
    }
}

