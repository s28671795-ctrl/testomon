/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.serialization.StreamAuthSerializer;
import com.incoresoft.middleware.analytics.serialization.StreamParamsSerializer;
import com.incoresoft.middleware.analytics.streams.authorization.NoAuth;
import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.vezha.database.converters.CodecConverter;
import com.incoresoft.vezha.database.converters.RestrictionsConverter;
import com.incoresoft.vezha.database.converters.TimeZoneConverter;
import com.incoresoft.vezha.generated.tables.records.StreamsRecord;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamsRecordConverter
implements Converter<StreamsRecord, StreamImpl> {
    private static final Logger log = LoggerFactory.getLogger(StreamsRecordConverter.class);
    private final StreamRestrictions.Converter streamRestrictionsConverter;
    private final RestrictionsConverter restrictionsConverter;

    @Override
    public StreamImpl from(StreamsRecord record) {
        StreamImpl stream = new StreamImpl(record.getUuid(), record.getId(), record.getName(), record.getCreatedAt(), new Coordinates(record.getLat(), record.getLng()), StreamType.valueOf(record.getType()), record.getAddress(), record.getDirection(), record.getClientId(), Optional.ofNullable(record.getTimezone()).map(StreamsRecordConverter::handleEuropeKyiv).map(ZoneId::of).orElse(ZoneId.systemDefault()), Optional.ofNullable(record.getAuth()).map(StreamAuthSerializer::deserialize).orElse(new NoAuth()), StreamParamsSerializer.deserialize(JSON.parseObject(record.getParams()).fluentPut("type", record.getType()).toJSONString()), new RestrictionsConverter(this.streamRestrictionsConverter).from(record.getRestrictions()), record.getParentId());
        stream.setStatus(StreamStatus.getById(record.getStatus().byteValue()));
        stream.setResolution(new Resolution(record.getWidth(), record.getHeight()));
        stream.setImage(StreamsRecordConverter.checkFramePath(record.getFileName()));
        stream.setCodec(Optional.ofNullable(record.getCodec()).map(Codec::getCodec).orElse(null));
        if (record.getDuration() != null) {
            stream.setDuration(Duration.ofMillis(record.getDuration()));
        }
        if (record.getPath() != null) {
            stream.setUri(URI.create(record.getPath()));
        }
        return stream;
    }

    private static String handleEuropeKyiv(String timezone) {
        return timezone.equals("Europe/Kiev") ? "Europe/Kyiv" : timezone;
    }

    @Override
    public StreamsRecord to(StreamImpl item) {
        StreamsRecord record = new StreamsRecord();
        record.setId(item.getId());
        record.setName(item.getName());
        record.setPath(Optional.ofNullable(item.getUri()).map(URI::toString).orElse(null));
        record.setWidth(item.getResolution().getWidth());
        record.setHeight(item.getResolution().getHeight());
        record.setFileName(item.getImage());
        record.setCodec((String)new CodecConverter().to(item.getCodec()));
        record.setLat(item.getLocation().getLatitude());
        record.setLng(item.getLocation().getLongitude());
        record.setStatus((byte)item.getStatus().getId());
        record.setType(item.getType().name());
        record.setUuid(Optional.ofNullable(item.getUUID()).orElse(UUID.randomUUID()));
        record.setAddress(item.getAddress());
        record.setParams(JSON.toJSONString(item.getParams()));
        record.setAuth(JSON.toJSONString(item.getAuth()));
        record.setDirection(item.getDirection());
        record.setClientId(item.getClientId());
        record.setTimezone(new TimeZoneConverter().to(item.getTimezone()));
        record.setParentId(item.getFolderId());
        record.setRestrictions(this.restrictionsConverter.to(item.getRestrictions()));
        record.setDuration(Optional.ofNullable(item.getDuration()).map(Duration::toMillis).orElse(null));
        return record;
    }

    @Override
    @NotNull
    public Class<StreamsRecord> fromType() {
        return StreamsRecord.class;
    }

    @Override
    @NotNull
    public Class<StreamImpl> toType() {
        return StreamImpl.class;
    }

    private static String checkFramePath(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return "stub_image.jpg";
        }
        String filePath = FilenameUtils.separatorsToSystem("./images/" + fileName);
        return Files.exists(Path.of(filePath, new String[0]), new LinkOption[0]) ? fileName : "stub_image.jpg";
    }

    @Inject
    public StreamsRecordConverter(StreamRestrictions.Converter streamRestrictionsConverter, RestrictionsConverter restrictionsConverter) {
        this.streamRestrictionsConverter = streamRestrictionsConverter;
        this.restrictionsConverter = restrictionsConverter;
    }
}

