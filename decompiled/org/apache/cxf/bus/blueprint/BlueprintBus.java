/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleContext
 *  org.osgi.service.blueprint.container.BlueprintContainer
 */
package org.apache.cxf.bus.blueprint;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.apache.cxf.bus.blueprint.BlueprintBeanLocator;
import org.apache.cxf.bus.blueprint.BundleDelegatingClassLoader;
import org.apache.cxf.bus.blueprint.ConfigurerImpl;
import org.apache.cxf.bus.extension.ExtensionManagerBus;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.resource.ClassLoaderResolver;
import org.apache.cxf.resource.ResourceManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.blueprint.container.BlueprintContainer;

public class BlueprintBus
extends ExtensionManagerBus {
    BundleContext context;
    BlueprintContainer container;

    public BlueprintBus() {
        super(null, null, BlueprintBus.class.getClassLoader());
    }

    public void setBundleContext(final BundleContext c) {
        this.context = c;
        ClassLoader bundleClassLoader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return new BundleDelegatingClassLoader(c.getBundle(), this.getClass().getClassLoader());
            }
        });
        super.setExtension(bundleClassLoader, ClassLoader.class);
        ResourceManager rm = super.getExtension(ResourceManager.class);
        rm.addResourceResolver(new ClassLoaderResolver(bundleClassLoader));
        super.setExtension(c, BundleContext.class);
    }

    public void setBlueprintContainer(BlueprintContainer con) {
        this.container = con;
        this.setExtension(new ConfigurerImpl(con), Configurer.class);
        this.setExtension(new BlueprintBeanLocator(this.getExtension(ConfiguredBeanLocator.class), this.container, this.context), ConfiguredBeanLocator.class);
    }

    @Override
    public String getId() {
        if (this.id == null) {
            this.id = this.context == null ? super.getId() : this.context.getBundle().getSymbolicName() + "-" + "cxf" + Integer.toString(this.hashCode());
        }
        return this.id;
    }
}

