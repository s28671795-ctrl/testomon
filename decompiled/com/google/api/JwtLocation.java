/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.AuthProto;
import com.google.api.JwtLocationOrBuilder;
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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class JwtLocation
extends GeneratedMessageV3
implements JwtLocationOrBuilder {
    private static final long serialVersionUID = 0L;
    private int inCase_ = 0;
    private Object in_;
    public static final int HEADER_FIELD_NUMBER = 1;
    public static final int QUERY_FIELD_NUMBER = 2;
    public static final int COOKIE_FIELD_NUMBER = 4;
    public static final int VALUE_PREFIX_FIELD_NUMBER = 3;
    private volatile Object valuePrefix_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final JwtLocation DEFAULT_INSTANCE = new JwtLocation();
    private static final Parser<JwtLocation> PARSER = new AbstractParser<JwtLocation>(){

        @Override
        public JwtLocation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = JwtLocation.newBuilder();
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

    private JwtLocation(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private JwtLocation() {
        this.valuePrefix_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new JwtLocation();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AuthProto.internal_static_google_api_JwtLocation_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AuthProto.internal_static_google_api_JwtLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtLocation.class, Builder.class);
    }

    @Override
    public InCase getInCase() {
        return InCase.forNumber(this.inCase_);
    }

    @Override
    public boolean hasHeader() {
        return this.inCase_ == 1;
    }

    @Override
    public String getHeader() {
        Object ref = "";
        if (this.inCase_ == 1) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.inCase_ == 1) {
            this.in_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getHeaderBytes() {
        Object ref = "";
        if (this.inCase_ == 1) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.inCase_ == 1) {
                this.in_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasQuery() {
        return this.inCase_ == 2;
    }

    @Override
    public String getQuery() {
        Object ref = "";
        if (this.inCase_ == 2) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.inCase_ == 2) {
            this.in_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getQueryBytes() {
        Object ref = "";
        if (this.inCase_ == 2) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.inCase_ == 2) {
                this.in_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public boolean hasCookie() {
        return this.inCase_ == 4;
    }

    @Override
    public String getCookie() {
        Object ref = "";
        if (this.inCase_ == 4) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        if (this.inCase_ == 4) {
            this.in_ = s2;
        }
        return s2;
    }

    @Override
    public ByteString getCookieBytes() {
        Object ref = "";
        if (this.inCase_ == 4) {
            ref = this.in_;
        }
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            if (this.inCase_ == 4) {
                this.in_ = b;
            }
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getValuePrefix() {
        Object ref = this.valuePrefix_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.valuePrefix_ = s2;
        return s2;
    }

    @Override
    public ByteString getValuePrefixBytes() {
        Object ref = this.valuePrefix_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.valuePrefix_ = b;
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
        if (this.inCase_ == 1) {
            GeneratedMessageV3.writeString(output, 1, this.in_);
        }
        if (this.inCase_ == 2) {
            GeneratedMessageV3.writeString(output, 2, this.in_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.valuePrefix_)) {
            GeneratedMessageV3.writeString(output, 3, this.valuePrefix_);
        }
        if (this.inCase_ == 4) {
            GeneratedMessageV3.writeString(output, 4, this.in_);
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
        if (this.inCase_ == 1) {
            size += GeneratedMessageV3.computeStringSize(1, this.in_);
        }
        if (this.inCase_ == 2) {
            size += GeneratedMessageV3.computeStringSize(2, this.in_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.valuePrefix_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.valuePrefix_);
        }
        if (this.inCase_ == 4) {
            size += GeneratedMessageV3.computeStringSize(4, this.in_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof JwtLocation)) {
            return super.equals(obj);
        }
        JwtLocation other = (JwtLocation)obj;
        if (!this.getValuePrefix().equals(other.getValuePrefix())) {
            return false;
        }
        if (!this.getInCase().equals(other.getInCase())) {
            return false;
        }
        switch (this.inCase_) {
            case 1: {
                if (this.getHeader().equals(other.getHeader())) break;
                return false;
            }
            case 2: {
                if (this.getQuery().equals(other.getQuery())) break;
                return false;
            }
            case 4: {
                if (this.getCookie().equals(other.getCookie())) break;
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
        hash = 19 * hash + JwtLocation.getDescriptor().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getValuePrefix().hashCode();
        switch (this.inCase_) {
            case 1: {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getHeader().hashCode();
                break;
            }
            case 2: {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getQuery().hashCode();
                break;
            }
            case 4: {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getCookie().hashCode();
                break;
            }
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static JwtLocation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JwtLocation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JwtLocation parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JwtLocation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JwtLocation parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static JwtLocation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static JwtLocation parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static JwtLocation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static JwtLocation parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static JwtLocation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static JwtLocation parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static JwtLocation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return JwtLocation.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(JwtLocation prototype) {
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

    public static JwtLocation getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<JwtLocation> parser() {
        return PARSER;
    }

    public Parser<JwtLocation> getParserForType() {
        return PARSER;
    }

    @Override
    public JwtLocation getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements JwtLocationOrBuilder {
        private int inCase_ = 0;
        private Object in_;
        private int bitField0_;
        private Object valuePrefix_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return AuthProto.internal_static_google_api_JwtLocation_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AuthProto.internal_static_google_api_JwtLocation_fieldAccessorTable.ensureFieldAccessorsInitialized(JwtLocation.class, Builder.class);
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
            this.valuePrefix_ = "";
            this.inCase_ = 0;
            this.in_ = null;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AuthProto.internal_static_google_api_JwtLocation_descriptor;
        }

        @Override
        public JwtLocation getDefaultInstanceForType() {
            return JwtLocation.getDefaultInstance();
        }

        @Override
        public JwtLocation build() {
            JwtLocation result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public JwtLocation buildPartial() {
            JwtLocation result = new JwtLocation(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.buildPartialOneofs(result);
            this.onBuilt();
            return result;
        }

        private void buildPartial0(JwtLocation result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 8) != 0) {
                result.valuePrefix_ = this.valuePrefix_;
            }
        }

        private void buildPartialOneofs(JwtLocation result) {
            result.inCase_ = this.inCase_;
            result.in_ = this.in_;
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
            if (other instanceof JwtLocation) {
                return this.mergeFrom((JwtLocation)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(JwtLocation other) {
            if (other == JwtLocation.getDefaultInstance()) {
                return this;
            }
            if (!other.getValuePrefix().isEmpty()) {
                this.valuePrefix_ = other.valuePrefix_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            switch (other.getInCase()) {
                case HEADER: {
                    this.inCase_ = 1;
                    this.in_ = other.in_;
                    this.onChanged();
                    break;
                }
                case QUERY: {
                    this.inCase_ = 2;
                    this.in_ = other.in_;
                    this.onChanged();
                    break;
                }
                case COOKIE: {
                    this.inCase_ = 4;
                    this.in_ = other.in_;
                    this.onChanged();
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
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            String s2 = input.readStringRequireUtf8();
                            this.inCase_ = 1;
                            this.in_ = s2;
                            continue block12;
                        }
                        case 18: {
                            String s2 = input.readStringRequireUtf8();
                            this.inCase_ = 2;
                            this.in_ = s2;
                            continue block12;
                        }
                        case 26: {
                            this.valuePrefix_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block12;
                        }
                        case 34: {
                            String s2 = input.readStringRequireUtf8();
                            this.inCase_ = 4;
                            this.in_ = s2;
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
        public InCase getInCase() {
            return InCase.forNumber(this.inCase_);
        }

        public Builder clearIn() {
            this.inCase_ = 0;
            this.in_ = null;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasHeader() {
            return this.inCase_ == 1;
        }

        @Override
        public String getHeader() {
            Object ref = "";
            if (this.inCase_ == 1) {
                ref = this.in_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.inCase_ == 1) {
                    this.in_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getHeaderBytes() {
            Object ref = "";
            if (this.inCase_ == 1) {
                ref = this.in_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.inCase_ == 1) {
                    this.in_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setHeader(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.inCase_ = 1;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearHeader() {
            if (this.inCase_ == 1) {
                this.inCase_ = 0;
                this.in_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setHeaderBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            JwtLocation.checkByteStringIsUtf8(value);
            this.inCase_ = 1;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasQuery() {
            return this.inCase_ == 2;
        }

        @Override
        public String getQuery() {
            Object ref = "";
            if (this.inCase_ == 2) {
                ref = this.in_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.inCase_ == 2) {
                    this.in_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getQueryBytes() {
            Object ref = "";
            if (this.inCase_ == 2) {
                ref = this.in_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.inCase_ == 2) {
                    this.in_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setQuery(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.inCase_ = 2;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearQuery() {
            if (this.inCase_ == 2) {
                this.inCase_ = 0;
                this.in_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setQueryBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            JwtLocation.checkByteStringIsUtf8(value);
            this.inCase_ = 2;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasCookie() {
            return this.inCase_ == 4;
        }

        @Override
        public String getCookie() {
            Object ref = "";
            if (this.inCase_ == 4) {
                ref = this.in_;
            }
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                if (this.inCase_ == 4) {
                    this.in_ = s2;
                }
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getCookieBytes() {
            Object ref = "";
            if (this.inCase_ == 4) {
                ref = this.in_;
            }
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                if (this.inCase_ == 4) {
                    this.in_ = b;
                }
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setCookie(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.inCase_ = 4;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearCookie() {
            if (this.inCase_ == 4) {
                this.inCase_ = 0;
                this.in_ = null;
                this.onChanged();
            }
            return this;
        }

        public Builder setCookieBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            JwtLocation.checkByteStringIsUtf8(value);
            this.inCase_ = 4;
            this.in_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public String getValuePrefix() {
            Object ref = this.valuePrefix_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.valuePrefix_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getValuePrefixBytes() {
            Object ref = this.valuePrefix_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.valuePrefix_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setValuePrefix(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.valuePrefix_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearValuePrefix() {
            this.valuePrefix_ = JwtLocation.getDefaultInstance().getValuePrefix();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setValuePrefixBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            JwtLocation.checkByteStringIsUtf8(value);
            this.valuePrefix_ = value;
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

    public static enum InCase implements Internal.EnumLite,
    AbstractMessageLite.InternalOneOfEnum
    {
        HEADER(1),
        QUERY(2),
        COOKIE(4),
        IN_NOT_SET(0);

        private final int value;

        private InCase(int value) {
            this.value = value;
        }

        @Deprecated
        public static InCase valueOf(int value) {
            return InCase.forNumber(value);
        }

        public static InCase forNumber(int value) {
            switch (value) {
                case 1: {
                    return HEADER;
                }
                case 2: {
                    return QUERY;
                }
                case 4: {
                    return COOKIE;
                }
                case 0: {
                    return IN_NOT_SET;
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

