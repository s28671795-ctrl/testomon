/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.LabelDescriptor;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.api.LaunchStage;
import com.google.api.MonitoredResourceDescriptorOrBuilder;
import com.google.api.MonitoredResourceProto;
import com.google.protobuf.AbstractMessageLite;
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
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MonitoredResourceDescriptor
extends GeneratedMessageV3
implements MonitoredResourceDescriptorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 5;
    private volatile Object name_ = "";
    public static final int TYPE_FIELD_NUMBER = 1;
    private volatile Object type_ = "";
    public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
    private volatile Object displayName_ = "";
    public static final int DESCRIPTION_FIELD_NUMBER = 3;
    private volatile Object description_ = "";
    public static final int LABELS_FIELD_NUMBER = 4;
    private List<LabelDescriptor> labels_;
    public static final int LAUNCH_STAGE_FIELD_NUMBER = 7;
    private int launchStage_ = 0;
    private byte memoizedIsInitialized = (byte)-1;
    private static final MonitoredResourceDescriptor DEFAULT_INSTANCE = new MonitoredResourceDescriptor();
    private static final Parser<MonitoredResourceDescriptor> PARSER = new AbstractParser<MonitoredResourceDescriptor>(){

        @Override
        public MonitoredResourceDescriptor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = MonitoredResourceDescriptor.newBuilder();
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

    private MonitoredResourceDescriptor(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private MonitoredResourceDescriptor() {
        this.name_ = "";
        this.type_ = "";
        this.displayName_ = "";
        this.description_ = "";
        this.labels_ = Collections.emptyList();
        this.launchStage_ = 0;
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new MonitoredResourceDescriptor();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResourceDescriptor_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MonitoredResourceProto.internal_static_google_api_MonitoredResourceDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResourceDescriptor.class, Builder.class);
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
    public String getDescription() {
        Object ref = this.description_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.description_ = s2;
        return s2;
    }

    @Override
    public ByteString getDescriptionBytes() {
        Object ref = this.description_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.description_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<LabelDescriptor> getLabelsList() {
        return this.labels_;
    }

    @Override
    public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
        return this.labels_;
    }

    @Override
    public int getLabelsCount() {
        return this.labels_.size();
    }

    @Override
    public LabelDescriptor getLabels(int index) {
        return this.labels_.get(index);
    }

    @Override
    public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
        return this.labels_.get(index);
    }

    @Override
    public int getLaunchStageValue() {
        return this.launchStage_;
    }

    @Override
    public LaunchStage getLaunchStage() {
        LaunchStage result = LaunchStage.forNumber(this.launchStage_);
        return result == null ? LaunchStage.UNRECOGNIZED : result;
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            GeneratedMessageV3.writeString(output, 1, this.type_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            GeneratedMessageV3.writeString(output, 2, this.displayName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 3, this.description_);
        }
        for (int i = 0; i < this.labels_.size(); ++i) {
            output.writeMessage(4, this.labels_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 5, this.name_);
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            output.writeEnum(7, this.launchStage_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.type_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.displayName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.description_);
        }
        for (int i = 0; i < this.labels_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.labels_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.name_);
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(7, this.launchStage_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MonitoredResourceDescriptor)) {
            return super.equals(obj);
        }
        MonitoredResourceDescriptor other = (MonitoredResourceDescriptor)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.getDisplayName().equals(other.getDisplayName())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
            return false;
        }
        if (!this.getLabelsList().equals(other.getLabelsList())) {
            return false;
        }
        if (this.launchStage_ != other.launchStage_) {
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
        hash = 19 * hash + MonitoredResourceDescriptor.getDescriptor().hashCode();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getType().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getDisplayName().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDescription().hashCode();
        if (this.getLabelsCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getLabelsList().hashCode();
        }
        hash = 37 * hash + 7;
        hash = 53 * hash + this.launchStage_;
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static MonitoredResourceDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static MonitoredResourceDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static MonitoredResourceDescriptor parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResourceDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResourceDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static MonitoredResourceDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static MonitoredResourceDescriptor parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static MonitoredResourceDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return MonitoredResourceDescriptor.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MonitoredResourceDescriptor prototype) {
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

    public static MonitoredResourceDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MonitoredResourceDescriptor> parser() {
        return PARSER;
    }

    public Parser<MonitoredResourceDescriptor> getParserForType() {
        return PARSER;
    }

    @Override
    public MonitoredResourceDescriptor getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MonitoredResourceDescriptorOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object type_ = "";
        private Object displayName_ = "";
        private Object description_ = "";
        private List<LabelDescriptor> labels_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> labelsBuilder_;
        private int launchStage_ = 0;

        public static final Descriptors.Descriptor getDescriptor() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceDescriptor_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoredResourceDescriptor.class, Builder.class);
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
            this.name_ = "";
            this.type_ = "";
            this.displayName_ = "";
            this.description_ = "";
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
            } else {
                this.labels_ = null;
                this.labelsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFEF;
            this.launchStage_ = 0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MonitoredResourceProto.internal_static_google_api_MonitoredResourceDescriptor_descriptor;
        }

        @Override
        public MonitoredResourceDescriptor getDefaultInstanceForType() {
            return MonitoredResourceDescriptor.getDefaultInstance();
        }

        @Override
        public MonitoredResourceDescriptor build() {
            MonitoredResourceDescriptor result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public MonitoredResourceDescriptor buildPartial() {
            MonitoredResourceDescriptor result = new MonitoredResourceDescriptor(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(MonitoredResourceDescriptor result) {
            if (this.labelsBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0) {
                    this.labels_ = Collections.unmodifiableList(this.labels_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result.labels_ = this.labels_;
            } else {
                result.labels_ = this.labelsBuilder_.build();
            }
        }

        private void buildPartial0(MonitoredResourceDescriptor result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.type_ = this.type_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.displayName_ = this.displayName_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.description_ = this.description_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.launchStage_ = this.launchStage_;
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
            if (other instanceof MonitoredResourceDescriptor) {
                return this.mergeFrom((MonitoredResourceDescriptor)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(MonitoredResourceDescriptor other) {
            if (other == MonitoredResourceDescriptor.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getType().isEmpty()) {
                this.type_ = other.type_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getDisplayName().isEmpty()) {
                this.displayName_ = other.displayName_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (this.labelsBuilder_ == null) {
                if (!other.labels_.isEmpty()) {
                    if (this.labels_.isEmpty()) {
                        this.labels_ = other.labels_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureLabelsIsMutable();
                        this.labels_.addAll(other.labels_);
                    }
                    this.onChanged();
                }
            } else if (!other.labels_.isEmpty()) {
                if (this.labelsBuilder_.isEmpty()) {
                    this.labelsBuilder_.dispose();
                    this.labelsBuilder_ = null;
                    this.labels_ = other.labels_;
                    this.bitField0_ &= 0xFFFFFFEF;
                    this.labelsBuilder_ = alwaysUseFieldBuilders ? this.getLabelsFieldBuilder() : null;
                } else {
                    this.labelsBuilder_.addAllMessages(other.labels_);
                }
            }
            if (other.launchStage_ != 0) {
                this.setLaunchStageValue(other.getLaunchStageValue());
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
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            this.type_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block14;
                        }
                        case 18: {
                            this.displayName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block14;
                        }
                        case 26: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block14;
                        }
                        case 34: {
                            LabelDescriptor m4 = input.readMessage(LabelDescriptor.parser(), extensionRegistry);
                            if (this.labelsBuilder_ == null) {
                                this.ensureLabelsIsMutable();
                                this.labels_.add(m4);
                                continue block14;
                            }
                            this.labelsBuilder_.addMessage(m4);
                            continue block14;
                        }
                        case 42: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block14;
                        }
                        case 56: {
                            this.launchStage_ = input.readEnum();
                            this.bitField0_ |= 0x20;
                            continue block14;
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
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = MonitoredResourceDescriptor.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MonitoredResourceDescriptor.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
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
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearType() {
            this.type_ = MonitoredResourceDescriptor.getDefaultInstance().getType();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MonitoredResourceDescriptor.checkByteStringIsUtf8(value);
            this.type_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
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
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDisplayName() {
            this.displayName_ = MonitoredResourceDescriptor.getDefaultInstance().getDisplayName();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MonitoredResourceDescriptor.checkByteStringIsUtf8(value);
            this.displayName_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getDescription() {
            Object ref = this.description_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.description_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDescriptionBytes() {
            Object ref = this.description_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.description_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDescription(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.description_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = MonitoredResourceDescriptor.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            MonitoredResourceDescriptor.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        private void ensureLabelsIsMutable() {
            if ((this.bitField0_ & 0x10) == 0) {
                this.labels_ = new ArrayList<LabelDescriptor>(this.labels_);
                this.bitField0_ |= 0x10;
            }
        }

        @Override
        public List<LabelDescriptor> getLabelsList() {
            if (this.labelsBuilder_ == null) {
                return Collections.unmodifiableList(this.labels_);
            }
            return this.labelsBuilder_.getMessageList();
        }

        @Override
        public int getLabelsCount() {
            if (this.labelsBuilder_ == null) {
                return this.labels_.size();
            }
            return this.labelsBuilder_.getCount();
        }

        @Override
        public LabelDescriptor getLabels(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessage(index);
        }

        public Builder setLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.set(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor value) {
            if (this.labelsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLabelsIsMutable();
                this.labels_.add(index, value);
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLabels(LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLabels(int index, LabelDescriptor.Builder builderForValue) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.labelsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLabels(Iterable<? extends LabelDescriptor> values2) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.labels_);
                this.onChanged();
            } else {
                this.labelsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLabels() {
            if (this.labelsBuilder_ == null) {
                this.labels_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
            } else {
                this.labelsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLabels(int index) {
            if (this.labelsBuilder_ == null) {
                this.ensureLabelsIsMutable();
                this.labels_.remove(index);
                this.onChanged();
            } else {
                this.labelsBuilder_.remove(index);
            }
            return this;
        }

        public LabelDescriptor.Builder getLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().getBuilder(index);
        }

        @Override
        public LabelDescriptorOrBuilder getLabelsOrBuilder(int index) {
            if (this.labelsBuilder_ == null) {
                return this.labels_.get(index);
            }
            return this.labelsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends LabelDescriptorOrBuilder> getLabelsOrBuilderList() {
            if (this.labelsBuilder_ != null) {
                return this.labelsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.labels_);
        }

        public LabelDescriptor.Builder addLabelsBuilder() {
            return this.getLabelsFieldBuilder().addBuilder(LabelDescriptor.getDefaultInstance());
        }

        public LabelDescriptor.Builder addLabelsBuilder(int index) {
            return this.getLabelsFieldBuilder().addBuilder(index, LabelDescriptor.getDefaultInstance());
        }

        public List<LabelDescriptor.Builder> getLabelsBuilderList() {
            return this.getLabelsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<LabelDescriptor, LabelDescriptor.Builder, LabelDescriptorOrBuilder> getLabelsFieldBuilder() {
            if (this.labelsBuilder_ == null) {
                this.labelsBuilder_ = new RepeatedFieldBuilderV3(this.labels_, (this.bitField0_ & 0x10) != 0, this.getParentForChildren(), this.isClean());
                this.labels_ = null;
            }
            return this.labelsBuilder_;
        }

        @Override
        public int getLaunchStageValue() {
            return this.launchStage_;
        }

        public Builder setLaunchStageValue(int value) {
            this.launchStage_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public LaunchStage getLaunchStage() {
            LaunchStage result = LaunchStage.forNumber(this.launchStage_);
            return result == null ? LaunchStage.UNRECOGNIZED : result;
        }

        public Builder setLaunchStage(LaunchStage value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 0x20;
            this.launchStage_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearLaunchStage() {
            this.bitField0_ &= 0xFFFFFFDF;
            this.launchStage_ = 0;
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

