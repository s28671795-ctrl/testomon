/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityIdPredicate
implements Predicate<EntityDescriptor> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EntityIdPredicate.class);
    @Nonnull
    @NonnullElements
    private final Set<String> entityIds;

    public EntityIdPredicate(@Nonnull @NonnullElements Collection<String> ids) {
        Constraint.isNotNull(ids, "EntityID collection cannot be null");
        this.entityIds = new HashSet<String>(ids.size());
        for (String id : ids) {
            String trimmed = StringSupport.trimOrNull(id);
            if (trimmed == null) continue;
            this.entityIds.add(trimmed);
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getEntityIds() {
        return ImmutableSet.copyOf(this.entityIds);
    }

    @Override
    public boolean apply(@Nullable EntityDescriptor input) {
        if (input == null || input.getEntityID() == null) {
            return false;
        }
        return this.entityIds.contains(input.getEntityID());
    }
}

