/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

public interface ContactPerson
extends SAMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ContactPerson";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ContactPerson", "md");
    public static final String TYPE_LOCAL_NAME = "ContactPersonType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ContactPersonType", "md");
    public static final String CONTACT_TYPE_ATTRIB_NAME = "contactType";

    public ContactPersonTypeEnumeration getType();

    public void setType(ContactPersonTypeEnumeration var1);

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public Company getCompany();

    public void setCompany(Company var1);

    public GivenName getGivenName();

    public void setGivenName(GivenName var1);

    public SurName getSurName();

    public void setSurName(SurName var1);

    public List<EmailAddress> getEmailAddresses();

    public List<TelephoneNumber> getTelephoneNumbers();
}

