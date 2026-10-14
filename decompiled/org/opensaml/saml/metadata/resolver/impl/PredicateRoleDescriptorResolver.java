/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resolver.ResolverSupport;
import org.opensaml.core.criterion.SatisfyAnyCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.criteria.role.EvaluableRoleDescriptorCriterion;
import org.opensaml.saml.metadata.criteria.role.impl.RoleDescriptorCriterionPredicateRegistry;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.common.IsTimeboundSAMLObjectValidPredicate;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateRoleDescriptorResolver
extends AbstractIdentifiedInitializableComponent
implements RoleDescriptorResolver {
    private static final Predicate<XMLObject> IS_VALID_PREDICATE = new IsTimeboundSAMLObjectValidPredicate();
    private Logger log = LoggerFactory.getLogger(PredicateRoleDescriptorResolver.class);
    private boolean requireValidMetadata;
    private MetadataResolver entityDescriptorResolver;
    private boolean satisfyAnyPredicates;
    private CriterionPredicateRegistry<RoleDescriptor> criterionPredicateRegistry;
    private boolean useDefaultPredicateRegistry;
    private boolean resolveViaPredicatesOnly;

    public PredicateRoleDescriptorResolver(@Nonnull MetadataResolver mdResolver) {
        this.entityDescriptorResolver = Constraint.isNotNull(mdResolver, "Resolver for EntityDescriptors may not be null");
        this.setId(UUID.randomUUID().toString());
        this.requireValidMetadata = true;
        this.useDefaultPredicateRegistry = true;
    }

    @Override
    public boolean isRequireValidMetadata() {
        return this.requireValidMetadata;
    }

    @Override
    public void setRequireValidMetadata(boolean require) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.requireValidMetadata = require;
    }

    public boolean isSatisfyAnyPredicates() {
        return this.satisfyAnyPredicates;
    }

    public void setSatisfyAnyPredicates(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.satisfyAnyPredicates = flag;
    }

    @NonnullAfterInit
    public CriterionPredicateRegistry<RoleDescriptor> getCriterionPredicateRegistry() {
        return this.criterionPredicateRegistry;
    }

    public void setCriterionPredicateRegistry(@Nullable CriterionPredicateRegistry<RoleDescriptor> registry) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.criterionPredicateRegistry = registry;
    }

    public boolean isUseDefaultPredicateRegistry() {
        return this.useDefaultPredicateRegistry;
    }

    public void setUseDefaultPredicateRegistry(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.useDefaultPredicateRegistry = flag;
    }

    public boolean isResolveViaPredicatesOnly() {
        return this.resolveViaPredicatesOnly;
    }

    public void setResolveViaPredicatesOnly(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.resolveViaPredicatesOnly = flag;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.getCriterionPredicateRegistry() == null && this.isUseDefaultPredicateRegistry()) {
            this.setCriterionPredicateRegistry(new RoleDescriptorCriterionPredicateRegistry());
        }
    }

    @Override
    @Nullable
    public RoleDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
        Iterator<RoleDescriptor> iterator2;
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        Iterable<RoleDescriptor> iterable = this.resolve(criteria);
        if (iterable != null && (iterator2 = iterable.iterator()) != null && iterator2.hasNext()) {
            return iterator2.next();
        }
        return null;
    }

    @Override
    @Nonnull
    public Iterable<RoleDescriptor> resolve(CriteriaSet criteria) throws ResolverException {
        Predicate<XMLObject> predicate;
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        Iterable<EntityDescriptor> entityDescriptorsSource = this.entityDescriptorResolver.resolve(criteria);
        if (!entityDescriptorsSource.iterator().hasNext()) {
            this.log.debug("Resolved no EntityDescriptors via underlying MetadataResolver, returning empty collection");
            return Collections.emptySet();
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Resolved {} source EntityDescriptors", (Object)Iterables.size(entityDescriptorsSource));
        }
        Predicate<XMLObject> predicate2 = predicate = this.isRequireValidMetadata() ? IS_VALID_PREDICATE : Predicates.alwaysTrue();
        if (this.haveRoleCriteria(criteria)) {
            Iterable<RoleDescriptor> candidates = this.getCandidatesByRoleAndProtocol(entityDescriptorsSource, criteria);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Resolved {} RoleDescriptor candidates via role criteria, performing predicate filtering", (Object)Iterables.size(candidates));
            }
            return this.predicateFilterCandidates(Iterables.filter(candidates, predicate), criteria, false);
        }
        if (this.isResolveViaPredicatesOnly()) {
            Iterable<RoleDescriptor> candidates = this.getAllCandidates(entityDescriptorsSource);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Resolved {} RoleDescriptor total candidates for predicate-only resolution", (Object)Iterables.size(candidates));
            }
            return this.predicateFilterCandidates(Iterables.filter(candidates, predicate), criteria, true);
        }
        this.log.debug("Found no role criteria and predicate-only resolution is disabled, returning empty collection");
        return Collections.emptySet();
    }

    protected boolean haveRoleCriteria(@Nonnull CriteriaSet criteria) {
        return criteria.contains(EntityRoleCriterion.class);
    }

    protected Iterable<RoleDescriptor> getCandidatesByRoleAndProtocol(@Nonnull Iterable<EntityDescriptor> entityDescriptors, @Nonnull CriteriaSet criteria) {
        EntityRoleCriterion roleCriterion = Constraint.isNotNull(criteria.get(EntityRoleCriterion.class), "EntityRoleCriterion was not supplied");
        ProtocolCriterion protocolCriterion = criteria.get(ProtocolCriterion.class);
        ArrayList<List<RoleDescriptor>> aggregate = new ArrayList<List<RoleDescriptor>>();
        for (EntityDescriptor entityDescriptor : entityDescriptors) {
            if (protocolCriterion != null) {
                aggregate.add(entityDescriptor.getRoleDescriptors(roleCriterion.getRole(), protocolCriterion.getProtocol()));
                continue;
            }
            aggregate.add(entityDescriptor.getRoleDescriptors(roleCriterion.getRole()));
        }
        return Iterables.concat(aggregate);
    }

    protected Iterable<RoleDescriptor> getAllCandidates(@Nonnull Iterable<EntityDescriptor> entityDescriptors) {
        ArrayList<List<RoleDescriptor>> aggregate = new ArrayList<List<RoleDescriptor>>();
        for (EntityDescriptor entityDescriptor : entityDescriptors) {
            aggregate.add(entityDescriptor.getRoleDescriptors());
        }
        return Iterables.concat(aggregate);
    }

    protected Iterable<RoleDescriptor> predicateFilterCandidates(@Nonnull Iterable<RoleDescriptor> candidates, @Nonnull CriteriaSet criteria, boolean onEmptyPredicatesReturnEmpty) throws ResolverException {
        boolean satisfyAny;
        if (!candidates.iterator().hasNext()) {
            this.log.debug("Candidates iteration was empty, nothing to filter via predicates");
            return Collections.emptySet();
        }
        this.log.debug("Attempting to filter candidate RoleDescriptors via resolved Predicates");
        Set predicates = ResolverSupport.getPredicates(criteria, EvaluableRoleDescriptorCriterion.class, this.getCriterionPredicateRegistry());
        this.log.trace("Resolved {} Predicates: {}", (Object)predicates.size(), (Object)predicates);
        SatisfyAnyCriterion satisfyAnyCriterion = criteria.get(SatisfyAnyCriterion.class);
        if (satisfyAnyCriterion != null) {
            this.log.trace("CriteriaSet contained SatisfyAnyCriterion");
            satisfyAny = satisfyAnyCriterion.isSatisfyAny();
        } else {
            this.log.trace("CriteriaSet did NOT contain SatisfyAnyCriterion");
            satisfyAny = this.isSatisfyAnyPredicates();
        }
        this.log.trace("Effective satisyAny value: {}", (Object)satisfyAny);
        Iterable<RoleDescriptor> result = ResolverSupport.getFilteredIterable(candidates, predicates, satisfyAny, onEmptyPredicatesReturnEmpty);
        if (this.log.isDebugEnabled()) {
            this.log.debug("After predicate filtering {} RoleDescriptors remain", (Object)Iterables.size(result));
        }
        return result;
    }
}

