/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.persist;

import java.io.IOException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.core.xml.XMLObject;

public interface XMLObjectLoadSaveManager<T extends XMLObject> {
    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Set<String> listKeys() throws IOException;

    @Nonnull
    @NonnullElements
    public Iterable<Pair<String, T>> listAll() throws IOException;

    public boolean exists(@Nonnull @NotEmpty String var1) throws IOException;

    @Nullable
    public T load(@Nonnull @NotEmpty String var1) throws IOException;

    public void save(@Nonnull @NotEmpty String var1, @Nonnull T var2) throws IOException;

    public void save(@Nonnull @NotEmpty String var1, @Nonnull T var2, boolean var3) throws IOException;

    public boolean remove(@Nonnull @NotEmpty String var1) throws IOException;

    public boolean updateKey(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2) throws IOException;
}

