/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.RequestMetadataOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.context.AttributeContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class RequestMetadata
extends GeneratedMessageV3
implements RequestMetadataOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CALLER_IP_FIELD_NUMBER = 1;
    private volatile Object callerIp_ = "";
    public static final int CALLER_SUPPLIED_USER_AGENT_FIELD_NUMBER = 2;
    private volatile Object callerSuppliedUserAgent_ = "";
    public static final int CALLER_NETWORK_FIELD_NUMBER = 3;
    private volatile Object callerNetwork_ = "";
    public static final int REQUEST_ATTRIBUTES_FIELD_NUMBER = 7;
    private AttributeContext.Request requestAttributes_;
    public static final int DESTINATION_ATTRIBUTES_FIELD_NUMBER = 8;
    private AttributeContext.Peer destinationAttributes_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final RequestMetadata DEFAULT_INSTANCE = new RequestMetadata();
    private static final Parser<RequestMetadata> PARSER = new AbstractParser<RequestMetadata>(){

        @Override
        public RequestMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = RequestMetadata.newBuilder();
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

    private RequestMetadata(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private RequestMetadata() {
        this.callerIp_ = "";
        this.callerSuppliedUserAgent_ = "";
        this.callerNetwork_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new RequestMetadata();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_RequestMetadata_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_RequestMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(RequestMetadata.class, Builder.class);
    }

    @Override
    public String getCallerIp() {
        Object ref = this.callerIp_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.callerIp_ = s2;
        return s2;
    }

    @Override
    public ByteString getCallerIpBytes() {
        Object ref = this.callerIp_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.callerIp_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getCallerSuppliedUserAgent() {
        Object ref = this.callerSuppliedUserAgent_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.callerSuppliedUserAgent_ = s2;
        return s2;
    }

    @Override
    public ByteString getCallerSuppliedUserAgentBytes() {
        Object ref = this.callerSuppliedUserAgent_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.callerSuppliedUserAgent_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getCallerNetwork() {
        Object ref = this.callerNetwork_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.callerNetwork_ = s2;
        return s2;
    }

    @Override
    public ByteString getCallerNetworkBytes() {
        Object ref = this.callerNetwork_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.callerNetwork_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasRequestAttributes() {
        return this.requestAttributes_ != null;
    }

    @Override
    public AttributeContext.Request getRequestAttributes() {
        return this.requestAttributes_ == null ? AttributeContext.Request.getDefaultInstance() : this.requestAttributes_;
    }

    @Override
    public AttributeContext.RequestOrBuilder getRequestAttributesOrBuilder() {
        return this.requestAttributes_ == null ? AttributeContext.Request.getDefaultInstance() : this.requestAttributes_;
    }

    @Override
    public boolean hasDestinationAttributes() {
        return this.destinationAttributes_ != null;
    }

    @Override
    public AttributeContext.Peer getDestinationAttributes() {
        return this.destinationAttributes_ == null ? AttributeContext.Peer.getDefaultInstance() : this.destinationAttributes_;
    }

    @Override
    public AttributeContext.PeerOrBuilder getDestinationAttributesOrBuilder() {
        return this.destinationAttributes_ == null ? AttributeContext.Peer.getDefaultInstance() : this.destinationAttributes_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.callerIp_)) {
            GeneratedMessageV3.writeString(output, 1, this.callerIp_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.callerSuppliedUserAgent_)) {
            GeneratedMessageV3.writeString(output, 2, this.callerSuppliedUserAgent_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.callerNetwork_)) {
            GeneratedMessageV3.writeString(output, 3, this.callerNetwork_);
        }
        if (this.requestAttributes_ != null) {
            output.writeMessage(7, this.getRequestAttributes());
        }
        if (this.destinationAttributes_ != null) {
            output.writeMessage(8, this.getDestinationAttributes());
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
        if (!GeneratedMessageV3.isStringEmpty(this.callerIp_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.callerIp_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.callerSuppliedUserAgent_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.callerSuppliedUserAgent_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.callerNetwork_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.callerNetwork_);
        }
        if (this.requestAttributes_ != null) {
            size += CodedOutputStream.computeMessageSize(7, this.getRequestAttributes());
        }
        if (this.destinationAttributes_ != null) {
            size += CodedOutputStream.computeMessageSize(8, this.getDestinationAttributes());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RequestMetadata)) {
            return super.equals(obj);
        }
        RequestMetadata other = (RequestMetadata)obj;
        if (!this.getCallerIp().equals(other.getCallerIp())) {
            return false;
        }
        if (!this.getCallerSuppliedUserAgent().equals(other.getCallerSuppliedUserAgent())) {
            return false;
        }
        if (!this.getCallerNetwork().equals(other.getCallerNetwork())) {
            return false;
        }
        if (this.hasRequestAttributes() != other.hasRequestAttributes()) {
            return false;
        }
        if (this.hasRequestAttributes() && !this.getRequestAttributes().equals(other.getRequestAttributes())) {
            return false;
        }
        if (this.hasDestinationAttributes() != other.hasDestinationAttributes()) {
            return false;
        }
        if (this.hasDestinationAttributes() && !this.getDestinationAttributes().equals(other.getDestinationAttributes())) {
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
        hash = 19 * hash + RequestMetadata.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getCallerIp().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getCallerSuppliedUserAgent().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getCallerNetwork().hashCode();
        if (this.hasRequestAttributes()) {
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getRequestAttributes().hashCode();
        }
        if (this.hasDestinationAttributes()) {
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getDestinationAttributes().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static RequestMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static RequestMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static RequestMetadata parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RequestMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static RequestMetadata parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static RequestMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static RequestMetadata parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static RequestMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return RequestMetadata.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RequestMetadata prototype) {
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

    public static RequestMetadata getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RequestMetadata> parser() {
        return PARSER;
    }

    public Parser<RequestMetadata> getParserForType() {
        return PARSER;
    }

    @Override
    public RequestMetadata getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements RequestMetadataOrBuilder {
        private int bitField0_;
        private Object callerIp_ = "";
        private Object callerSuppliedUserAgent_ = "";
        private Object callerNetwork_ = "";
        private AttributeContext.Request requestAttributes_;
        private SingleFieldBuilderV3<AttributeContext.Request, AttributeContext.Request.Builder, AttributeContext.RequestOrBuilder> requestAttributesBuilder_;
        private AttributeContext.Peer destinationAttributes_;
        private SingleFieldBuilderV3<AttributeContext.Peer, AttributeContext.Peer.Builder, AttributeContext.PeerOrBuilder> destinationAttributesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_RequestMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_RequestMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(RequestMetadata.class, Builder.class);
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
            this.callerIp_ = "";
            this.callerSuppliedUserAgent_ = "";
            this.callerNetwork_ = "";
            this.requestAttributes_ = null;
            if (this.requestAttributesBuilder_ != null) {
                this.requestAttributesBuilder_.dispose();
                this.requestAttributesBuilder_ = null;
            }
            this.destinationAttributes_ = null;
            if (this.destinationAttributesBuilder_ != null) {
                this.destinationAttributesBuilder_.dispose();
                this.destinationAttributesBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_RequestMetadata_descriptor;
        }

        @Override
        public RequestMetadata getDefaultInstanceForType() {
            return RequestMetadata.getDefaultInstance();
        }

        @Override
        public RequestMetadata build() {
            RequestMetadata result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public RequestMetadata buildPartial() {
            RequestMetadata result = new RequestMetadata(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(RequestMetadata result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.callerIp_ = this.callerIp_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.callerSuppliedUserAgent_ = this.callerSuppliedUserAgent_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.callerNetwork_ = this.callerNetwork_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.requestAttributes_ = this.requestAttributesBuilder_ == null ? this.requestAttributes_ : this.requestAttributesBuilder_.build();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.destinationAttributes_ = this.destinationAttributesBuilder_ == null ? this.destinationAttributes_ : this.destinationAttributesBuilder_.build();
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
            if (other instanceof RequestMetadata) {
                return this.mergeFrom((RequestMetadata)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(RequestMetadata other) {
            if (other == RequestMetadata.getDefaultInstance()) {
                return this;
            }
            if (!other.getCallerIp().isEmpty()) {
                this.callerIp_ = other.callerIp_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getCallerSuppliedUserAgent().isEmpty()) {
                this.callerSuppliedUserAgent_ = other.callerSuppliedUserAgent_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getCallerNetwork().isEmpty()) {
                this.callerNetwork_ = other.callerNetwork_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.hasRequestAttributes()) {
                this.mergeRequestAttributes(other.getRequestAttributes());
            }
            if (other.hasDestinationAttributes()) {
                this.mergeDestinationAttributes(other.getDestinationAttributes());
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
                block13: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.callerIp_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block13;
                        }
                        case 18: {
                            this.callerSuppliedUserAgent_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block13;
                        }
                        case 26: {
                            this.callerNetwork_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block13;
                        }
                        case 58: {
                            input.readMessage(this.getRequestAttributesFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
                            continue block13;
                        }
                        case 66: {
                            input.readMessage(this.getDestinationAttributesFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block13;
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
        public String getCallerIp() {
            Object ref = this.callerIp_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.callerIp_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCallerIpBytes() {
            Object ref = this.callerIp_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.callerIp_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCallerIp(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.callerIp_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearCallerIp() {
            this.callerIp_ = RequestMetadata.getDefaultInstance().getCallerIp();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setCallerIpBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RequestMetadata.checkByteStringIsUtf8(value);
            this.callerIp_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getCallerSuppliedUserAgent() {
            Object ref = this.callerSuppliedUserAgent_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.callerSuppliedUserAgent_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCallerSuppliedUserAgentBytes() {
            Object ref = this.callerSuppliedUserAgent_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.callerSuppliedUserAgent_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCallerSuppliedUserAgent(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.callerSuppliedUserAgent_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearCallerSuppliedUserAgent() {
            this.callerSuppliedUserAgent_ = RequestMetadata.getDefaultInstance().getCallerSuppliedUserAgent();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setCallerSuppliedUserAgentBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RequestMetadata.checkByteStringIsUtf8(value);
            this.callerSuppliedUserAgent_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getCallerNetwork() {
            Object ref = this.callerNetwork_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.callerNetwork_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCallerNetworkBytes() {
            Object ref = this.callerNetwork_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.callerNetwork_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCallerNetwork(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.callerNetwork_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearCallerNetwork() {
            this.callerNetwork_ = RequestMetadata.getDefaultInstance().getCallerNetwork();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setCallerNetworkBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            RequestMetadata.checkByteStringIsUtf8(value);
            this.callerNetwork_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasRequestAttributes() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public AttributeContext.Request getRequestAttributes() {
            if (this.requestAttributesBuilder_ == null) {
                return this.requestAttributes_ == null ? AttributeContext.Request.getDefaultInstance() : this.requestAttributes_;
            }
            return this.requestAttributesBuilder_.getMessage();
        }

        public Builder setRequestAttributes(AttributeContext.Request value) {
            if (this.requestAttributesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.requestAttributes_ = value;
            } else {
                this.requestAttributesBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setRequestAttributes(AttributeContext.Request.Builder builderForValue) {
            if (this.requestAttributesBuilder_ == null) {
                this.requestAttributes_ = builderForValue.build();
            } else {
                this.requestAttributesBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeRequestAttributes(AttributeContext.Request value) {
            if (this.requestAttributesBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.requestAttributes_ != null && this.requestAttributes_ != AttributeContext.Request.getDefaultInstance()) {
                    this.getRequestAttributesBuilder().mergeFrom(value);
                } else {
                    this.requestAttributes_ = value;
                }
            } else {
                this.requestAttributesBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearRequestAttributes() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.requestAttributes_ = null;
            if (this.requestAttributesBuilder_ != null) {
                this.requestAttributesBuilder_.dispose();
                this.requestAttributesBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public AttributeContext.Request.Builder getRequestAttributesBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getRequestAttributesFieldBuilder().getBuilder();
        }

        @Override
        public AttributeContext.RequestOrBuilder getRequestAttributesOrBuilder() {
            if (this.requestAttributesBuilder_ != null) {
                return this.requestAttributesBuilder_.getMessageOrBuilder();
            }
            return this.requestAttributes_ == null ? AttributeContext.Request.getDefaultInstance() : this.requestAttributes_;
        }

        private SingleFieldBuilderV3<AttributeContext.Request, AttributeContext.Request.Builder, AttributeContext.RequestOrBuilder> getRequestAttributesFieldBuilder() {
            if (this.requestAttributesBuilder_ == null) {
                this.requestAttributesBuilder_ = new SingleFieldBuilderV3(this.getRequestAttributes(), this.getParentForChildren(), this.isClean());
                this.requestAttributes_ = null;
            }
            return this.requestAttributesBuilder_;
        }

        @Override
        public boolean hasDestinationAttributes() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public AttributeContext.Peer getDestinationAttributes() {
            if (this.destinationAttributesBuilder_ == null) {
                return this.destinationAttributes_ == null ? AttributeContext.Peer.getDefaultInstance() : this.destinationAttributes_;
            }
            return this.destinationAttributesBuilder_.getMessage();
        }

        public Builder setDestinationAttributes(AttributeContext.Peer value) {
            if (this.destinationAttributesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.destinationAttributes_ = value;
            } else {
                this.destinationAttributesBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setDestinationAttributes(AttributeContext.Peer.Builder builderForValue) {
            if (this.destinationAttributesBuilder_ == null) {
                this.destinationAttributes_ = builderForValue.build();
            } else {
                this.destinationAttributesBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeDestinationAttributes(AttributeContext.Peer value) {
            if (this.destinationAttributesBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.destinationAttributes_ != null && this.destinationAttributes_ != AttributeContext.Peer.getDefaultInstance()) {
                    this.getDestinationAttributesBuilder().mergeFrom(value);
                } else {
                    this.destinationAttributes_ = value;
                }
            } else {
                this.destinationAttributesBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearDestinationAttributes() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.destinationAttributes_ = null;
            if (this.destinationAttributesBuilder_ != null) {
                this.destinationAttributesBuilder_.dispose();
                this.destinationAttributesBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public AttributeContext.Peer.Builder getDestinationAttributesBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getDestinationAttributesFieldBuilder().getBuilder();
        }

        @Override
        public AttributeContext.PeerOrBuilder getDestinationAttributesOrBuilder() {
            if (this.destinationAttributesBuilder_ != null) {
                return this.destinationAttributesBuilder_.getMessageOrBuilder();
            }
            return this.destinationAttributes_ == null ? AttributeContext.Peer.getDefaultInstance() : this.destinationAttributes_;
        }

        private SingleFieldBuilderV3<AttributeContext.Peer, AttributeContext.Peer.Builder, AttributeContext.PeerOrBuilder> getDestinationAttributesFieldBuilder() {
            if (this.destinationAttributesBuilder_ == null) {
                this.destinationAttributesBuilder_ = new SingleFieldBuilderV3(this.getDestinationAttributes(), this.getParentForChildren(), this.isClean());
                this.destinationAttributes_ = null;
            }
            return this.destinationAttributesBuilder_;
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
}

