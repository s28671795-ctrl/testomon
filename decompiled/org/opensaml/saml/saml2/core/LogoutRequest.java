/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.SessionIndex;

public interface LogoutRequest
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "LogoutRequest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequest", "saml2p");
    public static final String TYPE_LOCAL_NAME = "LogoutRequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequestType", "saml2p");
    public static final String REASON_ATTRIB_NAME = "Reason";
    public static final String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";
    public static final String USER_REASON = "urn:oasis:names:tc:SAML:2.0:logout:user";
    public static final String ADMIN_REASON = "urn:oasis:names:tc:SAML:2.0:logout:admin";
    public static final String GLOBAL_TIMEOUT_REASON = "urn:oasis:names:tc:SAML:2.0:logout:global-timeout";
    public static final String SP_TIMEOUT_REASON = "urn:oasis:names:tc:SAML:2.0:logout:sp-timeout";

    public String getReason();

    public void setReason(String var1);

    public DateTime getNotOnOrAfter();

    public void setNotOnOrAfter(DateTime var1);

    public BaseID getBaseID();

    public void setBaseID(BaseID var1);

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public List<SessionIndex> getSessionIndexes();
}

