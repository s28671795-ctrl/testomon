/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

import liquibase.license.LicenseService;
import liquibase.plugin.AbstractPluginFactory;

public class LicenseServiceFactory
extends AbstractPluginFactory<LicenseService> {
    private LicenseServiceFactory() {
    }

    @Override
    protected Class<LicenseService> getPluginClass() {
        return LicenseService.class;
    }

    @Override
    protected int getPriority(LicenseService obj, Object ... args2) {
        return obj.getPriority();
    }

    public LicenseService getLicenseService() {
        return (LicenseService)this.getPlugin(new Object[0]);
    }

    public void unregister(LicenseService service2) {
        this.removeInstance(service2);
    }
}

