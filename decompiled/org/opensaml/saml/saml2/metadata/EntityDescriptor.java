/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;

public interface EntityDescriptor
extends SignableSAMLObject,
TimeBoundSAMLObject,
CacheableSAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EntityDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "EntityDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptorType", "md");
    public static final QName ELEMENT_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptor");
    public static final String ID_ATTRIB_NAME = "ID";
    public static final String ENTITY_ID_ATTRIB_NAME = "entityID";

    public String getEntityID();

    public void setEntityID(String var1);

    public String getID();

    public void setID(String var1);

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public List<RoleDescriptor> getRoleDescriptors();

    public List<RoleDescriptor> getRoleDescriptors(QName var1);

    public List<RoleDescriptor> getRoleDescriptors(QName var1, String var2);

    public IDPSSODescriptor getIDPSSODescriptor(String var1);

    public SPSSODescriptor getSPSSODescriptor(String var1);

    public AuthnAuthorityDescriptor getAuthnAuthorityDescriptor(String var1);

    public AttributeAuthorityDescriptor getAttributeAuthorityDescriptor(String var1);

    public PDPDescriptor getPDPDescriptor(String var1);

    public AffiliationDescriptor getAffiliationDescriptor();

    public void setAffiliationDescriptor(AffiliationDescriptor var1);

    public Organization getOrganization();

    public void setOrganization(Organization var1);

    public List<ContactPerson> getContactPersons();

    public List<AdditionalMetadataLocation> getAdditionalMetadataLocations();
}

