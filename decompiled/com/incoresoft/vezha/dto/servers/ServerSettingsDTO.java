/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;
import com.incoresoft.vezha.dto.servers.validations.ExternalAddress;
import com.incoresoft.vezha.dto.servers.validations.ExternalAddressNotBlank;
import com.incoresoft.vezha.dto.servers.validations.ExternalPortMax;
import com.incoresoft.vezha.dto.servers.validations.ExternalPortMin;
import com.incoresoft.vezha.dto.servers.validations.ExternalPortNotNull;
import com.incoresoft.vezha.models.servers.ServerSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ExternalAddress
@ExternalAddressNotBlank
@ExternalPortNotNull
@ExternalPortMin(value=1L)
@ExternalPortMax(value=65536L)
public final class ServerSettingsDTO
implements ExternalAddress.Validatable,
ExternalAddressNotBlank.Validatable,
ExternalPortNotNull.Validatable,
ExternalPortMin.Validatable,
ExternalPortMax.Validatable {
    @JSONField(name="id")
    @Schema(name="id", description="Server id.", implementation=String.class, example="1", nullable=true)
    private final String id;
    @JSONField(name="name")
    @Schema(name="name", description="Custom server name.", example="LPR Server")
    @NotBlank
    private final String name;
    @JSONField(name="is_external_address_enabled")
    @Schema(name="is_external_address_enabled", description="Flag indicating if external address is enabled.", example="true")
    private final boolean isExternalAddressEnabled;
    @JSONField(name="external_address")
    @Schema(name="external_address", description="External IP address or domain name of this server.", example="145.12.23.200")
    private final String externalHost;
    @JSONField(name="external_port")
    @Schema(name="external_port", description="External port of this server", example="8080")
    private final Integer externalPort;
    @JSONField(name="external_interface")
    @Schema(name="external_interface", description="External network interface to use.", example="local")
    private final String externalInterface;
    @JSONField(name="preview_port")
    @Schema(name="preview_port", description="Live analytics view stream port.", example="3020")
    @NotNull
    @Min(value=1L)
    @Max(value=65536L)
    private final @NotNull @Min(value=1L) @Max(value=65536L) Integer previewPort;
    @JSONField(name="analytics_gpu_binding")
    @Schema(name="analytics_gpu_binding", description="", type="object", example="{\"0\": [\"alpr\",\"face\"]}")
    private final Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding;

    public ServerSettingsDTO(ServerSettings entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.isExternalAddressEnabled = entity.isExternalAddressEnabled();
        this.externalHost = entity.getExternalAddress();
        this.externalPort = entity.getExternalPort();
        this.externalInterface = entity.getExternalInterface();
        this.previewPort = entity.getPreviewPort();
        this.analyticsGpuBinding = entity.getAnalyticsGpuBinding();
    }

    public ServerSettingsDTO(FrontServerDTO serverDTO) {
        this.id = serverDTO.getId();
        this.name = serverDTO.getName();
        this.isExternalAddressEnabled = serverDTO.isExternalAddressEnabled();
        this.externalHost = serverDTO.getExternalAddress();
        this.externalPort = serverDTO.getExternalPort();
        this.externalInterface = serverDTO.getExternalInterface();
        this.previewPort = serverDTO.getPreviewPort();
        this.analyticsGpuBinding = serverDTO.getAnalyticsGpuAffinity();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean isExternalAddressEnabled() {
        return this.isExternalAddressEnabled;
    }

    @Override
    public String getExternalHost() {
        return this.externalHost;
    }

    @Override
    public Integer getExternalPort() {
        return this.externalPort;
    }

    public String getExternalInterface() {
        return this.externalInterface;
    }

    public Integer getPreviewPort() {
        return this.previewPort;
    }

    public Map<Integer, Set<AnalyticsTypes>> getAnalyticsGpuBinding() {
        return this.analyticsGpuBinding;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServerSettingsDTO)) {
            return false;
        }
        ServerSettingsDTO other = (ServerSettingsDTO)o;
        if (this.isExternalAddressEnabled() != other.isExternalAddressEnabled()) {
            return false;
        }
        Integer this$externalPort = this.getExternalPort();
        Integer other$externalPort = other.getExternalPort();
        if (this$externalPort == null ? other$externalPort != null : !((Object)this$externalPort).equals(other$externalPort)) {
            return false;
        }
        Integer this$previewPort = this.getPreviewPort();
        Integer other$previewPort = other.getPreviewPort();
        if (this$previewPort == null ? other$previewPort != null : !((Object)this$previewPort).equals(other$previewPort)) {
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
        String this$externalHost = this.getExternalHost();
        String other$externalHost = other.getExternalHost();
        if (this$externalHost == null ? other$externalHost != null : !this$externalHost.equals(other$externalHost)) {
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

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isExternalAddressEnabled() ? 79 : 97);
        Integer $externalPort = this.getExternalPort();
        result = result * 59 + ($externalPort == null ? 43 : ((Object)$externalPort).hashCode());
        Integer $previewPort = this.getPreviewPort();
        result = result * 59 + ($previewPort == null ? 43 : ((Object)$previewPort).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $externalHost = this.getExternalHost();
        result = result * 59 + ($externalHost == null ? 43 : $externalHost.hashCode());
        String $externalInterface = this.getExternalInterface();
        result = result * 59 + ($externalInterface == null ? 43 : $externalInterface.hashCode());
        Map<Integer, Set<AnalyticsTypes>> $analyticsGpuBinding = this.getAnalyticsGpuBinding();
        result = result * 59 + ($analyticsGpuBinding == null ? 43 : ((Object)$analyticsGpuBinding).hashCode());
        return result;
    }

    public String toString() {
        return "ServerSettingsDTO(id=" + this.getId() + ", name=" + this.getName() + ", isExternalAddressEnabled=" + this.isExternalAddressEnabled() + ", externalHost=" + this.getExternalHost() + ", externalPort=" + this.getExternalPort() + ", externalInterface=" + this.getExternalInterface() + ", previewPort=" + this.getPreviewPort() + ", analyticsGpuBinding=" + String.valueOf(this.getAnalyticsGpuBinding()) + ")";
    }

    public ServerSettingsDTO(String id, String name, boolean isExternalAddressEnabled, String externalHost, Integer externalPort, String externalInterface, Integer previewPort, Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding) {
        this.id = id;
        this.name = name;
        this.isExternalAddressEnabled = isExternalAddressEnabled;
        this.externalHost = externalHost;
        this.externalPort = externalPort;
        this.externalInterface = externalInterface;
        this.previewPort = previewPort;
        this.analyticsGpuBinding = analyticsGpuBinding;
    }
}

