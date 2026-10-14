/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.AbstractMessageLite;
import akka.protobufv3.internal.AbstractParser;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.GeneratedMessageV3;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.Parser;
import akka.protobufv3.internal.StringValueOrBuilder;
import akka.protobufv3.internal.UnknownFieldSet;
import akka.protobufv3.internal.WrappersProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class StringValue
extends GeneratedMessageV3
implements StringValueOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private volatile Object value_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final StringValue DEFAULT_INSTANCE = new StringValue();
    private static final Parser<StringValue> PARSER = new AbstractParser<StringValue>(){

        @Override
        public StringValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new StringValue(input, extensionRegistry);
        }
    };

    private StringValue(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private StringValue() {
        this.value_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new StringValue();
    }

    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private StringValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
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
                        String s2 = input.readStringRequireUtf8();
                        this.value_ = s2;
                        continue block10;
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
        return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
    }

    @Override
    public String getValue() {
        Object ref = this.value_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.value_ = s2;
        return s2;
    }

    @Override
    public ByteString getValueBytes() {
        Object ref = this.value_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.value_ = b;
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
        if (!this.getValueBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.value_);
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
        if (!this.getValueBytes().isEmpty()) {
            size += GeneratedMessageV3.computeStringSize(1, this.value_);
        }
        this.memoizedSize = size += this.unknownFields.getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StringValue)) {
            return super.equals(obj);
        }
        StringValue other = (StringValue)obj;
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
        hash = 19 * hash + StringValue.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getValue().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
        return hash;
    }

    public static StringValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static StringValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static StringValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static StringValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static StringValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static StringValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static StringValue parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static StringValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static StringValue parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static StringValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static StringValue parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static StringValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return StringValue.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(StringValue prototype) {
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

    public static StringValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static StringValue of(String value) {
        return StringValue.newBuilder().setValue(value).build();
    }

    public static Parser<StringValue> parser() {
        return PARSER;
    }

    public Parser<StringValue> getParserForType() {
        return PARSER;
    }

    @Override
    public StringValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements StringValueOrBuilder {
        private Object value_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_StringValue_fieldAccessorTable.ensureFieldAccessorsInitialized(StringValue.class, Builder.class);
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
            this.value_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_StringValue_descriptor;
        }

        @Override
        public StringValue getDefaultInstanceForType() {
            return StringValue.getDefaultInstance();
        }

        @Override
        public StringValue build() {
            StringValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public StringValue buildPartial() {
            StringValue result = new StringValue(this);
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
            if (other instanceof StringValue) {
                return this.mergeFrom((StringValue)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(StringValue other) {
            if (other == StringValue.getDefaultInstance()) {
                return this;
            }
            if (!other.getValue().isEmpty()) {
                this.value_ = other.value_;
                this.onChanged();
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
            StringValue parsedMessage = null;
            try {
                parsedMessage = (StringValue)PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (StringValue)e.getUnfinishedMessage();
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
        public String getValue() {
            Object ref = this.value_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.value_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getValueBytes() {
            Object ref = this.value_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.value_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.value_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = StringValue.getDefaultInstance().getValue();
            this.onChanged();
            return this;
        }

        public Builder setValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.value_ = value;
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

