/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.groups;

import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.engine.groups.Group;
import org.hibernate.validator.internal.util.CollectionHelper;

public class GroupWithInheritance
implements Iterable<Group> {
    private final Set<Group> groups;

    public GroupWithInheritance(Set<Group> groups2) {
        this.groups = CollectionHelper.toImmutableSet(groups2);
    }

    @Override
    public Iterator<Group> iterator() {
        return this.groups.iterator();
    }
}

