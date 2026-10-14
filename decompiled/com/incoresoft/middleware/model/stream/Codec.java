/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import java.util.Locale;

public enum Codec {
    mpeg4("MPEG4"),
    mjpeg("MJPEG"),
    h264("H.264"),
    h265("H.265");

    private final String displayedName;

    private Codec(String displayedName) {
        this.displayedName = displayedName;
    }

    public static Codec getCodec(String value) {
        if ((value = value.toLowerCase(Locale.ROOT)).contains("264")) {
            return h264;
        }
        if (value.contains("265")) {
            return h265;
        }
        if (value.contains("hevc")) {
            return h265;
        }
        if (value.contains("mjpeg")) {
            return mjpeg;
        }
        if (value.contains("mjpg")) {
            return mjpeg;
        }
        if (value.contains("mpeg4")) {
            return mpeg4;
        }
        return null;
    }

    public String getDisplayedName() {
        return this.displayedName;
    }
}

