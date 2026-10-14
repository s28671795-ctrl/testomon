/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ErrorCode {
    NO_ERROR(0),
    PROTOCOL_ERROR(1),
    INTERNAL_ERROR(2),
    FLOW_CONTROL_ERROR(3),
    SETTINGS_TIMEOUT_ERROR(4),
    STREAM_CLOSED_ERROR(5),
    FRAME_SIZE_ERROR(6),
    REFUSED_STREAM_ERROR(7),
    CANCEL_STREAM_ERROR(8),
    COMPRESSION_ERROR(9),
    HTTP_CONNECT_ERROR(10),
    ENHANCE_YOUR_CALM_ERROR(11),
    INADEQUATE_SECURITY_ERROR(12),
    HTTP_1_1_REQUIRED_ERROR(13);

    public final int code;

    private ErrorCode(int code) {
        this.code = code;
        Codes.codes.put(code, this);
    }

    public static ErrorCode from(int error) {
        return Codes.codes.get(error);
    }

    public static String toString(int error, String dft) {
        ErrorCode errorCode = ErrorCode.from(error);
        String result = errorCode != null ? errorCode.name().toLowerCase(Locale.ENGLISH) : (dft == null ? String.valueOf(error) : dft);
        return result;
    }

    private static class Codes {
        private static final Map<Integer, ErrorCode> codes = new HashMap<Integer, ErrorCode>();

        private Codes() {
        }
    }
}

