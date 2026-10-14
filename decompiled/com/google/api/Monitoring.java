/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.MonitoringOrBuilder;
import com.google.api.MonitoringProto;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Monitoring
extends GeneratedMessageV3
implements MonitoringOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int PRODUCER_DESTINATIONS_FIELD_NUMBER = 1;
    private List<MonitoringDestination> producerDestinations_;
    public static final int CONSUMER_DESTINATIONS_FIELD_NUMBER = 2;
    private List<MonitoringDestination> consumerDestinations_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Monitoring DEFAULT_INSTANCE = new Monitoring();
    private static final Parser<Monitoring> PARSER = new AbstractParser<Monitoring>(){

        @Override
        public Monitoring parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Monitoring.newBuilder();
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

    private Monitoring(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Monitoring() {
        this.producerDestinations_ = Collections.emptyList();
        this.consumerDestinations_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Monitoring();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return MonitoringProto.internal_static_google_api_Monitoring_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return MonitoringProto.internal_static_google_api_Monitoring_fieldAccessorTable.ensureFieldAccessorsInitialized(Monitoring.class, Builder.class);
    }

    @Override
    public List<MonitoringDestination> getProducerDestinationsList() {
        return this.producerDestinations_;
    }

    @Override
    public List<? extends MonitoringDestinationOrBuilder> getProducerDestinationsOrBuilderList() {
        return this.producerDestinations_;
    }

    @Override
    public int getProducerDestinationsCount() {
        return this.producerDestinations_.size();
    }

    @Override
    public MonitoringDestination getProducerDestinations(int index) {
        return this.producerDestinations_.get(index);
    }

    @Override
    public MonitoringDestinationOrBuilder getProducerDestinationsOrBuilder(int index) {
        return this.producerDestinations_.get(index);
    }

    @Override
    public List<MonitoringDestination> getConsumerDestinationsList() {
        return this.consumerDestinations_;
    }

    @Override
    public List<? extends MonitoringDestinationOrBuilder> getConsumerDestinationsOrBuilderList() {
        return this.consumerDestinations_;
    }

    @Override
    public int getConsumerDestinationsCount() {
        return this.consumerDestinations_.size();
    }

    @Override
    public MonitoringDestination getConsumerDestinations(int index) {
        return this.consumerDestinations_.get(index);
    }

    @Override
    public MonitoringDestinationOrBuilder getConsumerDestinationsOrBuilder(int index) {
        return this.consumerDestinations_.get(index);
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
        for (i = 0; i < this.producerDestinations_.size(); ++i) {
            output.writeMessage(1, this.producerDestinations_.get(i));
        }
        for (i = 0; i < this.consumerDestinations_.size(); ++i) {
            output.writeMessage(2, this.consumerDestinations_.get(i));
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
        for (i = 0; i < this.producerDestinations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.producerDestinations_.get(i));
        }
        for (i = 0; i < this.consumerDestinations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.consumerDestinations_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Monitoring)) {
            return super.equals(obj);
        }
        Monitoring other = (Monitoring)obj;
        if (!this.getProducerDestinationsList().equals(other.getProducerDestinationsList())) {
            return false;
        }
        if (!this.getConsumerDestinationsList().equals(other.getConsumerDestinationsList())) {
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
        hash = 19 * hash + Monitoring.getDescriptor().hashCode();
        if (this.getProducerDestinationsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getProducerDestinationsList().hashCode();
        }
        if (this.getConsumerDestinationsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getConsumerDestinationsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Monitoring parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Monitoring parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Monitoring parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Monitoring parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Monitoring parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Monitoring parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Monitoring parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Monitoring parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Monitoring parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Monitoring parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Monitoring parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Monitoring parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Monitoring.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Monitoring prototype) {
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

    public static Monitoring getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Monitoring> parser() {
        return PARSER;
    }

    public Parser<Monitoring> getParserForType() {
        return PARSER;
    }

    @Override
    public Monitoring getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements MonitoringOrBuilder {
        private int bitField0_;
        private List<MonitoringDestination> producerDestinations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MonitoringDestination, MonitoringDestination.Builder, MonitoringDestinationOrBuilder> producerDestinationsBuilder_;
        private List<MonitoringDestination> consumerDestinations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MonitoringDestination, MonitoringDestination.Builder, MonitoringDestinationOrBuilder> consumerDestinationsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return MonitoringProto.internal_static_google_api_Monitoring_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MonitoringProto.internal_static_google_api_Monitoring_fieldAccessorTable.ensureFieldAccessorsInitialized(Monitoring.class, Builder.class);
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
            if (this.producerDestinationsBuilder_ == null) {
                this.producerDestinations_ = Collections.emptyList();
            } else {
                this.producerDestinations_ = null;
                this.producerDestinationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            if (this.consumerDestinationsBuilder_ == null) {
                this.consumerDestinations_ = Collections.emptyList();
            } else {
                this.consumerDestinations_ = null;
                this.consumerDestinationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFD;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return MonitoringProto.internal_static_google_api_Monitoring_descriptor;
        }

        @Override
        public Monitoring getDefaultInstanceForType() {
            return Monitoring.getDefaultInstance();
        }

        @Override
        public Monitoring build() {
            Monitoring result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Monitoring buildPartial() {
            Monitoring result = new Monitoring(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Monitoring result) {
            if (this.producerDestinationsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.producerDestinations_ = Collections.unmodifiableList(this.producerDestinations_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.producerDestinations_ = this.producerDestinations_;
            } else {
                result.producerDestinations_ = this.producerDestinationsBuilder_.build();
            }
            if (this.consumerDestinationsBuilder_ == null) {
                if ((this.bitField0_ & 2) != 0) {
                    this.consumerDestinations_ = Collections.unmodifiableList(this.consumerDestinations_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.consumerDestinations_ = this.consumerDestinations_;
            } else {
                result.consumerDestinations_ = this.consumerDestinationsBuilder_.build();
            }
        }

        private void buildPartial0(Monitoring result) {
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
            if (other instanceof Monitoring) {
                return this.mergeFrom((Monitoring)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Monitoring other) {
            if (other == Monitoring.getDefaultInstance()) {
                return this;
            }
            if (this.producerDestinationsBuilder_ == null) {
                if (!other.producerDestinations_.isEmpty()) {
                    if (this.producerDestinations_.isEmpty()) {
                        this.producerDestinations_ = other.producerDestinations_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureProducerDestinationsIsMutable();
                        this.producerDestinations_.addAll(other.producerDestinations_);
                    }
                    this.onChanged();
                }
            } else if (!other.producerDestinations_.isEmpty()) {
                if (this.producerDestinationsBuilder_.isEmpty()) {
                    this.producerDestinationsBuilder_.dispose();
                    this.producerDestinationsBuilder_ = null;
                    this.producerDestinations_ = other.producerDestinations_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.producerDestinationsBuilder_ = alwaysUseFieldBuilders ? this.getProducerDestinationsFieldBuilder() : null;
                } else {
                    this.producerDestinationsBuilder_.addAllMessages(other.producerDestinations_);
                }
            }
            if (this.consumerDestinationsBuilder_ == null) {
                if (!other.consumerDestinations_.isEmpty()) {
                    if (this.consumerDestinations_.isEmpty()) {
                        this.consumerDestinations_ = other.consumerDestinations_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureConsumerDestinationsIsMutable();
                        this.consumerDestinations_.addAll(other.consumerDestinations_);
                    }
                    this.onChanged();
                }
            } else if (!other.consumerDestinations_.isEmpty()) {
                if (this.consumerDestinationsBuilder_.isEmpty()) {
                    this.consumerDestinationsBuilder_.dispose();
                    this.consumerDestinationsBuilder_ = null;
                    this.consumerDestinations_ = other.consumerDestinations_;
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.consumerDestinationsBuilder_ = alwaysUseFieldBuilders ? this.getConsumerDestinationsFieldBuilder() : null;
                } else {
                    this.consumerDestinationsBuilder_.addAllMessages(other.consumerDestinations_);
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
                block10: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        case 10: {
                            MonitoringDestination m4 = input.readMessage(MonitoringDestination.parser(), extensionRegistry);
                            if (this.producerDestinationsBuilder_ == null) {
                                this.ensureProducerDestinationsIsMutable();
                                this.producerDestinations_.add(m4);
                                continue block10;
                            }
                            this.producerDestinationsBuilder_.addMessage(m4);
                            continue block10;
                        }
                        case 18: {
                            MonitoringDestination m4 = input.readMessage(MonitoringDestination.parser(), extensionRegistry);
                            if (this.consumerDestinationsBuilder_ == null) {
                                this.ensureConsumerDestinationsIsMutable();
                                this.consumerDestinations_.add(m4);
                                continue block10;
                            }
                            this.consumerDestinationsBuilder_.addMessage(m4);
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

        private void ensureProducerDestinationsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.producerDestinations_ = new ArrayList<MonitoringDestination>(this.producerDestinations_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<MonitoringDestination> getProducerDestinationsList() {
            if (this.producerDestinationsBuilder_ == null) {
                return Collections.unmodifiableList(this.producerDestinations_);
            }
            return this.producerDestinationsBuilder_.getMessageList();
        }

        @Override
        public int getProducerDestinationsCount() {
            if (this.producerDestinationsBuilder_ == null) {
                return this.producerDestinations_.size();
            }
            return this.producerDestinationsBuilder_.getCount();
        }

        @Override
        public MonitoringDestination getProducerDestinations(int index) {
            if (this.producerDestinationsBuilder_ == null) {
                return this.producerDestinations_.get(index);
            }
            return this.producerDestinationsBuilder_.getMessage(index);
        }

        public Builder setProducerDestinations(int index, MonitoringDestination value) {
            if (this.producerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.set(index, value);
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setProducerDestinations(int index, MonitoringDestination.Builder builderForValue) {
            if (this.producerDestinationsBuilder_ == null) {
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addProducerDestinations(MonitoringDestination value) {
            if (this.producerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.add(value);
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addProducerDestinations(int index, MonitoringDestination value) {
            if (this.producerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.add(index, value);
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addProducerDestinations(MonitoringDestination.Builder builderForValue) {
            if (this.producerDestinationsBuilder_ == null) {
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addProducerDestinations(int index, MonitoringDestination.Builder builderForValue) {
            if (this.producerDestinationsBuilder_ == null) {
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllProducerDestinations(Iterable<? extends MonitoringDestination> values2) {
            if (this.producerDestinationsBuilder_ == null) {
                this.ensureProducerDestinationsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.producerDestinations_);
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearProducerDestinations() {
            if (this.producerDestinationsBuilder_ == null) {
                this.producerDestinations_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.clear();
            }
            return this;
        }

        public Builder removeProducerDestinations(int index) {
            if (this.producerDestinationsBuilder_ == null) {
                this.ensureProducerDestinationsIsMutable();
                this.producerDestinations_.remove(index);
                this.onChanged();
            } else {
                this.producerDestinationsBuilder_.remove(index);
            }
            return this;
        }

        public MonitoringDestination.Builder getProducerDestinationsBuilder(int index) {
            return this.getProducerDestinationsFieldBuilder().getBuilder(index);
        }

        @Override
        public MonitoringDestinationOrBuilder getProducerDestinationsOrBuilder(int index) {
            if (this.producerDestinationsBuilder_ == null) {
                return this.producerDestinations_.get(index);
            }
            return this.producerDestinationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MonitoringDestinationOrBuilder> getProducerDestinationsOrBuilderList() {
            if (this.producerDestinationsBuilder_ != null) {
                return this.producerDestinationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.producerDestinations_);
        }

        public MonitoringDestination.Builder addProducerDestinationsBuilder() {
            return this.getProducerDestinationsFieldBuilder().addBuilder(MonitoringDestination.getDefaultInstance());
        }

        public MonitoringDestination.Builder addProducerDestinationsBuilder(int index) {
            return this.getProducerDestinationsFieldBuilder().addBuilder(index, MonitoringDestination.getDefaultInstance());
        }

        public List<MonitoringDestination.Builder> getProducerDestinationsBuilderList() {
            return this.getProducerDestinationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MonitoringDestination, MonitoringDestination.Builder, MonitoringDestinationOrBuilder> getProducerDestinationsFieldBuilder() {
            if (this.producerDestinationsBuilder_ == null) {
                this.producerDestinationsBuilder_ = new RepeatedFieldBuilderV3(this.producerDestinations_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.producerDestinations_ = null;
            }
            return this.producerDestinationsBuilder_;
        }

        private void ensureConsumerDestinationsIsMutable() {
            if ((this.bitField0_ & 2) == 0) {
                this.consumerDestinations_ = new ArrayList<MonitoringDestination>(this.consumerDestinations_);
                this.bitField0_ |= 2;
            }
        }

        @Override
        public List<MonitoringDestination> getConsumerDestinationsList() {
            if (this.consumerDestinationsBuilder_ == null) {
                return Collections.unmodifiableList(this.consumerDestinations_);
            }
            return this.consumerDestinationsBuilder_.getMessageList();
        }

        @Override
        public int getConsumerDestinationsCount() {
            if (this.consumerDestinationsBuilder_ == null) {
                return this.consumerDestinations_.size();
            }
            return this.consumerDestinationsBuilder_.getCount();
        }

        @Override
        public MonitoringDestination getConsumerDestinations(int index) {
            if (this.consumerDestinationsBuilder_ == null) {
                return this.consumerDestinations_.get(index);
            }
            return this.consumerDestinationsBuilder_.getMessage(index);
        }

        public Builder setConsumerDestinations(int index, MonitoringDestination value) {
            if (this.consumerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.set(index, value);
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setConsumerDestinations(int index, MonitoringDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addConsumerDestinations(MonitoringDestination value) {
            if (this.consumerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(value);
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addConsumerDestinations(int index, MonitoringDestination value) {
            if (this.consumerDestinationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(index, value);
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addConsumerDestinations(MonitoringDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addConsumerDestinations(int index, MonitoringDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllConsumerDestinations(Iterable<? extends MonitoringDestination> values2) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.consumerDestinations_);
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearConsumerDestinations() {
            if (this.consumerDestinationsBuilder_ == null) {
                this.consumerDestinations_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.clear();
            }
            return this;
        }

        public Builder removeConsumerDestinations(int index) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.remove(index);
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.remove(index);
            }
            return this;
        }

        public MonitoringDestination.Builder getConsumerDestinationsBuilder(int index) {
            return this.getConsumerDestinationsFieldBuilder().getBuilder(index);
        }

        @Override
        public MonitoringDestinationOrBuilder getConsumerDestinationsOrBuilder(int index) {
            if (this.consumerDestinationsBuilder_ == null) {
                return this.consumerDestinations_.get(index);
            }
            return this.consumerDestinationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MonitoringDestinationOrBuilder> getConsumerDestinationsOrBuilderList() {
            if (this.consumerDestinationsBuilder_ != null) {
                return this.consumerDestinationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.consumerDestinations_);
        }

        public MonitoringDestination.Builder addConsumerDestinationsBuilder() {
            return this.getConsumerDestinationsFieldBuilder().addBuilder(MonitoringDestination.getDefaultInstance());
        }

        public MonitoringDestination.Builder addConsumerDestinationsBuilder(int index) {
            return this.getConsumerDestinationsFieldBuilder().addBuilder(index, MonitoringDestination.getDefaultInstance());
        }

        public List<MonitoringDestination.Builder> getConsumerDestinationsBuilderList() {
            return this.getConsumerDestinationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MonitoringDestination, MonitoringDestination.Builder, MonitoringDestinationOrBuilder> getConsumerDestinationsFieldBuilder() {
            if (this.consumerDestinationsBuilder_ == null) {
                this.consumerDestinationsBuilder_ = new RepeatedFieldBuilderV3(this.consumerDestinations_, (this.bitField0_ & 2) != 0, this.getParentForChildren(), this.isClean());
                this.consumerDestinations_ = null;
            }
            return this.consumerDestinationsBuilder_;
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

    public static final class MonitoringDestination
    extends GeneratedMessageV3
    implements MonitoringDestinationOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MONITORED_RESOURCE_FIELD_NUMBER = 1;
        private volatile Object monitoredResource_ = "";
        public static final int METRICS_FIELD_NUMBER = 2;
        private LazyStringArrayList metrics_ = LazyStringArrayList.emptyList();
        private byte memoizedIsInitialized = (byte)-1;
        private static final MonitoringDestination DEFAULT_INSTANCE = new MonitoringDestination();
        private static final Parser<MonitoringDestination> PARSER = new AbstractParser<MonitoringDestination>(){

            @Override
            public MonitoringDestination parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = MonitoringDestination.newBuilder();
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

        private MonitoringDestination(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MonitoringDestination() {
            this.monitoredResource_ = "";
            this.metrics_ = LazyStringArrayList.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new MonitoringDestination();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return MonitoringProto.internal_static_google_api_Monitoring_MonitoringDestination_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MonitoringProto.internal_static_google_api_Monitoring_MonitoringDestination_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoringDestination.class, Builder.class);
        }

        @Override
        public String getMonitoredResource() {
            Object ref = this.monitoredResource_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s2 = bs.toStringUtf8();
            this.monitoredResource_ = s2;
            return s2;
        }

        @Override
        public ByteString getMonitoredResourceBytes() {
            Object ref = this.monitoredResource_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.monitoredResource_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public ProtocolStringList getMetricsList() {
            return this.metrics_;
        }

        @Override
        public int getMetricsCount() {
            return this.metrics_.size();
        }

        @Override
        public String getMetrics(int index) {
            return this.metrics_.get(index);
        }

        @Override
        public ByteString getMetricsBytes(int index) {
            return this.metrics_.getByteString(index);
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
            if (!GeneratedMessageV3.isStringEmpty(this.monitoredResource_)) {
                GeneratedMessageV3.writeString(output, 1, this.monitoredResource_);
            }
            for (int i = 0; i < this.metrics_.size(); ++i) {
                GeneratedMessageV3.writeString(output, 2, this.metrics_.getRaw(i));
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
            if (!GeneratedMessageV3.isStringEmpty(this.monitoredResource_)) {
                size += GeneratedMessageV3.computeStringSize(1, this.monitoredResource_);
            }
            int dataSize = 0;
            for (int i = 0; i < this.metrics_.size(); ++i) {
                dataSize += MonitoringDestination.computeStringSizeNoTag(this.metrics_.getRaw(i));
            }
            size += dataSize;
            size += 1 * this.getMetricsList().size();
            this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MonitoringDestination)) {
                return super.equals(obj);
            }
            MonitoringDestination other = (MonitoringDestination)obj;
            if (!this.getMonitoredResource().equals(other.getMonitoredResource())) {
                return false;
            }
            if (!this.getMetricsList().equals(other.getMetricsList())) {
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
            hash = 19 * hash + MonitoringDestination.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMonitoredResource().hashCode();
            if (this.getMetricsCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getMetricsList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static MonitoringDestination parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MonitoringDestination parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MonitoringDestination parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MonitoringDestination parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MonitoringDestination parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MonitoringDestination parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MonitoringDestination parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MonitoringDestination parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MonitoringDestination parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MonitoringDestination parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MonitoringDestination parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MonitoringDestination parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MonitoringDestination.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MonitoringDestination prototype) {
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

        public static MonitoringDestination getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MonitoringDestination> parser() {
            return PARSER;
        }

        public Parser<MonitoringDestination> getParserForType() {
            return PARSER;
        }

        @Override
        public MonitoringDestination getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MonitoringDestinationOrBuilder {
            private int bitField0_;
            private Object monitoredResource_ = "";
            private LazyStringArrayList metrics_ = LazyStringArrayList.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return MonitoringProto.internal_static_google_api_Monitoring_MonitoringDestination_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MonitoringProto.internal_static_google_api_Monitoring_MonitoringDestination_fieldAccessorTable.ensureFieldAccessorsInitialized(MonitoringDestination.class, Builder.class);
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
                this.monitoredResource_ = "";
                this.metrics_ = LazyStringArrayList.emptyList();
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return MonitoringProto.internal_static_google_api_Monitoring_MonitoringDestination_descriptor;
            }

            @Override
            public MonitoringDestination getDefaultInstanceForType() {
                return MonitoringDestination.getDefaultInstance();
            }

            @Override
            public MonitoringDestination build() {
                MonitoringDestination result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MonitoringDestination buildPartial() {
                MonitoringDestination result = new MonitoringDestination(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(MonitoringDestination result) {
                int from_bitField0_ = this.bitField0_;
                if ((from_bitField0_ & 1) != 0) {
                    result.monitoredResource_ = this.monitoredResource_;
                }
                if ((from_bitField0_ & 2) != 0) {
                    this.metrics_.makeImmutable();
                    result.metrics_ = this.metrics_;
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
                if (other instanceof MonitoringDestination) {
                    return this.mergeFrom((MonitoringDestination)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MonitoringDestination other) {
                if (other == MonitoringDestination.getDefaultInstance()) {
                    return this;
                }
                if (!other.getMonitoredResource().isEmpty()) {
                    this.monitoredResource_ = other.monitoredResource_;
                    this.bitField0_ |= 1;
                    this.onChanged();
                }
                if (!other.metrics_.isEmpty()) {
                    if (this.metrics_.isEmpty()) {
                        this.metrics_ = other.metrics_;
                        this.bitField0_ |= 2;
                    } else {
                        this.ensureMetricsIsMutable();
                        this.metrics_.addAll(other.metrics_);
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
                    block10: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block10;
                            }
                            case 10: {
                                this.monitoredResource_ = input.readStringRequireUtf8();
                                this.bitField0_ |= 1;
                                continue block10;
                            }
                            case 18: {
                                String s2 = input.readStringRequireUtf8();
                                this.ensureMetricsIsMutable();
                                this.metrics_.add(s2);
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
            public String getMonitoredResource() {
                Object ref = this.monitoredResource_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s2 = bs.toStringUtf8();
                    this.monitoredResource_ = s2;
                    return s2;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMonitoredResourceBytes() {
                Object ref = this.monitoredResource_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.monitoredResource_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setMonitoredResource(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.monitoredResource_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            public Builder clearMonitoredResource() {
                this.monitoredResource_ = MonitoringDestination.getDefaultInstance().getMonitoredResource();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setMonitoredResourceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MonitoringDestination.checkByteStringIsUtf8(value);
                this.monitoredResource_ = value;
                this.bitField0_ |= 1;
                this.onChanged();
                return this;
            }

            private void ensureMetricsIsMutable() {
                if (!this.metrics_.isModifiable()) {
                    this.metrics_ = new LazyStringArrayList(this.metrics_);
                }
                this.bitField0_ |= 2;
            }

            public ProtocolStringList getMetricsList() {
                this.metrics_.makeImmutable();
                return this.metrics_;
            }

            @Override
            public int getMetricsCount() {
                return this.metrics_.size();
            }

            @Override
            public String getMetrics(int index) {
                return this.metrics_.get(index);
            }

            @Override
            public ByteString getMetricsBytes(int index) {
                return this.metrics_.getByteString(index);
            }

            public Builder setMetrics(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricsIsMutable();
                this.metrics_.set(index, value);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder addMetrics(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricsIsMutable();
                this.metrics_.add(value);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder addAllMetrics(Iterable<String> values2) {
                this.ensureMetricsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.metrics_);
                this.bitField0_ |= 2;
                this.onChanged();
                return this;
            }

            public Builder clearMetrics() {
                this.metrics_ = LazyStringArrayList.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addMetricsBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MonitoringDestination.checkByteStringIsUtf8(value);
                this.ensureMetricsIsMutable();
                this.metrics_.add(value);
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

    public static interface MonitoringDestinationOrBuilder
    extends MessageOrBuilder {
        public String getMonitoredResource();

        public ByteString getMonitoredResourceBytes();

        public List<String> getMetricsList();

        public int getMetricsCount();

        public String getMetrics(int var1);

        public ByteString getMetricsBytes(int var1);
    }
}

