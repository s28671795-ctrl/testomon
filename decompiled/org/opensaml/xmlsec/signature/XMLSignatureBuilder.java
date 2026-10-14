/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;

public interface XMLSignatureBuilder<XMLSignatureType extends XMLObject>
extends XMLObjectBuilder<XMLSignatureType> {
    @Nonnull
    public XMLSignatureType buildObject();
}

