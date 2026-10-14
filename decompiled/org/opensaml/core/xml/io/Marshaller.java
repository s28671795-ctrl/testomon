/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.io;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Marshaller {
    @Nonnull
    public Element marshall(@Nonnull XMLObject var1) throws MarshallingException;

    @Nonnull
    public Element marshall(@Nonnull XMLObject var1, @Nonnull Document var2) throws MarshallingException;

    @Nonnull
    public Element marshall(@Nonnull XMLObject var1, @Nonnull Element var2) throws MarshallingException;
}

