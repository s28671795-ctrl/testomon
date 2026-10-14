/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.incoresoft.config.models.LoadBalancerConfig;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.model.analytics.AnalyticsServer;
import com.incoresoft.middleware.model.analytics.module.hardware.Hardware;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareDecode;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserve;
import com.incoresoft.vezha.analytics.servers.dto.ServerDTO;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import com.incoresoft.vezha.analytics.servers.models.RunningService;
import com.incoresoft.vezha.models.servers.ServerPersistenceInfo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Server
implements AnalyticsServer {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private final String id;
    private final double cpuUsage;
    private final double totalMem;
    private final double freeMem;
    private final String ipAddress;
    private final boolean isExternalAddressEnabled;
    private final String externalIpAddress;
    private final Integer externalPort;
    private final String externalInterface;
    private final ConcurrentMap<String, String> netInterfaces;
    private final Instant lastHeartBeat = Instant.now();
    private final String version;
    private final ConcurrentHashMap<Integer, GpuCard> graphics;
    private final ConcurrentMap<Integer, RunningAnalytics> runningAnalytics;
    private final Set<RunningService> runningServices;
    private final Set<AnalyticsTypes> support;
    private final boolean analyticsChanged;
    private final int previewPort;
    private final ConcurrentMap<Integer, Set<AnalyticsTypes>> analyticsGpuBinding;

    public Server(Server server, ServerPersistenceInfo persistenceInfo) {
        this.id = server.getId();
        this.cpuUsage = server.getCpuUsage();
        this.totalMem = server.getTotalMem();
        this.freeMem = server.getFreeMem();
        this.ipAddress = server.getIpAddress();
        this.isExternalAddressEnabled = persistenceInfo.isExternalAddressEnabled();
        this.externalIpAddress = persistenceInfo.externalAddress();
        this.externalPort = persistenceInfo.externalPort();
        this.externalInterface = server.getExternalInterface();
        this.netInterfaces = new ConcurrentHashMap<String, String>(server.getNetInterfaces());
        this.version = server.getVersion();
        this.graphics = server.getGraphics() != null ? new ConcurrentHashMap<Integer, GpuCard>(server.getGraphics()) : new ConcurrentHashMap();
        this.runningAnalytics = new ConcurrentHashMap<Integer, RunningAnalytics>(server.runningAnalytics);
        this.runningServices = Set.copyOf(server.runningServices);
        this.support = server.getSupport();
        this.analyticsChanged = server.isAnalyticsChanged();
        this.previewPort = server.getPreviewPort();
        this.analyticsGpuBinding = new ConcurrentHashMap<Integer, Set<AnalyticsTypes>>(server.getAnalyticsGpuBinding());
    }

    public Server(ServerDTO dto, ServerPersistenceInfo persistenceInfo) {
        this.id = dto.getId();
        this.cpuUsage = dto.getCpu() == null ? 0.0 : dto.getCpu().getAvg();
        this.totalMem = dto.getTotalMem();
        this.freeMem = dto.getFreeMem();
        this.ipAddress = dto.getIp();
        this.isExternalAddressEnabled = persistenceInfo.isExternalAddressEnabled();
        this.externalIpAddress = persistenceInfo.externalAddress();
        this.externalPort = persistenceInfo.externalPort();
        this.externalInterface = dto.getExternalInterface();
        this.netInterfaces = dto.getNetInterfaces() == null ? new ConcurrentHashMap<String, String>() : new ConcurrentHashMap<String, String>(dto.getNetInterfaces());
        this.version = dto.getVersion();
        this.graphics = dto.getGraphics() != null ? dto.getGraphics().stream().map(GpuCard::new).collect(Collectors.toMap(GpuCard::getId, Function.identity(), (a, b) -> a, ConcurrentHashMap::new)) : new ConcurrentHashMap();
        this.runningAnalytics = dto.getRunningAnalytics() == null ? new ConcurrentHashMap<Integer, RunningAnalytics>() : (ConcurrentMap)dto.getRunningAnalytics().stream().map(RunningAnalytics::new).collect(Collectors.toMap(RunningAnalytics::getId, Function.identity(), (a, b) -> a, ConcurrentHashMap::new));
        this.runningServices = dto.getRunningServices() == null ? Set.of() : dto.getRunningServices().stream().map(RunningService::new).collect(Collectors.toUnmodifiableSet());
        this.support = dto.getSupport() == null ? Set.of() : dto.getSupport();
        this.analyticsChanged = dto.isAnalyticsChanged();
        this.previewPort = dto.getPreviewPort();
        this.analyticsGpuBinding = dto.getAnalyticsGpuBinding() == null ? new ConcurrentHashMap<Integer, Set<AnalyticsTypes>>() : new ConcurrentHashMap<Integer, Set<AnalyticsTypes>>(dto.getAnalyticsGpuBinding());
    }

    public Integer analyticsGpuId(int analyticsId) {
        return Optional.ofNullable((RunningAnalytics)this.runningAnalytics.get(analyticsId)).map(RunningAnalytics::getGpuId).orElse(-1);
    }

    public Map<Integer, RunningAnalytics> getRunningAnalyticsMap() {
        return this.runningAnalytics;
    }

    public Collection<RunningAnalytics> getRunningAnalytics() {
        return this.runningAnalytics.values();
    }

    public boolean supports(Analytics analytics) {
        return new HashSet<AnalyticsTypes>(this.support).containsAll(new ArrayList<AnalyticsTypes>(analytics.getModule().getAnalyticsTypes()));
    }

    public boolean canHold(BaseAnalytics analytics, LoadBalancerConfig config, GpuMemoryReserve reserve) {
        return this.canHold(analytics, config, reserve, false);
    }

    boolean canHold(BaseAnalytics analytics, LoadBalancerConfig config, GpuMemoryReserve reserve, boolean ignoreStartDebounce) {
        boolean noMemory;
        boolean serviceReady = this.checkRunningService(analytics);
        boolean bl = noMemory = !this.checkFreeMemory(this.freeMem, serviceReady);
        if (noMemory && config.isCheckMemory()) {
            return this.reject(analytics, "not enough system memory (free={} GB, serviceReady={})", this.freeMem, serviceReady);
        }
        if (this.cpuUsage > 90.0 && config.isCheckCpuUsage()) {
            return this.reject(analytics, "cpu usage {}% is above 90%", this.cpuUsage);
        }
        if (analytics.getModule().getHardwareSettings().getHardware() == Hardware.gpu) {
            if (this.graphics.isEmpty()) {
                return this.reject(analytics, "server has no GPU cards", new Object[0]);
            }
            boolean anyEligible = this.graphics.values().stream().anyMatch(g2 -> this.isCardEligible((GpuCard)g2, analytics, config, reserve, serviceReady, ignoreStartDebounce, true));
            if (anyEligible) {
                return true;
            }
            boolean anyDegraded = this.graphics.values().stream().anyMatch(g2 -> this.isCardEligible((GpuCard)g2, analytics, config, reserve, serviceReady, ignoreStartDebounce, false));
            if (anyDegraded) {
                log.warn("Server {}: admitting analytics {} with degraded decode \u2014 every otherwise-eligible GPU is above the nvDec threshold", (Object)this.id, (Object)analytics.getId());
                return true;
            }
            return this.reject(analytics, "no eligible GPU: {}", this.describeCardRejections(analytics, config, reserve, serviceReady, ignoreStartDebounce));
        }
        return true;
    }

    private boolean isCardEligible(GpuCard gpu, BaseAnalytics analytics, LoadBalancerConfig config, GpuMemoryReserve reserve, boolean serviceReady, boolean ignoreStartDebounce, boolean enforceNvDec) {
        if (!ignoreStartDebounce && !gpu.isGpuAvailable()) {
            return false;
        }
        if (config.isCheckGpuUsage()) {
            if (!this.checkGpuFreeMemory(analytics, this.effectiveFreeMemory(gpu, reserve), serviceReady)) {
                return false;
            }
            if (gpu.getGpuUsage() >= 90.0) {
                return false;
            }
        }
        return !enforceNvDec || analytics.getModule().getHardwareSettings().getDecoding() != HardwareDecode.nvidia || !config.isCheckNvDec() || gpu.getNvDec() < 80.0;
    }

    private String describeCardRejections(BaseAnalytics analytics, LoadBalancerConfig config, GpuMemoryReserve reserve, boolean serviceReady, boolean ignoreStartDebounce) {
        int debounced = 0;
        int noMemory = 0;
        int overloaded = 0;
        for (GpuCard gpu : this.graphics.values()) {
            if (!ignoreStartDebounce && !gpu.isGpuAvailable()) {
                ++debounced;
                continue;
            }
            if (config.isCheckGpuUsage() && !this.checkGpuFreeMemory(analytics, this.effectiveFreeMemory(gpu, reserve), serviceReady)) {
                ++noMemory;
                continue;
            }
            ++overloaded;
        }
        return "%d cards: %d in %ds start debounce, %d without memory (reserved not-yet-consuming: %s GB), %d over usage".formatted(this.graphics.size(), debounced, GpuCard.getStartDebounceSeconds(), noMemory, this.totalReservedGb(reserve), overloaded);
    }

    private double effectiveFreeMemory(GpuCard gpu, GpuMemoryReserve reserve) {
        if (reserve == null) {
            return gpu.getFreeMemory().get();
        }
        return gpu.getFreeMemory().get() - reserve.reservedGb(gpu.getId());
    }

    private double totalReservedGb(GpuMemoryReserve reserve) {
        if (reserve == null) {
            return 0.0;
        }
        return this.graphics.keySet().stream().mapToDouble(reserve::reservedGb).sum();
    }

    private boolean reject(BaseAnalytics analytics, String reason, Object ... args2) {
        if (log.isDebugEnabled()) {
            Object[] withContext = new Object[args2.length + 2];
            withContext[0] = this.id;
            withContext[1] = analytics.getId();
            System.arraycopy(args2, 0, withContext, 2, args2.length);
            log.debug("Server {} cannot hold analytics {}: " + reason, withContext);
        }
        return false;
    }

    private boolean checkRunningService(Analytics analytics) {
        return this.runningServices.stream().filter(RunningService::isReady).map(RunningService::getType).collect(Collectors.toSet()).containsAll(analytics.getModule().getAnalyticsTypes().stream().map(AnalyticsTypes::getName).collect(Collectors.toSet()));
    }

    public int getOptimalGpu(BaseAnalytics analytics, LoadBalancerConfig config, GpuMemoryReserve reserve) {
        if (this.graphics.isEmpty() || analytics.getModule().getHardwareSettings().getHardware() == Hardware.cpu) {
            return -1;
        }
        this.setRunnedAnalytics();
        boolean serviceReady = this.checkRunningService(analytics);
        Comparator<GpuCard> byLoad = Comparator.comparing(GpuCard::getRunnedAnalyticsWeight).thenComparing(GpuCard::getWeight);
        Optional<GpuCard> chosen = this.graphics.values().stream().filter(g2 -> this.isCardEligible((GpuCard)g2, analytics, config, reserve, serviceReady, false, true)).min(byLoad);
        if (chosen.isEmpty()) {
            chosen = this.graphics.values().stream().filter(g2 -> this.isCardEligible((GpuCard)g2, analytics, config, reserve, serviceReady, false, false)).min(byLoad);
            chosen.ifPresent(g2 -> log.warn("Server {}: placing analytics {} on GPU {} with degraded decode (nvDec={})", this.id, analytics.getId(), g2.getId(), g2.getNvDec()));
        }
        if (chosen.isEmpty()) {
            GpuCard fallback = this.graphics.values().stream().max(Comparator.comparingDouble(g2 -> this.effectiveFreeMemory((GpuCard)g2, reserve))).orElse(null);
            int gpuId = fallback == null ? 0 : fallback.getId();
            log.warn("Server {}: no GPU is eligible for analytics {}; best-effort fallback to GPU {} (largest effective free memory)", this.id, analytics.getId(), gpuId);
            return gpuId;
        }
        GpuCard gpu = chosen.get();
        if (log.isDebugEnabled()) {
            log.debug("Server {}: analytics {} -> GPU {} (effectiveFree={} GB, usage={}%, nvDec={}, runningAnalytics={})", this.id, analytics.getId(), gpu.getId(), this.effectiveFreeMemory(gpu, reserve), gpu.getGpuUsage(), gpu.getNvDec(), gpu.getRunnedAnalytics().get());
        }
        return gpu.getId();
    }

    static void carryOverStartDebounce(Server previous, Server next) {
        previous.graphics.forEach((gpuId, oldCard) -> {
            GpuCard fresh = next.graphics.get(gpuId);
            if (fresh == null) {
                return;
            }
            Instant mark = oldCard.getLastAnalyticsRun().get();
            fresh.getLastAnalyticsRun().accumulateAndGet(mark, (current, candidate) -> candidate.isAfter((Instant)current) ? candidate : current);
        });
    }

    private void setRunnedAnalytics() {
        Map<Integer, Long> GPURunningAnalytics = this.runningAnalytics.values().stream().collect(Collectors.groupingBy(RunningAnalytics::getGpuId, Collectors.counting()));
        this.graphics.values().forEach(gpu -> gpu.setRunnedAnalytics(GPURunningAnalytics.containsKey(gpu.getId()) ? ((Long)GPURunningAnalytics.get(gpu.getId())).intValue() : 0));
    }

    @Override
    public Instant lastHeartBeat() {
        return this.lastHeartBeat;
    }

    public String getServerTopic() {
        return String.format("server-%s", this.id);
    }

    public void updateLastGpuRunTime(int optimalGPU) {
        if (this.graphics.containsKey(optimalGPU)) {
            this.graphics.get(optimalGPU).updateLastAnalyticsRun();
        }
    }

    private boolean checkFreeMemory(double freeMemory, boolean serviceReady) {
        freeMemory -= 0.3;
        if (!serviceReady) {
            freeMemory -= 2.0;
        }
        return freeMemory > 1.0;
    }

    private boolean checkGpuFreeMemory(Analytics analytics, double gpuFreeMemory, boolean serviceReady) {
        if (analytics.getModule().getHardwareSettings().getDecoding() == HardwareDecode.nvidia) {
            gpuFreeMemory -= 0.3;
        }
        if (!serviceReady) {
            gpuFreeMemory -= 1.5;
        }
        return gpuFreeMemory >= 1.0;
    }

    @Override
    public String getId() {
        return this.id;
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

    public String getIpAddress() {
        return this.ipAddress;
    }

    public boolean isExternalAddressEnabled() {
        return this.isExternalAddressEnabled;
    }

    public String getExternalIpAddress() {
        return this.externalIpAddress;
    }

    public Integer getExternalPort() {
        return this.externalPort;
    }

    public String getExternalInterface() {
        return this.externalInterface;
    }

    public ConcurrentMap<String, String> getNetInterfaces() {
        return this.netInterfaces;
    }

    public Instant getLastHeartBeat() {
        return this.lastHeartBeat;
    }

    public String getVersion() {
        return this.version;
    }

    public ConcurrentHashMap<Integer, GpuCard> getGraphics() {
        return this.graphics;
    }

    public Set<RunningService> getRunningServices() {
        return this.runningServices;
    }

    public Set<AnalyticsTypes> getSupport() {
        return this.support;
    }

    public boolean isAnalyticsChanged() {
        return this.analyticsChanged;
    }

    public int getPreviewPort() {
        return this.previewPort;
    }

    public ConcurrentMap<Integer, Set<AnalyticsTypes>> getAnalyticsGpuBinding() {
        return this.analyticsGpuBinding;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Server)) {
            return false;
        }
        Server other = (Server)o;
        if (Double.compare(this.getCpuUsage(), other.getCpuUsage()) != 0) {
            return false;
        }
        if (Double.compare(this.getTotalMem(), other.getTotalMem()) != 0) {
            return false;
        }
        if (Double.compare(this.getFreeMem(), other.getFreeMem()) != 0) {
            return false;
        }
        if (this.isExternalAddressEnabled() != other.isExternalAddressEnabled()) {
            return false;
        }
        if (this.isAnalyticsChanged() != other.isAnalyticsChanged()) {
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
        String this$ipAddress = this.getIpAddress();
        String other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) {
            return false;
        }
        String this$externalIpAddress = this.getExternalIpAddress();
        String other$externalIpAddress = other.getExternalIpAddress();
        if (this$externalIpAddress == null ? other$externalIpAddress != null : !this$externalIpAddress.equals(other$externalIpAddress)) {
            return false;
        }
        String this$externalInterface = this.getExternalInterface();
        String other$externalInterface = other.getExternalInterface();
        if (this$externalInterface == null ? other$externalInterface != null : !this$externalInterface.equals(other$externalInterface)) {
            return false;
        }
        ConcurrentMap<String, String> this$netInterfaces = this.getNetInterfaces();
        ConcurrentMap<String, String> other$netInterfaces = other.getNetInterfaces();
        if (this$netInterfaces == null ? other$netInterfaces != null : !this$netInterfaces.equals(other$netInterfaces)) {
            return false;
        }
        Instant this$lastHeartBeat = this.getLastHeartBeat();
        Instant other$lastHeartBeat = other.getLastHeartBeat();
        if (this$lastHeartBeat == null ? other$lastHeartBeat != null : !((Object)this$lastHeartBeat).equals(other$lastHeartBeat)) {
            return false;
        }
        String this$version = this.getVersion();
        String other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) {
            return false;
        }
        ConcurrentHashMap<Integer, GpuCard> this$graphics = this.getGraphics();
        ConcurrentHashMap<Integer, GpuCard> other$graphics = other.getGraphics();
        if (this$graphics == null ? other$graphics != null : !((Object)this$graphics).equals(other$graphics)) {
            return false;
        }
        Collection<RunningAnalytics> this$runningAnalytics = this.getRunningAnalytics();
        Collection<RunningAnalytics> other$runningAnalytics = other.getRunningAnalytics();
        if (this$runningAnalytics == null ? other$runningAnalytics != null : !((Object)this$runningAnalytics).equals(other$runningAnalytics)) {
            return false;
        }
        Set<RunningService> this$runningServices = this.getRunningServices();
        Set<RunningService> other$runningServices = other.getRunningServices();
        if (this$runningServices == null ? other$runningServices != null : !((Object)this$runningServices).equals(other$runningServices)) {
            return false;
        }
        Set<AnalyticsTypes> this$support = this.getSupport();
        Set<AnalyticsTypes> other$support = other.getSupport();
        if (this$support == null ? other$support != null : !((Object)this$support).equals(other$support)) {
            return false;
        }
        ConcurrentMap<Integer, Set<AnalyticsTypes>> this$analyticsGpuBinding = this.getAnalyticsGpuBinding();
        ConcurrentMap<Integer, Set<AnalyticsTypes>> other$analyticsGpuBinding = other.getAnalyticsGpuBinding();
        return !(this$analyticsGpuBinding == null ? other$analyticsGpuBinding != null : !this$analyticsGpuBinding.equals(other$analyticsGpuBinding));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $cpuUsage = Double.doubleToLongBits(this.getCpuUsage());
        result = result * 59 + (int)($cpuUsage >>> 32 ^ $cpuUsage);
        long $totalMem = Double.doubleToLongBits(this.getTotalMem());
        result = result * 59 + (int)($totalMem >>> 32 ^ $totalMem);
        long $freeMem = Double.doubleToLongBits(this.getFreeMem());
        result = result * 59 + (int)($freeMem >>> 32 ^ $freeMem);
        result = result * 59 + (this.isExternalAddressEnabled() ? 79 : 97);
        result = result * 59 + (this.isAnalyticsChanged() ? 79 : 97);
        result = result * 59 + this.getPreviewPort();
        Integer $externalPort = this.getExternalPort();
        result = result * 59 + ($externalPort == null ? 43 : ((Object)$externalPort).hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $ipAddress = this.getIpAddress();
        result = result * 59 + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        String $externalIpAddress = this.getExternalIpAddress();
        result = result * 59 + ($externalIpAddress == null ? 43 : $externalIpAddress.hashCode());
        String $externalInterface = this.getExternalInterface();
        result = result * 59 + ($externalInterface == null ? 43 : $externalInterface.hashCode());
        ConcurrentMap<String, String> $netInterfaces = this.getNetInterfaces();
        result = result * 59 + ($netInterfaces == null ? 43 : $netInterfaces.hashCode());
        Instant $lastHeartBeat = this.getLastHeartBeat();
        result = result * 59 + ($lastHeartBeat == null ? 43 : ((Object)$lastHeartBeat).hashCode());
        String $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        ConcurrentHashMap<Integer, GpuCard> $graphics = this.getGraphics();
        result = result * 59 + ($graphics == null ? 43 : ((Object)$graphics).hashCode());
        Collection<RunningAnalytics> $runningAnalytics = this.getRunningAnalytics();
        result = result * 59 + ($runningAnalytics == null ? 43 : ((Object)$runningAnalytics).hashCode());
        Set<RunningService> $runningServices = this.getRunningServices();
        result = result * 59 + ($runningServices == null ? 43 : ((Object)$runningServices).hashCode());
        Set<AnalyticsTypes> $support = this.getSupport();
        result = result * 59 + ($support == null ? 43 : ((Object)$support).hashCode());
        ConcurrentMap<Integer, Set<AnalyticsTypes>> $analyticsGpuBinding = this.getAnalyticsGpuBinding();
        result = result * 59 + ($analyticsGpuBinding == null ? 43 : $analyticsGpuBinding.hashCode());
        return result;
    }

    public String toString() {
        return "Server(id=" + this.getId() + ", cpuUsage=" + this.getCpuUsage() + ", totalMem=" + this.getTotalMem() + ", freeMem=" + this.getFreeMem() + ", ipAddress=" + this.getIpAddress() + ", isExternalAddressEnabled=" + this.isExternalAddressEnabled() + ", externalIpAddress=" + this.getExternalIpAddress() + ", externalPort=" + this.getExternalPort() + ", externalInterface=" + this.getExternalInterface() + ", netInterfaces=" + String.valueOf(this.getNetInterfaces()) + ", lastHeartBeat=" + String.valueOf(this.getLastHeartBeat()) + ", version=" + this.getVersion() + ", graphics=" + String.valueOf(this.getGraphics()) + ", runningAnalytics=" + String.valueOf(this.getRunningAnalytics()) + ", runningServices=" + String.valueOf(this.getRunningServices()) + ", support=" + String.valueOf(this.getSupport()) + ", analyticsChanged=" + this.isAnalyticsChanged() + ", previewPort=" + this.getPreviewPort() + ", analyticsGpuBinding=" + String.valueOf(this.getAnalyticsGpuBinding()) + ")";
    }
}

