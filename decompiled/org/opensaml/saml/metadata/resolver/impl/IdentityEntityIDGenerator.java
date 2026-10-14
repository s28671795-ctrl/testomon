/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;

public class IdentityEntityIDGenerator
implements Function<CriteriaSet, String> {
    @Override
    public String apply(CriteriaSet input) {
        if (input == null) {
            return null;
        }
        EntityIdCriterion entityIDCrit = input.get(EntityIdCriterion.class);
        if (entityIDCrit == null) {
            return null;
        }
        return entityIDCrit.getEntityId();
    }
}

