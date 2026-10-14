/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;

public interface RoleDescriptor
extends SignableSAMLObject,
TimeBoundSAMLObject,
CacheableSAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RoleDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "RoleDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptorType", "md");
    public static final String ID_ATTRIB_NAME = "ID";
    public static final String PROTOCOL_ENUMERATION_ATTRIB_NAME = "protocolSupportEnumeration";
    public static final String ERROR_URL_ATTRIB_NAME = "errorURL";

    public String getID();

    public void setID(String var1);

    public List<String> getSupportedProtocols();

    public boolean isSupportedProtocol(String var1);

    public void addSupportedProtocol(String var1);

    public void removeSupportedProtocol(String var1);

    public void removeSupportedProtocols(Collection<String> var1);

    public void removeAllSupportedProtocols();

    public String getErrorURL();

    public void setErrorURL(String var1);

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public List<KeyDescriptor> getKeyDescriptors();

    public Organization getOrganization();

    public void setOrganization(Organization var1);

    public List<ContactPerson> getContactPersons();

    public List<Endpoint> getEndpoints();

    public List<Endpoint> getEndpoints(QName var1);
}

