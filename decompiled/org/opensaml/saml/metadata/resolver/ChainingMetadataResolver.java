/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
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

public class ChainingMetadataResolver
extends AbstractIdentifiableInitializableComponent
implements MetadataResolver,
RefreshableMetadataResolver {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingMetadataResolver.class);
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

    public void setResolvers(@Nonnull @NonnullElements List<? extends MetadataResolver> newResolvers) throws ResolverException {
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
        throw new UnsupportedOperationException("Setting requireValidMetadata is not supported on chaining resolver");
    }

    @Override
    public MetadataFilter getMetadataFilter() {
        this.log.warn("Attempt to access unsupported MetadataFilter property on ChainingMetadataResolver");
        return null;
    }

    @Override
    public void setMetadataFilter(MetadataFilter newFilter) {
        throw new UnsupportedOperationException("Metadata filters are not supported on ChainingMetadataResolver");
    }

    @Override
    @Nullable
    public EntityDescriptor resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
        Iterator<EntityDescriptor> iterator2;
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        Iterable<EntityDescriptor> iterable = this.resolve(criteria);
        if (iterable != null && (iterator2 = iterable.iterator()) != null && iterator2.hasNext()) {
            return iterator2.next();
        }
        return null;
    }

    @Override
    @Nonnull
    public Iterable<EntityDescriptor> resolve(@Nullable CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        for (MetadataResolver resolver2 : this.resolvers) {
            try {
                Iterable<EntityDescriptor> descriptors = resolver2.resolve(criteria);
                if (descriptors == null || !descriptors.iterator().hasNext()) continue;
                return descriptors;
            }
            catch (ResolverException e) {
                this.log.warn("Error retrieving metadata from resolver of type {}, proceeding to next resolver", (Object)resolver2.getClass().getName(), (Object)e);
            }
        }
        return Collections.emptyList();
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

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.resolvers == null) {
            this.log.warn("ChainingMetadataResolver was not configured with any member MetadataResolvers");
            this.resolvers = Collections.emptyList();
        }
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        this.resolvers = Collections.emptyList();
    }
}

