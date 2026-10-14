/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus;

import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.extension.ExtensionManagerBus;

public class CXFBusFactory
extends BusFactory {
    @Override
    public Bus createBus() {
        return this.createBus(new HashMap());
    }

    public Bus createBus(Map<Class<?>, Object> e) {
        return this.createBus(e, new HashMap<String, Object>());
    }

    public Bus createBus(Map<Class<?>, Object> e, Map<String, Object> properties) {
        ExtensionManagerBus bus = new ExtensionManagerBus(e, properties);
        CXFBusFactory.possiblySetDefaultBus(bus);
        this.initializeBus(bus);
        bus.initialize();
        return bus;
    }
}

