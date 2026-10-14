/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ConsumerProto;
import com.google.api.ProjectPropertiesOrBuilder;
import com.google.api.Property;
import com.google.api.PropertyOrBuilder;
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

public final class ProjectProperties
extends GeneratedMessageV3
implements ProjectPropertiesOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int PROPERTIES_FIELD_NUMBER = 1;
    private List<Property> properties_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ProjectProperties DEFAULT_INSTANCE = new ProjectProperties();
    private static final Parser<ProjectProperties> PARSER = new AbstractParser<ProjectProperties>(){

        @Override
        public ProjectProperties parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ProjectProperties.newBuilder();
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

    private ProjectProperties(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ProjectProperties() {
        this.properties_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ProjectProperties();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ConsumerProto.internal_static_google_api_ProjectProperties_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ConsumerProto.internal_static_google_api_ProjectProperties_fieldAccessorTable.ensureFieldAccessorsInitialized(ProjectProperties.class, Builder.class);
    }

    @Override
    public List<Property> getPropertiesList() {
        return this.properties_;
    }

    @Override
    public List<? extends PropertyOrBuilder> getPropertiesOrBuilderList() {
        return this.properties_;
    }

    @Override
    public int getPropertiesCount() {
        return this.properties_.size();
    }

    @Override
    public Property getProperties(int index) {
        return this.properties_.get(index);
    }

    @Override
    public PropertyOrBuilder getPropertiesOrBuilder(int index) {
        return this.properties_.get(index);
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
        for (int i = 0; i < this.properties_.size(); ++i) {
            output.writeMessage(1, this.properties_.get(i));
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
        for (int i = 0; i < this.properties_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.properties_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProjectProperties)) {
            return super.equals(obj);
        }
        ProjectProperties other = (ProjectProperties)obj;
        if (!this.getPropertiesList().equals(other.getPropertiesList())) {
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
        hash = 19 * hash + ProjectProperties.getDescriptor().hashCode();
        if (this.getPropertiesCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPropertiesList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ProjectProperties parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ProjectProperties parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ProjectProperties parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ProjectProperties parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ProjectProperties parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ProjectProperties parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ProjectProperties parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ProjectProperties parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ProjectProperties parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ProjectProperties parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ProjectProperties parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ProjectProperties parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ProjectProperties.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ProjectProperties prototype) {
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

    public static ProjectProperties getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ProjectProperties> parser() {
        return PARSER;
    }

    public Parser<ProjectProperties> getParserForType() {
        return PARSER;
    }

    @Override
    public ProjectProperties getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ProjectPropertiesOrBuilder {
        private int bitField0_;
        private List<Property> properties_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Property, Property.Builder, PropertyOrBuilder> propertiesBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ConsumerProto.internal_static_google_api_ProjectProperties_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ConsumerProto.internal_static_google_api_ProjectProperties_fieldAccessorTable.ensureFieldAccessorsInitialized(ProjectProperties.class, Builder.class);
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
            if (this.propertiesBuilder_ == null) {
                this.properties_ = Collections.emptyList();
            } else {
                this.properties_ = null;
                this.propertiesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ConsumerProto.internal_static_google_api_ProjectProperties_descriptor;
        }

        @Override
        public ProjectProperties getDefaultInstanceForType() {
            return ProjectProperties.getDefaultInstance();
        }

        @Override
        public ProjectProperties build() {
            ProjectProperties result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ProjectProperties buildPartial() {
            ProjectProperties result = new ProjectProperties(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(ProjectProperties result) {
            if (this.propertiesBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.properties_ = Collections.unmodifiableList(this.properties_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.properties_ = this.properties_;
            } else {
                result.properties_ = this.propertiesBuilder_.build();
            }
        }

        private void buildPartial0(ProjectProperties result) {
            int from_bitField0_ = this.bitField0_;
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
            if (other instanceof ProjectProperties) {
                return this.mergeFrom((ProjectProperties)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ProjectProperties other) {
            if (other == ProjectProperties.getDefaultInstance()) {
                return this;
            }
            if (this.propertiesBuilder_ == null) {
                if (!other.properties_.isEmpty()) {
                    if (this.properties_.isEmpty()) {
                        this.properties_ = other.properties_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensurePropertiesIsMutable();
                        this.properties_.addAll(other.properties_);
                    }
                    this.onChanged();
                }
            } else if (!other.properties_.isEmpty()) {
                if (this.propertiesBuilder_.isEmpty()) {
                    this.propertiesBuilder_.dispose();
                    this.propertiesBuilder_ = null;
                    this.properties_ = other.properties_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.propertiesBuilder_ = alwaysUseFieldBuilders ? this.getPropertiesFieldBuilder() : null;
                } else {
                    this.propertiesBuilder_.addAllMessages(other.properties_);
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 10: {
                            Property m4 = input.readMessage(Property.parser(), extensionRegistry);
                            if (this.propertiesBuilder_ == null) {
                                this.ensurePropertiesIsMutable();
                                this.properties_.add(m4);
                                continue block9;
                            }
                            this.propertiesBuilder_.addMessage(m4);
                            continue block9;
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

        private void ensurePropertiesIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.properties_ = new ArrayList<Property>(this.properties_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<Property> getPropertiesList() {
            if (this.propertiesBuilder_ == null) {
                return Collections.unmodifiableList(this.properties_);
            }
            return this.propertiesBuilder_.getMessageList();
        }

        @Override
        public int getPropertiesCount() {
            if (this.propertiesBuilder_ == null) {
                return this.properties_.size();
            }
            return this.propertiesBuilder_.getCount();
        }

        @Override
        public Property getProperties(int index) {
            if (this.propertiesBuilder_ == null) {
                return this.properties_.get(index);
            }
            return this.propertiesBuilder_.getMessage(index);
        }

        public Builder setProperties(int index, Property value) {
            if (this.propertiesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePropertiesIsMutable();
                this.properties_.set(index, value);
                this.onChanged();
            } else {
                this.propertiesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setProperties(int index, Property.Builder builderForValue) {
            if (this.propertiesBuilder_ == null) {
                this.ensurePropertiesIsMutable();
                this.properties_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.propertiesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addProperties(Property value) {
            if (this.propertiesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePropertiesIsMutable();
                this.properties_.add(value);
                this.onChanged();
            } else {
                this.propertiesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addProperties(int index, Property value) {
            if (this.propertiesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensurePropertiesIsMutable();
                this.properties_.add(index, value);
                this.onChanged();
            } else {
                this.propertiesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addProperties(Property.Builder builderForValue) {
            if (this.propertiesBuilder_ == null) {
                this.ensurePropertiesIsMutable();
                this.properties_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.propertiesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addProperties(int index, Property.Builder builderForValue) {
            if (this.propertiesBuilder_ == null) {
                this.ensurePropertiesIsMutable();
                this.properties_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.propertiesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllProperties(Iterable<? extends Property> values2) {
            if (this.propertiesBuilder_ == null) {
                this.ensurePropertiesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.properties_);
                this.onChanged();
            } else {
                this.propertiesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearProperties() {
            if (this.propertiesBuilder_ == null) {
                this.properties_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.propertiesBuilder_.clear();
            }
            return this;
        }

        public Builder removeProperties(int index) {
            if (this.propertiesBuilder_ == null) {
                this.ensurePropertiesIsMutable();
                this.properties_.remove(index);
                this.onChanged();
            } else {
                this.propertiesBuilder_.remove(index);
            }
            return this;
        }

        public Property.Builder getPropertiesBuilder(int index) {
            return this.getPropertiesFieldBuilder().getBuilder(index);
        }

        @Override
        public PropertyOrBuilder getPropertiesOrBuilder(int index) {
            if (this.propertiesBuilder_ == null) {
                return this.properties_.get(index);
            }
            return this.propertiesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends PropertyOrBuilder> getPropertiesOrBuilderList() {
            if (this.propertiesBuilder_ != null) {
                return this.propertiesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.properties_);
        }

        public Property.Builder addPropertiesBuilder() {
            return this.getPropertiesFieldBuilder().addBuilder(Property.getDefaultInstance());
        }

        public Property.Builder addPropertiesBuilder(int index) {
            return this.getPropertiesFieldBuilder().addBuilder(index, Property.getDefaultInstance());
        }

        public List<Property.Builder> getPropertiesBuilderList() {
            return this.getPropertiesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Property, Property.Builder, PropertyOrBuilder> getPropertiesFieldBuilder() {
            if (this.propertiesBuilder_ == null) {
                this.propertiesBuilder_ = new RepeatedFieldBuilderV3(this.properties_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.properties_ = null;
            }
            return this.propertiesBuilder_;
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

