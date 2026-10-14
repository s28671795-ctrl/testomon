/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager.extensions;

import com.incoresoft.middleware.eventmanager.EventNode;
import com.incoresoft.middleware.eventmanager.EventNodeContext;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.validation.ValidationViolation;
import java.util.Collections;
import java.util.List;

public abstract class GoBlock
extends EventManagerExtension {
    @Override
    public List<ValidationViolation> validate(EventNode eventNode) {
        return Collections.emptyList();
    }

    @Override
    public Integer execute(EventNodeContext eventNodeContext) {
        return 0;
    }

    @Override
    public List<String> getFrontComponents() {
        return Collections.emptyList();
    }

    @Override
    public void init(ExtensionContext extensionContext) {
    }
}

