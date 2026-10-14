/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogOrBuilder;
import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.AuthenticationInfo;
import com.google.cloud.audit.AuthenticationInfoOrBuilder;
import com.google.cloud.audit.AuthorizationInfo;
import com.google.cloud.audit.AuthorizationInfoOrBuilder;
import com.google.cloud.audit.PolicyViolationInfo;
import com.google.cloud.audit.PolicyViolationInfoOrBuilder;
import com.google.cloud.audit.RequestMetadata;
import com.google.cloud.audit.RequestMetadataOrBuilder;
import com.google.cloud.audit.ResourceLocation;
import com.google.cloud.audit.ResourceLocationOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Any;
import com.google.protobuf.AnyOrBuilder;
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
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.rpc.Status;
import com.google.rpc.StatusOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuditLog
extends GeneratedMessageV3
implements AuditLogOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SERVICE_NAME_FIELD_NUMBER = 7;
    private volatile Object serviceName_ = "";
    public static final int METHOD_NAME_FIELD_NUMBER = 8;
    private volatile Object methodName_ = "";
    public static final int RESOURCE_NAME_FIELD_NUMBER = 11;
    private volatile Object resourceName_ = "";
    public static final int RESOURCE_LOCATION_FIELD_NUMBER = 20;
    private ResourceLocation resourceLocation_;
    public static final int RESOURCE_ORIGINAL_STATE_FIELD_NUMBER = 19;
    private Struct resourceOriginalState_;
    public static final int NUM_RESPONSE_ITEMS_FIELD_NUMBER = 12;
    private long numResponseItems_ = 0L;
    public static final int STATUS_FIELD_NUMBER = 2;
    private Status status_;
    public static final int AUTHENTICATION_INFO_FIELD_NUMBER = 3;
    private AuthenticationInfo authenticationInfo_;
    public static final int AUTHORIZATION_INFO_FIELD_NUMBER = 9;
    private List<AuthorizationInfo> authorizationInfo_;
    public static final int POLICY_VIOLATION_INFO_FIELD_NUMBER = 25;
    private PolicyViolationInfo policyViolationInfo_;
    public static final int REQUEST_METADATA_FIELD_NUMBER = 4;
    private RequestMetadata requestMetadata_;
    public static final int REQUEST_FIELD_NUMBER = 16;
    private Struct request_;
    public static final int RESPONSE_FIELD_NUMBER = 17;
    private Struct response_;
    public static final int METADATA_FIELD_NUMBER = 18;
    private Struct metadata_;
    public static final int SERVICE_DATA_FIELD_NUMBER = 15;
    private Any serviceData_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final AuditLog DEFAULT_INSTANCE = new AuditLog();
    private static final Parser<AuditLog> PARSER = new AbstractParser<AuditLog>(){

        @Override
        public AuditLog parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = AuditLog.newBuilder();
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

    private AuditLog(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AuditLog() {
        this.serviceName_ = "";
        this.methodName_ = "";
        this.resourceName_ = "";
        this.authorizationInfo_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new AuditLog();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_AuditLog_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_AuditLog_fieldAccessorTable.ensureFieldAccessorsInitialized(AuditLog.class, Builder.class);
    }

    @Override
    public String getServiceName() {
        Object ref = this.serviceName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.serviceName_ = s2;
        return s2;
    }

    @Override
    public ByteString getServiceNameBytes() {
        Object ref = this.serviceName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.serviceName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getMethodName() {
        Object ref = this.methodName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.methodName_ = s2;
        return s2;
    }

    @Override
    public ByteString getMethodNameBytes() {
        Object ref = this.methodName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.methodName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getResourceName() {
        Object ref = this.resourceName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resourceName_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourceNameBytes() {
        Object ref = this.resourceName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resourceName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasResourceLocation() {
        return this.resourceLocation_ != null;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return this.resourceLocation_ == null ? ResourceLocation.getDefaultInstance() : this.resourceLocation_;
    }

    @Override
    public ResourceLocationOrBuilder getResourceLocationOrBuilder() {
        return this.resourceLocation_ == null ? ResourceLocation.getDefaultInstance() : this.resourceLocation_;
    }

    @Override
    public boolean hasResourceOriginalState() {
        return this.resourceOriginalState_ != null;
    }

    @Override
    public Struct getResourceOriginalState() {
        return this.resourceOriginalState_ == null ? Struct.getDefaultInstance() : this.resourceOriginalState_;
    }

    @Override
    public StructOrBuilder getResourceOriginalStateOrBuilder() {
        return this.resourceOriginalState_ == null ? Struct.getDefaultInstance() : this.resourceOriginalState_;
    }

    @Override
    public long getNumResponseItems() {
        return this.numResponseItems_;
    }

    @Override
    public boolean hasStatus() {
        return this.status_ != null;
    }

    @Override
    public Status getStatus() {
        return this.status_ == null ? Status.getDefaultInstance() : this.status_;
    }

    @Override
    public StatusOrBuilder getStatusOrBuilder() {
        return this.status_ == null ? Status.getDefaultInstance() : this.status_;
    }

    @Override
    public boolean hasAuthenticationInfo() {
        return this.authenticationInfo_ != null;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo() {
        return this.authenticationInfo_ == null ? AuthenticationInfo.getDefaultInstance() : this.authenticationInfo_;
    }

    @Override
    public AuthenticationInfoOrBuilder getAuthenticationInfoOrBuilder() {
        return this.authenticationInfo_ == null ? AuthenticationInfo.getDefaultInstance() : this.authenticationInfo_;
    }

    @Override
    public List<AuthorizationInfo> getAuthorizationInfoList() {
        return this.authorizationInfo_;
    }

    @Override
    public List<? extends AuthorizationInfoOrBuilder> getAuthorizationInfoOrBuilderList() {
        return this.authorizationInfo_;
    }

    @Override
    public int getAuthorizationInfoCount() {
        return this.authorizationInfo_.size();
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(int index) {
        return this.authorizationInfo_.get(index);
    }

    @Override
    public AuthorizationInfoOrBuilder getAuthorizationInfoOrBuilder(int index) {
        return this.authorizationInfo_.get(index);
    }

    @Override
    public boolean hasPolicyViolationInfo() {
        return this.policyViolationInfo_ != null;
    }

    @Override
    public PolicyViolationInfo getPolicyViolationInfo() {
        return this.policyViolationInfo_ == null ? PolicyViolationInfo.getDefaultInstance() : this.policyViolationInfo_;
    }

    @Override
    public PolicyViolationInfoOrBuilder getPolicyViolationInfoOrBuilder() {
        return this.policyViolationInfo_ == null ? PolicyViolationInfo.getDefaultInstance() : this.policyViolationInfo_;
    }

    @Override
    public boolean hasRequestMetadata() {
        return this.requestMetadata_ != null;
    }

    @Override
    public RequestMetadata getRequestMetadata() {
        return this.requestMetadata_ == null ? RequestMetadata.getDefaultInstance() : this.requestMetadata_;
    }

    @Override
    public RequestMetadataOrBuilder getRequestMetadataOrBuilder() {
        return this.requestMetadata_ == null ? RequestMetadata.getDefaultInstance() : this.requestMetadata_;
    }

    @Override
    public boolean hasRequest() {
        return this.request_ != null;
    }

    @Override
    public Struct getRequest() {
        return this.request_ == null ? Struct.getDefaultInstance() : this.request_;
    }

    @Override
    public StructOrBuilder getRequestOrBuilder() {
        return this.request_ == null ? Struct.getDefaultInstance() : this.request_;
    }

    @Override
    public boolean hasResponse() {
        return this.response_ != null;
    }

    @Override
    public Struct getResponse() {
        return this.response_ == null ? Struct.getDefaultInstance() : this.response_;
    }

    @Override
    public StructOrBuilder getResponseOrBuilder() {
        return this.response_ == null ? Struct.getDefaultInstance() : this.response_;
    }

    @Override
    public boolean hasMetadata() {
        return this.metadata_ != null;
    }

    @Override
    public Struct getMetadata() {
        return this.metadata_ == null ? Struct.getDefaultInstance() : this.metadata_;
    }

    @Override
    public StructOrBuilder getMetadataOrBuilder() {
        return this.metadata_ == null ? Struct.getDefaultInstance() : this.metadata_;
    }

    @Override
    @Deprecated
    public boolean hasServiceData() {
        return this.serviceData_ != null;
    }

    @Override
    @Deprecated
    public Any getServiceData() {
        return this.serviceData_ == null ? Any.getDefaultInstance() : this.serviceData_;
    }

    @Override
    @Deprecated
    public AnyOrBuilder getServiceDataOrBuilder() {
        return this.serviceData_ == null ? Any.getDefaultInstance() : this.serviceData_;
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
        if (this.status_ != null) {
            output.writeMessage(2, this.getStatus());
        }
        if (this.authenticationInfo_ != null) {
            output.writeMessage(3, this.getAuthenticationInfo());
        }
        if (this.requestMetadata_ != null) {
            output.writeMessage(4, this.getRequestMetadata());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceName_)) {
            GeneratedMessageV3.writeString(output, 7, this.serviceName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.methodName_)) {
            GeneratedMessageV3.writeString(output, 8, this.methodName_);
        }
        for (int i = 0; i < this.authorizationInfo_.size(); ++i) {
            output.writeMessage(9, this.authorizationInfo_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceName_)) {
            GeneratedMessageV3.writeString(output, 11, this.resourceName_);
        }
        if (this.numResponseItems_ != 0L) {
            output.writeInt64(12, this.numResponseItems_);
        }
        if (this.serviceData_ != null) {
            output.writeMessage(15, this.getServiceData());
        }
        if (this.request_ != null) {
            output.writeMessage(16, this.getRequest());
        }
        if (this.response_ != null) {
            output.writeMessage(17, this.getResponse());
        }
        if (this.metadata_ != null) {
            output.writeMessage(18, this.getMetadata());
        }
        if (this.resourceOriginalState_ != null) {
            output.writeMessage(19, this.getResourceOriginalState());
        }
        if (this.resourceLocation_ != null) {
            output.writeMessage(20, this.getResourceLocation());
        }
        if (this.policyViolationInfo_ != null) {
            output.writeMessage(25, this.getPolicyViolationInfo());
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
        if (this.status_ != null) {
            size += CodedOutputStream.computeMessageSize(2, this.getStatus());
        }
        if (this.authenticationInfo_ != null) {
            size += CodedOutputStream.computeMessageSize(3, this.getAuthenticationInfo());
        }
        if (this.requestMetadata_ != null) {
            size += CodedOutputStream.computeMessageSize(4, this.getRequestMetadata());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceName_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.serviceName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.methodName_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.methodName_);
        }
        for (int i = 0; i < this.authorizationInfo_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(9, this.authorizationInfo_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceName_)) {
            size += GeneratedMessageV3.computeStringSize(11, this.resourceName_);
        }
        if (this.numResponseItems_ != 0L) {
            size += CodedOutputStream.computeInt64Size(12, this.numResponseItems_);
        }
        if (this.serviceData_ != null) {
            size += CodedOutputStream.computeMessageSize(15, this.getServiceData());
        }
        if (this.request_ != null) {
            size += CodedOutputStream.computeMessageSize(16, this.getRequest());
        }
        if (this.response_ != null) {
            size += CodedOutputStream.computeMessageSize(17, this.getResponse());
        }
        if (this.metadata_ != null) {
            size += CodedOutputStream.computeMessageSize(18, this.getMetadata());
        }
        if (this.resourceOriginalState_ != null) {
            size += CodedOutputStream.computeMessageSize(19, this.getResourceOriginalState());
        }
        if (this.resourceLocation_ != null) {
            size += CodedOutputStream.computeMessageSize(20, this.getResourceLocation());
        }
        if (this.policyViolationInfo_ != null) {
            size += CodedOutputStream.computeMessageSize(25, this.getPolicyViolationInfo());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuditLog)) {
            return super.equals(obj);
        }
        AuditLog other = (AuditLog)obj;
        if (!this.getServiceName().equals(other.getServiceName())) {
            return false;
        }
        if (!this.getMethodName().equals(other.getMethodName())) {
            return false;
        }
        if (!this.getResourceName().equals(other.getResourceName())) {
            return false;
        }
        if (this.hasResourceLocation() != other.hasResourceLocation()) {
            return false;
        }
        if (this.hasResourceLocation() && !this.getResourceLocation().equals(other.getResourceLocation())) {
            return false;
        }
        if (this.hasResourceOriginalState() != other.hasResourceOriginalState()) {
            return false;
        }
        if (this.hasResourceOriginalState() && !this.getResourceOriginalState().equals(other.getResourceOriginalState())) {
            return false;
        }
        if (this.getNumResponseItems() != other.getNumResponseItems()) {
            return false;
        }
        if (this.hasStatus() != other.hasStatus()) {
            return false;
        }
        if (this.hasStatus() && !this.getStatus().equals(other.getStatus())) {
            return false;
        }
        if (this.hasAuthenticationInfo() != other.hasAuthenticationInfo()) {
            return false;
        }
        if (this.hasAuthenticationInfo() && !this.getAuthenticationInfo().equals(other.getAuthenticationInfo())) {
            return false;
        }
        if (!this.getAuthorizationInfoList().equals(other.getAuthorizationInfoList())) {
            return false;
        }
        if (this.hasPolicyViolationInfo() != other.hasPolicyViolationInfo()) {
            return false;
        }
        if (this.hasPolicyViolationInfo() && !this.getPolicyViolationInfo().equals(other.getPolicyViolationInfo())) {
            return false;
        }
        if (this.hasRequestMetadata() != other.hasRequestMetadata()) {
            return false;
        }
        if (this.hasRequestMetadata() && !this.getRequestMetadata().equals(other.getRequestMetadata())) {
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
        if (this.hasMetadata() != other.hasMetadata()) {
            return false;
        }
        if (this.hasMetadata() && !this.getMetadata().equals(other.getMetadata())) {
            return false;
        }
        if (this.hasServiceData() != other.hasServiceData()) {
            return false;
        }
        if (this.hasServiceData() && !this.getServiceData().equals(other.getServiceData())) {
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
        hash = 19 * hash + AuditLog.getDescriptor().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getServiceName().hashCode();
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getMethodName().hashCode();
        hash = 37 * hash + 11;
        hash = 53 * hash + this.getResourceName().hashCode();
        if (this.hasResourceLocation()) {
            hash = 37 * hash + 20;
            hash = 53 * hash + this.getResourceLocation().hashCode();
        }
        if (this.hasResourceOriginalState()) {
            hash = 37 * hash + 19;
            hash = 53 * hash + this.getResourceOriginalState().hashCode();
        }
        hash = 37 * hash + 12;
        hash = 53 * hash + Internal.hashLong(this.getNumResponseItems());
        if (this.hasStatus()) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getStatus().hashCode();
        }
        if (this.hasAuthenticationInfo()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getAuthenticationInfo().hashCode();
        }
        if (this.getAuthorizationInfoCount() > 0) {
            hash = 37 * hash + 9;
            hash = 53 * hash + this.getAuthorizationInfoList().hashCode();
        }
        if (this.hasPolicyViolationInfo()) {
            hash = 37 * hash + 25;
            hash = 53 * hash + this.getPolicyViolationInfo().hashCode();
        }
        if (this.hasRequestMetadata()) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getRequestMetadata().hashCode();
        }
        if (this.hasRequest()) {
            hash = 37 * hash + 16;
            hash = 53 * hash + this.getRequest().hashCode();
        }
        if (this.hasResponse()) {
            hash = 37 * hash + 17;
            hash = 53 * hash + this.getResponse().hashCode();
        }
        if (this.hasMetadata()) {
            hash = 37 * hash + 18;
            hash = 53 * hash + this.getMetadata().hashCode();
        }
        if (this.hasServiceData()) {
            hash = 37 * hash + 15;
            hash = 53 * hash + this.getServiceData().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static AuditLog parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditLog parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditLog parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditLog parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditLog parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AuditLog parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AuditLog parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuditLog parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuditLog parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static AuditLog parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AuditLog parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static AuditLog parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return AuditLog.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AuditLog prototype) {
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

    public static AuditLog getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AuditLog> parser() {
        return PARSER;
    }

    public Parser<AuditLog> getParserForType() {
        return PARSER;
    }

    @Override
    public AuditLog getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AuditLogOrBuilder {
        private int bitField0_;
        private Object serviceName_ = "";
        private Object methodName_ = "";
        private Object resourceName_ = "";
        private ResourceLocation resourceLocation_;
        private SingleFieldBuilderV3<ResourceLocation, ResourceLocation.Builder, ResourceLocationOrBuilder> resourceLocationBuilder_;
        private Struct resourceOriginalState_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> resourceOriginalStateBuilder_;
        private long numResponseItems_;
        private Status status_;
        private SingleFieldBuilderV3<Status, Status.Builder, StatusOrBuilder> statusBuilder_;
        private AuthenticationInfo authenticationInfo_;
        private SingleFieldBuilderV3<AuthenticationInfo, AuthenticationInfo.Builder, AuthenticationInfoOrBuilder> authenticationInfoBuilder_;
        private List<AuthorizationInfo> authorizationInfo_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<AuthorizationInfo, AuthorizationInfo.Builder, AuthorizationInfoOrBuilder> authorizationInfoBuilder_;
        private PolicyViolationInfo policyViolationInfo_;
        private SingleFieldBuilderV3<PolicyViolationInfo, PolicyViolationInfo.Builder, PolicyViolationInfoOrBuilder> policyViolationInfoBuilder_;
        private RequestMetadata requestMetadata_;
        private SingleFieldBuilderV3<RequestMetadata, RequestMetadata.Builder, RequestMetadataOrBuilder> requestMetadataBuilder_;
        private Struct request_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> requestBuilder_;
        private Struct response_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> responseBuilder_;
        private Struct metadata_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> metadataBuilder_;
        private Any serviceData_;
        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> serviceDataBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_AuditLog_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_AuditLog_fieldAccessorTable.ensureFieldAccessorsInitialized(AuditLog.class, Builder.class);
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
            this.serviceName_ = "";
            this.methodName_ = "";
            this.resourceName_ = "";
            this.resourceLocation_ = null;
            if (this.resourceLocationBuilder_ != null) {
                this.resourceLocationBuilder_.dispose();
                this.resourceLocationBuilder_ = null;
            }
            this.resourceOriginalState_ = null;
            if (this.resourceOriginalStateBuilder_ != null) {
                this.resourceOriginalStateBuilder_.dispose();
                this.resourceOriginalStateBuilder_ = null;
            }
            this.numResponseItems_ = 0L;
            this.status_ = null;
            if (this.statusBuilder_ != null) {
                this.statusBuilder_.dispose();
                this.statusBuilder_ = null;
            }
            this.authenticationInfo_ = null;
            if (this.authenticationInfoBuilder_ != null) {
                this.authenticationInfoBuilder_.dispose();
                this.authenticationInfoBuilder_ = null;
            }
            if (this.authorizationInfoBuilder_ == null) {
                this.authorizationInfo_ = Collections.emptyList();
            } else {
                this.authorizationInfo_ = null;
                this.authorizationInfoBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFEFF;
            this.policyViolationInfo_ = null;
            if (this.policyViolationInfoBuilder_ != null) {
                this.policyViolationInfoBuilder_.dispose();
                this.policyViolationInfoBuilder_ = null;
            }
            this.requestMetadata_ = null;
            if (this.requestMetadataBuilder_ != null) {
                this.requestMetadataBuilder_.dispose();
                this.requestMetadataBuilder_ = null;
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
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.serviceData_ = null;
            if (this.serviceDataBuilder_ != null) {
                this.serviceDataBuilder_.dispose();
                this.serviceDataBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_AuditLog_descriptor;
        }

        @Override
        public AuditLog getDefaultInstanceForType() {
            return AuditLog.getDefaultInstance();
        }

        @Override
        public AuditLog build() {
            AuditLog result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public AuditLog buildPartial() {
            AuditLog result = new AuditLog(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(AuditLog result) {
            if (this.authorizationInfoBuilder_ == null) {
                if ((this.bitField0_ & 0x100) != 0) {
                    this.authorizationInfo_ = Collections.unmodifiableList(this.authorizationInfo_);
                    this.bitField0_ &= 0xFFFFFEFF;
                }
                result.authorizationInfo_ = this.authorizationInfo_;
            } else {
                result.authorizationInfo_ = this.authorizationInfoBuilder_.build();
            }
        }

        private void buildPartial0(AuditLog result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.serviceName_ = this.serviceName_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.methodName_ = this.methodName_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.resourceName_ = this.resourceName_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.resourceLocation_ = this.resourceLocationBuilder_ == null ? this.resourceLocation_ : this.resourceLocationBuilder_.build();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.resourceOriginalState_ = this.resourceOriginalStateBuilder_ == null ? this.resourceOriginalState_ : this.resourceOriginalStateBuilder_.build();
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.numResponseItems_ = this.numResponseItems_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.status_ = this.statusBuilder_ == null ? this.status_ : this.statusBuilder_.build();
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.authenticationInfo_ = this.authenticationInfoBuilder_ == null ? this.authenticationInfo_ : this.authenticationInfoBuilder_.build();
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.policyViolationInfo_ = this.policyViolationInfoBuilder_ == null ? this.policyViolationInfo_ : this.policyViolationInfoBuilder_.build();
            }
            if ((from_bitField0_ & 0x400) != 0) {
                result.requestMetadata_ = this.requestMetadataBuilder_ == null ? this.requestMetadata_ : this.requestMetadataBuilder_.build();
            }
            if ((from_bitField0_ & 0x800) != 0) {
                result.request_ = this.requestBuilder_ == null ? this.request_ : this.requestBuilder_.build();
            }
            if ((from_bitField0_ & 0x1000) != 0) {
                result.response_ = this.responseBuilder_ == null ? this.response_ : this.responseBuilder_.build();
            }
            if ((from_bitField0_ & 0x2000) != 0) {
                result.metadata_ = this.metadataBuilder_ == null ? this.metadata_ : this.metadataBuilder_.build();
            }
            if ((from_bitField0_ & 0x4000) != 0) {
                result.serviceData_ = this.serviceDataBuilder_ == null ? this.serviceData_ : this.serviceDataBuilder_.build();
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
            if (other instanceof AuditLog) {
                return this.mergeFrom((AuditLog)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(AuditLog other) {
            if (other == AuditLog.getDefaultInstance()) {
                return this;
            }
            if (!other.getServiceName().isEmpty()) {
                this.serviceName_ = other.serviceName_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getMethodName().isEmpty()) {
                this.methodName_ = other.methodName_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getResourceName().isEmpty()) {
                this.resourceName_ = other.resourceName_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.hasResourceLocation()) {
                this.mergeResourceLocation(other.getResourceLocation());
            }
            if (other.hasResourceOriginalState()) {
                this.mergeResourceOriginalState(other.getResourceOriginalState());
            }
            if (other.getNumResponseItems() != 0L) {
                this.setNumResponseItems(other.getNumResponseItems());
            }
            if (other.hasStatus()) {
                this.mergeStatus(other.getStatus());
            }
            if (other.hasAuthenticationInfo()) {
                this.mergeAuthenticationInfo(other.getAuthenticationInfo());
            }
            if (this.authorizationInfoBuilder_ == null) {
                if (!other.authorizationInfo_.isEmpty()) {
                    if (this.authorizationInfo_.isEmpty()) {
                        this.authorizationInfo_ = other.authorizationInfo_;
                        this.bitField0_ &= 0xFFFFFEFF;
                    } else {
                        this.ensureAuthorizationInfoIsMutable();
                        this.authorizationInfo_.addAll(other.authorizationInfo_);
                    }
                    this.onChanged();
                }
            } else if (!other.authorizationInfo_.isEmpty()) {
                if (this.authorizationInfoBuilder_.isEmpty()) {
                    this.authorizationInfoBuilder_.dispose();
                    this.authorizationInfoBuilder_ = null;
                    this.authorizationInfo_ = other.authorizationInfo_;
                    this.bitField0_ &= 0xFFFFFEFF;
                    this.authorizationInfoBuilder_ = alwaysUseFieldBuilders ? this.getAuthorizationInfoFieldBuilder() : null;
                } else {
                    this.authorizationInfoBuilder_.addAllMessages(other.authorizationInfo_);
                }
            }
            if (other.hasPolicyViolationInfo()) {
                this.mergePolicyViolationInfo(other.getPolicyViolationInfo());
            }
            if (other.hasRequestMetadata()) {
                this.mergeRequestMetadata(other.getRequestMetadata());
            }
            if (other.hasRequest()) {
                this.mergeRequest(other.getRequest());
            }
            if (other.hasResponse()) {
                this.mergeResponse(other.getResponse());
            }
            if (other.hasMetadata()) {
                this.mergeMetadata(other.getMetadata());
            }
            if (other.hasServiceData()) {
                this.mergeServiceData(other.getServiceData());
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
                        case 18: {
                            input.readMessage(this.getStatusFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x40;
                            continue block23;
                        }
                        case 26: {
                            input.readMessage(this.getAuthenticationInfoFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x80;
                            continue block23;
                        }
                        case 34: {
                            input.readMessage(this.getRequestMetadataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x400;
                            continue block23;
                        }
                        case 58: {
                            this.serviceName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block23;
                        }
                        case 66: {
                            this.methodName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block23;
                        }
                        case 74: {
                            AuthorizationInfo m4 = input.readMessage(AuthorizationInfo.parser(), extensionRegistry);
                            if (this.authorizationInfoBuilder_ == null) {
                                this.ensureAuthorizationInfoIsMutable();
                                this.authorizationInfo_.add(m4);
                                continue block23;
                            }
                            this.authorizationInfoBuilder_.addMessage(m4);
                            continue block23;
                        }
                        case 90: {
                            this.resourceName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block23;
                        }
                        case 96: {
                            this.numResponseItems_ = input.readInt64();
                            this.bitField0_ |= 0x20;
                            continue block23;
                        }
                        case 122: {
                            input.readMessage(this.getServiceDataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x4000;
                            continue block23;
                        }
                        case 130: {
                            input.readMessage(this.getRequestFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x800;
                            continue block23;
                        }
                        case 138: {
                            input.readMessage(this.getResponseFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x1000;
                            continue block23;
                        }
                        case 146: {
                            input.readMessage(this.getMetadataFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x2000;
                            continue block23;
                        }
                        case 154: {
                            input.readMessage(this.getResourceOriginalStateFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block23;
                        }
                        case 162: {
                            input.readMessage(this.getResourceLocationFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
                            continue block23;
                        }
                        case 202: {
                            input.readMessage(this.getPolicyViolationInfoFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x200;
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
        public String getServiceName() {
            Object ref = this.serviceName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.serviceName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getServiceNameBytes() {
            Object ref = this.serviceName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.serviceName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setServiceName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.serviceName_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearServiceName() {
            this.serviceName_ = AuditLog.getDefaultInstance().getServiceName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setServiceNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuditLog.checkByteStringIsUtf8(value);
            this.serviceName_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getMethodName() {
            Object ref = this.methodName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.methodName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getMethodNameBytes() {
            Object ref = this.methodName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.methodName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setMethodName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.methodName_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMethodName() {
            this.methodName_ = AuditLog.getDefaultInstance().getMethodName();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setMethodNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuditLog.checkByteStringIsUtf8(value);
            this.methodName_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getResourceName() {
            Object ref = this.resourceName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resourceName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourceNameBytes() {
            Object ref = this.resourceName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resourceName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResourceName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resourceName_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearResourceName() {
            this.resourceName_ = AuditLog.getDefaultInstance().getResourceName();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setResourceNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AuditLog.checkByteStringIsUtf8(value);
            this.resourceName_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasResourceLocation() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public ResourceLocation getResourceLocation() {
            if (this.resourceLocationBuilder_ == null) {
                return this.resourceLocation_ == null ? ResourceLocation.getDefaultInstance() : this.resourceLocation_;
            }
            return this.resourceLocationBuilder_.getMessage();
        }

        public Builder setResourceLocation(ResourceLocation value) {
            if (this.resourceLocationBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resourceLocation_ = value;
            } else {
                this.resourceLocationBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setResourceLocation(ResourceLocation.Builder builderForValue) {
            if (this.resourceLocationBuilder_ == null) {
                this.resourceLocation_ = builderForValue.build();
            } else {
                this.resourceLocationBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeResourceLocation(ResourceLocation value) {
            if (this.resourceLocationBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.resourceLocation_ != null && this.resourceLocation_ != ResourceLocation.getDefaultInstance()) {
                    this.getResourceLocationBuilder().mergeFrom(value);
                } else {
                    this.resourceLocation_ = value;
                }
            } else {
                this.resourceLocationBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearResourceLocation() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.resourceLocation_ = null;
            if (this.resourceLocationBuilder_ != null) {
                this.resourceLocationBuilder_.dispose();
                this.resourceLocationBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public ResourceLocation.Builder getResourceLocationBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getResourceLocationFieldBuilder().getBuilder();
        }

        @Override
        public ResourceLocationOrBuilder getResourceLocationOrBuilder() {
            if (this.resourceLocationBuilder_ != null) {
                return this.resourceLocationBuilder_.getMessageOrBuilder();
            }
            return this.resourceLocation_ == null ? ResourceLocation.getDefaultInstance() : this.resourceLocation_;
        }

        private SingleFieldBuilderV3<ResourceLocation, ResourceLocation.Builder, ResourceLocationOrBuilder> getResourceLocationFieldBuilder() {
            if (this.resourceLocationBuilder_ == null) {
                this.resourceLocationBuilder_ = new SingleFieldBuilderV3(this.getResourceLocation(), this.getParentForChildren(), this.isClean());
                this.resourceLocation_ = null;
            }
            return this.resourceLocationBuilder_;
        }

        @Override
        public boolean hasResourceOriginalState() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public Struct getResourceOriginalState() {
            if (this.resourceOriginalStateBuilder_ == null) {
                return this.resourceOriginalState_ == null ? Struct.getDefaultInstance() : this.resourceOriginalState_;
            }
            return this.resourceOriginalStateBuilder_.getMessage();
        }

        public Builder setResourceOriginalState(Struct value) {
            if (this.resourceOriginalStateBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resourceOriginalState_ = value;
            } else {
                this.resourceOriginalStateBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setResourceOriginalState(Struct.Builder builderForValue) {
            if (this.resourceOriginalStateBuilder_ == null) {
                this.resourceOriginalState_ = builderForValue.build();
            } else {
                this.resourceOriginalStateBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeResourceOriginalState(Struct value) {
            if (this.resourceOriginalStateBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.resourceOriginalState_ != null && this.resourceOriginalState_ != Struct.getDefaultInstance()) {
                    this.getResourceOriginalStateBuilder().mergeFrom(value);
                } else {
                    this.resourceOriginalState_ = value;
                }
            } else {
                this.resourceOriginalStateBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearResourceOriginalState() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.resourceOriginalState_ = null;
            if (this.resourceOriginalStateBuilder_ != null) {
                this.resourceOriginalStateBuilder_.dispose();
                this.resourceOriginalStateBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getResourceOriginalStateBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getResourceOriginalStateFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getResourceOriginalStateOrBuilder() {
            if (this.resourceOriginalStateBuilder_ != null) {
                return this.resourceOriginalStateBuilder_.getMessageOrBuilder();
            }
            return this.resourceOriginalState_ == null ? Struct.getDefaultInstance() : this.resourceOriginalState_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getResourceOriginalStateFieldBuilder() {
            if (this.resourceOriginalStateBuilder_ == null) {
                this.resourceOriginalStateBuilder_ = new SingleFieldBuilderV3(this.getResourceOriginalState(), this.getParentForChildren(), this.isClean());
                this.resourceOriginalState_ = null;
            }
            return this.resourceOriginalStateBuilder_;
        }

        @Override
        public long getNumResponseItems() {
            return this.numResponseItems_;
        }

        public Builder setNumResponseItems(long value) {
            this.numResponseItems_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearNumResponseItems() {
            this.bitField0_ &= 0xFFFFFFDF;
            this.numResponseItems_ = 0L;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasStatus() {
            return (this.bitField0_ & 0x40) != 0;
        }

        @Override
        public Status getStatus() {
            if (this.statusBuilder_ == null) {
                return this.status_ == null ? Status.getDefaultInstance() : this.status_;
            }
            return this.statusBuilder_.getMessage();
        }

        public Builder setStatus(Status value) {
            if (this.statusBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.status_ = value;
            } else {
                this.statusBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder setStatus(Status.Builder builderForValue) {
            if (this.statusBuilder_ == null) {
                this.status_ = builderForValue.build();
            } else {
                this.statusBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder mergeStatus(Status value) {
            if (this.statusBuilder_ == null) {
                if ((this.bitField0_ & 0x40) != 0 && this.status_ != null && this.status_ != Status.getDefaultInstance()) {
                    this.getStatusBuilder().mergeFrom(value);
                } else {
                    this.status_ = value;
                }
            } else {
                this.statusBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearStatus() {
            this.bitField0_ &= 0xFFFFFFBF;
            this.status_ = null;
            if (this.statusBuilder_ != null) {
                this.statusBuilder_.dispose();
                this.statusBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Status.Builder getStatusBuilder() {
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this.getStatusFieldBuilder().getBuilder();
        }

        @Override
        public StatusOrBuilder getStatusOrBuilder() {
            if (this.statusBuilder_ != null) {
                return this.statusBuilder_.getMessageOrBuilder();
            }
            return this.status_ == null ? Status.getDefaultInstance() : this.status_;
        }

        private SingleFieldBuilderV3<Status, Status.Builder, StatusOrBuilder> getStatusFieldBuilder() {
            if (this.statusBuilder_ == null) {
                this.statusBuilder_ = new SingleFieldBuilderV3(this.getStatus(), this.getParentForChildren(), this.isClean());
                this.status_ = null;
            }
            return this.statusBuilder_;
        }

        @Override
        public boolean hasAuthenticationInfo() {
            return (this.bitField0_ & 0x80) != 0;
        }

        @Override
        public AuthenticationInfo getAuthenticationInfo() {
            if (this.authenticationInfoBuilder_ == null) {
                return this.authenticationInfo_ == null ? AuthenticationInfo.getDefaultInstance() : this.authenticationInfo_;
            }
            return this.authenticationInfoBuilder_.getMessage();
        }

        public Builder setAuthenticationInfo(AuthenticationInfo value) {
            if (this.authenticationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.authenticationInfo_ = value;
            } else {
                this.authenticationInfoBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder setAuthenticationInfo(AuthenticationInfo.Builder builderForValue) {
            if (this.authenticationInfoBuilder_ == null) {
                this.authenticationInfo_ = builderForValue.build();
            } else {
                this.authenticationInfoBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder mergeAuthenticationInfo(AuthenticationInfo value) {
            if (this.authenticationInfoBuilder_ == null) {
                if ((this.bitField0_ & 0x80) != 0 && this.authenticationInfo_ != null && this.authenticationInfo_ != AuthenticationInfo.getDefaultInstance()) {
                    this.getAuthenticationInfoBuilder().mergeFrom(value);
                } else {
                    this.authenticationInfo_ = value;
                }
            } else {
                this.authenticationInfoBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearAuthenticationInfo() {
            this.bitField0_ &= 0xFFFFFF7F;
            this.authenticationInfo_ = null;
            if (this.authenticationInfoBuilder_ != null) {
                this.authenticationInfoBuilder_.dispose();
                this.authenticationInfoBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public AuthenticationInfo.Builder getAuthenticationInfoBuilder() {
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this.getAuthenticationInfoFieldBuilder().getBuilder();
        }

        @Override
        public AuthenticationInfoOrBuilder getAuthenticationInfoOrBuilder() {
            if (this.authenticationInfoBuilder_ != null) {
                return this.authenticationInfoBuilder_.getMessageOrBuilder();
            }
            return this.authenticationInfo_ == null ? AuthenticationInfo.getDefaultInstance() : this.authenticationInfo_;
        }

        private SingleFieldBuilderV3<AuthenticationInfo, AuthenticationInfo.Builder, AuthenticationInfoOrBuilder> getAuthenticationInfoFieldBuilder() {
            if (this.authenticationInfoBuilder_ == null) {
                this.authenticationInfoBuilder_ = new SingleFieldBuilderV3(this.getAuthenticationInfo(), this.getParentForChildren(), this.isClean());
                this.authenticationInfo_ = null;
            }
            return this.authenticationInfoBuilder_;
        }

        private void ensureAuthorizationInfoIsMutable() {
            if ((this.bitField0_ & 0x100) == 0) {
                this.authorizationInfo_ = new ArrayList<AuthorizationInfo>(this.authorizationInfo_);
                this.bitField0_ |= 0x100;
            }
        }

        @Override
        public List<AuthorizationInfo> getAuthorizationInfoList() {
            if (this.authorizationInfoBuilder_ == null) {
                return Collections.unmodifiableList(this.authorizationInfo_);
            }
            return this.authorizationInfoBuilder_.getMessageList();
        }

        @Override
        public int getAuthorizationInfoCount() {
            if (this.authorizationInfoBuilder_ == null) {
                return this.authorizationInfo_.size();
            }
            return this.authorizationInfoBuilder_.getCount();
        }

        @Override
        public AuthorizationInfo getAuthorizationInfo(int index) {
            if (this.authorizationInfoBuilder_ == null) {
                return this.authorizationInfo_.get(index);
            }
            return this.authorizationInfoBuilder_.getMessage(index);
        }

        public Builder setAuthorizationInfo(int index, AuthorizationInfo value) {
            if (this.authorizationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.set(index, value);
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setAuthorizationInfo(int index, AuthorizationInfo.Builder builderForValue) {
            if (this.authorizationInfoBuilder_ == null) {
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAuthorizationInfo(AuthorizationInfo value) {
            if (this.authorizationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.add(value);
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addAuthorizationInfo(int index, AuthorizationInfo value) {
            if (this.authorizationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.add(index, value);
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addAuthorizationInfo(AuthorizationInfo.Builder builderForValue) {
            if (this.authorizationInfoBuilder_ == null) {
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addAuthorizationInfo(int index, AuthorizationInfo.Builder builderForValue) {
            if (this.authorizationInfoBuilder_ == null) {
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllAuthorizationInfo(Iterable<? extends AuthorizationInfo> values2) {
            if (this.authorizationInfoBuilder_ == null) {
                this.ensureAuthorizationInfoIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.authorizationInfo_);
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearAuthorizationInfo() {
            if (this.authorizationInfoBuilder_ == null) {
                this.authorizationInfo_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFEFF;
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.clear();
            }
            return this;
        }

        public Builder removeAuthorizationInfo(int index) {
            if (this.authorizationInfoBuilder_ == null) {
                this.ensureAuthorizationInfoIsMutable();
                this.authorizationInfo_.remove(index);
                this.onChanged();
            } else {
                this.authorizationInfoBuilder_.remove(index);
            }
            return this;
        }

        public AuthorizationInfo.Builder getAuthorizationInfoBuilder(int index) {
            return this.getAuthorizationInfoFieldBuilder().getBuilder(index);
        }

        @Override
        public AuthorizationInfoOrBuilder getAuthorizationInfoOrBuilder(int index) {
            if (this.authorizationInfoBuilder_ == null) {
                return this.authorizationInfo_.get(index);
            }
            return this.authorizationInfoBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends AuthorizationInfoOrBuilder> getAuthorizationInfoOrBuilderList() {
            if (this.authorizationInfoBuilder_ != null) {
                return this.authorizationInfoBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.authorizationInfo_);
        }

        public AuthorizationInfo.Builder addAuthorizationInfoBuilder() {
            return this.getAuthorizationInfoFieldBuilder().addBuilder(AuthorizationInfo.getDefaultInstance());
        }

        public AuthorizationInfo.Builder addAuthorizationInfoBuilder(int index) {
            return this.getAuthorizationInfoFieldBuilder().addBuilder(index, AuthorizationInfo.getDefaultInstance());
        }

        public List<AuthorizationInfo.Builder> getAuthorizationInfoBuilderList() {
            return this.getAuthorizationInfoFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<AuthorizationInfo, AuthorizationInfo.Builder, AuthorizationInfoOrBuilder> getAuthorizationInfoFieldBuilder() {
            if (this.authorizationInfoBuilder_ == null) {
                this.authorizationInfoBuilder_ = new RepeatedFieldBuilderV3(this.authorizationInfo_, (this.bitField0_ & 0x100) != 0, this.getParentForChildren(), this.isClean());
                this.authorizationInfo_ = null;
            }
            return this.authorizationInfoBuilder_;
        }

        @Override
        public boolean hasPolicyViolationInfo() {
            return (this.bitField0_ & 0x200) != 0;
        }

        @Override
        public PolicyViolationInfo getPolicyViolationInfo() {
            if (this.policyViolationInfoBuilder_ == null) {
                return this.policyViolationInfo_ == null ? PolicyViolationInfo.getDefaultInstance() : this.policyViolationInfo_;
            }
            return this.policyViolationInfoBuilder_.getMessage();
        }

        public Builder setPolicyViolationInfo(PolicyViolationInfo value) {
            if (this.policyViolationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.policyViolationInfo_ = value;
            } else {
                this.policyViolationInfoBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder setPolicyViolationInfo(PolicyViolationInfo.Builder builderForValue) {
            if (this.policyViolationInfoBuilder_ == null) {
                this.policyViolationInfo_ = builderForValue.build();
            } else {
                this.policyViolationInfoBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder mergePolicyViolationInfo(PolicyViolationInfo value) {
            if (this.policyViolationInfoBuilder_ == null) {
                if ((this.bitField0_ & 0x200) != 0 && this.policyViolationInfo_ != null && this.policyViolationInfo_ != PolicyViolationInfo.getDefaultInstance()) {
                    this.getPolicyViolationInfoBuilder().mergeFrom(value);
                } else {
                    this.policyViolationInfo_ = value;
                }
            } else {
                this.policyViolationInfoBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearPolicyViolationInfo() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.policyViolationInfo_ = null;
            if (this.policyViolationInfoBuilder_ != null) {
                this.policyViolationInfoBuilder_.dispose();
                this.policyViolationInfoBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public PolicyViolationInfo.Builder getPolicyViolationInfoBuilder() {
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this.getPolicyViolationInfoFieldBuilder().getBuilder();
        }

        @Override
        public PolicyViolationInfoOrBuilder getPolicyViolationInfoOrBuilder() {
            if (this.policyViolationInfoBuilder_ != null) {
                return this.policyViolationInfoBuilder_.getMessageOrBuilder();
            }
            return this.policyViolationInfo_ == null ? PolicyViolationInfo.getDefaultInstance() : this.policyViolationInfo_;
        }

        private SingleFieldBuilderV3<PolicyViolationInfo, PolicyViolationInfo.Builder, PolicyViolationInfoOrBuilder> getPolicyViolationInfoFieldBuilder() {
            if (this.policyViolationInfoBuilder_ == null) {
                this.policyViolationInfoBuilder_ = new SingleFieldBuilderV3(this.getPolicyViolationInfo(), this.getParentForChildren(), this.isClean());
                this.policyViolationInfo_ = null;
            }
            return this.policyViolationInfoBuilder_;
        }

        @Override
        public boolean hasRequestMetadata() {
            return (this.bitField0_ & 0x400) != 0;
        }

        @Override
        public RequestMetadata getRequestMetadata() {
            if (this.requestMetadataBuilder_ == null) {
                return this.requestMetadata_ == null ? RequestMetadata.getDefaultInstance() : this.requestMetadata_;
            }
            return this.requestMetadataBuilder_.getMessage();
        }

        public Builder setRequestMetadata(RequestMetadata value) {
            if (this.requestMetadataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.requestMetadata_ = value;
            } else {
                this.requestMetadataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder setRequestMetadata(RequestMetadata.Builder builderForValue) {
            if (this.requestMetadataBuilder_ == null) {
                this.requestMetadata_ = builderForValue.build();
            } else {
                this.requestMetadataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder mergeRequestMetadata(RequestMetadata value) {
            if (this.requestMetadataBuilder_ == null) {
                if ((this.bitField0_ & 0x400) != 0 && this.requestMetadata_ != null && this.requestMetadata_ != RequestMetadata.getDefaultInstance()) {
                    this.getRequestMetadataBuilder().mergeFrom(value);
                } else {
                    this.requestMetadata_ = value;
                }
            } else {
                this.requestMetadataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearRequestMetadata() {
            this.bitField0_ &= 0xFFFFFBFF;
            this.requestMetadata_ = null;
            if (this.requestMetadataBuilder_ != null) {
                this.requestMetadataBuilder_.dispose();
                this.requestMetadataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public RequestMetadata.Builder getRequestMetadataBuilder() {
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this.getRequestMetadataFieldBuilder().getBuilder();
        }

        @Override
        public RequestMetadataOrBuilder getRequestMetadataOrBuilder() {
            if (this.requestMetadataBuilder_ != null) {
                return this.requestMetadataBuilder_.getMessageOrBuilder();
            }
            return this.requestMetadata_ == null ? RequestMetadata.getDefaultInstance() : this.requestMetadata_;
        }

        private SingleFieldBuilderV3<RequestMetadata, RequestMetadata.Builder, RequestMetadataOrBuilder> getRequestMetadataFieldBuilder() {
            if (this.requestMetadataBuilder_ == null) {
                this.requestMetadataBuilder_ = new SingleFieldBuilderV3(this.getRequestMetadata(), this.getParentForChildren(), this.isClean());
                this.requestMetadata_ = null;
            }
            return this.requestMetadataBuilder_;
        }

        @Override
        public boolean hasRequest() {
            return (this.bitField0_ & 0x800) != 0;
        }

        @Override
        public Struct getRequest() {
            if (this.requestBuilder_ == null) {
                return this.request_ == null ? Struct.getDefaultInstance() : this.request_;
            }
            return this.requestBuilder_.getMessage();
        }

        public Builder setRequest(Struct value) {
            if (this.requestBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.request_ = value;
            } else {
                this.requestBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder setRequest(Struct.Builder builderForValue) {
            if (this.requestBuilder_ == null) {
                this.request_ = builderForValue.build();
            } else {
                this.requestBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder mergeRequest(Struct value) {
            if (this.requestBuilder_ == null) {
                if ((this.bitField0_ & 0x800) != 0 && this.request_ != null && this.request_ != Struct.getDefaultInstance()) {
                    this.getRequestBuilder().mergeFrom(value);
                } else {
                    this.request_ = value;
                }
            } else {
                this.requestBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder clearRequest() {
            this.bitField0_ &= 0xFFFFF7FF;
            this.request_ = null;
            if (this.requestBuilder_ != null) {
                this.requestBuilder_.dispose();
                this.requestBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getRequestBuilder() {
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this.getRequestFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getRequestOrBuilder() {
            if (this.requestBuilder_ != null) {
                return this.requestBuilder_.getMessageOrBuilder();
            }
            return this.request_ == null ? Struct.getDefaultInstance() : this.request_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getRequestFieldBuilder() {
            if (this.requestBuilder_ == null) {
                this.requestBuilder_ = new SingleFieldBuilderV3(this.getRequest(), this.getParentForChildren(), this.isClean());
                this.request_ = null;
            }
            return this.requestBuilder_;
        }

        @Override
        public boolean hasResponse() {
            return (this.bitField0_ & 0x1000) != 0;
        }

        @Override
        public Struct getResponse() {
            if (this.responseBuilder_ == null) {
                return this.response_ == null ? Struct.getDefaultInstance() : this.response_;
            }
            return this.responseBuilder_.getMessage();
        }

        public Builder setResponse(Struct value) {
            if (this.responseBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.response_ = value;
            } else {
                this.responseBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder setResponse(Struct.Builder builderForValue) {
            if (this.responseBuilder_ == null) {
                this.response_ = builderForValue.build();
            } else {
                this.responseBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder mergeResponse(Struct value) {
            if (this.responseBuilder_ == null) {
                if ((this.bitField0_ & 0x1000) != 0 && this.response_ != null && this.response_ != Struct.getDefaultInstance()) {
                    this.getResponseBuilder().mergeFrom(value);
                } else {
                    this.response_ = value;
                }
            } else {
                this.responseBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder clearResponse() {
            this.bitField0_ &= 0xFFFFEFFF;
            this.response_ = null;
            if (this.responseBuilder_ != null) {
                this.responseBuilder_.dispose();
                this.responseBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getResponseBuilder() {
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this.getResponseFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getResponseOrBuilder() {
            if (this.responseBuilder_ != null) {
                return this.responseBuilder_.getMessageOrBuilder();
            }
            return this.response_ == null ? Struct.getDefaultInstance() : this.response_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getResponseFieldBuilder() {
            if (this.responseBuilder_ == null) {
                this.responseBuilder_ = new SingleFieldBuilderV3(this.getResponse(), this.getParentForChildren(), this.isClean());
                this.response_ = null;
            }
            return this.responseBuilder_;
        }

        @Override
        public boolean hasMetadata() {
            return (this.bitField0_ & 0x2000) != 0;
        }

        @Override
        public Struct getMetadata() {
            if (this.metadataBuilder_ == null) {
                return this.metadata_ == null ? Struct.getDefaultInstance() : this.metadata_;
            }
            return this.metadataBuilder_.getMessage();
        }

        public Builder setMetadata(Struct value) {
            if (this.metadataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.metadata_ = value;
            } else {
                this.metadataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder setMetadata(Struct.Builder builderForValue) {
            if (this.metadataBuilder_ == null) {
                this.metadata_ = builderForValue.build();
            } else {
                this.metadataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder mergeMetadata(Struct value) {
            if (this.metadataBuilder_ == null) {
                if ((this.bitField0_ & 0x2000) != 0 && this.metadata_ != null && this.metadata_ != Struct.getDefaultInstance()) {
                    this.getMetadataBuilder().mergeFrom(value);
                } else {
                    this.metadata_ = value;
                }
            } else {
                this.metadataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder clearMetadata() {
            this.bitField0_ &= 0xFFFFDFFF;
            this.metadata_ = null;
            if (this.metadataBuilder_ != null) {
                this.metadataBuilder_.dispose();
                this.metadataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getMetadataBuilder() {
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this.getMetadataFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getMetadataOrBuilder() {
            if (this.metadataBuilder_ != null) {
                return this.metadataBuilder_.getMessageOrBuilder();
            }
            return this.metadata_ == null ? Struct.getDefaultInstance() : this.metadata_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getMetadataFieldBuilder() {
            if (this.metadataBuilder_ == null) {
                this.metadataBuilder_ = new SingleFieldBuilderV3(this.getMetadata(), this.getParentForChildren(), this.isClean());
                this.metadata_ = null;
            }
            return this.metadataBuilder_;
        }

        @Override
        @Deprecated
        public boolean hasServiceData() {
            return (this.bitField0_ & 0x4000) != 0;
        }

        @Override
        @Deprecated
        public Any getServiceData() {
            if (this.serviceDataBuilder_ == null) {
                return this.serviceData_ == null ? Any.getDefaultInstance() : this.serviceData_;
            }
            return this.serviceDataBuilder_.getMessage();
        }

        @Deprecated
        public Builder setServiceData(Any value) {
            if (this.serviceDataBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.serviceData_ = value;
            } else {
                this.serviceDataBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x4000;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder setServiceData(Any.Builder builderForValue) {
            if (this.serviceDataBuilder_ == null) {
                this.serviceData_ = builderForValue.build();
            } else {
                this.serviceDataBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x4000;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder mergeServiceData(Any value) {
            if (this.serviceDataBuilder_ == null) {
                if ((this.bitField0_ & 0x4000) != 0 && this.serviceData_ != null && this.serviceData_ != Any.getDefaultInstance()) {
                    this.getServiceDataBuilder().mergeFrom(value);
                } else {
                    this.serviceData_ = value;
                }
            } else {
                this.serviceDataBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x4000;
            this.onChanged();
            return this;
        }

        @Deprecated
        public Builder clearServiceData() {
            this.bitField0_ &= 0xFFFFBFFF;
            this.serviceData_ = null;
            if (this.serviceDataBuilder_ != null) {
                this.serviceDataBuilder_.dispose();
                this.serviceDataBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        @Deprecated
        public Any.Builder getServiceDataBuilder() {
            this.bitField0_ |= 0x4000;
            this.onChanged();
            return this.getServiceDataFieldBuilder().getBuilder();
        }

        @Override
        @Deprecated
        public AnyOrBuilder getServiceDataOrBuilder() {
            if (this.serviceDataBuilder_ != null) {
                return this.serviceDataBuilder_.getMessageOrBuilder();
            }
            return this.serviceData_ == null ? Any.getDefaultInstance() : this.serviceData_;
        }

        private SingleFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getServiceDataFieldBuilder() {
            if (this.serviceDataBuilder_ == null) {
                this.serviceDataBuilder_ = new SingleFieldBuilderV3(this.getServiceData(), this.getParentForChildren(), this.isClean());
                this.serviceData_ = null;
            }
            return this.serviceDataBuilder_;
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

