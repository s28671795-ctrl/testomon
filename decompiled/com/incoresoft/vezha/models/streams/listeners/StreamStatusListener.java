/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.listeners;

import com.alibaba.fastjson.JSON;
import com.incoresoft.dto.streams.status.StreamStatusDTO;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.mq.Message;
import com.incoresoft.middleware.mq.MessageQueueListener;
import com.incoresoft.vezha.dto.streams.streamstatus.MQStreamStatusDTO;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import com.incoresoft.vezha.protocol.ws.ServersWSBroadcaster;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamStatusListener
implements MessageQueueListener {
    private static final Logger log = LoggerFactory.getLogger(StreamStatusListener.class);
    private final StreamManagerImpl streamManager;
    private final ServersWSBroadcaster serversWsBroadcaster;

    public StreamStatusListener(@NotNull StreamManagerImpl streamManager, @NotNull ServersWSBroadcaster serversWsBroadcaster) {
        this.streamManager = Objects.requireNonNull(streamManager);
        this.serversWsBroadcaster = Objects.requireNonNull(serversWsBroadcaster);
    }

    @Override
    public String subject() {
        return "streamStatus";
    }

    @Override
    public Message messageReceived(Message message) {
        MQStreamStatusDTO dto = JSON.parseObject(new String(message.getData()), MQStreamStatusDTO.class);
        Stream stream = this.streamManager.get(dto.getStreamId());
        if (stream == null) {
            log.warn("Received status for unknown stream with id {}", (Object)dto.getStreamId());
            return null;
        }
        if (stream instanceof StreamImpl) {
            ((StreamImpl)stream).setStatus(dto.getStatus());
        }
        this.streamManager.setStatus(stream.getId(), dto.getStatus());
        this.serversWsBroadcaster.broadcast(new StreamStatusDTO(dto.getStreamId(), dto.getStatus() == StreamStatus.online));
        return null;
    }
}

