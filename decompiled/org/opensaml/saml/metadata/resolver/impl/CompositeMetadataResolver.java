/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RefreshableMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeMetadataResolver
extends AbstractIdentifiedInitializableComponent
implements MetadataResolver,
RefreshableMetadataResolver {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CompositeMetadataResolver.class);
    @Nonnull
    @NonnullElements
    private List<MetadataResolver> resolvers = Collections.emptyList();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<MetadataResolver> getResolvers() {
        return ImmutableList.copyOf(this.resolvers);
    }

    public void setResolvers(@Nonnull @NonnullElements List<MetadataResolver> newResolvers) throws ResolverException {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (newResolvers == null || newResolvers.isEmpty()) {
            this.resolvers = Collections.emptyList();
            return;
        }
        this.resolvers = new ArrayList<MetadataResolver>(Collections2.filter(newResolvers, Predicates.notNull()));
    }

    @Override
    public boolean isRequireValidMetadata() {
        this.log.warn("Attempt to access unsupported requireValidMetadata property on ChainingMetadataResolver");
        return false;
    }

    @Override
    public void setRequireValidMetadata(boolean requireValidMetadata) {
        throw new UnsupportedOperationException("Setting require valid metadata is not supported on chaining resolver");
    }

    @Override
    @Nullable
    public MetadataFilter getMetadataFilter() {
        this.log.warn("Attempt to access unsupported MetadataFilter property on ChainingMetadataResolver");
        return null;
    }

    @Override
    public void setMetadataFilter(@Nullable MetadataFilter newFilter) {
        throw new UnsupportedOperationException("Metadata filters are not supported on ChainingMetadataProviders");
    }

    @Override
    public Iterable<EntityDescriptor> resolve(@Nullable CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        return new CompositeMetadataResolverIterable(this.resolvers, criteria);
    }

    @Override
    public EntityDescriptor resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        EntityDescriptor metadata = null;
        for (MetadataResolver resolver2 : this.resolvers) {
            metadata = (EntityDescriptor)resolver2.resolveSingle(criteria);
            if (metadata == null) continue;
            return metadata;
        }
        return null;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.resolvers == null) {
            this.log.warn("CompositeMetadataResolver was not configured with any member MetadataResolvers");
            this.resolvers = Collections.emptyList();
        }
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        this.resolvers = Collections.emptyList();
    }

    @Override
    public void refresh() throws ResolverException {
        for (MetadataResolver resolver2 : this.resolvers) {
            if (!(resolver2 instanceof RefreshableMetadataResolver)) continue;
            ((RefreshableMetadataResolver)resolver2).refresh();
        }
    }

    @Override
    @Nullable
    public DateTime getLastUpdate() {
        DateTime ret = null;
        for (MetadataResolver resolver2 : this.resolvers) {
            if (!(resolver2 instanceof RefreshableMetadataResolver)) continue;
            DateTime lastUpdate = ((RefreshableMetadataResolver)resolver2).getLastUpdate();
            if (ret != null && !ret.isBefore(lastUpdate)) continue;
            ret = lastUpdate;
        }
        return ret;
    }

    @Override
    @Nullable
    public DateTime getLastRefresh() {
        DateTime ret = null;
        for (MetadataResolver resolver2 : this.resolvers) {
            if (!(resolver2 instanceof RefreshableMetadataResolver)) continue;
            DateTime lastRefresh = ((RefreshableMetadataResolver)resolver2).getLastRefresh();
            if (ret != null && !ret.isBefore(lastRefresh)) continue;
            ret = lastRefresh;
        }
        return ret;
    }

    private static class CompositeMetadataResolverIterable
    implements Iterable<EntityDescriptor> {
        private final Logger log = LoggerFactory.getLogger(CompositeMetadataResolverIterable.class);
        private final List<MetadataResolver> resolvers;
        private final CriteriaSet criteria;

        public CompositeMetadataResolverIterable(List<MetadataResolver> composedResolvers, CriteriaSet metadataCritiera) {
            this.resolvers = ((ImmutableList.Builder)ImmutableList.builder().addAll(Iterables.filter(composedResolvers, Predicates.notNull()))).build();
            this.criteria = metadataCritiera;
        }

        @Override
        public Iterator<EntityDescriptor> iterator() {
            return new CompositeMetadataResolverIterator();
        }

        private class CompositeMetadataResolverIterator
        implements Iterator<EntityDescriptor> {
            private Iterator<MetadataResolver> resolverIterator;
            private MetadataResolver currentResolver;
            private Iterator<EntityDescriptor> currentResolverMetadataIterator;

            public CompositeMetadataResolverIterator() {
                this.resolverIterator = CompositeMetadataResolverIterable.this.resolvers.iterator();
            }

            @Override
            public boolean hasNext() {
                if (!this.currentResolverMetadataIterator.hasNext()) {
                    this.proceedToNextResolverIterator();
                }
                return this.currentResolverMetadataIterator.hasNext();
            }

            @Override
            public EntityDescriptor next() {
                if (!this.currentResolverMetadataIterator.hasNext()) {
                    this.proceedToNextResolverIterator();
                }
                return this.currentResolverMetadataIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void proceedToNextResolverIterator() {
                try {
                    while (this.resolverIterator.hasNext()) {
                        this.currentResolver = this.resolverIterator.next();
                        this.currentResolverMetadataIterator = this.currentResolver.resolve(CompositeMetadataResolverIterable.this.criteria).iterator();
                        if (!this.currentResolverMetadataIterator.hasNext()) continue;
                        return;
                    }
                }
                catch (ResolverException e) {
                    CompositeMetadataResolverIterable.this.log.debug("Error encountered attempting to fetch results from resolver", e);
                }
            }
        }
    }
}

