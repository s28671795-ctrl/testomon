/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.ExprOrBuilder;
import com.google.type.ExprProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Expr
extends GeneratedMessageV3
implements ExprOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int EXPRESSION_FIELD_NUMBER = 1;
    private volatile Object expression_ = "";
    public static final int TITLE_FIELD_NUMBER = 2;
    private volatile Object title_ = "";
    public static final int DESCRIPTION_FIELD_NUMBER = 3;
    private volatile Object description_ = "";
    public static final int LOCATION_FIELD_NUMBER = 4;
    private volatile Object location_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final Expr DEFAULT_INSTANCE = new Expr();
    private static final Parser<Expr> PARSER = new AbstractParser<Expr>(){

        @Override
        public Expr parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Expr.newBuilder();
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

    private Expr(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Expr() {
        this.expression_ = "";
        this.title_ = "";
        this.description_ = "";
        this.location_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Expr();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ExprProto.internal_static_google_type_Expr_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ExprProto.internal_static_google_type_Expr_fieldAccessorTable.ensureFieldAccessorsInitialized(Expr.class, Builder.class);
    }

    @Override
    public String getExpression() {
        Object ref = this.expression_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.expression_ = s2;
        return s2;
    }

    @Override
    public ByteString getExpressionBytes() {
        Object ref = this.expression_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.expression_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getTitle() {
        Object ref = this.title_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.title_ = s2;
        return s2;
    }

    @Override
    public ByteString getTitleBytes() {
        Object ref = this.title_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.title_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.expression_)) {
            GeneratedMessageV3.writeString(output, 1, this.expression_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
            GeneratedMessageV3.writeString(output, 2, this.title_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            GeneratedMessageV3.writeString(output, 3, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.location_)) {
            GeneratedMessageV3.writeString(output, 4, this.location_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.expression_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.expression_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.title_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.description_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.description_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.location_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.location_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Expr)) {
            return super.equals(obj);
        }
        Expr other = (Expr)obj;
        if (!this.getExpression().equals(other.getExpression())) {
            return false;
        }
        if (!this.getTitle().equals(other.getTitle())) {
            return false;
        }
        if (!this.getDescription().equals(other.getDescription())) {
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
        hash = 19 * hash + Expr.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getExpression().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getTitle().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getDescription().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getLocation().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Expr parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Expr parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Expr parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Expr parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Expr parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Expr parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Expr parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Expr parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Expr parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Expr parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Expr parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Expr parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Expr.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Expr prototype) {
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

    public static Expr getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Expr> parser() {
        return PARSER;
    }

    public Parser<Expr> getParserForType() {
        return PARSER;
    }

    @Override
    public Expr getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ExprOrBuilder {
        private int bitField0_;
        private Object expression_ = "";
        private Object title_ = "";
        private Object description_ = "";
        private Object location_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ExprProto.internal_static_google_type_Expr_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ExprProto.internal_static_google_type_Expr_fieldAccessorTable.ensureFieldAccessorsInitialized(Expr.class, Builder.class);
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
            this.expression_ = "";
            this.title_ = "";
            this.description_ = "";
            this.location_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ExprProto.internal_static_google_type_Expr_descriptor;
        }

        @Override
        public Expr getDefaultInstanceForType() {
            return Expr.getDefaultInstance();
        }

        @Override
        public Expr build() {
            Expr result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Expr buildPartial() {
            Expr result = new Expr(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(Expr result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.expression_ = this.expression_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.title_ = this.title_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.description_ = this.description_;
            }
            if ((from_bitField0_ & 8) != 0) {
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
            if (other instanceof Expr) {
                return this.mergeFrom((Expr)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Expr other) {
            if (other == Expr.getDefaultInstance()) {
                return this;
            }
            if (!other.getExpression().isEmpty()) {
                this.expression_ = other.expression_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getTitle().isEmpty()) {
                this.title_ = other.title_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getDescription().isEmpty()) {
                this.description_ = other.description_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getLocation().isEmpty()) {
                this.location_ = other.location_;
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
                            this.expression_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block12;
                        }
                        case 18: {
                            this.title_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 26: {
                            this.description_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block12;
                        }
                        case 34: {
                            this.location_ = input.readStringRequireUtf8();
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
        public String getExpression() {
            Object ref = this.expression_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.expression_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getExpressionBytes() {
            Object ref = this.expression_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.expression_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setExpression(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.expression_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearExpression() {
            this.expression_ = Expr.getDefaultInstance().getExpression();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setExpressionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Expr.checkByteStringIsUtf8(value);
            this.expression_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getTitle() {
            Object ref = this.title_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.title_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTitleBytes() {
            Object ref = this.title_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.title_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setTitle(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.title_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearTitle() {
            this.title_ = Expr.getDefaultInstance().getTitle();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setTitleBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Expr.checkByteStringIsUtf8(value);
            this.title_ = value;
            this.bitField0_ |= 2;
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
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDescription() {
            this.description_ = Expr.getDefaultInstance().getDescription();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDescriptionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Expr.checkByteStringIsUtf8(value);
            this.description_ = value;
            this.bitField0_ |= 4;
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
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearLocation() {
            this.location_ = Expr.getDefaultInstance().getLocation();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setLocationBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Expr.checkByteStringIsUtf8(value);
            this.location_ = value;
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

