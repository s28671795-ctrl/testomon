/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.tls.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.tls.CertificateNameOptions;
import org.opensaml.security.x509.tls.ClientTLSValidationConfiguration;
import org.opensaml.security.x509.tls.ClientTLSValidationConfigurationCriterion;
import org.opensaml.security.x509.tls.ClientTLSValidationParameters;
import org.opensaml.security.x509.tls.ClientTLSValidationParametersResolver;

public class BasicClientTLSValidationParametersResolver
implements ClientTLSValidationParametersResolver {
    @Override
    @Nonnull
    @NonnullElements
    public Iterable<ClientTLSValidationParameters> resolve(CriteriaSet criteria) throws ResolverException {
        ClientTLSValidationParameters params = this.resolveSingle(criteria);
        if (params != null) {
            return Collections.singletonList(params);
        }
        return Collections.emptyList();
    }

    @Override
    @Nonnull
    public ClientTLSValidationParameters resolveSingle(CriteriaSet criteria) throws ResolverException {
        Constraint.isNotNull(criteria, "CriteriaSet was null");
        Constraint.isNotNull(criteria.get(ClientTLSValidationConfigurationCriterion.class), "Resolver requires an instance of ClientTLSValidationConfigurationCriterion");
        ClientTLSValidationParameters params = new ClientTLSValidationParameters();
        params.setX509TrustEngine(this.resolveTrustEngine(criteria));
        params.setCertificateNameOptions(this.resolveNameOptions(criteria));
        return params;
    }

    @Nullable
    protected TrustEngine<? super X509Credential> resolveTrustEngine(@Nonnull CriteriaSet criteria) {
        for (ClientTLSValidationConfiguration config : criteria.get(ClientTLSValidationConfigurationCriterion.class).getConfigurations()) {
            if (config.getX509TrustEngine() == null) continue;
            return config.getX509TrustEngine();
        }
        return null;
    }

    @Nullable
    protected CertificateNameOptions resolveNameOptions(@Nonnull CriteriaSet criteria) {
        for (ClientTLSValidationConfiguration config : criteria.get(ClientTLSValidationConfigurationCriterion.class).getConfigurations()) {
            if (config.getCertificateNameOptions() == null) continue;
            return config.getCertificateNameOptions();
        }
        return null;
    }
}

