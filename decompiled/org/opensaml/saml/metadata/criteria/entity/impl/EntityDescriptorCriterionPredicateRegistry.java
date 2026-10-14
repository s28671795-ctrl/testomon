/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.criteria.entity.impl;

import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityDescriptorCriterionPredicateRegistry
extends CriterionPredicateRegistry<EntityDescriptor> {
    public EntityDescriptorCriterionPredicateRegistry() {
        this.loadMappings("/entitydescriptor-criterion-predicate-registry.properties");
    }
}

