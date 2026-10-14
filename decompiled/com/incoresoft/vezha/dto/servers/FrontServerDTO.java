/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.dto.GpuCardDTO;
import com.incoresoft.vezha.analytics.servers.dto.RunningServiceDTO;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import com.incoresoft.vezha.dto.servers.RunningAnalyticsFrontDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

@Schema(name="FrontServerDTO", description="Analytics server class structure")
public class FrontServerDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Server ID", implementation=String.class, example="2")
    private String id;
    @JSONField(name="name")
    @Schema(name="name", description="Custom server name", implementation=String.class, example="LPR Server")
    private String name;
    @JSONField(name="cpu_usage")
    @Schema(name="cpu_usage", description="Server CPU utilization in percents.", implementation=Double.class, example="32.41")
    private double cpuUsage;
    @JSONField(name="total_mem")
    @Schema(name="total_mem", description="Total amount of server RAM in GiB", implementation=Double.class, example="31.68")
    private double totalMem;
    @JSONField(name="free_mem")
    @Schema(name="free_mem", description="Amount of free server RAM in GiB", implementation=Double.class, example="19.74")
    private double freeMem;
    @Schema(name="ip_address", description="External IP address of server", implementation=String.class, example="178.10.12.34")
    @JSONField(name="ip_address")
    private String ip;
    @Schema(name="external_interface", description="Currently used server external interface", implementation=String.class, example="lo")
    @JSONField(name="external_interface")
    private String externalInterface;
    @Schema(name="network_interfaces", description="List of all available external interfaces", type="object", example="{\"lo\": \"127.0.0.1\"}")
    @JSONField(name="network_interfaces")
    private Map<String, String> netInterfaces;
    @JSONField(name="version")
    @Schema(name="version", description="Server version", implementation=String.class, example="22.3.2")
    private String version;
    @JSONField(name="graphics")
    @Schema(name="graphics", description="List of all available graphic adapters")
    private List<GpuCardDTO> graphics;
    @Schema(name="analytics", description="List of all currently running analytics on this server")
    @JSONField(name="analytics")
    private List<RunningAnalyticsFrontDTO> runningAnalytics;
    @Schema(name="services", description="List of all available services")
    @JSONField(name="services")
    private List<RunningServiceDTO> runningServices;
    @JSONField(name="support")
    @Schema(name="support", description="List of all supported analytics", type="array", example="[\"alpr\"]")
    private Set<AnalyticsTypes> support;
    @JSONField(name="online")
    @Schema(name="online", description="Determines whether is Core has a live connection to this server")
    private boolean online;
    @JSONField(name="last_heart_beat")
    @Schema(name="last_heart_beat", description="Seconds elapsed since the last heartbeat from this server", example="5")
    private long lastHeartBeat;
    @Schema(name="preview_port", description="Current analytics live view port")
    @JSONField(name="preview_port")
    int previewPort;
    @Schema(name="analytics_gpu_binding", description="Analytics GPU affinity settings", type="object", example="{\"0\": [\"alpr\",\"face\"]}")
    @JSONField(name="analytics_gpu_binding")
    Map<Integer, Set<AnalyticsTypes>> analyticsGpuAffinity;
    @Schema(name="is_external_address_enabled", description="Determines whether external address is enabled")
    @JSONField(name="is_external_address_enabled")
    boolean externalAddressEnabled;
    @Schema(name="external_address", description="External IP address of server", implementation=String.class, example="200.100.10.10")
    @JSONField(name="external_address")
    String externalAddress;
    @Schema(name="external_port", description="External port of server", implementation=Integer.class, example="8080")
    @JSONField(name="external_port")
    Integer externalPort;

    public FrontServerDTO(@NotNull Server server, String serverName, AnalyticsManager analyticsManager) {
        this.id = server.getId();
        this.name = serverName;
        this.cpuUsage = server.getCpuUsage();
        this.totalMem = server.getTotalMem();
        this.freeMem = server.getFreeMem();
        this.ip = server.getExternalIpAddress();
        this.externalInterface = server.getExternalInterface();
        this.netInterfaces = server.getNetInterfaces();
        this.graphics = server.getGraphics().values().stream().map(GpuCardDTO::new).toList();
        this.runningAnalytics = server.getRunningAnalytics().stream().map(it -> new RunningAnalyticsFrontDTO((RunningAnalytics)it, analyticsManager)).toList();
        this.runningServices = server.getRunningServices().stream().map(RunningServiceDTO::new).toList();
        this.support = server.getSupport();
        this.lastHeartBeat = Duration.between(server.getLastHeartBeat(), Instant.now()).toSeconds();
        this.previewPort = server.getPreviewPort();
        this.analyticsGpuAffinity = server.getAnalyticsGpuBinding();
        this.externalAddressEnabled = server.isExternalAddressEnabled();
        this.externalAddress = server.getExternalIpAddress();
        this.externalPort = server.getExternalPort();
        this.version = server.getVersion();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public void setTotalMem(double totalMem) {
        this.totalMem = totalMem;
    }

    public void setFreeMem(double freeMem) {
        this.freeMem = freeMem;
    }

    public void setIp(String ip2) {
        this.ip = ip2;
    }

    public void setExternalInterface(String externalInterface) {
        this.externalInterface = externalInterface;
    }

    public void setNetInterfaces(Map<String, String> netInterfaces) {
        this.netInterfaces = netInterfaces;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGraphics(List<GpuCardDTO> graphics) {
        this.graphics = graphics;
    }

    public void setRunningAnalytics(List<RunningAnalyticsFrontDTO> runningAnalytics) {
        this.runningAnalytics = runningAnalytics;
    }

    public void setRunningServices(List<RunningServiceDTO> runningServices) {
        this.runningServices = runningServices;
    }

    public void setSupport(Set<AnalyticsTypes> support) {
        this.support = support;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setLastHeartBeat(long lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public void setPreviewPort(int previewPort) {
        this.previewPort = previewPort;
    }

    public void setAnalyticsGpuAffinity(Map<Integer, Set<AnalyticsTypes>> analyticsGpuAffinity) {
        this.analyticsGpuAffinity = analyticsGpuAffinity;
    }

    public void setExternalAddressEnabled(boolean externalAddressEnabled) {
        this.externalAddressEnabled = externalAddressEnabled;
    }

    public void setExternalAddress(String externalAddress) {
        this.externalAddress = externalAddress;
    }

    public void setExternalPort(Integer externalPort) {
        this.externalPort = externalPort;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getCpuUsage() {
        return this.cpuUsage;
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

    public List<RunningAnalyticsFrontDTO> getRunningAnalytics() {
        return this.runningAnalytics;
    }

    public List<RunningServiceDTO> getRunningServices() {
        return this.runningServices;
    }

    public Set<AnalyticsTypes> getSupport() {
        return this.support;
    }

    public boolean isOnline() {
        return this.online;
    }

    public long getLastHeartBeat() {
        return this.lastHeartBeat;
    }

    public int getPreviewPort() {
        return this.previewPort;
    }

    public Map<Integer, Set<AnalyticsTypes>> getAnalyticsGpuAffinity() {
        return this.analyticsGpuAffinity;
    }

    public boolean isExternalAddressEnabled() {
        return this.externalAddressEnabled;
    }

    public String getExternalAddress() {
        return this.externalAddress;
    }

    public Integer getExternalPort() {
        return this.externalPort;
    }
}

