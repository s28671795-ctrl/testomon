/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

@NotThreadSafe
public class IDIndex {
    @Nonnull
    private final XMLObject owner;
    @Nonnull
    private Map<String, XMLObject> idMappings;

    public IDIndex(@Nonnull XMLObject newOwner) {
        Constraint.isNotNull(newOwner, "ID-owning XMLObject may not be null");
        this.owner = newOwner;
        this.idMappings = new LazyMap<String, XMLObject>();
    }

    public void registerIDMapping(@Nonnull @NotEmpty String id, @Nonnull XMLObject referent) {
        if (id == null) {
            return;
        }
        this.idMappings.put(id, referent);
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMapping(id, referent);
        }
    }

    public void registerIDMappings(@Nonnull IDIndex idIndex) {
        if (idIndex == null || idIndex.isEmpty()) {
            return;
        }
        this.idMappings.putAll(idIndex.getIDMappings());
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMappings(idIndex);
        }
    }

    public void deregisterIDMapping(@Nonnull @NotEmpty String id) {
        if (id == null) {
            return;
        }
        this.idMappings.remove(id);
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMapping(id);
        }
    }

    public void deregisterIDMappings(@Nonnull IDIndex idIndex) {
        if (idIndex == null || idIndex.isEmpty()) {
            return;
        }
        for (String id : idIndex.getIDs()) {
            this.idMappings.remove(id);
        }
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMappings(idIndex);
        }
    }

    @Nullable
    public XMLObject lookup(@Nonnull @NotEmpty String id) {
        return this.idMappings.get(id);
    }

    public boolean isEmpty() {
        return this.idMappings.isEmpty();
    }

    @Nonnull
    public Set<String> getIDs() {
        return Collections.unmodifiableSet(this.idMappings.keySet());
    }

    @Nonnull
    protected Map<String, XMLObject> getIDMappings() {
        return Collections.unmodifiableMap(this.idMappings);
    }
}

