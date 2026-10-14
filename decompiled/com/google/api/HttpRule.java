/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.CustomHttpPattern;
import com.google.api.CustomHttpPatternOrBuilder;
import com.google.api.HttpProto;
import com.google.api.HttpRuleOrBuilder;
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
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HttpRule
extends GeneratedMessageV3
implements HttpRuleOrBuilder {
    private static final long serialVersionUID = 0L;
    private int patternCase_ = 0;
    private Object pattern_;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    private volatile Object selector_ = "";
    public static final int GET_FIELD_NUMBER = 2;
    public static final int PUT_FIELD_NUMBER = 3;
    public static final int POST_FIELD_NUMBER = 4;
    public static final int DELETE_FIELD_NUMBER = 5;
    public static final int PATCH_FIELD_NUMBER = 6;
    public static final int CUSTOM_FIELD_NUMBER = 8;
    public static final int BODY_FIELD_NUMBER = 7;
    private volatile Object body_ = "";
    public static final int RESPONSE_BODY_FIELD_NUMBER = 12;
    private volatile Object responseBody_ = "";
    public static final int ADDITIONAL_BINDINGS_FIELD_NUMBER = 11;
    private List<HttpRule> additionalBindings_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final HttpRule DEFAULT_INSTANCE = new HttpRule();
    private static final Parser<HttpRule> PARSER = new AbstractParser<HttpRule>(){

        @Override
        public HttpRule parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = HttpRule.newBuilder();
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

    private HttpRule(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private HttpRule() {
        this.selector_ = "";
        this.body_ = "";
        this.responseBody_ = "";
        this.additionalBindings_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new HttpRule();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return HttpProto.internal_static_google_api_HttpRule_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return HttpProto.internal_static_google_api_HttpRule_fieldAccessorTable.ensureFieldAccessorsInitialized(HttpRule.class, Builder.class);
    }

    @Override
    public PatternCase getPatternCase() {
        return PatternCase.forNumber(this.patternCase_);
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
    public boolean hasGet() {
        return this.patternCase_ == 2;
    }

    @Override
    public String getGet() {
        Object ref = "";
        if (this.patternCase_ == 2) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.patternCase_ == 2) {
            this.pattern_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getGetBytes() {
        Object ref = "";
        if (this.patternCase_ == 2) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.patternCase_ == 2) {
                this.pattern_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasPut() {
        return this.patternCase_ == 3;
    }

    @Override
    public String getPut() {
        Object ref = "";
        if (this.patternCase_ == 3) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.patternCase_ == 3) {
            this.pattern_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getPutBytes() {
        Object ref = "";
        if (this.patternCase_ == 3) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.patternCase_ == 3) {
                this.pattern_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasPost() {
        return this.patternCase_ == 4;
    }

    @Override
    public String getPost() {
        Object ref = "";
        if (this.patternCase_ == 4) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.patternCase_ == 4) {
            this.pattern_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getPostBytes() {
        Object ref = "";
        if (this.patternCase_ == 4) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.patternCase_ == 4) {
                this.pattern_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasDelete() {
        return this.patternCase_ == 5;
    }

    @Override
    public String getDelete() {
        Object ref = "";
        if (this.patternCase_ == 5) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.patternCase_ == 5) {
            this.pattern_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getDeleteBytes() {
        Object ref = "";
        if (this.patternCase_ == 5) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.patternCase_ == 5) {
                this.pattern_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasPatch() {
        return this.patternCase_ == 6;
    }

    @Override
    public String getPatch() {
        Object ref = "";
        if (this.patternCase_ == 6) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.patternCase_ == 6) {
            this.pattern_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getPatchBytes() {
        Object ref = "";
        if (this.patternCase_ == 6) {
            ref = this.pattern_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.patternCase_ == 6) {
                this.pattern_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasCustom() {
        return this.patternCase_ == 8;
    }

    @Override
    public CustomHttpPattern getCustom() {
        if (this.patternCase_ == 8) {
            return (CustomHttpPattern)this.pattern_;
        }
        return CustomHttpPattern.getDefaultInstance();
    }

    @Override
    public CustomHttpPatternOrBuilder getCustomOrBuilder() {
        if (this.patternCase_ == 8) {
            return (CustomHttpPattern)this.pattern_;
        }
        return CustomHttpPattern.getDefaultInstance();
    }

    @Override
    public String getBody() {
        Object ref = this.body_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.body_ = s2;
        return s2;
    }

    @Override
    public ByteString getBodyBytes() {
        Object ref = this.body_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.body_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getResponseBody() {
        Object ref = this.responseBody_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.responseBody_ = s2;
        return s2;
    }

    @Override
    public ByteString getResponseBodyBytes() {
        Object ref = this.responseBody_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.responseBody_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<HttpRule> getAdditionalBindingsList() {
        return this.additionalBindings_;
    }

    @Override
    public List<? extends HttpRuleOrBuilder> getAdditionalBindingsOrBuilderList() {
        return this.additionalBindings_;
    }

    @Override
    public int getAdditionalBindingsCount() {
        return this.additionalBindings_.size();
    }

    @Override
    public HttpRule getAdditionalBindings(int index) {
        return this.additionalBindings_.get(index);
    }

    @Override
    public HttpRuleOrBuilder getAdditionalBindingsOrBuilder(int index) {
        return this.additionalBindings_.get(index);
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
        if (this.patternCase_ == 2) {
            GeneratedMessageV3.writeString(output, 2, this.pattern_);
        }
        if (this.patternCase_ == 3) {
            GeneratedMessageV3.writeString(output, 3, this.pattern_);
        }
        if (this.patternCase_ == 4) {
            GeneratedMessageV3.writeString(output, 4, this.pattern_);
        }
        if (this.patternCase_ == 5) {
            GeneratedMessageV3.writeString(output, 5, this.pattern_);
        }
        if (this.patternCase_ == 6) {
            GeneratedMessageV3.writeString(output, 6, this.pattern_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.body_)) {
            GeneratedMessageV3.writeString(output, 7, this.body_);
        }
        if (this.patternCase_ == 8) {
            output.writeMessage(8, (CustomHttpPattern)this.pattern_);
        }
        for (int i = 0; i < this.additionalBindings_.size(); ++i) {
            output.writeMessage(11, this.additionalBindings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.responseBody_)) {
            GeneratedMessageV3.writeString(output, 12, this.responseBody_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.selector_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.selector_);
        }
        if (this.patternCase_ == 2) {
            size += GeneratedMessageV3.computeStringSize(2, this.pattern_);
        }
        if (this.patternCase_ == 3) {
            size += GeneratedMessageV3.computeStringSize(3, this.pattern_);
        }
        if (this.patternCase_ == 4) {
            size += GeneratedMessageV3.computeStringSize(4, this.pattern_);
        }
        if (this.patternCase_ == 5) {
            size += GeneratedMessageV3.computeStringSize(5, this.pattern_);
        }
        if (this.patternCase_ == 6) {
            size += GeneratedMessageV3.computeStringSize(6, this.pattern_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.body_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.body_);
        }
        if (this.patternCase_ == 8) {
            size += CodedOutputStream.computeMessageSize(8, (CustomHttpPattern)this.pattern_);
        }
        for (int i = 0; i < this.additionalBindings_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(11, this.additionalBindings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.responseBody_)) {
            size += GeneratedMessageV3.computeStringSize(12, this.responseBody_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HttpRule)) {
            return super.equals(obj);
        }
        HttpRule other = (HttpRule)obj;
        if (!this.getSelector().equals(other.getSelector())) {
            return false;
        }
        if (!this.getBody().equals(other.getBody())) {
            return false;
        }
        if (!this.getResponseBody().equals(other.getResponseBody())) {
            return false;
        }
        if (!this.getAdditionalBindingsList().equals(other.getAdditionalBindingsList())) {
            return false;
        }
        if (!this.getPatternCase().equals(other.getPatternCase())) {
            return false;
        }
        switch (this.patternCase_) {
            case 2: {
                if (this.getGet().equals(other.getGet())) break;
                return false;
            }
            case 3: {
                if (this.getPut().equals(other.getPut())) break;
                return false;
            }
            case 4: {
                if (this.getPost().equals(other.getPost())) break;
                return false;
            }
            case 5: {
                if (this.getDelete().equals(other.getDelete())) break;
                return false;
            }
            case 6: {
                if (this.getPatch().equals(other.getPatch())) break;
                return false;
            }
            case 8: {
                if (this.getCustom().equals(other.getCustom())) break;
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
        hash = 19 * hash + HttpRule.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getSelector().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getBody().hashCode();
        hash = 37 * hash + 12;
        hash = 53 * hash + this.getResponseBody().hashCode();
        if (this.getAdditionalBindingsCount() > 0) {
            hash = 37 * hash + 11;
            hash = 53 * hash + this.getAdditionalBindingsList().hashCode();
        }
        switch (this.patternCase_) {
            case 2: {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getGet().hashCode();
                break;
            }
            case 3: {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getPut().hashCode();
                break;
            }
            case 4: {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getPost().hashCode();
                break;
            }
            case 5: {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getDelete().hashCode();
                break;
            }
            case 6: {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getPatch().hashCode();
                break;
            }
            case 8: {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getCustom().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static HttpRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static HttpRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static HttpRule parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static HttpRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static HttpRule parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static HttpRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static HttpRule parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static HttpRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return HttpRule.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(HttpRule prototype) {
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

    public static HttpRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<HttpRule> parser() {
        return PARSER;
    }

    public Parser<HttpRule> getParserForType() {
        return PARSER;
    }

    @Override
    public HttpRule getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements HttpRuleOrBuilder {
        private int patternCase_ = 0;
        private Object pattern_;
        private int bitField0_;
        private Object selector_ = "";
        private SingleFieldBuilderV3<CustomHttpPattern, CustomHttpPattern.Builder, CustomHttpPatternOrBuilder> customBuilder_;
        private Object body_ = "";
        private Object responseBody_ = "";
        private List<HttpRule> additionalBindings_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<HttpRule, Builder, HttpRuleOrBuilder> additionalBindingsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return HttpProto.internal_static_google_api_HttpRule_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return HttpProto.internal_static_google_api_HttpRule_fieldAccessorTable.ensureFieldAccessorsInitialized(HttpRule.class, Builder.class);
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
            if (this.customBuilder_ != null) {
                this.customBuilder_.clear();
            }
            this.body_ = "";
            this.responseBody_ = "";
            if (this.additionalBindingsBuilder_ == null) {
                this.additionalBindings_ = Collections.emptyList();
            } else {
                this.additionalBindings_ = null;
                this.additionalBindingsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFDFF;
            this.patternCase_ = 0;
            this.pattern_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return HttpProto.internal_static_google_api_HttpRule_descriptor;
        }

        @Override
        public HttpRule getDefaultInstanceForType() {
            return HttpRule.getDefaultInstance();
        }

        @Override
        public HttpRule build() {
            HttpRule result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public HttpRule buildPartial() {
            HttpRule result = new HttpRule(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(HttpRule result) {
            if (this.additionalBindingsBuilder_ == null) {
                if ((this.bitField0_ & 0x200) != 0) {
                    this.additionalBindings_ = Collections.unmodifiableList(this.additionalBindings_);
                    this.bitField0_ &= 0xFFFFFDFF;
                }
                result.additionalBindings_ = this.additionalBindings_;
            } else {
                result.additionalBindings_ = this.additionalBindingsBuilder_.build();
            }
        }

        private void buildPartial0(HttpRule result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.selector_ = this.selector_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.body_ = this.body_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.responseBody_ = this.responseBody_;
            }
        }

        private void buildPartialOneofs(HttpRule result) {
            result.patternCase_ = this.patternCase_;
            result.pattern_ = this.pattern_;
            if (this.patternCase_ == 8 && this.customBuilder_ != null) {
                result.pattern_ = this.customBuilder_.build();
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
            if (other instanceof HttpRule) {
                return this.mergeFrom((HttpRule)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(HttpRule other) {
            if (other == HttpRule.getDefaultInstance()) {
                return this;
            }
            if (!other.getSelector().isEmpty()) {
                this.selector_ = other.selector_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getBody().isEmpty()) {
                this.body_ = other.body_;
                this.bitField0_ |= 0x80;
                this.onChanged();
            }
            if (!other.getResponseBody().isEmpty()) {
                this.responseBody_ = other.responseBody_;
                this.bitField0_ |= 0x100;
                this.onChanged();
            }
            if (this.additionalBindingsBuilder_ == null) {
                if (!other.additionalBindings_.isEmpty()) {
                    if (this.additionalBindings_.isEmpty()) {
                        this.additionalBindings_ = other.additionalBindings_;
                        this.bitField0_ &= 0xFFFFFDFF;
                    } else {
                        this.ensureAdditionalBindingsIsMutable();
                        this.additionalBindings_.addAll(other.additionalBindings_);
                    }
                    this.onChanged();
                }
            } else if (!other.additionalBindings_.isEmpty()) {
                if (this.additionalBindingsBuilder_.isEmpty()) {
                    this.additionalBindingsBuilder_.dispose();
                    this.additionalBindingsBuilder_ = null;
                    this.additionalBindings_ = other.additionalBindings_;
                    this.bitField0_ &= 0xFFFFFDFF;
                    this.additionalBindingsBuilder_ = alwaysUseFieldBuilders ? this.getAdditionalBindingsFieldBuilder() : null;
                } else {
                    this.additionalBindingsBuilder_.addAllMessages(other.additionalBindings_);
                }
            }
            switch (other.getPatternCase()) {
                case GET: {
                    this.patternCase_ = 2;
                    this.pattern_ = other.pattern_;
                    this.onChanged();
                    break;
                }
                case PUT: {
                    this.patternCase_ = 3;
                    this.pattern_ = other.pattern_;
                    this.onChanged();
                    break;
                }
                case POST: {
                    this.patternCase_ = 4;
                    this.pattern_ = other.pattern_;
                    this.onChanged();
                    break;
                }
                case DELETE: {
                    this.patternCase_ = 5;
                    this.pattern_ = other.pattern_;
                    this.onChanged();
                    break;
                }
                case PATCH: {
                    this.patternCase_ = 6;
                    this.pattern_ = other.pattern_;
                    this.onChanged();
                    break;
                }
                case CUSTOM: {
                    this.mergeCustom(other.getCustom());
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
                            String s2 = input.readStringRequireUtf8();
                            this.patternCase_ = 2;
                            this.pattern_ = s2;
                            continue block18;
                        }
                        case 26: {
                            String s2 = input.readStringRequireUtf8();
                            this.patternCase_ = 3;
                            this.pattern_ = s2;
                            continue block18;
                        }
                        case 34: {
                            String s2 = input.readStringRequireUtf8();
                            this.patternCase_ = 4;
                            this.pattern_ = s2;
                            continue block18;
                        }
                        case 42: {
                            String s2 = input.readStringRequireUtf8();
                            this.patternCase_ = 5;
                            this.pattern_ = s2;
                            continue block18;
                        }
                        case 50: {
                            String s2 = input.readStringRequireUtf8();
                            this.patternCase_ = 6;
                            this.pattern_ = s2;
                            continue block18;
                        }
                        case 58: {
                            this.body_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x80;
                            continue block18;
                        }
                        case 66: {
                            input.readMessage(this.getCustomFieldBuilder().getBuilder(), extensionRegistry);
                            this.patternCase_ = 8;
                            continue block18;
                        }
                        case 90: {
                            HttpRule m4 = input.readMessage(HttpRule.parser(), extensionRegistry);
                            if (this.additionalBindingsBuilder_ == null) {
                                this.ensureAdditionalBindingsIsMutable();
                                this.additionalBindings_.add(m4);
                                continue block18;
                            }
                            this.additionalBindingsBuilder_.addMessage(m4);
                            continue block18;
                        }
                        case 98: {
                            this.responseBody_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x100;
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
        public PatternCase getPatternCase() {
            return PatternCase.forNumber(this.patternCase_);
        }

        public Builder clearPattern() {
            this.patternCase_ = 0;
            this.pattern_ = null;
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
            this.selector_ = HttpRule.getDefaultInstance().getSelector();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.selector_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasGet() {
            return this.patternCase_ == 2;
        }

        @Override
        public String getGet() {
            Object ref = "";
            if (this.patternCase_ == 2) {
                ref = this.pattern_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.patternCase_ == 2) {
                    this.pattern_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getGetBytes() {
            Object ref = "";
            if (this.patternCase_ == 2) {
                ref = this.pattern_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.patternCase_ == 2) {
                    this.pattern_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setGet(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.patternCase_ = 2;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearGet() {
            if (this.patternCase_ == 2) {
                this.patternCase_ = 0;
                this.pattern_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setGetBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.patternCase_ = 2;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasPut() {
            return this.patternCase_ == 3;
        }

        @Override
        public String getPut() {
            Object ref = "";
            if (this.patternCase_ == 3) {
                ref = this.pattern_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.patternCase_ == 3) {
                    this.pattern_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPutBytes() {
            Object ref = "";
            if (this.patternCase_ == 3) {
                ref = this.pattern_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.patternCase_ == 3) {
                    this.pattern_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPut(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.patternCase_ = 3;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearPut() {
            if (this.patternCase_ == 3) {
                this.patternCase_ = 0;
                this.pattern_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setPutBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.patternCase_ = 3;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasPost() {
            return this.patternCase_ == 4;
        }

        @Override
        public String getPost() {
            Object ref = "";
            if (this.patternCase_ == 4) {
                ref = this.pattern_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.patternCase_ == 4) {
                    this.pattern_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPostBytes() {
            Object ref = "";
            if (this.patternCase_ == 4) {
                ref = this.pattern_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.patternCase_ == 4) {
                    this.pattern_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPost(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.patternCase_ = 4;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearPost() {
            if (this.patternCase_ == 4) {
                this.patternCase_ = 0;
                this.pattern_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setPostBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.patternCase_ = 4;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasDelete() {
            return this.patternCase_ == 5;
        }

        @Override
        public String getDelete() {
            Object ref = "";
            if (this.patternCase_ == 5) {
                ref = this.pattern_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.patternCase_ == 5) {
                    this.pattern_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDeleteBytes() {
            Object ref = "";
            if (this.patternCase_ == 5) {
                ref = this.pattern_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.patternCase_ == 5) {
                    this.pattern_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDelete(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.patternCase_ = 5;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearDelete() {
            if (this.patternCase_ == 5) {
                this.patternCase_ = 0;
                this.pattern_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setDeleteBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.patternCase_ = 5;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasPatch() {
            return this.patternCase_ == 6;
        }

        @Override
        public String getPatch() {
            Object ref = "";
            if (this.patternCase_ == 6) {
                ref = this.pattern_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.patternCase_ == 6) {
                    this.pattern_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPatchBytes() {
            Object ref = "";
            if (this.patternCase_ == 6) {
                ref = this.pattern_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.patternCase_ == 6) {
                    this.pattern_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPatch(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.patternCase_ = 6;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearPatch() {
            if (this.patternCase_ == 6) {
                this.patternCase_ = 0;
                this.pattern_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setPatchBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.patternCase_ = 6;
            this.pattern_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasCustom() {
            return this.patternCase_ == 8;
        }

        @Override
        public CustomHttpPattern getCustom() {
            if (this.customBuilder_ == null) {
                if (this.patternCase_ == 8) {
                    return (CustomHttpPattern)this.pattern_;
                }
                return CustomHttpPattern.getDefaultInstance();
            }
            if (this.patternCase_ == 8) {
                return this.customBuilder_.getMessage();
            }
            return CustomHttpPattern.getDefaultInstance();
        }

        public Builder setCustom(CustomHttpPattern value) {
            if (this.customBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.pattern_ = value;
                this.onChanged();
            } else {
                this.customBuilder_.setMessage(value);
            }
            this.patternCase_ = 8;
            return this;
        }

        public Builder setCustom(CustomHttpPattern.Builder builderForValue) {
            if (this.customBuilder_ == null) {
                this.pattern_ = builderForValue.build();
                this.onChanged();
            } else {
                this.customBuilder_.setMessage(builderForValue.build());
            }
            this.patternCase_ = 8;
            return this;
        }

        public Builder mergeCustom(CustomHttpPattern value) {
            if (this.customBuilder_ == null) {
                this.pattern_ = this.patternCase_ == 8 && this.pattern_ != CustomHttpPattern.getDefaultInstance() ? CustomHttpPattern.newBuilder((CustomHttpPattern)this.pattern_).mergeFrom(value).buildPartial() : value;
                this.onChanged();
            } else if (this.patternCase_ == 8) {
                this.customBuilder_.mergeFrom(value);
            } else {
                this.customBuilder_.setMessage(value);
            }
            this.patternCase_ = 8;
            return this;
        }

        public Builder clearCustom() {
            if (this.customBuilder_ == null) {
                if (this.patternCase_ == 8) {
                    this.patternCase_ = 0;
                    this.pattern_ = null;
                    this.onChanged();
                }
            } else {
                if (this.patternCase_ == 8) {
                    this.patternCase_ = 0;
                    this.pattern_ = null;
                }
                this.customBuilder_.clear();
            }
            return this;
        }

        public CustomHttpPattern.Builder getCustomBuilder() {
            return this.getCustomFieldBuilder().getBuilder();
        }

        @Override
        public CustomHttpPatternOrBuilder getCustomOrBuilder() {
            if (this.patternCase_ == 8 && this.customBuilder_ != null) {
                return this.customBuilder_.getMessageOrBuilder();
            }
            if (this.patternCase_ == 8) {
                return (CustomHttpPattern)this.pattern_;
            }
            return CustomHttpPattern.getDefaultInstance();
        }

        private SingleFieldBuilderV3<CustomHttpPattern, CustomHttpPattern.Builder, CustomHttpPatternOrBuilder> getCustomFieldBuilder() {
            if (this.customBuilder_ == null) {
                if (this.patternCase_ != 8) {
                    this.pattern_ = CustomHttpPattern.getDefaultInstance();
                }
                this.customBuilder_ = new SingleFieldBuilderV3((CustomHttpPattern)this.pattern_, this.getParentForChildren(), this.isClean());
                this.pattern_ = null;
            }
            this.patternCase_ = 8;
            this.onChanged();
            return this.customBuilder_;
        }

        @Override
        public String getBody() {
            Object ref = this.body_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.body_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getBodyBytes() {
            Object ref = this.body_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.body_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setBody(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.body_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearBody() {
            this.body_ = HttpRule.getDefaultInstance().getBody();
            this.bitField0_ &= 0xFFFFFF7F;
            this.onChanged();
            return this;
        }

        public Builder setBodyBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.body_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        @Override
        public String getResponseBody() {
            Object ref = this.responseBody_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.responseBody_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getResponseBodyBytes() {
            Object ref = this.responseBody_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.responseBody_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setResponseBody(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.responseBody_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearResponseBody() {
            this.responseBody_ = HttpRule.getDefaultInstance().getResponseBody();
            this.bitField0_ &= 0xFFFFFEFF;
            this.onChanged();
            return this;
        }

        public Builder setResponseBodyBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            HttpRule.checkByteStringIsUtf8(value);
            this.responseBody_ = value;
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        private void ensureAdditionalBindingsIsMutable() {
            if ((this.bitField0_ & 0x200) == 0) {
                this.additionalBindings_ = new ArrayList<HttpRule>(this.additionalBindings_);
                this.bitField0_ |= 0x200;
            }
        }

        @Override
        public List<HttpRule> getAdditionalBindingsList() {
            if (this.additionalBindingsBuilder_ == null) {
                return Collections.unmodifiableList(this.additionalBindings_);
            }
            return this.additionalBindingsBuilder_.getMessageList();
        }

        @Override
        public int getAdditionalBindingsCount() {
            if (this.additionalBindingsBuilder_ == null) {
                return this.additionalBindings_.size();
            }
            return this.additionalBindingsBuilder_.getCount();
        }

        @Override
        public HttpRule getAdditionalBindings(int index) {
            if (this.additionalBindingsBuilder_ == null) {
                return this.additionalBindings_.get(index);
            }
            return this.additionalBindingsBuilder_.getMessage(index);
        }

        public Builder setAdditionalBindings(int index, HttpRule value) {
            if (this.additionalBindingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.set(index, value);
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setAdditionalBindings(int index, Builder builderForValue) {
            if (this.additionalBindingsBuilder_ == null) {
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAdditionalBindings(HttpRule value) {
            if (this.additionalBindingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.add(value);
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addAdditionalBindings(int index, HttpRule value) {
            if (this.additionalBindingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.add(index, value);
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addAdditionalBindings(Builder builderForValue) {
            if (this.additionalBindingsBuilder_ == null) {
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addAdditionalBindings(int index, Builder builderForValue) {
            if (this.additionalBindingsBuilder_ == null) {
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllAdditionalBindings(Iterable<? extends HttpRule> values2) {
            if (this.additionalBindingsBuilder_ == null) {
                this.ensureAdditionalBindingsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.additionalBindings_);
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearAdditionalBindings() {
            if (this.additionalBindingsBuilder_ == null) {
                this.additionalBindings_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFDFF;
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.clear();
            }
            return this;
        }

        public Builder removeAdditionalBindings(int index) {
            if (this.additionalBindingsBuilder_ == null) {
                this.ensureAdditionalBindingsIsMutable();
                this.additionalBindings_.remove(index);
                this.onChanged();
            } else {
                this.additionalBindingsBuilder_.remove(index);
            }
            return this;
        }

        public Builder getAdditionalBindingsBuilder(int index) {
            return this.getAdditionalBindingsFieldBuilder().getBuilder(index);
        }

        @Override
        public HttpRuleOrBuilder getAdditionalBindingsOrBuilder(int index) {
            if (this.additionalBindingsBuilder_ == null) {
                return this.additionalBindings_.get(index);
            }
            return this.additionalBindingsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends HttpRuleOrBuilder> getAdditionalBindingsOrBuilderList() {
            if (this.additionalBindingsBuilder_ != null) {
                return this.additionalBindingsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.additionalBindings_);
        }

        public Builder addAdditionalBindingsBuilder() {
            return this.getAdditionalBindingsFieldBuilder().addBuilder(HttpRule.getDefaultInstance());
        }

        public Builder addAdditionalBindingsBuilder(int index) {
            return this.getAdditionalBindingsFieldBuilder().addBuilder(index, HttpRule.getDefaultInstance());
        }

        public List<Builder> getAdditionalBindingsBuilderList() {
            return this.getAdditionalBindingsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<HttpRule, Builder, HttpRuleOrBuilder> getAdditionalBindingsFieldBuilder() {
            if (this.additionalBindingsBuilder_ == null) {
                this.additionalBindingsBuilder_ = new RepeatedFieldBuilderV3(this.additionalBindings_, (this.bitField0_ & 0x200) != 0, this.getParentForChildren(), this.isClean());
                this.additionalBindings_ = null;
            }
            return this.additionalBindingsBuilder_;
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

    public static enum PatternCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        GET(2),
        PUT(3),
        POST(4),
        DELETE(5),
        PATCH(6),
        CUSTOM(8),
        PATTERN_NOT_SET(0);

        private final int value;

        private PatternCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static PatternCase valueOf(int value) {
            return PatternCase.forNumber(value);
        }

        public static PatternCase forNumber(int value) {
            switch (value) {
                case 2: {
                    return GET;
                }
                case 3: {
                    return PUT;
                }
                case 4: {
                    return POST;
                }
                case 5: {
                    return DELETE;
                }
                case 6: {
                    return PATCH;
                }
                case 8: {
                    return CUSTOM;
                }
                case 0: {
                    return PATTERN_NOT_SET;
                }
            }
            return null;
        }

        @Override
        public int getNumber() {
            return this.value;
        }
    }
}

