/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ResourceDescriptorOrBuilder;
import com.google.api.ResourceProto;
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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResourceDescriptor
extends GeneratedMessageV3
implements ResourceDescriptorOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int TYPE_FIELD_NUMBER = 1;
    private volatile Object type_ = "";
    public static final int PATTERN_FIELD_NUMBER = 2;
    private LazyStringArrayList pattern_ = LazyStringArrayList.emptyList();
    public static final int NAME_FIELD_FIELD_NUMBER = 3;
    private volatile Object nameField_ = "";
    public static final int HISTORY_FIELD_NUMBER = 4;
    private int history_ = 0;
    public static final int PLURAL_FIELD_NUMBER = 5;
    private volatile Object plural_ = "";
    public static final int SINGULAR_FIELD_NUMBER = 6;
    private volatile Object singular_ = "";
    public static final int STYLE_FIELD_NUMBER = 10;
    private List<Integer> style_;
    private static final Internal.ListAdapter.Converter<Integer, Style> style_converter_ = new Internal.ListAdapter.Converter<Integer, Style>(){

        @Override
        public Style convert(Integer from) {
            Style result = Style.forNumber(from);
            return result == null ? Style.UNRECOGNIZED : result;
        }
    };
    private int styleMemoizedSerializedSize;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ResourceDescriptor DEFAULT_INSTANCE = new ResourceDescriptor();
    private static final Parser<ResourceDescriptor> PARSER = new AbstractParser<ResourceDescriptor>(){

        @Override
        public ResourceDescriptor parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ResourceDescriptor.newBuilder();
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

    private ResourceDescriptor(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ResourceDescriptor() {
        this.type_ = "";
        this.pattern_ = LazyStringArrayList.emptyList();
        this.nameField_ = "";
        this.history_ = 0;
        this.plural_ = "";
        this.singular_ = "";
        this.style_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ResourceDescriptor();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ResourceProto.internal_static_google_api_ResourceDescriptor_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ResourceProto.internal_static_google_api_ResourceDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceDescriptor.class, Builder.class);
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

    public ProtocolStringList getPatternList() {
        return this.pattern_;
    }

    @Override
    public int getPatternCount() {
        return this.pattern_.size();
    }

    @Override
    public String getPattern(int index) {
        return this.pattern_.get(index);
    }

    @Override
    public ByteString getPatternBytes(int index) {
        return this.pattern_.getByteString(index);
    }

    @Override
    public String getNameField() {
        Object ref = this.nameField_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.nameField_ = s2;
        return s2;
    }

    @Override
    public ByteString getNameFieldBytes() {
        Object ref = this.nameField_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.nameField_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getHistoryValue() {
        return this.history_;
    }

    @Override
    public History getHistory() {
        History result = History.forNumber(this.history_);
        return result == null ? History.UNRECOGNIZED : result;
    }

    @Override
    public String getPlural() {
        Object ref = this.plural_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.plural_ = s2;
        return s2;
    }

    @Override
    public ByteString getPluralBytes() {
        Object ref = this.plural_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.plural_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getSingular() {
        Object ref = this.singular_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.singular_ = s2;
        return s2;
    }

    @Override
    public ByteString getSingularBytes() {
        Object ref = this.singular_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.singular_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<Style> getStyleList() {
        return new Internal.ListAdapter<Integer, Style>(this.style_, style_converter_);
    }

    @Override
    public int getStyleCount() {
        return this.style_.size();
    }

    @Override
    public Style getStyle(int index) {
        return style_converter_.convert(this.style_.get(index));
    }

    @Override
    public List<Integer> getStyleValueList() {
        return this.style_;
    }

    @Override
    public int getStyleValue(int index) {
        return this.style_.get(index);
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
        this.getSerializedSize();
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            GeneratedMessageV3.writeString(output, 1, this.type_);
        }
        for (i = 0; i < this.pattern_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 2, this.pattern_.getRaw(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.nameField_)) {
            GeneratedMessageV3.writeString(output, 3, this.nameField_);
        }
        if (this.history_ != History.HISTORY_UNSPECIFIED.getNumber()) {
            output.writeEnum(4, this.history_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.plural_)) {
            GeneratedMessageV3.writeString(output, 5, this.plural_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.singular_)) {
            GeneratedMessageV3.writeString(output, 6, this.singular_);
        }
        if (this.getStyleList().size() > 0) {
            output.writeUInt32NoTag(82);
            output.writeUInt32NoTag(this.styleMemoizedSerializedSize);
        }
        for (i = 0; i < this.style_.size(); ++i) {
            output.writeEnumNoTag(this.style_.get(i));
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.type_);
        }
        int dataSize = 0;
        for (i = 0; i < this.pattern_.size(); ++i) {
            dataSize += ResourceDescriptor.computeStringSizeNoTag(this.pattern_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getPatternList().size();
        if (!GeneratedMessageV3.isStringEmpty(this.nameField_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.nameField_);
        }
        if (this.history_ != History.HISTORY_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(4, this.history_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.plural_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.plural_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.singular_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.singular_);
        }
        dataSize = 0;
        for (i = 0; i < this.style_.size(); ++i) {
            dataSize += CodedOutputStream.computeEnumSizeNoTag(this.style_.get(i));
        }
        size += dataSize;
        if (!this.getStyleList().isEmpty()) {
            ++size;
            size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        }
        this.styleMemoizedSerializedSize = dataSize;
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResourceDescriptor)) {
            return super.equals(obj);
        }
        ResourceDescriptor other = (ResourceDescriptor)obj;
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.getPatternList().equals(other.getPatternList())) {
            return false;
        }
        if (!this.getNameField().equals(other.getNameField())) {
            return false;
        }
        if (this.history_ != other.history_) {
            return false;
        }
        if (!this.getPlural().equals(other.getPlural())) {
            return false;
        }
        if (!this.getSingular().equals(other.getSingular())) {
            return false;
        }
        if (!this.style_.equals(other.style_)) {
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
        hash = 19 * hash + ResourceDescriptor.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getType().hashCode();
        if (this.getPatternCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getPatternList().hashCode();
        }
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getNameField().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.history_;
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getPlural().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getSingular().hashCode();
        if (this.getStyleCount() > 0) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.style_.hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ResourceDescriptor parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceDescriptor parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceDescriptor parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceDescriptor parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceDescriptor parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceDescriptor parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceDescriptor parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceDescriptor parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceDescriptor parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ResourceDescriptor parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceDescriptor parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceDescriptor parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ResourceDescriptor.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ResourceDescriptor prototype) {
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

    public static ResourceDescriptor getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ResourceDescriptor> parser() {
        return PARSER;
    }

    public Parser<ResourceDescriptor> getParserForType() {
        return PARSER;
    }

    @Override
    public ResourceDescriptor getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ResourceDescriptorOrBuilder {
        private int bitField0_;
        private Object type_ = "";
        private LazyStringArrayList pattern_ = LazyStringArrayList.emptyList();
        private Object nameField_ = "";
        private int history_ = 0;
        private Object plural_ = "";
        private Object singular_ = "";
        private List<Integer> style_ = Collections.emptyList();

        public static final Descriptors.Descriptor getDescriptor() {
            return ResourceProto.internal_static_google_api_ResourceDescriptor_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ResourceProto.internal_static_google_api_ResourceDescriptor_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceDescriptor.class, Builder.class);
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
            this.type_ = "";
            this.pattern_ = LazyStringArrayList.emptyList();
            this.nameField_ = "";
            this.history_ = 0;
            this.plural_ = "";
            this.singular_ = "";
            this.style_ = Collections.emptyList();
            this.bitField0_ &= 0xFFFFFFBF;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ResourceProto.internal_static_google_api_ResourceDescriptor_descriptor;
        }

        @Override
        public ResourceDescriptor getDefaultInstanceForType() {
            return ResourceDescriptor.getDefaultInstance();
        }

        @Override
        public ResourceDescriptor build() {
            ResourceDescriptor result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ResourceDescriptor buildPartial() {
            ResourceDescriptor result = new ResourceDescriptor(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(ResourceDescriptor result) {
            if ((this.bitField0_ & 0x40) != 0) {
                this.style_ = Collections.unmodifiableList(this.style_);
                this.bitField0_ &= 0xFFFFFFBF;
            }
            result.style_ = this.style_;
        }

        private void buildPartial0(ResourceDescriptor result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.type_ = this.type_;
            }
            if ((from_bitField0_ & 2) != 0) {
                this.pattern_.makeImmutable();
                result.pattern_ = this.pattern_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.nameField_ = this.nameField_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.history_ = this.history_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.plural_ = this.plural_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.singular_ = this.singular_;
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
            if (other instanceof ResourceDescriptor) {
                return this.mergeFrom((ResourceDescriptor)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ResourceDescriptor other) {
            if (other == ResourceDescriptor.getDefaultInstance()) {
                return this;
            }
            if (!other.getType().isEmpty()) {
                this.type_ = other.type_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.pattern_.isEmpty()) {
                if (this.pattern_.isEmpty()) {
                    this.pattern_ = other.pattern_;
                    this.bitField0_ |= 2;
                } else {
                    this.ensurePatternIsMutable();
                    this.pattern_.addAll(other.pattern_);
                }
                this.onChanged();
            }
            if (!other.getNameField().isEmpty()) {
                this.nameField_ = other.nameField_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (other.history_ != 0) {
                this.setHistoryValue(other.getHistoryValue());
            }
            if (!other.getPlural().isEmpty()) {
                this.plural_ = other.plural_;
                this.bitField0_ |= 0x10;
                this.onChanged();
            }
            if (!other.getSingular().isEmpty()) {
                this.singular_ = other.singular_;
                this.bitField0_ |= 0x20;
                this.onChanged();
            }
            if (!other.style_.isEmpty()) {
                if (this.style_.isEmpty()) {
                    this.style_ = other.style_;
                    this.bitField0_ &= 0xFFFFFFBF;
                } else {
                    this.ensureStyleIsMutable();
                    this.style_.addAll(other.style_);
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
                block16: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block16;
                        }
                        case 10: {
                            this.type_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block16;
                        }
                        case 18: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensurePatternIsMutable();
                            this.pattern_.add(s2);
                            continue block16;
                        }
                        case 26: {
                            this.nameField_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block16;
                        }
                        case 32: {
                            this.history_ = input.readEnum();
                            this.bitField0_ |= 8;
                            continue block16;
                        }
                        case 42: {
                            this.plural_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
                            continue block16;
                        }
                        case 50: {
                            this.singular_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block16;
                        }
                        case 80: {
                            int tmpRaw = input.readEnum();
                            this.ensureStyleIsMutable();
                            this.style_.add(tmpRaw);
                            continue block16;
                        }
                        case 82: {
                            int length = input.readRawVarint32();
                            int oldLimit = input.pushLimit(length);
                            while (input.getBytesUntilLimit() > 0) {
                                int tmpRaw = input.readEnum();
                                this.ensureStyleIsMutable();
                                this.style_.add(tmpRaw);
                            }
                            input.popLimit(oldLimit);
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
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearType() {
            this.type_ = ResourceDescriptor.getDefaultInstance().getType();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceDescriptor.checkByteStringIsUtf8(value);
            this.type_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        private void ensurePatternIsMutable() {
            if (!this.pattern_.isModifiable()) {
                this.pattern_ = new LazyStringArrayList(this.pattern_);
            }
            this.bitField0_ |= 2;
        }

        public ProtocolStringList getPatternList() {
            this.pattern_.makeImmutable();
            return this.pattern_;
        }

        @Override
        public int getPatternCount() {
            return this.pattern_.size();
        }

        @Override
        public String getPattern(int index) {
            return this.pattern_.get(index);
        }

        @Override
        public ByteString getPatternBytes(int index) {
            return this.pattern_.getByteString(index);
        }

        public Builder setPattern(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensurePatternIsMutable();
            this.pattern_.set(index, value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addPattern(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensurePatternIsMutable();
            this.pattern_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder addAllPattern(Iterable<String> values2) {
            this.ensurePatternIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.pattern_);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearPattern() {
            this.pattern_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder addPatternBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceDescriptor.checkByteStringIsUtf8(value);
            this.ensurePatternIsMutable();
            this.pattern_.add(value);
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getNameField() {
            Object ref = this.nameField_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.nameField_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNameFieldBytes() {
            Object ref = this.nameField_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.nameField_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setNameField(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.nameField_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearNameField() {
            this.nameField_ = ResourceDescriptor.getDefaultInstance().getNameField();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setNameFieldBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceDescriptor.checkByteStringIsUtf8(value);
            this.nameField_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public int getHistoryValue() {
            return this.history_;
        }

        public Builder setHistoryValue(int value) {
            this.history_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public History getHistory() {
            History result = History.forNumber(this.history_);
            return result == null ? History.UNRECOGNIZED : result;
        }

        public Builder setHistory(History value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 8;
            this.history_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearHistory() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.history_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getPlural() {
            Object ref = this.plural_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.plural_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPluralBytes() {
            Object ref = this.plural_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.plural_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPlural(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.plural_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearPlural() {
            this.plural_ = ResourceDescriptor.getDefaultInstance().getPlural();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setPluralBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceDescriptor.checkByteStringIsUtf8(value);
            this.plural_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        @Override
        public String getSingular() {
            Object ref = this.singular_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.singular_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSingularBytes() {
            Object ref = this.singular_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.singular_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSingular(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.singular_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearSingular() {
            this.singular_ = ResourceDescriptor.getDefaultInstance().getSingular();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setSingularBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceDescriptor.checkByteStringIsUtf8(value);
            this.singular_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        private void ensureStyleIsMutable() {
            if ((this.bitField0_ & 0x40) == 0) {
                this.style_ = new ArrayList<Integer>(this.style_);
                this.bitField0_ |= 0x40;
            }
        }

        @Override
        public List<Style> getStyleList() {
            return new Internal.ListAdapter(this.style_, style_converter_);
        }

        @Override
        public int getStyleCount() {
            return this.style_.size();
        }

        @Override
        public Style getStyle(int index) {
            return (Style)style_converter_.convert(this.style_.get(index));
        }

        public Builder setStyle(int index, Style value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureStyleIsMutable();
            this.style_.set(index, value.getNumber());
            this.onChanged();
            return this;
        }

        public Builder addStyle(Style value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureStyleIsMutable();
            this.style_.add(value.getNumber());
            this.onChanged();
            return this;
        }

        public Builder addAllStyle(Iterable<? extends Style> values2) {
            this.ensureStyleIsMutable();
            for (Style style : values2) {
                this.style_.add(style.getNumber());
            }
            this.onChanged();
            return this;
        }

        public Builder clearStyle() {
            this.style_ = Collections.emptyList();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        @Override
        public List<Integer> getStyleValueList() {
            return Collections.unmodifiableList(this.style_);
        }

        @Override
        public int getStyleValue(int index) {
            return this.style_.get(index);
        }

        public Builder setStyleValue(int index, int value) {
            this.ensureStyleIsMutable();
            this.style_.set(index, value);
            this.onChanged();
            return this;
        }

        public Builder addStyleValue(int value) {
            this.ensureStyleIsMutable();
            this.style_.add(value);
            this.onChanged();
            return this;
        }

        public Builder addAllStyleValue(Iterable<Integer> values2) {
            this.ensureStyleIsMutable();
            for (int value : values2) {
                this.style_.add(value);
            }
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

    public static enum History implements ProtocolMessageEnum
    {
        HISTORY_UNSPECIFIED(0),
        ORIGINALLY_SINGLE_PATTERN(1),
        FUTURE_MULTI_PATTERN(2),
        UNRECOGNIZED(-1);

        public static final int HISTORY_UNSPECIFIED_VALUE = 0;
        public static final int ORIGINALLY_SINGLE_PATTERN_VALUE = 1;
        public static final int FUTURE_MULTI_PATTERN_VALUE = 2;
        private static final Internal.EnumLiteMap<History> internalValueMap;
        private static final History[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static History valueOf(int value) {
            return History.forNumber(value);
        }

        public static History forNumber(int value) {
            switch (value) {
                case 0: {
                    return HISTORY_UNSPECIFIED;
                }
                case 1: {
                    return ORIGINALLY_SINGLE_PATTERN;
                }
                case 2: {
                    return FUTURE_MULTI_PATTERN;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<History> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return History.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return History.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return ResourceDescriptor.getDescriptor().getEnumTypes().get(0);
        }

        public static History valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != History.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private History(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<History>(){

                @Override
                public History findValueByNumber(int number) {
                    return History.forNumber(number);
                }
            };
            VALUES = History.values();
        }
    }

    public static enum Style implements ProtocolMessageEnum
    {
        STYLE_UNSPECIFIED(0),
        DECLARATIVE_FRIENDLY(1),
        UNRECOGNIZED(-1);

        public static final int STYLE_UNSPECIFIED_VALUE = 0;
        public static final int DECLARATIVE_FRIENDLY_VALUE = 1;
        private static final Internal.EnumLiteMap<Style> internalValueMap;
        private static final Style[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static Style valueOf(int value) {
            return Style.forNumber(value);
        }

        public static Style forNumber(int value) {
            switch (value) {
                case 0: {
                    return STYLE_UNSPECIFIED;
                }
                case 1: {
                    return DECLARATIVE_FRIENDLY;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<Style> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            if (this == UNRECOGNIZED) {
                throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
            }
            return Style.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return Style.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return ResourceDescriptor.getDescriptor().getEnumTypes().get(1);
        }

        public static Style valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != Style.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private Style(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<Style>(){

                @Override
                public Style findValueByNumber(int number) {
                    return Style.forNumber(number);
                }
            };
            VALUES = Style.values();
        }
    }
}

