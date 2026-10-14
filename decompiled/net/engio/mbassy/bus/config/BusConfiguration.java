/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

public class BusConfiguration
implements IBusConfiguration {
    private final Map<Object, Object> properties = new HashMap<Object, Object>();
    private final List<IPublicationErrorHandler> publicationErrorHandlers = new ArrayList<IPublicationErrorHandler>();

    @Override
    public IBusConfiguration setProperty(String name, Object value) {
        this.properties.put(name, value);
        return this;
    }

    @Override
    public <T> T getProperty(String name, T defaultValue) {
        return (T)(this.properties.containsKey(name) ? this.properties.get(name) : defaultValue);
    }

    @Override
    public boolean hasProperty(String name) {
        return this.properties.containsKey(name);
    }

    @Override
    public <T extends Feature> T getFeature(Class<T> feature) {
        return (T)((Feature)this.properties.get(feature));
    }

    @Override
    public IBusConfiguration addFeature(Feature feature) {
        this.properties.put(feature.getClass(), feature);
        return this;
    }

    @Override
    public final BusConfiguration addPublicationErrorHandler(IPublicationErrorHandler handler) {
        this.publicationErrorHandlers.add(handler);
        return this;
    }

    @Override
    public Collection<IPublicationErrorHandler> getRegisteredPublicationErrorHandlers() {
        return Collections.unmodifiableCollection(this.publicationErrorHandlers);
    }
}

