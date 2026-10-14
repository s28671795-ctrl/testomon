/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface Request
extends SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Request", "ecp");
    public static final String TYPE_LOCAL_NAME = "RequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestType", "ecp");
    public static final String PROVIDER_NAME_ATTRIB_NAME = "ProviderName";
    public static final String IS_PASSIVE_NAME_ATTRIB_NAME = "IsPassive";

    public Issuer getIssuer();

    public void setIssuer(Issuer var1);

    public IDPList getIDPList();

    public void setIDPList(IDPList var1);

    public String getProviderName();

    public void setProviderName(String var1);

    public Boolean isPassive();

    public XSBooleanValue isPassiveXSBoolean();

    public void setPassive(Boolean var1);

    public void setPassive(XSBooleanValue var1);
}

