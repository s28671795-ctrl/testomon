/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations.events;

import com.incoresoft.services.installations.events.CoreServiceInstallationCompletedEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationFailedEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationProgressEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationStartedEvent;

public enum CoreServiceInstallationEventType {
    INSTALLATION_STARTED(CoreServiceInstallationStartedEvent.class),
    INSTALLATION_PROGRESS(CoreServiceInstallationProgressEvent.class),
    INSTALLATION_COMPLETED(CoreServiceInstallationCompletedEvent.class),
    INSTALLATION_FAILED(CoreServiceInstallationFailedEvent.class),
    UNKNOWN(null);

    private final Class<? extends CoreServiceInstallationEvent> messageClass;

    public String toString() {
        return this.name().toLowerCase();
    }

    public static CoreServiceInstallationEventType parse(String value) {
        return CoreServiceInstallationEventType.valueOf(value.toUpperCase());
    }

    public static CoreServiceInstallationEventType getByMessageClass(Class<? extends CoreServiceInstallationEvent> messageClass) {
        for (CoreServiceInstallationEventType messageType : CoreServiceInstallationEventType.values()) {
            if (!messageType.getMessageClass().equals(messageClass)) continue;
            return messageType;
        }
        return UNKNOWN;
    }

    public Class<? extends CoreServiceInstallationEvent> getMessageClass() {
        return this.messageClass;
    }

    private CoreServiceInstallationEventType(Class<? extends CoreServiceInstallationEvent> messageClass) {
        this.messageClass = messageClass;
    }
}

