/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.listeners;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.plugin.commons.mq.ResponseMessage;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.models.streams.StreamImpl;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamInfoListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(StreamInfoListener.class);
    private final StreamsManager streamsManager;
    private final StreamsRepository repository;

    public StreamInfoListener(@NotNull StreamsManager streamsManager, @NotNull StreamsRepository repository) {
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public String subject() {
        return "streamInfo";
    }

    @Override
    public Message messageReceived(Message message) {
        JSONObject messageJSON = JSONObject.parseObject(new String(message.getData()));
        JSONObject streamInfo = messageJSON.getJSONObject("streamInfo");
        if (streamInfo == null) {
            log.error("Cannot handle streamInfo. \"streamInfo\" object is not present.");
            return ResponseMessage.empty();
        }
        long streamId = streamInfo.getLong("stream_id");
        Stream stream = (Stream)this.streamsManager.get(Long.valueOf(streamId).intValue());
        if (stream == null) {
            log.error("Cannot handle streamInfo for stream {}. Stream with this id does not exist.", (Object)streamId);
            return ResponseMessage.empty();
        }
        if (stream instanceof StreamImpl) {
            Resolution resolution = new Resolution(streamInfo.getInteger("width"), streamInfo.getInteger("height"));
            ((StreamImpl)stream).setResolution(resolution);
            this.repository.update(stream);
        }
        return ResponseMessage.empty();
    }
}

