/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;

public interface XMLEncryptionBuilder<XMLEncryptionType extends XMLObject>
extends XMLObjectBuilder<XMLEncryptionType> {
    @Nonnull
    public XMLEncryptionType buildObject();
}

