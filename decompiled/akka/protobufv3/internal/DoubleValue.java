/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.AbstractParser;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.CodedOutputStream;
import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.DoubleValueOrBuilder;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.GeneratedMessageV3;
import akka.protobufv3.internal.Internal;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.Parser;
import akka.protobufv3.internal.UnknownFieldSet;
import akka.protobufv3.internal.WrappersProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class DoubleValue
extends GeneratedMessageV3
implements DoubleValueOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int VALUE_FIELD_NUMBER = 1;
    private double value_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final DoubleValue DEFAULT_INSTANCE = new DoubleValue();
    private static final Parser<DoubleValue> PARSER = new AbstractParser<DoubleValue>(){

        @Override
        public DoubleValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new DoubleValue(input, extensionRegistry);
        }
    };

    private DoubleValue(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private DoubleValue() {
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new DoubleValue();
    }

    @Override
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private DoubleValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 9: {
                        this.value_ = input.readDouble();
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
        return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_DoubleValue_fieldAccessorTable.ensureFieldAccessorsInitialized(DoubleValue.class, Builder.class);
    }

    @Override
    public double getValue() {
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
        if (this.value_ != 0.0) {
            output.writeDouble(1, this.value_);
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
        if (this.value_ != 0.0) {
            size += CodedOutputStream.computeDoubleSize(1, this.value_);
        }
        this.memoizedSize = size += this.unknownFields.getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleValue)) {
            return super.equals(obj);
        }
        DoubleValue other = (DoubleValue)obj;
        if (Double.doubleToLongBits(this.getValue()) != Double.doubleToLongBits(other.getValue())) {
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
        hash = 19 * hash + DoubleValue.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + Internal.hashLong(Double.doubleToLongBits(this.getValue()));
        this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
        return hash;
    }

    public static DoubleValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DoubleValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DoubleValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DoubleValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DoubleValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static DoubleValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static DoubleValue parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DoubleValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static DoubleValue parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static DoubleValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static DoubleValue parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static DoubleValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return DoubleValue.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DoubleValue prototype) {
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

    public static DoubleValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static DoubleValue of(double value) {
        return DoubleValue.newBuilder().setValue(value).build();
    }

    public static Parser<DoubleValue> parser() {
        return PARSER;
    }

    public Parser<DoubleValue> getParserForType() {
        return PARSER;
    }

    @Override
    public DoubleValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements DoubleValueOrBuilder {
        private double value_;

        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_DoubleValue_fieldAccessorTable.ensureFieldAccessorsInitialized(DoubleValue.class, Builder.class);
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
            this.value_ = 0.0;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_DoubleValue_descriptor;
        }

        @Override
        public DoubleValue getDefaultInstanceForType() {
            return DoubleValue.getDefaultInstance();
        }

        @Override
        public DoubleValue build() {
            DoubleValue result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public DoubleValue buildPartial() {
            DoubleValue result = new DoubleValue(this);
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
            if (other instanceof DoubleValue) {
                return this.mergeFrom((DoubleValue)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(DoubleValue other) {
            if (other == DoubleValue.getDefaultInstance()) {
                return this;
            }
            if (other.getValue() != 0.0) {
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
            DoubleValue parsedMessage = null;
            try {
                parsedMessage = (DoubleValue)PARSER.parsePartialFrom(input, extensionRegistry);
            }
            catch (InvalidProtocolBufferException e) {
                parsedMessage = (DoubleValue)e.getUnfinishedMessage();
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
        public double getValue() {
            return this.value_;
        }

        public Builder setValue(double value) {
            this.value_ = value;
            this.onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = 0.0;
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

