/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.OrganizationURL;

public interface Organization
extends SAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Organization";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Organization", "md");
    public static final String TYPE_LOCAL_NAME = "OrganizationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationType", "md");

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public List<OrganizationName> getOrganizationNames();

    public List<OrganizationDisplayName> getDisplayNames();

    public List<OrganizationURL> getURLs();
}

