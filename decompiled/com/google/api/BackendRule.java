/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.BackendProto;
import com.google.api.BackendRuleOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class BackendRule
extends GeneratedMessageV3
implements BackendRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    private int authenticationCase_ = 0;
    private Object authentication_;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int ADDRESS_FIELD_NUMBER = 2;
    private volatile Object address_ = "";
    public static final int DEADLINE_FIELD_NUMBER = 3;
    private double deadline_ = 0.0;
    public static final int MIN_DEADLINE_FIELD_NUMBER = 4;
    private double minDeadline_ = 0.0;
    public static final int OPERATION_DEADLINE_FIELD_NUMBER = 5;
    private double operationDeadline_ = 0.0;
    public static final int PATH_TRANSLATION_FIELD_NUMBER = 6;
    private int pathTranslation_ = 0;
    public static final int JWT_AUDIENCE_FIELD_NUMBER = 7;
    public static final int DISABLE_AUTH_FIELD_NUMBER = 8;
    public static final int PROTOCOL_FIELD_NUMBER = 9;
    private volatile Object protocol_ = "";
    public static final int OVERRIDES_BY_REQUEST_PROTOCOL_FIELD_NUMBER = 10;
    private MapField<String, BackendRule> overridesByRequestProtocol_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final BackendRule DEFAULT_INSTANCE = new BackendRule();
    private static final Parser<BackendRule> PARSER = new AbstractParser<BackendRule>(){

        @Override
        public BackendRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = BackendRule.newBuilder();
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

    private BackendRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private BackendRule() {
        this.selector_ = "";
        this.address_ = "";
        this.pathTranslation_ = 0;
        this.protocol_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new BackendRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return BackendProto.internal_static_google_api_BackendRule_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 10: {
                return this.internalGetOverridesByRequestProtocol();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return BackendProto.internal_static_google_api_BackendRule_fieldAccessorTable.ensureFieldAccessorsInitialized(BackendRule.class, Builder.class);
    }

    @Override
    public AuthenticationCase getAuthenticationCase() {
        return AuthenticationCase.forNumber(this.authenticationCase_);
    }

    @Override
    public String getSelector() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.selector_ = s2;
        return s2;
    }

    @Override
    public ByteString getSelectorBytes() {
        Object ref = this.selector_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.selector_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getAddress() {
        Object ref = this.address_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.address_ = s2;
        return s2;
    }

    @Override
    public ByteString getAddressBytes() {
        Object ref = this.address_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.address_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public double getDeadline() {
        return this.deadline_;
    }

    @Override
    @Deprecated
    public double getMinDeadline() {
        return this.minDeadline_;
    }

    @Override
    public double getOperationDeadline() {
        return this.operationDeadline_;
    }

    @Override
    public int getPathTranslationValue() {
        return this.pathTranslation_;
    }

    @Override
    public PathTranslation getPathTranslation() {
        PathTranslation result = PathTranslation.forNumber(this.pathTranslation_);
        return result == null ? PathTranslation.UNRECOGNIZED : result;
    }

    @Override
    public boolean hasJwtAudience() {
        return this.authenticationCase_ == 7;
    }

    @Override
    public String getJwtAudience() {
        Object ref = "";
        if (this.authenticationCase_ == 7) {
            ref = this.authentication_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.authenticationCase_ == 7) {
            this.authentication_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getJwtAudienceBytes() {
        Object ref = "";
        if (this.authenticationCase_ == 7) {
            ref = this.authentication_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.authenticationCase_ == 7) {
                this.authentication_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasDisableAuth() {
        return this.authenticationCase_ == 8;
    }

    @Override
    public boolean getDisableAuth() {
        if (this.authenticationCase_ == 8) {
            return (Boolean)this.authentication_;
        }
        return false;
    }

    @Override
    public String getProtocol() {
        Object ref = this.protocol_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.protocol_ = s2;
        return s2;
    }

    @Override
    public ByteString getProtocolBytes() {
        Object ref = this.protocol_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.protocol_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    private MapField<String, BackendRule> internalGetOverridesByRequestProtocol() {
        if (this.overridesByRequestProtocol_ == null) {
            return MapField.emptyMapField(OverridesByRequestProtocolDefaultEntryHolder.defaultEntry);
        }
        return this.overridesByRequestProtocol_;
    }

    @Override
    public int getOverridesByRequestProtocolCount() {
        return this.internalGetOverridesByRequestProtocol().getMap().size();
    }

    @Override
    public boolean containsOverridesByRequestProtocol(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetOverridesByRequestProtocol().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, BackendRule> getOverridesByRequestProtocol() {
        return this.getOverridesByRequestProtocolMap();
    }

    @Override
    public Map<String, BackendRule> getOverridesByRequestProtocolMap() {
        return this.internalGetOverridesByRequestProtocol().getMap();
    }

    @Override
    public BackendRule getOverridesByRequestProtocolOrDefault(String key, BackendRule defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, BackendRule> map = this.internalGetOverridesByRequestProtocol().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public BackendRule getOverridesByRequestProtocolOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, BackendRule> map = this.internalGetOverridesByRequestProtocol().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            GeneratedMessageV3.writeString(output, 1, this.selector_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.address_)) {
            GeneratedMessageV3.writeString(output, 2, this.address_);
        }
        if (Double.doubleToRawLongBits(this.deadline_) != 0L) {
            output.writeDouble(3, this.deadline_);
        }
        if (Double.doubleToRawLongBits(this.minDeadline_) != 0L) {
            output.writeDouble(4, this.minDeadline_);
        }
        if (Double.doubleToRawLongBits(this.operationDeadline_) != 0L) {
            output.writeDouble(5, this.operationDeadline_);
        }
        if (this.pathTranslation_ != PathTranslation.PATH_TRANSLATION_UNSPECIFIED.getNumber()) {
            output.writeEnum(6, this.pathTranslation_);
        }
        if (this.authenticationCase_ == 7) {
            GeneratedMessageV3.writeString(output, 7, this.authentication_);
        }
        if (this.authenticationCase_ == 8) {
            output.writeBool(8, (Boolean)this.authentication_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
            GeneratedMessageV3.writeString(output, 9, this.protocol_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetOverridesByRequestProtocol(), OverridesByRequestProtocolDefaultEntryHolder.defaultEntry, 10);
        this.getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.address_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.address_);
        }
        if (Double.doubleToRawLongBits(this.deadline_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(3, this.deadline_);
        }
        if (Double.doubleToRawLongBits(this.minDeadline_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(4, this.minDeadline_);
        }
        if (Double.doubleToRawLongBits(this.operationDeadline_) != 0L) {
            size += CodedOutputStream.computeDoubleSize(5, this.operationDeadline_);
        }
        if (this.pathTranslation_ != PathTranslation.PATH_TRANSLATION_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(6, this.pathTranslation_);
        }
        if (this.authenticationCase_ == 7) {
            size += GeneratedMessageV3.computeStringSize(7, this.authentication_);
        }
        if (this.authenticationCase_ == 8) {
            size += CodedOutputStream.computeBoolSize(8, (Boolean)this.authentication_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
            size += GeneratedMessageV3.computeStringSize(9, this.protocol_);
        }
        for (Map.Entry<String, BackendRule> entry : this.internalGetOverridesByRequestProtocol().getMap().entrySet()) {
            Message overridesByRequestProtocol__ = ((MapEntry.Builder)OverridesByRequestProtocolDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(10, overridesByRequestProtocol__);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BackendRule)) {
            return super.equals(obj);
        }
        BackendRule other = (BackendRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getAddress().equals(other.getAddress())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getDeadline()) != Double.doubleToLongBits(other.getDeadline())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getMinDeadline()) != Double.doubleToLongBits(other.getMinDeadline())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getOperationDeadline()) != Double.doubleToLongBits(other.getOperationDeadline())) {
            return false;
        }
        if (this.pathTranslation_ != other.pathTranslation_) {
            return false;
        }
        if (!this.getProtocol().equals(other.getProtocol())) {
            return false;
        }
        if (!this.internalGetOverridesByRequestProtocol().equals(other.internalGetOverridesByRequestProtocol())) {
            return false;
        }
        if (!this.getAuthenticationCase().equals(other.getAuthenticationCase())) {
            return false;
        }
        switch (this.authenticationCase_) {
            case 7: {
                if (this.getJwtAudience().equals(other.getJwtAudience())) break;
                return false;
            }
            case 8: {
                if (this.getDisableAuth() == other.getDisableAuth()) break;
                return false;
            }
        }
        return this.getUnknownFields().equals(other.getUnknownFields());
    }

    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + BackendRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getAddress().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getDeadline()));
        hash = 37 * hash + 4;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getMinDeadline()));
        hash = 37 * hash + 5;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getOperationDeadline()));
        hash = 37 * hash + 6;
        hash = 53 * hash + this.pathTranslation_;
        hash = 37 * hash + 9;
        hash = 53 * hash + this.getProtocol().hashCode();
        if (!this.internalGetOverridesByRequestProtocol().getMap().isEmpty()) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.internalGetOverridesByRequestProtocol().hashCode();
        }
        switch (this.authenticationCase_) {
            case 7: {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getJwtAudience().hashCode();
                break;
            }
            case 8: {
                hash = 37 * hash + 8;
                hash = 53 * hash + Internal.hashBoolean(this.getDisableAuth());
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static BackendRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static BackendRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static BackendRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static BackendRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static BackendRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static BackendRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static BackendRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static BackendRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static BackendRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static BackendRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static BackendRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static BackendRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return BackendRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(BackendRule prototype) {
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

    public static BackendRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BackendRule> parser() {
        return PARSER;
    }

    public Parser<BackendRule> getParserForType() {
        return PARSER;
    }

    @Override
    public BackendRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements BackendRuleOrBuilder {
        private int authenticationCase_ = 0;
        private Object authentication_;
        private int bitField0_;
        private Object selector_ = "";
        private Object address_ = "";
        private double deadline_;
        private double minDeadline_;
        private double operationDeadline_;
        private int pathTranslation_ = 0;
        private Object protocol_ = "";
        private MapField<String, BackendRule> overridesByRequestProtocol_;

        public static final Descriptors.Descriptor getDescriptor() {
            return BackendProto.internal_static_google_api_BackendRule_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 10: {
                    return this.internalGetOverridesByRequestProtocol();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 10: {
                    return this.internalGetMutableOverridesByRequestProtocol();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return BackendProto.internal_static_google_api_BackendRule_fieldAccessorTable.ensureFieldAccessorsInitialized(BackendRule.class, Builder.class);
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
            this.selector_ = "";
            this.address_ = "";
            this.deadline_ = 0.0;
            this.minDeadline_ = 0.0;
            this.operationDeadline_ = 0.0;
            this.pathTranslation_ = 0;
            this.protocol_ = "";
            this.internalGetMutableOverridesByRequestProtocol().clear();
            this.authenticationCase_ = 0;
            this.authentication_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return BackendProto.internal_static_google_api_BackendRule_descriptor;
        }

        @Override
        public BackendRule getDefaultInstanceForType() {
            return BackendRule.getDefaultInstance();
        }

        @Override
        public BackendRule build() {
            BackendRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public BackendRule buildPartial() {
            BackendRule result = new BackendRule(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(BackendRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.address_ = this.address_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.deadline_ = this.deadline_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.minDeadline_ = this.minDeadline_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.operationDeadline_ = this.operationDeadline_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.pathTranslation_ = this.pathTranslation_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.protocol_ = this.protocol_;
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.overridesByRequestProtocol_ = this.internalGetOverridesByRequestProtocol();
                result.overridesByRequestProtocol_.makeImmutable();
            }
        }

        private void buildPartialOneofs(BackendRule result) {
            result.authenticationCase_ = this.authenticationCase_;
            result.authentication_ = this.authentication_;
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
            if (other instanceof BackendRule) {
                return this.mergeFrom((BackendRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(BackendRule other) {
            if (other == BackendRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getAddress().isEmpty()) {
                this.address_ = other.address_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.getDeadline() != 0.0) {
                this.setDeadline(other.getDeadline());
            }
            if (other.getMinDeadline() != 0.0) {
                this.setMinDeadline(other.getMinDeadline());
            }
            if (other.getOperationDeadline() != 0.0) {
                this.setOperationDeadline(other.getOperationDeadline());
            }
            if (other.pathTranslation_ != 0) {
                this.setPathTranslationValue(other.getPathTranslationValue());
            }
            if (!other.getProtocol().isEmpty()) {
                this.protocol_ = other.protocol_;
                this.bitField0_ |= 0x100;
                this.onChanged();
            }
            this.internalGetMutableOverridesByRequestProtocol().mergeFrom(other.internalGetOverridesByRequestProtocol());
            this.bitField0_ |= 0x200;
            switch (other.getAuthenticationCase()) {
                case JWT_AUDIENCE: {
                    this.authenticationCase_ = 7;
                    this.authentication_ = other.authentication_;
                    this.onChanged();
                    break;
                }
                case DISABLE_AUTH: {
                    this.setDisableAuth(other.getDisableAuth());
                    break;
                }
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
                block18: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block18;
                        }
                        case 10: {
                            this.selector_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block18;
                        }
                        case 18: {
                            this.address_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block18;
                        }
                        case 25: {
                            this.deadline_ = input.readDouble();
                            this.bitField0_ |= 4;
                            continue block18;
                        }
                        case 33: {
                            this.minDeadline_ = input.readDouble();
                            this.bitField0_ |= 8;
                            continue block18;
                        }
                        case 41: {
                            this.operationDeadline_ = input.readDouble();
                            this.bitField0_ |= 0x10;
                            continue block18;
                        }
                        case 48: {
                            this.pathTranslation_ = input.readEnum();
                            this.bitField0_ |= 0x20;
                            continue block18;
                        }
                        case 58: {
                            String s2 = input.readStringRequireUtf8();
                            this.authenticationCase_ = 7;
                            this.authentication_ = s2;
                            continue block18;
                        }
                        case 64: {
                            this.authentication_ = input.readBool();
                            this.authenticationCase_ = 8;
                            continue block18;
                        }
                        case 74: {
                            this.protocol_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x100;
                            continue block18;
                        }
                        case 82: {
                            MapEntry<String, BackendRule> overridesByRequestProtocol__ = input.readMessage(OverridesByRequestProtocolDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableOverridesByRequestProtocol().getMutableMap().put(overridesByRequestProtocol__.getKey(), overridesByRequestProtocol__.getValue());
                            this.bitField0_ |= 0x200;
                            continue block18;
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
        public AuthenticationCase getAuthenticationCase() {
            return AuthenticationCase.forNumber(this.authenticationCase_);
        }

        public Builder clearAuthentication() {
            this.authenticationCase_ = 0;
            this.authentication_ = null;
            this.onChanged();
            return this;
        }

        @Override
        public String getSelector() {
            Object ref = this.selector_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.selector_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSelectorBytes() {
            Object ref = this.selector_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.selector_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSelector(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSelector() {
            this.selector_ = BackendRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            BackendRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getAddress() {
            Object ref = this.address_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.address_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getAddressBytes() {
            Object ref = this.address_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.address_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setAddress(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.address_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearAddress() {
            this.address_ = BackendRule.getDefaultInstance().getAddress();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setAddressBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            BackendRule.checkByteStringIsUtf8(value);
            this.address_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public double getDeadline() {
            return this.deadline_;
        }

        public Builder setDeadline(double value) {
            this.deadline_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDeadline() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.deadline_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        @Deprecated
        public double getMinDeadline() {
            return this.minDeadline_;
        }

        @Deprecated
        public Builder setMinDeadline(double value) {
            this.minDeadline_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder clearMinDeadline() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.minDeadline_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public double getOperationDeadline() {
            return this.operationDeadline_;
        }

        public Builder setOperationDeadline(double value) {
            this.operationDeadline_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearOperationDeadline() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.operationDeadline_ = 0.0;
            this.onChanged();
            return this;
        }

        @Override
        public int getPathTranslationValue() {
            return this.pathTranslation_;
        }

        public Builder setPathTranslationValue(int value) {
            this.pathTranslation_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public PathTranslation getPathTranslation() {
            PathTranslation result = PathTranslation.forNumber(this.pathTranslation_);
            return result == null ? PathTranslation.UNRECOGNIZED : result;
        }

        public Builder setPathTranslation(PathTranslation value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 0x20;
            this.pathTranslation_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearPathTranslation() {
            this.bitField0_ &= 0xFFFFFFDF;
            this.pathTranslation_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasJwtAudience() {
            return this.authenticationCase_ == 7;
        }

        @Override
        public String getJwtAudience() {
            Object ref = "";
            if (this.authenticationCase_ == 7) {
                ref = this.authentication_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.authenticationCase_ == 7) {
                    this.authentication_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getJwtAudienceBytes() {
            Object ref = "";
            if (this.authenticationCase_ == 7) {
                ref = this.authentication_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.authenticationCase_ == 7) {
                    this.authentication_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setJwtAudience(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.authenticationCase_ = 7;
            this.authentication_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearJwtAudience() {
            if (this.authenticationCase_ == 7) {
                this.authenticationCase_ = 0;
                this.authentication_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setJwtAudienceBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            BackendRule.checkByteStringIsUtf8(value);
            this.authenticationCase_ = 7;
            this.authentication_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasDisableAuth() {
            return this.authenticationCase_ == 8;
        }

        @Override
        public boolean getDisableAuth() {
            if (this.authenticationCase_ == 8) {
                return (Boolean)this.authentication_;
            }
            return false;
        }

        public Builder setDisableAuth(boolean value) {
            this.authenticationCase_ = 8;
            this.authentication_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearDisableAuth() {
            if (this.authenticationCase_ == 8) {
                this.authenticationCase_ = 0;
                this.authentication_ = null;
                this.onChanged();
            }
            return this;
        }

        @Override
        public String getProtocol() {
            Object ref = this.protocol_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.protocol_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getProtocolBytes() {
            Object ref = this.protocol_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.protocol_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setProtocol(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.protocol_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearProtocol() {
            this.protocol_ = BackendRule.getDefaultInstance().getProtocol();
            this.bitField0_ &= 0xFFFFFEFF;
            this.onChanged();
            return this;
        }

        public Builder setProtocolBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            BackendRule.checkByteStringIsUtf8(value);
            this.protocol_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        private MapField<String, BackendRule> internalGetOverridesByRequestProtocol() {
            if (this.overridesByRequestProtocol_ == null) {
                return MapField.emptyMapField(OverridesByRequestProtocolDefaultEntryHolder.defaultEntry);
            }
            return this.overridesByRequestProtocol_;
        }

        private MapField<String, BackendRule> internalGetMutableOverridesByRequestProtocol() {
            if (this.overridesByRequestProtocol_ == null) {
                this.overridesByRequestProtocol_ = MapField.newMapField(OverridesByRequestProtocolDefaultEntryHolder.defaultEntry);
            }
            if (!this.overridesByRequestProtocol_.isMutable()) {
                this.overridesByRequestProtocol_ = this.overridesByRequestProtocol_.copy();
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this.overridesByRequestProtocol_;
        }

        @Override
        public int getOverridesByRequestProtocolCount() {
            return this.internalGetOverridesByRequestProtocol().getMap().size();
        }

        @Override
        public boolean containsOverridesByRequestProtocol(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetOverridesByRequestProtocol().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, BackendRule> getOverridesByRequestProtocol() {
            return this.getOverridesByRequestProtocolMap();
        }

        @Override
        public Map<String, BackendRule> getOverridesByRequestProtocolMap() {
            return this.internalGetOverridesByRequestProtocol().getMap();
        }

        @Override
        public BackendRule getOverridesByRequestProtocolOrDefault(String key, BackendRule defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, BackendRule> map = this.internalGetOverridesByRequestProtocol().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public BackendRule getOverridesByRequestProtocolOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, BackendRule> map = this.internalGetOverridesByRequestProtocol().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearOverridesByRequestProtocol() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.internalGetMutableOverridesByRequestProtocol().getMutableMap().clear();
            return this;
        }

        public Builder removeOverridesByRequestProtocol(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableOverridesByRequestProtocol().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, BackendRule> getMutableOverridesByRequestProtocol() {
            this.bitField0_ |= 0x200;
            return this.internalGetMutableOverridesByRequestProtocol().getMutableMap();
        }

        public Builder putOverridesByRequestProtocol(String key, BackendRule value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableOverridesByRequestProtocol().getMutableMap().put(key, value);
            this.bitField0_ |= 0x200;
            return this;
        }

        public Builder putAllOverridesByRequestProtocol(Map<String, BackendRule> values2) {
            this.internalGetMutableOverridesByRequestProtocol().getMutableMap().putAll(values2);
            this.bitField0_ |= 0x200;
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

    public static enum AuthenticationCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        JWT_AUDIENCE(7),
        DISABLE_AUTH(8),
        AUTHENTICATION_NOT_SET(0);

        private final int value;

        private AuthenticationCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static AuthenticationCase valueOf(int value) {
            return AuthenticationCase.forNumber(value);
        }

        public static AuthenticationCase forNumber(int value) {
            switch (value) {
                case 7: {
                    return JWT_AUDIENCE;
                }
                case 8: {
                    return DISABLE_AUTH;
                }
                case 0: {
                    return AUTHENTICATION_NOT_SET;
                }
            }
            return null;
        }

        @Override
        public int getNumber() {
            return this.value;
        }
    }

    public static enum PathTranslation implements ProtocolMessageEnum
    {
        PATH_TRANSLATION_UNSPECIFIED(0),
        CONSTANT_ADDRESS(1),
        APPEND_PATH_TO_ADDRESS(2),
        UNRECOGNIZED(-1);

        public static final int PATH_TRANSLATION_UNSPECIFIED_VALUE = 0;
        public static final int CONSTANT_ADDRESS_VALUE = 1;
        public static final int APPEND_PATH_TO_ADDRESS_VALUE = 2;
        private static final Internal.EnumLiteMap<PathTranslation> internalValueMap;
        private static final PathTranslation[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static PathTranslation valueOf(int value) {
            return PathTranslation.forNumber(value);
        }

        public static PathTranslation forNumber(int value) {
            switch (value) {
                case 0: {
                    return PATH_TRANSLATION_UNSPECIFIED;
                }
                case 1: {
                    return CONSTANT_ADDRESS;
                }
                case 2: {
                    return APPEND_PATH_TO_ADDRESS;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<PathTranslation> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return PathTranslation.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return PathTranslation.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return BackendRule.getDescriptor().getEnumTypes().get(0);
        }

        public static PathTranslation valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != PathTranslation.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private PathTranslation(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<PathTranslation>(){

                @Override
                public PathTranslation findValueByNumber(int number) {
                    return PathTranslation.forNumber(number);
                }
            };
            VALUES = PathTranslation.values();
        }
    }

    private static final class OverridesByRequestProtocolDefaultEntryHolder {
        static final MapEntry<String, BackendRule> defaultEntry = MapEntry.newDefaultInstance(BackendProto.internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, BackendRule.getDefaultInstance());

        private OverridesByRequestProtocolDefaultEntryHolder() {
        }
    }
}

