/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.criteria.role;

import com.google.common.base.Predicate;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface EvaluableRoleDescriptorCriterion
extends Predicate<RoleDescriptor>,
Criterion {
}

