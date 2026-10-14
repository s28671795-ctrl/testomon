/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.signature.support.URIContentReference;

public class DocumentInternalIDContentReference
extends URIContentReference {
    public DocumentInternalIDContentReference(@Nonnull String referenceID) {
        super("#" + referenceID);
    }
}

