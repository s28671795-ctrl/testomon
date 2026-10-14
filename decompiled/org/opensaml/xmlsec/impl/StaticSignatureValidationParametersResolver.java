/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.SignatureValidationParametersResolver;

public class StaticSignatureValidationParametersResolver
implements SignatureValidationParametersResolver {
    private SignatureValidationParameters params;

    public StaticSignatureValidationParametersResolver(SignatureValidationParameters parameters) {
        this.params = Constraint.isNotNull(parameters, "Parameters instance may not be null");
    }

    @Override
    @Nonnull
    public Iterable<SignatureValidationParameters> resolve(CriteriaSet criteria) throws ResolverException {
        return Collections.singleton(this.params);
    }

    @Override
    @Nonnull
    public SignatureValidationParameters resolveSingle(CriteriaSet criteria) throws ResolverException {
        return this.params;
    }
}

