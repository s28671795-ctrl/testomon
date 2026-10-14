/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.support.ContentReference;

public interface Signature
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Signature";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
    public static final String TYPE_LOCAL_NAME = "SignatureType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureType", "ds");

    @Nullable
    public String getCanonicalizationAlgorithm();

    public void setCanonicalizationAlgorithm(@Nullable String var1);

    @Nullable
    public String getSignatureAlgorithm();

    public void setSignatureAlgorithm(@Nullable String var1);

    @Nullable
    public Integer getHMACOutputLength();

    public void setHMACOutputLength(@Nullable Integer var1);

    @Nullable
    public Credential getSigningCredential();

    public void setSigningCredential(@Nullable Credential var1);

    @Nullable
    public KeyInfo getKeyInfo();

    public void setKeyInfo(@Nullable KeyInfo var1);

    @Nonnull
    public List<ContentReference> getContentReferences();
}

