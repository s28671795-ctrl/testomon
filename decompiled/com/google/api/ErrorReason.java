/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ErrorReasonProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;

public enum ErrorReason implements ProtocolMessageEnum
{
    ERROR_REASON_UNSPECIFIED(0),
    SERVICE_DISABLED(1),
    BILLING_DISABLED(2),
    API_KEY_INVALID(3),
    API_KEY_SERVICE_BLOCKED(4),
    API_KEY_HTTP_REFERRER_BLOCKED(7),
    API_KEY_IP_ADDRESS_BLOCKED(8),
    API_KEY_ANDROID_APP_BLOCKED(9),
    API_KEY_IOS_APP_BLOCKED(13),
    RATE_LIMIT_EXCEEDED(5),
    RESOURCE_QUOTA_EXCEEDED(6),
    LOCATION_TAX_POLICY_VIOLATED(10),
    USER_PROJECT_DENIED(11),
    CONSUMER_SUSPENDED(12),
    CONSUMER_INVALID(14),
    SECURITY_POLICY_VIOLATED(15),
    ACCESS_TOKEN_EXPIRED(16),
    ACCESS_TOKEN_SCOPE_INSUFFICIENT(17),
    ACCOUNT_STATE_INVALID(18),
    ACCESS_TOKEN_TYPE_UNSUPPORTED(19),
    CREDENTIALS_MISSING(20),
    RESOURCE_PROJECT_INVALID(21),
    SESSION_COOKIE_INVALID(23),
    USER_BLOCKED_BY_ADMIN(24),
    RESOURCE_USAGE_RESTRICTION_VIOLATED(25),
    SYSTEM_PARAMETER_UNSUPPORTED(26),
    ORG_RESTRICTION_VIOLATION(27),
    ORG_RESTRICTION_HEADER_INVALID(28),
    SERVICE_NOT_VISIBLE(29),
    GCP_SUSPENDED(30),
    UNRECOGNIZED(-1);

    public static final int ERROR_REASON_UNSPECIFIED_VALUE = 0;
    public static final int SERVICE_DISABLED_VALUE = 1;
    public static final int BILLING_DISABLED_VALUE = 2;
    public static final int API_KEY_INVALID_VALUE = 3;
    public static final int API_KEY_SERVICE_BLOCKED_VALUE = 4;
    public static final int API_KEY_HTTP_REFERRER_BLOCKED_VALUE = 7;
    public static final int API_KEY_IP_ADDRESS_BLOCKED_VALUE = 8;
    public static final int API_KEY_ANDROID_APP_BLOCKED_VALUE = 9;
    public static final int API_KEY_IOS_APP_BLOCKED_VALUE = 13;
    public static final int RATE_LIMIT_EXCEEDED_VALUE = 5;
    public static final int RESOURCE_QUOTA_EXCEEDED_VALUE = 6;
    public static final int LOCATION_TAX_POLICY_VIOLATED_VALUE = 10;
    public static final int USER_PROJECT_DENIED_VALUE = 11;
    public static final int CONSUMER_SUSPENDED_VALUE = 12;
    public static final int CONSUMER_INVALID_VALUE = 14;
    public static final int SECURITY_POLICY_VIOLATED_VALUE = 15;
    public static final int ACCESS_TOKEN_EXPIRED_VALUE = 16;
    public static final int ACCESS_TOKEN_SCOPE_INSUFFICIENT_VALUE = 17;
    public static final int ACCOUNT_STATE_INVALID_VALUE = 18;
    public static final int ACCESS_TOKEN_TYPE_UNSUPPORTED_VALUE = 19;
    public static final int CREDENTIALS_MISSING_VALUE = 20;
    public static final int RESOURCE_PROJECT_INVALID_VALUE = 21;
    public static final int SESSION_COOKIE_INVALID_VALUE = 23;
    public static final int USER_BLOCKED_BY_ADMIN_VALUE = 24;
    public static final int RESOURCE_USAGE_RESTRICTION_VIOLATED_VALUE = 25;
    public static final int SYSTEM_PARAMETER_UNSUPPORTED_VALUE = 26;
    public static final int ORG_RESTRICTION_VIOLATION_VALUE = 27;
    public static final int ORG_RESTRICTION_HEADER_INVALID_VALUE = 28;
    public static final int SERVICE_NOT_VISIBLE_VALUE = 29;
    public static final int GCP_SUSPENDED_VALUE = 30;
    private static final Internal.EnumLiteMap<ErrorReason> internalValueMap;
    private static final ErrorReason[] VALUES;
    private final int value;

    @Override
    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
        return this.value;
    }

    @Deprecated
    public static ErrorReason valueOf(int value) {
        return ErrorReason.forNumber(value);
    }

    public static ErrorReason forNumber(int value) {
        switch (value) {
            case 0: {
                return ERROR_REASON_UNSPECIFIED;
            }
            case 1: {
                return SERVICE_DISABLED;
            }
            case 2: {
                return BILLING_DISABLED;
            }
            case 3: {
                return API_KEY_INVALID;
            }
            case 4: {
                return API_KEY_SERVICE_BLOCKED;
            }
            case 7: {
                return API_KEY_HTTP_REFERRER_BLOCKED;
            }
            case 8: {
                return API_KEY_IP_ADDRESS_BLOCKED;
            }
            case 9: {
                return API_KEY_ANDROID_APP_BLOCKED;
            }
            case 13: {
                return API_KEY_IOS_APP_BLOCKED;
            }
            case 5: {
                return RATE_LIMIT_EXCEEDED;
            }
            case 6: {
                return RESOURCE_QUOTA_EXCEEDED;
            }
            case 10: {
                return LOCATION_TAX_POLICY_VIOLATED;
            }
            case 11: {
                return USER_PROJECT_DENIED;
            }
            case 12: {
                return CONSUMER_SUSPENDED;
            }
            case 14: {
                return CONSUMER_INVALID;
            }
            case 15: {
                return SECURITY_POLICY_VIOLATED;
            }
            case 16: {
                return ACCESS_TOKEN_EXPIRED;
            }
            case 17: {
                return ACCESS_TOKEN_SCOPE_INSUFFICIENT;
            }
            case 18: {
                return ACCOUNT_STATE_INVALID;
            }
            case 19: {
                return ACCESS_TOKEN_TYPE_UNSUPPORTED;
            }
            case 20: {
                return CREDENTIALS_MISSING;
            }
            case 21: {
                return RESOURCE_PROJECT_INVALID;
            }
            case 23: {
                return SESSION_COOKIE_INVALID;
            }
            case 24: {
                return USER_BLOCKED_BY_ADMIN;
            }
            case 25: {
                return RESOURCE_USAGE_RESTRICTION_VIOLATED;
            }
            case 26: {
                return SYSTEM_PARAMETER_UNSUPPORTED;
            }
            case 27: {
                return ORG_RESTRICTION_VIOLATION;
            }
            case 28: {
                return ORG_RESTRICTION_HEADER_INVALID;
            }
            case 29: {
                return SERVICE_NOT_VISIBLE;
            }
            case 30: {
                return GCP_SUSPENDED;
            }
        }
        return null;
    }

    public static Internal.EnumLiteMap<ErrorReason> internalGetValueMap() {
        return internalValueMap;
    }

    @Override
    public final Descriptors.EnumValueDescriptor getValueDescriptor() {
        if (this == UNRECOGNIZED) {
            throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
        }
        return ErrorReason.getDescriptor().getValues().get(this.ordinal());
    }

    @Override
    public final Descriptors.EnumDescriptor getDescriptorForType() {
        return ErrorReason.getDescriptor();
    }

    public static final Descriptors.EnumDescriptor getDescriptor() {
        return ErrorReasonProto.getDescriptor().getEnumTypes().get(0);
    }

    public static ErrorReason valueOf(Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != ErrorReason.getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
            return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
    }

    private ErrorReason(int value) {
        this.value = value;
    }

    static {
        internalValueMap = new Internal.EnumLiteMap<ErrorReason>(){

            @Override
            public ErrorReason findValueByNumber(int number) {
                return ErrorReason.forNumber(number);
            }
        };
        VALUES = ErrorReason.values();
    }
}

