/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.analytics.servers.dto.CPUDTO;
import com.incoresoft.vezha.analytics.servers.dto.GpuCardDTO;
import com.incoresoft.vezha.analytics.servers.dto.RunningAnalyticsDTO;
import com.incoresoft.vezha.analytics.servers.dto.RunningServiceDTO;
import com.incoresoft.vezha.analytics.servers.dto.deserializers.AnalyticsGpuBindingDeserializer;
import com.incoresoft.vezha.analytics.servers.dto.deserializers.AnalyticsTypesDeserializer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Schema(name="ServerDTO", description="Server class structure")
public final class ServerDTO {
    @JSONField(name="heartbeat_id")
    @Schema(name="heartbeat_id", description="Heartbeat id", implementation=Integer.class, example="1", nullable=true)
    private final Integer heartbeatId;
    @JSONField(name="server_id")
    @Schema(name="server_id", description="Server id", implementation=String.class, example="Server 1", nullable=true)
    private final String id;
    @JSONField(name="cpu")
    @Schema(name="cpu", description="CPU information", implementation=CPUDTO.class)
    private final CPUDTO cpu;
    @JSONField(name="total_mem")
    @Schema(name="total_mem", description="Total RAM", implementation=Double.class, example="16.0")
    private final double totalMem;
    @JSONField(name="free_mem")
    @Schema(name="free_mem", description="Free RAM", implementation=Double.class, example="10.0")
    private final double freeMem;
    @JSONField(name="ip")
    @Schema(name="ip", description="IP address", implementation=String.class, example="127.0.0.1")
    private final String ip;
    @JSONField(name="external_ip")
    @Schema(name="external_ip", description="External IP address", implementation=String.class, example="190.10.10.1")
    private final String externalIp;
    @JSONField(name="external_interface")
    @Schema(name="external_interface", description="External interface", implementation=String.class, example="190.10.10.1")
    private final String externalInterface;
    @JSONField(name="network_interfaces")
    @Schema(name="network_interfaces", description="Network interface map", type="object")
    private final Map<String, String> netInterfaces;
    @JSONField(name="version")
    @Schema(name="version", description="Middleware version", implementation=String.class, example="22.5")
    private final String version;
    @JSONField(name="graphics")
    @ArraySchema(schema=@Schema(name="version", description="GPU information", implementation=GpuCardDTO.class))
    private final List<GpuCardDTO> graphics;
    @JSONField(name="analytics")
    @ArraySchema(schema=@Schema(name="analytics", description="Started analytics information", implementation=RunningAnalyticsDTO.class))
    private final List<RunningAnalyticsDTO> runningAnalytics;
    @JSONField(name="services")
    @ArraySchema(schema=@Schema(name="services", description="Server's started services(neural networks)", implementation=RunningServiceDTO.class))
    private final List<RunningServiceDTO> runningServices;
    @JSONField(name="support", deserializeUsing=AnalyticsTypesDeserializer.class)
    @ArraySchema(schema=@Schema(name="support", description="Server's supported services(neural networks)", implementation=AnalyticsTypes.class))
    private final Set<AnalyticsTypes> support;
    @JSONField(name="params")
    @Schema(name="params", description="Additional params (like servers first heartbeat - start)", type="object")
    private final JSONObject params;
    @JSONField(name="analytics_changed", defaultValue="true")
    private final boolean analyticsChanged;
    @JSONField(name="preview_port")
    @Schema(name="preview_port", description="Server preview port", implementation=Integer.class, example="3020")
    private final int previewPort;
    @JSONField(name="analytics_gpu_binding", deserializeUsing=AnalyticsGpuBindingDeserializer.class)
    @Schema(name="analytics_gpu_binding", description="Object of GPU ID to analytics type binding", type="object")
    private final Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding;

    public Integer getHeartbeatId() {
        return this.heartbeatId;
    }

    public String getId() {
        return this.id;
    }

    public CPUDTO getCpu() {
        return this.cpu;
    }

    public double getTotalMem() {
        return this.totalMem;
    }

    public double getFreeMem() {
        return this.freeMem;
    }

    public String getIp() {
        return this.ip;
    }

    public String getExternalIp() {
        return this.externalIp;
    }

    public String getExternalInterface() {
        return this.externalInterface;
    }

    public Map<String, String> getNetInterfaces() {
        return this.netInterfaces;
    }

    public String getVersion() {
        return this.version;
    }

    public List<GpuCardDTO> getGraphics() {
        return this.graphics;
    }

    public List<RunningAnalyticsDTO> getRunningAnalytics() {
        return this.runningAnalytics;
    }

    public List<RunningServiceDTO> getRunningServices() {
        return this.runningServices;
    }

    public Set<AnalyticsTypes> getSupport() {
        return this.support;
    }

    public JSONObject getParams() {
        return this.params;
    }

    public boolean isAnalyticsChanged() {
        return this.analyticsChanged;
    }

    public int getPreviewPort() {
        return this.previewPort;
    }

    public Map<Integer, Set<AnalyticsTypes>> getAnalyticsGpuBinding() {
        return this.analyticsGpuBinding;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServerDTO)) {
            return false;
        }
        ServerDTO other = (ServerDTO)o;
        if (Double.compare(this.getTotalMem(), other.getTotalMem()) != 0) {
            return false;
        }
        if (Double.compare(this.getFreeMem(), other.getFreeMem()) != 0) {
            return false;
        }
        if (this.isAnalyticsChanged() != other.isAnalyticsChanged()) {
            return false;
        }
        if (this.getPreviewPort() != other.getPreviewPort()) {
            return false;
        }
        Integer this$heartbeatId = this.getHeartbeatId();
        Integer other$heartbeatId = other.getHeartbeatId();
        if (this$heartbeatId == null ? other$heartbeatId != null : !((Object)this$heartbeatId).equals(other$heartbeatId)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        CPUDTO this$cpu = this.getCpu();
        CPUDTO other$cpu = other.getCpu();
        if (this$cpu == null ? other$cpu != null : !((Object)this$cpu).equals(other$cpu)) {
            return false;
        }
        String this$ip = this.getIp();
        String other$ip = other.getIp();
        if (this$ip == null ? other$ip != null : !this$ip.equals(other$ip)) {
            return false;
        }
        String this$externalIp = this.getExternalIp();
        String other$externalIp = other.getExternalIp();
        if (this$externalIp == null ? other$externalIp != null : !this$externalIp.equals(other$externalIp)) {
            return false;
        }
        String this$externalInterface = this.getExternalInterface();
        String other$externalInterface = other.getExternalInterface();
        if (this$externalInterface == null ? other$externalInterface != null : !this$externalInterface.equals(other$externalInterface)) {
            return false;
        }
        Map<String, String> this$netInterfaces = this.getNetInterfaces();
        Map<String, String> other$netInterfaces = other.getNetInterfaces();
        if (this$netInterfaces == null ? other$netInterfaces != null : !((Object)this$netInterfaces).equals(other$netInterfaces)) {
            return false;
        }
        String this$version = this.getVersion();
        String other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) {
            return false;
        }
        List<GpuCardDTO> this$graphics = this.getGraphics();
        List<GpuCardDTO> other$graphics = other.getGraphics();
        if (this$graphics == null ? other$graphics != null : !((Object)this$graphics).equals(other$graphics)) {
            return false;
        }
        List<RunningAnalyticsDTO> this$runningAnalytics = this.getRunningAnalytics();
        List<RunningAnalyticsDTO> other$runningAnalytics = other.getRunningAnalytics();
        if (this$runningAnalytics == null ? other$runningAnalytics != null : !((Object)this$runningAnalytics).equals(other$runningAnalytics)) {
            return false;
        }
        List<RunningServiceDTO> this$runningServices = this.getRunningServices();
        List<RunningServiceDTO> other$runningServices = other.getRunningServices();
        if (this$runningServices == null ? other$runningServices != null : !((Object)this$runningServices).equals(other$runningServices)) {
            return false;
        }
        Set<AnalyticsTypes> this$support = this.getSupport();
        Set<AnalyticsTypes> other$support = other.getSupport();
        if (this$support == null ? other$support != null : !((Object)this$support).equals(other$support)) {
            return false;
        }
        JSONObject this$params = this.getParams();
        JSONObject other$params = other.getParams();
        if (this$params == null ? other$params != null : !((Object)this$params).equals(other$params)) {
            return false;
        }
        Map<Integer, Set<AnalyticsTypes>> this$analyticsGpuBinding = this.getAnalyticsGpuBinding();
        Map<Integer, Set<AnalyticsTypes>> other$analyticsGpuBinding = other.getAnalyticsGpuBinding();
        return !(this$analyticsGpuBinding == null ? other$analyticsGpuBinding != null : !((Object)this$analyticsGpuBinding).equals(other$analyticsGpuBinding));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $totalMem = Double.doubleToLongBits(this.getTotalMem());
        result = result * 59 + (int)($totalMem >>> 32 ^ $totalMem);
        long $freeMem = Double.doubleToLongBits(this.getFreeMem());
        result = result * 59 + (int)($freeMem >>> 32 ^ $freeMem);
        result = result * 59 + (this.isAnalyticsChanged() ? 79 : 97);
        result = result * 59 + this.getPreviewPort();
        Integer $heartbeatId = this.getHeartbeatId();
        result = result * 59 + ($heartbeatId == null ? 43 : ((Object)$heartbeatId).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        CPUDTO $cpu = this.getCpu();
        result = result * 59 + ($cpu == null ? 43 : ((Object)$cpu).hashCode());
        String $ip = this.getIp();
        result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
        String $externalIp = this.getExternalIp();
        result = result * 59 + ($externalIp == null ? 43 : $externalIp.hashCode());
        String $externalInterface = this.getExternalInterface();
        result = result * 59 + ($externalInterface == null ? 43 : $externalInterface.hashCode());
        Map<String, String> $netInterfaces = this.getNetInterfaces();
        result = result * 59 + ($netInterfaces == null ? 43 : ((Object)$netInterfaces).hashCode());
        String $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        List<GpuCardDTO> $graphics = this.getGraphics();
        result = result * 59 + ($graphics == null ? 43 : ((Object)$graphics).hashCode());
        List<RunningAnalyticsDTO> $runningAnalytics = this.getRunningAnalytics();
        result = result * 59 + ($runningAnalytics == null ? 43 : ((Object)$runningAnalytics).hashCode());
        List<RunningServiceDTO> $runningServices = this.getRunningServices();
        result = result * 59 + ($runningServices == null ? 43 : ((Object)$runningServices).hashCode());
        Set<AnalyticsTypes> $support = this.getSupport();
        result = result * 59 + ($support == null ? 43 : ((Object)$support).hashCode());
        JSONObject $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : ((Object)$params).hashCode());
        Map<Integer, Set<AnalyticsTypes>> $analyticsGpuBinding = this.getAnalyticsGpuBinding();
        result = result * 59 + ($analyticsGpuBinding == null ? 43 : ((Object)$analyticsGpuBinding).hashCode());
        return result;
    }

    public String toString() {
        return "ServerDTO(heartbeatId=" + this.getHeartbeatId() + ", id=" + this.getId() + ", cpu=" + String.valueOf(this.getCpu()) + ", totalMem=" + this.getTotalMem() + ", freeMem=" + this.getFreeMem() + ", ip=" + this.getIp() + ", externalIp=" + this.getExternalIp() + ", externalInterface=" + this.getExternalInterface() + ", netInterfaces=" + String.valueOf(this.getNetInterfaces()) + ", version=" + this.getVersion() + ", graphics=" + String.valueOf(this.getGraphics()) + ", runningAnalytics=" + String.valueOf(this.getRunningAnalytics()) + ", runningServices=" + String.valueOf(this.getRunningServices()) + ", support=" + String.valueOf(this.getSupport()) + ", params=" + String.valueOf(this.getParams()) + ", analyticsChanged=" + this.isAnalyticsChanged() + ", previewPort=" + this.getPreviewPort() + ", analyticsGpuBinding=" + String.valueOf(this.getAnalyticsGpuBinding()) + ")";
    }

    public ServerDTO(Integer heartbeatId, String id, CPUDTO cpu, double totalMem, double freeMem, String ip2, String externalIp, String externalInterface, Map<String, String> netInterfaces, String version, List<GpuCardDTO> graphics, List<RunningAnalyticsDTO> runningAnalytics, List<RunningServiceDTO> runningServices, Set<AnalyticsTypes> support, JSONObject params, boolean analyticsChanged, int previewPort, Map<Integer, Set<AnalyticsTypes>> analyticsGpuBinding) {
        this.heartbeatId = heartbeatId;
        this.id = id;
        this.cpu = cpu;
        this.totalMem = totalMem;
        this.freeMem = freeMem;
        this.ip = ip2;
        this.externalIp = externalIp;
        this.externalInterface = externalInterface;
        this.netInterfaces = netInterfaces;
        this.version = version;
        this.graphics = graphics;
        this.runningAnalytics = runningAnalytics;
        this.runningServices = runningServices;
        this.support = support;
        this.params = params;
        this.analyticsChanged = analyticsChanged;
        this.previewPort = previewPort;
        this.analyticsGpuBinding = analyticsGpuBinding;
    }
}

