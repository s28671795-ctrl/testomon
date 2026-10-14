/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.criteria.role.impl;

import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class RoleDescriptorCriterionPredicateRegistry
extends CriterionPredicateRegistry<RoleDescriptor> {
    public RoleDescriptorCriterionPredicateRegistry() {
        this.loadMappings("/roledescriptor-criterion-predicate-registry.properties");
    }
}

