/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.DocumentationProto;
import com.google.api.PageOrBuilder;
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

public final class Page
extends GeneratedMessageV3
implements PageOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int CONTENT_FIELD_NUMBER = 2;
    private volatile Object content_ = "";
    public static final int SUBPAGES_FIELD_NUMBER = 3;
    private List<Page> subpages_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Page DEFAULT_INSTANCE = new Page();
    private static final Parser<Page> PARSER = new AbstractParser<Page>(){

        @Override
        public Page parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Page.newBuilder();
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

    private Page(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Page() {
        this.name_ = "";
        this.content_ = "";
        this.subpages_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Page();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DocumentationProto.internal_static_google_api_Page_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DocumentationProto.internal_static_google_api_Page_fieldAccessorTable.ensureFieldAccessorsInitialized(Page.class, Builder.class);
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
    public String getContent() {
        Object ref = this.content_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.content_ = s2;
        return s2;
    }

    @Override
    public ByteString getContentBytes() {
        Object ref = this.content_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.content_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<Page> getSubpagesList() {
        return this.subpages_;
    }

    @Override
    public List<? extends PageOrBuilder> getSubpagesOrBuilderList() {
        return this.subpages_;
    }

    @Override
    public int getSubpagesCount() {
        return this.subpages_.size();
    }

    @Override
    public Page getSubpages(int index) {
        return this.subpages_.get(index);
    }

    @Override
    public PageOrBuilder getSubpagesOrBuilder(int index) {
        return this.subpages_.get(index);
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.content_)) {
            GeneratedMessageV3.writeString(output, 2, this.content_);
        }
        for (int i = 0; i < this.subpages_.size(); ++i) {
            output.writeMessage(3, this.subpages_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.content_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.content_);
        }
        for (int i = 0; i < this.subpages_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.subpages_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Page)) {
            return super.equals(obj);
        }
        Page other = (Page)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getContent().equals(other.getContent())) {
            return false;
        }
        if (!this.getSubpagesList().equals(other.getSubpagesList())) {
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
        hash = 19 * hash + Page.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getContent().hashCode();
        if (this.getSubpagesCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getSubpagesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Page parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Page parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Page parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Page parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Page parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Page parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Page parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Page parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Page parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Page parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Page parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Page parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Page.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Page prototype) {
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

    public static Page getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Page> parser() {
        return PARSER;
    }

    public Parser<Page> getParserForType() {
        return PARSER;
    }

    @Override
    public Page getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PageOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object content_ = "";
        private List<Page> subpages_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Page, Builder, PageOrBuilder> subpagesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return DocumentationProto.internal_static_google_api_Page_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DocumentationProto.internal_static_google_api_Page_fieldAccessorTable.ensureFieldAccessorsInitialized(Page.class, Builder.class);
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
            this.content_ = "";
            if (this.subpagesBuilder_ == null) {
                this.subpages_ = Collections.emptyList();
            } else {
                this.subpages_ = null;
                this.subpagesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFB;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DocumentationProto.internal_static_google_api_Page_descriptor;
        }

        @Override
        public Page getDefaultInstanceForType() {
            return Page.getDefaultInstance();
        }

        @Override
        public Page build() {
            Page result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Page buildPartial() {
            Page result = new Page(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Page result) {
            if (this.subpagesBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0) {
                    this.subpages_ = Collections.unmodifiableList(this.subpages_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.subpages_ = this.subpages_;
            } else {
                result.subpages_ = this.subpagesBuilder_.build();
            }
        }

        private void buildPartial0(Page result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.content_ = this.content_;
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
            if (other instanceof Page) {
                return this.mergeFrom((Page)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Page other) {
            if (other == Page.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getContent().isEmpty()) {
                this.content_ = other.content_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (this.subpagesBuilder_ == null) {
                if (!other.subpages_.isEmpty()) {
                    if (this.subpages_.isEmpty()) {
                        this.subpages_ = other.subpages_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureSubpagesIsMutable();
                        this.subpages_.addAll(other.subpages_);
                    }
                    this.onChanged();
                }
            } else if (!other.subpages_.isEmpty()) {
                if (this.subpagesBuilder_.isEmpty()) {
                    this.subpagesBuilder_.dispose();
                    this.subpagesBuilder_ = null;
                    this.subpages_ = other.subpages_;
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.subpagesBuilder_ = alwaysUseFieldBuilders ? this.getSubpagesFieldBuilder() : null;
                } else {
                    this.subpagesBuilder_.addAllMessages(other.subpages_);
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
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: {
                            this.content_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block11;
                        }
                        case 26: {
                            Page m4 = input.readMessage(Page.parser(), extensionRegistry);
                            if (this.subpagesBuilder_ == null) {
                                this.ensureSubpagesIsMutable();
                                this.subpages_.add(m4);
                                continue block11;
                            }
                            this.subpagesBuilder_.addMessage(m4);
                            continue block11;
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
            this.name_ = Page.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Page.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getContent() {
            Object ref = this.content_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.content_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getContentBytes() {
            Object ref = this.content_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.content_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setContent(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.content_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearContent() {
            this.content_ = Page.getDefaultInstance().getContent();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setContentBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Page.checkByteStringIsUtf8(value);
            this.content_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        private void ensureSubpagesIsMutable() {
            if ((this.bitField0_ & 4) == 0) {
                this.subpages_ = new ArrayList<Page>(this.subpages_);
                this.bitField0_ |= 4;
            }
        }

        @Override
        public List<Page> getSubpagesList() {
            if (this.subpagesBuilder_ == null) {
                return Collections.unmodifiableList(this.subpages_);
            }
            return this.subpagesBuilder_.getMessageList();
        }

        @Override
        public int getSubpagesCount() {
            if (this.subpagesBuilder_ == null) {
                return this.subpages_.size();
            }
            return this.subpagesBuilder_.getCount();
        }

        @Override
        public Page getSubpages(int index) {
            if (this.subpagesBuilder_ == null) {
                return this.subpages_.get(index);
            }
            return this.subpagesBuilder_.getMessage(index);
        }

        public Builder setSubpages(int index, Page value) {
            if (this.subpagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSubpagesIsMutable();
                this.subpages_.set(index, value);
                this.onChanged();
            } else {
                this.subpagesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setSubpages(int index, Builder builderForValue) {
            if (this.subpagesBuilder_ == null) {
                this.ensureSubpagesIsMutable();
                this.subpages_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.subpagesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addSubpages(Page value) {
            if (this.subpagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSubpagesIsMutable();
                this.subpages_.add(value);
                this.onChanged();
            } else {
                this.subpagesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addSubpages(int index, Page value) {
            if (this.subpagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSubpagesIsMutable();
                this.subpages_.add(index, value);
                this.onChanged();
            } else {
                this.subpagesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addSubpages(Builder builderForValue) {
            if (this.subpagesBuilder_ == null) {
                this.ensureSubpagesIsMutable();
                this.subpages_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.subpagesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addSubpages(int index, Builder builderForValue) {
            if (this.subpagesBuilder_ == null) {
                this.ensureSubpagesIsMutable();
                this.subpages_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.subpagesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllSubpages(Iterable<? extends Page> values2) {
            if (this.subpagesBuilder_ == null) {
                this.ensureSubpagesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.subpages_);
                this.onChanged();
            } else {
                this.subpagesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearSubpages() {
            if (this.subpagesBuilder_ == null) {
                this.subpages_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
            } else {
                this.subpagesBuilder_.clear();
            }
            return this;
        }

        public Builder removeSubpages(int index) {
            if (this.subpagesBuilder_ == null) {
                this.ensureSubpagesIsMutable();
                this.subpages_.remove(index);
                this.onChanged();
            } else {
                this.subpagesBuilder_.remove(index);
            }
            return this;
        }

        public Builder getSubpagesBuilder(int index) {
            return this.getSubpagesFieldBuilder().getBuilder(index);
        }

        @Override
        public PageOrBuilder getSubpagesOrBuilder(int index) {
            if (this.subpagesBuilder_ == null) {
                return this.subpages_.get(index);
            }
            return this.subpagesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends PageOrBuilder> getSubpagesOrBuilderList() {
            if (this.subpagesBuilder_ != null) {
                return this.subpagesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.subpages_);
        }

        public Builder addSubpagesBuilder() {
            return this.getSubpagesFieldBuilder().addBuilder(Page.getDefaultInstance());
        }

        public Builder addSubpagesBuilder(int index) {
            return this.getSubpagesFieldBuilder().addBuilder(index, Page.getDefaultInstance());
        }

        public List<Builder> getSubpagesBuilderList() {
            return this.getSubpagesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Page, Builder, PageOrBuilder> getSubpagesFieldBuilder() {
            if (this.subpagesBuilder_ == null) {
                this.subpagesBuilder_ = new RepeatedFieldBuilderV3(this.subpages_, (this.bitField0_ & 4) != 0, this.getParentForChildren(), this.isClean());
                this.subpages_ = null;
            }
            return this.subpagesBuilder_;
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

