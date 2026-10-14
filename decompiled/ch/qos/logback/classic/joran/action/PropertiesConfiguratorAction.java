/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.classic.joran.action;

import ch.qos.logback.classic.model.PropertiesConfiguratorModel;
import ch.qos.logback.core.joran.action.ResourceAction;

public class PropertiesConfiguratorAction
extends ResourceAction {
    @Override
    protected PropertiesConfiguratorModel makeNewResourceModel() {
        return new PropertiesConfiguratorModel();
    }
}

