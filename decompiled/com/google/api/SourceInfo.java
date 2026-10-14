/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.SourceInfoOrBuilder;
import com.google.api.SourceInfoProto;
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

public final class SourceInfo
extends GeneratedMessageV3
implements SourceInfoOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SOURCE_FILES_FIELD_NUMBER = 1;
    private List<Any> sourceFiles_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final SourceInfo DEFAULT_INSTANCE = new SourceInfo();
    private static final Parser<SourceInfo> PARSER = new AbstractParser<SourceInfo>(){

        @Override
        public SourceInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = SourceInfo.newBuilder();
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

    private SourceInfo(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private SourceInfo() {
        this.sourceFiles_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new SourceInfo();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return SourceInfoProto.internal_static_google_api_SourceInfo_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return SourceInfoProto.internal_static_google_api_SourceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceInfo.class, Builder.class);
    }

    @Override
    public List<Any> getSourceFilesList() {
        return this.sourceFiles_;
    }

    @Override
    public List<? extends AnyOrBuilder> getSourceFilesOrBuilderList() {
        return this.sourceFiles_;
    }

    @Override
    public int getSourceFilesCount() {
        return this.sourceFiles_.size();
    }

    @Override
    public Any getSourceFiles(int index) {
        return this.sourceFiles_.get(index);
    }

    @Override
    public AnyOrBuilder getSourceFilesOrBuilder(int index) {
        return this.sourceFiles_.get(index);
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
        for (int i = 0; i < this.sourceFiles_.size(); ++i) {
            output.writeMessage(1, this.sourceFiles_.get(i));
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
        for (int i = 0; i < this.sourceFiles_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.sourceFiles_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SourceInfo)) {
            return super.equals(obj);
        }
        SourceInfo other = (SourceInfo)obj;
        if (!this.getSourceFilesList().equals(other.getSourceFilesList())) {
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
        hash = 19 * hash + SourceInfo.getDescriptor().hashCode();
        if (this.getSourceFilesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getSourceFilesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static SourceInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SourceInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SourceInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static SourceInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceInfo parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SourceInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static SourceInfo parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static SourceInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static SourceInfo parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SourceInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return SourceInfo.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(SourceInfo prototype) {
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

    public static SourceInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<SourceInfo> parser() {
        return PARSER;
    }

    public Parser<SourceInfo> getParserForType() {
        return PARSER;
    }

    @Override
    public SourceInfo getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements SourceInfoOrBuilder {
        private int bitField0_;
        private List<Any> sourceFiles_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> sourceFilesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return SourceInfoProto.internal_static_google_api_SourceInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return SourceInfoProto.internal_static_google_api_SourceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceInfo.class, Builder.class);
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
            if (this.sourceFilesBuilder_ == null) {
                this.sourceFiles_ = Collections.emptyList();
            } else {
                this.sourceFiles_ = null;
                this.sourceFilesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return SourceInfoProto.internal_static_google_api_SourceInfo_descriptor;
        }

        @Override
        public SourceInfo getDefaultInstanceForType() {
            return SourceInfo.getDefaultInstance();
        }

        @Override
        public SourceInfo build() {
            SourceInfo result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public SourceInfo buildPartial() {
            SourceInfo result = new SourceInfo(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(SourceInfo result) {
            if (this.sourceFilesBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.sourceFiles_ = Collections.unmodifiableList(this.sourceFiles_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.sourceFiles_ = this.sourceFiles_;
            } else {
                result.sourceFiles_ = this.sourceFilesBuilder_.build();
            }
        }

        private void buildPartial0(SourceInfo result) {
            int from_bitField0_ = this.bitField0_;
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
            if (other instanceof SourceInfo) {
                return this.mergeFrom((SourceInfo)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(SourceInfo other) {
            if (other == SourceInfo.getDefaultInstance()) {
                return this;
            }
            if (this.sourceFilesBuilder_ == null) {
                if (!other.sourceFiles_.isEmpty()) {
                    if (this.sourceFiles_.isEmpty()) {
                        this.sourceFiles_ = other.sourceFiles_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureSourceFilesIsMutable();
                        this.sourceFiles_.addAll(other.sourceFiles_);
                    }
                    this.onChanged();
                }
            } else if (!other.sourceFiles_.isEmpty()) {
                if (this.sourceFilesBuilder_.isEmpty()) {
                    this.sourceFilesBuilder_.dispose();
                    this.sourceFilesBuilder_ = null;
                    this.sourceFiles_ = other.sourceFiles_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.sourceFilesBuilder_ = alwaysUseFieldBuilders ? this.getSourceFilesFieldBuilder() : null;
                } else {
                    this.sourceFilesBuilder_.addAllMessages(other.sourceFiles_);
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 10: {
                            Any m4 = input.readMessage(Any.parser(), extensionRegistry);
                            if (this.sourceFilesBuilder_ == null) {
                                this.ensureSourceFilesIsMutable();
                                this.sourceFiles_.add(m4);
                                continue block9;
                            }
                            this.sourceFilesBuilder_.addMessage(m4);
                            continue block9;
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

        private void ensureSourceFilesIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.sourceFiles_ = new ArrayList<Any>(this.sourceFiles_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<Any> getSourceFilesList() {
            if (this.sourceFilesBuilder_ == null) {
                return Collections.unmodifiableList(this.sourceFiles_);
            }
            return this.sourceFilesBuilder_.getMessageList();
        }

        @Override
        public int getSourceFilesCount() {
            if (this.sourceFilesBuilder_ == null) {
                return this.sourceFiles_.size();
            }
            return this.sourceFilesBuilder_.getCount();
        }

        @Override
        public Any getSourceFiles(int index) {
            if (this.sourceFilesBuilder_ == null) {
                return this.sourceFiles_.get(index);
            }
            return this.sourceFilesBuilder_.getMessage(index);
        }

        public Builder setSourceFiles(int index, Any value) {
            if (this.sourceFilesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.set(index, value);
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setSourceFiles(int index, Any.Builder builderForValue) {
            if (this.sourceFilesBuilder_ == null) {
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addSourceFiles(Any value) {
            if (this.sourceFilesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.add(value);
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addSourceFiles(int index, Any value) {
            if (this.sourceFilesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.add(index, value);
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addSourceFiles(Any.Builder builderForValue) {
            if (this.sourceFilesBuilder_ == null) {
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addSourceFiles(int index, Any.Builder builderForValue) {
            if (this.sourceFilesBuilder_ == null) {
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllSourceFiles(Iterable<? extends Any> values2) {
            if (this.sourceFilesBuilder_ == null) {
                this.ensureSourceFilesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.sourceFiles_);
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearSourceFiles() {
            if (this.sourceFilesBuilder_ == null) {
                this.sourceFiles_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.clear();
            }
            return this;
        }

        public Builder removeSourceFiles(int index) {
            if (this.sourceFilesBuilder_ == null) {
                this.ensureSourceFilesIsMutable();
                this.sourceFiles_.remove(index);
                this.onChanged();
            } else {
                this.sourceFilesBuilder_.remove(index);
            }
            return this;
        }

        public Any.Builder getSourceFilesBuilder(int index) {
            return this.getSourceFilesFieldBuilder().getBuilder(index);
        }

        @Override
        public AnyOrBuilder getSourceFilesOrBuilder(int index) {
            if (this.sourceFilesBuilder_ == null) {
                return this.sourceFiles_.get(index);
            }
            return this.sourceFilesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends AnyOrBuilder> getSourceFilesOrBuilderList() {
            if (this.sourceFilesBuilder_ != null) {
                return this.sourceFilesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.sourceFiles_);
        }

        public Any.Builder addSourceFilesBuilder() {
            return this.getSourceFilesFieldBuilder().addBuilder(Any.getDefaultInstance());
        }

        public Any.Builder addSourceFilesBuilder(int index) {
            return this.getSourceFilesFieldBuilder().addBuilder(index, Any.getDefaultInstance());
        }

        public List<Any.Builder> getSourceFilesBuilderList() {
            return this.getSourceFilesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Any, Any.Builder, AnyOrBuilder> getSourceFilesFieldBuilder() {
            if (this.sourceFilesBuilder_ == null) {
                this.sourceFilesBuilder_ = new RepeatedFieldBuilderV3(this.sourceFiles_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.sourceFiles_ = null;
            }
            return this.sourceFilesBuilder_;
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

