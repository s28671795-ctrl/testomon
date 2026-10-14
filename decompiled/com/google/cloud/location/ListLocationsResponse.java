/*
 * Decompiled with CFR 0.152.
 */
package com.google.cloud.location;

import com.google.cloud.location.ListLocationsResponseOrBuilder;
import com.google.cloud.location.Location;
import com.google.cloud.location.LocationOrBuilder;
import com.google.cloud.location.LocationsProto;
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

public final class ListLocationsResponse
extends GeneratedMessageV3
implements ListLocationsResponseOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int LOCATIONS_FIELD_NUMBER = 1;
    private List<Location> locations_;
    public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
    private volatile Object nextPageToken_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final ListLocationsResponse DEFAULT_INSTANCE = new ListLocationsResponse();
    private static final Parser<ListLocationsResponse> PARSER = new AbstractParser<ListLocationsResponse>(){

        @Override
        public ListLocationsResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ListLocationsResponse.newBuilder();
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

    private ListLocationsResponse(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ListLocationsResponse() {
        this.locations_ = Collections.emptyList();
        this.nextPageToken_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ListLocationsResponse();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return LocationsProto.internal_static_google_cloud_location_ListLocationsResponse_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return LocationsProto.internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListLocationsResponse.class, Builder.class);
    }

    @Override
    public List<Location> getLocationsList() {
        return this.locations_;
    }

    @Override
    public List<? extends LocationOrBuilder> getLocationsOrBuilderList() {
        return this.locations_;
    }

    @Override
    public int getLocationsCount() {
        return this.locations_.size();
    }

    @Override
    public Location getLocations(int index) {
        return this.locations_.get(index);
    }

    @Override
    public LocationOrBuilder getLocationsOrBuilder(int index) {
        return this.locations_.get(index);
    }

    @Override
    public String getNextPageToken() {
        Object ref = this.nextPageToken_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.nextPageToken_ = s2;
        return s2;
    }

    @Override
    public ByteString getNextPageTokenBytes() {
        Object ref = this.nextPageToken_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.nextPageToken_ = b;
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
        for (int i = 0; i < this.locations_.size(); ++i) {
            output.writeMessage(1, this.locations_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.nextPageToken_)) {
            GeneratedMessageV3.writeString(output, 2, this.nextPageToken_);
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
        for (int i = 0; i < this.locations_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(1, this.locations_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.nextPageToken_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.nextPageToken_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ListLocationsResponse)) {
            return super.equals(obj);
        }
        ListLocationsResponse other = (ListLocationsResponse)obj;
        if (!this.getLocationsList().equals(other.getLocationsList())) {
            return false;
        }
        if (!this.getNextPageToken().equals(other.getNextPageToken())) {
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
        hash = 19 * hash + ListLocationsResponse.getDescriptor().hashCode();
        if (this.getLocationsCount() > 0) {
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getLocationsList().hashCode();
        }
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getNextPageToken().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ListLocationsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListLocationsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListLocationsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListLocationsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListLocationsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ListLocationsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListLocationsResponse parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListLocationsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListLocationsResponse parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ListLocationsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListLocationsResponse parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListLocationsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ListLocationsResponse.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListLocationsResponse prototype) {
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

    public static ListLocationsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListLocationsResponse> parser() {
        return PARSER;
    }

    public Parser<ListLocationsResponse> getParserForType() {
        return PARSER;
    }

    @Override
    public ListLocationsResponse getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ListLocationsResponseOrBuilder {
        private int bitField0_;
        private List<Location> locations_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Location, Location.Builder, LocationOrBuilder> locationsBuilder_;
        private Object nextPageToken_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return LocationsProto.internal_static_google_cloud_location_ListLocationsResponse_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return LocationsProto.internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ListLocationsResponse.class, Builder.class);
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
            if (this.locationsBuilder_ == null) {
                this.locations_ = Collections.emptyList();
            } else {
                this.locations_ = null;
                this.locationsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            this.nextPageToken_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return LocationsProto.internal_static_google_cloud_location_ListLocationsResponse_descriptor;
        }

        @Override
        public ListLocationsResponse getDefaultInstanceForType() {
            return ListLocationsResponse.getDefaultInstance();
        }

        @Override
        public ListLocationsResponse build() {
            ListLocationsResponse result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ListLocationsResponse buildPartial() {
            ListLocationsResponse result = new ListLocationsResponse(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(ListLocationsResponse result) {
            if (this.locationsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.locations_ = Collections.unmodifiableList(this.locations_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.locations_ = this.locations_;
            } else {
                result.locations_ = this.locationsBuilder_.build();
            }
        }

        private void buildPartial0(ListLocationsResponse result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 2) != 0) {
                result.nextPageToken_ = this.nextPageToken_;
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
            if (other instanceof ListLocationsResponse) {
                return this.mergeFrom((ListLocationsResponse)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ListLocationsResponse other) {
            if (other == ListLocationsResponse.getDefaultInstance()) {
                return this;
            }
            if (this.locationsBuilder_ == null) {
                if (!other.locations_.isEmpty()) {
                    if (this.locations_.isEmpty()) {
                        this.locations_ = other.locations_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureLocationsIsMutable();
                        this.locations_.addAll(other.locations_);
                    }
                    this.onChanged();
                }
            } else if (!other.locations_.isEmpty()) {
                if (this.locationsBuilder_.isEmpty()) {
                    this.locationsBuilder_.dispose();
                    this.locationsBuilder_ = null;
                    this.locations_ = other.locations_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.locationsBuilder_ = alwaysUseFieldBuilders ? this.getLocationsFieldBuilder() : null;
                } else {
                    this.locationsBuilder_.addAllMessages(other.locations_);
                }
            }
            if (!other.getNextPageToken().isEmpty()) {
                this.nextPageToken_ = other.nextPageToken_;
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
                            Location m4 = input.readMessage(Location.parser(), extensionRegistry);
                            if (this.locationsBuilder_ == null) {
                                this.ensureLocationsIsMutable();
                                this.locations_.add(m4);
                                continue block10;
                            }
                            this.locationsBuilder_.addMessage(m4);
                            continue block10;
                        }
                        case 18: {
                            this.nextPageToken_ = input.readStringRequireUtf8();
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

        private void ensureLocationsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.locations_ = new ArrayList<Location>(this.locations_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<Location> getLocationsList() {
            if (this.locationsBuilder_ == null) {
                return Collections.unmodifiableList(this.locations_);
            }
            return this.locationsBuilder_.getMessageList();
        }

        @Override
        public int getLocationsCount() {
            if (this.locationsBuilder_ == null) {
                return this.locations_.size();
            }
            return this.locationsBuilder_.getCount();
        }

        @Override
        public Location getLocations(int index) {
            if (this.locationsBuilder_ == null) {
                return this.locations_.get(index);
            }
            return this.locationsBuilder_.getMessage(index);
        }

        public Builder setLocations(int index, Location value) {
            if (this.locationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLocationsIsMutable();
                this.locations_.set(index, value);
                this.onChanged();
            } else {
                this.locationsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLocations(int index, Location.Builder builderForValue) {
            if (this.locationsBuilder_ == null) {
                this.ensureLocationsIsMutable();
                this.locations_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.locationsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLocations(Location value) {
            if (this.locationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLocationsIsMutable();
                this.locations_.add(value);
                this.onChanged();
            } else {
                this.locationsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLocations(int index, Location value) {
            if (this.locationsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLocationsIsMutable();
                this.locations_.add(index, value);
                this.onChanged();
            } else {
                this.locationsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLocations(Location.Builder builderForValue) {
            if (this.locationsBuilder_ == null) {
                this.ensureLocationsIsMutable();
                this.locations_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.locationsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLocations(int index, Location.Builder builderForValue) {
            if (this.locationsBuilder_ == null) {
                this.ensureLocationsIsMutable();
                this.locations_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.locationsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLocations(Iterable<? extends Location> values2) {
            if (this.locationsBuilder_ == null) {
                this.ensureLocationsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.locations_);
                this.onChanged();
            } else {
                this.locationsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLocations() {
            if (this.locationsBuilder_ == null) {
                this.locations_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.locationsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLocations(int index) {
            if (this.locationsBuilder_ == null) {
                this.ensureLocationsIsMutable();
                this.locations_.remove(index);
                this.onChanged();
            } else {
                this.locationsBuilder_.remove(index);
            }
            return this;
        }

        public Location.Builder getLocationsBuilder(int index) {
            return this.getLocationsFieldBuilder().getBuilder(index);
        }

        @Override
        public LocationOrBuilder getLocationsOrBuilder(int index) {
            if (this.locationsBuilder_ == null) {
                return this.locations_.get(index);
            }
            return this.locationsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends LocationOrBuilder> getLocationsOrBuilderList() {
            if (this.locationsBuilder_ != null) {
                return this.locationsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.locations_);
        }

        public Location.Builder addLocationsBuilder() {
            return this.getLocationsFieldBuilder().addBuilder(Location.getDefaultInstance());
        }

        public Location.Builder addLocationsBuilder(int index) {
            return this.getLocationsFieldBuilder().addBuilder(index, Location.getDefaultInstance());
        }

        public List<Location.Builder> getLocationsBuilderList() {
            return this.getLocationsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Location, Location.Builder, LocationOrBuilder> getLocationsFieldBuilder() {
            if (this.locationsBuilder_ == null) {
                this.locationsBuilder_ = new RepeatedFieldBuilderV3(this.locations_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.locations_ = null;
            }
            return this.locationsBuilder_;
        }

        @Override
        public String getNextPageToken() {
            Object ref = this.nextPageToken_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.nextPageToken_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNextPageTokenBytes() {
            Object ref = this.nextPageToken_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.nextPageToken_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setNextPageToken(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.nextPageToken_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearNextPageToken() {
            this.nextPageToken_ = ListLocationsResponse.getDefaultInstance().getNextPageToken();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setNextPageTokenBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ListLocationsResponse.checkByteStringIsUtf8(value);
            this.nextPageToken_ = value;
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

