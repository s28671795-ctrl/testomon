/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud;

import com.google.cloud.ExtendedOperationsProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum OperationResponseMapping implements ProtocolMessageEnum
{
    UNDEFINED(0),
    NAME(1),
    STATUS(2),
    ERROR_CODE(3),
    ERROR_MESSAGE(4),
    UNRECOGNIZED(-1);

    public static final int UNDEFINED_VALUE = 0;
    public static final int NAME_VALUE = 1;
    public static final int STATUS_VALUE = 2;
    public static final int ERROR_CODE_VALUE = 3;
    public static final int ERROR_MESSAGE_VALUE = 4;
    private static final Internal.EnumLiteMap<OperationResponseMapping> internalValueMap;
    private static final OperationResponseMapping[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static OperationResponseMapping valueOf(int value) {
        return OperationResponseMapping.forNumber(value);
    }

    public static OperationResponseMapping forNumber(int value) {
        switch (value) {
            case 0: {
                return UNDEFINED;
            }
            case 1: {
                return NAME;
            }
            case 2: {
                return STATUS;
            }
            case 3: {
                return ERROR_CODE;
            }
            case 4: {
                return ERROR_MESSAGE;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<OperationResponseMapping> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return OperationResponseMapping.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return OperationResponseMapping.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return ExtendedOperationsProto.getDescriptor().getEnumTypes().get(0);
    }

    public static OperationResponseMapping valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != OperationResponseMapping.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private OperationResponseMapping(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<OperationResponseMapping>(){

            @Override
            public OperationResponseMapping findValueByNumber(int number) {
                return OperationResponseMapping.forNumber(number);
            }
        };
        VALUES = OperationResponseMapping.values();
    }
}

