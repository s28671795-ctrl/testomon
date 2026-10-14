/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.type.DayOfWeekProto;

public enum DayOfWeek implements ProtocolMessageEnum
{
    DAY_OF_WEEK_UNSPECIFIED(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7),
    UNRECOGNIZED(-1);

    public static final int DAY_OF_WEEK_UNSPECIFIED_VALUE = 0;
    public static final int MONDAY_VALUE = 1;
    public static final int TUESDAY_VALUE = 2;
    public static final int WEDNESDAY_VALUE = 3;
    public static final int THURSDAY_VALUE = 4;
    public static final int FRIDAY_VALUE = 5;
    public static final int SATURDAY_VALUE = 6;
    public static final int SUNDAY_VALUE = 7;
    private static final Internal.EnumLiteMap<DayOfWeek> internalValueMap;
    private static final DayOfWeek[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static DayOfWeek valueOf(int value) {
        return DayOfWeek.forNumber(value);
    }

    public static DayOfWeek forNumber(int value) {
        switch (value) {
            case 0: {
                return DAY_OF_WEEK_UNSPECIFIED;
            }
            case 1: {
                return MONDAY;
            }
            case 2: {
                return TUESDAY;
            }
            case 3: {
                return WEDNESDAY;
            }
            case 4: {
                return THURSDAY;
            }
            case 5: {
                return FRIDAY;
            }
            case 6: {
                return SATURDAY;
            }
            case 7: {
                return SUNDAY;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<DayOfWeek> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return DayOfWeek.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return DayOfWeek.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return DayOfWeekProto.getDescriptor().getEnumTypes().get(0);
    }

    public static DayOfWeek valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != DayOfWeek.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private DayOfWeek(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<DayOfWeek>(){

            @Override
            public DayOfWeek findValueByNumber(int number) {
                return DayOfWeek.forNumber(number);
            }
        };
        VALUES = DayOfWeek.values();
    }
}

