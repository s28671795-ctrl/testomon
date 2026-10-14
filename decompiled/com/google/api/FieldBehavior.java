/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.FieldBehaviorProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum FieldBehavior implements ProtocolMessageEnum
{
    FIELD_BEHAVIOR_UNSPECIFIED(0),
    OPTIONAL(1),
    REQUIRED(2),
    OUTPUT_ONLY(3),
    INPUT_ONLY(4),
    IMMUTABLE(5),
    UNORDERED_LIST(6),
    NON_EMPTY_DEFAULT(7),
    IDENTIFIER(8),
    UNRECOGNIZED(-1);

    public static final int FIELD_BEHAVIOR_UNSPECIFIED_VALUE = 0;
    public static final int OPTIONAL_VALUE = 1;
    public static final int REQUIRED_VALUE = 2;
    public static final int OUTPUT_ONLY_VALUE = 3;
    public static final int INPUT_ONLY_VALUE = 4;
    public static final int IMMUTABLE_VALUE = 5;
    public static final int UNORDERED_LIST_VALUE = 6;
    public static final int NON_EMPTY_DEFAULT_VALUE = 7;
    public static final int IDENTIFIER_VALUE = 8;
    private static final Internal.EnumLiteMap<FieldBehavior> internalValueMap;
    private static final FieldBehavior[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static FieldBehavior valueOf(int value) {
        return FieldBehavior.forNumber(value);
    }

    public static FieldBehavior forNumber(int value) {
        switch (value) {
            case 0: {
                return FIELD_BEHAVIOR_UNSPECIFIED;
            }
            case 1: {
                return OPTIONAL;
            }
            case 2: {
                return REQUIRED;
            }
            case 3: {
                return OUTPUT_ONLY;
            }
            case 4: {
                return INPUT_ONLY;
            }
            case 5: {
                return IMMUTABLE;
            }
            case 6: {
                return UNORDERED_LIST;
            }
            case 7: {
                return NON_EMPTY_DEFAULT;
            }
            case 8: {
                return IDENTIFIER;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<FieldBehavior> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return FieldBehavior.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return FieldBehavior.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return FieldBehaviorProto.getDescriptor().getEnumTypes().get(0);
    }

    public static FieldBehavior valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != FieldBehavior.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private FieldBehavior(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<FieldBehavior>(){

            @Override
            public FieldBehavior findValueByNumber(int number) {
                return FieldBehavior.forNumber(number);
            }
        };
        VALUES = FieldBehavior.values();
    }
}

