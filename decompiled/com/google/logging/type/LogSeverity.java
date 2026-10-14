/*
 * Decompiled with CFR 0.152.
 */
package com.google.logging.type;

import com.google.logging.type.LogSeverityProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum LogSeverity implements ProtocolMessageEnum
{
    DEFAULT(0),
    DEBUG(100),
    INFO(200),
    NOTICE(300),
    WARNING(400),
    ERROR(500),
    CRITICAL(600),
    ALERT(700),
    EMERGENCY(800),
    UNRECOGNIZED(-1);

    public static final int DEFAULT_VALUE = 0;
    public static final int DEBUG_VALUE = 100;
    public static final int INFO_VALUE = 200;
    public static final int NOTICE_VALUE = 300;
    public static final int WARNING_VALUE = 400;
    public static final int ERROR_VALUE = 500;
    public static final int CRITICAL_VALUE = 600;
    public static final int ALERT_VALUE = 700;
    public static final int EMERGENCY_VALUE = 800;
    private static final Internal.EnumLiteMap<LogSeverity> internalValueMap;
    private static final LogSeverity[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static LogSeverity valueOf(int value) {
        return LogSeverity.forNumber(value);
    }

    public static LogSeverity forNumber(int value) {
        switch (value) {
            case 0: {
                return DEFAULT;
            }
            case 100: {
                return DEBUG;
            }
            case 200: {
                return INFO;
            }
            case 300: {
                return NOTICE;
            }
            case 400: {
                return WARNING;
            }
            case 500: {
                return ERROR;
            }
            case 600: {
                return CRITICAL;
            }
            case 700: {
                return ALERT;
            }
            case 800: {
                return EMERGENCY;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<LogSeverity> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return LogSeverity.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return LogSeverity.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return LogSeverityProto.getDescriptor().getEnumTypes().get(0);
    }

    public static LogSeverity valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != LogSeverity.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private LogSeverity(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<LogSeverity>(){

            @Override
            public LogSeverity findValueByNumber(int number) {
                return LogSeverity.forNumber(number);
            }
        };
        VALUES = LogSeverity.values();
    }
}

