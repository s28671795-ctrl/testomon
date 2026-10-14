/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.apache.xml.security.signature.XMLSignature;

public interface ContentReference {
    public void createReference(@Nonnull XMLSignature var1);
}

