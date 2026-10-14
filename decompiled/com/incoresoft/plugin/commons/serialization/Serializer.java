/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.serialization;

import com.alibaba.fastjson.JSON;
import com.incoresoft.Utils.StringUtils;
import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import java.time.ZoneId;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class Serializer<T> {
    protected final ItemManager<Stream> streamsManager;

    public Serializer(@NotNull ItemManager<Stream> streamsManager) {
        this.streamsManager = Objects.requireNonNull(streamsManager);
    }

    public abstract JSON serialize(User var1, T var2);

    protected ZoneId getZoneIdForStream(int streamId) {
        Stream stream = this.streamsManager.get(streamId);
        if (stream == null) {
            return ZoneId.systemDefault();
        }
        String timezone = stream.getTimezone().getId();
        if (StringUtils.isEmpty(timezone)) {
            return ZoneId.systemDefault();
        }
        return ZoneId.of(timezone);
    }
}

