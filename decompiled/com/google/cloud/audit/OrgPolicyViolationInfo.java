/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.audit;

import com.google.cloud.audit.AuditLogProto;
import com.google.cloud.audit.OrgPolicyViolationInfoOrBuilder;
import com.google.cloud.audit.ViolationInfo;
import com.google.cloud.audit.ViolationInfoOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapField;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Struct;
import com.google.protobuf.StructOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class OrgPolicyViolationInfo
extends GeneratedMessageV3
implements OrgPolicyViolationInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int PAYLOAD_FIELD_NUMBER = 1;
    private Struct payload_;
    public static final int RESOURCE_TYPE_FIELD_NUMBER = 2;
    private volatile Object resourceType_ = "";
    public static final int RESOURCE_TAGS_FIELD_NUMBER = 3;
    private MapField<String, String> resourceTags_;
    public static final int VIOLATION_INFO_FIELD_NUMBER = 4;
    private List<ViolationInfo> violationInfo_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final OrgPolicyViolationInfo DEFAULT_INSTANCE = new OrgPolicyViolationInfo();
    private static final Parser<OrgPolicyViolationInfo> PARSER = new AbstractParser<OrgPolicyViolationInfo>(){

        @Override
        public OrgPolicyViolationInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = OrgPolicyViolationInfo.newBuilder();
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

    private OrgPolicyViolationInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private OrgPolicyViolationInfo() {
        this.resourceType_ = "";
        this.violationInfo_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new OrgPolicyViolationInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor;
    }

    @Override
    protected MapField internalGetMapField(int number) {
        switch (number) {
            case 3: {
                return this.internalGetResourceTags();
            }
        }
        throw new RuntimeException("Invalid map field number: " + number);
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(OrgPolicyViolationInfo.class, Builder.class);
    }

    @Override
    public boolean hasPayload() {
        return this.payload_ != null;
    }

    @Override
    public Struct getPayload() {
        return this.payload_ == null ? Struct.getDefaultInstance() : this.payload_;
    }

    @Override
    public StructOrBuilder getPayloadOrBuilder() {
        return this.payload_ == null ? Struct.getDefaultInstance() : this.payload_;
    }

    @Override
    public String getResourceType() {
        Object ref = this.resourceType_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.resourceType_ = s2;
        return s2;
    }

    @Override
    public ByteString getResourceTypeBytes() {
        Object ref = this.resourceType_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.resourceType_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    private MapField<String, String> internalGetResourceTags() {
        if (this.resourceTags_ == null) {
            return MapField.emptyMapField(ResourceTagsDefaultEntryHolder.defaultEntry);
        }
        return this.resourceTags_;
    }

    @Override
    public int getResourceTagsCount() {
        return this.internalGetResourceTags().getMap().size();
    }

    @Override
    public boolean containsResourceTags(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        return this.internalGetResourceTags().getMap().containsKey(key);
    }

    @Override
    @Deprecated
    public Map<String, String> getResourceTags() {
        return this.getResourceTagsMap();
    }

    @Override
    public Map<String, String> getResourceTagsMap() {
        return this.internalGetResourceTags().getMap();
    }

    @Override
    public String getResourceTagsOrDefault(String key, String defaultValue) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetResourceTags().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    @Override
    public String getResourceTagsOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException("map key");
        }
        Map<String, String> map = this.internalGetResourceTags().getMap();
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return map.get(key);
    }

    @Override
    public List<ViolationInfo> getViolationInfoList() {
        return this.violationInfo_;
    }

    @Override
    public List<? extends ViolationInfoOrBuilder> getViolationInfoOrBuilderList() {
        return this.violationInfo_;
    }

    @Override
    public int getViolationInfoCount() {
        return this.violationInfo_.size();
    }

    @Override
    public ViolationInfo getViolationInfo(int index) {
        return this.violationInfo_.get(index);
    }

    @Override
    public ViolationInfoOrBuilder getViolationInfoOrBuilder(int index) {
        return this.violationInfo_.get(index);
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
        if (this.payload_ != null) {
            output.writeMessage(1, this.getPayload());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            GeneratedMessageV3.writeString(output, 2, this.resourceType_);
        }
        GeneratedMessageV3.serializeStringMapTo(output, this.internalGetResourceTags(), ResourceTagsDefaultEntryHolder.defaultEntry, 3);
        for (int i = 0; i < this.violationInfo_.size(); ++i) {
            output.writeMessage(4, this.violationInfo_.get(i));
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
        if (this.payload_ != null) {
            size += CodedOutputStream.computeMessageSize(1, this.getPayload());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.resourceType_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.resourceType_);
        }
        for (Map.Entry<String, String> entry : this.internalGetResourceTags().getMap().entrySet()) {
            Message resourceTags__ = ((MapEntry.Builder)ResourceTagsDefaultEntryHolder.defaultEntry.newBuilderForType()).setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += CodedOutputStream.computeMessageSize(3, resourceTags__);
        }
        for (int i = 0; i < this.violationInfo_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.violationInfo_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrgPolicyViolationInfo)) {
            return super.equals(obj);
        }
        OrgPolicyViolationInfo other = (OrgPolicyViolationInfo)obj;
        if (this.hasPayload() != other.hasPayload()) {
            return false;
        }
        if (this.hasPayload() && !this.getPayload().equals(other.getPayload())) {
            return false;
        }
        if (!this.getResourceType().equals(other.getResourceType())) {
            return false;
        }
        if (!this.internalGetResourceTags().equals(other.internalGetResourceTags())) {
            return false;
        }
        if (!this.getViolationInfoList().equals(other.getViolationInfoList())) {
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
        hash = 19 * hash + OrgPolicyViolationInfo.getDescriptor().hashCode();
        if (this.hasPayload()) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPayload().hashCode();
        }
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getResourceType().hashCode();
        if (!this.internalGetResourceTags().getMap().isEmpty()) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.internalGetResourceTags().hashCode();
        }
        if (this.getViolationInfoCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getViolationInfoList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static OrgPolicyViolationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OrgPolicyViolationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OrgPolicyViolationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OrgPolicyViolationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OrgPolicyViolationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static OrgPolicyViolationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static OrgPolicyViolationInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static OrgPolicyViolationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static OrgPolicyViolationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static OrgPolicyViolationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static OrgPolicyViolationInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static OrgPolicyViolationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return OrgPolicyViolationInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(OrgPolicyViolationInfo prototype) {
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

    public static OrgPolicyViolationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<OrgPolicyViolationInfo> parser() {
        return PARSER;
    }

    public Parser<OrgPolicyViolationInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public OrgPolicyViolationInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements OrgPolicyViolationInfoOrBuilder {
        private int bitField0_;
        private Struct payload_;
        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> payloadBuilder_;
        private Object resourceType_ = "";
        private MapField<String, String> resourceTags_;
        private List<ViolationInfo> violationInfo_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<ViolationInfo, ViolationInfo.Builder, ViolationInfoOrBuilder> violationInfoBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor;
        }

        @Override
        protected MapField internalGetMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetResourceTags();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 3: {
                    return this.internalGetMutableResourceTags();
                }
            }
            throw new RuntimeException("Invalid map field number: " + number);
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(OrgPolicyViolationInfo.class, Builder.class);
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
            this.payload_ = null;
            if (this.payloadBuilder_ != null) {
                this.payloadBuilder_.dispose();
                this.payloadBuilder_ = null;
            }
            this.resourceType_ = "";
            this.internalGetMutableResourceTags().clear();
            if (this.violationInfoBuilder_ == null) {
                this.violationInfo_ = Collections.emptyList();
            } else {
                this.violationInfo_ = null;
                this.violationInfoBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFF7;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_descriptor;
        }

        @Override
        public OrgPolicyViolationInfo getDefaultInstanceForType() {
            return OrgPolicyViolationInfo.getDefaultInstance();
        }

        @Override
        public OrgPolicyViolationInfo build() {
            OrgPolicyViolationInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public OrgPolicyViolationInfo buildPartial() {
            OrgPolicyViolationInfo result = new OrgPolicyViolationInfo(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(OrgPolicyViolationInfo result) {
            if (this.violationInfoBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0) {
                    this.violationInfo_ = Collections.unmodifiableList(this.violationInfo_);
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result.violationInfo_ = this.violationInfo_;
            } else {
                result.violationInfo_ = this.violationInfoBuilder_.build();
            }
        }

        private void buildPartial0(OrgPolicyViolationInfo result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.payload_ = this.payloadBuilder_ == null ? this.payload_ : this.payloadBuilder_.build();
            }
            if ((from_bitField0_ & 2) != 0) {
                result.resourceType_ = this.resourceType_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.resourceTags_ = this.internalGetResourceTags();
                result.resourceTags_.makeImmutable();
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
            if (other instanceof OrgPolicyViolationInfo) {
                return this.mergeFrom((OrgPolicyViolationInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(OrgPolicyViolationInfo other) {
            if (other == OrgPolicyViolationInfo.getDefaultInstance()) {
                return this;
            }
            if (other.hasPayload()) {
                this.mergePayload(other.getPayload());
            }
            if (!other.getResourceType().isEmpty()) {
                this.resourceType_ = other.resourceType_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            this.internalGetMutableResourceTags().mergeFrom(other.internalGetResourceTags());
            this.bitField0_ |= 4;
            if (this.violationInfoBuilder_ == null) {
                if (!other.violationInfo_.isEmpty()) {
                    if (this.violationInfo_.isEmpty()) {
                        this.violationInfo_ = other.violationInfo_;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureViolationInfoIsMutable();
                        this.violationInfo_.addAll(other.violationInfo_);
                    }
                    this.onChanged();
                }
            } else if (!other.violationInfo_.isEmpty()) {
                if (this.violationInfoBuilder_.isEmpty()) {
                    this.violationInfoBuilder_.dispose();
                    this.violationInfoBuilder_ = null;
                    this.violationInfo_ = other.violationInfo_;
                    this.bitField0_ &= 0xFFFFFFF7;
                    this.violationInfoBuilder_ = alwaysUseFieldBuilders ? this.getViolationInfoFieldBuilder() : null;
                } else {
                    this.violationInfoBuilder_.addAllMessages(other.violationInfo_);
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
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            input.readMessage(this.getPayloadFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            this.resourceType_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 26: {
                            MapEntry<String, String> resourceTags__ = input.readMessage(ResourceTagsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            this.internalGetMutableResourceTags().getMutableMap().put(resourceTags__.getKey(), resourceTags__.getValue());
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 34: {
                            ViolationInfo m4 = input.readMessage(ViolationInfo.parser(), extensionRegistry);
                            if (this.violationInfoBuilder_ == null) {
                                this.ensureViolationInfoIsMutable();
                                this.violationInfo_.add(m4);
                                continue block12;
                            }
                            this.violationInfoBuilder_.addMessage(m4);
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
        public boolean hasPayload() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override
        public Struct getPayload() {
            if (this.payloadBuilder_ == null) {
                return this.payload_ == null ? Struct.getDefaultInstance() : this.payload_;
            }
            return this.payloadBuilder_.getMessage();
        }

        public Builder setPayload(Struct value) {
            if (this.payloadBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.payload_ = value;
            } else {
                this.payloadBuilder_.setMessage(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder setPayload(Struct.Builder builderForValue) {
            if (this.payloadBuilder_ == null) {
                this.payload_ = builderForValue.build();
            } else {
                this.payloadBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder mergePayload(Struct value) {
            if (this.payloadBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0 && this.payload_ != null && this.payload_ != Struct.getDefaultInstance()) {
                    this.getPayloadBuilder().mergeFrom(value);
                } else {
                    this.payload_ = value;
                }
            } else {
                this.payloadBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearPayload() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.payload_ = null;
            if (this.payloadBuilder_ != null) {
                this.payloadBuilder_.dispose();
                this.payloadBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Struct.Builder getPayloadBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return this.getPayloadFieldBuilder().getBuilder();
        }

        @Override
        public StructOrBuilder getPayloadOrBuilder() {
            if (this.payloadBuilder_ != null) {
                return this.payloadBuilder_.getMessageOrBuilder();
            }
            return this.payload_ == null ? Struct.getDefaultInstance() : this.payload_;
        }

        private SingleFieldBuilderV3<Struct, Struct.Builder, StructOrBuilder> getPayloadFieldBuilder() {
            if (this.payloadBuilder_ == null) {
                this.payloadBuilder_ = new SingleFieldBuilderV3(this.getPayload(), this.getParentForChildren(), this.isClean());
                this.payload_ = null;
            }
            return this.payloadBuilder_;
        }

        @Override
        public String getResourceType() {
            Object ref = this.resourceType_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.resourceType_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResourceTypeBytes() {
            Object ref = this.resourceType_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.resourceType_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResourceType(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.resourceType_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearResourceType() {
            this.resourceType_ = OrgPolicyViolationInfo.getDefaultInstance().getResourceType();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setResourceTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            OrgPolicyViolationInfo.checkByteStringIsUtf8(value);
            this.resourceType_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        private MapField<String, String> internalGetResourceTags() {
            if (this.resourceTags_ == null) {
                return MapField.emptyMapField(ResourceTagsDefaultEntryHolder.defaultEntry);
            }
            return this.resourceTags_;
        }

        private MapField<String, String> internalGetMutableResourceTags() {
            if (this.resourceTags_ == null) {
                this.resourceTags_ = MapField.newMapField(ResourceTagsDefaultEntryHolder.defaultEntry);
            }
            if (!this.resourceTags_.isMutable()) {
                this.resourceTags_ = this.resourceTags_.copy();
            }
            this.bitField0_ |= 4;
            this.onChanged();
            return this.resourceTags_;
        }

        @Override
        public int getResourceTagsCount() {
            return this.internalGetResourceTags().getMap().size();
        }

        @Override
        public boolean containsResourceTags(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            return this.internalGetResourceTags().getMap().containsKey(key);
        }

        @Override
        @Deprecated
        public Map<String, String> getResourceTags() {
            return this.getResourceTagsMap();
        }

        @Override
        public Map<String, String> getResourceTagsMap() {
            return this.internalGetResourceTags().getMap();
        }

        @Override
        public String getResourceTagsOrDefault(String key, String defaultValue) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetResourceTags().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        @Override
        public String getResourceTagsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            Map<String, String> map = this.internalGetResourceTags().getMap();
            if (!map.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearResourceTags() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.internalGetMutableResourceTags().getMutableMap().clear();
            return this;
        }

        public Builder removeResourceTags(String key) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            this.internalGetMutableResourceTags().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, String> getMutableResourceTags() {
            this.bitField0_ |= 4;
            return this.internalGetMutableResourceTags().getMutableMap();
        }

        public Builder putResourceTags(String key, String value) {
            if (key == null) {
                throw new NullPointerException("map key");
            }
            if (value == null) {
                throw new NullPointerException("map value");
            }
            this.internalGetMutableResourceTags().getMutableMap().put(key, value);
            this.bitField0_ |= 4;
            return this;
        }

        public Builder putAllResourceTags(Map<String, String> values2) {
            this.internalGetMutableResourceTags().getMutableMap().putAll(values2);
            this.bitField0_ |= 4;
            return this;
        }

        private void ensureViolationInfoIsMutable() {
            if ((this.bitField0_ & 8) == 0) {
                this.violationInfo_ = new ArrayList<ViolationInfo>(this.violationInfo_);
                this.bitField0_ |= 8;
            }
        }

        @Override
        public List<ViolationInfo> getViolationInfoList() {
            if (this.violationInfoBuilder_ == null) {
                return Collections.unmodifiableList(this.violationInfo_);
            }
            return this.violationInfoBuilder_.getMessageList();
        }

        @Override
        public int getViolationInfoCount() {
            if (this.violationInfoBuilder_ == null) {
                return this.violationInfo_.size();
            }
            return this.violationInfoBuilder_.getCount();
        }

        @Override
        public ViolationInfo getViolationInfo(int index) {
            if (this.violationInfoBuilder_ == null) {
                return this.violationInfo_.get(index);
            }
            return this.violationInfoBuilder_.getMessage(index);
        }

        public Builder setViolationInfo(int index, ViolationInfo value) {
            if (this.violationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.set(index, value);
                this.onChanged();
            } else {
                this.violationInfoBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setViolationInfo(int index, ViolationInfo.Builder builderForValue) {
            if (this.violationInfoBuilder_ == null) {
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.violationInfoBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addViolationInfo(ViolationInfo value) {
            if (this.violationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.add(value);
                this.onChanged();
            } else {
                this.violationInfoBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addViolationInfo(int index, ViolationInfo value) {
            if (this.violationInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.add(index, value);
                this.onChanged();
            } else {
                this.violationInfoBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addViolationInfo(ViolationInfo.Builder builderForValue) {
            if (this.violationInfoBuilder_ == null) {
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.violationInfoBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addViolationInfo(int index, ViolationInfo.Builder builderForValue) {
            if (this.violationInfoBuilder_ == null) {
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.violationInfoBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllViolationInfo(Iterable<? extends ViolationInfo> values2) {
            if (this.violationInfoBuilder_ == null) {
                this.ensureViolationInfoIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.violationInfo_);
                this.onChanged();
            } else {
                this.violationInfoBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearViolationInfo() {
            if (this.violationInfoBuilder_ == null) {
                this.violationInfo_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
            } else {
                this.violationInfoBuilder_.clear();
            }
            return this;
        }

        public Builder removeViolationInfo(int index) {
            if (this.violationInfoBuilder_ == null) {
                this.ensureViolationInfoIsMutable();
                this.violationInfo_.remove(index);
                this.onChanged();
            } else {
                this.violationInfoBuilder_.remove(index);
            }
            return this;
        }

        public ViolationInfo.Builder getViolationInfoBuilder(int index) {
            return this.getViolationInfoFieldBuilder().getBuilder(index);
        }

        @Override
        public ViolationInfoOrBuilder getViolationInfoOrBuilder(int index) {
            if (this.violationInfoBuilder_ == null) {
                return this.violationInfo_.get(index);
            }
            return this.violationInfoBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends ViolationInfoOrBuilder> getViolationInfoOrBuilderList() {
            if (this.violationInfoBuilder_ != null) {
                return this.violationInfoBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.violationInfo_);
        }

        public ViolationInfo.Builder addViolationInfoBuilder() {
            return this.getViolationInfoFieldBuilder().addBuilder(ViolationInfo.getDefaultInstance());
        }

        public ViolationInfo.Builder addViolationInfoBuilder(int index) {
            return this.getViolationInfoFieldBuilder().addBuilder(index, ViolationInfo.getDefaultInstance());
        }

        public List<ViolationInfo.Builder> getViolationInfoBuilderList() {
            return this.getViolationInfoFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<ViolationInfo, ViolationInfo.Builder, ViolationInfoOrBuilder> getViolationInfoFieldBuilder() {
            if (this.violationInfoBuilder_ == null) {
                this.violationInfoBuilder_ = new RepeatedFieldBuilderV3(this.violationInfo_, (this.bitField0_ & 8) != 0, this.getParentForChildren(), this.isClean());
                this.violationInfo_ = null;
            }
            return this.violationInfoBuilder_;
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

    private static final class ResourceTagsDefaultEntryHolder {
        static final MapEntry<String, String> defaultEntry = MapEntry.newDefaultInstance(AuditLogProto.internal_static_google_cloud_audit_OrgPolicyViolationInfo_ResourceTagsEntry_descriptor, WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");

        private ResourceTagsDefaultEntryHolder() {
        }
    }
}

