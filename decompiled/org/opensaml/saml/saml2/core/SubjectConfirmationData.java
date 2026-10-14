/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectConfirmationData
extends SAMLObject,
ElementExtensibleXMLObject,
AttributeExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmationData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData", "saml2");
    public static final String TYPE_LOCAL_NAME = "SubjectConfirmationDataType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationDataType", "saml2");
    public static final String NOT_BEFORE_ATTRIB_NAME = "NotBefore";
    public static final String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";
    public static final String RECIPIENT_ATTRIB_NAME = "Recipient";
    public static final String IN_RESPONSE_TO_ATTRIB_NAME = "InResponseTo";
    public static final String ADDRESS_ATTRIB_NAME = "Address";

    public DateTime getNotBefore();

    public void setNotBefore(DateTime var1);

    public DateTime getNotOnOrAfter();

    public void setNotOnOrAfter(DateTime var1);

    public String getRecipient();

    public void setRecipient(String var1);

    public String getInResponseTo();

    public void setInResponseTo(String var1);

    public String getAddress();

    public void setAddress(String var1);
}

