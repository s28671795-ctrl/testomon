/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.db;

import com.alibaba.fastjson.JSON;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.plugin.commons.analytics.events.EventsHolderImpl;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class EventsHolderConverter
implements Converter<String, EventsHolder> {
    @Override
    public String to(EventsHolder eventsHolder) {
        return Optional.of(eventsHolder).map(EventsHolderDTO::new).map(JSON::toJSONString).orElse(null);
    }

    @Override
    public EventsHolder from(String s2) {
        return Optional.ofNullable(s2).map(it -> JSON.parseObject(it, EventsHolderDTO.class)).map(EventsHolderImpl::new).orElse(null);
    }

    @Override
    @NotNull
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    @NotNull
    public Class<EventsHolder> toType() {
        return EventsHolder.class;
    }
}

