/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.answers.ws.servers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.http.answers.Ws.WsAnswer;
import com.incoresoft.vezha.dto.servers.FrontServerDTO;
import com.incoresoft.vezha.protocol.answers.ws.servers.ServerStateMessageType;
import com.incoresoft.vezha.protocol.answers.ws.servers.WSPacketType;
import org.jetbrains.annotations.NotNull;

public class ServerStateWsAnswer
extends WsAnswer {
    @JSONField(name="message_type")
    private final String messageType;
    private final JSONObject server;

    public ServerStateWsAnswer(@NotNull ServerStateMessageType messageType, @NotNull FrontServerDTO server) {
        super(WSPacketType.SERVER.toString());
        this.messageType = messageType.name();
        this.server = (JSONObject)JSON.toJSON(server);
    }

    public String getMessageType() {
        return this.messageType;
    }

    public JSONObject getServer() {
        return this.server;
    }
}

