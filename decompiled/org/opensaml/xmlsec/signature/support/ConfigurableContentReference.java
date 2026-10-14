/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.signature.support.ContentReference;

public interface ConfigurableContentReference
extends ContentReference {
    @Nullable
    public String getDigestAlgorithm();

    public void setDigestAlgorithm(@Nullable String var1);
}

