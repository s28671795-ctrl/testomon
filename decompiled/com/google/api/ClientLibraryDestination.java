/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum ClientLibraryDestination implements ProtocolMessageEnum
{
    CLIENT_LIBRARY_DESTINATION_UNSPECIFIED(0),
    GITHUB(10),
    PACKAGE_MANAGER(20),
    UNRECOGNIZED(-1);

    public static final int CLIENT_LIBRARY_DESTINATION_UNSPECIFIED_VALUE = 0;
    public static final int GITHUB_VALUE = 10;
    public static final int PACKAGE_MANAGER_VALUE = 20;
    private static final Internal.EnumLiteMap<ClientLibraryDestination> internalValueMap;
    private static final ClientLibraryDestination[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static ClientLibraryDestination valueOf(int value) {
        return ClientLibraryDestination.forNumber(value);
    }

    public static ClientLibraryDestination forNumber(int value) {
        switch (value) {
            case 0: {
                return CLIENT_LIBRARY_DESTINATION_UNSPECIFIED;
            }
            case 10: {
                return GITHUB;
            }
            case 20: {
                return PACKAGE_MANAGER;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<ClientLibraryDestination> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return ClientLibraryDestination.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return ClientLibraryDestination.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return ClientProto.getDescriptor().getEnumTypes().get(1);
    }

    public static ClientLibraryDestination valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != ClientLibraryDestination.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private ClientLibraryDestination(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<ClientLibraryDestination>(){

            @Override
            public ClientLibraryDestination findValueByNumber(int number) {
                return ClientLibraryDestination.forNumber(number);
            }
        };
        VALUES = ClientLibraryDestination.values();
    }
}

