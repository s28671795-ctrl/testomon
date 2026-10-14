/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.tls;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.security.x509.tls.ClientTLSValidationParameters;

public interface ClientTLSValidationParametersResolver
extends Resolver<ClientTLSValidationParameters, CriteriaSet> {
}

