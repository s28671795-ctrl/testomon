/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.BillingOrBuilder;
import com.google.api.BillingProto;
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

public final class Billing
extends GeneratedMessageV3
implements BillingOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int CONSUMER_DESTINATIONS_FIELD_NUMBER = 8;
    private List<BillingDestination> consumerDestinations_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Billing DEFAULT_INSTANCE = new Billing();
    private static final Parser<Billing> PARSER = new AbstractParser<Billing>(){

        @Override
        public Billing parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Billing.newBuilder();
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

    private Billing(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Billing() {
        this.consumerDestinations_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Billing();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return BillingProto.internal_static_google_api_Billing_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return BillingProto.internal_static_google_api_Billing_fieldAccessorTable.ensureFieldAccessorsInitialized(Billing.class, Builder.class);
    }

    @Override
    public List<BillingDestination> getConsumerDestinationsList() {
        return this.consumerDestinations_;
    }

    @Override
    public List<? extends BillingDestinationOrBuilder> getConsumerDestinationsOrBuilderList() {
        return this.consumerDestinations_;
    }

    @Override
    public int getConsumerDestinationsCount() {
        return this.consumerDestinations_.size();
    }

    @Override
    public BillingDestination getConsumerDestinations(int index) {
        return this.consumerDestinations_.get(index);
    }

    @Override
    public BillingDestinationOrBuilder getConsumerDestinationsOrBuilder(int index) {
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
        for (int i = 0; i < this.consumerDestinations_.size(); ++i) {
            output.writeMessage(8, this.consumerDestinations_.get(i));
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
        for (int i = 0; i < this.consumerDestinations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(8, this.consumerDestinations_.get(i));
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Billing)) {
            return super.equals(obj);
        }
        Billing other = (Billing)obj;
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
        hash = 19 * hash + Billing.getDescriptor().hashCode();
        if (this.getConsumerDestinationsCount() > 0) {
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getConsumerDestinationsList().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Billing parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Billing parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Billing parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Billing parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Billing parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Billing parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Billing parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Billing parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Billing parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Billing parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Billing parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Billing parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Billing.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Billing prototype) {
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

    public static Billing getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Billing> parser() {
        return PARSER;
    }

    public Parser<Billing> getParserForType() {
        return PARSER;
    }

    @Override
    public Billing getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements BillingOrBuilder {
        private int bitField0_;
        private List<BillingDestination> consumerDestinations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<BillingDestination, BillingDestination.Builder, BillingDestinationOrBuilder> consumerDestinationsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return BillingProto.internal_static_google_api_Billing_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return BillingProto.internal_static_google_api_Billing_fieldAccessorTable.ensureFieldAccessorsInitialized(Billing.class, Builder.class);
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
            if (this.consumerDestinationsBuilder_ == null) {
                this.consumerDestinations_ = Collections.emptyList();
            } else {
                this.consumerDestinations_ = null;
                this.consumerDestinationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return BillingProto.internal_static_google_api_Billing_descriptor;
        }

        @Override
        public Billing getDefaultInstanceForType() {
            return Billing.getDefaultInstance();
        }

        @Override
        public Billing build() {
            Billing result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Billing buildPartial() {
            Billing result = new Billing(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Billing result) {
            if (this.consumerDestinationsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.consumerDestinations_ = Collections.unmodifiableList(this.consumerDestinations_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.consumerDestinations_ = this.consumerDestinations_;
            } else {
                result.consumerDestinations_ = this.consumerDestinationsBuilder_.build();
            }
        }

        private void buildPartial0(Billing result) {
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
            if (other instanceof Billing) {
                return this.mergeFrom((Billing)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Billing other) {
            if (other == Billing.getDefaultInstance()) {
                return this;
            }
            if (this.consumerDestinationsBuilder_ == null) {
                if (!other.consumerDestinations_.isEmpty()) {
                    if (this.consumerDestinations_.isEmpty()) {
                        this.consumerDestinations_ = other.consumerDestinations_;
                        this.bitField0_ &= 0xFFFFFFFE;
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
                    this.bitField0_ &= 0xFFFFFFFE;
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
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                        case 66: {
                            BillingDestination m4 = input.readMessage(BillingDestination.parser(), extensionRegistry);
                            if (this.consumerDestinationsBuilder_ == null) {
                                this.ensureConsumerDestinationsIsMutable();
                                this.consumerDestinations_.add(m4);
                                continue block9;
                            }
                            this.consumerDestinationsBuilder_.addMessage(m4);
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

        private void ensureConsumerDestinationsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.consumerDestinations_ = new ArrayList<BillingDestination>(this.consumerDestinations_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<BillingDestination> getConsumerDestinationsList() {
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
        public BillingDestination getConsumerDestinations(int index) {
            if (this.consumerDestinationsBuilder_ == null) {
                return this.consumerDestinations_.get(index);
            }
            return this.consumerDestinationsBuilder_.getMessage(index);
        }

        public Builder setConsumerDestinations(int index, BillingDestination value) {
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

        public Builder setConsumerDestinations(int index, BillingDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addConsumerDestinations(BillingDestination value) {
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

        public Builder addConsumerDestinations(int index, BillingDestination value) {
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

        public Builder addConsumerDestinations(BillingDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addConsumerDestinations(int index, BillingDestination.Builder builderForValue) {
            if (this.consumerDestinationsBuilder_ == null) {
                this.ensureConsumerDestinationsIsMutable();
                this.consumerDestinations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.consumerDestinationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllConsumerDestinations(Iterable<? extends BillingDestination> values2) {
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
                this.bitField0_ &= 0xFFFFFFFE;
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

        public BillingDestination.Builder getConsumerDestinationsBuilder(int index) {
            return this.getConsumerDestinationsFieldBuilder().getBuilder(index);
        }

        @Override
        public BillingDestinationOrBuilder getConsumerDestinationsOrBuilder(int index) {
            if (this.consumerDestinationsBuilder_ == null) {
                return this.consumerDestinations_.get(index);
            }
            return this.consumerDestinationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends BillingDestinationOrBuilder> getConsumerDestinationsOrBuilderList() {
            if (this.consumerDestinationsBuilder_ != null) {
                return this.consumerDestinationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.consumerDestinations_);
        }

        public BillingDestination.Builder addConsumerDestinationsBuilder() {
            return this.getConsumerDestinationsFieldBuilder().addBuilder(BillingDestination.getDefaultInstance());
        }

        public BillingDestination.Builder addConsumerDestinationsBuilder(int index) {
            return this.getConsumerDestinationsFieldBuilder().addBuilder(index, BillingDestination.getDefaultInstance());
        }

        public List<BillingDestination.Builder> getConsumerDestinationsBuilderList() {
            return this.getConsumerDestinationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<BillingDestination, BillingDestination.Builder, BillingDestinationOrBuilder> getConsumerDestinationsFieldBuilder() {
            if (this.consumerDestinationsBuilder_ == null) {
                this.consumerDestinationsBuilder_ = new RepeatedFieldBuilderV3(this.consumerDestinations_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
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

    public static final class BillingDestination
    extends GeneratedMessageV3
    implements BillingDestinationOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MONITORED_RESOURCE_FIELD_NUMBER = 1;
        private volatile Object monitoredResource_ = "";
        public static final int METRICS_FIELD_NUMBER = 2;
        private LazyStringArrayList metrics_ = LazyStringArrayList.emptyList();
        private byte memoizedIsInitialized = (byte)-1;
        private static final BillingDestination DEFAULT_INSTANCE = new BillingDestination();
        private static final Parser<BillingDestination> PARSER = new AbstractParser<BillingDestination>(){

            @Override
            public BillingDestination parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = BillingDestination.newBuilder();
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

        private BillingDestination(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private BillingDestination() {
            this.monitoredResource_ = "";
            this.metrics_ = LazyStringArrayList.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
            return new BillingDestination();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return BillingProto.internal_static_google_api_Billing_BillingDestination_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return BillingProto.internal_static_google_api_Billing_BillingDestination_fieldAccessorTable.ensureFieldAccessorsInitialized(BillingDestination.class, Builder.class);
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
                dataSize += BillingDestination.computeStringSizeNoTag(this.metrics_.getRaw(i));
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
            if (!(obj instanceof BillingDestination)) {
                return super.equals(obj);
            }
            BillingDestination other = (BillingDestination)obj;
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
            hash = 19 * hash + BillingDestination.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMonitoredResource().hashCode();
            if (this.getMetricsCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getMetricsList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
            return hash;
        }

        public static BillingDestination parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BillingDestination parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BillingDestination parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BillingDestination parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BillingDestination parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BillingDestination parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BillingDestination parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BillingDestination parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BillingDestination parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BillingDestination parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BillingDestination parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BillingDestination parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return BillingDestination.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BillingDestination prototype) {
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

        public static BillingDestination getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BillingDestination> parser() {
            return PARSER;
        }

        public Parser<BillingDestination> getParserForType() {
            return PARSER;
        }

        @Override
        public BillingDestination getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BillingDestinationOrBuilder {
            private int bitField0_;
            private Object monitoredResource_ = "";
            private LazyStringArrayList metrics_ = LazyStringArrayList.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return BillingProto.internal_static_google_api_Billing_BillingDestination_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return BillingProto.internal_static_google_api_Billing_BillingDestination_fieldAccessorTable.ensureFieldAccessorsInitialized(BillingDestination.class, Builder.class);
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
                return BillingProto.internal_static_google_api_Billing_BillingDestination_descriptor;
            }

            @Override
            public BillingDestination getDefaultInstanceForType() {
                return BillingDestination.getDefaultInstance();
            }

            @Override
            public BillingDestination build() {
                BillingDestination result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public BillingDestination buildPartial() {
                BillingDestination result = new BillingDestination(this);
                if (this.bitField0_ != 0) {
                    this.buildPartial0(result);
                }
                this.onBuilt();
                return result;
            }

            private void buildPartial0(BillingDestination result) {
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
                if (other instanceof BillingDestination) {
                    return this.mergeFrom((BillingDestination)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BillingDestination other) {
                if (other == BillingDestination.getDefaultInstance()) {
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
                this.monitoredResource_ = BillingDestination.getDefaultInstance().getMonitoredResource();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder setMonitoredResourceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                BillingDestination.checkByteStringIsUtf8(value);
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
                BillingDestination.checkByteStringIsUtf8(value);
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

    public static interface BillingDestinationOrBuilder
    extends MessageOrBuilder {
        public String getMonitoredResource();

        public ByteString getMonitoredResourceBytes();

        public List<String> getMetricsList();

        public int getMetricsCount();

        public String getMetrics(int var1);

        public ByteString getMetricsBytes(int var1);
    }
}

