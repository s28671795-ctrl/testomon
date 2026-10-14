/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.servers;

import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.dto.servers.ServerSettingsDTO;
import com.incoresoft.vezha.models.servers.ServerPersistenceInfo;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class ServerSettings {
    private String id;
    private String name;
    private boolean isExternalAddressEnabled;
    private String externalAddress;
    private Integer externalPort;
    private String externalInterface;
    private int previewPort;
    private Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding;

    public ServerSettings(@NotNull String serverId, @NotNull ServerSettingsDTO serverSettingsDTO) {
        this.id = Objects.requireNonNull(serverId);
        this.name = serverSettingsDTO.getName();
        this.isExternalAddressEnabled = serverSettingsDTO.isExternalAddressEnabled();
        this.externalAddress = serverSettingsDTO.getExternalHost();
        this.externalPort = serverSettingsDTO.getExternalPort();
        this.externalInterface = serverSettingsDTO.getExternalInterface();
        this.previewPort = serverSettingsDTO.getPreviewPort();
        this.analyticsGpuBinding = serverSettingsDTO.getAnalyticsGpuBinding();
    }

    public ServerPersistenceInfo getPersistenceInfo() {
        return new ServerPersistenceInfo(this.getId(), this.getName(), this.isExternalAddressEnabled(), this.getExternalAddress(), this.getExternalPort());
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isExternalAddressEnabled() {
        return this.isExternalAddressEnabled;
    }

    public String getExternalAddress() {
        return this.externalAddress;
    }

    public Integer getExternalPort() {
        return this.externalPort;
    }

    public String getExternalInterface() {
        return this.externalInterface;
    }

    public int getPreviewPort() {
        return this.previewPort;
    }

    public Map<Integer, Set<AnalyticsTypes>> getAnalyticsGpuBinding() {
        return this.analyticsGpuBinding;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExternalAddressEnabled(boolean isExternalAddressEnabled) {
        this.isExternalAddressEnabled = isExternalAddressEnabled;
    }

    public void setExternalAddress(String externalAddress) {
        this.externalAddress = externalAddress;
    }

    public void setExternalPort(Integer externalPort) {
        this.externalPort = externalPort;
    }

    public void setExternalInterface(String externalInterface) {
        this.externalInterface = externalInterface;
    }

    public void setPreviewPort(int previewPort) {
        this.previewPort = previewPort;
    }

    public void setAnalyticsGpuBinding(Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding) {
        this.analyticsGpuBinding = analyticsGpuBinding;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServerSettings)) {
            return false;
        }
        ServerSettings other = (ServerSettings)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isExternalAddressEnabled() != other.isExternalAddressEnabled()) {
            return false;
        }
        if (this.getPreviewPort() != other.getPreviewPort()) {
            return false;
        }
        Integer this$externalPort = this.getExternalPort();
        Integer other$externalPort = other.getExternalPort();
        if (this$externalPort == null ? other$externalPort != null : !((Object)this$externalPort).equals(other$externalPort)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$externalAddress = this.getExternalAddress();
        String other$externalAddress = other.getExternalAddress();
        if (this$externalAddress == null ? other$externalAddress != null : !this$externalAddress.equals(other$externalAddress)) {
            return false;
        }
        String this$externalInterface = this.getExternalInterface();
        String other$externalInterface = other.getExternalInterface();
        if (this$externalInterface == null ? other$externalInterface != null : !this$externalInterface.equals(other$externalInterface)) {
            return false;
        }
        Map<Integer, Set<AnalyticsTypes>> this$analyticsGpuBinding = this.getAnalyticsGpuBinding();
        Map<Integer, Set<AnalyticsTypes>> other$analyticsGpuBinding = other.getAnalyticsGpuBinding();
        return !(this$analyticsGpuBinding == null ? other$analyticsGpuBinding != null : !((Object)this$analyticsGpuBinding).equals(other$analyticsGpuBinding));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ServerSettings;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isExternalAddressEnabled() ? 79 : 97);
        result = result * 59 + this.getPreviewPort();
        Integer $externalPort = this.getExternalPort();
        result = result * 59 + ($externalPort == null ? 43 : ((Object)$externalPort).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $externalAddress = this.getExternalAddress();
        result = result * 59 + ($externalAddress == null ? 43 : $externalAddress.hashCode());
        String $externalInterface = this.getExternalInterface();
        result = result * 59 + ($externalInterface == null ? 43 : $externalInterface.hashCode());
        Map<Integer, Set<AnalyticsTypes>> $analyticsGpuBinding = this.getAnalyticsGpuBinding();
        result = result * 59 + ($analyticsGpuBinding == null ? 43 : ((Object)$analyticsGpuBinding).hashCode());
        return result;
    }

    public String toString() {
        return "ServerSettings(id=" + this.getId() + ", name=" + this.getName() + ", isExternalAddressEnabled=" + this.isExternalAddressEnabled() + ", externalAddress=" + this.getExternalAddress() + ", externalPort=" + this.getExternalPort() + ", externalInterface=" + this.getExternalInterface() + ", previewPort=" + this.getPreviewPort() + ", analyticsGpuBinding=" + String.valueOf(this.getAnalyticsGpuBinding()) + ")";
    }

    public ServerSettings() {
    }
}

