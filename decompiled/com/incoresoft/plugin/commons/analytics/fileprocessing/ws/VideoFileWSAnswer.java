/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.fileprocessing.ws;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.plugin.commons.http.answers.Ws.WsAnswer;

public class VideoFileWSAnswer
extends WsAnswer {
    @JSONField(name="data")
    protected final JSONObject data = new JSONObject();

    public VideoFileWSAnswer(String method2, VideoFileProgress message) {
        super(method2);
        this.data.put("stream_id", (Object)message.getStreamId());
        this.data.put("va_id", (Object)message.getVaId());
    }

    public JSONObject getData() {
        return this.data;
    }
}

