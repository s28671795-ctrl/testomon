/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc;

import liquibase.logging.mdc.MdcManager;
import liquibase.plugin.AbstractPluginFactory;

public class MdcManagerFactory
extends AbstractPluginFactory<MdcManager> {
    private MdcManagerFactory() {
    }

    @Override
    protected Class<MdcManager> getPluginClass() {
        return MdcManager.class;
    }

    @Override
    protected int getPriority(MdcManager obj, Object ... args2) {
        return obj.getPriority();
    }

    public MdcManager getMdcManager() {
        return (MdcManager)this.getPlugin(new Object[0]);
    }

    public void unregister(MdcManager manager) {
        this.removeInstance(manager);
    }
}

