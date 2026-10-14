/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.smartparking;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import java.time.Instant;
import java.util.List;

public class SmartParkingDetection {
    @JSONField(name="uuid")
    String uuid;
    @JSONField(name="va_id")
    int vaId;
    @JSONField(name="stream_id")
    int streamId;
    @JSONField(name="box")
    List<Double> box;
    @JSONField(name="license_plate")
    String licensePlate;
    @JSONField(name="make_model_id")
    Integer makeModelId;
    @JSONField(name="plate_image_path")
    String plateImagePath;
    @JSONField(name="frame_image_path")
    String frameImagePath;
    @JSONField(name="color_id")
    Integer colorId;
    @JSONField(name="direction")
    Short direction;
    @JSONField(name="country")
    String country;
    @JSONField(name="plate_pattern_id")
    String platePatternId;
    @JSONField(name="timestamp", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    Instant timestamp;
    @JSONField(name="client_id")
    long clientId;
    @JSONField(name="tags")
    List<String> tags;

    public String getUuid() {
        return this.uuid;
    }

    public int getVaId() {
        return this.vaId;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public List<Double> getBox() {
        return this.box;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public Integer getMakeModelId() {
        return this.makeModelId;
    }

    public String getPlateImagePath() {
        return this.plateImagePath;
    }

    public String getFrameImagePath() {
        return this.frameImagePath;
    }

    public Integer getColorId() {
        return this.colorId;
    }

    public Short getDirection() {
        return this.direction;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPlatePatternId() {
        return this.platePatternId;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public long getClientId() {
        return this.clientId;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setVaId(int vaId) {
        this.vaId = vaId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public void setBox(List<Double> box) {
        this.box = box;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setMakeModelId(Integer makeModelId) {
        this.makeModelId = makeModelId;
    }

    public void setPlateImagePath(String plateImagePath) {
        this.plateImagePath = plateImagePath;
    }

    public void setFrameImagePath(String frameImagePath) {
        this.frameImagePath = frameImagePath;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlatePatternId(String platePatternId) {
        this.platePatternId = platePatternId;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public SmartParkingDetection() {
    }

    public SmartParkingDetection(String uuid, int vaId, int streamId, List<Double> box, String licensePlate, Integer makeModelId, String plateImagePath, String frameImagePath, Integer colorId, Short direction, String country, String platePatternId, Instant timestamp, long clientId, List<String> tags) {
        this.uuid = uuid;
        this.vaId = vaId;
        this.streamId = streamId;
        this.box = box;
        this.licensePlate = licensePlate;
        this.makeModelId = makeModelId;
        this.plateImagePath = plateImagePath;
        this.frameImagePath = frameImagePath;
        this.colorId = colorId;
        this.direction = direction;
        this.country = country;
        this.platePatternId = platePatternId;
        this.timestamp = timestamp;
        this.clientId = clientId;
        this.tags = tags;
    }
}

