/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.type.CalendarPeriodProto;

public enum CalendarPeriod implements ProtocolMessageEnum
{
    CALENDAR_PERIOD_UNSPECIFIED(0),
    DAY(1),
    WEEK(2),
    FORTNIGHT(3),
    MONTH(4),
    QUARTER(5),
    HALF(6),
    YEAR(7),
    UNRECOGNIZED(-1);

    public static final int CALENDAR_PERIOD_UNSPECIFIED_VALUE = 0;
    public static final int DAY_VALUE = 1;
    public static final int WEEK_VALUE = 2;
    public static final int FORTNIGHT_VALUE = 3;
    public static final int MONTH_VALUE = 4;
    public static final int QUARTER_VALUE = 5;
    public static final int HALF_VALUE = 6;
    public static final int YEAR_VALUE = 7;
    private static final Internal.EnumLiteMap<CalendarPeriod> internalValueMap;
    private static final CalendarPeriod[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static CalendarPeriod valueOf(int value) {
        return CalendarPeriod.forNumber(value);
    }

    public static CalendarPeriod forNumber(int value) {
        switch (value) {
            case 0: {
                return CALENDAR_PERIOD_UNSPECIFIED;
            }
            case 1: {
                return DAY;
            }
            case 2: {
                return WEEK;
            }
            case 3: {
                return FORTNIGHT;
            }
            case 4: {
                return MONTH;
            }
            case 5: {
                return QUARTER;
            }
            case 6: {
                return HALF;
            }
            case 7: {
                return YEAR;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<CalendarPeriod> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return CalendarPeriod.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return CalendarPeriod.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return CalendarPeriodProto.getDescriptor().getEnumTypes().get(0);
    }

    public static CalendarPeriod valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != CalendarPeriod.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private CalendarPeriod(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<CalendarPeriod>(){

            @Override
            public CalendarPeriod findValueByNumber(int number) {
                return CalendarPeriod.forNumber(number);
            }
        };
        VALUES = CalendarPeriod.values();
    }
}

