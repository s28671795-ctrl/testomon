/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.cameraanalytics;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.cameraanalytics.InitializationRequestListener;
import com.incoresoft.vezha.dto.servers.cameraanalytics.InitializationDTO;
import com.incoresoft.vezha.frames.FramesManager;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializationRequestReceiver
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(InitializationRequestReceiver.class);
    private final Set<InitializationRequestListener> listeners = ConcurrentHashMap.newKeySet();
    private final MessageQueueConnector messageQueueConnector;
    private final Provider<StreamManagerImpl> streamsManager;
    private final AnalyticsManager analyticsManager;
    private final UsersManager usersManager;
    private final FramesManager framesManager;

    @Inject
    public void init(MessageQueueConnector messageQueueConnector) {
        messageQueueConnector.addListener(this);
    }

    @Override
    public String subject() {
        return "init-camera-analytics";
    }

    @Override
    public Message messageReceived(Message message) {
        this.handleInitializationRequest(JSON.parseObject(new String(message.getData(), StandardCharsets.UTF_8), InitializationDTO.class));
        return ResponseMessage.empty();
    }

    public void handleInitializationRequest(InitializationDTO initializationDTO) {
        Stream stream = this.streamsManager.get().get(UUID.fromString(initializationDTO.getCameraUUID()));
        if (StringUtils.isEmpty(initializationDTO.getCameraName())) {
            log.error("Camera name was empty. Initialization request will be skipped.");
            return;
        }
        if (stream != null) {
            this.streamsManager.get().setNewImage(stream, this.saveFrameImage(initializationDTO.getFrameImage()), initializationDTO.getResolution());
            this.streamsManager.get().rename(stream, initializationDTO.getCameraName());
            this.streamsManager.get().markAsEmbedded(stream);
            stream = this.streamsManager.get().get(UUID.fromString(initializationDTO.getCameraUUID()));
        } else {
            Path frameImagePath = this.saveFrameImage(initializationDTO.getFrameImage());
            stream = this.streamsManager.get().addStream(new StreamImpl(this.usersManager, initializationDTO, frameImagePath), false);
        }
        Optional<Analytics> analytics = this.findAnalytics(stream.getUUID(), initializationDTO.getAnalyticsType());
        if (analytics.isEmpty()) {
            this.createAnalytics(initializationDTO, stream);
        }
        this.messageQueueConnector.publish("camera-analytics-initialized-%s".formatted(initializationDTO.getServerId()), new byte[0]);
        for (InitializationRequestListener listener : this.listeners) {
            try {
                listener.onInitializationRequest(initializationDTO);
            }
            catch (Exception e) {
                log.error("Error while processing initialization request listeners", e);
            }
        }
    }

    private Path saveFrameImage(String frameImageBase64) {
        Path path = this.framesManager.generateOriginalImageFilePath();
        byte[] frameImage = Base64.getDecoder().decode(frameImageBase64);
        try (ByteArrayInputStream inputStream2 = new ByteArrayInputStream(frameImage);){
            Files.copy(inputStream2, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    private Optional<Analytics> findAnalytics(UUID streamUuid, AnalyticsTypes analyticsType) {
        Predicate<Analytics> filter2 = it -> it.getStreamUUID().equals(streamUuid) && it.getModule().getAnalyticsTypes().contains(analyticsType);
        return this.analyticsManager.getAll(filter2).stream().findFirst();
    }

    private void createAnalytics(InitializationDTO initializationDTO, Stream stream) {
        this.analyticsManager.add(new BaseAnalytics(stream, initializationDTO.getAnalyticsType(), initializationDTO.getServerId()));
    }

    public void addListener(InitializationRequestListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(InitializationRequestListener listener) {
        this.listeners.remove(listener);
    }

    @Inject
    public InitializationRequestReceiver(MessageQueueConnector messageQueueConnector, Provider<StreamManagerImpl> streamsManager, AnalyticsManager analyticsManager, UsersManager usersManager, FramesManager framesManager) {
        this.messageQueueConnector = messageQueueConnector;
        this.streamsManager = streamsManager;
        this.analyticsManager = analyticsManager;
        this.usersManager = usersManager;
        this.framesManager = framesManager;
    }
}

