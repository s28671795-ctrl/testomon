/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public interface MetadataResolver
extends Resolver<EntityDescriptor, CriteriaSet>,
IdentifiedComponent {
    public boolean isRequireValidMetadata();

    public void setRequireValidMetadata(boolean var1);

    @Nullable
    public MetadataFilter getMetadataFilter();

    public void setMetadataFilter(@Nullable MetadataFilter var1);
}

