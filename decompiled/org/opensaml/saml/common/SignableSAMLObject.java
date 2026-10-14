/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import javax.annotation.Nullable;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.xmlsec.signature.SignableXMLObject;

public interface SignableSAMLObject
extends SignableXMLObject,
SAMLObject {
    @Nullable
    public String getSignatureReferenceID();
}

