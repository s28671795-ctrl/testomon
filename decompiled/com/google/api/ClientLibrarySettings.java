/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.ClientLibrarySettingsOrBuilder;
import com.google.api.ClientProto;
import com.google.api.CppSettings;
import com.google.api.CppSettingsOrBuilder;
import com.google.api.DotnetSettings;
import com.google.api.DotnetSettingsOrBuilder;
import com.google.api.GoSettings;
import com.google.api.GoSettingsOrBuilder;
import com.google.api.JavaSettings;
import com.google.api.JavaSettingsOrBuilder;
import com.google.api.LaunchStage;
import com.google.api.NodeSettings;
import com.google.api.NodeSettingsOrBuilder;
import com.google.api.PhpSettings;
import com.google.api.PhpSettingsOrBuilder;
import com.google.api.PythonSettings;
import com.google.api.PythonSettingsOrBuilder;
import com.google.api.RubySettings;
import com.google.api.RubySettingsOrBuilder;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ClientLibrarySettings
extends GeneratedMessageV3
implements ClientLibrarySettingsOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int VERSION_FIELD_NUMBER = 1;
    private volatile Object version_ = "";
    public static final int LAUNCH_STAGE_FIELD_NUMBER = 2;
    private int launchStage_ = 0;
    public static final int REST_NUMERIC_ENUMS_FIELD_NUMBER = 3;
    private boolean restNumericEnums_ = false;
    public static final int JAVA_SETTINGS_FIELD_NUMBER = 21;
    private JavaSettings javaSettings_;
    public static final int CPP_SETTINGS_FIELD_NUMBER = 22;
    private CppSettings cppSettings_;
    public static final int PHP_SETTINGS_FIELD_NUMBER = 23;
    private PhpSettings phpSettings_;
    public static final int PYTHON_SETTINGS_FIELD_NUMBER = 24;
    private PythonSettings pythonSettings_;
    public static final int NODE_SETTINGS_FIELD_NUMBER = 25;
    private NodeSettings nodeSettings_;
    public static final int DOTNET_SETTINGS_FIELD_NUMBER = 26;
    private DotnetSettings dotnetSettings_;
    public static final int RUBY_SETTINGS_FIELD_NUMBER = 27;
    private RubySettings rubySettings_;
    public static final int GO_SETTINGS_FIELD_NUMBER = 28;
    private GoSettings goSettings_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final ClientLibrarySettings DEFAULT_INSTANCE = new ClientLibrarySettings();
    private static final Parser<ClientLibrarySettings> PARSER = new AbstractParser<ClientLibrarySettings>(){

        @Override
        public ClientLibrarySettings parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = ClientLibrarySettings.newBuilder();
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

    private ClientLibrarySettings(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private ClientLibrarySettings() {
        this.version_ = "";
        this.launchStage_ = 0;
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new ClientLibrarySettings();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ClientProto.internal_static_google_api_ClientLibrarySettings_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ClientProto.internal_static_google_api_ClientLibrarySettings_fieldAccessorTable.ensureFieldAccessorsInitialized(ClientLibrarySettings.class, Builder.class);
    }

    @Override
    public String getVersion() {
        Object ref = this.version_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.version_ = s2;
        return s2;
    }

    @Override
    public ByteString getVersionBytes() {
        Object ref = this.version_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.version_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public int getLaunchStageValue() {
        return this.launchStage_;
    }

    @Override
    public LaunchStage getLaunchStage() {
        LaunchStage result = LaunchStage.forNumber(this.launchStage_);
        return result == null ? LaunchStage.UNRECOGNIZED : result;
    }

    @Override
    public boolean getRestNumericEnums() {
        return this.restNumericEnums_;
    }

    @Override
    public boolean hasJavaSettings() {
        return this.javaSettings_ != null;
    }

    @Override
    public JavaSettings getJavaSettings() {
        return this.javaSettings_ == null ? JavaSettings.getDefaultInstance() : this.javaSettings_;
    }

    @Override
    public JavaSettingsOrBuilder getJavaSettingsOrBuilder() {
        return this.javaSettings_ == null ? JavaSettings.getDefaultInstance() : this.javaSettings_;
    }

    @Override
    public boolean hasCppSettings() {
        return this.cppSettings_ != null;
    }

    @Override
    public CppSettings getCppSettings() {
        return this.cppSettings_ == null ? CppSettings.getDefaultInstance() : this.cppSettings_;
    }

    @Override
    public CppSettingsOrBuilder getCppSettingsOrBuilder() {
        return this.cppSettings_ == null ? CppSettings.getDefaultInstance() : this.cppSettings_;
    }

    @Override
    public boolean hasPhpSettings() {
        return this.phpSettings_ != null;
    }

    @Override
    public PhpSettings getPhpSettings() {
        return this.phpSettings_ == null ? PhpSettings.getDefaultInstance() : this.phpSettings_;
    }

    @Override
    public PhpSettingsOrBuilder getPhpSettingsOrBuilder() {
        return this.phpSettings_ == null ? PhpSettings.getDefaultInstance() : this.phpSettings_;
    }

    @Override
    public boolean hasPythonSettings() {
        return this.pythonSettings_ != null;
    }

    @Override
    public PythonSettings getPythonSettings() {
        return this.pythonSettings_ == null ? PythonSettings.getDefaultInstance() : this.pythonSettings_;
    }

    @Override
    public PythonSettingsOrBuilder getPythonSettingsOrBuilder() {
        return this.pythonSettings_ == null ? PythonSettings.getDefaultInstance() : this.pythonSettings_;
    }

    @Override
    public boolean hasNodeSettings() {
        return this.nodeSettings_ != null;
    }

    @Override
    public NodeSettings getNodeSettings() {
        return this.nodeSettings_ == null ? NodeSettings.getDefaultInstance() : this.nodeSettings_;
    }

    @Override
    public NodeSettingsOrBuilder getNodeSettingsOrBuilder() {
        return this.nodeSettings_ == null ? NodeSettings.getDefaultInstance() : this.nodeSettings_;
    }

    @Override
    public boolean hasDotnetSettings() {
        return this.dotnetSettings_ != null;
    }

    @Override
    public DotnetSettings getDotnetSettings() {
        return this.dotnetSettings_ == null ? DotnetSettings.getDefaultInstance() : this.dotnetSettings_;
    }

    @Override
    public DotnetSettingsOrBuilder getDotnetSettingsOrBuilder() {
        return this.dotnetSettings_ == null ? DotnetSettings.getDefaultInstance() : this.dotnetSettings_;
    }

    @Override
    public boolean hasRubySettings() {
        return this.rubySettings_ != null;
    }

    @Override
    public RubySettings getRubySettings() {
        return this.rubySettings_ == null ? RubySettings.getDefaultInstance() : this.rubySettings_;
    }

    @Override
    public RubySettingsOrBuilder getRubySettingsOrBuilder() {
        return this.rubySettings_ == null ? RubySettings.getDefaultInstance() : this.rubySettings_;
    }

    @Override
    public boolean hasGoSettings() {
        return this.goSettings_ != null;
    }

    @Override
    public GoSettings getGoSettings() {
        return this.goSettings_ == null ? GoSettings.getDefaultInstance() : this.goSettings_;
    }

    @Override
    public GoSettingsOrBuilder getGoSettingsOrBuilder() {
        return this.goSettings_ == null ? GoSettings.getDefaultInstance() : this.goSettings_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
            GeneratedMessageV3.writeString(output, 1, this.version_);
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            output.writeEnum(2, this.launchStage_);
        }
        if (this.restNumericEnums_) {
            output.writeBool(3, this.restNumericEnums_);
        }
        if (this.javaSettings_ != null) {
            output.writeMessage(21, this.getJavaSettings());
        }
        if (this.cppSettings_ != null) {
            output.writeMessage(22, this.getCppSettings());
        }
        if (this.phpSettings_ != null) {
            output.writeMessage(23, this.getPhpSettings());
        }
        if (this.pythonSettings_ != null) {
            output.writeMessage(24, this.getPythonSettings());
        }
        if (this.nodeSettings_ != null) {
            output.writeMessage(25, this.getNodeSettings());
        }
        if (this.dotnetSettings_ != null) {
            output.writeMessage(26, this.getDotnetSettings());
        }
        if (this.rubySettings_ != null) {
            output.writeMessage(27, this.getRubySettings());
        }
        if (this.goSettings_ != null) {
            output.writeMessage(28, this.getGoSettings());
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
        if (!GeneratedMessageV3.isStringEmpty(this.version_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.version_);
        }
        if (this.launchStage_ != LaunchStage.LAUNCH_STAGE_UNSPECIFIED.getNumber()) {
            size += CodedOutputStream.computeEnumSize(2, this.launchStage_);
        }
        if (this.restNumericEnums_) {
            size += CodedOutputStream.computeBoolSize(3, this.restNumericEnums_);
        }
        if (this.javaSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(21, this.getJavaSettings());
        }
        if (this.cppSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(22, this.getCppSettings());
        }
        if (this.phpSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(23, this.getPhpSettings());
        }
        if (this.pythonSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(24, this.getPythonSettings());
        }
        if (this.nodeSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(25, this.getNodeSettings());
        }
        if (this.dotnetSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(26, this.getDotnetSettings());
        }
        if (this.rubySettings_ != null) {
            size += CodedOutputStream.computeMessageSize(27, this.getRubySettings());
        }
        if (this.goSettings_ != null) {
            size += CodedOutputStream.computeMessageSize(28, this.getGoSettings());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ClientLibrarySettings)) {
            return super.equals(obj);
        }
        ClientLibrarySettings other = (ClientLibrarySettings)obj;
        if (!this.getVersion().equals(other.getVersion())) {
            return false;
        }
        if (this.launchStage_ != other.launchStage_) {
            return false;
        }
        if (this.getRestNumericEnums() != other.getRestNumericEnums()) {
            return false;
        }
        if (this.hasJavaSettings() != other.hasJavaSettings()) {
            return false;
        }
        if (this.hasJavaSettings() && !this.getJavaSettings().equals(other.getJavaSettings())) {
            return false;
        }
        if (this.hasCppSettings() != other.hasCppSettings()) {
            return false;
        }
        if (this.hasCppSettings() && !this.getCppSettings().equals(other.getCppSettings())) {
            return false;
        }
        if (this.hasPhpSettings() != other.hasPhpSettings()) {
            return false;
        }
        if (this.hasPhpSettings() && !this.getPhpSettings().equals(other.getPhpSettings())) {
            return false;
        }
        if (this.hasPythonSettings() != other.hasPythonSettings()) {
            return false;
        }
        if (this.hasPythonSettings() && !this.getPythonSettings().equals(other.getPythonSettings())) {
            return false;
        }
        if (this.hasNodeSettings() != other.hasNodeSettings()) {
            return false;
        }
        if (this.hasNodeSettings() && !this.getNodeSettings().equals(other.getNodeSettings())) {
            return false;
        }
        if (this.hasDotnetSettings() != other.hasDotnetSettings()) {
            return false;
        }
        if (this.hasDotnetSettings() && !this.getDotnetSettings().equals(other.getDotnetSettings())) {
            return false;
        }
        if (this.hasRubySettings() != other.hasRubySettings()) {
            return false;
        }
        if (this.hasRubySettings() && !this.getRubySettings().equals(other.getRubySettings())) {
            return false;
        }
        if (this.hasGoSettings() != other.hasGoSettings()) {
            return false;
        }
        if (this.hasGoSettings() && !this.getGoSettings().equals(other.getGoSettings())) {
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
        hash = 19 * hash + ClientLibrarySettings.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getVersion().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.launchStage_;
        hash = 37 * hash + 3;
        hash = 53 * hash + Internal.hashBoolean(this.getRestNumericEnums());
        if (this.hasJavaSettings()) {
            hash = 37 * hash + 21;
            hash = 53 * hash + this.getJavaSettings().hashCode();
        }
        if (this.hasCppSettings()) {
            hash = 37 * hash + 22;
            hash = 53 * hash + this.getCppSettings().hashCode();
        }
        if (this.hasPhpSettings()) {
            hash = 37 * hash + 23;
            hash = 53 * hash + this.getPhpSettings().hashCode();
        }
        if (this.hasPythonSettings()) {
            hash = 37 * hash + 24;
            hash = 53 * hash + this.getPythonSettings().hashCode();
        }
        if (this.hasNodeSettings()) {
            hash = 37 * hash + 25;
            hash = 53 * hash + this.getNodeSettings().hashCode();
        }
        if (this.hasDotnetSettings()) {
            hash = 37 * hash + 26;
            hash = 53 * hash + this.getDotnetSettings().hashCode();
        }
        if (this.hasRubySettings()) {
            hash = 37 * hash + 27;
            hash = 53 * hash + this.getRubySettings().hashCode();
        }
        if (this.hasGoSettings()) {
            hash = 37 * hash + 28;
            hash = 53 * hash + this.getGoSettings().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static ClientLibrarySettings parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ClientLibrarySettings parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ClientLibrarySettings parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ClientLibrarySettings parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ClientLibrarySettings parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ClientLibrarySettings parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ClientLibrarySettings parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ClientLibrarySettings parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ClientLibrarySettings parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ClientLibrarySettings parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ClientLibrarySettings parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ClientLibrarySettings parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return ClientLibrarySettings.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ClientLibrarySettings prototype) {
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

    public static ClientLibrarySettings getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ClientLibrarySettings> parser() {
        return PARSER;
    }

    public Parser<ClientLibrarySettings> getParserForType() {
        return PARSER;
    }

    @Override
    public ClientLibrarySettings getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ClientLibrarySettingsOrBuilder {
        private int bitField0_;
        private Object version_ = "";
        private int launchStage_ = 0;
        private boolean restNumericEnums_;
        private JavaSettings javaSettings_;
        private SingleFieldBuilderV3<JavaSettings, JavaSettings.Builder, JavaSettingsOrBuilder> javaSettingsBuilder_;
        private CppSettings cppSettings_;
        private SingleFieldBuilderV3<CppSettings, CppSettings.Builder, CppSettingsOrBuilder> cppSettingsBuilder_;
        private PhpSettings phpSettings_;
        private SingleFieldBuilderV3<PhpSettings, PhpSettings.Builder, PhpSettingsOrBuilder> phpSettingsBuilder_;
        private PythonSettings pythonSettings_;
        private SingleFieldBuilderV3<PythonSettings, PythonSettings.Builder, PythonSettingsOrBuilder> pythonSettingsBuilder_;
        private NodeSettings nodeSettings_;
        private SingleFieldBuilderV3<NodeSettings, NodeSettings.Builder, NodeSettingsOrBuilder> nodeSettingsBuilder_;
        private DotnetSettings dotnetSettings_;
        private SingleFieldBuilderV3<DotnetSettings, DotnetSettings.Builder, DotnetSettingsOrBuilder> dotnetSettingsBuilder_;
        private RubySettings rubySettings_;
        private SingleFieldBuilderV3<RubySettings, RubySettings.Builder, RubySettingsOrBuilder> rubySettingsBuilder_;
        private GoSettings goSettings_;
        private SingleFieldBuilderV3<GoSettings, GoSettings.Builder, GoSettingsOrBuilder> goSettingsBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ClientProto.internal_static_google_api_ClientLibrarySettings_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ClientProto.internal_static_google_api_ClientLibrarySettings_fieldAccessorTable.ensureFieldAccessorsInitialized(ClientLibrarySettings.class, Builder.class);
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
            this.version_ = "";
            this.launchStage_ = 0;
            this.restNumericEnums_ = false;
            this.javaSettings_ = null;
            if (this.javaSettingsBuilder_ != null) {
                this.javaSettingsBuilder_.dispose();
                this.javaSettingsBuilder_ = null;
            }
            this.cppSettings_ = null;
            if (this.cppSettingsBuilder_ != null) {
                this.cppSettingsBuilder_.dispose();
                this.cppSettingsBuilder_ = null;
            }
            this.phpSettings_ = null;
            if (this.phpSettingsBuilder_ != null) {
                this.phpSettingsBuilder_.dispose();
                this.phpSettingsBuilder_ = null;
            }
            this.pythonSettings_ = null;
            if (this.pythonSettingsBuilder_ != null) {
                this.pythonSettingsBuilder_.dispose();
                this.pythonSettingsBuilder_ = null;
            }
            this.nodeSettings_ = null;
            if (this.nodeSettingsBuilder_ != null) {
                this.nodeSettingsBuilder_.dispose();
                this.nodeSettingsBuilder_ = null;
            }
            this.dotnetSettings_ = null;
            if (this.dotnetSettingsBuilder_ != null) {
                this.dotnetSettingsBuilder_.dispose();
                this.dotnetSettingsBuilder_ = null;
            }
            this.rubySettings_ = null;
            if (this.rubySettingsBuilder_ != null) {
                this.rubySettingsBuilder_.dispose();
                this.rubySettingsBuilder_ = null;
            }
            this.goSettings_ = null;
            if (this.goSettingsBuilder_ != null) {
                this.goSettingsBuilder_.dispose();
                this.goSettingsBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ClientProto.internal_static_google_api_ClientLibrarySettings_descriptor;
        }

        @Override
        public ClientLibrarySettings getDefaultInstanceForType() {
            return ClientLibrarySettings.getDefaultInstance();
        }

        @Override
        public ClientLibrarySettings build() {
            ClientLibrarySettings result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ClientLibrarySettings buildPartial() {
            ClientLibrarySettings result = new ClientLibrarySettings(this);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartial0(ClientLibrarySettings result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.version_ = this.version_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.launchStage_ = this.launchStage_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.restNumericEnums_ = this.restNumericEnums_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.javaSettings_ = this.javaSettingsBuilder_ == null ? this.javaSettings_ : this.javaSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x10) != 0) {
                result.cppSettings_ = this.cppSettingsBuilder_ == null ? this.cppSettings_ : this.cppSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x20) != 0) {
                result.phpSettings_ = this.phpSettingsBuilder_ == null ? this.phpSettings_ : this.phpSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x40) != 0) {
                result.pythonSettings_ = this.pythonSettingsBuilder_ == null ? this.pythonSettings_ : this.pythonSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.nodeSettings_ = this.nodeSettingsBuilder_ == null ? this.nodeSettings_ : this.nodeSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.dotnetSettings_ = this.dotnetSettingsBuilder_ == null ? this.dotnetSettings_ : this.dotnetSettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.rubySettings_ = this.rubySettingsBuilder_ == null ? this.rubySettings_ : this.rubySettingsBuilder_.build();
            }
            if ((from_bitField0_ & 0x400) != 0) {
                result.goSettings_ = this.goSettingsBuilder_ == null ? this.goSettings_ : this.goSettingsBuilder_.build();
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
            if (other instanceof ClientLibrarySettings) {
                return this.mergeFrom((ClientLibrarySettings)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ClientLibrarySettings other) {
            if (other == ClientLibrarySettings.getDefaultInstance()) {
                return this;
            }
            if (!other.getVersion().isEmpty()) {
                this.version_ = other.version_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (other.launchStage_ != 0) {
                this.setLaunchStageValue(other.getLaunchStageValue());
            }
            if (other.getRestNumericEnums()) {
                this.setRestNumericEnums(other.getRestNumericEnums());
            }
            if (other.hasJavaSettings()) {
                this.mergeJavaSettings(other.getJavaSettings());
            }
            if (other.hasCppSettings()) {
                this.mergeCppSettings(other.getCppSettings());
            }
            if (other.hasPhpSettings()) {
                this.mergePhpSettings(other.getPhpSettings());
            }
            if (other.hasPythonSettings()) {
                this.mergePythonSettings(other.getPythonSettings());
            }
            if (other.hasNodeSettings()) {
                this.mergeNodeSettings(other.getNodeSettings());
            }
            if (other.hasDotnetSettings()) {
                this.mergeDotnetSettings(other.getDotnetSettings());
            }
            if (other.hasRubySettings()) {
                this.mergeRubySettings(other.getRubySettings());
            }
            if (other.hasGoSettings()) {
                this.mergeGoSettings(other.getGoSettings());
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
                        case 10: {
                            this.version_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block19;
                        }
                        case 16: {
                            this.launchStage_ = input.readEnum();
                            this.bitField0_ |= 2;
                            continue block19;
                        }
                        case 24: {
                            this.restNumericEnums_ = input.readBool();
                            this.bitField0_ |= 4;
                            continue block19;
                        }
                        case 170: {
                            input.readMessage(this.getJavaSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 8;
                            continue block19;
                        }
                        case 178: {
                            input.readMessage(this.getCppSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x10;
                            continue block19;
                        }
                        case 186: {
                            input.readMessage(this.getPhpSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x20;
                            continue block19;
                        }
                        case 194: {
                            input.readMessage(this.getPythonSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x40;
                            continue block19;
                        }
                        case 202: {
                            input.readMessage(this.getNodeSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x80;
                            continue block19;
                        }
                        case 210: {
                            input.readMessage(this.getDotnetSettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x100;
                            continue block19;
                        }
                        case 218: {
                            input.readMessage(this.getRubySettingsFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x200;
                            continue block19;
                        }
                        case 226: {
                            input.readMessage(this.getGoSettingsFieldBuilder().getBuilder(), extensionRegistry);
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
        public String getVersion() {
            Object ref = this.version_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.version_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.version_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setVersion(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.version_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearVersion() {
            this.version_ = ClientLibrarySettings.getDefaultInstance().getVersion();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setVersionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            ClientLibrarySettings.checkByteStringIsUtf8(value);
            this.version_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public int getLaunchStageValue() {
            return this.launchStage_;
        }

        public Builder setLaunchStageValue(int value) {
            this.launchStage_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public LaunchStage getLaunchStage() {
            LaunchStage result = LaunchStage.forNumber(this.launchStage_);
            return result == null ? LaunchStage.UNRECOGNIZED : result;
        }

        public Builder setLaunchStage(LaunchStage value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.bitField0_ |= 2;
            this.launchStage_ = value.getNumber();
            this.onChanged();
            return this;
        }

        public Builder clearLaunchStage() {
            this.bitField0_ &= 0xFFFFFFFD;
            this.launchStage_ = 0;
            this.onChanged();
            return this;
        }

        @Override
        public boolean getRestNumericEnums() {
            return this.restNumericEnums_;
        }

        public Builder setRestNumericEnums(boolean value) {
            this.restNumericEnums_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearRestNumericEnums() {
            this.bitField0_ &= 0xFFFFFFFB;
            this.restNumericEnums_ = false;
            this.onChanged();
            return this;
        }

        @Override
        public boolean hasJavaSettings() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override
        public JavaSettings getJavaSettings() {
            if (this.javaSettingsBuilder_ == null) {
                return this.javaSettings_ == null ? JavaSettings.getDefaultInstance() : this.javaSettings_;
            }
            return this.javaSettingsBuilder_.getMessage();
        }

        public Builder setJavaSettings(JavaSettings value) {
            if (this.javaSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.javaSettings_ = value;
            } else {
                this.javaSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder setJavaSettings(JavaSettings.Builder builderForValue) {
            if (this.javaSettingsBuilder_ == null) {
                this.javaSettings_ = builderForValue.build();
            } else {
                this.javaSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder mergeJavaSettings(JavaSettings value) {
            if (this.javaSettingsBuilder_ == null) {
                if ((this.bitField0_ & 8) != 0 && this.javaSettings_ != null && this.javaSettings_ != JavaSettings.getDefaultInstance()) {
                    this.getJavaSettingsBuilder().mergeFrom(value);
                } else {
                    this.javaSettings_ = value;
                }
            } else {
                this.javaSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearJavaSettings() {
            this.bitField0_ &= 0xFFFFFFF7;
            this.javaSettings_ = null;
            if (this.javaSettingsBuilder_ != null) {
                this.javaSettingsBuilder_.dispose();
                this.javaSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public JavaSettings.Builder getJavaSettingsBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return this.getJavaSettingsFieldBuilder().getBuilder();
        }

        @Override
        public JavaSettingsOrBuilder getJavaSettingsOrBuilder() {
            if (this.javaSettingsBuilder_ != null) {
                return this.javaSettingsBuilder_.getMessageOrBuilder();
            }
            return this.javaSettings_ == null ? JavaSettings.getDefaultInstance() : this.javaSettings_;
        }

        private SingleFieldBuilderV3<JavaSettings, JavaSettings.Builder, JavaSettingsOrBuilder> getJavaSettingsFieldBuilder() {
            if (this.javaSettingsBuilder_ == null) {
                this.javaSettingsBuilder_ = new SingleFieldBuilderV3(this.getJavaSettings(), this.getParentForChildren(), this.isClean());
                this.javaSettings_ = null;
            }
            return this.javaSettingsBuilder_;
        }

        @Override
        public boolean hasCppSettings() {
            return (this.bitField0_ & 0x10) != 0;
        }

        @Override
        public CppSettings getCppSettings() {
            if (this.cppSettingsBuilder_ == null) {
                return this.cppSettings_ == null ? CppSettings.getDefaultInstance() : this.cppSettings_;
            }
            return this.cppSettingsBuilder_.getMessage();
        }

        public Builder setCppSettings(CppSettings value) {
            if (this.cppSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.cppSettings_ = value;
            } else {
                this.cppSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder setCppSettings(CppSettings.Builder builderForValue) {
            if (this.cppSettingsBuilder_ == null) {
                this.cppSettings_ = builderForValue.build();
            } else {
                this.cppSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder mergeCppSettings(CppSettings value) {
            if (this.cppSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0 && this.cppSettings_ != null && this.cppSettings_ != CppSettings.getDefaultInstance()) {
                    this.getCppSettingsBuilder().mergeFrom(value);
                } else {
                    this.cppSettings_ = value;
                }
            } else {
                this.cppSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this;
        }

        public Builder clearCppSettings() {
            this.bitField0_ &= 0xFFFFFFEF;
            this.cppSettings_ = null;
            if (this.cppSettingsBuilder_ != null) {
                this.cppSettingsBuilder_.dispose();
                this.cppSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public CppSettings.Builder getCppSettingsBuilder() {
            this.bitField0_ |= 0x10;
            this.onChanged();
            return this.getCppSettingsFieldBuilder().getBuilder();
        }

        @Override
        public CppSettingsOrBuilder getCppSettingsOrBuilder() {
            if (this.cppSettingsBuilder_ != null) {
                return this.cppSettingsBuilder_.getMessageOrBuilder();
            }
            return this.cppSettings_ == null ? CppSettings.getDefaultInstance() : this.cppSettings_;
        }

        private SingleFieldBuilderV3<CppSettings, CppSettings.Builder, CppSettingsOrBuilder> getCppSettingsFieldBuilder() {
            if (this.cppSettingsBuilder_ == null) {
                this.cppSettingsBuilder_ = new SingleFieldBuilderV3(this.getCppSettings(), this.getParentForChildren(), this.isClean());
                this.cppSettings_ = null;
            }
            return this.cppSettingsBuilder_;
        }

        @Override
        public boolean hasPhpSettings() {
            return (this.bitField0_ & 0x20) != 0;
        }

        @Override
        public PhpSettings getPhpSettings() {
            if (this.phpSettingsBuilder_ == null) {
                return this.phpSettings_ == null ? PhpSettings.getDefaultInstance() : this.phpSettings_;
            }
            return this.phpSettingsBuilder_.getMessage();
        }

        public Builder setPhpSettings(PhpSettings value) {
            if (this.phpSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.phpSettings_ = value;
            } else {
                this.phpSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder setPhpSettings(PhpSettings.Builder builderForValue) {
            if (this.phpSettingsBuilder_ == null) {
                this.phpSettings_ = builderForValue.build();
            } else {
                this.phpSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder mergePhpSettings(PhpSettings value) {
            if (this.phpSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x20) != 0 && this.phpSettings_ != null && this.phpSettings_ != PhpSettings.getDefaultInstance()) {
                    this.getPhpSettingsBuilder().mergeFrom(value);
                } else {
                    this.phpSettings_ = value;
                }
            } else {
                this.phpSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this;
        }

        public Builder clearPhpSettings() {
            this.bitField0_ &= 0xFFFFFFDF;
            this.phpSettings_ = null;
            if (this.phpSettingsBuilder_ != null) {
                this.phpSettingsBuilder_.dispose();
                this.phpSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public PhpSettings.Builder getPhpSettingsBuilder() {
            this.bitField0_ |= 0x20;
            this.onChanged();
            return this.getPhpSettingsFieldBuilder().getBuilder();
        }

        @Override
        public PhpSettingsOrBuilder getPhpSettingsOrBuilder() {
            if (this.phpSettingsBuilder_ != null) {
                return this.phpSettingsBuilder_.getMessageOrBuilder();
            }
            return this.phpSettings_ == null ? PhpSettings.getDefaultInstance() : this.phpSettings_;
        }

        private SingleFieldBuilderV3<PhpSettings, PhpSettings.Builder, PhpSettingsOrBuilder> getPhpSettingsFieldBuilder() {
            if (this.phpSettingsBuilder_ == null) {
                this.phpSettingsBuilder_ = new SingleFieldBuilderV3(this.getPhpSettings(), this.getParentForChildren(), this.isClean());
                this.phpSettings_ = null;
            }
            return this.phpSettingsBuilder_;
        }

        @Override
        public boolean hasPythonSettings() {
            return (this.bitField0_ & 0x40) != 0;
        }

        @Override
        public PythonSettings getPythonSettings() {
            if (this.pythonSettingsBuilder_ == null) {
                return this.pythonSettings_ == null ? PythonSettings.getDefaultInstance() : this.pythonSettings_;
            }
            return this.pythonSettingsBuilder_.getMessage();
        }

        public Builder setPythonSettings(PythonSettings value) {
            if (this.pythonSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.pythonSettings_ = value;
            } else {
                this.pythonSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder setPythonSettings(PythonSettings.Builder builderForValue) {
            if (this.pythonSettingsBuilder_ == null) {
                this.pythonSettings_ = builderForValue.build();
            } else {
                this.pythonSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder mergePythonSettings(PythonSettings value) {
            if (this.pythonSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x40) != 0 && this.pythonSettings_ != null && this.pythonSettings_ != PythonSettings.getDefaultInstance()) {
                    this.getPythonSettingsBuilder().mergeFrom(value);
                } else {
                    this.pythonSettings_ = value;
                }
            } else {
                this.pythonSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this;
        }

        public Builder clearPythonSettings() {
            this.bitField0_ &= 0xFFFFFFBF;
            this.pythonSettings_ = null;
            if (this.pythonSettingsBuilder_ != null) {
                this.pythonSettingsBuilder_.dispose();
                this.pythonSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public PythonSettings.Builder getPythonSettingsBuilder() {
            this.bitField0_ |= 0x40;
            this.onChanged();
            return this.getPythonSettingsFieldBuilder().getBuilder();
        }

        @Override
        public PythonSettingsOrBuilder getPythonSettingsOrBuilder() {
            if (this.pythonSettingsBuilder_ != null) {
                return this.pythonSettingsBuilder_.getMessageOrBuilder();
            }
            return this.pythonSettings_ == null ? PythonSettings.getDefaultInstance() : this.pythonSettings_;
        }

        private SingleFieldBuilderV3<PythonSettings, PythonSettings.Builder, PythonSettingsOrBuilder> getPythonSettingsFieldBuilder() {
            if (this.pythonSettingsBuilder_ == null) {
                this.pythonSettingsBuilder_ = new SingleFieldBuilderV3(this.getPythonSettings(), this.getParentForChildren(), this.isClean());
                this.pythonSettings_ = null;
            }
            return this.pythonSettingsBuilder_;
        }

        @Override
        public boolean hasNodeSettings() {
            return (this.bitField0_ & 0x80) != 0;
        }

        @Override
        public NodeSettings getNodeSettings() {
            if (this.nodeSettingsBuilder_ == null) {
                return this.nodeSettings_ == null ? NodeSettings.getDefaultInstance() : this.nodeSettings_;
            }
            return this.nodeSettingsBuilder_.getMessage();
        }

        public Builder setNodeSettings(NodeSettings value) {
            if (this.nodeSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.nodeSettings_ = value;
            } else {
                this.nodeSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder setNodeSettings(NodeSettings.Builder builderForValue) {
            if (this.nodeSettingsBuilder_ == null) {
                this.nodeSettings_ = builderForValue.build();
            } else {
                this.nodeSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder mergeNodeSettings(NodeSettings value) {
            if (this.nodeSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x80) != 0 && this.nodeSettings_ != null && this.nodeSettings_ != NodeSettings.getDefaultInstance()) {
                    this.getNodeSettingsBuilder().mergeFrom(value);
                } else {
                    this.nodeSettings_ = value;
                }
            } else {
                this.nodeSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearNodeSettings() {
            this.bitField0_ &= 0xFFFFFF7F;
            this.nodeSettings_ = null;
            if (this.nodeSettingsBuilder_ != null) {
                this.nodeSettingsBuilder_.dispose();
                this.nodeSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public NodeSettings.Builder getNodeSettingsBuilder() {
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this.getNodeSettingsFieldBuilder().getBuilder();
        }

        @Override
        public NodeSettingsOrBuilder getNodeSettingsOrBuilder() {
            if (this.nodeSettingsBuilder_ != null) {
                return this.nodeSettingsBuilder_.getMessageOrBuilder();
            }
            return this.nodeSettings_ == null ? NodeSettings.getDefaultInstance() : this.nodeSettings_;
        }

        private SingleFieldBuilderV3<NodeSettings, NodeSettings.Builder, NodeSettingsOrBuilder> getNodeSettingsFieldBuilder() {
            if (this.nodeSettingsBuilder_ == null) {
                this.nodeSettingsBuilder_ = new SingleFieldBuilderV3(this.getNodeSettings(), this.getParentForChildren(), this.isClean());
                this.nodeSettings_ = null;
            }
            return this.nodeSettingsBuilder_;
        }

        @Override
        public boolean hasDotnetSettings() {
            return (this.bitField0_ & 0x100) != 0;
        }

        @Override
        public DotnetSettings getDotnetSettings() {
            if (this.dotnetSettingsBuilder_ == null) {
                return this.dotnetSettings_ == null ? DotnetSettings.getDefaultInstance() : this.dotnetSettings_;
            }
            return this.dotnetSettingsBuilder_.getMessage();
        }

        public Builder setDotnetSettings(DotnetSettings value) {
            if (this.dotnetSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.dotnetSettings_ = value;
            } else {
                this.dotnetSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder setDotnetSettings(DotnetSettings.Builder builderForValue) {
            if (this.dotnetSettingsBuilder_ == null) {
                this.dotnetSettings_ = builderForValue.build();
            } else {
                this.dotnetSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder mergeDotnetSettings(DotnetSettings value) {
            if (this.dotnetSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x100) != 0 && this.dotnetSettings_ != null && this.dotnetSettings_ != DotnetSettings.getDefaultInstance()) {
                    this.getDotnetSettingsBuilder().mergeFrom(value);
                } else {
                    this.dotnetSettings_ = value;
                }
            } else {
                this.dotnetSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearDotnetSettings() {
            this.bitField0_ &= 0xFFFFFEFF;
            this.dotnetSettings_ = null;
            if (this.dotnetSettingsBuilder_ != null) {
                this.dotnetSettingsBuilder_.dispose();
                this.dotnetSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public DotnetSettings.Builder getDotnetSettingsBuilder() {
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this.getDotnetSettingsFieldBuilder().getBuilder();
        }

        @Override
        public DotnetSettingsOrBuilder getDotnetSettingsOrBuilder() {
            if (this.dotnetSettingsBuilder_ != null) {
                return this.dotnetSettingsBuilder_.getMessageOrBuilder();
            }
            return this.dotnetSettings_ == null ? DotnetSettings.getDefaultInstance() : this.dotnetSettings_;
        }

        private SingleFieldBuilderV3<DotnetSettings, DotnetSettings.Builder, DotnetSettingsOrBuilder> getDotnetSettingsFieldBuilder() {
            if (this.dotnetSettingsBuilder_ == null) {
                this.dotnetSettingsBuilder_ = new SingleFieldBuilderV3(this.getDotnetSettings(), this.getParentForChildren(), this.isClean());
                this.dotnetSettings_ = null;
            }
            return this.dotnetSettingsBuilder_;
        }

        @Override
        public boolean hasRubySettings() {
            return (this.bitField0_ & 0x200) != 0;
        }

        @Override
        public RubySettings getRubySettings() {
            if (this.rubySettingsBuilder_ == null) {
                return this.rubySettings_ == null ? RubySettings.getDefaultInstance() : this.rubySettings_;
            }
            return this.rubySettingsBuilder_.getMessage();
        }

        public Builder setRubySettings(RubySettings value) {
            if (this.rubySettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.rubySettings_ = value;
            } else {
                this.rubySettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder setRubySettings(RubySettings.Builder builderForValue) {
            if (this.rubySettingsBuilder_ == null) {
                this.rubySettings_ = builderForValue.build();
            } else {
                this.rubySettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder mergeRubySettings(RubySettings value) {
            if (this.rubySettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x200) != 0 && this.rubySettings_ != null && this.rubySettings_ != RubySettings.getDefaultInstance()) {
                    this.getRubySettingsBuilder().mergeFrom(value);
                } else {
                    this.rubySettings_ = value;
                }
            } else {
                this.rubySettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearRubySettings() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.rubySettings_ = null;
            if (this.rubySettingsBuilder_ != null) {
                this.rubySettingsBuilder_.dispose();
                this.rubySettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public RubySettings.Builder getRubySettingsBuilder() {
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this.getRubySettingsFieldBuilder().getBuilder();
        }

        @Override
        public RubySettingsOrBuilder getRubySettingsOrBuilder() {
            if (this.rubySettingsBuilder_ != null) {
                return this.rubySettingsBuilder_.getMessageOrBuilder();
            }
            return this.rubySettings_ == null ? RubySettings.getDefaultInstance() : this.rubySettings_;
        }

        private SingleFieldBuilderV3<RubySettings, RubySettings.Builder, RubySettingsOrBuilder> getRubySettingsFieldBuilder() {
            if (this.rubySettingsBuilder_ == null) {
                this.rubySettingsBuilder_ = new SingleFieldBuilderV3(this.getRubySettings(), this.getParentForChildren(), this.isClean());
                this.rubySettings_ = null;
            }
            return this.rubySettingsBuilder_;
        }

        @Override
        public boolean hasGoSettings() {
            return (this.bitField0_ & 0x400) != 0;
        }

        @Override
        public GoSettings getGoSettings() {
            if (this.goSettingsBuilder_ == null) {
                return this.goSettings_ == null ? GoSettings.getDefaultInstance() : this.goSettings_;
            }
            return this.goSettingsBuilder_.getMessage();
        }

        public Builder setGoSettings(GoSettings value) {
            if (this.goSettingsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.goSettings_ = value;
            } else {
                this.goSettingsBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder setGoSettings(GoSettings.Builder builderForValue) {
            if (this.goSettingsBuilder_ == null) {
                this.goSettings_ = builderForValue.build();
            } else {
                this.goSettingsBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder mergeGoSettings(GoSettings value) {
            if (this.goSettingsBuilder_ == null) {
                if ((this.bitField0_ & 0x400) != 0 && this.goSettings_ != null && this.goSettings_ != GoSettings.getDefaultInstance()) {
                    this.getGoSettingsBuilder().mergeFrom(value);
                } else {
                    this.goSettings_ = value;
                }
            } else {
                this.goSettingsBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearGoSettings() {
            this.bitField0_ &= 0xFFFFFBFF;
            this.goSettings_ = null;
            if (this.goSettingsBuilder_ != null) {
                this.goSettingsBuilder_.dispose();
                this.goSettingsBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public GoSettings.Builder getGoSettingsBuilder() {
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this.getGoSettingsFieldBuilder().getBuilder();
        }

        @Override
        public GoSettingsOrBuilder getGoSettingsOrBuilder() {
            if (this.goSettingsBuilder_ != null) {
                return this.goSettingsBuilder_.getMessageOrBuilder();
            }
            return this.goSettings_ == null ? GoSettings.getDefaultInstance() : this.goSettings_;
        }

        private SingleFieldBuilderV3<GoSettings, GoSettings.Builder, GoSettingsOrBuilder> getGoSettingsFieldBuilder() {
            if (this.goSettingsBuilder_ == null) {
                this.goSettingsBuilder_ = new SingleFieldBuilderV3(this.getGoSettings(), this.getParentForChildren(), this.isClean());
                this.goSettings_ = null;
            }
            return this.goSettingsBuilder_;
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

