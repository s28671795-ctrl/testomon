/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import liquibase.plugin.AbstractPluginFactory;
import liquibase.ui.UIService;

public class UIServiceFactory
extends AbstractPluginFactory<UIService> {
    private UIServiceFactory() {
    }

    @Override
    protected Class<UIService> getPluginClass() {
        return UIService.class;
    }

    @Override
    protected int getPriority(UIService uiService, Object ... args2) {
        return uiService.getPriority();
    }
}

