/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.io;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Element;

public interface Unmarshaller {
    @Nonnull
    public XMLObject unmarshall(@Nonnull Element var1) throws UnmarshallingException;
}

