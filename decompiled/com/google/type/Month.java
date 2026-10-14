/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.type.MonthProto;

public enum Month implements ProtocolMessageEnum
{
    MONTH_UNSPECIFIED(0),
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12),
    UNRECOGNIZED(-1);

    public static final int MONTH_UNSPECIFIED_VALUE = 0;
    public static final int JANUARY_VALUE = 1;
    public static final int FEBRUARY_VALUE = 2;
    public static final int MARCH_VALUE = 3;
    public static final int APRIL_VALUE = 4;
    public static final int MAY_VALUE = 5;
    public static final int JUNE_VALUE = 6;
    public static final int JULY_VALUE = 7;
    public static final int AUGUST_VALUE = 8;
    public static final int SEPTEMBER_VALUE = 9;
    public static final int OCTOBER_VALUE = 10;
    public static final int NOVEMBER_VALUE = 11;
    public static final int DECEMBER_VALUE = 12;
    private static final Internal.EnumLiteMap<Month> internalValueMap;
    private static final Month[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static Month valueOf(int value) {
        return Month.forNumber(value);
    }

    public static Month forNumber(int value) {
        switch (value) {
            case 0: {
                return MONTH_UNSPECIFIED;
            }
            case 1: {
                return JANUARY;
            }
            case 2: {
                return FEBRUARY;
            }
            case 3: {
                return MARCH;
            }
            case 4: {
                return APRIL;
            }
            case 5: {
                return MAY;
            }
            case 6: {
                return JUNE;
            }
            case 7: {
                return JULY;
            }
            case 8: {
                return AUGUST;
            }
            case 9: {
                return SEPTEMBER;
            }
            case 10: {
                return OCTOBER;
            }
            case 11: {
                return NOVEMBER;
            }
            case 12: {
                return DECEMBER;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<Month> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return Month.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return Month.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return MonthProto.getDescriptor().getEnumTypes().get(0);
    }

    public static Month valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != Month.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private Month(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<Month>(){

            @Override
            public Month findValueByNumber(int number) {
                return Month.forNumber(number);
            }
        };
        VALUES = Month.values();
    }
}

