/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 */
package org.apache.geronimo.osgi.locator;

import org.apache.geronimo.osgi.locator.ProviderLocator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

public class Activator
implements BundleActivator {
    protected BundleContext bundleContext;

    public synchronized void start(BundleContext bundleContext) throws Exception {
        this.bundleContext = bundleContext;
        ProviderLocator.init(bundleContext);
    }

    public synchronized void stop(BundleContext bundleContext) throws Exception {
        ProviderLocator.destroy();
        this.bundleContext = null;
    }

    public void bundleChanged(BundleEvent event) {
    }
}

