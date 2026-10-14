/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;

public interface SSODescriptor
extends RoleDescriptor {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SSODescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SSODescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "SSODescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SSODescriptorType", "md");

    public List<ArtifactResolutionService> getArtifactResolutionServices();

    public ArtifactResolutionService getDefaultArtifactResolutionService();

    public List<SingleLogoutService> getSingleLogoutServices();

    public List<ManageNameIDService> getManageNameIDServices();

    public List<NameIDFormat> getNameIDFormats();
}

