/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;
import com.incoresoft.vezha.backup.ws.message.BackupWsMessageType;
import java.lang.reflect.Type;

public class BackupWsMessageDeserializer
implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject jsonObject = parser.parseObject();
        String messageTypeValue = jsonObject.getString("type");
        if (messageTypeValue == null) {
            throw new IllegalArgumentException("Field 'type' is missing");
        }
        BackupWsMessageType messageType = BackupWsMessageType.parse(messageTypeValue);
        Class<? extends BackupWSMessage> messageClass = messageType.getMessageClass();
        return (T)jsonObject.toJavaObject(messageClass);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

