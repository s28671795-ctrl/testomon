/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.service;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.service.ServiceableComponent;
import org.joda.time.DateTime;

public interface ReloadableService<T>
extends InitializableComponent {
    @Nullable
    public DateTime getLastSuccessfulReloadInstant();

    @Nullable
    public DateTime getLastReloadAttemptInstant();

    @Nullable
    public Throwable getReloadFailureCause();

    public void reload();

    @Nullable
    public ServiceableComponent<T> getServiceableComponent();
}

