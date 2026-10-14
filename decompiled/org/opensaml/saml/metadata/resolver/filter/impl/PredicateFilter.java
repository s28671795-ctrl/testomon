/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateFilter
implements MetadataFilter {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(PredicateFilter.class);
    @Nonnull
    private final Direction direction;
    @Nonnull
    private final Predicate<EntityDescriptor> condition;
    private boolean removeEmptyEntitiesDescriptors;

    public PredicateFilter(@Nonnull Direction dir, @Nonnull Predicate<EntityDescriptor> theCondition) {
        this.condition = Constraint.isNotNull(theCondition, "Matching condition cannot be null");
        this.direction = Constraint.isNotNull(dir, "Direction cannot be null");
        this.removeEmptyEntitiesDescriptors = true;
    }

    @Nonnull
    public Direction getDirection() {
        return this.direction;
    }

    @Nonnull
    public Predicate<EntityDescriptor> getCondition() {
        return this.condition;
    }

    public boolean getRemoveEmptyEntitiesDescriptors() {
        return this.removeEmptyEntitiesDescriptors;
    }

    public void setRemoveEmptyEntitiesDescriptors(boolean remove) {
        this.removeEmptyEntitiesDescriptors = remove;
    }

    @Override
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
        if (metadata == null) {
            return null;
        }
        if (metadata instanceof EntitiesDescriptor) {
            this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
            return metadata;
        }
        if (metadata instanceof EntityDescriptor) {
            EntityDescriptor entity = (EntityDescriptor)metadata;
            if (Direction.EXCLUDE.equals((Object)this.direction) == this.condition.apply(entity)) {
                this.log.trace("Filtering out entity {} ", (Object)entity.getEntityID());
                return null;
            }
            return metadata;
        }
        this.log.error("Unrecognised metadata type {}", (Object)metadata.getClass().getSimpleName());
        return null;
    }

    protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) {
        List<EntitiesDescriptor> entitiesDescriptors;
        List<EntityDescriptor> entityDescriptors = descriptor.getEntityDescriptors();
        if (!entityDescriptors.isEmpty()) {
            ArrayList<EntityDescriptor> emptyEntityDescriptors = new ArrayList<EntityDescriptor>();
            for (EntityDescriptor entityDescriptor : entityDescriptors) {
                if (Direction.EXCLUDE.equals((Object)this.direction) != this.condition.apply(entityDescriptor)) continue;
                this.log.trace("Filtering out entity {} from group {}", (Object)entityDescriptor.getEntityID(), (Object)descriptor.getName());
                emptyEntityDescriptors.add(entityDescriptor);
            }
            entityDescriptors.removeAll(emptyEntityDescriptors);
        }
        if (!(entitiesDescriptors = descriptor.getEntitiesDescriptors()).isEmpty()) {
            ArrayList<EntitiesDescriptor> emptyEntitiesDescriptors = new ArrayList<EntitiesDescriptor>();
            for (EntitiesDescriptor entitiesDescriptor : entitiesDescriptors) {
                this.filterEntitiesDescriptor(entitiesDescriptor);
                if (!this.getRemoveEmptyEntitiesDescriptors() || !entitiesDescriptor.getEntityDescriptors().isEmpty() || !entitiesDescriptor.getEntitiesDescriptors().isEmpty()) continue;
                this.log.trace("Filtering out empty group {} from group {}", (Object)entitiesDescriptor.getName(), (Object)descriptor.getName());
                emptyEntitiesDescriptors.add(entitiesDescriptor);
            }
            entitiesDescriptors.removeAll(emptyEntitiesDescriptors);
        }
    }

    public static enum Direction {
        INCLUDE,
        EXCLUDE;

    }
}

