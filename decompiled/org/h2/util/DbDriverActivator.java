/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 */
package org.h2.util;

import org.h2.Driver;
import org.h2.util.JdbcUtils;
import org.h2.util.OsgiDataSourceFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DbDriverActivator
implements BundleActivator {
    private static final String DATASOURCE_FACTORY_CLASS = "org.osgi.service.jdbc.DataSourceFactory";

    public void start(BundleContext bundleContext) {
        Driver driver = Driver.load();
        try {
            JdbcUtils.loadUserClass(DATASOURCE_FACTORY_CLASS);
        }
        catch (Exception exception) {
            return;
        }
        OsgiDataSourceFactory.registerService(bundleContext, driver);
    }

    public void stop(BundleContext bundleContext) {
        Driver.unload();
    }
}

