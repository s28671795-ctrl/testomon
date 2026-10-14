/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum ClientLibraryOrganization implements ProtocolMessageEnum
{
    CLIENT_LIBRARY_ORGANIZATION_UNSPECIFIED(0),
    CLOUD(1),
    ADS(2),
    PHOTOS(3),
    STREET_VIEW(4),
    SHOPPING(5),
    GEO(6),
    GENERATIVE_AI(7),
    UNRECOGNIZED(-1);

    public static final int CLIENT_LIBRARY_ORGANIZATION_UNSPECIFIED_VALUE = 0;
    public static final int CLOUD_VALUE = 1;
    public static final int ADS_VALUE = 2;
    public static final int PHOTOS_VALUE = 3;
    public static final int STREET_VIEW_VALUE = 4;
    public static final int SHOPPING_VALUE = 5;
    public static final int GEO_VALUE = 6;
    public static final int GENERATIVE_AI_VALUE = 7;
    private static final Internal.EnumLiteMap<ClientLibraryOrganization> internalValueMap;
    private static final ClientLibraryOrganization[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static ClientLibraryOrganization valueOf(int value) {
        return ClientLibraryOrganization.forNumber(value);
    }

    public static ClientLibraryOrganization forNumber(int value) {
        switch (value) {
            case 0: {
                return CLIENT_LIBRARY_ORGANIZATION_UNSPECIFIED;
            }
            case 1: {
                return CLOUD;
            }
            case 2: {
                return ADS;
            }
            case 3: {
                return PHOTOS;
            }
            case 4: {
                return STREET_VIEW;
            }
            case 5: {
                return SHOPPING;
            }
            case 6: {
                return GEO;
            }
            case 7: {
                return GENERATIVE_AI;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<ClientLibraryOrganization> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return ClientLibraryOrganization.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return ClientLibraryOrganization.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return ClientProto.getDescriptor().getEnumTypes().get(0);
    }

    public static ClientLibraryOrganization valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != ClientLibraryOrganization.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private ClientLibraryOrganization(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<ClientLibraryOrganization>(){

            @Override
            public ClientLibraryOrganization findValueByNumber(int number) {
                return ClientLibraryOrganization.forNumber(number);
            }
        };
        VALUES = ClientLibraryOrganization.values();
    }
}

