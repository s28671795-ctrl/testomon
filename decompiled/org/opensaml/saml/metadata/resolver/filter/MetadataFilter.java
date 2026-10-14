/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter;

import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;

public interface MetadataFilter {
    @Nullable
    public XMLObject filter(@Nullable XMLObject var1) throws FilterException;
}

