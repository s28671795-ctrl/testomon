/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.schema.XSDateTime;
import org.opensaml.core.xml.schema.impl.XSDateTimeImpl;

public class XSDateTimeBuilder
extends AbstractXMLObjectBuilder<XSDateTime> {
    @Override
    @Nonnull
    public XSDateTime buildObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String localName, @Nullable String namespacePrefix) {
        return new XSDateTimeImpl(namespaceURI, localName, namespacePrefix);
    }
}

