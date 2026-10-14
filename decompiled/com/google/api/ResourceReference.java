/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ResourceProto;
import com.google.api.ResourceReferenceOrBuilder;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ResourceReference
extends GeneratedMessageV3
implements ResourceReferenceOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int TYPE_FIELD_NUMBER = 1;
    private volatile Object type_ = "";
    public static final int CHILD_TYPE_FIELD_NUMBER = 2;
    private volatile Object childType_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final ResourceReference DEFAULT_INSTANCE = new ResourceReference();
    private static final Parser<ResourceReference> PARSER = new AbstractParser<ResourceReference>(){

        @Override
        public ResourceReference parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ResourceReference.newBuilder();
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

    private ResourceReference(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ResourceReference() {
        this.type_ = "";
        this.childType_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ResourceReference();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ResourceProto.internal_static_google_api_ResourceReference_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ResourceProto.internal_static_google_api_ResourceReference_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceReference.class, Builder.class);
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

    @Override
    public String getChildType() {
        Object ref = this.childType_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.childType_ = s2;
        return s2;
    }

    @Override
    public ByteString getChildTypeBytes() {
        Object ref = this.childType_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.childType_ = b;
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            GeneratedMessageV3.writeString(output, 1, this.type_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.childType_)) {
            GeneratedMessageV3.writeString(output, 2, this.childType_);
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
        if (!GeneratedMessageV3.isStringEmpty(this.type_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.type_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.childType_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.childType_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResourceReference)) {
            return super.equals(obj);
        }
        ResourceReference other = (ResourceReference)obj;
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.getChildType().equals(other.getChildType())) {
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
        hash = 19 * hash + ResourceReference.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getType().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getChildType().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ResourceReference parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceReference parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceReference parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceReference parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceReference parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ResourceReference parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ResourceReference parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceReference parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceReference parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ResourceReference parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ResourceReference parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ResourceReference parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ResourceReference.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ResourceReference prototype) {
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

    public static ResourceReference getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ResourceReference> parser() {
        return PARSER;
    }

    public Parser<ResourceReference> getParserForType() {
        return PARSER;
    }

    @Override
    public ResourceReference getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ResourceReferenceOrBuilder {
        private int bitField0_;
        private Object type_ = "";
        private Object childType_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ResourceProto.internal_static_google_api_ResourceReference_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ResourceProto.internal_static_google_api_ResourceReference_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceReference.class, Builder.class);
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
            this.childType_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ResourceProto.internal_static_google_api_ResourceReference_descriptor;
        }

        @Override
        public ResourceReference getDefaultInstanceForType() {
            return ResourceReference.getDefaultInstance();
        }

        @Override
        public ResourceReference build() {
            ResourceReference result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ResourceReference buildPartial() {
            ResourceReference result = new ResourceReference(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ResourceReference result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.type_ = this.type_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.childType_ = this.childType_;
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
            if (other instanceof ResourceReference) {
                return this.mergeFrom((ResourceReference)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ResourceReference other) {
            if (other == ResourceReference.getDefaultInstance()) {
                return this;
            }
            if (!other.getType().isEmpty()) {
                this.type_ = other.type_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getChildType().isEmpty()) {
                this.childType_ = other.childType_;
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
                            this.type_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block10;
                        }
                        case 18: {
                            this.childType_ = input.readStringRequireUtf8();
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
            this.type_ = ResourceReference.getDefaultInstance().getType();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceReference.checkByteStringIsUtf8(value);
            this.type_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getChildType() {
            Object ref = this.childType_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.childType_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getChildTypeBytes() {
            Object ref = this.childType_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.childType_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setChildType(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.childType_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearChildType() {
            this.childType_ = ResourceReference.getDefaultInstance().getChildType();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setChildTypeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ResourceReference.checkByteStringIsUtf8(value);
            this.childType_ = value;
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

