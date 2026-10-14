/*
 * Decompiled with CFR 0.152.
 */
package com.google.rpc;

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
import com.google.rpc.ErrorDetailsProto;
import com.google.rpc.LocalizedMessageOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class LocalizedMessage
extends GeneratedMessageV3
implements LocalizedMessageOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LOCALE_FIELD_NUMBER = 1;
    private volatile Object locale_ = "";
    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile Object message_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final LocalizedMessage DEFAULT_INSTANCE = new LocalizedMessage();
    private static final Parser<LocalizedMessage> PARSER = new AbstractParser<LocalizedMessage>(){

        @Override
        public LocalizedMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = LocalizedMessage.newBuilder();
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

    private LocalizedMessage(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private LocalizedMessage() {
        this.locale_ = "";
        this.message_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new LocalizedMessage();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ErrorDetailsProto.internal_static_google_rpc_LocalizedMessage_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ErrorDetailsProto.internal_static_google_rpc_LocalizedMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalizedMessage.class, Builder.class);
    }

    @Override
    public String getLocale() {
        Object ref = this.locale_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.locale_ = s2;
        return s2;
    }

    @Override
    public ByteString getLocaleBytes() {
        Object ref = this.locale_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.locale_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getMessage() {
        Object ref = this.message_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.message_ = s2;
        return s2;
    }

    @Override
    public ByteString getMessageBytes() {
        Object ref = this.message_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.message_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.locale_)) {
            GeneratedMessageV3.writeString(output, 1, this.locale_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
            GeneratedMessageV3.writeString(output, 2, this.message_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.locale_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.locale_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.message_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocalizedMessage)) {
            return super.equals(obj);
        }
        LocalizedMessage other = (LocalizedMessage)obj;
        if (!this.getLocale().equals(other.getLocale())) {
            return false;
        }
        if (!this.getMessage().equals(other.getMessage())) {
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
        hash = 19 * hash + LocalizedMessage.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getLocale().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getMessage().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static LocalizedMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static LocalizedMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static LocalizedMessage parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LocalizedMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static LocalizedMessage parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static LocalizedMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static LocalizedMessage parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static LocalizedMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return LocalizedMessage.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(LocalizedMessage prototype) {
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

    public static LocalizedMessage getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<LocalizedMessage> parser() {
        return PARSER;
    }

    public Parser<LocalizedMessage> getParserForType() {
        return PARSER;
    }

    @Override
    public LocalizedMessage getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements LocalizedMessageOrBuilder {
        private int bitField0_;
        private Object locale_ = "";
        private Object message_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ErrorDetailsProto.internal_static_google_rpc_LocalizedMessage_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ErrorDetailsProto.internal_static_google_rpc_LocalizedMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalizedMessage.class, Builder.class);
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
            this.locale_ = "";
            this.message_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ErrorDetailsProto.internal_static_google_rpc_LocalizedMessage_descriptor;
        }

        @Override
        public LocalizedMessage getDefaultInstanceForType() {
            return LocalizedMessage.getDefaultInstance();
        }

        @Override
        public LocalizedMessage build() {
            LocalizedMessage result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public LocalizedMessage buildPartial() {
            LocalizedMessage result = new LocalizedMessage(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(LocalizedMessage result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.locale_ = this.locale_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.message_ = this.message_;
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
            if (other instanceof LocalizedMessage) {
                return this.mergeFrom((LocalizedMessage)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(LocalizedMessage other) {
            if (other == LocalizedMessage.getDefaultInstance()) {
                return this;
            }
            if (!other.getLocale().isEmpty()) {
                this.locale_ = other.locale_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getMessage().isEmpty()) {
                this.message_ = other.message_;
                this.bitField0_ |= 2;
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
                block10: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        case 10: {
                            this.locale_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.message_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block10;
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
        public String getLocale() {
            Object ref = this.locale_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.locale_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLocaleBytes() {
            Object ref = this.locale_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.locale_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLocale(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.locale_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearLocale() {
            this.locale_ = LocalizedMessage.getDefaultInstance().getLocale();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setLocaleBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LocalizedMessage.checkByteStringIsUtf8(value);
            this.locale_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getMessage() {
            Object ref = this.message_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.message_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getMessageBytes() {
            Object ref = this.message_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.message_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setMessage(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.message_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearMessage() {
            this.message_ = LocalizedMessage.getDefaultInstance().getMessage();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setMessageBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            LocalizedMessage.checkByteStringIsUtf8(value);
            this.message_ = value;
            this.bitField0_ |= 2;
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

