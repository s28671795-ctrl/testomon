/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;

public interface RegistrationInfo
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RegistrationInfo";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfo", "mdrpi");
    public static final String TYPE_LOCAL_NAME = "RegistrationInfoType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfoType", "mdrpi");
    public static final String REGISTRATION_AUTHORITY_ATTRIB_NAME = "registrationAuthority";
    public static final String REGISTRATION_INSTANT_ATTRIB_NAME = "registrationInstant";

    public String getRegistrationAuthority();

    public void setRegistrationAuthority(String var1);

    public DateTime getRegistrationInstant();

    public void setRegistrationInstant(DateTime var1);

    public List<RegistrationPolicy> getRegistrationPolicies();
}

