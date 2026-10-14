/*
 * Decompiled with CFR 0.152.
 */
package com.google.type;

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
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.type.PostalAddressOrBuilder;
import com.google.type.PostalAddressProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class PostalAddress
extends GeneratedMessageV3
implements PostalAddressOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int REVISION_FIELD_NUMBER = 1;
    private int revision_ = 0;
    public static final int REGION_CODE_FIELD_NUMBER = 2;
    private volatile Object regionCode_ = "";
    public static final int LANGUAGE_CODE_FIELD_NUMBER = 3;
    private volatile Object languageCode_ = "";
    public static final int POSTAL_CODE_FIELD_NUMBER = 4;
    private volatile Object postalCode_ = "";
    public static final int SORTING_CODE_FIELD_NUMBER = 5;
    private volatile Object sortingCode_ = "";
    public static final int ADMINISTRATIVE_AREA_FIELD_NUMBER = 6;
    private volatile Object administrativeArea_ = "";
    public static final int LOCALITY_FIELD_NUMBER = 7;
    private volatile Object locality_ = "";
    public static final int SUBLOCALITY_FIELD_NUMBER = 8;
    private volatile Object sublocality_ = "";
    public static final int ADDRESS_LINES_FIELD_NUMBER = 9;
    private LazyStringArrayList addressLines_ = LazyStringArrayList.emptyList();
    public static final int RECIPIENTS_FIELD_NUMBER = 10;
    private LazyStringArrayList recipients_ = LazyStringArrayList.emptyList();
    public static final int ORGANIZATION_FIELD_NUMBER = 11;
    private volatile Object organization_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final PostalAddress DEFAULT_INSTANCE = new PostalAddress();
    private static final Parser<PostalAddress> PARSER = new AbstractParser<PostalAddress>(){

        @Override
        public PostalAddress parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = PostalAddress.newBuilder();
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

    private PostalAddress(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private PostalAddress() {
        this.regionCode_ = "";
        this.languageCode_ = "";
        this.postalCode_ = "";
        this.sortingCode_ = "";
        this.administrativeArea_ = "";
        this.locality_ = "";
        this.sublocality_ = "";
        this.addressLines_ = LazyStringArrayList.emptyList();
        this.recipients_ = LazyStringArrayList.emptyList();
        this.organization_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new PostalAddress();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return PostalAddressProto.internal_static_google_type_PostalAddress_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return PostalAddressProto.internal_static_google_type_PostalAddress_fieldAccessorTable.ensureFieldAccessorsInitialized(PostalAddress.class, Builder.class);
    }

    @Override
    public int getRevision() {
        return this.revision_;
    }

    @Override
    public String getRegionCode() {
        Object ref = this.regionCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.regionCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getRegionCodeBytes() {
        Object ref = this.regionCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.regionCode_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getLanguageCode() {
        Object ref = this.languageCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.languageCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getLanguageCodeBytes() {
        Object ref = this.languageCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.languageCode_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getPostalCode() {
        Object ref = this.postalCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.postalCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getPostalCodeBytes() {
        Object ref = this.postalCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.postalCode_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getSortingCode() {
        Object ref = this.sortingCode_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.sortingCode_ = s2;
        return s2;
    }

    @Override
    public ByteString getSortingCodeBytes() {
        Object ref = this.sortingCode_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.sortingCode_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getAdministrativeArea() {
        Object ref = this.administrativeArea_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.administrativeArea_ = s2;
        return s2;
    }

    @Override
    public ByteString getAdministrativeAreaBytes() {
        Object ref = this.administrativeArea_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.administrativeArea_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getLocality() {
        Object ref = this.locality_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.locality_ = s2;
        return s2;
    }

    @Override
    public ByteString getLocalityBytes() {
        Object ref = this.locality_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.locality_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getSublocality() {
        Object ref = this.sublocality_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.sublocality_ = s2;
        return s2;
    }

    @Override
    public ByteString getSublocalityBytes() {
        Object ref = this.sublocality_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.sublocality_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    public ProtocolStringList getAddressLinesList() {
        return this.addressLines_;
    }

    @Override
    public int getAddressLinesCount() {
        return this.addressLines_.size();
    }

    @Override
    public String getAddressLines(int index) {
        return this.addressLines_.get(index);
    }

    @Override
    public ByteString getAddressLinesBytes(int index) {
        return this.addressLines_.getByteString(index);
    }

    public ProtocolStringList getRecipientsList() {
        return this.recipients_;
    }

    @Override
    public int getRecipientsCount() {
        return this.recipients_.size();
    }

    @Override
    public String getRecipients(int index) {
        return this.recipients_.get(index);
    }

    @Override
    public ByteString getRecipientsBytes(int index) {
        return this.recipients_.getByteString(index);
    }

    @Override
    public String getOrganization() {
        Object ref = this.organization_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.organization_ = s2;
        return s2;
    }

    @Override
    public ByteString getOrganizationBytes() {
        Object ref = this.organization_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.organization_ = b;
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
        int i;
        if (this.revision_ != 0) {
            output.writeInt32(1, this.revision_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
            GeneratedMessageV3.writeString(output, 2, this.regionCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.languageCode_)) {
            GeneratedMessageV3.writeString(output, 3, this.languageCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.postalCode_)) {
            GeneratedMessageV3.writeString(output, 4, this.postalCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.sortingCode_)) {
            GeneratedMessageV3.writeString(output, 5, this.sortingCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.administrativeArea_)) {
            GeneratedMessageV3.writeString(output, 6, this.administrativeArea_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.locality_)) {
            GeneratedMessageV3.writeString(output, 7, this.locality_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.sublocality_)) {
            GeneratedMessageV3.writeString(output, 8, this.sublocality_);
        }
        for (i = 0; i < this.addressLines_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 9, this.addressLines_.getRaw(i));
        }
        for (i = 0; i < this.recipients_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 10, this.recipients_.getRaw(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.organization_)) {
            GeneratedMessageV3.writeString(output, 11, this.organization_);
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
        if (this.revision_ != 0) {
            size += CodedOutputStream.computeInt32Size(1, this.revision_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.regionCode_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.regionCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.languageCode_)) {
            size += GeneratedMessageV3.computeStringSize(3, this.languageCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.postalCode_)) {
            size += GeneratedMessageV3.computeStringSize(4, this.postalCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.sortingCode_)) {
            size += GeneratedMessageV3.computeStringSize(5, this.sortingCode_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.administrativeArea_)) {
            size += GeneratedMessageV3.computeStringSize(6, this.administrativeArea_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.locality_)) {
            size += GeneratedMessageV3.computeStringSize(7, this.locality_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.sublocality_)) {
            size += GeneratedMessageV3.computeStringSize(8, this.sublocality_);
        }
        int dataSize = 0;
        for (i = 0; i < this.addressLines_.size(); ++i) {
            dataSize += PostalAddress.computeStringSizeNoTag(this.addressLines_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getAddressLinesList().size();
        dataSize = 0;
        for (i = 0; i < this.recipients_.size(); ++i) {
            dataSize += PostalAddress.computeStringSizeNoTag(this.recipients_.getRaw(i));
        }
        size += dataSize;
        size += 1 * this.getRecipientsList().size();
        if (!GeneratedMessageV3.isStringEmpty(this.organization_)) {
            size += GeneratedMessageV3.computeStringSize(11, this.organization_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PostalAddress)) {
            return super.equals(obj);
        }
        PostalAddress other = (PostalAddress)obj;
        if (this.getRevision() != other.getRevision()) {
            return false;
        }
        if (!this.getRegionCode().equals(other.getRegionCode())) {
            return false;
        }
        if (!this.getLanguageCode().equals(other.getLanguageCode())) {
            return false;
        }
        if (!this.getPostalCode().equals(other.getPostalCode())) {
            return false;
        }
        if (!this.getSortingCode().equals(other.getSortingCode())) {
            return false;
        }
        if (!this.getAdministrativeArea().equals(other.getAdministrativeArea())) {
            return false;
        }
        if (!this.getLocality().equals(other.getLocality())) {
            return false;
        }
        if (!this.getSublocality().equals(other.getSublocality())) {
            return false;
        }
        if (!this.getAddressLinesList().equals(other.getAddressLinesList())) {
            return false;
        }
        if (!this.getRecipientsList().equals(other.getRecipientsList())) {
            return false;
        }
        if (!this.getOrganization().equals(other.getOrganization())) {
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
        hash = 19 * hash + PostalAddress.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getRevision();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getRegionCode().hashCode();
        hash = 37 * hash + 3;
        hash = 53 * hash + this.getLanguageCode().hashCode();
        hash = 37 * hash + 4;
        hash = 53 * hash + this.getPostalCode().hashCode();
        hash = 37 * hash + 5;
        hash = 53 * hash + this.getSortingCode().hashCode();
        hash = 37 * hash + 6;
        hash = 53 * hash + this.getAdministrativeArea().hashCode();
        hash = 37 * hash + 7;
        hash = 53 * hash + this.getLocality().hashCode();
        hash = 37 * hash + 8;
        hash = 53 * hash + this.getSublocality().hashCode();
        if (this.getAddressLinesCount() > 0) {
            hash = 37 * hash + 9;
            hash = 53 * hash + this.getAddressLinesList().hashCode();
        }
        if (this.getRecipientsCount() > 0) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.getRecipientsList().hashCode();
        }
        hash = 37 * hash + 11;
        hash = 53 * hash + this.getOrganization().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static PostalAddress parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PostalAddress parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PostalAddress parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PostalAddress parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PostalAddress parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static PostalAddress parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static PostalAddress parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PostalAddress parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static PostalAddress parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static PostalAddress parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static PostalAddress parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static PostalAddress parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return PostalAddress.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(PostalAddress prototype) {
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

    public static PostalAddress getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<PostalAddress> parser() {
        return PARSER;
    }

    public Parser<PostalAddress> getParserForType() {
        return PARSER;
    }

    @Override
    public PostalAddress getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PostalAddressOrBuilder {
        private int bitField0_;
        private int revision_;
        private Object regionCode_ = "";
        private Object languageCode_ = "";
        private Object postalCode_ = "";
        private Object sortingCode_ = "";
        private Object administrativeArea_ = "";
        private Object locality_ = "";
        private Object sublocality_ = "";
        private LazyStringArrayList addressLines_ = LazyStringArrayList.emptyList();
        private LazyStringArrayList recipients_ = LazyStringArrayList.emptyList();
        private Object organization_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return PostalAddressProto.internal_static_google_type_PostalAddress_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return PostalAddressProto.internal_static_google_type_PostalAddress_fieldAccessorTable.ensureFieldAccessorsInitialized(PostalAddress.class, Builder.class);
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
            this.revision_ = 0;
            this.regionCode_ = "";
            this.languageCode_ = "";
            this.postalCode_ = "";
            this.sortingCode_ = "";
            this.administrativeArea_ = "";
            this.locality_ = "";
            this.sublocality_ = "";
            this.addressLines_ = LazyStringArrayList.emptyList();
            this.recipients_ = LazyStringArrayList.emptyList();
            this.organization_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return PostalAddressProto.internal_static_google_type_PostalAddress_descriptor;
        }

        @Override
        public PostalAddress getDefaultInstanceForType() {
            return PostalAddress.getDefaultInstance();
        }

        @Override
        public PostalAddress build() {
            PostalAddress result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public PostalAddress buildPartial() {
            PostalAddress result = new PostalAddress(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(PostalAddress result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.revision_ = this.revision_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.regionCode_ = this.regionCode_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.languageCode_ = this.languageCode_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.postalCode_ = this.postalCode_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.sortingCode_ = this.sortingCode_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.administrativeArea_ = this.administrativeArea_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.locality_ = this.locality_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.sublocality_ = this.sublocality_;
            }
            if ((from_bitField0_ & 0x100) != 0) {
                this.addressLines_.makeImmutable();
                result.addressLines_ = this.addressLines_;
            }
            if ((from_bitField0_ & 0x200) != 0) {
                this.recipients_.makeImmutable();
                result.recipients_ = this.recipients_;
            }
            if ((from_bitField0_ & 0x400) != 0) {
                result.organization_ = this.organization_;
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
            if (other instanceof PostalAddress) {
                return this.mergeFrom((PostalAddress)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(PostalAddress other) {
            if (other == PostalAddress.getDefaultInstance()) {
                return this;
            }
            if (other.getRevision() != 0) {
                this.setRevision(other.getRevision());
            }
            if (!other.getRegionCode().isEmpty()) {
                this.regionCode_ = other.regionCode_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getLanguageCode().isEmpty()) {
                this.languageCode_ = other.languageCode_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getPostalCode().isEmpty()) {
                this.postalCode_ = other.postalCode_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (!other.getSortingCode().isEmpty()) {
                this.sortingCode_ = other.sortingCode_;
                this.bitField0_ |= 0x10;
                this.onChanged();
            }
            if (!other.getAdministrativeArea().isEmpty()) {
                this.administrativeArea_ = other.administrativeArea_;
                this.bitField0_ |= 0x20;
                this.onChanged();
            }
            if (!other.getLocality().isEmpty()) {
                this.locality_ = other.locality_;
                this.bitField0_ |= 0x40;
                this.onChanged();
            }
            if (!other.getSublocality().isEmpty()) {
                this.sublocality_ = other.sublocality_;
                this.bitField0_ |= 0x80;
                this.onChanged();
            }
            if (!other.addressLines_.isEmpty()) {
                if (this.addressLines_.isEmpty()) {
                    this.addressLines_ = other.addressLines_;
                    this.bitField0_ |= 0x100;
                } else {
                    this.ensureAddressLinesIsMutable();
                    this.addressLines_.addAll(other.addressLines_);
                }
                this.onChanged();
            }
            if (!other.recipients_.isEmpty()) {
                if (this.recipients_.isEmpty()) {
                    this.recipients_ = other.recipients_;
                    this.bitField0_ |= 0x200;
                } else {
                    this.ensureRecipientsIsMutable();
                    this.recipients_.addAll(other.recipients_);
                }
                this.onChanged();
            }
            if (!other.getOrganization().isEmpty()) {
                this.organization_ = other.organization_;
                this.bitField0_ |= 0x400;
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
                block19: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block19;
                        }
                        case 8: {
                            this.revision_ = input.readInt32();
                            this.bitField0_ |= 1;
                            continue block19;
                        }
                        case 18: {
                            this.regionCode_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block19;
                        }
                        case 26: {
                            this.languageCode_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block19;
                        }
                        case 34: {
                            this.postalCode_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block19;
                        }
                        case 42: {
                            this.sortingCode_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
                            continue block19;
                        }
                        case 50: {
                            this.administrativeArea_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x20;
                            continue block19;
                        }
                        case 58: {
                            this.locality_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x40;
                            continue block19;
                        }
                        case 66: {
                            this.sublocality_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x80;
                            continue block19;
                        }
                        case 74: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureAddressLinesIsMutable();
                            this.addressLines_.add(s2);
                            continue block19;
                        }
                        case 82: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureRecipientsIsMutable();
                            this.recipients_.add(s2);
                            continue block19;
                        }
                        case 90: {
                            this.organization_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x400;
                            continue block19;
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
        public int getRevision() {
            return this.revision_;
        }

        public Builder setRevision(int value) {
            this.revision_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearRevision() {
            this.bitField0_ &= 0xFFFFFFFE;
            this.revision_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public String getRegionCode() {
            Object ref = this.regionCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.regionCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getRegionCodeBytes() {
            Object ref = this.regionCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.regionCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setRegionCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.regionCode_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearRegionCode() {
            this.regionCode_ = PostalAddress.getDefaultInstance().getRegionCode();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setRegionCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.regionCode_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getLanguageCode() {
            Object ref = this.languageCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.languageCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLanguageCodeBytes() {
            Object ref = this.languageCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.languageCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLanguageCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.languageCode_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearLanguageCode() {
            this.languageCode_ = PostalAddress.getDefaultInstance().getLanguageCode();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setLanguageCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.languageCode_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getPostalCode() {
            Object ref = this.postalCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.postalCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getPostalCodeBytes() {
            Object ref = this.postalCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.postalCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setPostalCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.postalCode_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearPostalCode() {
            this.postalCode_ = PostalAddress.getDefaultInstance().getPostalCode();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setPostalCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.postalCode_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public String getSortingCode() {
            Object ref = this.sortingCode_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.sortingCode_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSortingCodeBytes() {
            Object ref = this.sortingCode_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.sortingCode_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSortingCode(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.sortingCode_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearSortingCode() {
            this.sortingCode_ = PostalAddress.getDefaultInstance().getSortingCode();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setSortingCodeBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.sortingCode_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        @Override
        public String getAdministrativeArea() {
            Object ref = this.administrativeArea_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.administrativeArea_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getAdministrativeAreaBytes() {
            Object ref = this.administrativeArea_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.administrativeArea_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setAdministrativeArea(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.administrativeArea_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearAdministrativeArea() {
            this.administrativeArea_ = PostalAddress.getDefaultInstance().getAdministrativeArea();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder setAdministrativeAreaBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.administrativeArea_ = value;
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public String getLocality() {
            Object ref = this.locality_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.locality_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getLocalityBytes() {
            Object ref = this.locality_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.locality_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setLocality(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.locality_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearLocality() {
            this.locality_ = PostalAddress.getDefaultInstance().getLocality();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        public Builder setLocalityBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.locality_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        @Override
        public String getSublocality() {
            Object ref = this.sublocality_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.sublocality_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getSublocalityBytes() {
            Object ref = this.sublocality_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.sublocality_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setSublocality(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.sublocality_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearSublocality() {
            this.sublocality_ = PostalAddress.getDefaultInstance().getSublocality();
            this.bitField0_ &= 0xFFFFFF7F;
            this.onChanged();
            return this;
        }

        public Builder setSublocalityBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.sublocality_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        private void ensureAddressLinesIsMutable() {
            if (!this.addressLines_.isModifiable()) {
                this.addressLines_ = new LazyStringArrayList(this.addressLines_);
            }
            this.bitField0_ |= 0x100;
        }

        public ProtocolStringList getAddressLinesList() {
            this.addressLines_.makeImmutable();
            return this.addressLines_;
        }

        @Override
        public int getAddressLinesCount() {
            return this.addressLines_.size();
        }

        @Override
        public String getAddressLines(int index) {
            return this.addressLines_.get(index);
        }

        @Override
        public ByteString getAddressLinesBytes(int index) {
            return this.addressLines_.getByteString(index);
        }

        public Builder setAddressLines(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAddressLinesIsMutable();
            this.addressLines_.set(index, value);
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder addAddressLines(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureAddressLinesIsMutable();
            this.addressLines_.add(value);
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder addAllAddressLines(Iterable<String> values2) {
            this.ensureAddressLinesIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.addressLines_);
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearAddressLines() {
            this.addressLines_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFEFF;
            this.onChanged();
            return this;
        }

        public Builder addAddressLinesBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.ensureAddressLinesIsMutable();
            this.addressLines_.add(value);
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        private void ensureRecipientsIsMutable() {
            if (!this.recipients_.isModifiable()) {
                this.recipients_ = new LazyStringArrayList(this.recipients_);
            }
            this.bitField0_ |= 0x200;
        }

        public ProtocolStringList getRecipientsList() {
            this.recipients_.makeImmutable();
            return this.recipients_;
        }

        @Override
        public int getRecipientsCount() {
            return this.recipients_.size();
        }

        @Override
        public String getRecipients(int index) {
            return this.recipients_.get(index);
        }

        @Override
        public ByteString getRecipientsBytes(int index) {
            return this.recipients_.getByteString(index);
        }

        public Builder setRecipients(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRecipientsIsMutable();
            this.recipients_.set(index, value);
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder addRecipients(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureRecipientsIsMutable();
            this.recipients_.add(value);
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder addAllRecipients(Iterable<String> values2) {
            this.ensureRecipientsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.recipients_);
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearRecipients() {
            this.recipients_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFDFF;
            this.onChanged();
            return this;
        }

        public Builder addRecipientsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.ensureRecipientsIsMutable();
            this.recipients_.add(value);
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        @Override
        public String getOrganization() {
            Object ref = this.organization_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.organization_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getOrganizationBytes() {
            Object ref = this.organization_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.organization_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setOrganization(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.organization_ = value;
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearOrganization() {
            this.organization_ = PostalAddress.getDefaultInstance().getOrganization();
            this.bitField0_ &= 0xFFFFFBFF;
            this.onChanged();
            return this;
        }

        public Builder setOrganizationBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            PostalAddress.checkByteStringIsUtf8(value);
            this.organization_ = value;
            this.bitField0_ |= 0x400;
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

