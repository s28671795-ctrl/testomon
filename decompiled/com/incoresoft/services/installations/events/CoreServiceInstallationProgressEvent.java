/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.services.installations.events.CoreServiceInstallationEvent;

public class CoreServiceInstallationProgressEvent
implements CoreServiceInstallationEvent {
    @JsonProperty(value="service_id")
    @JSONField(name="service_id")
    private String serviceId;
    @JsonProperty(value="downloaded_part")
    @JSONField(name="downloaded_part")
    private double downloadedPart;

    public String getServiceId() {
        return this.serviceId;
    }

    public double getDownloadedPart() {
        return this.downloadedPart;
    }

    @JsonProperty(value="service_id")
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @JsonProperty(value="downloaded_part")
    public void setDownloadedPart(double downloadedPart) {
        this.downloadedPart = downloadedPart;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CoreServiceInstallationProgressEvent)) {
            return false;
        }
        CoreServiceInstallationProgressEvent other = (CoreServiceInstallationProgressEvent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (Double.compare(this.getDownloadedPart(), other.getDownloadedPart()) != 0) {
            return false;
        }
        String this$serviceId = this.getServiceId();
        String other$serviceId = other.getServiceId();
        return !(this$serviceId == null ? other$serviceId != null : !this$serviceId.equals(other$serviceId));
    }

    protected boolean canEqual(Object other) {
        return other instanceof CoreServiceInstallationProgressEvent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $downloadedPart = Double.doubleToLongBits(this.getDownloadedPart());
        result = result * 59 + (int)($downloadedPart >>> 32 ^ $downloadedPart);
        String $serviceId = this.getServiceId();
        result = result * 59 + ($serviceId == null ? 43 : $serviceId.hashCode());
        return result;
    }

    public String toString() {
        return "CoreServiceInstallationProgressEvent(serviceId=" + this.getServiceId() + ", downloadedPart=" + this.getDownloadedPart() + ")";
    }

    public CoreServiceInstallationProgressEvent() {
    }

    public CoreServiceInstallationProgressEvent(String serviceId, double downloadedPart) {
        this.serviceId = serviceId;
        this.downloadedPart = downloadedPart;
    }
}

