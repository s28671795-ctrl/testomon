/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.middleware.analytics.streams.params.AxisStreamParams;
import com.incoresoft.middleware.analytics.streams.params.CamtraceStreamParams;
import com.incoresoft.middleware.analytics.streams.params.EmptyParams;
import com.incoresoft.middleware.analytics.streams.params.FileStreamParams;
import com.incoresoft.middleware.analytics.streams.params.GenetecStreamParams;
import com.incoresoft.middleware.analytics.streams.params.LuxriotStreamParams;
import com.incoresoft.middleware.analytics.streams.params.MilestoneStreamParams;
import com.incoresoft.middleware.analytics.streams.params.NxStreamParams;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;

public class StreamParamsSerializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T)StreamParamsSerializer.deserialize(parser.parseObject(String.class));
    }

    @NotNull
    public static StreamParams deserialize(String paramsJSONString) {
        StreamType streamType = JSON.parseObject(paramsJSONString).getObject("type", StreamType.class);
        switch (streamType) {
            case rtsp: 
            case http: 
            case ftp: 
            case https: 
            case hikvision: 
            case embedded: {
                return new EmptyParams(streamType);
            }
            case nx: {
                return JSON.parseObject(paramsJSONString, NxStreamParams.class);
            }
            case milestone: 
            case milestone_rtsp: {
                return JSON.parseObject(paramsJSONString, MilestoneStreamParams.class);
            }
            case file: 
            case smb_file: {
                return JSON.parseObject(paramsJSONString, FileStreamParams.class);
            }
            case luxriot: {
                return JSON.parseObject(paramsJSONString, LuxriotStreamParams.class);
            }
            case camtrace: {
                return JSON.parseObject(paramsJSONString, CamtraceStreamParams.class);
            }
            case axis_rtsp: {
                return JSON.parseObject(paramsJSONString, AxisStreamParams.class);
            }
            case genetec_rtsp: {
                return JSON.parseObject(paramsJSONString, GenetecStreamParams.class);
            }
        }
        throw new IllegalArgumentException(String.format("Cannot parse parameters for stream type \"%s\"", new Object[]{streamType}));
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

