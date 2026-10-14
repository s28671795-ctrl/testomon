/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantSerializer
extends InstantSerializerBase<Instant> {
    private static final long serialVersionUID = 1L;
    public static final InstantSerializer INSTANCE = new InstantSerializer();

    protected InstantSerializer() {
        super(Instant.class, Instant::toEpochMilli, Instant::getEpochSecond, Instant::getNano, null);
    }

    @Deprecated
    protected InstantSerializer(InstantSerializer base, Boolean useTimestamp, DateTimeFormatter formatter) {
        this(base, useTimestamp, base._useNanoseconds, formatter);
    }

    protected InstantSerializer(InstantSerializer base, Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
        super(base, useTimestamp, base._useNanoseconds, formatter, shape);
    }

    protected InstantSerializer(InstantSerializer base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter formatter) {
        super(base, useTimestamp, useNanoseconds, formatter);
    }

    @Override
    protected JSR310FormattedSerializerBase<Instant> withFormat(Boolean useTimestamp, DateTimeFormatter formatter, JsonFormat.Shape shape) {
        return new InstantSerializer(this, useTimestamp, formatter, shape);
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId, Boolean writeNanoseconds) {
        return new InstantSerializer(this, this._useTimestamp, writeNanoseconds, this._formatter);
    }
}

