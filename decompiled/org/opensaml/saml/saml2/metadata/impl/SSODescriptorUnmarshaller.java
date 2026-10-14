/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorUnmarshaller;

public abstract class SSODescriptorUnmarshaller
extends RoleDescriptorUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentElement, XMLObject childElement) throws UnmarshallingException {
        SSODescriptor descriptor = (SSODescriptor)parentElement;
        if (childElement instanceof ArtifactResolutionService) {
            descriptor.getArtifactResolutionServices().add((ArtifactResolutionService)childElement);
        } else if (childElement instanceof SingleLogoutService) {
            descriptor.getSingleLogoutServices().add((SingleLogoutService)childElement);
        } else if (childElement instanceof ManageNameIDService) {
            descriptor.getManageNameIDServices().add((ManageNameIDService)childElement);
        } else if (childElement instanceof NameIDFormat) {
            descriptor.getNameIDFormats().add((NameIDFormat)childElement);
        } else {
            super.processChildElement(parentElement, childElement);
        }
    }
}

