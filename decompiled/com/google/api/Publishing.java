/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientLibraryOrganization;
import com.google.api.ClientLibrarySettings;
import com.google.api.ClientLibrarySettingsOrBuilder;
import com.google.api.ClientProto;
import com.google.api.MethodSettings;
import com.google.api.MethodSettingsOrBuilder;
import com.google.api.PublishingOrBuilder;
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
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Publishing
extends GeneratedMessageV3
implements PublishingOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int METHOD_SETTINGS_FIELD_NUMBER = 2;
    private List<MethodSettings> methodSettings_;
    public static final int NEW_ISSUE_URI_FIELD_NUMBER = 101;
    private volatile Object newIssueUri_ = "";
    public static final int DOCUMENTATION_URI_FIELD_NUMBER = 102;
    private volatile Object documentationUri_ = "";
    public static final int API_SHORT_NAME_FIELD_NUMBER = 103;
    private volatile Object apiShortName_ = "";
    public static final int GITHUB_LABEL_FIELD_NUMBER = 104;
    private volatile Object githubLabel_ = "";
    public static final int CODEOWNER_GITHUB_TEAMS_FIELD_NUMBER = 105;
    private LazyStringArrayList codeownerGithubTeams_ = LazyStringArrayList.emptyList();
    public static final int DOC_TAG_PREFIX_FIELD_NUMBER = 106;
    private volatile Object docTagPrefix_ = "";
    public static final int ORGANIZATION_FIELD_NUMBER = 107;
    private int organization_ = 0;
    public static final int LIBRARY_SETTINGS_FIELD_NUMBER = 109;
    private List<ClientLibrarySettings> librarySettings_;
    public static final int PROTO_REFERENCE_DOCUMENTATION_URI_FIELD_NUMBER = 110;
    private volatile Object protoReferenceDocumentationUri_ = "";
    private byte memoizedIsInitialized = (byte)-1;
    private static final Publishing DEFAULT_INSTANCE = new Publishing();
    private static final Parser<Publishing> PARSER = new AbstractParser<Publishing>(){

        @Override
        public Publishing parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Publishing.newBuilder();
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

    private Publishing(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Publishing() {
        this.methodSettings_ = Collections.emptyList();
        this.newIssueUri_ = "";
        this.documentationUri_ = "";
        this.apiShortName_ = "";
        this.githubLabel_ = "";
        this.codeownerGithubTeams_ = LazyStringArrayList.emptyList();
        this.docTagPrefix_ = "";
        this.organization_ = 0;
        this.librarySettings_ = Collections.emptyList();
        this.protoReferenceDocumentationUri_ = "";
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Publishing();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_Publishing_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_Publishing_fieldAccessorTable.ensureFieldAccessorsInitialized(Publishing.class, Builder.class);
    }

    @Override
    public List<MethodSettings> getMethodSettingsList() {
        return this.methodSettings_;
    }

    @Override
    public List<? extends MethodSettingsOrBuilder> getMethodSettingsOrBuilderList() {
        return this.methodSettings_;
    }

    @Override
    public int getMethodSettingsCount() {
        return this.methodSettings_.size();
    }

    @Override
    public MethodSettings getMethodSettings(int index) {
        return this.methodSettings_.get(index);
    }

    @Override
    public MethodSettingsOrBuilder getMethodSettingsOrBuilder(int index) {
        return this.methodSettings_.get(index);
    }

    @Override
    public String getNewIssueUri() {
        Object ref = this.newIssueUri_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.newIssueUri_ = s2;
        return s2;
    }

    @Override
    public ByteString getNewIssueUriBytes() {
        Object ref = this.newIssueUri_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.newIssueUri_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getDocumentationUri() {
        Object ref = this.documentationUri_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.documentationUri_ = s2;
        return s2;
    }

    @Override
    public ByteString getDocumentationUriBytes() {
        Object ref = this.documentationUri_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.documentationUri_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getApiShortName() {
        Object ref = this.apiShortName_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.apiShortName_ = s2;
        return s2;
    }

    @Override
    public ByteString getApiShortNameBytes() {
        Object ref = this.apiShortName_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.apiShortName_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getGithubLabel() {
        Object ref = this.githubLabel_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.githubLabel_ = s2;
        return s2;
    }

    @Override
    public ByteString getGithubLabelBytes() {
        Object ref = this.githubLabel_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.githubLabel_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    public ProtocolStringList getCodeownerGithubTeamsList() {
        return this.codeownerGithubTeams_;
    }

    @Override
    public int getCodeownerGithubTeamsCount() {
        return this.codeownerGithubTeams_.size();
    }

    @Override
    public String getCodeownerGithubTeams(int index) {
        return this.codeownerGithubTeams_.get(index);
    }

    @Override
    public ByteString getCodeownerGithubTeamsBytes(int index) {
        return this.codeownerGithubTeams_.getByteString(index);
    }

    @Override
    public String getDocTagPrefix() {
        Object ref = this.docTagPrefix_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.docTagPrefix_ = s2;
        return s2;
    }

    @Override
    public ByteString getDocTagPrefixBytes() {
        Object ref = this.docTagPrefix_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.docTagPrefix_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getOrganizationValue() {
        return this.organization_;
    }

    @Override
    public ClientLibraryOrganization getOrganization() {
        ClientLibraryOrganization result = ClientLibraryOrganization.forNumber(this.organization_);
        return result == null ? ClientLibraryOrganization.UNRECOGNIZED : result;
    }

    @Override
    public List<ClientLibrarySettings> getLibrarySettingsList() {
        return this.librarySettings_;
    }

    @Override
    public List<? extends ClientLibrarySettingsOrBuilder> getLibrarySettingsOrBuilderList() {
        return this.librarySettings_;
    }

    @Override
    public int getLibrarySettingsCount() {
        return this.librarySettings_.size();
    }

    @Override
    public ClientLibrarySettings getLibrarySettings(int index) {
        return this.librarySettings_.get(index);
    }

    @Override
    public ClientLibrarySettingsOrBuilder getLibrarySettingsOrBuilder(int index) {
        return this.librarySettings_.get(index);
    }

    @Override
    public String getProtoReferenceDocumentationUri() {
        Object ref = this.protoReferenceDocumentationUri_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.protoReferenceDocumentationUri_ = s2;
        return s2;
    }

    @Override
    public ByteString getProtoReferenceDocumentationUriBytes() {
        Object ref = this.protoReferenceDocumentationUri_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.protoReferenceDocumentationUri_ = b;
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
        for (i = 0; i < this.methodSettings_.size(); ++i) {
            output.writeMessage(2, this.methodSettings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.newIssueUri_)) {
            GeneratedMessageV3.writeString(output, 101, this.newIssueUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.documentationUri_)) {
            GeneratedMessageV3.writeString(output, 102, this.documentationUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.apiShortName_)) {
            GeneratedMessageV3.writeString(output, 103, this.apiShortName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.githubLabel_)) {
            GeneratedMessageV3.writeString(output, 104, this.githubLabel_);
        }
        for (i = 0; i < this.codeownerGithubTeams_.size(); ++i) {
            GeneratedMessageV3.writeString(output, 105, this.codeownerGithubTeams_.getRaw(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.docTagPrefix_)) {
            GeneratedMessageV3.writeString(output, 106, this.docTagPrefix_);
        }
        if (this.organization_ != ClientLibraryOrganization.CLIENT_LIBRARY_ORGANIZATION_UNSPECIFIED.getNumber()) {
            output.writeEnum(107, this.organization_);
        }
        for (i = 0; i < this.librarySettings_.size(); ++i) {
            output.writeMessage(109, this.librarySettings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protoReferenceDocumentationUri_)) {
            GeneratedMessageV3.writeString(output, 110, this.protoReferenceDocumentationUri_);
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
        for (i = 0; i < this.methodSettings_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(2, this.methodSettings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.newIssueUri_)) {
            size += GeneratedMessageV3.computeStringSize(101, this.newIssueUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.documentationUri_)) {
            size += GeneratedMessageV3.computeStringSize(102, this.documentationUri_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.apiShortName_)) {
            size += GeneratedMessageV3.computeStringSize(103, this.apiShortName_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.githubLabel_)) {
            size += GeneratedMessageV3.computeStringSize(104, this.githubLabel_);
        }
        int dataSize = 0;
        for (int i2 = 0; i2 < this.codeownerGithubTeams_.size(); ++i2) {
            dataSize += Publishing.computeStringSizeNoTag(this.codeownerGithubTeams_.getRaw(i2));
        }
        size += dataSize;
        size += 2 * this.getCodeownerGithubTeamsList().size();
        if (!GeneratedMessageV3.isStringEmpty(this.docTagPrefix_)) {
            size += GeneratedMessageV3.computeStringSize(106, this.docTagPrefix_);
        }
        if (this.organization_ != ClientLibraryOrganization.CLIENT_LIBRARY_ORGANIZATION_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(107, this.organization_);
        }
        for (i = 0; i < this.librarySettings_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(109, this.librarySettings_.get(i));
        }
        if (!GeneratedMessageV3.isStringEmpty(this.protoReferenceDocumentationUri_)) {
            size += GeneratedMessageV3.computeStringSize(110, this.protoReferenceDocumentationUri_);
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Publishing)) {
            return super.equals(obj);
        }
        Publishing other = (Publishing)obj;
        if (!this.getMethodSettingsList().equals(other.getMethodSettingsList())) {
            return false;
        }
        if (!this.getNewIssueUri().equals(other.getNewIssueUri())) {
            return false;
        }
        if (!this.getDocumentationUri().equals(other.getDocumentationUri())) {
            return false;
        }
        if (!this.getApiShortName().equals(other.getApiShortName())) {
            return false;
        }
        if (!this.getGithubLabel().equals(other.getGithubLabel())) {
            return false;
        }
        if (!this.getCodeownerGithubTeamsList().equals(other.getCodeownerGithubTeamsList())) {
            return false;
        }
        if (!this.getDocTagPrefix().equals(other.getDocTagPrefix())) {
            return false;
        }
        if (this.organization_ != other.organization_) {
            return false;
        }
        if (!this.getLibrarySettingsList().equals(other.getLibrarySettingsList())) {
            return false;
        }
        if (!this.getProtoReferenceDocumentationUri().equals(other.getProtoReferenceDocumentationUri())) {
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
        hash = 19 * hash + Publishing.getDescriptor().hashCode();
        if (this.getMethodSettingsCount() > 0) {
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMethodSettingsList().hashCode();
        }
        hash = 37 * hash + 101;
        hash = 53 * hash + this.getNewIssueUri().hashCode();
        hash = 37 * hash + 102;
        hash = 53 * hash + this.getDocumentationUri().hashCode();
        hash = 37 * hash + 103;
        hash = 53 * hash + this.getApiShortName().hashCode();
        hash = 37 * hash + 104;
        hash = 53 * hash + this.getGithubLabel().hashCode();
        if (this.getCodeownerGithubTeamsCount() > 0) {
            hash = 37 * hash + 105;
            hash = 53 * hash + this.getCodeownerGithubTeamsList().hashCode();
        }
        hash = 37 * hash + 106;
        hash = 53 * hash + this.getDocTagPrefix().hashCode();
        hash = 37 * hash + 107;
        hash = 53 * hash + this.organization_;
        if (this.getLibrarySettingsCount() > 0) {
            hash = 37 * hash + 109;
            hash = 53 * hash + this.getLibrarySettingsList().hashCode();
        }
        hash = 37 * hash + 110;
        hash = 53 * hash + this.getProtoReferenceDocumentationUri().hashCode();
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Publishing parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Publishing parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Publishing parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Publishing parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Publishing parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Publishing parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Publishing parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Publishing parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Publishing parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Publishing parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Publishing parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Publishing parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Publishing.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Publishing prototype) {
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

    public static Publishing getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Publishing> parser() {
        return PARSER;
    }

    public Parser<Publishing> getParserForType() {
        return PARSER;
    }

    @Override
    public Publishing getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements PublishingOrBuilder {
        private int bitField0_;
        private List<MethodSettings> methodSettings_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MethodSettings, MethodSettings.Builder, MethodSettingsOrBuilder> methodSettingsBuilder_;
        private Object newIssueUri_ = "";
        private Object documentationUri_ = "";
        private Object apiShortName_ = "";
        private Object githubLabel_ = "";
        private LazyStringArrayList codeownerGithubTeams_ = LazyStringArrayList.emptyList();
        private Object docTagPrefix_ = "";
        private int organization_ = 0;
        private List<ClientLibrarySettings> librarySettings_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<ClientLibrarySettings, ClientLibrarySettings.Builder, ClientLibrarySettingsOrBuilder> librarySettingsBuilder_;
        private Object protoReferenceDocumentationUri_ = "";

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_Publishing_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_Publishing_fieldAccessorTable.ensureFieldAccessorsInitialized(Publishing.class, Builder.class);
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
            if (this.methodSettingsBuilder_ == null) {
                this.methodSettings_ = Collections.emptyList();
            } else {
                this.methodSettings_ = null;
                this.methodSettingsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFFE;
            this.newIssueUri_ = "";
            this.documentationUri_ = "";
            this.apiShortName_ = "";
            this.githubLabel_ = "";
            this.codeownerGithubTeams_ = LazyStringArrayList.emptyList();
            this.docTagPrefix_ = "";
            this.organization_ = 0;
            if (this.librarySettingsBuilder_ == null) {
                this.librarySettings_ = Collections.emptyList();
            } else {
                this.librarySettings_ = null;
                this.librarySettingsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFEFF;
            this.protoReferenceDocumentationUri_ = "";
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_Publishing_descriptor;
        }

        @Override
        public Publishing getDefaultInstanceForType() {
            return Publishing.getDefaultInstance();
        }

        @Override
        public Publishing build() {
            Publishing result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Publishing buildPartial() {
            Publishing result = new Publishing(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Publishing result) {
            if (this.methodSettingsBuilder_ == null) {
                if ((this.bitField0_ & 1) != 0) {
                    this.methodSettings_ = Collections.unmodifiableList(this.methodSettings_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.methodSettings_ = this.methodSettings_;
            } else {
                result.methodSettings_ = this.methodSettingsBuilder_.build();
            }
            if (this.librarySettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x100) != 0) {
                    this.librarySettings_ = Collections.unmodifiableList(this.librarySettings_);
                    this.bitField0_ &= 0xFFFFFEFF;
                }
                result.librarySettings_ = this.librarySettings_;
            } else {
                result.librarySettings_ = this.librarySettingsBuilder_.build();
            }
        }

        private void buildPartial0(Publishing result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 2) != 0) {
                result.newIssueUri_ = this.newIssueUri_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.documentationUri_ = this.documentationUri_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.apiShortName_ = this.apiShortName_;
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.githubLabel_ = this.githubLabel_;
            }
            if ((from_bitField0_ & 0x20) != 0) {
                this.codeownerGithubTeams_.makeImmutable();
                result.codeownerGithubTeams_ = this.codeownerGithubTeams_;
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.docTagPrefix_ = this.docTagPrefix_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.organization_ = this.organization_;
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.protoReferenceDocumentationUri_ = this.protoReferenceDocumentationUri_;
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
            if (other instanceof Publishing) {
                return this.mergeFrom((Publishing)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Publishing other) {
            if (other == Publishing.getDefaultInstance()) {
                return this;
            }
            if (this.methodSettingsBuilder_ == null) {
                if (!other.methodSettings_.isEmpty()) {
                    if (this.methodSettings_.isEmpty()) {
                        this.methodSettings_ = other.methodSettings_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureMethodSettingsIsMutable();
                        this.methodSettings_.addAll(other.methodSettings_);
                    }
                    this.onChanged();
                }
            } else if (!other.methodSettings_.isEmpty()) {
                if (this.methodSettingsBuilder_.isEmpty()) {
                    this.methodSettingsBuilder_.dispose();
                    this.methodSettingsBuilder_ = null;
                    this.methodSettings_ = other.methodSettings_;
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.methodSettingsBuilder_ = alwaysUseFieldBuilders ? this.getMethodSettingsFieldBuilder() : null;
                } else {
                    this.methodSettingsBuilder_.addAllMessages(other.methodSettings_);
                }
            }
            if (!other.getNewIssueUri().isEmpty()) {
                this.newIssueUri_ = other.newIssueUri_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getDocumentationUri().isEmpty()) {
                this.documentationUri_ = other.documentationUri_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getApiShortName().isEmpty()) {
                this.apiShortName_ = other.apiShortName_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (!other.getGithubLabel().isEmpty()) {
                this.githubLabel_ = other.githubLabel_;
                this.bitField0_ |= 0x10;
                this.onChanged();
            }
            if (!other.codeownerGithubTeams_.isEmpty()) {
                if (this.codeownerGithubTeams_.isEmpty()) {
                    this.codeownerGithubTeams_ = other.codeownerGithubTeams_;
                    this.bitField0_ |= 0x20;
                } else {
                    this.ensureCodeownerGithubTeamsIsMutable();
                    this.codeownerGithubTeams_.addAll(other.codeownerGithubTeams_);
                }
                this.onChanged();
            }
            if (!other.getDocTagPrefix().isEmpty()) {
                this.docTagPrefix_ = other.docTagPrefix_;
                this.bitField0_ |= 0x40;
                this.onChanged();
            }
            if (other.organization_ != 0) {
                this.setOrganizationValue(other.getOrganizationValue());
            }
            if (this.librarySettingsBuilder_ == null) {
                if (!other.librarySettings_.isEmpty()) {
                    if (this.librarySettings_.isEmpty()) {
                        this.librarySettings_ = other.librarySettings_;
                        this.bitField0_ &= 0xFFFFFEFF;
                    } else {
                        this.ensureLibrarySettingsIsMutable();
                        this.librarySettings_.addAll(other.librarySettings_);
                    }
                    this.onChanged();
                }
            } else if (!other.librarySettings_.isEmpty()) {
                if (this.librarySettingsBuilder_.isEmpty()) {
                    this.librarySettingsBuilder_.dispose();
                    this.librarySettingsBuilder_ = null;
                    this.librarySettings_ = other.librarySettings_;
                    this.bitField0_ &= 0xFFFFFEFF;
                    this.librarySettingsBuilder_ = alwaysUseFieldBuilders ? this.getLibrarySettingsFieldBuilder() : null;
                } else {
                    this.librarySettingsBuilder_.addAllMessages(other.librarySettings_);
                }
            }
            if (!other.getProtoReferenceDocumentationUri().isEmpty()) {
                this.protoReferenceDocumentationUri_ = other.protoReferenceDocumentationUri_;
                this.bitField0_ |= 0x200;
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
                block18: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block18;
                        }
                        case 18: {
                            GeneratedMessageV3 m4 = input.readMessage(MethodSettings.parser(), extensionRegistry);
                            if (this.methodSettingsBuilder_ == null) {
                                this.ensureMethodSettingsIsMutable();
                                this.methodSettings_.add((MethodSettings)m4);
                                continue block18;
                            }
                            this.methodSettingsBuilder_.addMessage((MethodSettings)m4);
                            continue block18;
                        }
                        case 810: {
                            this.newIssueUri_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block18;
                        }
                        case 818: {
                            this.documentationUri_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block18;
                        }
                        case 826: {
                            this.apiShortName_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block18;
                        }
                        case 834: {
                            this.githubLabel_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x10;
                            continue block18;
                        }
                        case 842: {
                            String s2 = input.readStringRequireUtf8();
                            this.ensureCodeownerGithubTeamsIsMutable();
                            this.codeownerGithubTeams_.add(s2);
                            continue block18;
                        }
                        case 850: {
                            this.docTagPrefix_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x40;
                            continue block18;
                        }
                        case 856: {
                            this.organization_ = input.readEnum();
                            this.bitField0_ |= 0x80;
                            continue block18;
                        }
                        case 874: {
                            GeneratedMessageV3 m4 = input.readMessage(ClientLibrarySettings.parser(), extensionRegistry);
                            if (this.librarySettingsBuilder_ == null) {
                                this.ensureLibrarySettingsIsMutable();
                                this.librarySettings_.add((ClientLibrarySettings)m4);
                                continue block18;
                            }
                            this.librarySettingsBuilder_.addMessage((ClientLibrarySettings)m4);
                            continue block18;
                        }
                        case 882: {
                            this.protoReferenceDocumentationUri_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 0x200;
                            continue block18;
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

        private void ensureMethodSettingsIsMutable() {
            if ((this.bitField0_ & 1) == 0) {
                this.methodSettings_ = new ArrayList<MethodSettings>(this.methodSettings_);
                this.bitField0_ |= 1;
            }
        }

        @Override
        public List<MethodSettings> getMethodSettingsList() {
            if (this.methodSettingsBuilder_ == null) {
                return Collections.unmodifiableList(this.methodSettings_);
            }
            return this.methodSettingsBuilder_.getMessageList();
        }

        @Override
        public int getMethodSettingsCount() {
            if (this.methodSettingsBuilder_ == null) {
                return this.methodSettings_.size();
            }
            return this.methodSettingsBuilder_.getCount();
        }

        @Override
        public MethodSettings getMethodSettings(int index) {
            if (this.methodSettingsBuilder_ == null) {
                return this.methodSettings_.get(index);
            }
            return this.methodSettingsBuilder_.getMessage(index);
        }

        public Builder setMethodSettings(int index, MethodSettings value) {
            if (this.methodSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.set(index, value);
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setMethodSettings(int index, MethodSettings.Builder builderForValue) {
            if (this.methodSettingsBuilder_ == null) {
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMethodSettings(MethodSettings value) {
            if (this.methodSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.add(value);
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addMethodSettings(int index, MethodSettings value) {
            if (this.methodSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.add(index, value);
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addMethodSettings(MethodSettings.Builder builderForValue) {
            if (this.methodSettingsBuilder_ == null) {
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMethodSettings(int index, MethodSettings.Builder builderForValue) {
            if (this.methodSettingsBuilder_ == null) {
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMethodSettings(Iterable<? extends MethodSettings> values2) {
            if (this.methodSettingsBuilder_ == null) {
                this.ensureMethodSettingsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.methodSettings_);
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearMethodSettings() {
            if (this.methodSettingsBuilder_ == null) {
                this.methodSettings_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.clear();
            }
            return this;
        }

        public Builder removeMethodSettings(int index) {
            if (this.methodSettingsBuilder_ == null) {
                this.ensureMethodSettingsIsMutable();
                this.methodSettings_.remove(index);
                this.onChanged();
            } else {
                this.methodSettingsBuilder_.remove(index);
            }
            return this;
        }

        public MethodSettings.Builder getMethodSettingsBuilder(int index) {
            return this.getMethodSettingsFieldBuilder().getBuilder(index);
        }

        @Override
        public MethodSettingsOrBuilder getMethodSettingsOrBuilder(int index) {
            if (this.methodSettingsBuilder_ == null) {
                return this.methodSettings_.get(index);
            }
            return this.methodSettingsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MethodSettingsOrBuilder> getMethodSettingsOrBuilderList() {
            if (this.methodSettingsBuilder_ != null) {
                return this.methodSettingsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.methodSettings_);
        }

        public MethodSettings.Builder addMethodSettingsBuilder() {
            return this.getMethodSettingsFieldBuilder().addBuilder(MethodSettings.getDefaultInstance());
        }

        public MethodSettings.Builder addMethodSettingsBuilder(int index) {
            return this.getMethodSettingsFieldBuilder().addBuilder(index, MethodSettings.getDefaultInstance());
        }

        public List<MethodSettings.Builder> getMethodSettingsBuilderList() {
            return this.getMethodSettingsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MethodSettings, MethodSettings.Builder, MethodSettingsOrBuilder> getMethodSettingsFieldBuilder() {
            if (this.methodSettingsBuilder_ == null) {
                this.methodSettingsBuilder_ = new RepeatedFieldBuilderV3(this.methodSettings_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                this.methodSettings_ = null;
            }
            return this.methodSettingsBuilder_;
        }

        @Override
        public String getNewIssueUri() {
            Object ref = this.newIssueUri_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.newIssueUri_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNewIssueUriBytes() {
            Object ref = this.newIssueUri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.newIssueUri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setNewIssueUri(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.newIssueUri_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearNewIssueUri() {
            this.newIssueUri_ = Publishing.getDefaultInstance().getNewIssueUri();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setNewIssueUriBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.newIssueUri_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getDocumentationUri() {
            Object ref = this.documentationUri_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.documentationUri_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDocumentationUriBytes() {
            Object ref = this.documentationUri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.documentationUri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDocumentationUri(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.documentationUri_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearDocumentationUri() {
            this.documentationUri_ = Publishing.getDefaultInstance().getDocumentationUri();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setDocumentationUriBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.documentationUri_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getApiShortName() {
            Object ref = this.apiShortName_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.apiShortName_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getApiShortNameBytes() {
            Object ref = this.apiShortName_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.apiShortName_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setApiShortName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.apiShortName_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearApiShortName() {
            this.apiShortName_ = Publishing.getDefaultInstance().getApiShortName();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setApiShortNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.apiShortName_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        @Override
        public String getGithubLabel() {
            Object ref = this.githubLabel_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.githubLabel_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getGithubLabelBytes() {
            Object ref = this.githubLabel_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.githubLabel_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setGithubLabel(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.githubLabel_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearGithubLabel() {
            this.githubLabel_ = Publishing.getDefaultInstance().getGithubLabel();
            this.bitField0_ &= 0xFFFFFFEF;
            this.onChanged();
            return this;
        }

        public Builder setGithubLabelBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.githubLabel_ = value;
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        private void ensureCodeownerGithubTeamsIsMutable() {
            if (!this.codeownerGithubTeams_.isModifiable()) {
                this.codeownerGithubTeams_ = new LazyStringArrayList(this.codeownerGithubTeams_);
            }
            this.bitField0_ |= 0x20;
        }

        public ProtocolStringList getCodeownerGithubTeamsList() {
            this.codeownerGithubTeams_.makeImmutable();
            return this.codeownerGithubTeams_;
        }

        @Override
        public int getCodeownerGithubTeamsCount() {
            return this.codeownerGithubTeams_.size();
        }

        @Override
        public String getCodeownerGithubTeams(int index) {
            return this.codeownerGithubTeams_.get(index);
        }

        @Override
        public ByteString getCodeownerGithubTeamsBytes(int index) {
            return this.codeownerGithubTeams_.getByteString(index);
        }

        public Builder setCodeownerGithubTeams(int index, String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureCodeownerGithubTeamsIsMutable();
            this.codeownerGithubTeams_.set(index, value);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder addCodeownerGithubTeams(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.ensureCodeownerGithubTeamsIsMutable();
            this.codeownerGithubTeams_.add(value);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder addAllCodeownerGithubTeams(Iterable<String> values2) {
            this.ensureCodeownerGithubTeamsIsMutable();
            AbstractMessageLite.Builder.addAll(values2, this.codeownerGithubTeams_);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearCodeownerGithubTeams() {
            this.codeownerGithubTeams_ = LazyStringArrayList.emptyList();
            this.bitField0_ &= 0xFFFFFFDF;
            this.onChanged();
            return this;
        }

        public Builder addCodeownerGithubTeamsBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.ensureCodeownerGithubTeamsIsMutable();
            this.codeownerGithubTeams_.add(value);
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        @Override
        public String getDocTagPrefix() {
            Object ref = this.docTagPrefix_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.docTagPrefix_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getDocTagPrefixBytes() {
            Object ref = this.docTagPrefix_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.docTagPrefix_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setDocTagPrefix(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.docTagPrefix_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearDocTagPrefix() {
            this.docTagPrefix_ = Publishing.getDefaultInstance().getDocTagPrefix();
            this.bitField0_ &= 0xFFFFFFBF;
            this.onChanged();
            return this;
        }

        public Builder setDocTagPrefixBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.docTagPrefix_ = value;
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        @Override
        public int getOrganizationValue() {
            return this.organization_;
        }

        public Builder setOrganizationValue(int value) {
            this.organization_ = value;
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        @Override
        public ClientLibraryOrganization getOrganization() {
            ClientLibraryOrganization result = ClientLibraryOrganization.forNumber(this.organization_);
            return result == null ? ClientLibraryOrganization.UNRECOGNIZED : result;
        }

        public Builder setOrganization(ClientLibraryOrganization value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 0x80;
            this.organization_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearOrganization() {
            this.bitField0_ &= 0xFFFFFF7F;
            this.organization_ = 0;
            this.onChanged();
            return this;
        }

        private void ensureLibrarySettingsIsMutable() {
            if ((this.bitField0_ & 0x100) == 0) {
                this.librarySettings_ = new ArrayList<ClientLibrarySettings>(this.librarySettings_);
                this.bitField0_ |= 0x100;
            }
        }

        @Override
        public List<ClientLibrarySettings> getLibrarySettingsList() {
            if (this.librarySettingsBuilder_ == null) {
                return Collections.unmodifiableList(this.librarySettings_);
            }
            return this.librarySettingsBuilder_.getMessageList();
        }

        @Override
        public int getLibrarySettingsCount() {
            if (this.librarySettingsBuilder_ == null) {
                return this.librarySettings_.size();
            }
            return this.librarySettingsBuilder_.getCount();
        }

        @Override
        public ClientLibrarySettings getLibrarySettings(int index) {
            if (this.librarySettingsBuilder_ == null) {
                return this.librarySettings_.get(index);
            }
            return this.librarySettingsBuilder_.getMessage(index);
        }

        public Builder setLibrarySettings(int index, ClientLibrarySettings value) {
            if (this.librarySettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.set(index, value);
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLibrarySettings(int index, ClientLibrarySettings.Builder builderForValue) {
            if (this.librarySettingsBuilder_ == null) {
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLibrarySettings(ClientLibrarySettings value) {
            if (this.librarySettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.add(value);
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLibrarySettings(int index, ClientLibrarySettings value) {
            if (this.librarySettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.add(index, value);
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLibrarySettings(ClientLibrarySettings.Builder builderForValue) {
            if (this.librarySettingsBuilder_ == null) {
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLibrarySettings(int index, ClientLibrarySettings.Builder builderForValue) {
            if (this.librarySettingsBuilder_ == null) {
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLibrarySettings(Iterable<? extends ClientLibrarySettings> values2) {
            if (this.librarySettingsBuilder_ == null) {
                this.ensureLibrarySettingsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.librarySettings_);
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLibrarySettings() {
            if (this.librarySettingsBuilder_ == null) {
                this.librarySettings_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFEFF;
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLibrarySettings(int index) {
            if (this.librarySettingsBuilder_ == null) {
                this.ensureLibrarySettingsIsMutable();
                this.librarySettings_.remove(index);
                this.onChanged();
            } else {
                this.librarySettingsBuilder_.remove(index);
            }
            return this;
        }

        public ClientLibrarySettings.Builder getLibrarySettingsBuilder(int index) {
            return this.getLibrarySettingsFieldBuilder().getBuilder(index);
        }

        @Override
        public ClientLibrarySettingsOrBuilder getLibrarySettingsOrBuilder(int index) {
            if (this.librarySettingsBuilder_ == null) {
                return this.librarySettings_.get(index);
            }
            return this.librarySettingsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends ClientLibrarySettingsOrBuilder> getLibrarySettingsOrBuilderList() {
            if (this.librarySettingsBuilder_ != null) {
                return this.librarySettingsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.librarySettings_);
        }

        public ClientLibrarySettings.Builder addLibrarySettingsBuilder() {
            return this.getLibrarySettingsFieldBuilder().addBuilder(ClientLibrarySettings.getDefaultInstance());
        }

        public ClientLibrarySettings.Builder addLibrarySettingsBuilder(int index) {
            return this.getLibrarySettingsFieldBuilder().addBuilder(index, ClientLibrarySettings.getDefaultInstance());
        }

        public List<ClientLibrarySettings.Builder> getLibrarySettingsBuilderList() {
            return this.getLibrarySettingsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<ClientLibrarySettings, ClientLibrarySettings.Builder, ClientLibrarySettingsOrBuilder> getLibrarySettingsFieldBuilder() {
            if (this.librarySettingsBuilder_ == null) {
                this.librarySettingsBuilder_ = new RepeatedFieldBuilderV3(this.librarySettings_, (this.bitField0_ & 0x100) != 0, this.getParentForChildren(), this.isClean());
                this.librarySettings_ = null;
            }
            return this.librarySettingsBuilder_;
        }

        @Override
        public String getProtoReferenceDocumentationUri() {
            Object ref = this.protoReferenceDocumentationUri_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.protoReferenceDocumentationUri_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getProtoReferenceDocumentationUriBytes() {
            Object ref = this.protoReferenceDocumentationUri_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.protoReferenceDocumentationUri_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setProtoReferenceDocumentationUri(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.protoReferenceDocumentationUri_ = value;
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearProtoReferenceDocumentationUri() {
            this.protoReferenceDocumentationUri_ = Publishing.getDefaultInstance().getProtoReferenceDocumentationUri();
            this.bitField0_ &= 0xFFFFFDFF;
            this.onChanged();
            return this;
        }

        public Builder setProtoReferenceDocumentationUriBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Publishing.checkByteStringIsUtf8(value);
            this.protoReferenceDocumentationUri_ = value;
            this.bitField0_ |= 0x200;
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

