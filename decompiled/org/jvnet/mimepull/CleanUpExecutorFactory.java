/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.util.concurrent.ScheduledExecutorService;
import org.jvnet.mimepull.FactoryFinder;

public abstract class CleanUpExecutorFactory {
    private static final String DEFAULT_PROPERTY_NAME = CleanUpExecutorFactory.class.getName();

    protected CleanUpExecutorFactory() {
    }

    public static CleanUpExecutorFactory newInstance() {
        try {
            return (CleanUpExecutorFactory)FactoryFinder.find(DEFAULT_PROPERTY_NAME);
        }
        catch (RuntimeException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    public abstract ScheduledExecutorService getScheduledExecutorService();
}

