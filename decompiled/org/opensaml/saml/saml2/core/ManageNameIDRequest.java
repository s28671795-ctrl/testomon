/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.Terminate;

public interface ManageNameIDRequest
extends RequestAbstractType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ManageNameIDRequest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequest", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ManageNameIDRequestType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequestType", "saml2p");

    public NameID getNameID();

    public void setNameID(NameID var1);

    public EncryptedID getEncryptedID();

    public void setEncryptedID(EncryptedID var1);

    public NewID getNewID();

    public void setNewID(NewID var1);

    public NewEncryptedID getNewEncryptedID();

    public void setNewEncryptedID(NewEncryptedID var1);

    public Terminate getTerminate();

    public void setTerminate(Terminate var1);
}

