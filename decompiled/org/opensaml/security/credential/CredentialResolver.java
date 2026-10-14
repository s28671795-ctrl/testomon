/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.security.credential.Credential;

public interface CredentialResolver
extends Resolver<Credential, CriteriaSet> {
}

