/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LaunchStageProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum LaunchStage implements ProtocolMessageEnum
{
    LAUNCH_STAGE_UNSPECIFIED(0),
    UNIMPLEMENTED(6),
    PRELAUNCH(7),
    EARLY_ACCESS(1),
    ALPHA(2),
    BETA(3),
    GA(4),
    DEPRECATED(5),
    UNRECOGNIZED(-1);

    public static final int LAUNCH_STAGE_UNSPECIFIED_VALUE = 0;
    public static final int UNIMPLEMENTED_VALUE = 6;
    public static final int PRELAUNCH_VALUE = 7;
    public static final int EARLY_ACCESS_VALUE = 1;
    public static final int ALPHA_VALUE = 2;
    public static final int BETA_VALUE = 3;
    public static final int GA_VALUE = 4;
    public static final int DEPRECATED_VALUE = 5;
    private static final Internal.EnumLiteMap<LaunchStage> internalValueMap;
    private static final LaunchStage[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static LaunchStage valueOf(int value) {
        return LaunchStage.forNumber(value);
    }

    public static LaunchStage forNumber(int value) {
        switch (value) {
            case 0: {
                return LAUNCH_STAGE_UNSPECIFIED;
            }
            case 6: {
                return UNIMPLEMENTED;
            }
            case 7: {
                return PRELAUNCH;
            }
            case 1: {
                return EARLY_ACCESS;
            }
            case 2: {
                return ALPHA;
            }
            case 3: {
                return BETA;
            }
            case 4: {
                return GA;
            }
            case 5: {
                return DEPRECATED;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<LaunchStage> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return LaunchStage.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return LaunchStage.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return LaunchStageProto.getDescriptor().getEnumTypes().get(0);
    }

    public static LaunchStage valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != LaunchStage.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private LaunchStage(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<LaunchStage>(){

            @Override
            public LaunchStage findValueByNumber(int number) {
                return LaunchStage.forNumber(number);
            }
        };
        VALUES = LaunchStage.values();
    }
}

