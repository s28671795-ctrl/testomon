/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver;

import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface RoleDescriptorResolver
extends Resolver<RoleDescriptor, CriteriaSet>,
IdentifiedComponent {
    public boolean isRequireValidMetadata();

    public void setRequireValidMetadata(boolean var1);
}

