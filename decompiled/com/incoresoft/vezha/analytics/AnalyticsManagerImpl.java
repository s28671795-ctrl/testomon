/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.incoresoft.crypto.Cipher;
import com.incoresoft.license.LicenseManager;
import com.incoresoft.license.model.LicenseGrabStatus;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsArchive;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.analytics.fileprocessing.VideoFileProgressTracker;
import com.incoresoft.system.disk.DiskSpaceManager;
import com.incoresoft.vezha.analytics.AnalyticsArchiveImpl;
import com.incoresoft.vezha.analytics.AnalyticsHeartbeatManager;
import com.incoresoft.vezha.analytics.AnalyticsQueue;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequester;
import com.incoresoft.vezha.analytics.errors.AnalyticsSideError;
import com.incoresoft.vezha.analytics.exceptions.AnalyticsIsNotStoppedException;
import com.incoresoft.vezha.analytics.exceptions.CannotStartCameraAnalyticsOnLocalS3Exception;
import com.incoresoft.vezha.analytics.exceptions.CannotStartCameraAnalyticsOnLocalStorageException;
import com.incoresoft.vezha.analytics.exceptions.NoAccessToModuleException;
import com.incoresoft.vezha.analytics.exceptions.NoAvailableLicenseException;
import com.incoresoft.vezha.analytics.exceptions.WrongSystemVersionException;
import com.incoresoft.vezha.analytics.listeners.AnalyticsErrorsListener;
import com.incoresoft.vezha.analytics.listeners.GetParamsRequestListener;
import com.incoresoft.vezha.analytics.listeners.LicenseAndDiskChangesListener;
import com.incoresoft.vezha.analytics.listeners.MiddlewareAnalyticsStatusListener;
import com.incoresoft.vezha.analytics.listeners.ServerActionListenerImpl;
import com.incoresoft.vezha.analytics.mq.metadata.AnalyticsTopicListenerManager;
import com.incoresoft.vezha.analytics.mq.service.AnalyticsServiceListenerManager;
import com.incoresoft.vezha.analytics.mq.state.AnalyticsStateListenersRegistry;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserveCalculator;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.analytics.status.AnalyticsHeartBeat;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.database.AnalyticsRepository;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsServerErrorsEvent;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsStatusChangedEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AnalyticsManagerImpl
implements AnalyticsManager {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsManagerImpl.class);
    private static final Set<String> LOCALHOSTS = Set.of("127.0.0.1", "localhost");
    private final Map<Integer, BaseAnalytics> analyticsMap = new ConcurrentHashMap<Integer, BaseAnalytics>();
    private final Map<Integer, Analytics> archivedAnalyticsMap = new ConcurrentHashMap<Integer, Analytics>();
    private final AnalyticsRepository repository;
    private final MessageQueueConnector mqConnector;
    private final LicenseManager licenseManager;
    private final LicenseUsageManager licenseUsageManager;
    private final ServersMonitor serversMonitor;
    private final StreamsManager streamsManager;
    private final Cipher cipher;
    private final AnalyticsStateListenersRegistry analyticsStateListenersRegistry;
    private final AnalyticsTopicListenerManager topicListenerManager;
    private final AnalyticsServiceListenerManager serviceListenerManager;
    private final AnalyticsWSBroadcaster analyticsWsBroadcaster;
    private final AnalyticsQueue analyticsQueue;
    private final AnalyticsHeartbeatManager analyticsHeartbeatManager;
    private final ScheduledExecutorService analyticsQueueExecutor;
    private final ScheduledExecutorService analyticsHeartbeatExecutor;
    private final InitializationRequester initializationRequester;
    private final VideoFileProgressTracker progressTracker;
    private final VezhaConfigManager configManager;
    private final LicenseAndDiskChangesListener licenseAndDiskChangesListener;

    @Inject
    public AnalyticsManagerImpl(@NotNull AnalyticsRepository repository, @NotNull MessageQueueConnector mqConnector, @NotNull LicenseManager licenseManager, @NotNull LicenseUsageManager licenseUsageManager, @NotNull ServersMonitor serversMonitor, @NotNull StreamsManager streamsManager, @NotNull Cipher cipher, @NotNull AnalyticsStateListenersRegistry analyticsStateListenersRegistry, @NotNull AnalyticsTopicListenerManager topicListenerService, @NotNull AnalyticsServiceListenerManager serviceListenerManager, @NotNull AnalyticsWSBroadcaster analyticsWsBroadcaster, @NotNull VezhaConfigManager configManager, @NotNull Provider<DiskSpaceManager> diskSpaceManagerProvider, @NotNull InitializationRequester initializationRequester, @NotNull VideoFileProgressTracker progressTracker, @NotNull LicenseAndDiskChangesListener licenseAndDiskChangesListener, @NotNull GpuMemoryReserveCalculator gpuMemoryReserveCalculator) {
        this.repository = Objects.requireNonNull(repository, "repository must be not null");
        this.mqConnector = Objects.requireNonNull(mqConnector, "MQ connector must be not null");
        this.licenseManager = Objects.requireNonNull(licenseManager, "license manager must be not null");
        this.licenseUsageManager = Objects.requireNonNull(licenseUsageManager, "license usage manager must be not null");
        this.serversMonitor = Objects.requireNonNull(serversMonitor, "servers monitor must be not null");
        this.streamsManager = Objects.requireNonNull(streamsManager, "streams manager must be not null");
        this.analyticsWsBroadcaster = Objects.requireNonNull(analyticsWsBroadcaster, "ws broadcaster must be not null");
        this.cipher = Objects.requireNonNull(cipher, "cipher must be not null");
        this.analyticsStateListenersRegistry = Objects.requireNonNull(analyticsStateListenersRegistry, "state listeners registry must be not null");
        this.topicListenerManager = Objects.requireNonNull(topicListenerService, "topic listener manager must be not null");
        this.serviceListenerManager = Objects.requireNonNull(serviceListenerManager, "service listener manager must be not null");
        this.progressTracker = Objects.requireNonNull(progressTracker, "progressTracker must be not null");
        this.configManager = Objects.requireNonNull(configManager, "configManager must be not null");
        this.initializationRequester = Objects.requireNonNull(initializationRequester, "initializationRequester must be not null");
        this.licenseAndDiskChangesListener = Objects.requireNonNull(licenseAndDiskChangesListener, "license and disk changes listener must be not null");
        Optional.ofNullable((VezhaConfig)configManager.getConfig()).ifPresent(config -> GpuCard.setStartDebounceSeconds(config.getGpuStartDebounceSeconds()));
        this.analyticsQueue = new AnalyticsQueue(mqConnector, this, serversMonitor, licenseUsageManager, diskSpaceManagerProvider, configManager, analyticsWsBroadcaster, gpuMemoryReserveCalculator);
        this.analyticsHeartbeatManager = new AnalyticsHeartbeatManager(this, configManager, serversMonitor);
        this.analyticsQueueExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t2 = new Thread(r, "analytics-queue");
            t2.setDaemon(true);
            return t2;
        });
        this.analyticsHeartbeatExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t2 = new Thread(r, "analytics-heartbeat");
            t2.setDaemon(true);
            return t2;
        });
    }

    private void createListeners() {
        this.licenseManager.addOnLicenseChangeListener(this.licenseAndDiskChangesListener);
        this.licenseManager.addOnLicenseChangeListener(this.licenseUsageManager);
        this.mqConnector.addListener(new MiddlewareAnalyticsStatusListener(this.analyticsWsBroadcaster));
        this.mqConnector.addListener(new GetParamsRequestListener(this, this.streamsManager, this.licenseUsageManager, this.configManager));
        this.mqConnector.addListener(new AnalyticsErrorsListener(this, this.serversMonitor));
        this.serversMonitor.addServerActionListener(new ServerActionListenerImpl(this, this.initializationRequester, this.serversMonitor));
        this.addAnalyticsStateListener(this.analyticsQueue);
        this.addAnalyticsStateListener(this.licenseUsageManager);
    }

    public void init() {
        this.createListeners();
        this.analyticsQueueExecutor.scheduleWithFixedDelay(this.analyticsQueue, 0L, 200L, TimeUnit.MILLISECONDS);
        this.analyticsHeartbeatExecutor.scheduleWithFixedDelay(this.analyticsHeartbeatManager, 10000L, 1000L, TimeUnit.MILLISECONDS);
    }

    public void loadByPlugin(String pluginName) {
        ArrayList<BaseAnalytics> list = new ArrayList<BaseAnalytics>();
        try {
            list.addAll(this.repository.getItems(pluginName));
        }
        catch (Exception e) {
            log.error("Error while loading analytics from db", e);
        }
        this.analyticsMap.entrySet().removeIf(it -> Objects.equals(((BaseAnalytics)it.getValue()).getPluginName(), pluginName));
        this.initAll(list);
        this.analyticsQueueExecutor.schedule(this::runProcessingAnalytics, 30L, TimeUnit.SECONDS);
    }

    private void initAll(List<BaseAnalytics> list) {
        list.stream().filter(a -> a.getStatus() == AnalyticsStatus.deleted).forEach(a -> this.archivedAnalyticsMap.put(a.getId(), (Analytics)a));
        list.stream().filter(a -> a.getStatus() != AnalyticsStatus.deleted).peek(a -> this.analyticsMap.put(a.getId(), (BaseAnalytics)a)).filter(a -> a.getStatus() != AnalyticsStatus.stopped).filter(a -> a.getStatus() != AnalyticsStatus.paused).forEach(this::setProcessingStatus);
    }

    private void runProcessingAnalytics() {
        log.info("runProcessingAnalytics");
        this.analyticsMap.values().stream().filter(a -> a.getStatus() == AnalyticsStatus.processing).forEach(this::enqueue);
    }

    @NotNull
    public Map<Integer, Analytics> getArchivedAnalyticsMap() {
        return Collections.unmodifiableMap(this.archivedAnalyticsMap);
    }

    public Analytics addAnalytics(@NotNull Analytics analytics) throws IllegalArgumentException {
        Objects.requireNonNull(analytics, "analytics must be not null");
        BaseAnalytics added = this.repository.add(analytics);
        if (added != null) {
            this.analyticsMap.put(added.getId(), added);
        }
        return added;
    }

    @Override
    public Analytics update(@NotNull Analytics analytics) throws IllegalStateException, IllegalArgumentException {
        Objects.requireNonNull(analytics, "analytics must be not null");
        if (analytics.getStatus() != AnalyticsStatus.stopped) {
            throw new AnalyticsIsNotStoppedException(analytics.getId());
        }
        BaseAnalytics updatedAnalytics = Optional.of(analytics).map(Analytics::getId).map(this::get).map(it -> (BaseAnalytics)it).map(it -> new BaseAnalytics(analytics, (BaseAnalytics)it)).orElseThrow(RuntimeException::new);
        this.repository.update(updatedAnalytics);
        this.analyticsMap.put(updatedAnalytics.getId(), this.repository.get(updatedAnalytics.getId()).orElseThrow());
        return updatedAnalytics;
    }

    @Override
    public Analytics delete(Integer id) {
        return this.delete(Objects.requireNonNull(this.analyticsMap.get(id)));
    }

    @Override
    public Analytics delete(@NotNull Analytics analytics) {
        Objects.requireNonNull(analytics, "analytics must be not null");
        if (analytics.getStatus() != AnalyticsStatus.stopped) {
            throw new AnalyticsIsNotStoppedException(analytics.getId());
        }
        this.analyticsMap.remove(analytics.getId());
        Analytics archived = this.repository.archive(analytics.getId());
        this.archivedAnalyticsMap.put(analytics.getId(), archived);
        this.analyticsStateListenersRegistry.onStatusChanged(archived);
        return analytics;
    }

    private void start(@NotNull Analytics inAnalytics) {
        Objects.requireNonNull(inAnalytics, "analytics must be not null");
        Stream analyticsStream = this.streamsManager.get(inAnalytics.getStreamUUID());
        if (analyticsStream.getType() == StreamType.embedded) {
            if (Objects.equals(this.configManager.getStorage().getProperties().get("type"), "local")) {
                throw new CannotStartCameraAnalyticsOnLocalStorageException();
            }
            URI uri = URI.create(this.configManager.getStorage().getProperties().get("url"));
            if (LOCALHOSTS.contains(uri.getHost())) {
                throw new CannotStartCameraAnalyticsOnLocalS3Exception();
            }
        }
        if (!(inAnalytics instanceof BaseAnalytics)) {
            throw new IllegalArgumentException("wrong Analytics class");
        }
        BaseAnalytics analytics = (BaseAnalytics)inAnalytics;
        if (analytics.getStatus() != AnalyticsStatus.stopped) {
            return;
        }
        LicenseGrabStatus status = this.licenseUsageManager.checkGrabLicense(analytics);
        if (status == LicenseGrabStatus.NOT_LICENSED) {
            throw new NoAvailableLicenseException(analytics.getPluginName());
        }
        if (status == LicenseGrabStatus.UNAVAILABLE) {
            throw new NoAccessToModuleException(analytics.getPluginName());
        }
        if (status == LicenseGrabStatus.MAINTENANCE_BLOCKED) {
            throw new WrongSystemVersionException();
        }
        this.enqueue(analytics);
    }

    public void removeAndStop(int id, Server server) {
        this.analyticsQueue.removeAndStop(id, server);
    }

    public void removeAndStop(BaseAnalytics analytics) {
        Optional.ofNullable(analytics.getHeartBeatValue()).map(AnalyticsHeartBeat::getServerId).map(this.serversMonitor::getServerById).ifPresent(server -> this.removeAndStop(analytics, (Server)server));
        analytics.stopAnalytics();
    }

    public void removeAndStop(BaseAnalytics analytics, Server server) {
        this.analyticsQueue.removeAndStop(analytics, server);
    }

    @Override
    public void updateSignature(Analytics analytics) {
        Optional.ofNullable(analytics).map(it -> (BaseAnalytics)it).ifPresent(this.analyticsQueue::updateSignature);
    }

    public void initEncryption(Analytics analytics) {
        BaseAnalytics baseAnalytics = (BaseAnalytics)analytics;
        baseAnalytics.initEncryptionKey();
        this.repository.update(baseAnalytics);
    }

    public void enqueue(BaseAnalytics analytics) {
        this.setProcessingStatus(analytics);
        analytics.setHeartBeatValue(null);
        this.analyticsQueue.offer(analytics);
    }

    public void unpause(int id) {
        BaseAnalytics analytics = this.analyticsMap.get(id);
        if (analytics != null) {
            this.enqueue(analytics);
        }
    }

    public boolean isEmbeddedStream(BaseAnalytics analytics) {
        return ((Stream)this.streamsManager.get(analytics.getStreamId())).getType() == StreamType.embedded;
    }

    public void setStartedStatus(BaseAnalytics analytics) {
        Optional.ofNullable(analytics.getHeartBeatValue()).map(AnalyticsHeartBeat::getServerId).map(this.serversMonitor::getServerById).ifPresent(server -> {
            analytics.setStartedStatus((Server)server);
            this.changeStatus(analytics);
        });
    }

    private void setProcessingStatus(BaseAnalytics analytics) {
        analytics.setProcessingStatus();
        this.changeStatus(analytics);
    }

    private void changeStatus(BaseAnalytics analytics) {
        this.repository.update(analytics);
        this.analyticsWsBroadcaster.broadcast(new AnalyticsStatusChangedEvent(analytics), user -> user.hasAccessTo(analytics));
        this.analyticsStateListenersRegistry.onStatusChanged(analytics);
    }

    public void setAnalyticsSideErrors(BaseAnalytics analytics, Set<AnalyticsSideError> errors2) {
        analytics.setAnalyticsSideErrors(errors2);
        this.broadcastAnalyticsErrors(analytics);
    }

    private void broadcastAnalyticsErrors(BaseAnalytics analytics) {
        Thread.startVirtualThread(() -> {
            try {
                this.analyticsWsBroadcaster.broadcast(new AnalyticsServerErrorsEvent(analytics), user -> user.hasAccessTo(analytics));
            }
            catch (Exception e) {
                log.error("Error broadcasting analytics errors for analytics {}", (Object)analytics.getId(), (Object)e);
            }
        });
    }

    @Override
    public Analytics get(Integer id) {
        return this.analyticsMap.get(id);
    }

    @Override
    public Collection<? extends Analytics> getAll() {
        return this.getAll((Predicate<Analytics>)null);
    }

    @Override
    public Collection<Analytics> getAll(boolean showDeleted) {
        if (showDeleted) {
            return CollectionUtils.union(this.getAll(), this.getAnalyticsArchive().getAll());
        }
        return this.getAll().stream().map(it -> it).toList();
    }

    @Override
    public Analytics add(Analytics analytics) {
        return this.addAnalytics(analytics);
    }

    public void removeListeners(VezhaExtension extension) {
        this.topicListenerManager.unregisterExtension(extension);
        this.serviceListenerManager.unregisterExtension(extension);
    }

    @Override
    public Collection<Analytics> getAll(Predicate<Analytics> predicate) {
        return this.analyticsMap.values().stream().filter(it -> predicate == null || predicate.test((Analytics)it)).map(it -> it).toList();
    }

    @Override
    public Analytics get(UUID uuid) {
        return this.getAll().stream().filter(a -> a.getUUID().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public Collection<Analytics> getAll(User user) {
        if (!user.hasPermission(CorePermissions.ViewStreams)) {
            return Collections.emptyList();
        }
        Set allowedStreams = this.streamsManager.getAll(user).stream().map(Stream::getUUID).collect(Collectors.toSet());
        return this.getAll().stream().filter(user::hasAccessTo).filter(a -> allowedStreams.contains(a.getStreamUUID())).map(it -> it).toList();
    }

    @Override
    public void start(int id) {
        BaseAnalytics analytics = this.analyticsMap.get(id);
        if (analytics != null) {
            this.start(analytics);
        }
    }

    @Override
    public void stop(int id) {
        BaseAnalytics analytics = this.analyticsMap.get(id);
        if (analytics == null) {
            return;
        }
        analytics.setStoppedStatus();
        this.changeStatus(analytics);
        analytics.stopAnalytics();
        this.progressTracker.resetProgress(analytics.getId());
    }

    @Override
    public void pause(int id) {
        BaseAnalytics analytics = this.analyticsMap.get(id);
        if (analytics == null) {
            return;
        }
        this.removeAndStop(analytics);
        analytics.setPausedStatus();
        this.changeStatus(analytics);
        analytics.pauseAnalytics();
    }

    @Override
    public void startAll(Collection<Integer> ids) {
        for (Integer id : ids) {
            try {
                this.start(id);
            }
            catch (Exception e) {
                log.error("Failed to start analytics {}", (Object)id, (Object)e);
            }
        }
    }

    @Override
    public void stopAll(Collection<Integer> ids) {
        for (Integer id : ids) {
            try {
                this.stop(id);
            }
            catch (Exception e) {
                log.error("Failed to stop analytics {}", (Object)id, (Object)e);
            }
        }
    }

    @Override
    public void addAnalyticsStateListener(AnalyticsStateListener listener) {
        this.analyticsStateListenersRegistry.addListener(listener);
    }

    @Override
    public void removeAnalyticsStateListener(AnalyticsStateListener listener) {
        this.analyticsStateListenersRegistry.removeListener(listener);
    }

    @Override
    public <T extends ServiceMetadata> void addAnalyticsServiceListener(ServiceMetadataListener<T> listener) {
        this.serviceListenerManager.addAnalyticsServiceListener(listener);
    }

    @Override
    public <T extends ServiceMetadata> void removeAnalyticsServiceListener(ServiceMetadataListener<T> listener) {
        this.serviceListenerManager.removeAnalyticsServiceListener(listener);
    }

    @Override
    public <T extends AnalyticsMetadata> void addAnalyticsMetadataListener(AnalyticsMetadataListener<T> listener) {
        this.topicListenerManager.addAnalyticsMetadataListener(listener);
    }

    @Override
    public <T extends AnalyticsMetadata> void removeAnalyticsMetadataListener(AnalyticsMetadataListener<T> listener) {
        this.topicListenerManager.removeAnalyticsMetadataListener(listener);
    }

    @Override
    public AnalyticsArchive getAnalyticsArchive() {
        return new AnalyticsArchiveImpl(this, this.streamsManager);
    }

    public void updateServer(BaseAnalytics analytics) {
        AnalyticsStatusImpl status = analytics.getAnalyticsStatus();
        if (status == null || status.getServerValue() == null) {
            return;
        }
        this.removeAndStop(analytics, status.getServerValue());
        AnalyticsHeartBeat hb = analytics.getHeartBeatValue();
        if (hb == null) {
            return;
        }
        Server srv = this.serversMonitor.getServerById(hb.getServerId());
        if (srv == null) {
            this.enqueue(analytics);
            return;
        }
        analytics.changeStatus(AnalyticsStatus.started, srv, hb.getGpuId(), status.getStartInstant(), false);
        this.changeStatus(analytics);
    }

    public void renameAnalyticsByStreams(Map<UUID, String> streamMap, DSLContext transaction) {
        Map<Integer, String> analyticsRenameMap = this.getAll((Analytics it) -> streamMap.containsKey(it.getStreamUUID())).stream().collect(Collectors.toMap(Analytics::getId, it -> (String)streamMap.get(it.getStreamUUID())));
        this.repository.renameAll(analyticsRenameMap, transaction);
        for (Map.Entry<Integer, String> entry : analyticsRenameMap.entrySet()) {
            Integer analyticsId = entry.getKey();
            String newName = entry.getValue();
            this.analyticsMap.computeIfPresent(analyticsId, (k, a) -> a.withName(newName));
        }
    }

    public Set<Integer> removeArchivedAnalytics(Set<Integer> analyticsToDelete) {
        Set<Integer> removedAnalytics = this.repository.removeArchivedAnalytics(analyticsToDelete);
        this.archivedAnalyticsMap.keySet().removeIf(removedAnalytics::contains);
        return removedAnalytics;
    }

    public Cipher getCipher() {
        return this.cipher;
    }

    public AnalyticsQueue getAnalyticsQueue() {
        return this.analyticsQueue;
    }
}

