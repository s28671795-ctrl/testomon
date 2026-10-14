/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.metadata.resolver.index.impl.MetadataIndexStore;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataIndexManager<T> {
    private Logger log = LoggerFactory.getLogger(MetadataIndexManager.class);
    private Map<MetadataIndex, MetadataIndexStore<T>> indexes;
    private Function<EntityDescriptor, T> entityDescriptorFunction;

    public MetadataIndexManager(@Nullable @NonnullElements @Unmodifiable @NotLive Set<MetadataIndex> initIndexes, @Nonnull Function<EntityDescriptor, T> extractionFunction) {
        this.entityDescriptorFunction = Constraint.isNotNull(extractionFunction, "EntityDescriptor extraction function was null");
        this.indexes = new ConcurrentHashMap<MetadataIndex, MetadataIndexStore<T>>();
        if (initIndexes != null) {
            for (MetadataIndex index : initIndexes) {
                this.log.trace("Initializing manager for index: {}", (Object)index);
                this.indexes.put(index, new MetadataIndexStore());
            }
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndex> getIndexes() {
        return ImmutableSet.copyOf(this.indexes.keySet());
    }

    @Nullable
    protected MetadataIndexStore<T> getStore(@Nonnull MetadataIndex index) {
        Constraint.isNotNull(index, "MetadataIndex was null");
        return this.indexes.get(index);
    }

    @Nonnull
    @NonnullElements
    public Optional<Set<T>> lookupIndexedItems(@Nonnull CriteriaSet criteria) {
        HashSet items = new HashSet();
        for (MetadataIndex index : this.indexes.keySet()) {
            Set<MetadataIndexKey> keys2 = index.generateKeys(criteria);
            if (keys2 == null || keys2.isEmpty()) continue;
            LazySet<T> indexResult = new LazySet<T>();
            MetadataIndexStore<T> indexStore = this.getStore(index);
            for (MetadataIndexKey key : keys2) {
                indexResult.addAll((Collection<T>)indexStore.lookup(key));
            }
            this.log.trace("MetadataIndex '{}' produced results: {}", (Object)index, (Object)indexResult);
            if (items.isEmpty()) {
                items.addAll(indexResult);
            } else {
                items.retainAll(indexResult);
            }
            if (!items.isEmpty()) continue;
            this.log.trace("Accumulator intersected with MetadataIndex '{}' result produced empty result, terminating early and returning empty result set", (Object)index);
            return Optional.of(Collections.emptySet());
        }
        if (items.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(items);
    }

    public void indexEntityDescriptor(@Nonnull EntityDescriptor descriptor) {
        T item = this.entityDescriptorFunction.apply(descriptor);
        if (item != null) {
            for (MetadataIndex index : this.indexes.keySet()) {
                Set<MetadataIndexKey> keys2 = index.generateKeys(descriptor);
                if (keys2 == null || keys2.isEmpty()) continue;
                MetadataIndexStore<T> store = this.getStore(index);
                for (MetadataIndexKey key : keys2) {
                    this.log.trace("Indexing metadata: index '{}', key '{}', data item '{}'", index, key, item);
                    store.add(key, item);
                }
            }
        } else {
            this.log.trace("Unable to extract indexed data item from EntityDescriptor");
        }
    }

    public static class EntityIDExtractionFunction
    implements Function<EntityDescriptor, String> {
        @Override
        public String apply(EntityDescriptor input) {
            if (input == null) {
                return null;
            }
            return StringSupport.trimOrNull(input.getEntityID());
        }
    }

    public static class IdentityExtractionFunction
    implements Function<EntityDescriptor, EntityDescriptor> {
        @Override
        public EntityDescriptor apply(EntityDescriptor input) {
            return input;
        }
    }
}

