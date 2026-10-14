/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.stream.Stream;
import java.util.Collection;
import java.util.function.Predicate;

public interface StreamArchive
extends ItemManager<Stream> {
    public Collection<Stream> getAll(Predicate<Stream> var1);
}

