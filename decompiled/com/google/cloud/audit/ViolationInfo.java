/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.ViolationInfoOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ViolationInfo
extends GeneratedMessageV3
implements ViolationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CONSTRAINT_FIELD_NUMBER = 1;
    private volatile Object constraint_ = "";
    public static final int ERROR_MESSAGE_FIELD_NUMBER = 2;
    private volatile Object errorMessage_ = "";
    public static final int CHECKED_VALUE_FIELD_NUMBER = 3;
    private volatile Object checkedValue_ = "";
    public static final int POLICY_TYPE_FIELD_NUMBER = 4;
    private int policyType_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ViolationInfo DEFAULT_INSTANCE = new ViolationInfo();
    private static final Parser<ViolationInfo> PARSER = new AbstractParser<ViolationInfo>(){

        @Override
        public ViolationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ViolationInfo.newBuilder();
            try {
                builder.mergeFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(builder.buildPartial());
            }
            catch (UninitializedMessageException e) {
                throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
            }
            return builder.buildPartial();
        }
    };

    private ViolationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ViolationInfo() {
        this.constraint_ = "";
        this.errorMessage_ = "";
        this.checkedValue_ = "";
        this.policyType_ = 0;
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ViolationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_ViolationInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_ViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ViolationInfo.class, Builder.class);
    }

    @Override
    public String getConstraint() {
        Object ref = this.constraint_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.constraint_ = s2;
        return s2;
    }

    @Override
    public ByteString getConstraintBytes() {
        Object ref = this.constraint_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.constraint_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getErrorMessage() {
        Object ref = this.errorMessage_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.errorMessage_ = s2;
        return s2;
    }

    @Override
    public ByteString getErrorMessageBytes() {
        Object ref = this.errorMessage_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.errorMessage_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getCheckedValue() {
        Object ref = this.checkedValue_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.checkedValue_ = s2;
        return s2;
    }

    @Override
    public ByteString getCheckedValueBytes() {
        Object ref = this.checkedValue_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.checkedValue_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getPolicyTypeValue() {
        return this.policyType_;
    }

    @Override
    public PolicyType getPolicyType() {
        PolicyType result = PolicyType.forNumber(this.policyType_);
        return result == null ? PolicyType.UNRECOGNIZED : result;
    }

    @Override
    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    @Override
    public void writeTo(CodedOutputStream output) throws IOException {
        if (!GeneratedMessageV3.isStringEmpty(this.constraint_)) {
            GeneratedMessageV3.writeString(output, 1, this.constraint_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.errorMessage_)) {
            GeneratedMessageV3.writeString(output, 2, this.errorMessage_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.checkedValue_)) {
            GeneratedMessageV3.writeString(output, 3, this.checkedValue_);
        }
        if (this.policyType_ != PolicyType.POLICY_TYPE_UNSPECIFIED.getNumber()) {
            output.writeEnum(4, this.policyType_);
        }
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!GeneratedMessageV3.isStringEmpty(this.constraint_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.constraint_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.errorMessage_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.errorMessage_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.checkedValue_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.checkedValue_);
        }
        if (this.policyType_ != PolicyType.POLICY_TYPE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(4, this.policyType_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViolationInfo)) {
            return super.equals(obj);
        }
        ViolationInfo other = (ViolationInfo)obj;
        if (!this.getConstraint().equals(other.getConstraint())) {
            return false;
        }
        if (!this.getErrorMessage().equals(other.getErrorMessage())) {
            return false;
        }
        if (!this.getCheckedValue().equals(other.getCheckedValue())) {
            return false;
        }
        if (this.policyType_ != other.policyType_) {
            return false;
        }
        return this.getUnknownFields().equals(other.getUnknownFields());
    }

    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + ViolationInfo.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getConstraint().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getErrorMessage().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getCheckedValue().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.policyType_;
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ViolationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ViolationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ViolationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ViolationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ViolationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ViolationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ViolationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ViolationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ViolationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ViolationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ViolationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ViolationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ViolationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ViolationInfo prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    public static ViolationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ViolationInfo> parser() {
        return PARSER;
    }

    public Parser<ViolationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public ViolationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ViolationInfoOrBuilder {
        private int bitField0_;
        private Object constraint_ = "";
        private Object errorMessage_ = "";
        private Object checkedValue_ = "";
        private int policyType_ = 0;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_ViolationInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_ViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ViolationInfo.class, Builder.class);
        }

        private Builder() {
        }

        private Builder(GeneratedMessageV3.BuilderParent parent) {
            super(parent);
        }

        @Override
        public Builder clear() {
            super.clear();
            this.bitField0_ = 0;
            this.constraint_ = "";
            this.errorMessage_ = "";
            this.checkedValue_ = "";
            this.policyType_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_ViolationInfo_descriptor;
        }

        @Override
        public ViolationInfo getDefaultInstanceForType() {
            return ViolationInfo.getDefaultInstance();
        }

        @Override
        public ViolationInfo build() {
            ViolationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ViolationInfo buildPartial() {
            ViolationInfo result = new ViolationInfo(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ViolationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.constraint_ = this.constraint_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.errorMessage_ = this.errorMessage_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.checkedValue_ = this.checkedValue_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.policyType_ = this.policyType_;
            }
        }

        @Override
        public Builder clone() {
            return (Builder)super.clone();
        }

        @Override
        public Builder setField(Descriptors.FieldDescriptor field2, Object value) {
            return (Builder)super.setField(field2, value);
        }

        @Override
        public Builder clearField(Descriptors.FieldDescriptor field2) {
            return (Builder)super.clearField(field2);
        }

        @Override
        public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
            return (Builder)super.clearOneof(oneof);
        }

        @Override
        public Builder setRepeatedField(Descriptors.FieldDescriptor field2, int index, Object value) {
            return (Builder)super.setRepeatedField(field2, index, value);
        }

        @Override
        public Builder addRepeatedField(Descriptors.FieldDescriptor field2, Object value) {
            return (Builder)super.addRepeatedField(field2, value);
        }

        @Override
        public Builder mergeFrom(Message other) {
            if (other instanceof ViolationInfo) {
                return this.mergeFrom((ViolationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ViolationInfo other) {
            if (other == ViolationInfo.getDefaultInstance()) {
                return this;
            }
            if (!other.getConstraint().isEmpty()) {
                this.constraint_ = other.constraint_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getErrorMessage().isEmpty()) {
                this.errorMessage_ = other.errorMessage_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getCheckedValue().isEmpty()) {
                this.checkedValue_ = other.checkedValue_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.policyType_ != 0) {
                this.setPolicyTypeValue(other.getPolicyTypeValue());
            }
            this.mergeUnknownFields(other.getUnknownFields());
            this.onChanged();
            return this;
        }

        @Override
        public final boolean isInitialized() {
            return true;
        }

        @Override
        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            try {
                boolean done = false;
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            this.constraint_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            this.errorMessage_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 26: {
                            this.checkedValue_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 32: {
                            this.policyType_ = input.readEnum();
                            this.bitField0_ |= 8;
                            continue block12;
                        }
                    }
                    if (super.parseUnknownField(input, extensionRegistry, tag)) continue;
                    done = true;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.unwrapIOException();
            }
            finally {
                this.onChanged();
            }
            return this;
        }

        @Override
        public String getConstraint() {
            Object ref = this.constraint_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.constraint_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getConstraintBytes() {
            Object ref = this.constraint_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.constraint_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setConstraint(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.constraint_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearConstraint() {
            this.constraint_ = ViolationInfo.getDefaultInstance().getConstraint();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setConstraintBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ViolationInfo.checkByteStringIsUtf8(value);
            this.constraint_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getErrorMessage() {
            Object ref = this.errorMessage_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.errorMessage_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getErrorMessageBytes() {
            Object ref = this.errorMessage_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.errorMessage_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setErrorMessage(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.errorMessage_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearErrorMessage() {
            this.errorMessage_ = ViolationInfo.getDefaultInstance().getErrorMessage();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setErrorMessageBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ViolationInfo.checkByteStringIsUtf8(value);
            this.errorMessage_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getCheckedValue() {
            Object ref = this.checkedValue_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.checkedValue_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCheckedValueBytes() {
            Object ref = this.checkedValue_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.checkedValue_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCheckedValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.checkedValue_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearCheckedValue() {
            this.checkedValue_ = ViolationInfo.getDefaultInstance().getCheckedValue();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setCheckedValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ViolationInfo.checkByteStringIsUtf8(value);
            this.checkedValue_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public int getPolicyTypeValue() {
            return this.policyType_;
        }

        public Builder setPolicyTypeValue(int value) {
            this.policyType_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public PolicyType getPolicyType() {
            PolicyType result = PolicyType.forNumber(this.policyType_);
            return result == null ? PolicyType.UNRECOGNIZED : result;
        }

        public Builder setPolicyType(PolicyType value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 8;
            this.policyType_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearPolicyType() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.policyType_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder)super.setUnknownFields(unknownFields);
        }

        @Override
        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder)super.mergeUnknownFields(unknownFields);
        }
    }

    public static enum PolicyType implements ProtocolMessageEnum
    {
        POLICY_TYPE_UNSPECIFIED(0),
        BOOLEAN_CONSTRAINT(1),
        LIST_CONSTRAINT(2),
        CUSTOM_CONSTRAINT(3),
        UNRECOGNIZED(-1);

        public static final int POLICY_TYPE_UNSPECIFIED_VALUE = 0;
        public static final int BOOLEAN_CONSTRAINT_VALUE = 1;
        public static final int LIST_CONSTRAINT_VALUE = 2;
        public static final int CUSTOM_CONSTRAINT_VALUE = 3;
        private static final Internal.EnumLiteMap<PolicyType> internalValueMap;
        private static final PolicyType[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static PolicyType valueOf(int value) {
            return PolicyType.forNumber(value);
        }

        public static PolicyType forNumber(int value) {
            switch (value) {
                case 0: {
                    return POLICY_TYPE_UNSPECIFIED;
                }
                case 1: {
                    return BOOLEAN_CONSTRAINT;
                }
                case 2: {
                    return LIST_CONSTRAINT;
                }
                case 3: {
                    return CUSTOM_CONSTRAINT;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<PolicyType> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return PolicyType.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return PolicyType.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return ViolationInfo.getDescriptor().getEnumTypes().get(0);
        }

        public static PolicyType valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != PolicyType.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private PolicyType(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<PolicyType>(){

                @Override
                public PolicyType findValueByNumber(int number) {
                    return PolicyType.forNumber(number);
                }
            };
            VALUES = PolicyType.values();
        }
    }
}

