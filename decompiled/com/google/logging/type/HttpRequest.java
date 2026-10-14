/*
 * Decompiled with CFR 0.152.
 */
package com.google.logging.type;

import com.google.logging.type.HttpRequestOrBuilder;
import com.google.logging.type.HttpRequestProto;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class HttpRequest
extends GeneratedMessageV3
implements HttpRequestOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REQUEST_METHOD_FIELD_NUMBER = 1;
    private volatile Object requestMethod_ = "";
    public static final int REQUEST_URL_FIELD_NUMBER = 2;
    private volatile Object requestUrl_ = "";
    public static final int REQUEST_SIZE_FIELD_NUMBER = 3;
    private long requestSize_ = 0L;
    public static final int STATUS_FIELD_NUMBER = 4;
    private int status_ = 0;
    public static final int RESPONSE_SIZE_FIELD_NUMBER = 5;
    private long responseSize_ = 0L;
    public static final int USER_AGENT_FIELD_NUMBER = 6;
    private volatile Object userAgent_ = "";
    public static final int REMOTE_IP_FIELD_NUMBER = 7;
    private volatile Object remoteIp_ = "";
    public static final int SERVER_IP_FIELD_NUMBER = 13;
    private volatile Object serverIp_ = "";
    public static final int REFERER_FIELD_NUMBER = 8;
    private volatile Object referer_ = "";
    public static final int LATENCY_FIELD_NUMBER = 14;
    private Duration latency_;
    public static final int CACHE_LOOKUP_FIELD_NUMBER = 11;
    private boolean cacheLookup_ = false;
    public static final int CACHE_HIT_FIELD_NUMBER = 9;
    private boolean cacheHit_ = false;
    public static final int CACHE_VALIDATED_WITH_ORIGIN_SERVER_FIELD_NUMBER = 10;
    private boolean cacheValidatedWithOriginServer_ = false;
    public static final int CACHE_FILL_BYTES_FIELD_NUMBER = 12;
    private long cacheFillBytes_ = 0L;
    public static final int PROTOCOL_FIELD_NUMBER = 15;
    private volatile Object protocol_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final HttpRequest DEFAULT_INSTANCE = new HttpRequest();
    private static final Parser<HttpRequest> PARSER = new AbstractParser<HttpRequest>(){

        @Override
        public HttpRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = HttpRequest.newBuilder();
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

    private HttpRequest(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private HttpRequest() {
        this.requestMethod_ = "";
        this.requestUrl_ = "";
        this.userAgent_ = "";
        this.remoteIp_ = "";
        this.serverIp_ = "";
        this.referer_ = "";
        this.protocol_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new HttpRequest();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return HttpRequestProto.internal_static_google_logging_type_HttpRequest_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return HttpRequestProto.internal_static_google_logging_type_HttpRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HttpRequest.class, Builder.class);
    }

    @Override
    public String getRequestMethod() {
        Object ref = this.requestMethod_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.requestMethod_ = s2;
        return s2;
    }

    @Override
    public ByteString getRequestMethodBytes() {
        Object ref = this.requestMethod_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.requestMethod_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getRequestUrl() {
        Object ref = this.requestUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.requestUrl_ = s2;
        return s2;
    }

    @Override
    public ByteString getRequestUrlBytes() {
        Object ref = this.requestUrl_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.requestUrl_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public long getRequestSize() {
        return this.requestSize_;
    }

    @Override
    public int getStatus() {
        return this.status_;
    }

    @Override
    public long getResponseSize() {
        return this.responseSize_;
    }

    @Override
    public String getUserAgent() {
        Object ref = this.userAgent_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.userAgent_ = s2;
        return s2;
    }

    @Override
    public ByteString getUserAgentBytes() {
        Object ref = this.userAgent_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.userAgent_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getRemoteIp() {
        Object ref = this.remoteIp_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.remoteIp_ = s2;
        return s2;
    }

    @Override
    public ByteString getRemoteIpBytes() {
        Object ref = this.remoteIp_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.remoteIp_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getServerIp() {
        Object ref = this.serverIp_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.serverIp_ = s2;
        return s2;
    }

    @Override
    public ByteString getServerIpBytes() {
        Object ref = this.serverIp_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.serverIp_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getReferer() {
        Object ref = this.referer_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.referer_ = s2;
        return s2;
    }

    @Override
    public ByteString getRefererBytes() {
        Object ref = this.referer_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.referer_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasLatency() {
        return this.latency_ != null;
    }

    @Override
    public Duration getLatency() {
        return this.latency_ == null ? Duration.getDefaultInstance() : this.latency_;
    }

    @Override
    public DurationOrBuilder getLatencyOrBuilder() {
        return this.latency_ == null ? Duration.getDefaultInstance() : this.latency_;
    }

    @Override
    public boolean getCacheLookup() {
        return this.cacheLookup_;
    }

    @Override
    public boolean getCacheHit() {
        return this.cacheHit_;
    }

    @Override
    public boolean getCacheValidatedWithOriginServer() {
        return this.cacheValidatedWithOriginServer_;
    }

    @Override
    public long getCacheFillBytes() {
        return this.cacheFillBytes_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.requestMethod_)) {
            GeneratedMessageV3.writeString(output, 1, this.requestMethod_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.requestUrl_)) {
            GeneratedMessageV3.writeString(output, 2, this.requestUrl_);
        }
        if (this.requestSize_ != 0L) {
            output.writeInt64(3, this.requestSize_);
        }
        if (this.status_ != 0) {
            output.writeInt32(4, this.status_);
        }
        if (this.responseSize_ != 0L) {
            output.writeInt64(5, this.responseSize_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.userAgent_)) {
            GeneratedMessageV3.writeString(output, 6, this.userAgent_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.remoteIp_)) {
            GeneratedMessageV3.writeString(output, 7, this.remoteIp_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.referer_)) {
            GeneratedMessageV3.writeString(output, 8, this.referer_);
        }
        if (this.cacheHit_) {
            output.writeBool(9, this.cacheHit_);
        }
        if (this.cacheValidatedWithOriginServer_) {
            output.writeBool(10, this.cacheValidatedWithOriginServer_);
        }
        if (this.cacheLookup_) {
            output.writeBool(11, this.cacheLookup_);
        }
        if (this.cacheFillBytes_ != 0L) {
            output.writeInt64(12, this.cacheFillBytes_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serverIp_)) {
            GeneratedMessageV3.writeString(output, 13, this.serverIp_);
        }
        if (this.latency_ != null) {
            output.writeMessage(14, this.getLatency());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
            GeneratedMessageV3.writeString(output, 15, this.protocol_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.requestMethod_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.requestMethod_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.requestUrl_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.requestUrl_);
        }
        if (this.requestSize_ != 0L) {
            size += CodedOutputStream.computeInt64Size(3, this.requestSize_);
        }
        if (this.status_ != 0) {
            size += CodedOutputStream.computeInt32Size(4, this.status_);
        }
        if (this.responseSize_ != 0L) {
            size += CodedOutputStream.computeInt64Size(5, this.responseSize_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.userAgent_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.userAgent_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.remoteIp_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.remoteIp_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.referer_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.referer_);
        }
        if (this.cacheHit_) {
            size += CodedOutputStream.computeBoolSize(9, this.cacheHit_);
        }
        if (this.cacheValidatedWithOriginServer_) {
            size += CodedOutputStream.computeBoolSize(10, this.cacheValidatedWithOriginServer_);
        }
        if (this.cacheLookup_) {
            size += CodedOutputStream.computeBoolSize(11, this.cacheLookup_);
        }
        if (this.cacheFillBytes_ != 0L) {
            size += CodedOutputStream.computeInt64Size(12, this.cacheFillBytes_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serverIp_)) {
            size += GeneratedMessageV3.computeStringSize(13, this.serverIp_);
        }
        if (this.latency_ != null) {
            size += CodedOutputStream.computeMessageSize(14, this.getLatency());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
            size += GeneratedMessageV3.computeStringSize(15, this.protocol_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HttpRequest)) {
            return super.equals(obj);
        }
        HttpRequest other = (HttpRequest)obj;
        if (!this.getRequestMethod().equals(other.getRequestMethod())) {
            return false;
        }
        if (!this.getRequestUrl().equals(other.getRequestUrl())) {
            return false;
        }
        if (this.getRequestSize() != other.getRequestSize()) {
            return false;
        }
        if (this.getStatus() != other.getStatus()) {
            return false;
        }
        if (this.getResponseSize() != other.getResponseSize()) {
            return false;
        }
        if (!this.getUserAgent().equals(other.getUserAgent())) {
            return false;
        }
        if (!this.getRemoteIp().equals(other.getRemoteIp())) {
            return false;
        }
        if (!this.getServerIp().equals(other.getServerIp())) {
            return false;
        }
        if (!this.getReferer().equals(other.getReferer())) {
            return false;
        }
        if (this.hasLatency() != other.hasLatency()) {
            return false;
        }
        if (this.hasLatency() && !this.getLatency().equals(other.getLatency())) {
            return false;
        }
        if (this.getCacheLookup() != other.getCacheLookup()) {
            return false;
        }
        if (this.getCacheHit() != other.getCacheHit()) {
            return false;
        }
        if (this.getCacheValidatedWithOriginServer() != other.getCacheValidatedWithOriginServer()) {
            return false;
        }
        if (this.getCacheFillBytes() != other.getCacheFillBytes()) {
            return false;
        }
        if (!this.getProtocol().equals(other.getProtocol())) {
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
        hash = 19 * hash + HttpRequest.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getRequestMethod().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getRequestUrl().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashLong(this.getRequestSize());
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getStatus();
        hash = 37 * hash + 5;
        hash = 53 * hash + Internal.hashLong(this.getResponseSize());
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getUserAgent().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getRemoteIp().hashCode();
        hash = 37 * hash + 13;
        hash = 53 * hash + this.getServerIp().hashCode();
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getReferer().hashCode();
        if (this.hasLatency()) {
            hash = 37 * hash + 14;
            hash = 53 * hash + this.getLatency().hashCode();
        }
        hash = 37 * hash + 11;
        hash = 53 * hash + Internal.hashBoolean(this.getCacheLookup());
        hash = 37 * hash + 9;
        hash = 53 * hash + Internal.hashBoolean(this.getCacheHit());
        hash = 37 * hash + 10;
        hash = 53 * hash + Internal.hashBoolean(this.getCacheValidatedWithOriginServer());
        hash = 37 * hash + 12;
        hash = 53 * hash + Internal.hashLong(this.getCacheFillBytes());
        hash = 37 * hash + 15;
        hash = 53 * hash + this.getProtocol().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static HttpRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRequest parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static HttpRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static HttpRequest parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static HttpRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static HttpRequest parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static HttpRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return HttpRequest.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(HttpRequest prototype) {
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

    public static HttpRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<HttpRequest> parser() {
        return PARSER;
    }

    public Parser<HttpRequest> getParserForType() {
        return PARSER;
    }

    @Override
    public HttpRequest getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements HttpRequestOrBuilder {
        private int bitField0_;
        private Object requestMethod_ = "";
        private Object requestUrl_ = "";
        private long requestSize_;
        private int status_;
        private long responseSize_;
        private Object userAgent_ = "";
        private Object remoteIp_ = "";
        private Object serverIp_ = "";
        private Object referer_ = "";
        private Duration latency_;
        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> latencyBuilder_;
        private boolean cacheLookup_;
        private boolean cacheHit_;
        private boolean cacheValidatedWithOriginServer_;
        private long cacheFillBytes_;
        private Object protocol_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return HttpRequestProto.internal_static_google_logging_type_HttpRequest_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return HttpRequestProto.internal_static_google_logging_type_HttpRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(HttpRequest.class, Builder.class);
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
            this.requestMethod_ = "";
            this.requestUrl_ = "";
            this.requestSize_ = 0L;
            this.status_ = 0;
            this.responseSize_ = 0L;
            this.userAgent_ = "";
            this.remoteIp_ = "";
            this.serverIp_ = "";
            this.referer_ = "";
            this.latency_ = null;
            if (this.latencyBuilder_ != null) {
                this.latencyBuilder_.dispose();
                this.latencyBuilder_ = null;
            }
            this.cacheLookup_ = false;
            this.cacheHit_ = false;
            this.cacheValidatedWithOriginServer_ = false;
            this.cacheFillBytes_ = 0L;
            this.protocol_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return HttpRequestProto.internal_static_google_logging_type_HttpRequest_descriptor;
        }

        @Override
        public HttpRequest getDefaultInstanceForType() {
            return HttpRequest.getDefaultInstance();
        }

        @Override
        public HttpRequest build() {
            HttpRequest result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public HttpRequest buildPartial() {
            HttpRequest result = new HttpRequest(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(HttpRequest result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.requestMethod_ = this.requestMethod_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.requestUrl_ = this.requestUrl_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.requestSize_ = this.requestSize_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.status_ = this.status_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.responseSize_ = this.responseSize_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.userAgent_ = this.userAgent_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.remoteIp_ = this.remoteIp_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.serverIp_ = this.serverIp_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.referer_ = this.referer_;
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.latency_ = this.latencyBuilder_ == null ? this.latency_ : this.latencyBuilder_.build();
            }
            if ((from_bitField0_ & 0x400) != 0) {
                result.cacheLookup_ = this.cacheLookup_;
            }
            if ((from_bitField0_ & 0x800) != 0) {
                result.cacheHit_ = this.cacheHit_;
            }
            if ((from_bitField0_ & 0x1000) != 0) {
                result.cacheValidatedWithOriginServer_ = this.cacheValidatedWithOriginServer_;
            }
            if ((from_bitField0_ & 0x2000) != 0) {
                result.cacheFillBytes_ = this.cacheFillBytes_;
            }
            if ((from_bitField0_ & 0x4000) != 0) {
                result.protocol_ = this.protocol_;
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
            if (other instanceof HttpRequest) {
                return this.mergeFrom((HttpRequest)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(HttpRequest other) {
            if (other == HttpRequest.getDefaultInstance()) {
                return this;
            }
            if (!other.getRequestMethod().isEmpty()) {
                this.requestMethod_ = other.requestMethod_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getRequestUrl().isEmpty()) {
                this.requestUrl_ = other.requestUrl_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (other.getRequestSize() != 0L) {
                this.setRequestSize(other.getRequestSize());
            }
            if (other.getStatus() != 0) {
                this.setStatus(other.getStatus());
            }
            if (other.getResponseSize() != 0L) {
                this.setResponseSize(other.getResponseSize());
            }
            if (!other.getUserAgent().isEmpty()) {
                this.userAgent_ = other.userAgent_;
                this.bitField0_ |= 0x20;
                this.onChanged();
            }
            if (!other.getRemoteIp().isEmpty()) {
                this.remoteIp_ = other.remoteIp_;
                this.bitField0_ |= 0x40;
                this.onChanged();
            }
            if (!other.getServerIp().isEmpty()) {
                this.serverIp_ = other.serverIp_;
                this.bitField0_ |= 0x80;
                this.onChanged();
            }
            if (!other.getReferer().isEmpty()) {
                this.referer_ = other.referer_;
                this.bitField0_ |= 0x100;
                this.onChanged();
            }
            if (other.hasLatency()) {
                this.mergeLatency(other.getLatency());
            }
            if (other.getCacheLookup()) {
                this.setCacheLookup(other.getCacheLookup());
            }
            if (other.getCacheHit()) {
                this.setCacheHit(other.getCacheHit());
            }
            if (other.getCacheValidatedWithOriginServer()) {
                this.setCacheValidatedWithOriginServer(other.getCacheValidatedWithOriginServer());
            }
            if (other.getCacheFillBytes() != 0L) {
                this.setCacheFillBytes(other.getCacheFillBytes());
            }
            if (!other.getProtocol().isEmpty()) {
                this.protocol_ = other.protocol_;
                this.bitField0_ |= 0x4000;
                this.onChanged();
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
                block23: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block23;
                        }
                        case 10: {
                            this.requestMethod_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block23;
                        }
                        case 18: {
                            this.requestUrl_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block23;
                        }
                        case 24: {
                            this.requestSize_ = input.readInt64();
                            this.bitField0_ |= 4;
                            continue block23;
                        }
                        case 32: {
                            this.status_ = input.readInt32();
                            this.bitField0_ |= 8;
                            continue block23;
                        }
                        case 40: {
                            this.responseSize_ = input.readInt64();
                            this.bitField0_ |= 0x10;
                            continue block23;
                        }
                        case 50: {
                            this.userAgent_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block23;
                        }
                        case 58: {
                            this.remoteIp_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x40;
                            continue block23;
                        }
                        case 66: {
                            this.referer_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x100;
                            continue block23;
                        }
                        case 72: {
                            this.cacheHit_ = input.readBool();
                            this.bitField0_ |= 0x800;
                            continue block23;
                        }
                        case 80: {
                            this.cacheValidatedWithOriginServer_ = input.readBool();
                            this.bitField0_ |= 0x1000;
                            continue block23;
                        }
                        case 88: {
                            this.cacheLookup_ = input.readBool();
                            this.bitField0_ |= 0x400;
                            continue block23;
                        }
                        case 96: {
                            this.cacheFillBytes_ = input.readInt64();
                            this.bitField0_ |= 0x2000;
                            continue block23;
                        }
                        case 106: {
                            this.serverIp_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x80;
                            continue block23;
                        }
                        case 114: {
                            input.readMessage(this.getLatencyFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x200;
                            continue block23;
                        }
                        case 122: {
                            this.protocol_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x4000;
                            continue block23;
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
        public String getRequestMethod() {
            Object ref = this.requestMethod_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.requestMethod_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRequestMethodBytes() {
            Object ref = this.requestMethod_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.requestMethod_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setRequestMethod(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.requestMethod_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRequestMethod() {
            this.requestMethod_ = HttpRequest.getDefaultInstance().getRequestMethod();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setRequestMethodBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.requestMethod_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getRequestUrl() {
            Object ref = this.requestUrl_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.requestUrl_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRequestUrlBytes() {
            Object ref = this.requestUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.requestUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setRequestUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.requestUrl_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearRequestUrl() {
            this.requestUrl_ = HttpRequest.getDefaultInstance().getRequestUrl();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setRequestUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.requestUrl_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public long getRequestSize() {
            return this.requestSize_;
        }

        public Builder setRequestSize(long value) {
            this.requestSize_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearRequestSize() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.requestSize_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public int getStatus() {
            return this.status_;
        }

        public Builder setStatus(int value) {
            this.status_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearStatus() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.status_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public long getResponseSize() {
            return this.responseSize_;
        }

        public Builder setResponseSize(long value) {
            this.responseSize_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearResponseSize() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.responseSize_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public String getUserAgent() {
            Object ref = this.userAgent_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.userAgent_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getUserAgentBytes() {
            Object ref = this.userAgent_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.userAgent_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setUserAgent(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.userAgent_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearUserAgent() {
            this.userAgent_ = HttpRequest.getDefaultInstance().getUserAgent();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setUserAgentBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.userAgent_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public String getRemoteIp() {
            Object ref = this.remoteIp_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.remoteIp_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRemoteIpBytes() {
            Object ref = this.remoteIp_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.remoteIp_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setRemoteIp(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.remoteIp_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearRemoteIp() {
            this.remoteIp_ = HttpRequest.getDefaultInstance().getRemoteIp();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        public Builder setRemoteIpBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.remoteIp_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        @Override
        public String getServerIp() {
            Object ref = this.serverIp_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.serverIp_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getServerIpBytes() {
            Object ref = this.serverIp_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.serverIp_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setServerIp(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.serverIp_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearServerIp() {
            this.serverIp_ = HttpRequest.getDefaultInstance().getServerIp();
            this.bitField0_ &= 0xFFFFFF7F;
            this.onChanged();
            return this;
        }

        public Builder setServerIpBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.serverIp_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        @Override
        public String getReferer() {
            Object ref = this.referer_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.referer_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRefererBytes() {
            Object ref = this.referer_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.referer_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setReferer(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.referer_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearReferer() {
            this.referer_ = HttpRequest.getDefaultInstance().getReferer();
            this.bitField0_ &= 0xFFFFFEFF;
            this.onChanged();
            return this;
        }

        public Builder setRefererBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.referer_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasLatency() {
            return (this.bitField0_ & 0x200) != 0;
        }

        @Override
        public Duration getLatency() {
            if (this.latencyBuilder_ == null) {
                return this.latency_ == null ? Duration.getDefaultInstance() : this.latency_;
            }
            return this.latencyBuilder_.getMessage();
        }

        public Builder setLatency(Duration value) {
            if (this.latencyBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.latency_ = value;
            } else {
                this.latencyBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder setLatency(Duration.Builder builderForValue) {
            if (this.latencyBuilder_ == null) {
                this.latency_ = builderForValue.build();
            } else {
                this.latencyBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder mergeLatency(Duration value) {
            if (this.latencyBuilder_ == null) {
                if ((this.bitField0_ & 0x200) != 0 && this.latency_ != null && this.latency_ != Duration.getDefaultInstance()) {
                    this.getLatencyBuilder().mergeFrom(value);
                } else {
                    this.latency_ = value;
                }
            } else {
                this.latencyBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearLatency() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.latency_ = null;
            if (this.latencyBuilder_ != null) {
                this.latencyBuilder_.dispose();
                this.latencyBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Duration.Builder getLatencyBuilder() {
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this.getLatencyFieldBuilder().getBuilder();
        }

        @Override
        public DurationOrBuilder getLatencyOrBuilder() {
            if (this.latencyBuilder_ != null) {
                return this.latencyBuilder_.getMessageOrBuilder();
            }
            return this.latency_ == null ? Duration.getDefaultInstance() : this.latency_;
        }

        private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getLatencyFieldBuilder() {
            if (this.latencyBuilder_ == null) {
                this.latencyBuilder_ = new SingleFieldBuilderV3(this.getLatency(), this.getParentForChildren(), this.isClean());
                this.latency_ = null;
            }
            return this.latencyBuilder_;
        }

        @Override
        public boolean getCacheLookup() {
            return this.cacheLookup_;
        }

        public Builder setCacheLookup(boolean value) {
            this.cacheLookup_ = value;
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearCacheLookup() {
            this.bitField0_ &= 0xFFFFFBFF;
            this.cacheLookup_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getCacheHit() {
            return this.cacheHit_;
        }

        public Builder setCacheHit(boolean value) {
            this.cacheHit_ = value;
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder clearCacheHit() {
            this.bitField0_ &= 0xFFFFF7FF;
            this.cacheHit_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getCacheValidatedWithOriginServer() {
            return this.cacheValidatedWithOriginServer_;
        }

        public Builder setCacheValidatedWithOriginServer(boolean value) {
            this.cacheValidatedWithOriginServer_ = value;
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder clearCacheValidatedWithOriginServer() {
            this.bitField0_ &= 0xFFFFEFFF;
            this.cacheValidatedWithOriginServer_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public long getCacheFillBytes() {
            return this.cacheFillBytes_;
        }

        public Builder setCacheFillBytes(long value) {
            this.cacheFillBytes_ = value;
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder clearCacheFillBytes() {
            this.bitField0_ &= 0xFFFFDFFF;
            this.cacheFillBytes_ = 0L;
            this.onChanged();
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
            this.bitField0_ |= 0x4000;
            this.onChanged();
            return this;
        }

        public Builder clearProtocol() {
            this.protocol_ = HttpRequest.getDefaultInstance().getProtocol();
            this.bitField0_ &= 0xFFFFBFFF;
            this.onChanged();
            return this;
        }

        public Builder setProtocolBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRequest.checkByteStringIsUtf8(value);
            this.protocol_ = value;
            this.bitField0_ |= 0x4000;
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
}

