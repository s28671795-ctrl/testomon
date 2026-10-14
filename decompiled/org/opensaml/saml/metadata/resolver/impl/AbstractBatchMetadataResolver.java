/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.IterableMetadataSource;
import org.opensaml.saml.metadata.resolver.BatchMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.impl.MetadataIndexManager;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBatchMetadataResolver
extends AbstractMetadataResolver
implements BatchMetadataResolver,
IterableMetadataSource {
    private final Logger log = LoggerFactory.getLogger(AbstractBatchMetadataResolver.class);
    private boolean cacheSourceMetadata;
    private Set<MetadataIndex> indexes = Collections.emptySet();
    private boolean resolveViaPredicatesOnly;

    public AbstractBatchMetadataResolver() {
        this.setCacheSourceMetadata(true);
    }

    @Override
    public Iterator<EntityDescriptor> iterator() {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        return Collections.unmodifiableList(this.getBackingStore().getOrderedDescriptors()).iterator();
    }

    protected boolean isCacheSourceMetadata() {
        return this.cacheSourceMetadata;
    }

    protected void setCacheSourceMetadata(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.cacheSourceMetadata = flag;
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndex> getIndexes() {
        return ImmutableSet.copyOf(this.indexes);
    }

    public void setIndexes(@Nullable Set<MetadataIndex> newIndexes) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        if (newIndexes == null) {
            this.indexes = Collections.emptySet();
        } else {
            this.indexes = new HashSet<MetadataIndex>();
            this.indexes.addAll(Collections2.filter(newIndexes, Predicates.notNull()));
        }
    }

    public boolean isResolveViaPredicatesOnly() {
        return this.resolveViaPredicatesOnly;
    }

    public void setResolveViaPredicatesOnly(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.resolveViaPredicatesOnly = flag;
    }

    @Override
    @Nonnull
    public Iterable<EntityDescriptor> resolve(CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        EntityIdCriterion entityIdCriterion = criteria.get(EntityIdCriterion.class);
        if (entityIdCriterion != null) {
            List<EntityDescriptor> entityIdcandidates = this.lookupEntityID(entityIdCriterion.getEntityId());
            if (this.log.isDebugEnabled()) {
                this.log.debug("{} Resolved {} candidates via EntityIdCriterion: {}", this.getLogPrefix(), Iterables.size(entityIdcandidates), entityIdCriterion);
            }
            return this.predicateFilterCandidates(entityIdcandidates, criteria, false);
        }
        Optional<Set<EntityDescriptor>> indexedCandidates = this.lookupByIndexes(criteria);
        if (this.log.isDebugEnabled()) {
            if (indexedCandidates.isPresent()) {
                this.log.debug("{} Resolved {} candidates via secondary index lookup", (Object)this.getLogPrefix(), (Object)Iterables.size((Iterable)indexedCandidates.get()));
            } else {
                this.log.debug("{} Resolved no candidates via secondary index lookup (Optional indicated result was absent)", (Object)this.getLogPrefix());
            }
        }
        if (indexedCandidates.isPresent()) {
            this.log.debug("{} Performing predicate filtering of resolved secondary indexed candidates", (Object)this.getLogPrefix());
            return this.predicateFilterCandidates((Iterable<EntityDescriptor>)indexedCandidates.get(), criteria, false);
        }
        if (this.isResolveViaPredicatesOnly()) {
            this.log.debug("{} Performing predicate filtering of entire metadata collection", (Object)this.getLogPrefix());
            return this.predicateFilterCandidates(this, criteria, true);
        }
        this.log.debug("{} Resolved no secondary indexed candidates, returning empty result", (Object)this.getLogPrefix());
        return Collections.emptySet();
    }

    @Nonnull
    @NonnullElements
    protected Optional<Set<EntityDescriptor>> lookupByIndexes(@Nonnull CriteriaSet criteria) {
        return this.getBackingStore().getSecondaryIndexManager().lookupIndexedItems(criteria);
    }

    @Override
    protected void indexEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor, @Nonnull AbstractMetadataResolver.EntityBackingStore backingStore) {
        super.indexEntityDescriptor(entityDescriptor, backingStore);
        ((BatchEntityBackingStore)backingStore).getSecondaryIndexManager().indexEntityDescriptor(entityDescriptor);
    }

    @Override
    @Nonnull
    protected BatchEntityBackingStore createNewBackingStore() {
        return new BatchEntityBackingStore(this.getIndexes());
    }

    @Override
    @Nonnull
    protected BatchEntityBackingStore getBackingStore() {
        return (BatchEntityBackingStore)super.getBackingStore();
    }

    @Override
    protected void initMetadataResolver() throws ComponentInitializationException {
        super.initMetadataResolver();
        this.setBackingStore(this.createNewBackingStore());
    }

    @Nullable
    protected XMLObject getCachedOriginalMetadata() {
        return this.getBackingStore().getCachedOriginalMetadata();
    }

    @Nullable
    protected XMLObject getCachedFilteredMetadata() {
        return this.getBackingStore().getCachedFilteredMetadata();
    }

    @Nonnull
    protected BatchEntityBackingStore preProcessNewMetadata(@Nonnull XMLObject root) throws FilterException {
        BatchEntityBackingStore newBackingStore = this.createNewBackingStore();
        XMLObject filteredMetadata = this.filterMetadata(root);
        if (this.isCacheSourceMetadata()) {
            newBackingStore.setCachedOriginalMetadata(root);
            newBackingStore.setCachedFilteredMetadata(filteredMetadata);
        }
        if (filteredMetadata == null) {
            this.log.info("{} Metadata filtering process produced a null document, resulting in an empty data set", (Object)this.getLogPrefix());
            return newBackingStore;
        }
        if (filteredMetadata instanceof EntityDescriptor) {
            this.preProcessEntityDescriptor((EntityDescriptor)filteredMetadata, newBackingStore);
        } else if (filteredMetadata instanceof EntitiesDescriptor) {
            this.preProcessEntitiesDescriptor((EntitiesDescriptor)filteredMetadata, newBackingStore);
        } else {
            this.log.warn("{} Document root was neither an EntityDescriptor nor an EntitiesDescriptor: {}", (Object)this.getLogPrefix(), (Object)root.getClass().getName());
        }
        return newBackingStore;
    }

    protected class BatchEntityBackingStore
    extends AbstractMetadataResolver.EntityBackingStore {
        private XMLObject cachedOriginalMetadata;
        private XMLObject cachedFilteredMetadata;
        private MetadataIndexManager<EntityDescriptor> secondaryIndexManager;

        protected BatchEntityBackingStore(Set<MetadataIndex> initIndexes) {
            super(AbstractBatchMetadataResolver.this);
            this.secondaryIndexManager = new MetadataIndexManager<EntityDescriptor>(initIndexes, new MetadataIndexManager.IdentityExtractionFunction());
        }

        public XMLObject getCachedOriginalMetadata() {
            return this.cachedOriginalMetadata;
        }

        public void setCachedOriginalMetadata(XMLObject metadata) {
            this.cachedOriginalMetadata = metadata;
        }

        public XMLObject getCachedFilteredMetadata() {
            return this.cachedFilteredMetadata;
        }

        public void setCachedFilteredMetadata(XMLObject metadata) {
            this.cachedFilteredMetadata = metadata;
        }

        public MetadataIndexManager<EntityDescriptor> getSecondaryIndexManager() {
            return this.secondaryIndexManager;
        }
    }
}

