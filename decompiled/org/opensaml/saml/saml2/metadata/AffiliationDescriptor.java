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
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;

public interface AffiliationDescriptor
extends SignableSAMLObject,
TimeBoundSAMLObject,
CacheableSAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AffiliationDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "AffiliationDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptorType", "md");
    public static final String OWNER_ID_ATTRIB_NAME = "affiliationOwnerID";
    public static final String ID_ATTRIB_NAME = "ID";

    public String getOwnerID();

    public String getID();

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public void setOwnerID(String var1);

    public void setID(String var1);

    public List<AffiliateMember> getMembers();

    public List<KeyDescriptor> getKeyDescriptors();
}

