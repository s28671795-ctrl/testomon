/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.ReferenceList;

public interface EncryptedKey
extends EncryptedType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedKey";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptedKeyType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKeyType", "xenc");
    public static final String RECIPIENT_ATTRIB_NAME = "Recipient";

    @Nullable
    public String getRecipient();

    public void setRecipient(@Nullable String var1);

    @Nullable
    public ReferenceList getReferenceList();

    public void setReferenceList(@Nullable ReferenceList var1);

    @Nullable
    public CarriedKeyName getCarriedKeyName();

    public void setCarriedKeyName(@Nullable CarriedKeyName var1);
}

