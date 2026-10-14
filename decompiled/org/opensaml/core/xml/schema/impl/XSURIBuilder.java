/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.core.xml.schema.impl.XSURIImpl;

public class XSURIBuilder
extends AbstractXMLObjectBuilder<XSURI> {
    @Override
    @Nonnull
    public XSURI buildObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String localName, @Nullable String namespacePrefix) {
        return new XSURIImpl(namespaceURI, localName, namespacePrefix);
    }
}

