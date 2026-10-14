/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.cameraanalytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.vezha.analytics.cameraanalytics.StoredImageRetrievalException;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.models.servers.ServerManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

public class StoredImageRequester {
    private final ServerManager serverManager;
    private final MessageQueueConnector messageQueueConnector;

    public Optional<InputStream> request(int analyticsId, String imagePath) {
        ResponseMessage responseMessage;
        Optional<Server> server = this.serverManager.tryFindServerByCameraAnalyticsId(analyticsId);
        if (server.isEmpty()) {
            return Optional.empty();
        }
        String subject = "request-image-from-camera-analytics-%s".formatted(server.orElseThrow().getId());
        Message response = this.messageQueueConnector.request(subject, new RequestMessage(subject, imagePath), Duration.ofSeconds(10L));
        if (response == null || response.getData() == null) {
            return Optional.empty();
        }
        try {
            responseMessage = Serialization.objectMapper().readValue(response.getData(), ResponseMessage.class);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (responseMessage.message() != null) {
            throw new StoredImageRetrievalException(responseMessage.message());
        }
        if (responseMessage.encodedImageBase64() == null) {
            return Optional.empty();
        }
        return Optional.of(new ByteArrayInputStream(Base64.getDecoder().decode(responseMessage.encodedImageBase64())));
    }

    @Inject
    public StoredImageRequester(ServerManager serverManager, MessageQueueConnector messageQueueConnector) {
        this.serverManager = serverManager;
        this.messageQueueConnector = messageQueueConnector;
    }

    private record RequestMessage(String subject, String imagePath) implements Message
    {
        @Override
        public byte[] getData() {
            try {
                return Serialization.objectMapper().writeValueAsBytes(new ImagePathDTO(this.imagePath));
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getReplyTo() {
            return null;
        }
    }

    private record ResponseMessage(@JsonProperty(value="image") String encodedImageBase64, @JsonProperty(value="message") String message) {
    }

    private record ImagePathDTO(@JsonProperty(value="image_path") String imagePath) {
    }
}

