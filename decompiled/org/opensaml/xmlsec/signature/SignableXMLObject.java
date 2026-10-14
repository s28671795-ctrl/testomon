/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.Signature;

public interface SignableXMLObject
extends XMLObject {
    public boolean isSigned();

    @Nullable
    public Signature getSignature();

    public void setSignature(@Nullable Signature var1);
}

