/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.x509.PKIXValidationInformation;

public interface PKIXValidationInformationResolver
extends Resolver<PKIXValidationInformation, CriteriaSet> {
    @Nonnull
    public Set<String> resolveTrustedNames(@Nullable CriteriaSet var1) throws ResolverException;

    public boolean supportsTrustedNameResolution();
}

