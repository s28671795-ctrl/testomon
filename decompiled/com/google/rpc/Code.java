/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.rpc.CodeProto;

public enum Code implements ProtocolMessageEnum
{
    OK(0),
    CANCELLED(1),
    UNKNOWN(2),
    INVALID_ARGUMENT(3),
    DEADLINE_EXCEEDED(4),
    NOT_FOUND(5),
    ALREADY_EXISTS(6),
    PERMISSION_DENIED(7),
    UNAUTHENTICATED(16),
    RESOURCE_EXHAUSTED(8),
    FAILED_PRECONDITION(9),
    ABORTED(10),
    OUT_OF_RANGE(11),
    UNIMPLEMENTED(12),
    INTERNAL(13),
    UNAVAILABLE(14),
    DATA_LOSS(15),
    UNRECOGNIZED(-1);

    public static final int OK_VALUE = 0;
    public static final int CANCELLED_VALUE = 1;
    public static final int UNKNOWN_VALUE = 2;
    public static final int INVALID_ARGUMENT_VALUE = 3;
    public static final int DEADLINE_EXCEEDED_VALUE = 4;
    public static final int NOT_FOUND_VALUE = 5;
    public static final int ALREADY_EXISTS_VALUE = 6;
    public static final int PERMISSION_DENIED_VALUE = 7;
    public static final int UNAUTHENTICATED_VALUE = 16;
    public static final int RESOURCE_EXHAUSTED_VALUE = 8;
    public static final int FAILED_PRECONDITION_VALUE = 9;
    public static final int ABORTED_VALUE = 10;
    public static final int OUT_OF_RANGE_VALUE = 11;
    public static final int UNIMPLEMENTED_VALUE = 12;
    public static final int INTERNAL_VALUE = 13;
    public static final int UNAVAILABLE_VALUE = 14;
    public static final int DATA_LOSS_VALUE = 15;
    private static final Internal.EnumLiteMap<Code> internalValueMap;
    private static final Code[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static Code valueOf(int value) {
        return Code.forNumber(value);
    }

    public static Code forNumber(int value) {
        switch (value) {
            case 0: {
                return OK;
            }
            case 1: {
                return CANCELLED;
            }
            case 2: {
                return UNKNOWN;
            }
            case 3: {
                return INVALID_ARGUMENT;
            }
            case 4: {
                return DEADLINE_EXCEEDED;
            }
            case 5: {
                return NOT_FOUND;
            }
            case 6: {
                return ALREADY_EXISTS;
            }
            case 7: {
                return PERMISSION_DENIED;
            }
            case 16: {
                return UNAUTHENTICATED;
            }
            case 8: {
                return RESOURCE_EXHAUSTED;
            }
            case 9: {
                return FAILED_PRECONDITION;
            }
            case 10: {
                return ABORTED;
            }
            case 11: {
                return OUT_OF_RANGE;
            }
            case 12: {
                return UNIMPLEMENTED;
            }
            case 13: {
                return INTERNAL;
            }
            case 14: {
                return UNAVAILABLE;
            }
            case 15: {
                return DATA_LOSS;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<Code> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return Code.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return Code.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return CodeProto.getDescriptor().getEnumTypes().get(0);
    }

    public static Code valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != Code.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private Code(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<Code>(){

            @Override
            public Code findValueByNumber(int number) {
                return Code.forNumber(number);
            }
        };
        VALUES = Code.values();
    }
}

