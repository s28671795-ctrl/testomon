/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.plugins;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;

public class AllPluginsStoppedAuditMessage
extends AuditMessage {
    @Override
    protected String getTemplateId() {
        return "ALL_PLUGINS_STOPPED";
    }
}

