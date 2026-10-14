/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.opensaml.saml.metadata.resolver.MetadataResolver;

public interface RefreshableMetadataResolver
extends MetadataResolver {
    public void refresh() throws ResolverException;

    @Nullable
    public DateTime getLastRefresh();

    @Nullable
    public DateTime getLastUpdate();
}

