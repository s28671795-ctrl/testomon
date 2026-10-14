/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import liquibase.ui.ConsoleUIService;
import liquibase.ui.LoggerUIService;
import liquibase.ui.UIService;

public enum UIServiceEnum {
    CONSOLE(ConsoleUIService.class),
    LOGGER(LoggerUIService.class);

    private final Class<? extends UIService> uiServiceClass;

    public Class<? extends UIService> getUiServiceClass() {
        return this.uiServiceClass;
    }

    private UIServiceEnum(Class<? extends UIService> uiServiceClass) {
        this.uiServiceClass = uiServiceClass;
    }
}

