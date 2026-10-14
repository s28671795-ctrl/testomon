/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit.messages.cleaner;

import com.incoresoft.middleware.cleaner.CleaningCategory;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateAuditMessage;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

public final class CleaningSettingsUpdatedAuditMessage
extends ObjectUpdateAuditMessage<Map<CleaningCategory, Integer>> {
    @Override
    protected String getTemplateId() {
        return "CLEANING_SETTINGS_UPDATED";
    }

    public CleaningSettingsUpdatedAuditMessage(Map<CleaningCategory, Duration> old, Map<CleaningCategory, Duration> updated) {
        super(old.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> Math.toIntExact(((Duration)it.getValue()).toDays()))), updated.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> Math.toIntExact(((Duration)it.getValue()).toDays()))));
    }

    public String toString() {
        return "CleaningSettingsUpdatedAuditMessage()";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CleaningSettingsUpdatedAuditMessage)) {
            return false;
        }
        CleaningSettingsUpdatedAuditMessage other = (CleaningSettingsUpdatedAuditMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    protected boolean canEqual(Object other) {
        return other instanceof CleaningSettingsUpdatedAuditMessage;
    }

    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

