/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ConfigChangeProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum ChangeType implements ProtocolMessageEnum
{
    CHANGE_TYPE_UNSPECIFIED(0),
    ADDED(1),
    REMOVED(2),
    MODIFIED(3),
    UNRECOGNIZED(-1);

    public static final int CHANGE_TYPE_UNSPECIFIED_VALUE = 0;
    public static final int ADDED_VALUE = 1;
    public static final int REMOVED_VALUE = 2;
    public static final int MODIFIED_VALUE = 3;
    private static final Internal.EnumLiteMap<ChangeType> internalValueMap;
    private static final ChangeType[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static ChangeType valueOf(int value) {
        return ChangeType.forNumber(value);
    }

    public static ChangeType forNumber(int value) {
        switch (value) {
            case 0: {
                return CHANGE_TYPE_UNSPECIFIED;
            }
            case 1: {
                return ADDED;
            }
            case 2: {
                return REMOVED;
            }
            case 3: {
                return MODIFIED;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<ChangeType> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return ChangeType.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return ChangeType.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return ConfigChangeProto.getDescriptor().getEnumTypes().get(0);
    }

    public static ChangeType valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != ChangeType.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private ChangeType(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<ChangeType>(){

            @Override
            public ChangeType findValueByNumber(int number) {
                return ChangeType.forNumber(number);
            }
        };
        VALUES = ChangeType.values();
    }
}

