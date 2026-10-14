/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc.context;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import com.google.rpc.context.AttributeContextOrBuilder;
import com.google.rpc.context.AttributeContextProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class AttributeContext
extends GeneratedMessageV3
implements AttributeContextOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int ORIGIN_FIELD_NUMBER = 7;
    private Peer origin_;
    public static final int SOURCE_FIELD_NUMBER = 1;
    private Peer source_;
    public static final int DESTINATION_FIELD_NUMBER = 2;
    private Peer destination_;
    public static final int REQUEST_FIELD_NUMBER = 3;
    private Request request_;
    public static final int RESPONSE_FIELD_NUMBER = 4;
    private Response response_;
    public static final int RESOURCE_FIELD_NUMBER = 5;
    private Resource resource_;
    public static final int API_FIELD_NUMBER = 6;
    private Api api_;
    public static final int EXTENSIONS_FIELD_NUMBER = 8;
    private List<Any> extensions_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final AttributeContext DEFAULT_INSTANCE = new AttributeContext();
    private static final Parser<AttributeContext> PARSER = new AbstractParser<AttributeContext>(){

        @Override
        public AttributeContext parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AttributeContext.newBuilder();
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

    private AttributeContext(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AttributeContext() {
        this.extensions_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AttributeContext();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_fieldAccessorTable.ensureFieldAccessorsInitialized(AttributeContext.class, Builder.class);
    }

    @Override
    public boolean hasOrigin() {
        return this.origin_ != null;
    }

    @Override
    public Peer getOrigin() {
        return this.origin_ == null ? Peer.getDefaultInstance() : this.origin_;
    }

    @Override
    public PeerOrBuilder getOriginOrBuilder() {
        return this.origin_ == null ? Peer.getDefaultInstance() : this.origin_;
    }

    @Override
    public boolean hasSource() {
        return this.source_ != null;
    }

    @Override
    public Peer getSource() {
        return this.source_ == null ? Peer.getDefaultInstance() : this.source_;
    }

    @Override
    public PeerOrBuilder getSourceOrBuilder() {
        return this.source_ == null ? Peer.getDefaultInstance() : this.source_;
    }

    @Override
    public boolean hasDestination() {
        return this.destination_ != null;
    }

    @Override
    public Peer getDestination() {
        return this.destination_ == null ? Peer.getDefaultInstance() : this.destination_;
    }

    @Override
    public PeerOrBuilder getDestinationOrBuilder() {
        return this.destination_ == null ? Peer.getDefaultInstance() : this.destination_;
    }

    @Override
    public boolean hasRequest() {
        return this.request_ != null;
    }

    @Override
    public Request getRequest() {
        return this.request_ == null ? Request.getDefaultInstance() : this.request_;
    }

    @Override
    public RequestOrBuilder getRequestOrBuilder() {
        return this.request_ == null ? Request.getDefaultInstance() : this.request_;
    }

    @Override
    public boolean hasResponse() {
        return this.response_ != null;
    }

    @Override
    public Response getResponse() {
        return this.response_ == null ? Response.getDefaultInstance() : this.response_;
    }

    @Override
    public ResponseOrBuilder getResponseOrBuilder() {
        return this.response_ == null ? Response.getDefaultInstance() : this.response_;
    }

    @Override
    public boolean hasResource() {
        return this.resource_ != null;
    }

    @Override
    public Resource getResource() {
        return this.resource_ == null ? Resource.getDefaultInstance() : this.resource_;
    }

    @Override
    public ResourceOrBuilder getResourceOrBuilder() {
        return this.resource_ == null ? Resource.getDefaultInstance() : this.resource_;
    }

    @Override
    public boolean hasApi() {
        return this.api_ != null;
    }

    @Override
    public Api getApi() {
        return this.api_ == null ? Api.getDefaultInstance() : this.api_;
    }

    @Override
    public ApiOrBuilder getApiOrBuilder() {
        return this.api_ == null ? Api.getDefaultInstance() : this.api_;
    }

    @Override
    public List<Any> getExtensionsList() {
        return this.extensions_;
    }

    @Override
    public List<? extends AnyOrBuilder> getExtensionsOrBuilderList() {
        return this.extensions_;
    }

    @Override
    public int getExtensionsCount() {
        return this.extensions_.size();
    }

    @Override
    public Any getExtensions(int index) {
        return this.extensions_.get(index);
    }

    @Override
    public AnyOrBuilder getExtensionsOrBuilder(int index) {
        return this.extensions_.get(index);
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
        if (this.source_ != null) {
            output.writeMessage(1, this.getSource());
        }
        if (this.destination_ != null) {
            output.writeMessage(2, this.getDestination());
        }
        if (this.request_ != null) {
            output.writeMessage(3, this.getRequest());
        }
        if (this.response_ != null) {
            output.writeMessage(4, this.getResponse());
        }
        if (this.resource_ != null) {
            output.writeMessage(5, this.getResource());
        }
        if (this.api_ != null) {
            output.writeMessage(6, this.getApi());
        }
        if (this.origin_ != null) {
            output.writeMessage(7, this.getOrigin());
        }
        for (int i = 0; i < this.extensions_.size(); ++i) {
            output.writeMessage(8, this.extensions_.get(i));
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
        if (this.source_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getSource());
        }
        if (this.destination_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getDestination());
        }
        if (this.request_ != null) {
            size += CodedOutputStream.computeMessageSize(3, this.getRequest());
        }
        if (this.response_ != null) {
            size += CodedOutputStream.computeMessageSize(4, this.getResponse());
        }
        if (this.resource_ != null) {
            size += CodedOutputStream.computeMessageSize(5, this.getResource());
        }
        if (this.api_ != null) {
            size += CodedOutputStream.computeMessageSize(6, this.getApi());
        }
        if (this.origin_ != null) {
            size += CodedOutputStream.computeMessageSize(7, this.getOrigin());
        }
        for (int i = 0; i < this.extensions_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(8, this.extensions_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeContext)) {
            return super.equals(obj);
        }
        AttributeContext other = (AttributeContext)obj;
        if (this.hasOrigin() != other.hasOrigin()) {
            return false;
        }
        if (this.hasOrigin() && !this.getOrigin().equals(other.getOrigin())) {
            return false;
        }
        if (this.hasSource() != other.hasSource()) {
            return false;
        }
        if (this.hasSource() && !this.getSource().equals(other.getSource())) {
            return false;
        }
        if (this.hasDestination() != other.hasDestination()) {
            return false;
        }
        if (this.hasDestination() && !this.getDestination().equals(other.getDestination())) {
            return false;
        }
        if (this.hasRequest() != other.hasRequest()) {
            return false;
        }
        if (this.hasRequest() && !this.getRequest().equals(other.getRequest())) {
            return false;
        }
        if (this.hasResponse() != other.hasResponse()) {
            return false;
        }
        if (this.hasResponse() && !this.getResponse().equals(other.getResponse())) {
            return false;
        }
        if (this.hasResource() != other.hasResource()) {
            return false;
        }
        if (this.hasResource() && !this.getResource().equals(other.getResource())) {
            return false;
        }
        if (this.hasApi() != other.hasApi()) {
            return false;
        }
        if (this.hasApi() && !this.getApi().equals(other.getApi())) {
            return false;
        }
        if (!this.getExtensionsList().equals(other.getExtensionsList())) {
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
        hash = 19 * hash + AttributeContext.getDescriptor().hashCode();
        if (this.hasOrigin()) {
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getOrigin().hashCode();
        }
        if (this.hasSource()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getSource().hashCode();
        }
        if (this.hasDestination()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getDestination().hashCode();
        }
        if (this.hasRequest()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getRequest().hashCode();
        }
        if (this.hasResponse()) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getResponse().hashCode();
        }
        if (this.hasResource()) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getResource().hashCode();
        }
        if (this.hasApi()) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getApi().hashCode();
        }
        if (this.getExtensionsCount() > 0) {
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getExtensionsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AttributeContext parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AttributeContext parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AttributeContext parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AttributeContext parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AttributeContext parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AttributeContext parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AttributeContext parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AttributeContext parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AttributeContext parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AttributeContext parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AttributeContext parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AttributeContext parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AttributeContext.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AttributeContext prototype) {
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

    public static AttributeContext getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AttributeContext> parser() {
        return PARSER;
    }

    public Parser<AttributeContext> getParserForType() {
        return PARSER;
    }

    @Override
    public AttributeContext getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Api
    extends GeneratedMessageV3
    implements ApiOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SERVICE_FIELD_NUMBER = 1;
        private volatile Object service_ = "";
        public static final int OPERATION_FIELD_NUMBER = 2;
        private volatile Object operation_ = "";
        public static final int PROTOCOL_FIELD_NUMBER = 3;
        private volatile Object protocol_ = "";
        public static final int VERSION_FIELD_NUMBER = 4;
        private volatile Object version_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final Api DEFAULT_INSTANCE = new Api();
        private static final Parser<Api> PARSER = new AbstractParser<Api>(){

            @Override
            public Api parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Api.newBuilder();
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

        private Api(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Api() {
            this.service_ = "";
            this.operation_ = "";
            this.protocol_ = "";
            this.version_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Api();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Api_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
        }

        @Override
        public String getService() {
            Object ref = this.service_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.service_ = s2;
            return s2;
        }

        @Override
        public ByteString getServiceBytes() {
            Object ref = this.service_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.service_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getOperation() {
            Object ref = this.operation_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.operation_ = s2;
            return s2;
        }

        @Override
        public ByteString getOperationBytes() {
            Object ref = this.operation_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.operation_ = b;
                return b;
            }
            return (ByteString)ref;
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
        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.version_ = s2;
            return s2;
        }

        @Override
        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.version_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                GeneratedMessageV3.writeString(output, 1, this.service_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.operation_)) {
                GeneratedMessageV3.writeString(output, 2, this.operation_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
                GeneratedMessageV3.writeString(output, 3, this.protocol_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
                GeneratedMessageV3.writeString(output, 4, this.version_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.service_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.operation_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.operation_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
                size += GeneratedMessageV3.computeStringSize(3, this.protocol_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
                size += GeneratedMessageV3.computeStringSize(4, this.version_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Api)) {
                return super.equals(obj);
            }
            Api other = (Api)obj;
            if (!this.getService().equals(other.getService())) {
                return false;
            }
            if (!this.getOperation().equals(other.getOperation())) {
                return false;
            }
            if (!this.getProtocol().equals(other.getProtocol())) {
                return false;
            }
            if (!this.getVersion().equals(other.getVersion())) {
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
            hash = 19 * hash + Api.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getService().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getOperation().hashCode();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getProtocol().hashCode();
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getVersion().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Api parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Api parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Api parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Api parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Api parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Api parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Api parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Api parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Api parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Api parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Api parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Api parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Api.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Api prototype) {
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

        public static Api getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Api> parser() {
            return PARSER;
        }

        public Parser<Api> getParserForType() {
            return PARSER;
        }

        @Override
        public Api getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApiOrBuilder {
            private int bitField0_;
            private Object service_ = "";
            private Object operation_ = "";
            private Object protocol_ = "";
            private Object version_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Api_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
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
                this.service_ = "";
                this.operation_ = "";
                this.protocol_ = "";
                this.version_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Api_descriptor;
            }

            @Override
            public Api getDefaultInstanceForType() {
                return Api.getDefaultInstance();
            }

            @Override
            public Api build() {
                Api result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Api buildPartial() {
                Api result = new Api(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Api result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.service_ = this.service_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.operation_ = this.operation_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.protocol_ = this.protocol_;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.version_ = this.version_;
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
                if (other instanceof Api) {
                    return this.mergeFrom((Api)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Api other) {
                if (other == Api.getDefaultInstance()) {
                    return this;
                }
                if (!other.getService().isEmpty()) {
                    this.service_ = other.service_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getOperation().isEmpty()) {
                    this.operation_ = other.operation_;
                    this.bitField0_ |= 2;
                    this.onChanged();
                }
                if (!other.getProtocol().isEmpty()) {
                    this.protocol_ = other.protocol_;
                    this.bitField0_ |= 4;
                    this.onChanged();
                }
                if (!other.getVersion().isEmpty()) {
                    this.version_ = other.version_;
                    this.bitField0_ |= 8;
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
                    block12: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block12;
                            }
                            case 10: {
                                this.service_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block12;
                            }
                            case 18: {
                                this.operation_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 2;
                                continue block12;
                            }
                            case 26: {
                                this.protocol_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 4;
                                continue block12;
                            }
                            case 34: {
                                this.version_ = input.readStringRequireUtf8();
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
            public String getService() {
                Object ref = this.service_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.service_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getServiceBytes() {
                Object ref = this.service_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.service_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setService(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.service_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearService() {
                this.service_ = Api.getDefaultInstance().getService();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setServiceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Api.checkByteStringIsUtf8(value);
                this.service_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public String getOperation() {
                Object ref = this.operation_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.operation_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getOperationBytes() {
                Object ref = this.operation_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.operation_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setOperation(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.operation_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearOperation() {
                this.operation_ = Api.getDefaultInstance().getOperation();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setOperationBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Api.checkByteStringIsUtf8(value);
                this.operation_ = value;
                this.bitField0_ |= 2;
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
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearProtocol() {
                this.protocol_ = Api.getDefaultInstance().getProtocol();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder setProtocolBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Api.checkByteStringIsUtf8(value);
                this.protocol_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            @Override
            public String getVersion() {
                Object ref = this.version_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.version_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getVersionBytes() {
                Object ref = this.version_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.version_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setVersion(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.version_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.version_ = Api.getDefaultInstance().getVersion();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder setVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Api.checkByteStringIsUtf8(value);
                this.version_ = value;
                this.bitField0_ |= 8;
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

    public static final class Resource
    extends GeneratedMessageV3
    implements ResourceOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SERVICE_FIELD_NUMBER = 1;
        private volatile Object service_ = "";
        public static final int NAME_FIELD_NUMBER = 2;
        private volatile Object name_ = "";
        public static final int TYPE_FIELD_NUMBER = 3;
        private volatile Object type_ = "";
        public static final int LABELS_FIELD_NUMBER = 4;
        private MapField<String, String> labels_;
        public static final int UID_FIELD_NUMBER = 5;
        private volatile Object uid_ = "";
        public static final int ANNOTATIONS_FIELD_NUMBER = 6;
        private MapField<String, String> annotations_;
        public static final int DISPLAY_NAME_FIELD_NUMBER = 7;
        private volatile Object displayName_ = "";
        public static final int CREATE_TIME_FIELD_NUMBER = 8;
        private Timestamp createTime_;
        public static final int UPDATE_TIME_FIELD_NUMBER = 9;
        private Timestamp updateTime_;
        public static final int DELETE_TIME_FIELD_NUMBER = 10;
        private Timestamp deleteTime_;
        public static final int ETAG_FIELD_NUMBER = 11;
        private volatile Object etag_ = "";
        public static final int LOCATION_FIELD_NUMBER = 12;
        private volatile Object location_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final Resource DEFAULT_INSTANCE = new Resource();
        private static final Parser<Resource> PARSER = new AbstractParser<Resource>(){

            @Override
            public Resource parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Resource.newBuilder();
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

        private Resource(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Resource() {
            this.service_ = "";
            this.name_ = "";
            this.type_ = "";
            this.uid_ = "";
            this.displayName_ = "";
            this.etag_ = "";
            this.location_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Resource();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 4: {
                    return this.internalGetLabels();
                }
                case 6: {
                    return this.internalGetAnnotations();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_fieldAccessorTable.ensureFieldAccessorsInitialized(Resource.class, Builder.class);
        }

        @Override
        public String getService() {
            Object ref = this.service_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.service_ = s2;
            return s2;
        }

        @Override
        public ByteString getServiceBytes() {
            Object ref = this.service_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.service_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getName() {
            Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.name_ = s2;
            return s2;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getType() {
            Object ref = this.type_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.type_ = s2;
            return s2;
        }

        @Override
        public ByteString getTypeBytes() {
            Object ref = this.type_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.type_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private MapField<String, String> internalGetLabels() {
            if (this.labels_ == null) {
                return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
            }
            return this.labels_;
        }

        @Override
        public int getLabelsCount() {
            return this.internalGetLabels().getMap().size();
        }

        @Override
        public boolean containsLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetLabels().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getLabels() {
            return this.getLabelsMap();
        }

        @Override
        public Map<String, String> getLabelsMap() {
            return this.internalGetLabels().getMap();
        }

        @Override
        public String getLabelsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getLabelsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public String getUid() {
            Object ref = this.uid_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.uid_ = s2;
            return s2;
        }

        @Override
        public ByteString getUidBytes() {
            Object ref = this.uid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.uid_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private MapField<String, String> internalGetAnnotations() {
            if (this.annotations_ == null) {
                return MapField.emptyMapField(AnnotationsDefaultEntryHolder.defaultEntry);
            }
            return this.annotations_;
        }

        @Override
        public int getAnnotationsCount() {
            return this.internalGetAnnotations().getMap().size();
        }

        @Override
        public boolean containsAnnotations(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetAnnotations().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getAnnotations() {
            return this.getAnnotationsMap();
        }

        @Override
        public Map<String, String> getAnnotationsMap() {
            return this.internalGetAnnotations().getMap();
        }

        @Override
        public String getAnnotationsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetAnnotations().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getAnnotationsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetAnnotations().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public String getDisplayName() {
            Object ref = this.displayName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.displayName_ = s2;
            return s2;
        }

        @Override
        public ByteString getDisplayNameBytes() {
            Object ref = this.displayName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.displayName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasCreateTime() {
            return this.createTime_ != null;
        }

        @Override
        public Timestamp getCreateTime() {
            return this.createTime_ == null ? Timestamp.getDefaultInstance() : this.createTime_;
        }

        @Override
        public TimestampOrBuilder getCreateTimeOrBuilder() {
            return this.createTime_ == null ? Timestamp.getDefaultInstance() : this.createTime_;
        }

        @Override
        public boolean hasUpdateTime() {
            return this.updateTime_ != null;
        }

        @Override
        public Timestamp getUpdateTime() {
            return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
        }

        @Override
        public TimestampOrBuilder getUpdateTimeOrBuilder() {
            return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
        }

        @Override
        public boolean hasDeleteTime() {
            return this.deleteTime_ != null;
        }

        @Override
        public Timestamp getDeleteTime() {
            return this.deleteTime_ == null ? Timestamp.getDefaultInstance() : this.deleteTime_;
        }

        @Override
        public TimestampOrBuilder getDeleteTimeOrBuilder() {
            return this.deleteTime_ == null ? Timestamp.getDefaultInstance() : this.deleteTime_;
        }

        @Override
        public String getEtag() {
            Object ref = this.etag_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.etag_ = s2;
            return s2;
        }

        @Override
        public ByteString getEtagBytes() {
            Object ref = this.etag_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.etag_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getLocation() {
            Object ref = this.location_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.location_ = s2;
            return s2;
        }

        @Override
        public ByteString getLocationBytes() {
            Object ref = this.location_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.location_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                GeneratedMessageV3.writeString(output, 1, this.service_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
                GeneratedMessageV3.writeString(output, 2, this.name_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
                GeneratedMessageV3.writeString(output, 3, this.type_);
            }
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetLabels(), LabelsDefaultEntryHolder.defaultEntry, 4);
            if (!GeneratedMessageV3.isStringEmpty(this.uid_)) {
                GeneratedMessageV3.writeString(output, 5, this.uid_);
            }
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetAnnotations(), AnnotationsDefaultEntryHolder.defaultEntry, 6);
            if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
                GeneratedMessageV3.writeString(output, 7, this.displayName_);
            }
            if (this.createTime_ != null) {
                output.writeMessage(8, this.getCreateTime());
            }
            if (this.updateTime_ != null) {
                output.writeMessage(9, this.getUpdateTime());
            }
            if (this.deleteTime_ != null) {
                output.writeMessage(10, this.getDeleteTime());
            }
            if (!GeneratedMessageV3.isStringEmpty(this.etag_)) {
                GeneratedMessageV3.writeString(output, 11, this.etag_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.location_)) {
                GeneratedMessageV3.writeString(output, 12, this.location_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.service_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.service_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.name_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
                size += GeneratedMessageV3.computeStringSize(3, this.type_);
            }
            for (Map.Entry<String, String> entry : this.internalGetLabels().getMap().entrySet()) {
                Message labels__ = ((MapEntry.Builder)LabelsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(4, labels__);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.uid_)) {
                size += GeneratedMessageV3.computeStringSize(5, this.uid_);
            }
            for (Map.Entry<String, String> entry : this.internalGetAnnotations().getMap().entrySet()) {
                Message annotations__ = ((MapEntry.Builder)AnnotationsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(6, annotations__);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
                size += GeneratedMessageV3.computeStringSize(7, this.displayName_);
            }
            if (this.createTime_ != null) {
                size += CodedOutputStream.computeMessageSize(8, this.getCreateTime());
            }
            if (this.updateTime_ != null) {
                size += CodedOutputStream.computeMessageSize(9, this.getUpdateTime());
            }
            if (this.deleteTime_ != null) {
                size += CodedOutputStream.computeMessageSize(10, this.getDeleteTime());
            }
            if (!GeneratedMessageV3.isStringEmpty(this.etag_)) {
                size += GeneratedMessageV3.computeStringSize(11, this.etag_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.location_)) {
                size += GeneratedMessageV3.computeStringSize(12, this.location_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Resource)) {
                return super.equals(obj);
            }
            Resource other = (Resource)obj;
            if (!this.getService().equals(other.getService())) {
                return false;
            }
            if (!this.getName().equals(other.getName())) {
                return false;
            }
            if (!this.getType().equals(other.getType())) {
                return false;
            }
            if (!this.internalGetLabels().equals(other.internalGetLabels())) {
                return false;
            }
            if (!this.getUid().equals(other.getUid())) {
                return false;
            }
            if (!this.internalGetAnnotations().equals(other.internalGetAnnotations())) {
                return false;
            }
            if (!this.getDisplayName().equals(other.getDisplayName())) {
                return false;
            }
            if (this.hasCreateTime() != other.hasCreateTime()) {
                return false;
            }
            if (this.hasCreateTime() && !this.getCreateTime().equals(other.getCreateTime())) {
                return false;
            }
            if (this.hasUpdateTime() != other.hasUpdateTime()) {
                return false;
            }
            if (this.hasUpdateTime() && !this.getUpdateTime().equals(other.getUpdateTime())) {
                return false;
            }
            if (this.hasDeleteTime() != other.hasDeleteTime()) {
                return false;
            }
            if (this.hasDeleteTime() && !this.getDeleteTime().equals(other.getDeleteTime())) {
                return false;
            }
            if (!this.getEtag().equals(other.getEtag())) {
                return false;
            }
            if (!this.getLocation().equals(other.getLocation())) {
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
            hash = 19 * hash + Resource.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getService().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getName().hashCode();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getType().hashCode();
            if (!this.internalGetLabels().getMap().isEmpty()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.internalGetLabels().hashCode();
            }
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getUid().hashCode();
            if (!this.internalGetAnnotations().getMap().isEmpty()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.internalGetAnnotations().hashCode();
            }
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getDisplayName().hashCode();
            if (this.hasCreateTime()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getCreateTime().hashCode();
            }
            if (this.hasUpdateTime()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getUpdateTime().hashCode();
            }
            if (this.hasDeleteTime()) {
                hash = 37 * hash + 10;
                hash = 53 * hash + this.getDeleteTime().hashCode();
            }
            hash = 37 * hash + 11;
            hash = 53 * hash + this.getEtag().hashCode();
            hash = 37 * hash + 12;
            hash = 53 * hash + this.getLocation().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Resource parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Resource parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Resource parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Resource parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Resource parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Resource parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Resource parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Resource parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Resource parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Resource parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Resource parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Resource parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Resource.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Resource prototype) {
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

        public static Resource getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Resource> parser() {
            return PARSER;
        }

        public Parser<Resource> getParserForType() {
            return PARSER;
        }

        @Override
        public Resource getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResourceOrBuilder {
            private int bitField0_;
            private Object service_ = "";
            private Object name_ = "";
            private Object type_ = "";
            private MapField<String, String> labels_;
            private Object uid_ = "";
            private MapField<String, String> annotations_;
            private Object displayName_ = "";
            private Timestamp createTime_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> createTimeBuilder_;
            private Timestamp updateTime_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> updateTimeBuilder_;
            private Timestamp deleteTime_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> deleteTimeBuilder_;
            private Object etag_ = "";
            private Object location_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_descriptor;
            }

            @Override
            protected MapField internalGetMapField(int number) {
                switch (number) {
                    case 4: {
                        return this.internalGetLabels();
                    }
                    case 6: {
                        return this.internalGetAnnotations();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected MapField internalGetMutableMapField(int number) {
                switch (number) {
                    case 4: {
                        return this.internalGetMutableLabels();
                    }
                    case 6: {
                        return this.internalGetMutableAnnotations();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_fieldAccessorTable.ensureFieldAccessorsInitialized(Resource.class, Builder.class);
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
                this.service_ = "";
                this.name_ = "";
                this.type_ = "";
                this.internalGetMutableLabels().clear();
                this.uid_ = "";
                this.internalGetMutableAnnotations().clear();
                this.displayName_ = "";
                this.createTime_ = null;
                if (this.createTimeBuilder_ != null) {
                    this.createTimeBuilder_.dispose();
                    this.createTimeBuilder_ = null;
                }
                this.updateTime_ = null;
                if (this.updateTimeBuilder_ != null) {
                    this.updateTimeBuilder_.dispose();
                    this.updateTimeBuilder_ = null;
                }
                this.deleteTime_ = null;
                if (this.deleteTimeBuilder_ != null) {
                    this.deleteTimeBuilder_.dispose();
                    this.deleteTimeBuilder_ = null;
                }
                this.etag_ = "";
                this.location_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_descriptor;
            }

            @Override
            public Resource getDefaultInstanceForType() {
                return Resource.getDefaultInstance();
            }

            @Override
            public Resource build() {
                Resource result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Resource buildPartial() {
                Resource result = new Resource(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Resource result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.service_ = this.service_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.name_ = this.name_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.type_ = this.type_;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.labels_ = this.internalGetLabels();
                    result.labels_.makeImmutable();
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    result.uid_ = this.uid_;
                }
                if ((from_bitField0_ & 0x20) != 0) {
                    result.annotations_ = this.internalGetAnnotations();
                    result.annotations_.makeImmutable();
                }
                if ((from_bitField0_ & 0x40) != 0) {
                    result.displayName_ = this.displayName_;
                }
                if ((from_bitField0_ & 0x80) != 0) {
                    result.createTime_ = this.createTimeBuilder_ == null ? this.createTime_ : this.createTimeBuilder_.build();
                }
                if ((from_bitField0_ & 0x100) != 0) {
                    result.updateTime_ = this.updateTimeBuilder_ == null ? this.updateTime_ : this.updateTimeBuilder_.build();
                }
                if ((from_bitField0_ & 0x200) != 0) {
                    result.deleteTime_ = this.deleteTimeBuilder_ == null ? this.deleteTime_ : this.deleteTimeBuilder_.build();
                }
                if ((from_bitField0_ & 0x400) != 0) {
                    result.etag_ = this.etag_;
                }
                if ((from_bitField0_ & 0x800) != 0) {
                    result.location_ = this.location_;
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
                if (other instanceof Resource) {
                    return this.mergeFrom((Resource)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Resource other) {
                if (other == Resource.getDefaultInstance()) {
                    return this;
                }
                if (!other.getService().isEmpty()) {
                    this.service_ = other.service_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    this.bitField0_ |= 2;
                    this.onChanged();
                }
                if (!other.getType().isEmpty()) {
                    this.type_ = other.type_;
                    this.bitField0_ |= 4;
                    this.onChanged();
                }
                this.internalGetMutableLabels().mergeFrom(other.internalGetLabels());
                this.bitField0_ |= 8;
                if (!other.getUid().isEmpty()) {
                    this.uid_ = other.uid_;
                    this.bitField0_ |= 0x10;
                    this.onChanged();
                }
                this.internalGetMutableAnnotations().mergeFrom(other.internalGetAnnotations());
                this.bitField0_ |= 0x20;
                if (!other.getDisplayName().isEmpty()) {
                    this.displayName_ = other.displayName_;
                    this.bitField0_ |= 0x40;
                    this.onChanged();
                }
                if (other.hasCreateTime()) {
                    this.mergeCreateTime(other.getCreateTime());
                }
                if (other.hasUpdateTime()) {
                    this.mergeUpdateTime(other.getUpdateTime());
                }
                if (other.hasDeleteTime()) {
                    this.mergeDeleteTime(other.getDeleteTime());
                }
                if (!other.getEtag().isEmpty()) {
                    this.etag_ = other.etag_;
                    this.bitField0_ |= 0x400;
                    this.onChanged();
                }
                if (!other.getLocation().isEmpty()) {
                    this.location_ = other.location_;
                    this.bitField0_ |= 0x800;
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
                    block20: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block20;
                            }
                            case 10: {
                                this.service_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block20;
                            }
                            case 18: {
                                this.name_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 2;
                                continue block20;
                            }
                            case 26: {
                                this.type_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 4;
                                continue block20;
                            }
                            case 34: {
                                MapEntry<String, String> labels__ = input.readMessage(LabelsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableLabels().getMutableMap().put(labels__.getKey(), labels__.getValue());
                                this.bitField0_ |= 8;
                                continue block20;
                            }
                            case 42: {
                                this.uid_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x10;
                                continue block20;
                            }
                            case 50: {
                                MapEntry<String, String> annotations__ = input.readMessage(AnnotationsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableAnnotations().getMutableMap().put(annotations__.getKey(), annotations__.getValue());
                                this.bitField0_ |= 0x20;
                                continue block20;
                            }
                            case 58: {
                                this.displayName_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x40;
                                continue block20;
                            }
                            case 66: {
                                input.readMessage(this.getCreateTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 0x80;
                                continue block20;
                            }
                            case 74: {
                                input.readMessage(this.getUpdateTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 0x100;
                                continue block20;
                            }
                            case 82: {
                                input.readMessage(this.getDeleteTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 0x200;
                                continue block20;
                            }
                            case 90: {
                                this.etag_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x400;
                                continue block20;
                            }
                            case 98: {
                                this.location_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x800;
                                continue block20;
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
            public String getService() {
                Object ref = this.service_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.service_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getServiceBytes() {
                Object ref = this.service_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.service_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setService(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.service_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearService() {
                this.service_ = Resource.getDefaultInstance().getService();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setServiceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.service_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public String getName() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.name_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.name_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.name_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearName() {
                this.name_ = Resource.getDefaultInstance().getName();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.name_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            @Override
            public String getType() {
                Object ref = this.type_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.type_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getTypeBytes() {
                Object ref = this.type_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.type_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setType(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.type_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearType() {
                this.type_ = Resource.getDefaultInstance().getType();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder setTypeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.type_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            private MapField<String, String> internalGetLabels() {
                if (this.labels_ == null) {
                    return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
                }
                return this.labels_;
            }

            private MapField<String, String> internalGetMutableLabels() {
                if (this.labels_ == null) {
                    this.labels_ = MapField.newMapField(LabelsDefaultEntryHolder.defaultEntry);
                }
                if (!this.labels_.isMutable()) {
                    this.labels_ = this.labels_.copy();
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this.labels_;
            }

            @Override
            public int getLabelsCount() {
                return this.internalGetLabels().getMap().size();
            }

            @Override
            public boolean containsLabels(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetLabels().getMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, String> getLabels() {
                return this.getLabelsMap();
            }

            @Override
            public Map<String, String> getLabelsMap() {
                return this.internalGetLabels().getMap();
            }

            @Override
            public String getLabelsOrDefault(String key, String defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetLabels().getMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            @Override
            public String getLabelsOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetLabels().getMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return map.get(key);
            }

            public Builder clearLabels() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.internalGetMutableLabels().getMutableMap().clear();
                return this;
            }

            public Builder removeLabels(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableLabels().getMutableMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getMutableLabels() {
                this.bitField0_ |= 8;
                return this.internalGetMutableLabels().getMutableMap();
            }

            public Builder putLabels(String key, String value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableLabels().getMutableMap().put(key, value);
                this.bitField0_ |= 8;
                return this;
            }

            public Builder putAllLabels(Map<String, String> values2) {
                this.internalGetMutableLabels().getMutableMap().putAll(values2);
                this.bitField0_ |= 8;
                return this;
            }

            @Override
            public String getUid() {
                Object ref = this.uid_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.uid_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getUidBytes() {
                Object ref = this.uid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.uid_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setUid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uid_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearUid() {
                this.uid_ = Resource.getDefaultInstance().getUid();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
                return this;
            }

            public Builder setUidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.uid_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            private MapField<String, String> internalGetAnnotations() {
                if (this.annotations_ == null) {
                    return MapField.emptyMapField(AnnotationsDefaultEntryHolder.defaultEntry);
                }
                return this.annotations_;
            }

            private MapField<String, String> internalGetMutableAnnotations() {
                if (this.annotations_ == null) {
                    this.annotations_ = MapField.newMapField(AnnotationsDefaultEntryHolder.defaultEntry);
                }
                if (!this.annotations_.isMutable()) {
                    this.annotations_ = this.annotations_.copy();
                }
                this.bitField0_ |= 0x20;
                this.onChanged();
                return this.annotations_;
            }

            @Override
            public int getAnnotationsCount() {
                return this.internalGetAnnotations().getMap().size();
            }

            @Override
            public boolean containsAnnotations(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetAnnotations().getMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, String> getAnnotations() {
                return this.getAnnotationsMap();
            }

            @Override
            public Map<String, String> getAnnotationsMap() {
                return this.internalGetAnnotations().getMap();
            }

            @Override
            public String getAnnotationsOrDefault(String key, String defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetAnnotations().getMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            @Override
            public String getAnnotationsOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetAnnotations().getMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return map.get(key);
            }

            public Builder clearAnnotations() {
                this.bitField0_ &= 0xFFFFFFDF;
                this.internalGetMutableAnnotations().getMutableMap().clear();
                return this;
            }

            public Builder removeAnnotations(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableAnnotations().getMutableMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getMutableAnnotations() {
                this.bitField0_ |= 0x20;
                return this.internalGetMutableAnnotations().getMutableMap();
            }

            public Builder putAnnotations(String key, String value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableAnnotations().getMutableMap().put(key, value);
                this.bitField0_ |= 0x20;
                return this;
            }

            public Builder putAllAnnotations(Map<String, String> values2) {
                this.internalGetMutableAnnotations().getMutableMap().putAll(values2);
                this.bitField0_ |= 0x20;
                return this;
            }

            @Override
            public String getDisplayName() {
                Object ref = this.displayName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.displayName_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getDisplayNameBytes() {
                Object ref = this.displayName_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.displayName_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setDisplayName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.displayName_ = value;
                this.bitField0_ |= 0x40;
                this.onChanged();
                return this;
            }

            public Builder clearDisplayName() {
                this.displayName_ = Resource.getDefaultInstance().getDisplayName();
                this.bitField0_ &= 0xFFFFFFBF;
                this.onChanged();
                return this;
            }

            public Builder setDisplayNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.displayName_ = value;
                this.bitField0_ |= 0x40;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasCreateTime() {
                return (this.bitField0_ & 0x80) != 0;
            }

            @Override
            public Timestamp getCreateTime() {
                if (this.createTimeBuilder_ == null) {
                    return this.createTime_ == null ? Timestamp.getDefaultInstance() : this.createTime_;
                }
                return this.createTimeBuilder_.getMessage();
            }

            public Builder setCreateTime(Timestamp value) {
                if (this.createTimeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.createTime_ = value;
                } else {
                    this.createTimeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder setCreateTime(Timestamp.Builder builderForValue) {
                if (this.createTimeBuilder_ == null) {
                    this.createTime_ = builderForValue.build();
                } else {
                    this.createTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder mergeCreateTime(Timestamp value) {
                if (this.createTimeBuilder_ == null) {
                    if ((this.bitField0_ & 0x80) != 0 && this.createTime_ != null && this.createTime_ != Timestamp.getDefaultInstance()) {
                        this.getCreateTimeBuilder().mergeFrom(value);
                    } else {
                        this.createTime_ = value;
                    }
                } else {
                    this.createTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder clearCreateTime() {
                this.bitField0_ &= 0xFFFFFF7F;
                this.createTime_ = null;
                if (this.createTimeBuilder_ != null) {
                    this.createTimeBuilder_.dispose();
                    this.createTimeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getCreateTimeBuilder() {
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this.getCreateTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getCreateTimeOrBuilder() {
                if (this.createTimeBuilder_ != null) {
                    return this.createTimeBuilder_.getMessageOrBuilder();
                }
                return this.createTime_ == null ? Timestamp.getDefaultInstance() : this.createTime_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getCreateTimeFieldBuilder() {
                if (this.createTimeBuilder_ == null) {
                    this.createTimeBuilder_ = new SingleFieldBuilderV3(this.getCreateTime(), this.getParentForChildren(), this.isClean());
                    this.createTime_ = null;
                }
                return this.createTimeBuilder_;
            }

            @Override
            public boolean hasUpdateTime() {
                return (this.bitField0_ & 0x100) != 0;
            }

            @Override
            public Timestamp getUpdateTime() {
                if (this.updateTimeBuilder_ == null) {
                    return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
                }
                return this.updateTimeBuilder_.getMessage();
            }

            public Builder setUpdateTime(Timestamp value) {
                if (this.updateTimeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.updateTime_ = value;
                } else {
                    this.updateTimeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x100;
                this.onChanged();
                return this;
            }

            public Builder setUpdateTime(Timestamp.Builder builderForValue) {
                if (this.updateTimeBuilder_ == null) {
                    this.updateTime_ = builderForValue.build();
                } else {
                    this.updateTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x100;
                this.onChanged();
                return this;
            }

            public Builder mergeUpdateTime(Timestamp value) {
                if (this.updateTimeBuilder_ == null) {
                    if ((this.bitField0_ & 0x100) != 0 && this.updateTime_ != null && this.updateTime_ != Timestamp.getDefaultInstance()) {
                        this.getUpdateTimeBuilder().mergeFrom(value);
                    } else {
                        this.updateTime_ = value;
                    }
                } else {
                    this.updateTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x100;
                this.onChanged();
                return this;
            }

            public Builder clearUpdateTime() {
                this.bitField0_ &= 0xFFFFFEFF;
                this.updateTime_ = null;
                if (this.updateTimeBuilder_ != null) {
                    this.updateTimeBuilder_.dispose();
                    this.updateTimeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getUpdateTimeBuilder() {
                this.bitField0_ |= 0x100;
                this.onChanged();
                return this.getUpdateTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getUpdateTimeOrBuilder() {
                if (this.updateTimeBuilder_ != null) {
                    return this.updateTimeBuilder_.getMessageOrBuilder();
                }
                return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getUpdateTimeFieldBuilder() {
                if (this.updateTimeBuilder_ == null) {
                    this.updateTimeBuilder_ = new SingleFieldBuilderV3(this.getUpdateTime(), this.getParentForChildren(), this.isClean());
                    this.updateTime_ = null;
                }
                return this.updateTimeBuilder_;
            }

            @Override
            public boolean hasDeleteTime() {
                return (this.bitField0_ & 0x200) != 0;
            }

            @Override
            public Timestamp getDeleteTime() {
                if (this.deleteTimeBuilder_ == null) {
                    return this.deleteTime_ == null ? Timestamp.getDefaultInstance() : this.deleteTime_;
                }
                return this.deleteTimeBuilder_.getMessage();
            }

            public Builder setDeleteTime(Timestamp value) {
                if (this.deleteTimeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.deleteTime_ = value;
                } else {
                    this.deleteTimeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this;
            }

            public Builder setDeleteTime(Timestamp.Builder builderForValue) {
                if (this.deleteTimeBuilder_ == null) {
                    this.deleteTime_ = builderForValue.build();
                } else {
                    this.deleteTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this;
            }

            public Builder mergeDeleteTime(Timestamp value) {
                if (this.deleteTimeBuilder_ == null) {
                    if ((this.bitField0_ & 0x200) != 0 && this.deleteTime_ != null && this.deleteTime_ != Timestamp.getDefaultInstance()) {
                        this.getDeleteTimeBuilder().mergeFrom(value);
                    } else {
                        this.deleteTime_ = value;
                    }
                } else {
                    this.deleteTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this;
            }

            public Builder clearDeleteTime() {
                this.bitField0_ &= 0xFFFFFDFF;
                this.deleteTime_ = null;
                if (this.deleteTimeBuilder_ != null) {
                    this.deleteTimeBuilder_.dispose();
                    this.deleteTimeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getDeleteTimeBuilder() {
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this.getDeleteTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getDeleteTimeOrBuilder() {
                if (this.deleteTimeBuilder_ != null) {
                    return this.deleteTimeBuilder_.getMessageOrBuilder();
                }
                return this.deleteTime_ == null ? Timestamp.getDefaultInstance() : this.deleteTime_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getDeleteTimeFieldBuilder() {
                if (this.deleteTimeBuilder_ == null) {
                    this.deleteTimeBuilder_ = new SingleFieldBuilderV3(this.getDeleteTime(), this.getParentForChildren(), this.isClean());
                    this.deleteTime_ = null;
                }
                return this.deleteTimeBuilder_;
            }

            @Override
            public String getEtag() {
                Object ref = this.etag_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.etag_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getEtagBytes() {
                Object ref = this.etag_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.etag_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setEtag(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.etag_ = value;
                this.bitField0_ |= 0x400;
                this.onChanged();
                return this;
            }

            public Builder clearEtag() {
                this.etag_ = Resource.getDefaultInstance().getEtag();
                this.bitField0_ &= 0xFFFFFBFF;
                this.onChanged();
                return this;
            }

            public Builder setEtagBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.etag_ = value;
                this.bitField0_ |= 0x400;
                this.onChanged();
                return this;
            }

            @Override
            public String getLocation() {
                Object ref = this.location_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.location_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getLocationBytes() {
                Object ref = this.location_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.location_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setLocation(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.location_ = value;
                this.bitField0_ |= 0x800;
                this.onChanged();
                return this;
            }

            public Builder clearLocation() {
                this.location_ = Resource.getDefaultInstance().getLocation();
                this.bitField0_ &= 0xFFFFF7FF;
                this.onChanged();
                return this;
            }

            public Builder setLocationBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Resource.checkByteStringIsUtf8(value);
                this.location_ = value;
                this.bitField0_ |= 0x800;
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

        private static final class LabelsDefaultEntryHolder {
            static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_LabelsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private LabelsDefaultEntryHolder() {
            }
        }

        private static final class AnnotationsDefaultEntryHolder {
            static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Resource_AnnotationsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private AnnotationsDefaultEntryHolder() {
            }
        }
    }

    public static final class Response
    extends GeneratedMessageV3
    implements ResponseOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int CODE_FIELD_NUMBER = 1;
        private long code_ = 0L;
        public static final int SIZE_FIELD_NUMBER = 2;
        private long size_ = 0L;
        public static final int HEADERS_FIELD_NUMBER = 3;
        private MapField<String, String> headers_;
        public static final int TIME_FIELD_NUMBER = 4;
        private Timestamp time_;
        public static final int BACKEND_LATENCY_FIELD_NUMBER = 5;
        private Duration backendLatency_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Response DEFAULT_INSTANCE = new Response();
        private static final Parser<Response> PARSER = new AbstractParser<Response>(){

            @Override
            public Response parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Response.newBuilder();
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

        private Response(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Response() {
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Response();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetHeaders();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_fieldAccessorTable.ensureFieldAccessorsInitialized(Response.class, Builder.class);
        }

        @Override
        public long getCode() {
            return this.code_;
        }

        @Override
        public long getSize() {
            return this.size_;
        }

        private MapField<String, String> internalGetHeaders() {
            if (this.headers_ == null) {
                return MapField.emptyMapField(HeadersDefaultEntryHolder.defaultEntry);
            }
            return this.headers_;
        }

        @Override
        public int getHeadersCount() {
            return this.internalGetHeaders().getMap().size();
        }

        @Override
        public boolean containsHeaders(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetHeaders().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getHeaders() {
            return this.getHeadersMap();
        }

        @Override
        public Map<String, String> getHeadersMap() {
            return this.internalGetHeaders().getMap();
        }

        @Override
        public String getHeadersOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetHeaders().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getHeadersOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetHeaders().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public boolean hasTime() {
            return this.time_ != null;
        }

        @Override
        public Timestamp getTime() {
            return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
        }

        @Override
        public TimestampOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
        }

        @Override
        public boolean hasBackendLatency() {
            return this.backendLatency_ != null;
        }

        @Override
        public Duration getBackendLatency() {
            return this.backendLatency_ == null ? Duration.getDefaultInstance() : this.backendLatency_;
        }

        @Override
        public DurationOrBuilder getBackendLatencyOrBuilder() {
            return this.backendLatency_ == null ? Duration.getDefaultInstance() : this.backendLatency_;
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
            if (this.code_ != 0L) {
                output.writeInt64(1, this.code_);
            }
            if (this.size_ != 0L) {
                output.writeInt64(2, this.size_);
            }
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetHeaders(), HeadersDefaultEntryHolder.defaultEntry, 3);
            if (this.time_ != null) {
                output.writeMessage(4, this.getTime());
            }
            if (this.backendLatency_ != null) {
                output.writeMessage(5, this.getBackendLatency());
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
            if (this.code_ != 0L) {
                size += CodedOutputStream.computeInt64Size(1, this.code_);
            }
            if (this.size_ != 0L) {
                size += CodedOutputStream.computeInt64Size(2, this.size_);
            }
            for (Map.Entry<String, String> entry : this.internalGetHeaders().getMap().entrySet()) {
                Message headers__ = ((MapEntry.Builder)HeadersDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(3, headers__);
            }
            if (this.time_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getTime());
            }
            if (this.backendLatency_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getBackendLatency());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Response)) {
                return super.equals(obj);
            }
            Response other = (Response)obj;
            if (this.getCode() != other.getCode()) {
                return false;
            }
            if (this.getSize() != other.getSize()) {
                return false;
            }
            if (!this.internalGetHeaders().equals(other.internalGetHeaders())) {
                return false;
            }
            if (this.hasTime() != other.hasTime()) {
                return false;
            }
            if (this.hasTime() && !this.getTime().equals(other.getTime())) {
                return false;
            }
            if (this.hasBackendLatency() != other.hasBackendLatency()) {
                return false;
            }
            if (this.hasBackendLatency() && !this.getBackendLatency().equals(other.getBackendLatency())) {
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
            hash = 19 * hash + Response.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getCode());
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getSize());
            if (!this.internalGetHeaders().getMap().isEmpty()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.internalGetHeaders().hashCode();
            }
            if (this.hasTime()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getTime().hashCode();
            }
            if (this.hasBackendLatency()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getBackendLatency().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Response parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Response parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Response parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Response parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Response parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Response parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Response parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Response parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Response parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Response parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Response parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Response parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Response.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Response prototype) {
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

        public static Response getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Response> parser() {
            return PARSER;
        }

        public Parser<Response> getParserForType() {
            return PARSER;
        }

        @Override
        public Response getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResponseOrBuilder {
            private int bitField0_;
            private long code_;
            private long size_;
            private MapField<String, String> headers_;
            private Timestamp time_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> timeBuilder_;
            private Duration backendLatency_;
            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> backendLatencyBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_descriptor;
            }

            @Override
            protected MapField internalGetMapField(int number) {
                switch (number) {
                    case 3: {
                        return this.internalGetHeaders();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected MapField internalGetMutableMapField(int number) {
                switch (number) {
                    case 3: {
                        return this.internalGetMutableHeaders();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_fieldAccessorTable.ensureFieldAccessorsInitialized(Response.class, Builder.class);
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
                this.code_ = 0L;
                this.size_ = 0L;
                this.internalGetMutableHeaders().clear();
                this.time_ = null;
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.dispose();
                    this.timeBuilder_ = null;
                }
                this.backendLatency_ = null;
                if (this.backendLatencyBuilder_ != null) {
                    this.backendLatencyBuilder_.dispose();
                    this.backendLatencyBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_descriptor;
            }

            @Override
            public Response getDefaultInstanceForType() {
                return Response.getDefaultInstance();
            }

            @Override
            public Response build() {
                Response result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Response buildPartial() {
                Response result = new Response(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Response result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.code_ = this.code_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.size_ = this.size_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.headers_ = this.internalGetHeaders();
                    result.headers_.makeImmutable();
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.time_ = this.timeBuilder_ == null ? this.time_ : this.timeBuilder_.build();
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    result.backendLatency_ = this.backendLatencyBuilder_ == null ? this.backendLatency_ : this.backendLatencyBuilder_.build();
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
                if (other instanceof Response) {
                    return this.mergeFrom((Response)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Response other) {
                if (other == Response.getDefaultInstance()) {
                    return this;
                }
                if (other.getCode() != 0L) {
                    this.setCode(other.getCode());
                }
                if (other.getSize() != 0L) {
                    this.setSize(other.getSize());
                }
                this.internalGetMutableHeaders().mergeFrom(other.internalGetHeaders());
                this.bitField0_ |= 4;
                if (other.hasTime()) {
                    this.mergeTime(other.getTime());
                }
                if (other.hasBackendLatency()) {
                    this.mergeBackendLatency(other.getBackendLatency());
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
                            case 8: {
                                this.code_ = input.readInt64();
                                this.bitField0_ |= 1;
                                continue block13;
                            }
                            case 16: {
                                this.size_ = input.readInt64();
                                this.bitField0_ |= 2;
                                continue block13;
                            }
                            case 26: {
                                MapEntry<String, String> headers__ = input.readMessage(HeadersDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableHeaders().getMutableMap().put(headers__.getKey(), headers__.getValue());
                                this.bitField0_ |= 4;
                                continue block13;
                            }
                            case 34: {
                                input.readMessage(this.getTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 8;
                                continue block13;
                            }
                            case 42: {
                                input.readMessage(this.getBackendLatencyFieldBuilder().getBuilder(), extensionRegistry);
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
            public long getCode() {
                return this.code_;
            }

            public Builder setCode(long value) {
                this.code_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearCode() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.code_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getSize() {
                return this.size_;
            }

            public Builder setSize(long value) {
                this.size_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearSize() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.size_ = 0L;
                this.onChanged();
                return this;
            }

            private MapField<String, String> internalGetHeaders() {
                if (this.headers_ == null) {
                    return MapField.emptyMapField(HeadersDefaultEntryHolder.defaultEntry);
                }
                return this.headers_;
            }

            private MapField<String, String> internalGetMutableHeaders() {
                if (this.headers_ == null) {
                    this.headers_ = MapField.newMapField(HeadersDefaultEntryHolder.defaultEntry);
                }
                if (!this.headers_.isMutable()) {
                    this.headers_ = this.headers_.copy();
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this.headers_;
            }

            @Override
            public int getHeadersCount() {
                return this.internalGetHeaders().getMap().size();
            }

            @Override
            public boolean containsHeaders(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetHeaders().getMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, String> getHeaders() {
                return this.getHeadersMap();
            }

            @Override
            public Map<String, String> getHeadersMap() {
                return this.internalGetHeaders().getMap();
            }

            @Override
            public String getHeadersOrDefault(String key, String defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetHeaders().getMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            @Override
            public String getHeadersOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetHeaders().getMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return map.get(key);
            }

            public Builder clearHeaders() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.internalGetMutableHeaders().getMutableMap().clear();
                return this;
            }

            public Builder removeHeaders(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableHeaders().getMutableMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getMutableHeaders() {
                this.bitField0_ |= 4;
                return this.internalGetMutableHeaders().getMutableMap();
            }

            public Builder putHeaders(String key, String value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableHeaders().getMutableMap().put(key, value);
                this.bitField0_ |= 4;
                return this;
            }

            public Builder putAllHeaders(Map<String, String> values2) {
                this.internalGetMutableHeaders().getMutableMap().putAll(values2);
                this.bitField0_ |= 4;
                return this;
            }

            @Override
            public boolean hasTime() {
                return (this.bitField0_ & 8) != 0;
            }

            @Override
            public Timestamp getTime() {
                if (this.timeBuilder_ == null) {
                    return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
                }
                return this.timeBuilder_.getMessage();
            }

            public Builder setTime(Timestamp value) {
                if (this.timeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.time_ = value;
                } else {
                    this.timeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder setTime(Timestamp.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder mergeTime(Timestamp value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 0 && this.time_ != null && this.time_ != Timestamp.getDefaultInstance()) {
                        this.getTimeBuilder().mergeFrom(value);
                    } else {
                        this.time_ = value;
                    }
                } else {
                    this.timeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.time_ = null;
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.dispose();
                    this.timeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getTimeBuilder() {
                this.bitField0_ |= 8;
                this.onChanged();
                return this.getTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3(this.getTime(), this.getParentForChildren(), this.isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            @Override
            public boolean hasBackendLatency() {
                return (this.bitField0_ & 0x10) != 0;
            }

            @Override
            public Duration getBackendLatency() {
                if (this.backendLatencyBuilder_ == null) {
                    return this.backendLatency_ == null ? Duration.getDefaultInstance() : this.backendLatency_;
                }
                return this.backendLatencyBuilder_.getMessage();
            }

            public Builder setBackendLatency(Duration value) {
                if (this.backendLatencyBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.backendLatency_ = value;
                } else {
                    this.backendLatencyBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder setBackendLatency(Duration.Builder builderForValue) {
                if (this.backendLatencyBuilder_ == null) {
                    this.backendLatency_ = builderForValue.build();
                } else {
                    this.backendLatencyBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder mergeBackendLatency(Duration value) {
                if (this.backendLatencyBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) != 0 && this.backendLatency_ != null && this.backendLatency_ != Duration.getDefaultInstance()) {
                        this.getBackendLatencyBuilder().mergeFrom(value);
                    } else {
                        this.backendLatency_ = value;
                    }
                } else {
                    this.backendLatencyBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearBackendLatency() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.backendLatency_ = null;
                if (this.backendLatencyBuilder_ != null) {
                    this.backendLatencyBuilder_.dispose();
                    this.backendLatencyBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Duration.Builder getBackendLatencyBuilder() {
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this.getBackendLatencyFieldBuilder().getBuilder();
            }

            @Override
            public DurationOrBuilder getBackendLatencyOrBuilder() {
                if (this.backendLatencyBuilder_ != null) {
                    return this.backendLatencyBuilder_.getMessageOrBuilder();
                }
                return this.backendLatency_ == null ? Duration.getDefaultInstance() : this.backendLatency_;
            }

            private SingleFieldBuilderV3<Duration, Duration.Builder, DurationOrBuilder> getBackendLatencyFieldBuilder() {
                if (this.backendLatencyBuilder_ == null) {
                    this.backendLatencyBuilder_ = new SingleFieldBuilderV3(this.getBackendLatency(), this.getParentForChildren(), this.isClean());
                    this.backendLatency_ = null;
                }
                return this.backendLatencyBuilder_;
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

        private static final class HeadersDefaultEntryHolder {
            static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Response_HeadersEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private HeadersDefaultEntryHolder() {
            }
        }
    }

    public static final class Request
    extends GeneratedMessageV3
    implements RequestOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ID_FIELD_NUMBER = 1;
        private volatile Object id_ = "";
        public static final int METHOD_FIELD_NUMBER = 2;
        private volatile Object method_ = "";
        public static final int HEADERS_FIELD_NUMBER = 3;
        private MapField<String, String> headers_;
        public static final int PATH_FIELD_NUMBER = 4;
        private volatile Object path_ = "";
        public static final int HOST_FIELD_NUMBER = 5;
        private volatile Object host_ = "";
        public static final int SCHEME_FIELD_NUMBER = 6;
        private volatile Object scheme_ = "";
        public static final int QUERY_FIELD_NUMBER = 7;
        private volatile Object query_ = "";
        public static final int TIME_FIELD_NUMBER = 9;
        private Timestamp time_;
        public static final int SIZE_FIELD_NUMBER = 10;
        private long size_ = 0L;
        public static final int PROTOCOL_FIELD_NUMBER = 11;
        private volatile Object protocol_ = "";
        public static final int REASON_FIELD_NUMBER = 12;
        private volatile Object reason_ = "";
        public static final int AUTH_FIELD_NUMBER = 13;
        private Auth auth_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Request DEFAULT_INSTANCE = new Request();
        private static final Parser<Request> PARSER = new AbstractParser<Request>(){

            @Override
            public Request parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Request.newBuilder();
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

        private Request(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Request() {
            this.id_ = "";
            this.method_ = "";
            this.path_ = "";
            this.host_ = "";
            this.scheme_ = "";
            this.query_ = "";
            this.protocol_ = "";
            this.reason_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Request();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetHeaders();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_fieldAccessorTable.ensureFieldAccessorsInitialized(Request.class, Builder.class);
        }

        @Override
        public String getId() {
            Object ref = this.id_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.id_ = s2;
            return s2;
        }

        @Override
        public ByteString getIdBytes() {
            Object ref = this.id_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.id_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getMethod() {
            Object ref = this.method_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.method_ = s2;
            return s2;
        }

        @Override
        public ByteString getMethodBytes() {
            Object ref = this.method_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.method_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private MapField<String, String> internalGetHeaders() {
            if (this.headers_ == null) {
                return MapField.emptyMapField(HeadersDefaultEntryHolder.defaultEntry);
            }
            return this.headers_;
        }

        @Override
        public int getHeadersCount() {
            return this.internalGetHeaders().getMap().size();
        }

        @Override
        public boolean containsHeaders(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetHeaders().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getHeaders() {
            return this.getHeadersMap();
        }

        @Override
        public Map<String, String> getHeadersMap() {
            return this.internalGetHeaders().getMap();
        }

        @Override
        public String getHeadersOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetHeaders().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getHeadersOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetHeaders().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public String getPath() {
            Object ref = this.path_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.path_ = s2;
            return s2;
        }

        @Override
        public ByteString getPathBytes() {
            Object ref = this.path_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.path_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getHost() {
            Object ref = this.host_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.host_ = s2;
            return s2;
        }

        @Override
        public ByteString getHostBytes() {
            Object ref = this.host_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.host_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getScheme() {
            Object ref = this.scheme_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.scheme_ = s2;
            return s2;
        }

        @Override
        public ByteString getSchemeBytes() {
            Object ref = this.scheme_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.scheme_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getQuery() {
            Object ref = this.query_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.query_ = s2;
            return s2;
        }

        @Override
        public ByteString getQueryBytes() {
            Object ref = this.query_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.query_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasTime() {
            return this.time_ != null;
        }

        @Override
        public Timestamp getTime() {
            return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
        }

        @Override
        public TimestampOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
        }

        @Override
        public long getSize() {
            return this.size_;
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
        public String getReason() {
            Object ref = this.reason_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.reason_ = s2;
            return s2;
        }

        @Override
        public ByteString getReasonBytes() {
            Object ref = this.reason_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.reason_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasAuth() {
            return this.auth_ != null;
        }

        @Override
        public Auth getAuth() {
            return this.auth_ == null ? Auth.getDefaultInstance() : this.auth_;
        }

        @Override
        public AuthOrBuilder getAuthOrBuilder() {
            return this.auth_ == null ? Auth.getDefaultInstance() : this.auth_;
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
            if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
                GeneratedMessageV3.writeString(output, 1, this.id_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.method_)) {
                GeneratedMessageV3.writeString(output, 2, this.method_);
            }
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetHeaders(), HeadersDefaultEntryHolder.defaultEntry, 3);
            if (!GeneratedMessageV3.isStringEmpty(this.path_)) {
                GeneratedMessageV3.writeString(output, 4, this.path_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.host_)) {
                GeneratedMessageV3.writeString(output, 5, this.host_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.scheme_)) {
                GeneratedMessageV3.writeString(output, 6, this.scheme_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.query_)) {
                GeneratedMessageV3.writeString(output, 7, this.query_);
            }
            if (this.time_ != null) {
                output.writeMessage(9, this.getTime());
            }
            if (this.size_ != 0L) {
                output.writeInt64(10, this.size_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
                GeneratedMessageV3.writeString(output, 11, this.protocol_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.reason_)) {
                GeneratedMessageV3.writeString(output, 12, this.reason_);
            }
            if (this.auth_ != null) {
                output.writeMessage(13, this.getAuth());
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
            if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.id_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.method_)) {
                size += GeneratedMessageV3.computeStringSize(2, this.method_);
            }
            for (Map.Entry<String, String> entry : this.internalGetHeaders().getMap().entrySet()) {
                Message headers__ = ((MapEntry.Builder)HeadersDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(3, headers__);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.path_)) {
                size += GeneratedMessageV3.computeStringSize(4, this.path_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.host_)) {
                size += GeneratedMessageV3.computeStringSize(5, this.host_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.scheme_)) {
                size += GeneratedMessageV3.computeStringSize(6, this.scheme_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.query_)) {
                size += GeneratedMessageV3.computeStringSize(7, this.query_);
            }
            if (this.time_ != null) {
                size += CodedOutputStream.computeMessageSize(9, this.getTime());
            }
            if (this.size_ != 0L) {
                size += CodedOutputStream.computeInt64Size(10, this.size_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.protocol_)) {
                size += GeneratedMessageV3.computeStringSize(11, this.protocol_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.reason_)) {
                size += GeneratedMessageV3.computeStringSize(12, this.reason_);
            }
            if (this.auth_ != null) {
                size += CodedOutputStream.computeMessageSize(13, this.getAuth());
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Request)) {
                return super.equals(obj);
            }
            Request other = (Request)obj;
            if (!this.getId().equals(other.getId())) {
                return false;
            }
            if (!this.getMethod().equals(other.getMethod())) {
                return false;
            }
            if (!this.internalGetHeaders().equals(other.internalGetHeaders())) {
                return false;
            }
            if (!this.getPath().equals(other.getPath())) {
                return false;
            }
            if (!this.getHost().equals(other.getHost())) {
                return false;
            }
            if (!this.getScheme().equals(other.getScheme())) {
                return false;
            }
            if (!this.getQuery().equals(other.getQuery())) {
                return false;
            }
            if (this.hasTime() != other.hasTime()) {
                return false;
            }
            if (this.hasTime() && !this.getTime().equals(other.getTime())) {
                return false;
            }
            if (this.getSize() != other.getSize()) {
                return false;
            }
            if (!this.getProtocol().equals(other.getProtocol())) {
                return false;
            }
            if (!this.getReason().equals(other.getReason())) {
                return false;
            }
            if (this.hasAuth() != other.hasAuth()) {
                return false;
            }
            if (this.hasAuth() && !this.getAuth().equals(other.getAuth())) {
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
            hash = 19 * hash + Request.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getId().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMethod().hashCode();
            if (!this.internalGetHeaders().getMap().isEmpty()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.internalGetHeaders().hashCode();
            }
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getPath().hashCode();
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getHost().hashCode();
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getScheme().hashCode();
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getQuery().hashCode();
            if (this.hasTime()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getTime().hashCode();
            }
            hash = 37 * hash + 10;
            hash = 53 * hash + Internal.hashLong(this.getSize());
            hash = 37 * hash + 11;
            hash = 53 * hash + this.getProtocol().hashCode();
            hash = 37 * hash + 12;
            hash = 53 * hash + this.getReason().hashCode();
            if (this.hasAuth()) {
                hash = 37 * hash + 13;
                hash = 53 * hash + this.getAuth().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Request parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Request parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Request parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Request parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Request parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Request parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Request parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Request parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Request parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Request parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Request parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Request parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Request.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Request prototype) {
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

        public static Request getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Request> parser() {
            return PARSER;
        }

        public Parser<Request> getParserForType() {
            return PARSER;
        }

        @Override
        public Request getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements RequestOrBuilder {
            private int bitField0_;
            private Object id_ = "";
            private Object method_ = "";
            private MapField<String, String> headers_;
            private Object path_ = "";
            private Object host_ = "";
            private Object scheme_ = "";
            private Object query_ = "";
            private Timestamp time_;
            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> timeBuilder_;
            private long size_;
            private Object protocol_ = "";
            private Object reason_ = "";
            private Auth auth_;
            private SingleFieldBuilderV3<Auth, Auth.Builder, AuthOrBuilder> authBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_descriptor;
            }

            @Override
            protected MapField internalGetMapField(int number) {
                switch (number) {
                    case 3: {
                        return this.internalGetHeaders();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected MapField internalGetMutableMapField(int number) {
                switch (number) {
                    case 3: {
                        return this.internalGetMutableHeaders();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_fieldAccessorTable.ensureFieldAccessorsInitialized(Request.class, Builder.class);
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
                this.id_ = "";
                this.method_ = "";
                this.internalGetMutableHeaders().clear();
                this.path_ = "";
                this.host_ = "";
                this.scheme_ = "";
                this.query_ = "";
                this.time_ = null;
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.dispose();
                    this.timeBuilder_ = null;
                }
                this.size_ = 0L;
                this.protocol_ = "";
                this.reason_ = "";
                this.auth_ = null;
                if (this.authBuilder_ != null) {
                    this.authBuilder_.dispose();
                    this.authBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_descriptor;
            }

            @Override
            public Request getDefaultInstanceForType() {
                return Request.getDefaultInstance();
            }

            @Override
            public Request build() {
                Request result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Request buildPartial() {
                Request result = new Request(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Request result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.id_ = this.id_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.method_ = this.method_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.headers_ = this.internalGetHeaders();
                    result.headers_.makeImmutable();
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.path_ = this.path_;
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    result.host_ = this.host_;
                }
                if ((from_bitField0_ & 0x20) != 0) {
                    result.scheme_ = this.scheme_;
                }
                if ((from_bitField0_ & 0x40) != 0) {
                    result.query_ = this.query_;
                }
                if ((from_bitField0_ & 0x80) != 0) {
                    result.time_ = this.timeBuilder_ == null ? this.time_ : this.timeBuilder_.build();
                }
                if ((from_bitField0_ & 0x100) != 0) {
                    result.size_ = this.size_;
                }
                if ((from_bitField0_ & 0x200) != 0) {
                    result.protocol_ = this.protocol_;
                }
                if ((from_bitField0_ & 0x400) != 0) {
                    result.reason_ = this.reason_;
                }
                if ((from_bitField0_ & 0x800) != 0) {
                    result.auth_ = this.authBuilder_ == null ? this.auth_ : this.authBuilder_.build();
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
                if (other instanceof Request) {
                    return this.mergeFrom((Request)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Request other) {
                if (other == Request.getDefaultInstance()) {
                    return this;
                }
                if (!other.getId().isEmpty()) {
                    this.id_ = other.id_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.getMethod().isEmpty()) {
                    this.method_ = other.method_;
                    this.bitField0_ |= 2;
                    this.onChanged();
                }
                this.internalGetMutableHeaders().mergeFrom(other.internalGetHeaders());
                this.bitField0_ |= 4;
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
                    this.bitField0_ |= 8;
                    this.onChanged();
                }
                if (!other.getHost().isEmpty()) {
                    this.host_ = other.host_;
                    this.bitField0_ |= 0x10;
                    this.onChanged();
                }
                if (!other.getScheme().isEmpty()) {
                    this.scheme_ = other.scheme_;
                    this.bitField0_ |= 0x20;
                    this.onChanged();
                }
                if (!other.getQuery().isEmpty()) {
                    this.query_ = other.query_;
                    this.bitField0_ |= 0x40;
                    this.onChanged();
                }
                if (other.hasTime()) {
                    this.mergeTime(other.getTime());
                }
                if (other.getSize() != 0L) {
                    this.setSize(other.getSize());
                }
                if (!other.getProtocol().isEmpty()) {
                    this.protocol_ = other.protocol_;
                    this.bitField0_ |= 0x200;
                    this.onChanged();
                }
                if (!other.getReason().isEmpty()) {
                    this.reason_ = other.reason_;
                    this.bitField0_ |= 0x400;
                    this.onChanged();
                }
                if (other.hasAuth()) {
                    this.mergeAuth(other.getAuth());
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
                    block20: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block20;
                            }
                            case 10: {
                                this.id_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block20;
                            }
                            case 18: {
                                this.method_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 2;
                                continue block20;
                            }
                            case 26: {
                                MapEntry<String, String> headers__ = input.readMessage(HeadersDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableHeaders().getMutableMap().put(headers__.getKey(), headers__.getValue());
                                this.bitField0_ |= 4;
                                continue block20;
                            }
                            case 34: {
                                this.path_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 8;
                                continue block20;
                            }
                            case 42: {
                                this.host_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x10;
                                continue block20;
                            }
                            case 50: {
                                this.scheme_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x20;
                                continue block20;
                            }
                            case 58: {
                                this.query_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x40;
                                continue block20;
                            }
                            case 74: {
                                input.readMessage(this.getTimeFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 0x80;
                                continue block20;
                            }
                            case 80: {
                                this.size_ = input.readInt64();
                                this.bitField0_ |= 0x100;
                                continue block20;
                            }
                            case 90: {
                                this.protocol_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x200;
                                continue block20;
                            }
                            case 98: {
                                this.reason_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 0x400;
                                continue block20;
                            }
                            case 106: {
                                input.readMessage(this.getAuthFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 0x800;
                                continue block20;
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
            public String getId() {
                Object ref = this.id_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.id_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getIdBytes() {
                Object ref = this.id_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.id_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setId(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.id_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearId() {
                this.id_ = Request.getDefaultInstance().getId();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.id_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public String getMethod() {
                Object ref = this.method_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.method_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMethodBytes() {
                Object ref = this.method_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.method_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setMethod(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.method_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearMethod() {
                this.method_ = Request.getDefaultInstance().getMethod();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder setMethodBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.method_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            private MapField<String, String> internalGetHeaders() {
                if (this.headers_ == null) {
                    return MapField.emptyMapField(HeadersDefaultEntryHolder.defaultEntry);
                }
                return this.headers_;
            }

            private MapField<String, String> internalGetMutableHeaders() {
                if (this.headers_ == null) {
                    this.headers_ = MapField.newMapField(HeadersDefaultEntryHolder.defaultEntry);
                }
                if (!this.headers_.isMutable()) {
                    this.headers_ = this.headers_.copy();
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this.headers_;
            }

            @Override
            public int getHeadersCount() {
                return this.internalGetHeaders().getMap().size();
            }

            @Override
            public boolean containsHeaders(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetHeaders().getMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, String> getHeaders() {
                return this.getHeadersMap();
            }

            @Override
            public Map<String, String> getHeadersMap() {
                return this.internalGetHeaders().getMap();
            }

            @Override
            public String getHeadersOrDefault(String key, String defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetHeaders().getMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            @Override
            public String getHeadersOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetHeaders().getMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return map.get(key);
            }

            public Builder clearHeaders() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.internalGetMutableHeaders().getMutableMap().clear();
                return this;
            }

            public Builder removeHeaders(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableHeaders().getMutableMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getMutableHeaders() {
                this.bitField0_ |= 4;
                return this.internalGetMutableHeaders().getMutableMap();
            }

            public Builder putHeaders(String key, String value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableHeaders().getMutableMap().put(key, value);
                this.bitField0_ |= 4;
                return this;
            }

            public Builder putAllHeaders(Map<String, String> values2) {
                this.internalGetMutableHeaders().getMutableMap().putAll(values2);
                this.bitField0_ |= 4;
                return this;
            }

            @Override
            public String getPath() {
                Object ref = this.path_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.path_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPathBytes() {
                Object ref = this.path_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.path_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.path_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearPath() {
                this.path_ = Request.getDefaultInstance().getPath();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            @Override
            public String getHost() {
                Object ref = this.host_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.host_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getHostBytes() {
                Object ref = this.host_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.host_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setHost(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.host_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearHost() {
                this.host_ = Request.getDefaultInstance().getHost();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
                return this;
            }

            public Builder setHostBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.host_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            @Override
            public String getScheme() {
                Object ref = this.scheme_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.scheme_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getSchemeBytes() {
                Object ref = this.scheme_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.scheme_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setScheme(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.scheme_ = value;
                this.bitField0_ |= 0x20;
                this.onChanged();
                return this;
            }

            public Builder clearScheme() {
                this.scheme_ = Request.getDefaultInstance().getScheme();
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
                return this;
            }

            public Builder setSchemeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.scheme_ = value;
                this.bitField0_ |= 0x20;
                this.onChanged();
                return this;
            }

            @Override
            public String getQuery() {
                Object ref = this.query_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.query_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getQueryBytes() {
                Object ref = this.query_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.query_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setQuery(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.query_ = value;
                this.bitField0_ |= 0x40;
                this.onChanged();
                return this;
            }

            public Builder clearQuery() {
                this.query_ = Request.getDefaultInstance().getQuery();
                this.bitField0_ &= 0xFFFFFFBF;
                this.onChanged();
                return this;
            }

            public Builder setQueryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.query_ = value;
                this.bitField0_ |= 0x40;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTime() {
                return (this.bitField0_ & 0x80) != 0;
            }

            @Override
            public Timestamp getTime() {
                if (this.timeBuilder_ == null) {
                    return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
                }
                return this.timeBuilder_.getMessage();
            }

            public Builder setTime(Timestamp value) {
                if (this.timeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.time_ = value;
                } else {
                    this.timeBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder setTime(Timestamp.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder mergeTime(Timestamp value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 0x80) != 0 && this.time_ != null && this.time_ != Timestamp.getDefaultInstance()) {
                        this.getTimeBuilder().mergeFrom(value);
                    } else {
                        this.time_ = value;
                    }
                } else {
                    this.timeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= 0xFFFFFF7F;
                this.time_ = null;
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.dispose();
                    this.timeBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Timestamp.Builder getTimeBuilder() {
                this.bitField0_ |= 0x80;
                this.onChanged();
                return this.getTimeFieldBuilder().getBuilder();
            }

            @Override
            public TimestampOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? Timestamp.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<Timestamp, Timestamp.Builder, TimestampOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3(this.getTime(), this.getParentForChildren(), this.isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            @Override
            public long getSize() {
                return this.size_;
            }

            public Builder setSize(long value) {
                this.size_ = value;
                this.bitField0_ |= 0x100;
                this.onChanged();
                return this;
            }

            public Builder clearSize() {
                this.bitField0_ &= 0xFFFFFEFF;
                this.size_ = 0L;
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
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this;
            }

            public Builder clearProtocol() {
                this.protocol_ = Request.getDefaultInstance().getProtocol();
                this.bitField0_ &= 0xFFFFFDFF;
                this.onChanged();
                return this;
            }

            public Builder setProtocolBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.protocol_ = value;
                this.bitField0_ |= 0x200;
                this.onChanged();
                return this;
            }

            @Override
            public String getReason() {
                Object ref = this.reason_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.reason_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getReasonBytes() {
                Object ref = this.reason_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.reason_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setReason(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.reason_ = value;
                this.bitField0_ |= 0x400;
                this.onChanged();
                return this;
            }

            public Builder clearReason() {
                this.reason_ = Request.getDefaultInstance().getReason();
                this.bitField0_ &= 0xFFFFFBFF;
                this.onChanged();
                return this;
            }

            public Builder setReasonBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Request.checkByteStringIsUtf8(value);
                this.reason_ = value;
                this.bitField0_ |= 0x400;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasAuth() {
                return (this.bitField0_ & 0x800) != 0;
            }

            @Override
            public Auth getAuth() {
                if (this.authBuilder_ == null) {
                    return this.auth_ == null ? Auth.getDefaultInstance() : this.auth_;
                }
                return this.authBuilder_.getMessage();
            }

            public Builder setAuth(Auth value) {
                if (this.authBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.auth_ = value;
                } else {
                    this.authBuilder_.setMessage(value);
                }
                this.bitField0_ |= 0x800;
                this.onChanged();
                return this;
            }

            public Builder setAuth(Auth.Builder builderForValue) {
                if (this.authBuilder_ == null) {
                    this.auth_ = builderForValue.build();
                } else {
                    this.authBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 0x800;
                this.onChanged();
                return this;
            }

            public Builder mergeAuth(Auth value) {
                if (this.authBuilder_ == null) {
                    if ((this.bitField0_ & 0x800) != 0 && this.auth_ != null && this.auth_ != Auth.getDefaultInstance()) {
                        this.getAuthBuilder().mergeFrom(value);
                    } else {
                        this.auth_ = value;
                    }
                } else {
                    this.authBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 0x800;
                this.onChanged();
                return this;
            }

            public Builder clearAuth() {
                this.bitField0_ &= 0xFFFFF7FF;
                this.auth_ = null;
                if (this.authBuilder_ != null) {
                    this.authBuilder_.dispose();
                    this.authBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Auth.Builder getAuthBuilder() {
                this.bitField0_ |= 0x800;
                this.onChanged();
                return this.getAuthFieldBuilder().getBuilder();
            }

            @Override
            public AuthOrBuilder getAuthOrBuilder() {
                if (this.authBuilder_ != null) {
                    return this.authBuilder_.getMessageOrBuilder();
                }
                return this.auth_ == null ? Auth.getDefaultInstance() : this.auth_;
            }

            private SingleFieldBuilderV3<Auth, Auth.Builder, AuthOrBuilder> getAuthFieldBuilder() {
                if (this.authBuilder_ == null) {
                    this.authBuilder_ = new SingleFieldBuilderV3(this.getAuth(), this.getParentForChildren(), this.isClean());
                    this.auth_ = null;
                }
                return this.authBuilder_;
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

        private static final class HeadersDefaultEntryHolder {
            static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Request_HeadersEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private HeadersDefaultEntryHolder() {
            }
        }
    }

    public static final class Peer
    extends GeneratedMessageV3
    implements PeerOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int IP_FIELD_NUMBER = 1;
        private volatile Object ip_ = "";
        public static final int PORT_FIELD_NUMBER = 2;
        private long port_ = 0L;
        public static final int LABELS_FIELD_NUMBER = 6;
        private MapField<String, String> labels_;
        public static final int PRINCIPAL_FIELD_NUMBER = 7;
        private volatile Object principal_ = "";
        public static final int REGION_CODE_FIELD_NUMBER = 8;
        private volatile Object regionCode_ = "";
        private byte memoizedIsInitialized = (byte)-1;
        private static final Peer DEFAULT_INSTANCE = new Peer();
        private static final Parser<Peer> PARSER = new AbstractParser<Peer>(){

            @Override
            public Peer parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Peer.newBuilder();
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

        private Peer(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Peer() {
            this.ip_ = "";
            this.principal_ = "";
            this.regionCode_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Peer();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 6: {
                    return this.internalGetLabels();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_fieldAccessorTable.ensureFieldAccessorsInitialized(Peer.class, Builder.class);
        }

        @Override
        public String getIp() {
            Object ref = this.ip_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.ip_ = s2;
            return s2;
        }

        @Override
        public ByteString getIpBytes() {
            Object ref = this.ip_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.ip_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public long getPort() {
            return this.port_;
        }

        private MapField<String, String> internalGetLabels() {
            if (this.labels_ == null) {
                return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
            }
            return this.labels_;
        }

        @Override
        public int getLabelsCount() {
            return this.internalGetLabels().getMap().size();
        }

        @Override
        public boolean containsLabels(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetLabels().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getLabels() {
            return this.getLabelsMap();
        }

        @Override
        public Map<String, String> getLabelsMap() {
            return this.internalGetLabels().getMap();
        }

        @Override
        public String getLabelsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getLabelsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetLabels().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        @Override
        public String getPrincipal() {
            Object ref = this.principal_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.principal_ = s2;
            return s2;
        }

        @Override
        public ByteString getPrincipalBytes() {
            Object ref = this.principal_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.principal_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public String getRegionCode() {
            Object ref = this.regionCode_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.regionCode_ = s2;
            return s2;
        }

        @Override
        public ByteString getRegionCodeBytes() {
            Object ref = this.regionCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.regionCode_ = b;
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
            if (!GeneratedMessageV3.isStringEmpty(this.ip_)) {
                GeneratedMessageV3.writeString(output, 1, this.ip_);
            }
            if (this.port_ != 0L) {
                output.writeInt64(2, this.port_);
            }
            GeneratedMessageV3.serializeStringMapTo(output, this.internalGetLabels(), LabelsDefaultEntryHolder.defaultEntry, 6);
            if (!GeneratedMessageV3.isStringEmpty(this.principal_)) {
                GeneratedMessageV3.writeString(output, 7, this.principal_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
                GeneratedMessageV3.writeString(output, 8, this.regionCode_);
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
            if (!GeneratedMessageV3.isStringEmpty(this.ip_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.ip_);
            }
            if (this.port_ != 0L) {
                size += CodedOutputStream.computeInt64Size(2, this.port_);
            }
            for (Map.Entry<String, String> entry : this.internalGetLabels().getMap().entrySet()) {
                Message labels__ = ((MapEntry.Builder)LabelsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
                size += CodedOutputStream.computeMessageSize(6, labels__);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.principal_)) {
                size += GeneratedMessageV3.computeStringSize(7, this.principal_);
            }
            if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
                size += GeneratedMessageV3.computeStringSize(8, this.regionCode_);
            }
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Peer)) {
                return super.equals(obj);
            }
            Peer other = (Peer)obj;
            if (!this.getIp().equals(other.getIp())) {
                return false;
            }
            if (this.getPort() != other.getPort()) {
                return false;
            }
            if (!this.internalGetLabels().equals(other.internalGetLabels())) {
                return false;
            }
            if (!this.getPrincipal().equals(other.getPrincipal())) {
                return false;
            }
            if (!this.getRegionCode().equals(other.getRegionCode())) {
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
            hash = 19 * hash + Peer.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getIp().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getPort());
            if (!this.internalGetLabels().getMap().isEmpty()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.internalGetLabels().hashCode();
            }
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getPrincipal().hashCode();
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getRegionCode().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Peer parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Peer parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Peer parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Peer parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Peer parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Peer parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Peer parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Peer parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Peer.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Peer prototype) {
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

        public static Peer getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Peer> parser() {
            return PARSER;
        }

        public Parser<Peer> getParserForType() {
            return PARSER;
        }

        @Override
        public Peer getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements PeerOrBuilder {
            private int bitField0_;
            private Object ip_ = "";
            private long port_;
            private MapField<String, String> labels_;
            private Object principal_ = "";
            private Object regionCode_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_descriptor;
            }

            @Override
            protected MapField internalGetMapField(int number) {
                switch (number) {
                    case 6: {
                        return this.internalGetLabels();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected MapField internalGetMutableMapField(int number) {
                switch (number) {
                    case 6: {
                        return this.internalGetMutableLabels();
                    }
                }
                throw new RuntimeException("Invalid map field number: " + number);
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_fieldAccessorTable.ensureFieldAccessorsInitialized(Peer.class, Builder.class);
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
                this.ip_ = "";
                this.port_ = 0L;
                this.internalGetMutableLabels().clear();
                this.principal_ = "";
                this.regionCode_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_descriptor;
            }

            @Override
            public Peer getDefaultInstanceForType() {
                return Peer.getDefaultInstance();
            }

            @Override
            public Peer build() {
                Peer result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Peer buildPartial() {
                Peer result = new Peer(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Peer result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.ip_ = this.ip_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    result.port_ = this.port_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.labels_ = this.internalGetLabels();
                    result.labels_.makeImmutable();
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.principal_ = this.principal_;
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    result.regionCode_ = this.regionCode_;
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
                if (other instanceof Peer) {
                    return this.mergeFrom((Peer)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Peer other) {
                if (other == Peer.getDefaultInstance()) {
                    return this;
                }
                if (!other.getIp().isEmpty()) {
                    this.ip_ = other.ip_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (other.getPort() != 0L) {
                    this.setPort(other.getPort());
                }
                this.internalGetMutableLabels().mergeFrom(other.internalGetLabels());
                this.bitField0_ |= 4;
                if (!other.getPrincipal().isEmpty()) {
                    this.principal_ = other.principal_;
                    this.bitField0_ |= 8;
                    this.onChanged();
                }
                if (!other.getRegionCode().isEmpty()) {
                    this.regionCode_ = other.regionCode_;
                    this.bitField0_ |= 0x10;
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
                    block13: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block13;
                            }
                            case 10: {
                                this.ip_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block13;
                            }
                            case 16: {
                                this.port_ = input.readInt64();
                                this.bitField0_ |= 2;
                                continue block13;
                            }
                            case 50: {
                                MapEntry<String, String> labels__ = input.readMessage(LabelsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                                this.internalGetMutableLabels().getMutableMap().put(labels__.getKey(), labels__.getValue());
                                this.bitField0_ |= 4;
                                continue block13;
                            }
                            case 58: {
                                this.principal_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 8;
                                continue block13;
                            }
                            case 66: {
                                this.regionCode_ = input.readStringRequireUtf8();
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
            public String getIp() {
                Object ref = this.ip_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.ip_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getIpBytes() {
                Object ref = this.ip_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.ip_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setIp(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ip_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearIp() {
                this.ip_ = Peer.getDefaultInstance().getIp();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setIpBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Peer.checkByteStringIsUtf8(value);
                this.ip_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            @Override
            public long getPort() {
                return this.port_;
            }

            public Builder setPort(long value) {
                this.port_ = value;
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearPort() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.port_ = 0L;
                this.onChanged();
                return this;
            }

            private MapField<String, String> internalGetLabels() {
                if (this.labels_ == null) {
                    return MapField.emptyMapField(LabelsDefaultEntryHolder.defaultEntry);
                }
                return this.labels_;
            }

            private MapField<String, String> internalGetMutableLabels() {
                if (this.labels_ == null) {
                    this.labels_ = MapField.newMapField(LabelsDefaultEntryHolder.defaultEntry);
                }
                if (!this.labels_.isMutable()) {
                    this.labels_ = this.labels_.copy();
                }
                this.bitField0_ |= 4;
                this.onChanged();
                return this.labels_;
            }

            @Override
            public int getLabelsCount() {
                return this.internalGetLabels().getMap().size();
            }

            @Override
            public boolean containsLabels(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                return this.internalGetLabels().getMap().containsKey(key);
            }

            @Override
            @Deprecated
            public Map<String, String> getLabels() {
                return this.getLabelsMap();
            }

            @Override
            public Map<String, String> getLabelsMap() {
                return this.internalGetLabels().getMap();
            }

            @Override
            public String getLabelsOrDefault(String key, String defaultValue) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetLabels().getMap();
                return map.containsKey(key) ? map.get(key) : defaultValue;
            }

            @Override
            public String getLabelsOrThrow(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                Map<String, String> map = this.internalGetLabels().getMap();
                if (!map.containsKey(key)) {
                    throw new IllegalArgumentException();
                }
                return map.get(key);
            }

            public Builder clearLabels() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.internalGetMutableLabels().getMutableMap().clear();
                return this;
            }

            public Builder removeLabels(String key) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                this.internalGetMutableLabels().getMutableMap().remove(key);
                return this;
            }

            @Deprecated
            public Map<String, String> getMutableLabels() {
                this.bitField0_ |= 4;
                return this.internalGetMutableLabels().getMutableMap();
            }

            public Builder putLabels(String key, String value) {
                if (key == null) {
                    throw new NullPointerException("map key");
                }
                if (value == null) {
                    throw new NullPointerException("map value");
                }
                this.internalGetMutableLabels().getMutableMap().put(key, value);
                this.bitField0_ |= 4;
                return this;
            }

            public Builder putAllLabels(Map<String, String> values2) {
                this.internalGetMutableLabels().getMutableMap().putAll(values2);
                this.bitField0_ |= 4;
                return this;
            }

            @Override
            public String getPrincipal() {
                Object ref = this.principal_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.principal_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPrincipalBytes() {
                Object ref = this.principal_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.principal_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPrincipal(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.principal_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearPrincipal() {
                this.principal_ = Peer.getDefaultInstance().getPrincipal();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder setPrincipalBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Peer.checkByteStringIsUtf8(value);
                this.principal_ = value;
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            @Override
            public String getRegionCode() {
                Object ref = this.regionCode_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.regionCode_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getRegionCodeBytes() {
                Object ref = this.regionCode_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.regionCode_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setRegionCode(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.regionCode_ = value;
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearRegionCode() {
                this.regionCode_ = Peer.getDefaultInstance().getRegionCode();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
                return this;
            }

            public Builder setRegionCodeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Peer.checkByteStringIsUtf8(value);
                this.regionCode_ = value;
                this.bitField0_ |= 0x10;
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

        private static final class LabelsDefaultEntryHolder {
            static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Peer_LabelsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

            private LabelsDefaultEntryHolder() {
            }
        }
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AttributeContextOrBuilder {
        private int bitField0_;
        private Peer origin_;
        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> originBuilder_;
        private Peer source_;
        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> sourceBuilder_;
        private Peer destination_;
        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> destinationBuilder_;
        private Request request_;
        private SingleFieldBuilderV3<Request, Request.Builder, RequestOrBuilder> requestBuilder_;
        private Response response_;
        private SingleFieldBuilderV3<Response, Response.Builder, ResponseOrBuilder> responseBuilder_;
        private Resource resource_;
        private SingleFieldBuilderV3<Resource, Resource.Builder, ResourceOrBuilder> resourceBuilder_;
        private Api api_;
        private SingleFieldBuilderV3<Api, Api.Builder, ApiOrBuilder> apiBuilder_;
        private List<Any> extensions_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> extensionsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_fieldAccessorTable.ensureFieldAccessorsInitialized(AttributeContext.class, Builder.class);
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
            this.origin_ = null;
            if (this.originBuilder_ != null) {
                this.originBuilder_.dispose();
                this.originBuilder_ = null;
            }
            this.source_ = null;
            if (this.sourceBuilder_ != null) {
                this.sourceBuilder_.dispose();
                this.sourceBuilder_ = null;
            }
            this.destination_ = null;
            if (this.destinationBuilder_ != null) {
                this.destinationBuilder_.dispose();
                this.destinationBuilder_ = null;
            }
            this.request_ = null;
            if (this.requestBuilder_ != null) {
                this.requestBuilder_.dispose();
                this.requestBuilder_ = null;
            }
            this.response_ = null;
            if (this.responseBuilder_ != null) {
                this.responseBuilder_.dispose();
                this.responseBuilder_ = null;
            }
            this.resource_ = null;
            if (this.resourceBuilder_ != null) {
                this.resourceBuilder_.dispose();
                this.resourceBuilder_ = null;
            }
            this.api_ = null;
            if (this.apiBuilder_ != null) {
                this.apiBuilder_.dispose();
                this.apiBuilder_ = null;
            }
            if (this.extensionsBuilder_ == null) {
                this.extensions_ = Collections.emptyList();
            } else {
                this.extensions_ = null;
                this.extensionsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFF7F;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_descriptor;
        }

        @Override
        public AttributeContext getDefaultInstanceForType() {
            return AttributeContext.getDefaultInstance();
        }

        @Override
        public AttributeContext build() {
            AttributeContext result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AttributeContext buildPartial() {
            AttributeContext result = new AttributeContext(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(AttributeContext result) {
            if (this.extensionsBuilder_ == null) {
                if ((this.bitField0_ & 0x80) != 0) {
                    this.extensions_ = Collections.unmodifiableList(this.extensions_);
                    this.bitField0_ &= 0xFFFFFF7F;
                }
                result.extensions_ = this.extensions_;
            } else {
                result.extensions_ = this.extensionsBuilder_.build();
            }
        }

        private void buildPartial0(AttributeContext result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.origin_ = this.originBuilder_ == null ? this.origin_ : this.originBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.source_ = this.sourceBuilder_ == null ? this.source_ : this.sourceBuilder_.build();
            }
            if ((from_bitField0_ & 4) != 0) {
                result.destination_ = this.destinationBuilder_ == null ? this.destination_ : this.destinationBuilder_.build();
            }
            if ((from_bitField0_ & 8) != 0) {
                result.request_ = this.requestBuilder_ == null ? this.request_ : this.requestBuilder_.build();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.response_ = this.responseBuilder_ == null ? this.response_ : this.responseBuilder_.build();
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.resource_ = this.resourceBuilder_ == null ? this.resource_ : this.resourceBuilder_.build();
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.api_ = this.apiBuilder_ == null ? this.api_ : this.apiBuilder_.build();
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
            if (other instanceof AttributeContext) {
                return this.mergeFrom((AttributeContext)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AttributeContext other) {
            if (other == AttributeContext.getDefaultInstance()) {
                return this;
            }
            if (other.hasOrigin()) {
                this.mergeOrigin(other.getOrigin());
            }
            if (other.hasSource()) {
                this.mergeSource(other.getSource());
            }
            if (other.hasDestination()) {
                this.mergeDestination(other.getDestination());
            }
            if (other.hasRequest()) {
                this.mergeRequest(other.getRequest());
            }
            if (other.hasResponse()) {
                this.mergeResponse(other.getResponse());
            }
            if (other.hasResource()) {
                this.mergeResource(other.getResource());
            }
            if (other.hasApi()) {
                this.mergeApi(other.getApi());
            }
            if (this.extensionsBuilder_ == null) {
                if (!other.extensions_.isEmpty()) {
                    if (this.extensions_.isEmpty()) {
                        this.extensions_ = other.extensions_;
                        this.bitField0_ &= 0xFFFFFF7F;
                    } else {
                        this.ensureExtensionsIsMutable();
                        this.extensions_.addAll(other.extensions_);
                    }
                    this.onChanged();
                }
            } else if (!other.extensions_.isEmpty()) {
                if (this.extensionsBuilder_.isEmpty()) {
                    this.extensionsBuilder_.dispose();
                    this.extensionsBuilder_ = null;
                    this.extensions_ = other.extensions_;
                    this.bitField0_ &= 0xFFFFFF7F;
                    this.extensionsBuilder_ = alwaysUseFieldBuilders ? this.getExtensionsFieldBuilder() : null;
                } else {
                    this.extensionsBuilder_.addAllMessages(other.extensions_);
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
                block16: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block16;
                        }
                        case 10: {
                            input.readMessage(this.getSourceFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 2;
                            continue block16;
                        }
                        case 18: {
                            input.readMessage(this.getDestinationFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 4;
                            continue block16;
                        }
                        case 26: {
                            input.readMessage(this.getRequestFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
                            continue block16;
                        }
                        case 34: {
                            input.readMessage(this.getResponseFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block16;
                        }
                        case 42: {
                            input.readMessage(this.getResourceFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x20;
                            continue block16;
                        }
                        case 50: {
                            input.readMessage(this.getApiFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x40;
                            continue block16;
                        }
                        case 58: {
                            input.readMessage(this.getOriginFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block16;
                        }
                        case 66: {
                            Any m4 = input.readMessage(Any.parser(), extensionRegistry);
                            if (this.extensionsBuilder_ == null) {
                                this.ensureExtensionsIsMutable();
                                this.extensions_.add(m4);
                                continue block16;
                            }
                            this.extensionsBuilder_.addMessage(m4);
                            continue block16;
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
        public boolean hasOrigin() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Peer getOrigin() {
            if (this.originBuilder_ == null) {
                return this.origin_ == null ? Peer.getDefaultInstance() : this.origin_;
            }
            return this.originBuilder_.getMessage();
        }

        public Builder setOrigin(Peer value) {
            if (this.originBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.origin_ = value;
            } else {
                this.originBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setOrigin(Peer.Builder builderForValue) {
            if (this.originBuilder_ == null) {
                this.origin_ = builderForValue.build();
            } else {
                this.originBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergeOrigin(Peer value) {
            if (this.originBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.origin_ != null && this.origin_ != Peer.getDefaultInstance()) {
                    this.getOriginBuilder().mergeFrom(value);
                } else {
                    this.origin_ = value;
                }
            } else {
                this.originBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearOrigin() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.origin_ = null;
            if (this.originBuilder_ != null) {
                this.originBuilder_.dispose();
                this.originBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Peer.Builder getOriginBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getOriginFieldBuilder().getBuilder();
        }

        @Override
        public PeerOrBuilder getOriginOrBuilder() {
            if (this.originBuilder_ != null) {
                return this.originBuilder_.getMessageOrBuilder();
            }
            return this.origin_ == null ? Peer.getDefaultInstance() : this.origin_;
        }

        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> getOriginFieldBuilder() {
            if (this.originBuilder_ == null) {
                this.originBuilder_ = new SingleFieldBuilderV3(this.getOrigin(), this.getParentForChildren(), this.isClean());
                this.origin_ = null;
            }
            return this.originBuilder_;
        }

        @Override
        public boolean hasSource() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override
        public Peer getSource() {
            if (this.sourceBuilder_ == null) {
                return this.source_ == null ? Peer.getDefaultInstance() : this.source_;
            }
            return this.sourceBuilder_.getMessage();
        }

        public Builder setSource(Peer value) {
            if (this.sourceBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.source_ = value;
            } else {
                this.sourceBuilder_.setMessage(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder setSource(Peer.Builder builderForValue) {
            if (this.sourceBuilder_ == null) {
                this.source_ = builderForValue.build();
            } else {
                this.sourceBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder mergeSource(Peer value) {
            if (this.sourceBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0 && this.source_ != null && this.source_ != Peer.getDefaultInstance()) {
                    this.getSourceBuilder().mergeFrom(value);
                } else {
                    this.source_ = value;
                }
            } else {
                this.sourceBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearSource() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.source_ = null;
            if (this.sourceBuilder_ != null) {
                this.sourceBuilder_.dispose();
                this.sourceBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Peer.Builder getSourceBuilder() {
            this.bitField0_ |= 2;
            this.onChanged();
            return this.getSourceFieldBuilder().getBuilder();
        }

        @Override
        public PeerOrBuilder getSourceOrBuilder() {
            if (this.sourceBuilder_ != null) {
                return this.sourceBuilder_.getMessageOrBuilder();
            }
            return this.source_ == null ? Peer.getDefaultInstance() : this.source_;
        }

        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> getSourceFieldBuilder() {
            if (this.sourceBuilder_ == null) {
                this.sourceBuilder_ = new SingleFieldBuilderV3(this.getSource(), this.getParentForChildren(), this.isClean());
                this.source_ = null;
            }
            return this.sourceBuilder_;
        }

        @Override
        public boolean hasDestination() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override
        public Peer getDestination() {
            if (this.destinationBuilder_ == null) {
                return this.destination_ == null ? Peer.getDefaultInstance() : this.destination_;
            }
            return this.destinationBuilder_.getMessage();
        }

        public Builder setDestination(Peer value) {
            if (this.destinationBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.destination_ = value;
            } else {
                this.destinationBuilder_.setMessage(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder setDestination(Peer.Builder builderForValue) {
            if (this.destinationBuilder_ == null) {
                this.destination_ = builderForValue.build();
            } else {
                this.destinationBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder mergeDestination(Peer value) {
            if (this.destinationBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0 && this.destination_ != null && this.destination_ != Peer.getDefaultInstance()) {
                    this.getDestinationBuilder().mergeFrom(value);
                } else {
                    this.destination_ = value;
                }
            } else {
                this.destinationBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDestination() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.destination_ = null;
            if (this.destinationBuilder_ != null) {
                this.destinationBuilder_.dispose();
                this.destinationBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Peer.Builder getDestinationBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return this.getDestinationFieldBuilder().getBuilder();
        }

        @Override
        public PeerOrBuilder getDestinationOrBuilder() {
            if (this.destinationBuilder_ != null) {
                return this.destinationBuilder_.getMessageOrBuilder();
            }
            return this.destination_ == null ? Peer.getDefaultInstance() : this.destination_;
        }

        private SingleFieldBuilderV3<Peer, Peer.Builder, PeerOrBuilder> getDestinationFieldBuilder() {
            if (this.destinationBuilder_ == null) {
                this.destinationBuilder_ = new SingleFieldBuilderV3(this.getDestination(), this.getParentForChildren(), this.isClean());
                this.destination_ = null;
            }
            return this.destinationBuilder_;
        }

        @Override
        public boolean hasRequest() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public Request getRequest() {
            if (this.requestBuilder_ == null) {
                return this.request_ == null ? Request.getDefaultInstance() : this.request_;
            }
            return this.requestBuilder_.getMessage();
        }

        public Builder setRequest(Request value) {
            if (this.requestBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.request_ = value;
            } else {
                this.requestBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setRequest(Request.Builder builderForValue) {
            if (this.requestBuilder_ == null) {
                this.request_ = builderForValue.build();
            } else {
                this.requestBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeRequest(Request value) {
            if (this.requestBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.request_ != null && this.request_ != Request.getDefaultInstance()) {
                    this.getRequestBuilder().mergeFrom(value);
                } else {
                    this.request_ = value;
                }
            } else {
                this.requestBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearRequest() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.request_ = null;
            if (this.requestBuilder_ != null) {
                this.requestBuilder_.dispose();
                this.requestBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Request.Builder getRequestBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getRequestFieldBuilder().getBuilder();
        }

        @Override
        public RequestOrBuilder getRequestOrBuilder() {
            if (this.requestBuilder_ != null) {
                return this.requestBuilder_.getMessageOrBuilder();
            }
            return this.request_ == null ? Request.getDefaultInstance() : this.request_;
        }

        private SingleFieldBuilderV3<Request, Request.Builder, RequestOrBuilder> getRequestFieldBuilder() {
            if (this.requestBuilder_ == null) {
                this.requestBuilder_ = new SingleFieldBuilderV3(this.getRequest(), this.getParentForChildren(), this.isClean());
                this.request_ = null;
            }
            return this.requestBuilder_;
        }

        @Override
        public boolean hasResponse() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public Response getResponse() {
            if (this.responseBuilder_ == null) {
                return this.response_ == null ? Response.getDefaultInstance() : this.response_;
            }
            return this.responseBuilder_.getMessage();
        }

        public Builder setResponse(Response value) {
            if (this.responseBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.response_ = value;
            } else {
                this.responseBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setResponse(Response.Builder builderForValue) {
            if (this.responseBuilder_ == null) {
                this.response_ = builderForValue.build();
            } else {
                this.responseBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeResponse(Response value) {
            if (this.responseBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.response_ != null && this.response_ != Response.getDefaultInstance()) {
                    this.getResponseBuilder().mergeFrom(value);
                } else {
                    this.response_ = value;
                }
            } else {
                this.responseBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearResponse() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.response_ = null;
            if (this.responseBuilder_ != null) {
                this.responseBuilder_.dispose();
                this.responseBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Response.Builder getResponseBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getResponseFieldBuilder().getBuilder();
        }

        @Override
        public ResponseOrBuilder getResponseOrBuilder() {
            if (this.responseBuilder_ != null) {
                return this.responseBuilder_.getMessageOrBuilder();
            }
            return this.response_ == null ? Response.getDefaultInstance() : this.response_;
        }

        private SingleFieldBuilderV3<Response, Response.Builder, ResponseOrBuilder> getResponseFieldBuilder() {
            if (this.responseBuilder_ == null) {
                this.responseBuilder_ = new SingleFieldBuilderV3(this.getResponse(), this.getParentForChildren(), this.isClean());
                this.response_ = null;
            }
            return this.responseBuilder_;
        }

        @Override
        public boolean hasResource() {
            return (this.bitField0_ & 0x20) != 0;
        }

        @Override
        public Resource getResource() {
            if (this.resourceBuilder_ == null) {
                return this.resource_ == null ? Resource.getDefaultInstance() : this.resource_;
            }
            return this.resourceBuilder_.getMessage();
        }

        public Builder setResource(Resource value) {
            if (this.resourceBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resource_ = value;
            } else {
                this.resourceBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder setResource(Resource.Builder builderForValue) {
            if (this.resourceBuilder_ == null) {
                this.resource_ = builderForValue.build();
            } else {
                this.resourceBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder mergeResource(Resource value) {
            if (this.resourceBuilder_ == null) {
                if ((this.bitField0_ & 0x20) != 0 && this.resource_ != null && this.resource_ != Resource.getDefaultInstance()) {
                    this.getResourceBuilder().mergeFrom(value);
                } else {
                    this.resource_ = value;
                }
            } else {
                this.resourceBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearResource() {
            this.bitField0_ &= 0xFFFFFFDF;
            this.resource_ = null;
            if (this.resourceBuilder_ != null) {
                this.resourceBuilder_.dispose();
                this.resourceBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Resource.Builder getResourceBuilder() {
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this.getResourceFieldBuilder().getBuilder();
        }

        @Override
        public ResourceOrBuilder getResourceOrBuilder() {
            if (this.resourceBuilder_ != null) {
                return this.resourceBuilder_.getMessageOrBuilder();
            }
            return this.resource_ == null ? Resource.getDefaultInstance() : this.resource_;
        }

        private SingleFieldBuilderV3<Resource, Resource.Builder, ResourceOrBuilder> getResourceFieldBuilder() {
            if (this.resourceBuilder_ == null) {
                this.resourceBuilder_ = new SingleFieldBuilderV3(this.getResource(), this.getParentForChildren(), this.isClean());
                this.resource_ = null;
            }
            return this.resourceBuilder_;
        }

        @Override
        public boolean hasApi() {
            return (this.bitField0_ & 0x40) != 0;
        }

        @Override
        public Api getApi() {
            if (this.apiBuilder_ == null) {
                return this.api_ == null ? Api.getDefaultInstance() : this.api_;
            }
            return this.apiBuilder_.getMessage();
        }

        public Builder setApi(Api value) {
            if (this.apiBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.api_ = value;
            } else {
                this.apiBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder setApi(Api.Builder builderForValue) {
            if (this.apiBuilder_ == null) {
                this.api_ = builderForValue.build();
            } else {
                this.apiBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder mergeApi(Api value) {
            if (this.apiBuilder_ == null) {
                if ((this.bitField0_ & 0x40) != 0 && this.api_ != null && this.api_ != Api.getDefaultInstance()) {
                    this.getApiBuilder().mergeFrom(value);
                } else {
                    this.api_ = value;
                }
            } else {
                this.apiBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearApi() {
            this.bitField0_ &= 0xFFFFFFBF;
            this.api_ = null;
            if (this.apiBuilder_ != null) {
                this.apiBuilder_.dispose();
                this.apiBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Api.Builder getApiBuilder() {
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this.getApiFieldBuilder().getBuilder();
        }

        @Override
        public ApiOrBuilder getApiOrBuilder() {
            if (this.apiBuilder_ != null) {
                return this.apiBuilder_.getMessageOrBuilder();
            }
            return this.api_ == null ? Api.getDefaultInstance() : this.api_;
        }

        private SingleFieldBuilderV3<Api, Api.Builder, ApiOrBuilder> getApiFieldBuilder() {
            if (this.apiBuilder_ == null) {
                this.apiBuilder_ = new SingleFieldBuilderV3(this.getApi(), this.getParentForChildren(), this.isClean());
                this.api_ = null;
            }
            return this.apiBuilder_;
        }

        private void ensureExtensionsIsMutable() {
            if ((this.bitField0_ & 0x80) == 0) {
                this.extensions_ = new ArrayList<Any>(this.extensions_);
                this.bitField0_ |= 0x80;
            }
        }

        @Override
        public List<Any> getExtensionsList() {
            if (this.extensionsBuilder_ == null) {
                return Collections.unmodifiableList(this.extensions_);
            }
            return this.extensionsBuilder_.getMessageList();
        }

        @Override
        public int getExtensionsCount() {
            if (this.extensionsBuilder_ == null) {
                return this.extensions_.size();
            }
            return this.extensionsBuilder_.getCount();
        }

        @Override
        public Any getExtensions(int index) {
            if (this.extensionsBuilder_ == null) {
                return this.extensions_.get(index);
            }
            return this.extensionsBuilder_.getMessage(index);
        }

        public Builder setExtensions(int index, Any value) {
            if (this.extensionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExtensionsIsMutable();
                this.extensions_.set(index, value);
                this.onChanged();
            } else {
                this.extensionsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setExtensions(int index, Any.Builder builderForValue) {
            if (this.extensionsBuilder_ == null) {
                this.ensureExtensionsIsMutable();
                this.extensions_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.extensionsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addExtensions(Any value) {
            if (this.extensionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExtensionsIsMutable();
                this.extensions_.add(value);
                this.onChanged();
            } else {
                this.extensionsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addExtensions(int index, Any value) {
            if (this.extensionsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureExtensionsIsMutable();
                this.extensions_.add(index, value);
                this.onChanged();
            } else {
                this.extensionsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addExtensions(Any.Builder builderForValue) {
            if (this.extensionsBuilder_ == null) {
                this.ensureExtensionsIsMutable();
                this.extensions_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.extensionsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addExtensions(int index, Any.Builder builderForValue) {
            if (this.extensionsBuilder_ == null) {
                this.ensureExtensionsIsMutable();
                this.extensions_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.extensionsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllExtensions(Iterable<? extends Any> values2) {
            if (this.extensionsBuilder_ == null) {
                this.ensureExtensionsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.extensions_);
                this.onChanged();
            } else {
                this.extensionsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearExtensions() {
            if (this.extensionsBuilder_ == null) {
                this.extensions_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFF7F;
                this.onChanged();
            } else {
                this.extensionsBuilder_.clear();
            }
            return this;
        }

        public Builder removeExtensions(int index) {
            if (this.extensionsBuilder_ == null) {
                this.ensureExtensionsIsMutable();
                this.extensions_.remove(index);
                this.onChanged();
            } else {
                this.extensionsBuilder_.remove(index);
            }
            return this;
        }

        public Any.Builder getExtensionsBuilder(int index) {
            return this.getExtensionsFieldBuilder().getBuilder(index);
        }

        @Override
        public AnyOrBuilder getExtensionsOrBuilder(int index) {
            if (this.extensionsBuilder_ == null) {
                return this.extensions_.get(index);
            }
            return this.extensionsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends AnyOrBuilder> getExtensionsOrBuilderList() {
            if (this.extensionsBuilder_ != null) {
                return this.extensionsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.extensions_);
        }

        public Any.Builder addExtensionsBuilder() {
            return this.getExtensionsFieldBuilder().addBuilder(Any.getDefaultInstance());
        }

        public Any.Builder addExtensionsBuilder(int index) {
            return this.getExtensionsFieldBuilder().addBuilder(index, Any.getDefaultInstance());
        }

        public List<Any.Builder> getExtensionsBuilderList() {
            return this.getExtensionsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getExtensionsFieldBuilder() {
            if (this.extensionsBuilder_ == null) {
                this.extensionsBuilder_ = new RepeatedFieldBuilderV3(this.extensions_, (this.bitField0_ & 0x80) != 0, this.getParentForChildren(), this.isClean());
                this.extensions_ = null;
            }
            return this.extensionsBuilder_;
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

    public static interface ResourceOrBuilder
    extends MessageOrBuilder {
        public String getService();

        public ByteString getServiceBytes();

        public String getName();

        public ByteString getNameBytes();

        public String getType();

        public ByteString getTypeBytes();

        public int getLabelsCount();

        public boolean containsLabels(String var1);

        @Deprecated
        public Map<String, String> getLabels();

        public Map<String, String> getLabelsMap();

        public String getLabelsOrDefault(String var1, String var2);

        public String getLabelsOrThrow(String var1);

        public String getUid();

        public ByteString getUidBytes();

        public int getAnnotationsCount();

        public boolean containsAnnotations(String var1);

        @Deprecated
        public Map<String, String> getAnnotations();

        public Map<String, String> getAnnotationsMap();

        public String getAnnotationsOrDefault(String var1, String var2);

        public String getAnnotationsOrThrow(String var1);

        public String getDisplayName();

        public ByteString getDisplayNameBytes();

        public boolean hasCreateTime();

        public Timestamp getCreateTime();

        public TimestampOrBuilder getCreateTimeOrBuilder();

        public boolean hasUpdateTime();

        public Timestamp getUpdateTime();

        public TimestampOrBuilder getUpdateTimeOrBuilder();

        public boolean hasDeleteTime();

        public Timestamp getDeleteTime();

        public TimestampOrBuilder getDeleteTimeOrBuilder();

        public String getEtag();

        public ByteString getEtagBytes();

        public String getLocation();

        public ByteString getLocationBytes();
    }

    public static interface ResponseOrBuilder
    extends MessageOrBuilder {
        public long getCode();

        public long getSize();

        public int getHeadersCount();

        public boolean containsHeaders(String var1);

        @Deprecated
        public Map<String, String> getHeaders();

        public Map<String, String> getHeadersMap();

        public String getHeadersOrDefault(String var1, String var2);

        public String getHeadersOrThrow(String var1);

        public boolean hasTime();

        public Timestamp getTime();

        public TimestampOrBuilder getTimeOrBuilder();

        public boolean hasBackendLatency();

        public Duration getBackendLatency();

        public DurationOrBuilder getBackendLatencyOrBuilder();
    }

    public static interface RequestOrBuilder
    extends MessageOrBuilder {
        public String getId();

        public ByteString getIdBytes();

        public String getMethod();

        public ByteString getMethodBytes();

        public int getHeadersCount();

        public boolean containsHeaders(String var1);

        @Deprecated
        public Map<String, String> getHeaders();

        public Map<String, String> getHeadersMap();

        public String getHeadersOrDefault(String var1, String var2);

        public String getHeadersOrThrow(String var1);

        public String getPath();

        public ByteString getPathBytes();

        public String getHost();

        public ByteString getHostBytes();

        public String getScheme();

        public ByteString getSchemeBytes();

        public String getQuery();

        public ByteString getQueryBytes();

        public boolean hasTime();

        public Timestamp getTime();

        public TimestampOrBuilder getTimeOrBuilder();

        public long getSize();

        public String getProtocol();

        public ByteString getProtocolBytes();

        public String getReason();

        public ByteString getReasonBytes();

        public boolean hasAuth();

        public Auth getAuth();

        public AuthOrBuilder getAuthOrBuilder();
    }

    public static final class Auth
    extends GeneratedMessageV3
    implements AuthOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PRINCIPAL_FIELD_NUMBER = 1;
        private volatile Object principal_ = "";
        public static final int AUDIENCES_FIELD_NUMBER = 2;
        private LazyStringArrayList audiences_ = LazyStringArrayList.emptyList();
        public static final int PRESENTER_FIELD_NUMBER = 3;
        private volatile Object presenter_ = "";
        public static final int CLAIMS_FIELD_NUMBER = 4;
        private Struct claims_;
        public static final int ACCESS_LEVELS_FIELD_NUMBER = 5;
        private LazyStringArrayList accessLevels_ = LazyStringArrayList.emptyList();
        private byte memoizedIsInitialized = (byte)-1;
        private static final Auth DEFAULT_INSTANCE = new Auth();
        private static final Parser<Auth> PARSER = new AbstractParser<Auth>(){

            @Override
            public Auth parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = Auth.newBuilder();
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

        private Auth(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Auth() {
            this.principal_ = "";
            this.audiences_ = LazyStringArrayList.emptyList();
            this.presenter_ = "";
            this.accessLevels_ = LazyStringArrayList.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new Auth();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Auth_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Auth_fieldAccessorTable.ensureFieldAccessorsInitialized(Auth.class, Builder.class);
        }

        @Override
        public String getPrincipal() {
            Object ref = this.principal_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.principal_ = s2;
            return s2;
        }

        @Override
        public ByteString getPrincipalBytes() {
            Object ref = this.principal_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.principal_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public ProtocolStringList getAudiencesList() {
            return this.audiences_;
        }

        @Override
        public int getAudiencesCount() {
            return this.audiences_.size();
        }

        @Override
        public String getAudiences(int index) {
            return this.audiences_.get(index);
        }

        @Override
        public ByteString getAudiencesBytes(int index) {
            return this.audiences_.getByteString(index);
        }

        @Override
        public String getPresenter() {
            Object ref = this.presenter_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.presenter_ = s2;
            return s2;
        }

        @Override
        public ByteString getPresenterBytes() {
            Object ref = this.presenter_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.presenter_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasClaims() {
            return this.claims_ != null;
        }

        @Override
        public Struct getClaims() {
            return this.claims_ == null ? Struct.getDefaultInstance() : this.claims_;
        }

        @Override
        public StructOrBuilder getClaimsOrBuilder() {
            return this.claims_ == null ? Struct.getDefaultInstance() : this.claims_;
        }

        public ProtocolStringList getAccessLevelsList() {
            return this.accessLevels_;
        }

        @Override
        public int getAccessLevelsCount() {
            return this.accessLevels_.size();
        }

        @Override
        public String getAccessLevels(int index) {
            return this.accessLevels_.get(index);
        }

        @Override
        public ByteString getAccessLevelsBytes(int index) {
            return this.accessLevels_.getByteString(index);
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
            int i;
            if (!GeneratedMessageV3.isStringEmpty(this.principal_)) {
                GeneratedMessageV3.writeString(output, 1, this.principal_);
            }
            for (i = 0; i < this.audiences_.size(); ++i) {
                GeneratedMessageV3.writeString(output, 2, this.audiences_.getRaw(i));
            }
            if (!GeneratedMessageV3.isStringEmpty(this.presenter_)) {
                GeneratedMessageV3.writeString(output, 3, this.presenter_);
            }
            if (this.claims_ != null) {
                output.writeMessage(4, this.getClaims());
            }
            for (i = 0; i < this.accessLevels_.size(); ++i) {
                GeneratedMessageV3.writeString(output, 5, this.accessLevels_.getRaw(i));
            }
            this.getUnknownFields().writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int i;
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (!GeneratedMessageV3.isStringEmpty(this.principal_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.principal_);
            }
            int dataSize = 0;
            for (i = 0; i < this.audiences_.size(); ++i) {
                dataSize += Auth.computeStringSizeNoTag(this.audiences_.getRaw(i));
            }
            size += dataSize;
            size += 1 * this.getAudiencesList().size();
            if (!GeneratedMessageV3.isStringEmpty(this.presenter_)) {
                size += GeneratedMessageV3.computeStringSize(3, this.presenter_);
            }
            if (this.claims_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getClaims());
            }
            dataSize = 0;
            for (i = 0; i < this.accessLevels_.size(); ++i) {
                dataSize += Auth.computeStringSizeNoTag(this.accessLevels_.getRaw(i));
            }
            size += dataSize;
            size += 1 * this.getAccessLevelsList().size();
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Auth)) {
                return super.equals(obj);
            }
            Auth other = (Auth)obj;
            if (!this.getPrincipal().equals(other.getPrincipal())) {
                return false;
            }
            if (!this.getAudiencesList().equals(other.getAudiencesList())) {
                return false;
            }
            if (!this.getPresenter().equals(other.getPresenter())) {
                return false;
            }
            if (this.hasClaims() != other.hasClaims()) {
                return false;
            }
            if (this.hasClaims() && !this.getClaims().equals(other.getClaims())) {
                return false;
            }
            if (!this.getAccessLevelsList().equals(other.getAccessLevelsList())) {
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
            hash = 19 * hash + Auth.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPrincipal().hashCode();
            if (this.getAudiencesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAudiencesList().hashCode();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getPresenter().hashCode();
            if (this.hasClaims()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getClaims().hashCode();
            }
            if (this.getAccessLevelsCount() > 0) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getAccessLevelsList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static Auth parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Auth parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Auth parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Auth parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Auth parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Auth parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Auth parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Auth parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Auth parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Auth parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Auth parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Auth parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Auth.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Auth prototype) {
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

        public static Auth getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Auth> parser() {
            return PARSER;
        }

        public Parser<Auth> getParserForType() {
            return PARSER;
        }

        @Override
        public Auth getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AuthOrBuilder {
            private int bitField0_;
            private Object principal_ = "";
            private LazyStringArrayList audiences_ = LazyStringArrayList.emptyList();
            private Object presenter_ = "";
            private Struct claims_;
            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> claimsBuilder_;
            private LazyStringArrayList accessLevels_ = LazyStringArrayList.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Auth_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Auth_fieldAccessorTable.ensureFieldAccessorsInitialized(Auth.class, Builder.class);
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
                this.principal_ = "";
                this.audiences_ = LazyStringArrayList.emptyList();
                this.presenter_ = "";
                this.claims_ = null;
                if (this.claimsBuilder_ != null) {
                    this.claimsBuilder_.dispose();
                    this.claimsBuilder_ = null;
                }
                this.accessLevels_ = LazyStringArrayList.emptyList();
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return AttributeContextProto.internal_static_google_rpc_context_AttributeContext_Auth_descriptor;
            }

            @Override
            public Auth getDefaultInstanceForType() {
                return Auth.getDefaultInstance();
            }

            @Override
            public Auth build() {
                Auth result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Auth buildPartial() {
                Auth result = new Auth(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(Auth result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.principal_ = this.principal_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    this.audiences_.makeImmutable();
                    result.audiences_ = this.audiences_;
                }
                if ((from_bitField0_ & 4) != 0) {
                    result.presenter_ = this.presenter_;
                }
                if ((from_bitField0_ & 8) != 0) {
                    result.claims_ = this.claimsBuilder_ == null ? this.claims_ : this.claimsBuilder_.build();
                }
                if ((from_bitField0_ & 0x10) != 0) {
                    this.accessLevels_.makeImmutable();
                    result.accessLevels_ = this.accessLevels_;
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
                if (other instanceof Auth) {
                    return this.mergeFrom((Auth)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Auth other) {
                if (other == Auth.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPrincipal().isEmpty()) {
                    this.principal_ = other.principal_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.audiences_.isEmpty()) {
                    if (this.audiences_.isEmpty()) {
                        this.audiences_ = other.audiences_;
                        this.bitField0_ |= 2;
                    } else {
                        this.ensureAudiencesIsMutable();
                        this.audiences_.addAll(other.audiences_);
                    }
                    this.onChanged();
                }
                if (!other.getPresenter().isEmpty()) {
                    this.presenter_ = other.presenter_;
                    this.bitField0_ |= 4;
                    this.onChanged();
                }
                if (other.hasClaims()) {
                    this.mergeClaims(other.getClaims());
                }
                if (!other.accessLevels_.isEmpty()) {
                    if (this.accessLevels_.isEmpty()) {
                        this.accessLevels_ = other.accessLevels_;
                        this.bitField0_ |= 0x10;
                    } else {
                        this.ensureAccessLevelsIsMutable();
                        this.accessLevels_.addAll(other.accessLevels_);
                    }
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
                    block13: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block13;
                            }
                            case 10: {
                                this.principal_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block13;
                            }
                            case 18: {
                                String s2 = input.readStringRequireUtf8();
                                this.ensureAudiencesIsMutable();
                                this.audiences_.add(s2);
                                continue block13;
                            }
                            case 26: {
                                this.presenter_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 4;
                                continue block13;
                            }
                            case 34: {
                                input.readMessage(this.getClaimsFieldBuilder().getBuilder(), extensionRegistry);
                                this.bitField0_ |= 8;
                                continue block13;
                            }
                            case 42: {
                                String s2 = input.readStringRequireUtf8();
                                this.ensureAccessLevelsIsMutable();
                                this.accessLevels_.add(s2);
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
            public String getPrincipal() {
                Object ref = this.principal_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.principal_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPrincipalBytes() {
                Object ref = this.principal_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.principal_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPrincipal(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.principal_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearPrincipal() {
                this.principal_ = Auth.getDefaultInstance().getPrincipal();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setPrincipalBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Auth.checkByteStringIsUtf8(value);
                this.principal_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            private void ensureAudiencesIsMutable() {
                if (!this.audiences_.isModifiable()) {
                    this.audiences_ = new LazyStringArrayList(this.audiences_);
                }
                this.bitField0_ |= 2;
            }

            public ProtocolStringList getAudiencesList() {
                this.audiences_.makeImmutable();
                return this.audiences_;
            }

            @Override
            public int getAudiencesCount() {
                return this.audiences_.size();
            }

            @Override
            public String getAudiences(int index) {
                return this.audiences_.get(index);
            }

            @Override
            public ByteString getAudiencesBytes(int index) {
                return this.audiences_.getByteString(index);
            }

            public Builder setAudiences(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAudiencesIsMutable();
                this.audiences_.set(index, value);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder addAudiences(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAudiencesIsMutable();
                this.audiences_.add(value);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder addAllAudiences(Iterable<String> values2) {
                this.ensureAudiencesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.audiences_);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearAudiences() {
                this.audiences_ = LazyStringArrayList.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addAudiencesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Auth.checkByteStringIsUtf8(value);
                this.ensureAudiencesIsMutable();
                this.audiences_.add(value);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            @Override
            public String getPresenter() {
                Object ref = this.presenter_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.presenter_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPresenterBytes() {
                Object ref = this.presenter_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.presenter_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPresenter(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.presenter_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            public Builder clearPresenter() {
                this.presenter_ = Auth.getDefaultInstance().getPresenter();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder setPresenterBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Auth.checkByteStringIsUtf8(value);
                this.presenter_ = value;
                this.bitField0_ |= 4;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasClaims() {
                return (this.bitField0_ & 8) != 0;
            }

            @Override
            public Struct getClaims() {
                if (this.claimsBuilder_ == null) {
                    return this.claims_ == null ? Struct.getDefaultInstance() : this.claims_;
                }
                return this.claimsBuilder_.getMessage();
            }

            public Builder setClaims(Struct value) {
                if (this.claimsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.claims_ = value;
                } else {
                    this.claimsBuilder_.setMessage(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder setClaims(Struct.Builder builderForValue) {
                if (this.claimsBuilder_ == null) {
                    this.claims_ = builderForValue.build();
                } else {
                    this.claimsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder mergeClaims(Struct value) {
                if (this.claimsBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 0 && this.claims_ != null && this.claims_ != Struct.getDefaultInstance()) {
                        this.getClaimsBuilder().mergeFrom(value);
                    } else {
                        this.claims_ = value;
                    }
                } else {
                    this.claimsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                this.onChanged();
                return this;
            }

            public Builder clearClaims() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.claims_ = null;
                if (this.claimsBuilder_ != null) {
                    this.claimsBuilder_.dispose();
                    this.claimsBuilder_ = null;
                }
                this.onChanged();
                return this;
            }

            public Struct.Builder getClaimsBuilder() {
                this.bitField0_ |= 8;
                this.onChanged();
                return this.getClaimsFieldBuilder().getBuilder();
            }

            @Override
            public StructOrBuilder getClaimsOrBuilder() {
                if (this.claimsBuilder_ != null) {
                    return this.claimsBuilder_.getMessageOrBuilder();
                }
                return this.claims_ == null ? Struct.getDefaultInstance() : this.claims_;
            }

            private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getClaimsFieldBuilder() {
                if (this.claimsBuilder_ == null) {
                    this.claimsBuilder_ = new SingleFieldBuilderV3(this.getClaims(), this.getParentForChildren(), this.isClean());
                    this.claims_ = null;
                }
                return this.claimsBuilder_;
            }

            private void ensureAccessLevelsIsMutable() {
                if (!this.accessLevels_.isModifiable()) {
                    this.accessLevels_ = new LazyStringArrayList(this.accessLevels_);
                }
                this.bitField0_ |= 0x10;
            }

            public ProtocolStringList getAccessLevelsList() {
                this.accessLevels_.makeImmutable();
                return this.accessLevels_;
            }

            @Override
            public int getAccessLevelsCount() {
                return this.accessLevels_.size();
            }

            @Override
            public String getAccessLevels(int index) {
                return this.accessLevels_.get(index);
            }

            @Override
            public ByteString getAccessLevelsBytes(int index) {
                return this.accessLevels_.getByteString(index);
            }

            public Builder setAccessLevels(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAccessLevelsIsMutable();
                this.accessLevels_.set(index, value);
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder addAccessLevels(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAccessLevelsIsMutable();
                this.accessLevels_.add(value);
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder addAllAccessLevels(Iterable<String> values2) {
                this.ensureAccessLevelsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.accessLevels_);
                this.bitField0_ |= 0x10;
                this.onChanged();
                return this;
            }

            public Builder clearAccessLevels() {
                this.accessLevels_ = LazyStringArrayList.emptyList();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
                return this;
            }

            public Builder addAccessLevelsBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Auth.checkByteStringIsUtf8(value);
                this.ensureAccessLevelsIsMutable();
                this.accessLevels_.add(value);
                this.bitField0_ |= 0x10;
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

    public static interface AuthOrBuilder
    extends MessageOrBuilder {
        public String getPrincipal();

        public ByteString getPrincipalBytes();

        public List<String> getAudiencesList();

        public int getAudiencesCount();

        public String getAudiences(int var1);

        public ByteString getAudiencesBytes(int var1);

        public String getPresenter();

        public ByteString getPresenterBytes();

        public boolean hasClaims();

        public Struct getClaims();

        public StructOrBuilder getClaimsOrBuilder();

        public List<String> getAccessLevelsList();

        public int getAccessLevelsCount();

        public String getAccessLevels(int var1);

        public ByteString getAccessLevelsBytes(int var1);
    }

    public static interface ApiOrBuilder
    extends MessageOrBuilder {
        public String getService();

        public ByteString getServiceBytes();

        public String getOperation();

        public ByteString getOperationBytes();

        public String getProtocol();

        public ByteString getProtocolBytes();

        public String getVersion();

        public ByteString getVersionBytes();
    }

    public static interface PeerOrBuilder
    extends MessageOrBuilder {
        public String getIp();

        public ByteString getIpBytes();

        public long getPort();

        public int getLabelsCount();

        public boolean containsLabels(String var1);

        @Deprecated
        public Map<String, String> getLabels();

        public Map<String, String> getLabelsMap();

        public String getLabelsOrDefault(String var1, String var2);

        public String getLabelsOrThrow(String var1);

        public String getPrincipal();

        public ByteString getPrincipalBytes();

        public String getRegionCode();

        public ByteString getRegionCodeBytes();
    }
}

