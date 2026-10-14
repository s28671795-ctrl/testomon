/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.google.inject.Inject;
import com.incoresoft.Utils.Pair;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import com.incoresoft.middleware.analytics.streams.authorization.BasicAuth;
import com.incoresoft.middleware.analytics.streams.params.MilestoneStreamParams;
import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.SnapshotCreationException;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamArchive;
import com.incoresoft.middleware.model.stream.StreamListener;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.onvif.OnvifManager;
import com.incoresoft.onvif.models.OnvifDevice;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.onvif.parsers.GetMediaProfilesParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequester;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.database.AnalyticsRepository;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.dto.onvif.OnvifDTO;
import com.incoresoft.vezha.dto.onvif.OnvifObjectDTO;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import com.incoresoft.vezha.dto.streams.StreamSearchParams;
import com.incoresoft.vezha.frames.FramesManager;
import com.incoresoft.vezha.models.streams.StreamArchiveImpl;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.StreamRenameItem;
import com.incoresoft.vezha.models.streams.StreamSnapshotPathManager;
import com.incoresoft.vezha.models.streams.exceptions.OnvifRequestFailedException;
import com.incoresoft.vezha.models.streams.exceptions.StreamFrameGrabException;
import com.incoresoft.vezha.models.streams.exceptions.StreamWithNameExistsException;
import com.incoresoft.vezha.models.streams.exceptions.StreamWithThisUriExistsException;
import com.incoresoft.vezha.models.streams.groups.FolderImpl;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import com.incoresoft.vezha.models.streams.groups.StreamGroupManager;
import com.incoresoft.vezha.models.streams.listeners.StreamInfoListener;
import com.incoresoft.vezha.models.streams.listeners.StreamStatusListener;
import com.incoresoft.vezha.models.streams.model.FrameGrabManager;
import com.incoresoft.vezha.models.streams.model.FrameGrabResultImpl;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import com.incoresoft.vezha.models.streams.onvif.ImportResult;
import com.incoresoft.vezha.models.streams.onvif.OnvifStream;
import com.incoresoft.vezha.models.streams.onvif.SuccessfullyAddedStream;
import com.incoresoft.vezha.models.streams.strategies.StreamDeletionStrategy;
import com.incoresoft.vezha.protocol.ws.ServersWSBroadcaster;
import io.javalin.http.NotFoundResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamManagerImpl
implements StreamsManager {
    private static final Logger log = LoggerFactory.getLogger(StreamManagerImpl.class);
    private final Map<Integer, Stream> archivedStreams = new ConcurrentHashMap<Integer, Stream>();
    private final Map<String, OnvifStream> onvifStreams = new ConcurrentHashMap<String, OnvifStream>();
    private final Set<StreamListener> streamListeners = new CopyOnWriteArraySet<StreamListener>();
    private final StreamsRepository repository;
    private final OnvifManager onvifManager;
    private final FrameGrabManager frameGrabManager;
    private final AnalyticsManagerImpl analyticsManager;
    private final StreamGroupManager streamGroupManager;
    private final StreamRestrictions.Converter streamRestrictionsConverter;
    private final Storage storage;
    private final MessageQueueConnector messageQueueConnector;
    private final ServersWSBroadcaster serversWsBroadcaster;
    private final UsersManager usersManager;
    private final VezhaConfigManager configManager;
    private final StreamDeletionStrategy streamDeletionStrategy;
    private final AnalyticsRepository analyticsRepository;
    private final DSLContext context;
    private final InitializationRequester initializationRequester;
    private final FramesManager framesManager;
    private final StreamSnapshotPathManager snapshotPathManager;

    public void init() {
        this.subscribeToStreams();
        this.initializeStreams();
    }

    private void subscribeToStreams() {
        this.messageQueueConnector.addListener(new StreamStatusListener(this, this.serversWsBroadcaster));
        this.messageQueueConnector.addListener(new StreamInfoListener(this, this.repository));
    }

    private void initializeStreams() {
        List<Stream> archivedStreamsList = this.repository.getAll(true);
        archivedStreamsList.forEach(stream -> this.archivedStreams.put(stream.getId(), (Stream)stream));
    }

    @Override
    public Stream get(UUID streamUUID) {
        return this.repository.get(streamUUID).orElse(null);
    }

    @Override
    public Collection<? extends Stream> getAll(User user) {
        return this.getAll().stream().filter(user::hasAccessTo).filter(it -> it.getRestrictions().isAllowed(user, CorePermissions.ViewStreams)).toList();
    }

    @Override
    public String createSnapshot(UUID streamUUID) {
        Stream stream = this.get(streamUUID);
        if (stream == null) {
            return null;
        }
        try {
            Stream updated = this.updateStreamFrame(stream);
            File originalImageFile = this.framesManager.getOriginalImagePath(updated.getImage()).toFile();
            String storagePath = this.snapshotPathManager.getSnapshotStoragePath(stream, originalImageFile);
            Files.createDirectories(Path.of(storagePath, new String[0]).getParent(), new FileAttribute[0]);
            this.storage.save(storagePath, originalImageFile);
            return storagePath;
        }
        catch (IOException | RuntimeException e) {
            log.error("Creating snapshot for stream {} was failed", (Object)streamUUID, (Object)e);
            throw new SnapshotCreationException(e);
        }
    }

    public Pair<Long, List<StreamImpl>> getAll(User user, StreamSearchParams searchParams, PaginationParams paginationParams, SortParams sortParams) {
        return this.repository.getAll(user, searchParams, paginationParams, sortParams);
    }

    public Stream addStream(Stream stream, boolean tryToGrabFrame) throws StreamWithNameExistsException, StreamWithThisUriExistsException, StreamFrameGrabException {
        Objects.requireNonNull(stream);
        this.checkStream(stream);
        if (!(stream instanceof StreamImpl)) {
            FullStreamDTO streamDTO = new FullStreamDTO(stream);
            StreamRestrictions restrictions = new StreamRestrictions((User)this.usersManager.get(1));
            stream = new StreamImpl(streamDTO, restrictions);
        }
        if (tryToGrabFrame) {
            if (this.configManager.isVmsIntegrationEnabled()) {
                stream.update(FrameGrabResultImpl.getDefault());
            } else {
                stream.update(this.grabFrame(stream));
            }
        }
        return this.repository.add(stream);
    }

    public Stream updateStream(Stream stream) throws StreamWithNameExistsException, StreamWithThisUriExistsException, StreamFrameGrabException {
        Optional.ofNullable(stream).filter(it -> it.getId() > 0).ifPresentOrElse(this::checkStream, NotFoundResponse::new);
        if (!(stream instanceof StreamImpl)) {
            assert (stream != null);
            FullStreamDTO streamDTO = new FullStreamDTO(stream);
            StreamRestrictions restrictions = this.streamRestrictionsConverter.convert(new ItemRestrictionsDTO(1, null, null, null));
            stream = new StreamImpl(streamDTO, restrictions);
        }
        Stream old = this.get(stream.getId());
        if (!this.configManager.isVmsIntegrationEnabled()) {
            stream = this.updateStreamFrame(stream);
        }
        if (!old.getName().equals(stream.getName())) {
            this.updateWithAnalyticsRename(stream);
        } else {
            this.repository.update(stream);
        }
        return stream;
    }

    private void updateWithAnalyticsRename(Stream stream) {
        this.context.transaction(ctx -> {
            DSLContext transaction = ctx.dsl();
            this.repository.update(stream, transaction);
            this.analyticsManager.renameAnalyticsByStreams(Map.of(stream.getUUID(), stream.getName()), transaction);
        });
    }

    public void rename(Stream stream, String newName) {
        this.renameStreams(List.of(new StreamRenameItem(stream, newName)));
    }

    public List<Stream> renameStreams(List<StreamRenameItem> streams) {
        Map streamMap = streams.stream().filter(it -> !Objects.equals(it.oldName(), it.newName())).collect(Collectors.toMap(StreamRenameItem::uuid, Function.identity()));
        Set<String> newNames = streamMap.values().stream().map(StreamRenameItem::newName).collect(Collectors.toSet());
        List<Stream> existingStreams = this.repository.findByNames(newNames);
        if (!existingStreams.isEmpty()) {
            throw new StreamWithNameExistsException(existingStreams.stream().map(Stream::getName).collect(Collectors.toSet()));
        }
        return this.context.transactionResult(ctx -> {
            DSLContext transaction = ctx.dsl();
            Map<Integer, String> streamsRenameMap = streamMap.values().stream().collect(Collectors.toMap(StreamRenameItem::id, StreamRenameItem::newName));
            Map<UUID, String> streamUuidRenameMap = streamMap.values().stream().collect(Collectors.toMap(StreamRenameItem::uuid, StreamRenameItem::newName));
            this.repository.renameAll(streamsRenameMap, transaction);
            this.analyticsManager.renameAnalyticsByStreams(streamUuidRenameMap, transaction);
            return this.repository.getAll(streamsRenameMap.keySet(), transaction);
        });
    }

    public List<Stream> changeLocation(Set<UUID> streams, CoordinatesDTO location) {
        this.repository.updateLocation(streams, location);
        return this.repository.getAllByUuids(streams);
    }

    private void checkStream(Stream stream) throws StreamWithNameExistsException, StreamWithThisUriExistsException {
        this.streamWithThisName(stream.getName(), stream.getClientId()).filter(it -> it.getId() != stream.getId()).ifPresent(it -> {
            throw new StreamWithNameExistsException(Set.of(it.getName()));
        });
        if (stream.getUri() != null) {
            this.streamWithThisPath(stream.getUri().toString(), stream.getClientId()).filter(it -> it.getId() != stream.getId()).ifPresent(it -> {
                throw new StreamWithThisUriExistsException(it.getUri());
            });
        }
    }

    public Stream updateStreamFrame(Stream stream) {
        FrameGrabResultImpl frameGrabResult = this.grabFrame(stream);
        stream.update(frameGrabResult);
        return this.repository.update(stream);
    }

    private FrameGrabResultImpl grabFrame(Stream stream) throws StreamFrameGrabException {
        return stream.getType() == StreamType.embedded ? this.grabEmbeddedStreamFrame(stream) : this.frameGrabManager.grabFrame(stream);
    }

    private FrameGrabResultImpl grabEmbeddedStreamFrame(Stream stream) {
        Collection<Analytics> analyticsOfThisStream = this.analyticsManager.getAll((Analytics it) -> it.getStreamUUID().equals(stream.getUUID()));
        if (analyticsOfThisStream.isEmpty()) {
            throw new StreamFrameGrabException("No analytics for this stream");
        }
        String serverId = (String)analyticsOfThisStream.stream().findAny().map(Analytics::getAllowedServerIds).map(it -> it.stream().findAny()).flatMap(it -> it).orElseThrow();
        this.initializationRequester.requestInitializationAsync(serverId);
        Stream updatedStream = this.get(stream.getId());
        return new FrameGrabResultImpl(updatedStream.getResolution(), updatedStream.getCodec(), updatedStream.getImage(), null, null);
    }

    private Optional<Stream> streamWithThisName(String name, long clientId) {
        return this.getAll().stream().filter(it -> (long)it.getClientId() == clientId).filter(it -> StringUtils.equals(it.getName(), name)).map(it -> it).findFirst();
    }

    private Optional<Stream> streamWithThisPath(String path, long clientId) {
        return this.getAll().stream().filter(it -> (long)it.getClientId() == clientId).filter(it -> it.getUri() != null).filter(it -> path.equals(it.getUri().toString())).map(it -> it).findFirst();
    }

    public void setNewImage(Stream stream, Path frameImagePath, ResolutionDTO resolution) {
        ((StreamImpl)stream).setImage(frameImagePath.getFileName().toString());
        ((StreamImpl)stream).setResolution(new Resolution(resolution.getWidth(), resolution.getHeight()));
        this.repository.update(stream);
    }

    public List<OnvifMediaProfile> getOnvifStreams(@NotNull String host2, @Nullable Integer onvifPort, @NotNull Integer rtspPort, @NotNull String userName, @NotNull String password) {
        OnvifDevice device;
        OnvifResponse response;
        if (onvifPort != null) {
            host2 = (String)host2 + ":" + onvifPort;
        }
        if (!(response = this.onvifManager.getMediaProfiles(device = new OnvifDevice((String)host2, userName, password))).isSuccess()) {
            throw new OnvifRequestFailedException(response);
        }
        return new GetMediaProfilesParser().parse(response).stream().peek(it -> this.onvifStreams.put(it.getToken(), new OnvifStream(it.getToken(), it.getName(), rtspPort))).toList();
    }

    public ImportResult importOnvifStreams(User user, OnvifDTO onvifDTO) {
        ArrayList<SuccessfullyAddedStream> imported = new ArrayList<SuccessfullyAddedStream>();
        ArrayList<OnvifStream> failed = new ArrayList<OnvifStream>();
        String host2 = URLDecoder.decode(onvifDTO.host(), Charset.defaultCharset());
        String userName = onvifDTO.user();
        String password = URLDecoder.decode(onvifDTO.password(), Charset.defaultCharset());
        for (OnvifObjectDTO stream : onvifDTO.streams()) {
            OnvifStream onvifStream = this.onvifStreams.get(stream.token());
            try {
                MilestoneStreamParams streamParams = new MilestoneStreamParams(StreamType.milestone, stream.token());
                BasicAuth basicAuth = new BasicAuth(userName, password);
                FullStreamDTO streamDTO = FullStreamDTO.builder().name(onvifStream.name()).host(host2).port(onvifStream.rtspPort()).path(String.format("/live/%s", stream)).params(streamParams).auth(basicAuth).clientId(user.getClientId()).parentId(stream.parentId()).direction(0).build();
                imported.add(new SuccessfullyAddedStream(this.addStream(new StreamImpl(streamDTO, new StreamRestrictions()), true), onvifStream.token()));
            }
            catch (Exception ex) {
                log.error("Cannot import Onvif stream, name: {}, id: {}", onvifStream, stream, ex);
                failed.add(onvifStream);
            }
        }
        return new ImportResult(imported, failed);
    }

    @Override
    public Stream add(Stream stream) throws StreamWithNameExistsException, StreamWithThisUriExistsException, StreamFrameGrabException {
        return this.addStream(stream, true);
    }

    @Override
    public Stream update(Stream stream) throws StreamWithNameExistsException, StreamWithThisUriExistsException, StreamFrameGrabException {
        return this.updateStream(stream);
    }

    @Override
    public Stream delete(Integer id) {
        Stream stream = this.get(id);
        return this.delete(stream);
    }

    @Override
    public Stream delete(Stream stream) {
        if (stream == null) {
            return null;
        }
        this.streamDeletionStrategy.deleteStreams(Set.of(Integer.valueOf(stream.getId())));
        ListenersUtils.triggerListeners(listener -> listener.onStreamDeleted(stream), this.streamListeners);
        return stream;
    }

    public void deleteAllByUuids(Set<UUID> uuids) {
        this.streamDeletionStrategy.deleteStreamsByUuids(uuids);
    }

    @Override
    public Stream get(Integer id) {
        return this.repository.get(id).orElse(null);
    }

    @Override
    public Collection<? extends Stream> getAll() {
        return this.repository.getAll(false);
    }

    public void markAsEmbedded(Stream stream) {
        this.repository.markAsEmbedded(stream);
    }

    public void setStatus(int id, StreamStatus status) {
        this.repository.setStatus(id, status);
    }

    @Override
    public List<Folder> getStreamFolders() {
        return this.streamGroupManager.getGroups().stream().map(it -> this.buildFolder(it.getId())).toList();
    }

    @Override
    public Folder getStreamFolder(int folderId) {
        return this.buildFolder(folderId);
    }

    @NotNull
    private Folder buildFolder(int folderId) {
        StreamGroup streamGroup = Optional.ofNullable(this.streamGroupManager.get(folderId)).orElse(StreamGroup.ROOT);
        Folder parent = StreamGroup.ROOT.getId() != streamGroup.getId() ? this.buildFolder(streamGroup.getParentId()) : null;
        return new FolderImpl(streamGroup.getId(), streamGroup.getName(), parent, streamGroup.getClientId());
    }

    @Override
    public StreamArchive getStreamsArchive() {
        return new StreamArchiveImpl(this.archivedStreams);
    }

    @Override
    public void addListener(StreamListener listener) {
        this.streamListeners.add(listener);
    }

    @Override
    public void removeListener(StreamListener listener) {
        this.streamListeners.remove(listener);
    }

    public Map<Integer, Stream> getArchivedStreams() {
        return this.archivedStreams;
    }

    public Map<String, OnvifStream> getOnvifStreams() {
        return this.onvifStreams;
    }

    public Set<StreamListener> getStreamListeners() {
        return this.streamListeners;
    }

    public StreamsRepository getRepository() {
        return this.repository;
    }

    public OnvifManager getOnvifManager() {
        return this.onvifManager;
    }

    public FrameGrabManager getFrameGrabManager() {
        return this.frameGrabManager;
    }

    public AnalyticsManagerImpl getAnalyticsManager() {
        return this.analyticsManager;
    }

    public StreamGroupManager getStreamGroupManager() {
        return this.streamGroupManager;
    }

    public StreamRestrictions.Converter getStreamRestrictionsConverter() {
        return this.streamRestrictionsConverter;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public MessageQueueConnector getMessageQueueConnector() {
        return this.messageQueueConnector;
    }

    public ServersWSBroadcaster getServersWsBroadcaster() {
        return this.serversWsBroadcaster;
    }

    public UsersManager getUsersManager() {
        return this.usersManager;
    }

    public VezhaConfigManager getConfigManager() {
        return this.configManager;
    }

    public StreamDeletionStrategy getStreamDeletionStrategy() {
        return this.streamDeletionStrategy;
    }

    public AnalyticsRepository getAnalyticsRepository() {
        return this.analyticsRepository;
    }

    public DSLContext getContext() {
        return this.context;
    }

    public InitializationRequester getInitializationRequester() {
        return this.initializationRequester;
    }

    public FramesManager getFramesManager() {
        return this.framesManager;
    }

    public StreamSnapshotPathManager getSnapshotPathManager() {
        return this.snapshotPathManager;
    }

    @Inject
    public StreamManagerImpl(StreamsRepository repository, OnvifManager onvifManager, FrameGrabManager frameGrabManager, AnalyticsManagerImpl analyticsManager, StreamGroupManager streamGroupManager, StreamRestrictions.Converter streamRestrictionsConverter, Storage storage, MessageQueueConnector messageQueueConnector, ServersWSBroadcaster serversWsBroadcaster, UsersManager usersManager, VezhaConfigManager configManager, StreamDeletionStrategy streamDeletionStrategy, AnalyticsRepository analyticsRepository, DSLContext context, InitializationRequester initializationRequester, FramesManager framesManager, StreamSnapshotPathManager snapshotPathManager) {
        this.repository = repository;
        this.onvifManager = onvifManager;
        this.frameGrabManager = frameGrabManager;
        this.analyticsManager = analyticsManager;
        this.streamGroupManager = streamGroupManager;
        this.streamRestrictionsConverter = streamRestrictionsConverter;
        this.storage = storage;
        this.messageQueueConnector = messageQueueConnector;
        this.serversWsBroadcaster = serversWsBroadcaster;
        this.usersManager = usersManager;
        this.configManager = configManager;
        this.streamDeletionStrategy = streamDeletionStrategy;
        this.analyticsRepository = analyticsRepository;
        this.context = context;
        this.initializationRequester = initializationRequester;
        this.framesManager = framesManager;
        this.snapshotPathManager = snapshotPathManager;
    }
}

