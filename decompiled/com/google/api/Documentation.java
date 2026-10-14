/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.DocumentationOrBuilder;
import com.google.api.DocumentationProto;
import com.google.api.DocumentationRule;
import com.google.api.DocumentationRuleOrBuilder;
import com.google.api.Page;
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

public final class Documentation
extends GeneratedMessageV3
implements DocumentationOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int SUMMARY_FIELD_NUMBER = 1;
    private volatile Object summary_ = "";
    public static final int PAGES_FIELD_NUMBER = 5;
    private List<Page> pages_;
    public static final int RULES_FIELD_NUMBER = 3;
    private List<DocumentationRule> rules_;
    public static final int DOCUMENTATION_ROOT_URL_FIELD_NUMBER = 4;
    private volatile Object documentationRootUrl_ = "";
    public static final int SERVICE_ROOT_URL_FIELD_NUMBER = 6;
    private volatile Object serviceRootUrl_ = "";
    public static final int OVERVIEW_FIELD_NUMBER = 2;
    private volatile Object overview_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final Documentation DEFAULT_INSTANCE = new Documentation();
    private static final Parser<Documentation> PARSER = new AbstractParser<Documentation>(){

        @Override
        public Documentation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Documentation.newBuilder();
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

    private Documentation(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Documentation() {
        this.summary_ = "";
        this.pages_ = Collections.emptyList();
        this.rules_ = Collections.emptyList();
        this.documentationRootUrl_ = "";
        this.serviceRootUrl_ = "";
        this.overview_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Documentation();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return DocumentationProto.internal_static_google_api_Documentation_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return DocumentationProto.internal_static_google_api_Documentation_fieldAccessorTable.ensureFieldAccessorsInitialized(Documentation.class, Builder.class);
    }

    @Override
    public String getSummary() {
        Object ref = this.summary_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.summary_ = s2;
        return s2;
    }

    @Override
    public ByteString getSummaryBytes() {
        Object ref = this.summary_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.summary_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<Page> getPagesList() {
        return this.pages_;
    }

    @Override
    public List<? extends PageOrBuilder> getPagesOrBuilderList() {
        return this.pages_;
    }

    @Override
    public int getPagesCount() {
        return this.pages_.size();
    }

    @Override
    public Page getPages(int index) {
        return this.pages_.get(index);
    }

    @Override
    public PageOrBuilder getPagesOrBuilder(int index) {
        return this.pages_.get(index);
    }

    @Override
    public List<DocumentationRule> getRulesList() {
        return this.rules_;
    }

    @Override
    public List<? extends DocumentationRuleOrBuilder> getRulesOrBuilderList() {
        return this.rules_;
    }

    @Override
    public int getRulesCount() {
        return this.rules_.size();
    }

    @Override
    public DocumentationRule getRules(int index) {
        return this.rules_.get(index);
    }

    @Override
    public DocumentationRuleOrBuilder getRulesOrBuilder(int index) {
        return this.rules_.get(index);
    }

    @Override
    public String getDocumentationRootUrl() {
        Object ref = this.documentationRootUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.documentationRootUrl_ = s2;
        return s2;
    }

    @Override
    public ByteString getDocumentationRootUrlBytes() {
        Object ref = this.documentationRootUrl_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.documentationRootUrl_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getServiceRootUrl() {
        Object ref = this.serviceRootUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.serviceRootUrl_ = s2;
        return s2;
    }

    @Override
    public ByteString getServiceRootUrlBytes() {
        Object ref = this.serviceRootUrl_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.serviceRootUrl_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getOverview() {
        Object ref = this.overview_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.overview_ = s2;
        return s2;
    }

    @Override
    public ByteString getOverviewBytes() {
        Object ref = this.overview_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.overview_ = b;
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
        int i;
        if (!GeneratedMessageV3.isStringEmpty(this.summary_)) {
            GeneratedMessageV3.writeString(output, 1, this.summary_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.overview_)) {
            GeneratedMessageV3.writeString(output, 2, this.overview_);
        }
        for (i = 0; i < this.rules_.size(); ++i) {
            output.writeMessage(3, this.rules_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.documentationRootUrl_)) {
            GeneratedMessageV3.writeString(output, 4, this.documentationRootUrl_);
        }
        for (i = 0; i < this.pages_.size(); ++i) {
            output.writeMessage(5, this.pages_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceRootUrl_)) {
            GeneratedMessageV3.writeString(output, 6, this.serviceRootUrl_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.summary_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.summary_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.overview_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.overview_);
        }
        for (i = 0; i < this.rules_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.rules_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.documentationRootUrl_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.documentationRootUrl_);
        }
        for (i = 0; i < this.pages_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(5, this.pages_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.serviceRootUrl_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.serviceRootUrl_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Documentation)) {
            return super.equals(obj);
        }
        Documentation other = (Documentation)obj;
        if (!this.getSummary().equals(other.getSummary())) {
            return false;
        }
        if (!this.getPagesList().equals(other.getPagesList())) {
            return false;
        }
        if (!this.getRulesList().equals(other.getRulesList())) {
            return false;
        }
        if (!this.getDocumentationRootUrl().equals(other.getDocumentationRootUrl())) {
            return false;
        }
        if (!this.getServiceRootUrl().equals(other.getServiceRootUrl())) {
            return false;
        }
        if (!this.getOverview().equals(other.getOverview())) {
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
        hash = 19 * hash + Documentation.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSummary().hashCode();
        if (this.getPagesCount() > 0) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getPagesList().hashCode();
        }
        if (this.getRulesCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getRulesList().hashCode();
        }
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getDocumentationRootUrl().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getServiceRootUrl().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getOverview().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Documentation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Documentation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Documentation parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Documentation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Documentation parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Documentation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Documentation parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Documentation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Documentation parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Documentation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Documentation parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Documentation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Documentation.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Documentation prototype) {
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

    public static Documentation getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Documentation> parser() {
        return PARSER;
    }

    public Parser<Documentation> getParserForType() {
        return PARSER;
    }

    @Override
    public Documentation getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DocumentationOrBuilder {
        private int bitField0_;
        private Object summary_ = "";
        private List<Page> pages_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Page, Page.Builder, PageOrBuilder> pagesBuilder_;
        private List<DocumentationRule> rules_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<DocumentationRule, DocumentationRule.Builder, DocumentationRuleOrBuilder> rulesBuilder_;
        private Object documentationRootUrl_ = "";
        private Object serviceRootUrl_ = "";
        private Object overview_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return DocumentationProto.internal_static_google_api_Documentation_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return DocumentationProto.internal_static_google_api_Documentation_fieldAccessorTable.ensureFieldAccessorsInitialized(Documentation.class, Builder.class);
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
            this.summary_ = "";
            if (this.pagesBuilder_ == null) {
                this.pages_ = Collections.emptyList();
            } else {
                this.pages_ = null;
                this.pagesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            if (this.rulesBuilder_ == null) {
                this.rules_ = Collections.emptyList();
            } else {
                this.rules_ = null;
                this.rulesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFB;
            this.documentationRootUrl_ = "";
            this.serviceRootUrl_ = "";
            this.overview_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return DocumentationProto.internal_static_google_api_Documentation_descriptor;
        }

        @Override
        public Documentation getDefaultInstanceForType() {
            return Documentation.getDefaultInstance();
        }

        @Override
        public Documentation build() {
            Documentation result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Documentation buildPartial() {
            Documentation result = new Documentation(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Documentation result) {
            if (this.pagesBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.pages_ = Collections.unmodifiableList(this.pages_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.pages_ = this.pages_;
            } else {
                result.pages_ = this.pagesBuilder_.build();
            }
            if (this.rulesBuilder_ == null) {
                if ((this.bitField0_ & 4) != 0) {
                    this.rules_ = Collections.unmodifiableList(this.rules_);
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.rules_ = this.rules_;
            } else {
                result.rules_ = this.rulesBuilder_.build();
            }
        }

        private void buildPartial0(Documentation result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.summary_ = this.summary_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.documentationRootUrl_ = this.documentationRootUrl_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.serviceRootUrl_ = this.serviceRootUrl_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.overview_ = this.overview_;
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
            if (other instanceof Documentation) {
                return this.mergeFrom((Documentation)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Documentation other) {
            if (other == Documentation.getDefaultInstance()) {
                return this;
            }
            if (!other.getSummary().isEmpty()) {
                this.summary_ = other.summary_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (this.pagesBuilder_ == null) {
                if (!other.pages_.isEmpty()) {
                    if (this.pages_.isEmpty()) {
                        this.pages_ = other.pages_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensurePagesIsMutable();
                        this.pages_.addAll(other.pages_);
                    }
                    this.onChanged();
                }
            } else if (!other.pages_.isEmpty()) {
                if (this.pagesBuilder_.isEmpty()) {
                    this.pagesBuilder_.dispose();
                    this.pagesBuilder_ = null;
                    this.pages_ = other.pages_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.pagesBuilder_ = alwaysUseFieldBuilders ? this.getPagesFieldBuilder() : null;
                } else {
                    this.pagesBuilder_.addAllMessages(other.pages_);
                }
            }
            if (this.rulesBuilder_ == null) {
                if (!other.rules_.isEmpty()) {
                    if (this.rules_.isEmpty()) {
                        this.rules_ = other.rules_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureRulesIsMutable();
                        this.rules_.addAll(other.rules_);
                    }
                    this.onChanged();
                }
            } else if (!other.rules_.isEmpty()) {
                if (this.rulesBuilder_.isEmpty()) {
                    this.rulesBuilder_.dispose();
                    this.rulesBuilder_ = null;
                    this.rules_ = other.rules_;
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.rulesBuilder_ = alwaysUseFieldBuilders ? this.getRulesFieldBuilder() : null;
                } else {
                    this.rulesBuilder_.addAllMessages(other.rules_);
                }
            }
            if (!other.getDocumentationRootUrl().isEmpty()) {
                this.documentationRootUrl_ = other.documentationRootUrl_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (!other.getServiceRootUrl().isEmpty()) {
                this.serviceRootUrl_ = other.serviceRootUrl_;
                this.bitField0_ |= 0x10;
                this.onChanged();
            }
            if (!other.getOverview().isEmpty()) {
                this.overview_ = other.overview_;
                this.bitField0_ |= 0x20;
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
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            this.summary_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block14;
                        }
                        case 18: {
                            this.overview_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block14;
                        }
                        case 26: {
                            GeneratedMessageV3 m4 = input.readMessage(DocumentationRule.parser(), extensionRegistry);
                            if (this.rulesBuilder_ == null) {
                                this.ensureRulesIsMutable();
                                this.rules_.add((DocumentationRule)m4);
                                continue block14;
                            }
                            this.rulesBuilder_.addMessage((DocumentationRule)m4);
                            continue block14;
                        }
                        case 34: {
                            this.documentationRootUrl_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block14;
                        }
                        case 42: {
                            GeneratedMessageV3 m4 = input.readMessage(Page.parser(), extensionRegistry);
                            if (this.pagesBuilder_ == null) {
                                this.ensurePagesIsMutable();
                                this.pages_.add((Page)m4);
                                continue block14;
                            }
                            this.pagesBuilder_.addMessage((Page)m4);
                            continue block14;
                        }
                        case 50: {
                            this.serviceRootUrl_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
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
        public String getSummary() {
            Object ref = this.summary_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.summary_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSummaryBytes() {
            Object ref = this.summary_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.summary_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSummary(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.summary_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearSummary() {
            this.summary_ = Documentation.getDefaultInstance().getSummary();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSummaryBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Documentation.checkByteStringIsUtf8(value);
            this.summary_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensurePagesIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.pages_ = new ArrayList<Page>(this.pages_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<Page> getPagesList() {
            if (this.pagesBuilder_ == null) {
                return Collections.unmodifiableList(this.pages_);
            }
            return this.pagesBuilder_.getMessageList();
        }

        @Override
        public int getPagesCount() {
            if (this.pagesBuilder_ == null) {
                return this.pages_.size();
            }
            return this.pagesBuilder_.getCount();
        }

        @Override
        public Page getPages(int index) {
            if (this.pagesBuilder_ == null) {
                return this.pages_.get(index);
            }
            return this.pagesBuilder_.getMessage(index);
        }

        public Builder setPages(int index, Page value) {
            if (this.pagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePagesIsMutable();
                this.pages_.set(index, value);
                this.onChanged();
            } else {
                this.pagesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setPages(int index, Page.Builder builderForValue) {
            if (this.pagesBuilder_ == null) {
                this.ensurePagesIsMutable();
                this.pages_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.pagesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addPages(Page value) {
            if (this.pagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePagesIsMutable();
                this.pages_.add(value);
                this.onChanged();
            } else {
                this.pagesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addPages(int index, Page value) {
            if (this.pagesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePagesIsMutable();
                this.pages_.add(index, value);
                this.onChanged();
            } else {
                this.pagesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addPages(Page.Builder builderForValue) {
            if (this.pagesBuilder_ == null) {
                this.ensurePagesIsMutable();
                this.pages_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.pagesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addPages(int index, Page.Builder builderForValue) {
            if (this.pagesBuilder_ == null) {
                this.ensurePagesIsMutable();
                this.pages_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.pagesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllPages(Iterable<? extends Page> values2) {
            if (this.pagesBuilder_ == null) {
                this.ensurePagesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.pages_);
                this.onChanged();
            } else {
                this.pagesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearPages() {
            if (this.pagesBuilder_ == null) {
                this.pages_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.pagesBuilder_.clear();
            }
            return this;
        }

        public Builder removePages(int index) {
            if (this.pagesBuilder_ == null) {
                this.ensurePagesIsMutable();
                this.pages_.remove(index);
                this.onChanged();
            } else {
                this.pagesBuilder_.remove(index);
            }
            return this;
        }

        public Page.Builder getPagesBuilder(int index) {
            return this.getPagesFieldBuilder().getBuilder(index);
        }

        @Override
        public PageOrBuilder getPagesOrBuilder(int index) {
            if (this.pagesBuilder_ == null) {
                return this.pages_.get(index);
            }
            return this.pagesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends PageOrBuilder> getPagesOrBuilderList() {
            if (this.pagesBuilder_ != null) {
                return this.pagesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.pages_);
        }

        public Page.Builder addPagesBuilder() {
            return this.getPagesFieldBuilder().addBuilder(Page.getDefaultInstance());
        }

        public Page.Builder addPagesBuilder(int index) {
            return this.getPagesFieldBuilder().addBuilder(index, Page.getDefaultInstance());
        }

        public List<Page.Builder> getPagesBuilderList() {
            return this.getPagesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Page, Page.Builder, PageOrBuilder> getPagesFieldBuilder() {
            if (this.pagesBuilder_ == null) {
                this.pagesBuilder_ = new RepeatedFieldBuilderV3(this.pages_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.pages_ = null;
            }
            return this.pagesBuilder_;
        }

        private void ensureRulesIsMutable() {
            if ((this.bitField0_ & 4) == 0) {
                this.rules_ = new ArrayList<DocumentationRule>(this.rules_);
                this.bitField0_ |= 4;
            }
        }

        @Override
        public List<DocumentationRule> getRulesList() {
            if (this.rulesBuilder_ == null) {
                return Collections.unmodifiableList(this.rules_);
            }
            return this.rulesBuilder_.getMessageList();
        }

        @Override
        public int getRulesCount() {
            if (this.rulesBuilder_ == null) {
                return this.rules_.size();
            }
            return this.rulesBuilder_.getCount();
        }

        @Override
        public DocumentationRule getRules(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessage(index);
        }

        public Builder setRules(int index, DocumentationRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.set(index, value);
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setRules(int index, DocumentationRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addRules(DocumentationRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.add(value);
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addRules(int index, DocumentationRule value) {
            if (this.rulesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureRulesIsMutable();
                this.rules_.add(index, value);
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addRules(DocumentationRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addRules(int index, DocumentationRule.Builder builderForValue) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.rulesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllRules(Iterable<? extends DocumentationRule> values2) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.rules_);
                this.onChanged();
            } else {
                this.rulesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearRules() {
            if (this.rulesBuilder_ == null) {
                this.rules_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
            } else {
                this.rulesBuilder_.clear();
            }
            return this;
        }

        public Builder removeRules(int index) {
            if (this.rulesBuilder_ == null) {
                this.ensureRulesIsMutable();
                this.rules_.remove(index);
                this.onChanged();
            } else {
                this.rulesBuilder_.remove(index);
            }
            return this;
        }

        public DocumentationRule.Builder getRulesBuilder(int index) {
            return this.getRulesFieldBuilder().getBuilder(index);
        }

        @Override
        public DocumentationRuleOrBuilder getRulesOrBuilder(int index) {
            if (this.rulesBuilder_ == null) {
                return this.rules_.get(index);
            }
            return this.rulesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends DocumentationRuleOrBuilder> getRulesOrBuilderList() {
            if (this.rulesBuilder_ != null) {
                return this.rulesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.rules_);
        }

        public DocumentationRule.Builder addRulesBuilder() {
            return this.getRulesFieldBuilder().addBuilder(DocumentationRule.getDefaultInstance());
        }

        public DocumentationRule.Builder addRulesBuilder(int index) {
            return this.getRulesFieldBuilder().addBuilder(index, DocumentationRule.getDefaultInstance());
        }

        public List<DocumentationRule.Builder> getRulesBuilderList() {
            return this.getRulesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<DocumentationRule, DocumentationRule.Builder, DocumentationRuleOrBuilder> getRulesFieldBuilder() {
            if (this.rulesBuilder_ == null) {
                this.rulesBuilder_ = new RepeatedFieldBuilderV3(this.rules_, (this.bitField0_ & 4) != 0, this.getParentForChildren(), this.isClean());
                this.rules_ = null;
            }
            return this.rulesBuilder_;
        }

        @Override
        public String getDocumentationRootUrl() {
            Object ref = this.documentationRootUrl_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.documentationRootUrl_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDocumentationRootUrlBytes() {
            Object ref = this.documentationRootUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.documentationRootUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDocumentationRootUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.documentationRootUrl_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearDocumentationRootUrl() {
            this.documentationRootUrl_ = Documentation.getDefaultInstance().getDocumentationRootUrl();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setDocumentationRootUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Documentation.checkByteStringIsUtf8(value);
            this.documentationRootUrl_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public String getServiceRootUrl() {
            Object ref = this.serviceRootUrl_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.serviceRootUrl_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getServiceRootUrlBytes() {
            Object ref = this.serviceRootUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.serviceRootUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setServiceRootUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.serviceRootUrl_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearServiceRootUrl() {
            this.serviceRootUrl_ = Documentation.getDefaultInstance().getServiceRootUrl();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setServiceRootUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Documentation.checkByteStringIsUtf8(value);
            this.serviceRootUrl_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        @Override
        public String getOverview() {
            Object ref = this.overview_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.overview_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getOverviewBytes() {
            Object ref = this.overview_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.overview_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setOverview(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.overview_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearOverview() {
            this.overview_ = Documentation.getDefaultInstance().getOverview();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setOverviewBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Documentation.checkByteStringIsUtf8(value);
            this.overview_ = value;
            this.bitField0_ |= 0x20;
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

