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
import org.opensaml.saml.metadata.EntityGroupName;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityGroupNamePredicate
implements Predicate<EntityDescriptor> {
    @Nonnull
    @NonnullElements
    private final Set<String> groupNames;

    public EntityGroupNamePredicate(@Nonnull @NonnullElements Collection<String> names) {
        Constraint.isNotNull(names, "Group name collection cannot be null");
        this.groupNames = new HashSet<String>(names.size());
        for (String name : names) {
            String trimmed = StringSupport.trimOrNull(name);
            if (trimmed == null) continue;
            this.groupNames.add(trimmed);
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getGroupNames() {
        return ImmutableSet.copyOf(this.groupNames);
    }

    @Override
    public boolean apply(@Nullable EntityDescriptor input) {
        if (input != null) {
            for (EntityGroupName group : input.getObjectMetadata().get(EntityGroupName.class)) {
                if (!this.groupNames.contains(group.getName())) continue;
                return true;
            }
        }
        return false;
    }
}

