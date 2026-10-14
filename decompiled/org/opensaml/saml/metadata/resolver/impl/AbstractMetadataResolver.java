/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resolver.ResolverSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.criterion.SatisfyAnyCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.metadata.criteria.entity.EvaluableEntityDescriptorCriterion;
import org.opensaml.saml.metadata.criteria.entity.impl.EntityDescriptorCriterionPredicateRegistry;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public abstract class AbstractMetadataResolver
extends AbstractIdentifiableInitializableComponent
implements MetadataResolver {
    private final Logger log = LoggerFactory.getLogger(AbstractMetadataResolver.class);
    private UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
    private boolean requireValidMetadata = true;
    private MetadataFilter mdFilter;
    private String logPrefix;
    private boolean failFastInitialization = true;
    private EntityBackingStore entityBackingStore;
    private ParserPool parser;
    private boolean satisfyAnyPredicates;
    private CriterionPredicateRegistry<EntityDescriptor> criterionPredicateRegistry;
    private boolean useDefaultPredicateRegistry = true;

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

    @Override
    @Nullable
    public MetadataFilter getMetadataFilter() {
        return this.mdFilter;
    }

    @Override
    public void setMetadataFilter(@Nullable MetadataFilter newFilter) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.mdFilter = newFilter;
    }

    public boolean isFailFastInitialization() {
        return this.failFastInitialization;
    }

    public void setFailFastInitialization(boolean failFast) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.failFastInitialization = failFast;
    }

    @Nonnull
    public ParserPool getParserPool() {
        return this.parser;
    }

    public void setParserPool(@Nonnull ParserPool pool) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.parser = Constraint.isNotNull(pool, "ParserPool may not be null");
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
    public CriterionPredicateRegistry<EntityDescriptor> getCriterionPredicateRegistry() {
        return this.criterionPredicateRegistry;
    }

    public void setCriterionPredicateRegistry(@Nullable CriterionPredicateRegistry<EntityDescriptor> registry) {
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

    @Override
    @Nullable
    public EntityDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
        Iterator iterator2;
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        Iterable iterable = this.resolve(criteria);
        if (iterable != null && (iterator2 = iterable.iterator()) != null && iterator2.hasNext()) {
            return (EntityDescriptor)iterator2.next();
        }
        return null;
    }

    protected UnmarshallerFactory getUnmarshallerFactory() {
        return this.unmarshallerFactory;
    }

    @Override
    protected final void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        try {
            this.initMetadataResolver();
        }
        catch (ComponentInitializationException e) {
            if (this.failFastInitialization) {
                this.log.error("{} Metadata provider failed to properly initialize, fail-fast=true, halting", (Object)this.getLogPrefix(), (Object)e);
                throw e;
            }
            this.log.error("{} Metadata provider failed to properly initialize, fail-fast=false, continuing on in a degraded state", (Object)this.getLogPrefix(), (Object)e);
        }
    }

    @Override
    protected void doDestroy() {
        this.unmarshallerFactory = null;
        this.mdFilter = null;
        this.entityBackingStore = null;
        this.parser = null;
        this.criterionPredicateRegistry = null;
        super.doDestroy();
    }

    protected void initMetadataResolver() throws ComponentInitializationException {
        if (this.getCriterionPredicateRegistry() == null && this.isUseDefaultPredicateRegistry()) {
            this.setCriterionPredicateRegistry(new EntityDescriptorCriterionPredicateRegistry());
        }
    }

    @Nonnull
    protected XMLObject unmarshallMetadata(@Nonnull InputStream metadataInput) throws UnmarshallingException {
        try {
            XMLObject metadata;
            if (this.parser == null) {
                throw new UnmarshallingException("ParserPool is null, can't parse input stream");
            }
            this.log.trace("{} Parsing retrieved metadata into a DOM object", (Object)this.getLogPrefix());
            Document mdDocument = this.parser.parse(metadataInput);
            this.log.trace("{} Unmarshalling and caching metadata DOM", (Object)this.getLogPrefix());
            Unmarshaller unmarshaller = this.getUnmarshallerFactory().getUnmarshaller(mdDocument.getDocumentElement());
            if (unmarshaller == null) {
                String msg = "No unmarshaller registered for document element " + QNameSupport.getNodeQName(mdDocument.getDocumentElement());
                this.log.error("{} " + msg, (Object)this.getLogPrefix());
                throw new UnmarshallingException(msg);
            }
            XMLObject xMLObject = metadata = unmarshaller.unmarshall(mdDocument.getDocumentElement());
            return xMLObject;
        }
        catch (Exception e) {
            throw new UnmarshallingException(e);
        }
        finally {
            try {
                metadataInput.close();
            }
            catch (IOException e2) {
                this.log.debug("{} Failed to close input: {}", (Object)this.getLogPrefix(), (Object)e2);
            }
        }
    }

    @Nullable
    protected XMLObject filterMetadata(@Nullable XMLObject metadata) throws FilterException {
        if (this.getMetadataFilter() != null) {
            this.log.debug("{} Applying metadata filter", (Object)this.getLogPrefix());
            return this.getMetadataFilter().filter(metadata);
        }
        return metadata;
    }

    protected void releaseMetadataDOM(@Nullable XMLObject metadata) {
        if (metadata != null) {
            metadata.releaseDOM();
            metadata.releaseChildrenDOM(true);
        }
    }

    protected boolean isValid(@Nullable XMLObject descriptor) {
        if (descriptor == null) {
            return false;
        }
        if (!this.isRequireValidMetadata()) {
            return true;
        }
        return SAML2Support.isValid(descriptor);
    }

    @Nonnull
    @NonnullElements
    protected List<EntityDescriptor> lookupEntityID(@Nonnull @NotEmpty String entityID) throws ResolverException {
        if (!this.isInitialized()) {
            throw new ResolverException("Metadata resolver has not been initialized");
        }
        if (Strings.isNullOrEmpty(entityID)) {
            this.log.debug("{} EntityDescriptor entityID was null or empty, skipping search for it", (Object)this.getLogPrefix());
            return Collections.emptyList();
        }
        List<EntityDescriptor> descriptors = this.lookupIndexedEntityID(entityID);
        if (descriptors.isEmpty()) {
            this.log.debug("{} Metadata backing store does not contain any EntityDescriptors with the ID: {}", (Object)this.getLogPrefix(), (Object)entityID);
            return descriptors;
        }
        Iterator<EntityDescriptor> entitiesIter = descriptors.iterator();
        while (entitiesIter.hasNext()) {
            EntityDescriptor descriptor = entitiesIter.next();
            if (this.isValid(descriptor)) continue;
            this.log.warn("{} Metadata backing store contained an EntityDescriptor with the ID: {},  but it was no longer valid", (Object)this.getLogPrefix(), (Object)entityID);
            entitiesIter.remove();
        }
        return descriptors;
    }

    @Nonnull
    @NonnullElements
    protected List<EntityDescriptor> lookupIndexedEntityID(@Nonnull @NotEmpty String entityID) {
        List<EntityDescriptor> descriptors = this.getBackingStore().getIndexedDescriptors().get(entityID);
        if (descriptors != null) {
            return new ArrayList<EntityDescriptor>(descriptors);
        }
        return Collections.emptyList();
    }

    @Nonnull
    protected EntityBackingStore createNewBackingStore() {
        return new EntityBackingStore();
    }

    @Nonnull
    protected EntityBackingStore getBackingStore() {
        return this.entityBackingStore;
    }

    protected void setBackingStore(@Nonnull EntityBackingStore newBackingStore) {
        this.entityBackingStore = Constraint.isNotNull(newBackingStore, "EntityBackingStore may not be null");
    }

    protected void preProcessEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull EntityBackingStore backingStore) {
        backingStore.getOrderedDescriptors().add(entityDescriptor);
        this.indexEntityDescriptor(entityDescriptor, backingStore);
    }

    protected void removeByEntityID(@Nonnull String entityID, @Nonnull EntityBackingStore backingStore) {
        Map<String, List<EntityDescriptor>> indexedDescriptors = backingStore.getIndexedDescriptors();
        List<EntityDescriptor> descriptors = indexedDescriptors.get(entityID);
        if (descriptors != null) {
            backingStore.getOrderedDescriptors().removeAll(descriptors);
        }
        indexedDescriptors.remove(entityID);
    }

    protected void indexEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull EntityBackingStore backingStore) {
        String entityID = StringSupport.trimOrNull(entityDescriptor.getEntityID());
        if (entityID != null) {
            List<EntityDescriptor> entities = backingStore.getIndexedDescriptors().get(entityID);
            if (entities == null) {
                entities = new ArrayList<EntityDescriptor>();
                backingStore.getIndexedDescriptors().put(entityID, entities);
            } else if (!entities.isEmpty()) {
                this.log.warn("{} Detected duplicate EntityDescriptor for entityID: {}", (Object)this.getLogPrefix(), (Object)entityID);
            }
            entities.add(entityDescriptor);
        }
    }

    protected void preProcessEntitiesDescriptor(@Nonnull EntitiesDescriptor entitiesDescriptor, EntityBackingStore backingStore) {
        for (XMLObject child : entitiesDescriptor.getOrderedChildren()) {
            if (child instanceof EntityDescriptor) {
                this.preProcessEntityDescriptor((EntityDescriptor)child, backingStore);
                continue;
            }
            if (!(child instanceof EntitiesDescriptor)) continue;
            this.preProcessEntitiesDescriptor((EntitiesDescriptor)child, backingStore);
        }
    }

    protected Iterable<EntityDescriptor> predicateFilterCandidates(@Nonnull Iterable<EntityDescriptor> candidates, @Nonnull CriteriaSet criteria, boolean onEmptyPredicatesReturnEmpty) throws ResolverException {
        boolean satisfyAny;
        if (!candidates.iterator().hasNext()) {
            this.log.debug("{} Candidates iteration was empty, nothing to filter via predicates", (Object)this.getLogPrefix());
            return Collections.emptySet();
        }
        this.log.debug("{} Attempting to filter candidate EntityDescriptors via resolved Predicates", (Object)this.getLogPrefix());
        Set predicates = ResolverSupport.getPredicates(criteria, EvaluableEntityDescriptorCriterion.class, this.getCriterionPredicateRegistry());
        this.log.trace("{} Resolved {} Predicates: {}", this.getLogPrefix(), predicates.size(), predicates);
        SatisfyAnyCriterion satisfyAnyCriterion = criteria.get(SatisfyAnyCriterion.class);
        if (satisfyAnyCriterion != null) {
            this.log.trace("{} CriteriaSet contained SatisfyAnyCriterion", (Object)this.getLogPrefix());
            satisfyAny = satisfyAnyCriterion.isSatisfyAny();
        } else {
            this.log.trace("{} CriteriaSet did NOT contain SatisfyAnyCriterion", (Object)this.getLogPrefix());
            satisfyAny = this.isSatisfyAnyPredicates();
        }
        this.log.trace("{} Effective satisyAny value: {}", (Object)this.getLogPrefix(), (Object)satisfyAny);
        Iterable<EntityDescriptor> result = ResolverSupport.getFilteredIterable(candidates, predicates, satisfyAny, onEmptyPredicatesReturnEmpty);
        if (this.log.isDebugEnabled()) {
            this.log.debug("{} After predicate filtering {} EntityDescriptors remain", (Object)this.getLogPrefix(), (Object)Iterables.size(result));
        }
        return result;
    }

    @Nonnull
    @NotEmpty
    protected String getLogPrefix() {
        if (this.logPrefix == null) {
            this.logPrefix = String.format("Metadata Resolver %s %s:", this.getClass().getSimpleName(), this.getId());
        }
        return this.logPrefix;
    }

    protected class EntityBackingStore {
        private Map<String, List<EntityDescriptor>> indexedDescriptors = new ConcurrentHashMap<String, List<EntityDescriptor>>();
        private List<EntityDescriptor> orderedDescriptors = new ArrayList<EntityDescriptor>();

        protected EntityBackingStore() {
        }

        @Nonnull
        public Map<String, List<EntityDescriptor>> getIndexedDescriptors() {
            return this.indexedDescriptors;
        }

        @Nonnull
        public List<EntityDescriptor> getOrderedDescriptors() {
            return this.orderedDescriptors;
        }
    }
}

