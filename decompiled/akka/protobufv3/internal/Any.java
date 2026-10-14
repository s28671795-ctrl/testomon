/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.AbstractMessageLite;
import akka.protobufv3.internal.AbstractParser;
import akka.protobufv3.internal.AnyOrBuilder;
import akka.protobufv3.internal.AnyProto;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.GeneratedMessageV3;
import akka.protobufv3.internal.Internal;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.Parser;
import akka.protobufv3.internal.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Any
extends GeneratedMessageV3
implements AnyOrBuilder {
    private static final long serialVersionUID = 0L;
    private volatile Message cachedUnpackValue;
    public static final int TYPE_URL_FIELD_NUMBER = 1;
    private volatile Object typeUrl_;
    public static final int VALUE_FIELD_NUMBER = 2;
    private ByteString value_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Any DEFAULT_INSTANCE = new Any();
    private static final Parser<Any> PARSER = new AbstractParser<Any>(){

        @Override
        public Any parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Any(input, extensionRegistry);
        }
    };

    private Any(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Any() {
        this.typeUrl_ = "";
        this.value_ = ByteString.EMPTY;
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Any();
    }

    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Any(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
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
                        String s2 = input.readStringRequireUtf8();
                        this.typeUrl_ = s2;
                        continue block11;
                    }
                    case 18: {
                        this.value_ = input.readBytes();
                        continue block11;
                    }
                }
                if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue;
                done = true;
            }
        }
        catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        }
        catch (IOException e) {
            throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
        }
        finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
        }
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return AnyProto.internal_static_google_protobuf_Any_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
    }

    private static String getTypeUrl(String typeUrlPrefix, Descriptors.Descriptor descriptor) {
        return typeUrlPrefix.endsWith("/") ? typeUrlPrefix + descriptor.getFullName() : typeUrlPrefix + "/" + descriptor.getFullName();
    }

    private static String getTypeNameFromTypeUrl(String typeUrl) {
        int pos = typeUrl.lastIndexOf(47);
        return pos == -1 ? "" : typeUrl.substring(pos + 1);
    }

    public static <T extends Message> Any pack(T message) {
        return Any.newBuilder().setTypeUrl(Any.getTypeUrl("type.googleapis.com", message.getDescriptorForType())).setValue(message.toByteString()).build();
    }

    public static <T extends Message> Any pack(T message, String typeUrlPrefix) {
        return Any.newBuilder().setTypeUrl(Any.getTypeUrl(typeUrlPrefix, message.getDescriptorForType())).setValue(message.toByteString()).build();
    }

    public <T extends Message> boolean is(Class<T> clazz) {
        Message defaultInstance = (Message)Internal.getDefaultInstance(clazz);
        return Any.getTypeNameFromTypeUrl(this.getTypeUrl()).equals(defaultInstance.getDescriptorForType().getFullName());
    }

    public <T extends Message> T unpack(Class<T> clazz) throws InvalidProtocolBufferException {
        Message result;
        boolean invalidClazz = false;
        if (this.cachedUnpackValue != null) {
            if (this.cachedUnpackValue.getClass() == clazz) {
                return (T)this.cachedUnpackValue;
            }
            invalidClazz = true;
        }
        if (invalidClazz || !this.is(clazz)) {
            throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");
        }
        Message defaultInstance = (Message)Internal.getDefaultInstance(clazz);
        this.cachedUnpackValue = result = defaultInstance.getParserForType().parseFrom(this.getValue());
        return (T)result;
    }

    @Override
    public String getTypeUrl() {
        Object ref = this.typeUrl_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.typeUrl_ = s2;
        return s2;
    }

    @Override
    public ByteString getTypeUrlBytes() {
        Object ref = this.typeUrl_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.typeUrl_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public ByteString getValue() {
        return this.value_;
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
        if (!this.getTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            output.writeBytes(2, this.value_);
        }
        this.unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        if (!this.getTypeUrlBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            size += CodedOutputStream.computeBytesSize(2, this.value_);
        }
        this.memoizedSize = size += this.unknownFields.getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Any)) {
            return super.equals(obj);
        }
        Any other = (Any)obj;
        if (!this.getTypeUrl().equals(other.getTypeUrl())) {
            return false;
        }
        if (!this.getValue().equals(other.getValue())) {
            return false;
        }
        return this.unknownFields.equals(other.unknownFields);
    }

    @Override
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = 41;
        hash = 19 * hash + Any.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getTypeUrl().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getValue().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
        return hash;
    }

    public static Any parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Any parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Any parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Any parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Any parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Any parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Any parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Any parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Any parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Any.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Any prototype) {
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

    public static Any getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Any> parser() {
        return PARSER;
    }

    public Parser<Any> getParserForType() {
        return PARSER;
    }

    @Override
    public Any getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements AnyOrBuilder {
        private Object typeUrl_ = "";
        private ByteString value_ = ByteString.EMPTY;

        public static final Descriptors.Descriptor getDescriptor() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
        }

        private Builder() {
            this.maybeForceBuilderInitialization();
        }

        private Builder(GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            this.maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                // empty if block
            }
        }

        @Override
        public Builder clear() {
            super.clear();
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }

        @Override
        public Any getDefaultInstanceForType() {
            return Any.getDefaultInstance();
        }

        @Override
        public Any build() {
            Any result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Any buildPartial() {
            Any result = new Any(this);
            result.typeUrl_ = this.typeUrl_;
            result.value_ = this.value_;
            this.onBuilt();
            return result;
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
            if (other instanceof Any) {
                return this.mergeFrom((Any)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Any other) {
            if (other == Any.getDefaultInstance()) {
                return this;
            }
            if (!other.getTypeUrl().isEmpty()) {
                this.typeUrl_ = other.typeUrl_;
                this.onChanged();
            }
            if (other.getValue() != ByteString.EMPTY) {
                this.setValue(other.getValue());
            }
            this.mergeUnknownFields(other.unknownFields);
            this.onChanged();
            return this;
        }

        @Override
        public final boolean isInitialized() {
            return true;
        }

        @Override
        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Any parsedMessage = null;
            try {
                parsedMessage = (Any)PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (Any)e.getUnfinishedMessage();
                throw e.unwrapIOException();
            }
            finally {
                if (parsedMessage != null) {
                    this.mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        @Override
        public String getTypeUrl() {
            Object ref = this.typeUrl_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.typeUrl_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTypeUrlBytes() {
            Object ref = this.typeUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.typeUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setTypeUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.typeUrl_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearTypeUrl() {
            this.typeUrl_ = Any.getDefaultInstance().getTypeUrl();
            this.onChanged();
            return this;
        }

        public Builder setTypeUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.typeUrl_ = value;
            this.onChanged();
            return this;
        }

        @Override
        public ByteString getValue() {
            return this.value_;
        }

        public Builder setValue(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.value_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = Any.getDefaultInstance().getValue();
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

