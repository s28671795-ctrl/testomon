/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.criteria.impl;

import com.google.common.base.Predicate;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.security.credential.Credential;

public interface EvaluableCredentialCriterion
extends Predicate<Credential>,
Criterion {
}

