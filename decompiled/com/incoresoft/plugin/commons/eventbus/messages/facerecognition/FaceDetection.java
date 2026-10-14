/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus.messages.facerecognition;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.AgeRange;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.FaceRecognitionPerson;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.FaceRecognitionPersonList;
import com.incoresoft.plugin.commons.eventbus.messages.facerecognition.Gender;
import com.incoresoft.plugin.commons.serialization.InstantEpochSerializer;
import java.time.Instant;
import java.util.List;

public class FaceDetection {
    @JSONField(name="uuid")
    String uuid;
    @JSONField(name="va_id")
    int vaId;
    @JSONField(name="stream_id")
    int streamId;
    @JSONField(name="box")
    List<Double> box;
    @JSONField(name="timestamp", serializeUsing=InstantEpochSerializer.class, deserializeUsing=InstantEpochSerializer.class)
    Instant timestamp;
    @JSONField(name="age_range")
    AgeRange ageRange;
    @JSONField(name="gender")
    Gender gender;
    @JSONField(name="is_fake")
    boolean fake;
    @JSONField(name="is_mask_present")
    boolean mask;
    @JSONField(name="face_image_path")
    String faceImagePath;
    @JSONField(name="frame_image_path")
    String frameImagePath;
    @JSONField(name="client_id")
    long clientId;
    @JSONField(name="person")
    FaceRecognitionPerson person;
    @JSONField(name="list")
    FaceRecognitionPersonList list;

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

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public AgeRange getAgeRange() {
        return this.ageRange;
    }

    public Gender getGender() {
        return this.gender;
    }

    public boolean isFake() {
        return this.fake;
    }

    public boolean isMask() {
        return this.mask;
    }

    public String getFaceImagePath() {
        return this.faceImagePath;
    }

    public String getFrameImagePath() {
        return this.frameImagePath;
    }

    public long getClientId() {
        return this.clientId;
    }

    public FaceRecognitionPerson getPerson() {
        return this.person;
    }

    public FaceRecognitionPersonList getList() {
        return this.list;
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

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public void setFaceImagePath(String faceImagePath) {
        this.faceImagePath = faceImagePath;
    }

    public void setFrameImagePath(String frameImagePath) {
        this.frameImagePath = frameImagePath;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setPerson(FaceRecognitionPerson person) {
        this.person = person;
    }

    public void setList(FaceRecognitionPersonList list) {
        this.list = list;
    }

    public FaceDetection() {
    }

    public FaceDetection(String uuid, int vaId, int streamId, List<Double> box, Instant timestamp, AgeRange ageRange, Gender gender, boolean fake, boolean mask, String faceImagePath, String frameImagePath, long clientId, FaceRecognitionPerson person, FaceRecognitionPersonList list) {
        this.uuid = uuid;
        this.vaId = vaId;
        this.streamId = streamId;
        this.box = box;
        this.timestamp = timestamp;
        this.ageRange = ageRange;
        this.gender = gender;
        this.fake = fake;
        this.mask = mask;
        this.faceImagePath = faceImagePath;
        this.frameImagePath = frameImagePath;
        this.clientId = clientId;
        this.person = person;
        this.list = list;
    }
}

