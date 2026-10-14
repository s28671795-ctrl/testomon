/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.services.installations.events.CoreServiceInstallationEvent;

public class CoreServiceInstallationFailedEvent
implements CoreServiceInstallationEvent {
    @JsonProperty(value="service_id")
    @JSONField(name="service_id")
    private String serviceId;

    public String getServiceId() {
        return this.serviceId;
    }

    @JsonProperty(value="service_id")
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CoreServiceInstallationFailedEvent)) {
            return false;
        }
        CoreServiceInstallationFailedEvent other = (CoreServiceInstallationFailedEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$serviceId = this.getServiceId();
        String other$serviceId = other.getServiceId();
        return !(this$serviceId == null ? other$serviceId != null : !this$serviceId.equals(other$serviceId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CoreServiceInstallationFailedEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $serviceId = this.getServiceId();
        result = result * 59 + ($serviceId == null ? 43 : $serviceId.hashCode());
        return result;
    }

    public String toString() {
        return "CoreServiceInstallationFailedEvent(serviceId=" + this.getServiceId() + ")";
    }

    public CoreServiceInstallationFailedEvent() {
    }

    public CoreServiceInstallationFailedEvent(String serviceId) {
        this.serviceId = serviceId;
    }
}

