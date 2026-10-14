/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;

public interface AgreementMethod
extends XMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AgreementMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
    public static final String TYPE_LOCAL_NAME = "AgreementMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethodType", "xenc");
    public static final String ALGORITHM_ATTRIBUTE_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);

    @Nullable
    public KANonce getKANonce();

    public void setKANonce(@Nullable KANonce var1);

    @Nullable
    public OriginatorKeyInfo getOriginatorKeyInfo();

    public void setOriginatorKeyInfo(@Nullable OriginatorKeyInfo var1);

    @Nullable
    public RecipientKeyInfo getRecipientKeyInfo();

    public void setRecipientKeyInfo(@Nullable RecipientKeyInfo var1);
}

