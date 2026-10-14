/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.NamespaceHandler
 *  org.osgi.framework.BundleContext
 */
package org.apache.cxf.bus.blueprint;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.osgi.framework.BundleContext;

public final class NamespaceHandlerRegisterer {
    private static final Logger LOG = LogUtils.getL7dLogger(NamespaceHandlerRegisterer.class);

    private NamespaceHandlerRegisterer() {
    }

    public static void register(BundleContext bc, BlueprintNameSpaceHandlerFactory factory2, String ... namespaces) {
        try {
            Object handler = factory2.createNamespaceHandler();
            for (String namespace : namespaces) {
                Hashtable<String, String> properties = new Hashtable<String, String>();
                ((Dictionary)properties).put("osgi.service.blueprint.namespace", namespace);
                bc.registerService(NamespaceHandler.class.getName(), handler, properties);
                LOG.fine("Registered blueprint namespace handler for " + namespace);
            }
        }
        catch (NoClassDefFoundError e) {
            LOG.log(Level.INFO, "Aries Blueprint packages not available. So namespaces will not be registered");
        }
        catch (Throwable e) {
            LOG.log(Level.WARNING, "Unexpected exception when trying to install Aries Blueprint namespaces", e);
        }
    }
}

