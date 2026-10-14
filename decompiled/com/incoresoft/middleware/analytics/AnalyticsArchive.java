/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.user.User;
import java.util.Collection;
import java.util.function.Predicate;

public interface AnalyticsArchive
extends ItemManager<Analytics> {
    public Collection<Analytics> getAll(Predicate<Analytics> var1);

    public Collection<Analytics> getAll(User var1);
}

