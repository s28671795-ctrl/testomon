/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2delrestrict;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;

public interface Delegate
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Delegate";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "Delegate", "del");
    public static final String TYPE_LOCAL_NAME = "DelegateType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "DelegateType", "del");
    public static final String DELEGATION_INSTANT_ATTRIB_NAME = "DelegationInstant";
    public static final String CONFIRMATION_METHOD_ATTRIB_NAME = "ConfirmationMethod";

    public BaseID getBaseID();

    public void setBaseID(BaseID var1);

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public DateTime getDelegationInstant();

    public void setDelegationInstant(DateTime var1);

    public String getConfirmationMethod();

    public void setConfirmationMethod(String var1);
}

