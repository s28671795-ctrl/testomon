/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.config;

import java.util.Collection;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

public interface IBusConfiguration {
    public IBusConfiguration setProperty(String var1, Object var2);

    public <T> T getProperty(String var1, T var2);

    public boolean hasProperty(String var1);

    public <T extends Feature> T getFeature(Class<T> var1);

    public IBusConfiguration addFeature(Feature var1);

    public BusConfiguration addPublicationErrorHandler(IPublicationErrorHandler var1);

    public Collection<IPublicationErrorHandler> getRegisteredPublicationErrorHandlers();

    public static final class Properties {
        public static final String BusId = "bus.id";
        public static final String PublicationErrorHandlers = "bus.handlers.error";
        public static final String AsynchronousHandlerExecutor = "bus.handlers.async-executor";
    }
}

