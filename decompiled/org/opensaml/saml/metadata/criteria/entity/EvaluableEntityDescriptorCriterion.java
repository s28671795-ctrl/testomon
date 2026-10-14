/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.criteria.entity;

import com.google.common.base.Predicate;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public interface EvaluableEntityDescriptorCriterion
extends Predicate<EntityDescriptor>,
Criterion {
}

