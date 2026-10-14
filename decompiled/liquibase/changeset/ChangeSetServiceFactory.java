/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changeset;

import liquibase.changeset.ChangeSetService;
import liquibase.plugin.AbstractPluginFactory;

public class ChangeSetServiceFactory
extends AbstractPluginFactory<ChangeSetService> {
    private static ChangeSetServiceFactory factory = null;

    private ChangeSetServiceFactory() {
    }

    public static ChangeSetServiceFactory getInstance() {
        if (factory == null) {
            factory = new ChangeSetServiceFactory();
        }
        return factory;
    }

    @Override
    protected Class<ChangeSetService> getPluginClass() {
        return ChangeSetService.class;
    }

    @Override
    protected int getPriority(ChangeSetService obj, Object ... args2) {
        return obj.getPriority();
    }

    public ChangeSetService createChangeSetService() {
        return (ChangeSetService)this.getPlugin(new Object[0]);
    }
}

