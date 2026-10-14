/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Authentication;
import com.google.api.AuthenticationOrBuilder;
import com.google.api.Backend;
import com.google.api.BackendOrBuilder;
import com.google.api.Billing;
import com.google.api.BillingOrBuilder;
import com.google.api.Context;
import com.google.api.ContextOrBuilder;
import com.google.api.Control;
import com.google.api.ControlOrBuilder;
import com.google.api.Documentation;
import com.google.api.DocumentationOrBuilder;
import com.google.api.Endpoint;
import com.google.api.EndpointOrBuilder;
import com.google.api.Http;
import com.google.api.HttpOrBuilder;
import com.google.api.LogDescriptor;
import com.google.api.LogDescriptorOrBuilder;
import com.google.api.Logging;
import com.google.api.LoggingOrBuilder;
import com.google.api.MetricDescriptor;
import com.google.api.MetricDescriptorOrBuilder;
import com.google.api.MonitoredResourceDescriptor;
import com.google.api.MonitoredResourceDescriptorOrBuilder;
import com.google.api.Monitoring;
import com.google.api.MonitoringOrBuilder;
import com.google.api.Publishing;
import com.google.api.PublishingOrBuilder;
import com.google.api.Quota;
import com.google.api.QuotaOrBuilder;
import com.google.api.ServiceOrBuilder;
import com.google.api.ServiceProto;
import com.google.api.SourceInfo;
import com.google.api.SourceInfoOrBuilder;
import com.google.api.SystemParameters;
import com.google.api.SystemParametersOrBuilder;
import com.google.api.Usage;
import com.google.api.UsageOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Api;
import com.google.protobuf.ApiOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Enum;
import com.google.protobuf.EnumOrBuilder;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.Type;
import com.google.protobuf.TypeOrBuilder;
import com.google.protobuf.UInt32Value;
import com.google.protobuf.UInt32ValueOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Service
extends GeneratedMessageV3
implements ServiceOrBuilder {
    private static final long serialVersionUID = 0L;
    public static final int NAME_FIELD_NUMBER = 1;
    private volatile Object name_ = "";
    public static final int TITLE_FIELD_NUMBER = 2;
    private volatile Object title_ = "";
    public static final int PRODUCER_PROJECT_ID_FIELD_NUMBER = 22;
    private volatile Object producerProjectId_ = "";
    public static final int ID_FIELD_NUMBER = 33;
    private volatile Object id_ = "";
    public static final int APIS_FIELD_NUMBER = 3;
    private List<Api> apis_;
    public static final int TYPES_FIELD_NUMBER = 4;
    private List<Type> types_;
    public static final int ENUMS_FIELD_NUMBER = 5;
    private List<Enum> enums_;
    public static final int DOCUMENTATION_FIELD_NUMBER = 6;
    private Documentation documentation_;
    public static final int BACKEND_FIELD_NUMBER = 8;
    private Backend backend_;
    public static final int HTTP_FIELD_NUMBER = 9;
    private Http http_;
    public static final int QUOTA_FIELD_NUMBER = 10;
    private Quota quota_;
    public static final int AUTHENTICATION_FIELD_NUMBER = 11;
    private Authentication authentication_;
    public static final int CONTEXT_FIELD_NUMBER = 12;
    private Context context_;
    public static final int USAGE_FIELD_NUMBER = 15;
    private Usage usage_;
    public static final int ENDPOINTS_FIELD_NUMBER = 18;
    private List<Endpoint> endpoints_;
    public static final int CONTROL_FIELD_NUMBER = 21;
    private Control control_;
    public static final int LOGS_FIELD_NUMBER = 23;
    private List<LogDescriptor> logs_;
    public static final int METRICS_FIELD_NUMBER = 24;
    private List<MetricDescriptor> metrics_;
    public static final int MONITORED_RESOURCES_FIELD_NUMBER = 25;
    private List<MonitoredResourceDescriptor> monitoredResources_;
    public static final int BILLING_FIELD_NUMBER = 26;
    private Billing billing_;
    public static final int LOGGING_FIELD_NUMBER = 27;
    private Logging logging_;
    public static final int MONITORING_FIELD_NUMBER = 28;
    private Monitoring monitoring_;
    public static final int SYSTEM_PARAMETERS_FIELD_NUMBER = 29;
    private SystemParameters systemParameters_;
    public static final int SOURCE_INFO_FIELD_NUMBER = 37;
    private SourceInfo sourceInfo_;
    public static final int PUBLISHING_FIELD_NUMBER = 45;
    private Publishing publishing_;
    public static final int CONFIG_VERSION_FIELD_NUMBER = 20;
    private UInt32Value configVersion_;
    private byte memoizedIsInitialized = (byte)-1;
    private static final Service DEFAULT_INSTANCE = new Service();
    private static final Parser<Service> PARSER = new AbstractParser<Service>(){

        @Override
        public Service parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            Builder builder = Service.newBuilder();
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

    private Service(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Service() {
        this.name_ = "";
        this.title_ = "";
        this.producerProjectId_ = "";
        this.id_ = "";
        this.apis_ = Collections.emptyList();
        this.types_ = Collections.emptyList();
        this.enums_ = Collections.emptyList();
        this.endpoints_ = Collections.emptyList();
        this.logs_ = Collections.emptyList();
        this.metrics_ = Collections.emptyList();
        this.monitoredResources_ = Collections.emptyList();
    }

    @Override
    protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused2) {
        return new Service();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return ServiceProto.internal_static_google_api_Service_descriptor;
    }

    @Override
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return ServiceProto.internal_static_google_api_Service_fieldAccessorTable.ensureFieldAccessorsInitialized(Service.class, Builder.class);
    }

    @Override
    public String getName() {
        Object ref = this.name_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.name_ = s2;
        return s2;
    }

    @Override
    public ByteString getNameBytes() {
        Object ref = this.name_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getTitle() {
        Object ref = this.title_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.title_ = s2;
        return s2;
    }

    @Override
    public ByteString getTitleBytes() {
        Object ref = this.title_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.title_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getProducerProjectId() {
        Object ref = this.producerProjectId_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.producerProjectId_ = s2;
        return s2;
    }

    @Override
    public ByteString getProducerProjectIdBytes() {
        Object ref = this.producerProjectId_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.producerProjectId_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public String getId() {
        Object ref = this.id_;
        if (ref instanceof String) {
            return (String)ref;
        }
        ByteString bs = (ByteString)ref;
        String s2 = bs.toStringUtf8();
        this.id_ = s2;
        return s2;
    }

    @Override
    public ByteString getIdBytes() {
        Object ref = this.id_;
        if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.id_ = b;
            return b;
        }
        return (ByteString)ref;
    }

    @Override
    public List<Api> getApisList() {
        return this.apis_;
    }

    @Override
    public List<? extends ApiOrBuilder> getApisOrBuilderList() {
        return this.apis_;
    }

    @Override
    public int getApisCount() {
        return this.apis_.size();
    }

    @Override
    public Api getApis(int index) {
        return this.apis_.get(index);
    }

    @Override
    public ApiOrBuilder getApisOrBuilder(int index) {
        return this.apis_.get(index);
    }

    @Override
    public List<Type> getTypesList() {
        return this.types_;
    }

    @Override
    public List<? extends TypeOrBuilder> getTypesOrBuilderList() {
        return this.types_;
    }

    @Override
    public int getTypesCount() {
        return this.types_.size();
    }

    @Override
    public Type getTypes(int index) {
        return this.types_.get(index);
    }

    @Override
    public TypeOrBuilder getTypesOrBuilder(int index) {
        return this.types_.get(index);
    }

    @Override
    public List<Enum> getEnumsList() {
        return this.enums_;
    }

    @Override
    public List<? extends EnumOrBuilder> getEnumsOrBuilderList() {
        return this.enums_;
    }

    @Override
    public int getEnumsCount() {
        return this.enums_.size();
    }

    @Override
    public Enum getEnums(int index) {
        return this.enums_.get(index);
    }

    @Override
    public EnumOrBuilder getEnumsOrBuilder(int index) {
        return this.enums_.get(index);
    }

    @Override
    public boolean hasDocumentation() {
        return this.documentation_ != null;
    }

    @Override
    public Documentation getDocumentation() {
        return this.documentation_ == null ? Documentation.getDefaultInstance() : this.documentation_;
    }

    @Override
    public DocumentationOrBuilder getDocumentationOrBuilder() {
        return this.documentation_ == null ? Documentation.getDefaultInstance() : this.documentation_;
    }

    @Override
    public boolean hasBackend() {
        return this.backend_ != null;
    }

    @Override
    public Backend getBackend() {
        return this.backend_ == null ? Backend.getDefaultInstance() : this.backend_;
    }

    @Override
    public BackendOrBuilder getBackendOrBuilder() {
        return this.backend_ == null ? Backend.getDefaultInstance() : this.backend_;
    }

    @Override
    public boolean hasHttp() {
        return this.http_ != null;
    }

    @Override
    public Http getHttp() {
        return this.http_ == null ? Http.getDefaultInstance() : this.http_;
    }

    @Override
    public HttpOrBuilder getHttpOrBuilder() {
        return this.http_ == null ? Http.getDefaultInstance() : this.http_;
    }

    @Override
    public boolean hasQuota() {
        return this.quota_ != null;
    }

    @Override
    public Quota getQuota() {
        return this.quota_ == null ? Quota.getDefaultInstance() : this.quota_;
    }

    @Override
    public QuotaOrBuilder getQuotaOrBuilder() {
        return this.quota_ == null ? Quota.getDefaultInstance() : this.quota_;
    }

    @Override
    public boolean hasAuthentication() {
        return this.authentication_ != null;
    }

    @Override
    public Authentication getAuthentication() {
        return this.authentication_ == null ? Authentication.getDefaultInstance() : this.authentication_;
    }

    @Override
    public AuthenticationOrBuilder getAuthenticationOrBuilder() {
        return this.authentication_ == null ? Authentication.getDefaultInstance() : this.authentication_;
    }

    @Override
    public boolean hasContext() {
        return this.context_ != null;
    }

    @Override
    public Context getContext() {
        return this.context_ == null ? Context.getDefaultInstance() : this.context_;
    }

    @Override
    public ContextOrBuilder getContextOrBuilder() {
        return this.context_ == null ? Context.getDefaultInstance() : this.context_;
    }

    @Override
    public boolean hasUsage() {
        return this.usage_ != null;
    }

    @Override
    public Usage getUsage() {
        return this.usage_ == null ? Usage.getDefaultInstance() : this.usage_;
    }

    @Override
    public UsageOrBuilder getUsageOrBuilder() {
        return this.usage_ == null ? Usage.getDefaultInstance() : this.usage_;
    }

    @Override
    public List<Endpoint> getEndpointsList() {
        return this.endpoints_;
    }

    @Override
    public List<? extends EndpointOrBuilder> getEndpointsOrBuilderList() {
        return this.endpoints_;
    }

    @Override
    public int getEndpointsCount() {
        return this.endpoints_.size();
    }

    @Override
    public Endpoint getEndpoints(int index) {
        return this.endpoints_.get(index);
    }

    @Override
    public EndpointOrBuilder getEndpointsOrBuilder(int index) {
        return this.endpoints_.get(index);
    }

    @Override
    public boolean hasControl() {
        return this.control_ != null;
    }

    @Override
    public Control getControl() {
        return this.control_ == null ? Control.getDefaultInstance() : this.control_;
    }

    @Override
    public ControlOrBuilder getControlOrBuilder() {
        return this.control_ == null ? Control.getDefaultInstance() : this.control_;
    }

    @Override
    public List<LogDescriptor> getLogsList() {
        return this.logs_;
    }

    @Override
    public List<? extends LogDescriptorOrBuilder> getLogsOrBuilderList() {
        return this.logs_;
    }

    @Override
    public int getLogsCount() {
        return this.logs_.size();
    }

    @Override
    public LogDescriptor getLogs(int index) {
        return this.logs_.get(index);
    }

    @Override
    public LogDescriptorOrBuilder getLogsOrBuilder(int index) {
        return this.logs_.get(index);
    }

    @Override
    public List<MetricDescriptor> getMetricsList() {
        return this.metrics_;
    }

    @Override
    public List<? extends MetricDescriptorOrBuilder> getMetricsOrBuilderList() {
        return this.metrics_;
    }

    @Override
    public int getMetricsCount() {
        return this.metrics_.size();
    }

    @Override
    public MetricDescriptor getMetrics(int index) {
        return this.metrics_.get(index);
    }

    @Override
    public MetricDescriptorOrBuilder getMetricsOrBuilder(int index) {
        return this.metrics_.get(index);
    }

    @Override
    public List<MonitoredResourceDescriptor> getMonitoredResourcesList() {
        return this.monitoredResources_;
    }

    @Override
    public List<? extends MonitoredResourceDescriptorOrBuilder> getMonitoredResourcesOrBuilderList() {
        return this.monitoredResources_;
    }

    @Override
    public int getMonitoredResourcesCount() {
        return this.monitoredResources_.size();
    }

    @Override
    public MonitoredResourceDescriptor getMonitoredResources(int index) {
        return this.monitoredResources_.get(index);
    }

    @Override
    public MonitoredResourceDescriptorOrBuilder getMonitoredResourcesOrBuilder(int index) {
        return this.monitoredResources_.get(index);
    }

    @Override
    public boolean hasBilling() {
        return this.billing_ != null;
    }

    @Override
    public Billing getBilling() {
        return this.billing_ == null ? Billing.getDefaultInstance() : this.billing_;
    }

    @Override
    public BillingOrBuilder getBillingOrBuilder() {
        return this.billing_ == null ? Billing.getDefaultInstance() : this.billing_;
    }

    @Override
    public boolean hasLogging() {
        return this.logging_ != null;
    }

    @Override
    public Logging getLogging() {
        return this.logging_ == null ? Logging.getDefaultInstance() : this.logging_;
    }

    @Override
    public LoggingOrBuilder getLoggingOrBuilder() {
        return this.logging_ == null ? Logging.getDefaultInstance() : this.logging_;
    }

    @Override
    public boolean hasMonitoring() {
        return this.monitoring_ != null;
    }

    @Override
    public Monitoring getMonitoring() {
        return this.monitoring_ == null ? Monitoring.getDefaultInstance() : this.monitoring_;
    }

    @Override
    public MonitoringOrBuilder getMonitoringOrBuilder() {
        return this.monitoring_ == null ? Monitoring.getDefaultInstance() : this.monitoring_;
    }

    @Override
    public boolean hasSystemParameters() {
        return this.systemParameters_ != null;
    }

    @Override
    public SystemParameters getSystemParameters() {
        return this.systemParameters_ == null ? SystemParameters.getDefaultInstance() : this.systemParameters_;
    }

    @Override
    public SystemParametersOrBuilder getSystemParametersOrBuilder() {
        return this.systemParameters_ == null ? SystemParameters.getDefaultInstance() : this.systemParameters_;
    }

    @Override
    public boolean hasSourceInfo() {
        return this.sourceInfo_ != null;
    }

    @Override
    public SourceInfo getSourceInfo() {
        return this.sourceInfo_ == null ? SourceInfo.getDefaultInstance() : this.sourceInfo_;
    }

    @Override
    public SourceInfoOrBuilder getSourceInfoOrBuilder() {
        return this.sourceInfo_ == null ? SourceInfo.getDefaultInstance() : this.sourceInfo_;
    }

    @Override
    public boolean hasPublishing() {
        return this.publishing_ != null;
    }

    @Override
    public Publishing getPublishing() {
        return this.publishing_ == null ? Publishing.getDefaultInstance() : this.publishing_;
    }

    @Override
    public PublishingOrBuilder getPublishingOrBuilder() {
        return this.publishing_ == null ? Publishing.getDefaultInstance() : this.publishing_;
    }

    @Override
    public boolean hasConfigVersion() {
        return this.configVersion_ != null;
    }

    @Override
    public UInt32Value getConfigVersion() {
        return this.configVersion_ == null ? UInt32Value.getDefaultInstance() : this.configVersion_;
    }

    @Override
    public UInt32ValueOrBuilder getConfigVersionOrBuilder() {
        return this.configVersion_ == null ? UInt32Value.getDefaultInstance() : this.configVersion_;
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
            GeneratedMessageV3.writeString(output, 2, this.title_);
        }
        for (i = 0; i < this.apis_.size(); ++i) {
            output.writeMessage(3, this.apis_.get(i));
        }
        for (i = 0; i < this.types_.size(); ++i) {
            output.writeMessage(4, this.types_.get(i));
        }
        for (i = 0; i < this.enums_.size(); ++i) {
            output.writeMessage(5, this.enums_.get(i));
        }
        if (this.documentation_ != null) {
            output.writeMessage(6, this.getDocumentation());
        }
        if (this.backend_ != null) {
            output.writeMessage(8, this.getBackend());
        }
        if (this.http_ != null) {
            output.writeMessage(9, this.getHttp());
        }
        if (this.quota_ != null) {
            output.writeMessage(10, this.getQuota());
        }
        if (this.authentication_ != null) {
            output.writeMessage(11, this.getAuthentication());
        }
        if (this.context_ != null) {
            output.writeMessage(12, this.getContext());
        }
        if (this.usage_ != null) {
            output.writeMessage(15, this.getUsage());
        }
        for (i = 0; i < this.endpoints_.size(); ++i) {
            output.writeMessage(18, this.endpoints_.get(i));
        }
        if (this.configVersion_ != null) {
            output.writeMessage(20, this.getConfigVersion());
        }
        if (this.control_ != null) {
            output.writeMessage(21, this.getControl());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.producerProjectId_)) {
            GeneratedMessageV3.writeString(output, 22, this.producerProjectId_);
        }
        for (i = 0; i < this.logs_.size(); ++i) {
            output.writeMessage(23, this.logs_.get(i));
        }
        for (i = 0; i < this.metrics_.size(); ++i) {
            output.writeMessage(24, this.metrics_.get(i));
        }
        for (i = 0; i < this.monitoredResources_.size(); ++i) {
            output.writeMessage(25, this.monitoredResources_.get(i));
        }
        if (this.billing_ != null) {
            output.writeMessage(26, this.getBilling());
        }
        if (this.logging_ != null) {
            output.writeMessage(27, this.getLogging());
        }
        if (this.monitoring_ != null) {
            output.writeMessage(28, this.getMonitoring());
        }
        if (this.systemParameters_ != null) {
            output.writeMessage(29, this.getSystemParameters());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            GeneratedMessageV3.writeString(output, 33, this.id_);
        }
        if (this.sourceInfo_ != null) {
            output.writeMessage(37, this.getSourceInfo());
        }
        if (this.publishing_ != null) {
            output.writeMessage(45, this.getPublishing());
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
        if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
            size += GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        if (!GeneratedMessageV3.isStringEmpty(this.title_)) {
            size += GeneratedMessageV3.computeStringSize(2, this.title_);
        }
        for (i = 0; i < this.apis_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(3, this.apis_.get(i));
        }
        for (i = 0; i < this.types_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(4, this.types_.get(i));
        }
        for (i = 0; i < this.enums_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(5, this.enums_.get(i));
        }
        if (this.documentation_ != null) {
            size += CodedOutputStream.computeMessageSize(6, this.getDocumentation());
        }
        if (this.backend_ != null) {
            size += CodedOutputStream.computeMessageSize(8, this.getBackend());
        }
        if (this.http_ != null) {
            size += CodedOutputStream.computeMessageSize(9, this.getHttp());
        }
        if (this.quota_ != null) {
            size += CodedOutputStream.computeMessageSize(10, this.getQuota());
        }
        if (this.authentication_ != null) {
            size += CodedOutputStream.computeMessageSize(11, this.getAuthentication());
        }
        if (this.context_ != null) {
            size += CodedOutputStream.computeMessageSize(12, this.getContext());
        }
        if (this.usage_ != null) {
            size += CodedOutputStream.computeMessageSize(15, this.getUsage());
        }
        for (i = 0; i < this.endpoints_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(18, this.endpoints_.get(i));
        }
        if (this.configVersion_ != null) {
            size += CodedOutputStream.computeMessageSize(20, this.getConfigVersion());
        }
        if (this.control_ != null) {
            size += CodedOutputStream.computeMessageSize(21, this.getControl());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.producerProjectId_)) {
            size += GeneratedMessageV3.computeStringSize(22, this.producerProjectId_);
        }
        for (i = 0; i < this.logs_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(23, this.logs_.get(i));
        }
        for (i = 0; i < this.metrics_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(24, this.metrics_.get(i));
        }
        for (i = 0; i < this.monitoredResources_.size(); ++i) {
            size += CodedOutputStream.computeMessageSize(25, this.monitoredResources_.get(i));
        }
        if (this.billing_ != null) {
            size += CodedOutputStream.computeMessageSize(26, this.getBilling());
        }
        if (this.logging_ != null) {
            size += CodedOutputStream.computeMessageSize(27, this.getLogging());
        }
        if (this.monitoring_ != null) {
            size += CodedOutputStream.computeMessageSize(28, this.getMonitoring());
        }
        if (this.systemParameters_ != null) {
            size += CodedOutputStream.computeMessageSize(29, this.getSystemParameters());
        }
        if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
            size += GeneratedMessageV3.computeStringSize(33, this.id_);
        }
        if (this.sourceInfo_ != null) {
            size += CodedOutputStream.computeMessageSize(37, this.getSourceInfo());
        }
        if (this.publishing_ != null) {
            size += CodedOutputStream.computeMessageSize(45, this.getPublishing());
        }
        this.memoizedSize = size += this.getUnknownFields().getSerializedSize();
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Service)) {
            return super.equals(obj);
        }
        Service other = (Service)obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getTitle().equals(other.getTitle())) {
            return false;
        }
        if (!this.getProducerProjectId().equals(other.getProducerProjectId())) {
            return false;
        }
        if (!this.getId().equals(other.getId())) {
            return false;
        }
        if (!this.getApisList().equals(other.getApisList())) {
            return false;
        }
        if (!this.getTypesList().equals(other.getTypesList())) {
            return false;
        }
        if (!this.getEnumsList().equals(other.getEnumsList())) {
            return false;
        }
        if (this.hasDocumentation() != other.hasDocumentation()) {
            return false;
        }
        if (this.hasDocumentation() && !this.getDocumentation().equals(other.getDocumentation())) {
            return false;
        }
        if (this.hasBackend() != other.hasBackend()) {
            return false;
        }
        if (this.hasBackend() && !this.getBackend().equals(other.getBackend())) {
            return false;
        }
        if (this.hasHttp() != other.hasHttp()) {
            return false;
        }
        if (this.hasHttp() && !this.getHttp().equals(other.getHttp())) {
            return false;
        }
        if (this.hasQuota() != other.hasQuota()) {
            return false;
        }
        if (this.hasQuota() && !this.getQuota().equals(other.getQuota())) {
            return false;
        }
        if (this.hasAuthentication() != other.hasAuthentication()) {
            return false;
        }
        if (this.hasAuthentication() && !this.getAuthentication().equals(other.getAuthentication())) {
            return false;
        }
        if (this.hasContext() != other.hasContext()) {
            return false;
        }
        if (this.hasContext() && !this.getContext().equals(other.getContext())) {
            return false;
        }
        if (this.hasUsage() != other.hasUsage()) {
            return false;
        }
        if (this.hasUsage() && !this.getUsage().equals(other.getUsage())) {
            return false;
        }
        if (!this.getEndpointsList().equals(other.getEndpointsList())) {
            return false;
        }
        if (this.hasControl() != other.hasControl()) {
            return false;
        }
        if (this.hasControl() && !this.getControl().equals(other.getControl())) {
            return false;
        }
        if (!this.getLogsList().equals(other.getLogsList())) {
            return false;
        }
        if (!this.getMetricsList().equals(other.getMetricsList())) {
            return false;
        }
        if (!this.getMonitoredResourcesList().equals(other.getMonitoredResourcesList())) {
            return false;
        }
        if (this.hasBilling() != other.hasBilling()) {
            return false;
        }
        if (this.hasBilling() && !this.getBilling().equals(other.getBilling())) {
            return false;
        }
        if (this.hasLogging() != other.hasLogging()) {
            return false;
        }
        if (this.hasLogging() && !this.getLogging().equals(other.getLogging())) {
            return false;
        }
        if (this.hasMonitoring() != other.hasMonitoring()) {
            return false;
        }
        if (this.hasMonitoring() && !this.getMonitoring().equals(other.getMonitoring())) {
            return false;
        }
        if (this.hasSystemParameters() != other.hasSystemParameters()) {
            return false;
        }
        if (this.hasSystemParameters() && !this.getSystemParameters().equals(other.getSystemParameters())) {
            return false;
        }
        if (this.hasSourceInfo() != other.hasSourceInfo()) {
            return false;
        }
        if (this.hasSourceInfo() && !this.getSourceInfo().equals(other.getSourceInfo())) {
            return false;
        }
        if (this.hasPublishing() != other.hasPublishing()) {
            return false;
        }
        if (this.hasPublishing() && !this.getPublishing().equals(other.getPublishing())) {
            return false;
        }
        if (this.hasConfigVersion() != other.hasConfigVersion()) {
            return false;
        }
        if (this.hasConfigVersion() && !this.getConfigVersion().equals(other.getConfigVersion())) {
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
        hash = 19 * hash + Service.getDescriptor().hashCode();
        hash = 37 * hash + 1;
        hash = 53 * hash + this.getName().hashCode();
        hash = 37 * hash + 2;
        hash = 53 * hash + this.getTitle().hashCode();
        hash = 37 * hash + 22;
        hash = 53 * hash + this.getProducerProjectId().hashCode();
        hash = 37 * hash + 33;
        hash = 53 * hash + this.getId().hashCode();
        if (this.getApisCount() > 0) {
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getApisList().hashCode();
        }
        if (this.getTypesCount() > 0) {
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getTypesList().hashCode();
        }
        if (this.getEnumsCount() > 0) {
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getEnumsList().hashCode();
        }
        if (this.hasDocumentation()) {
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getDocumentation().hashCode();
        }
        if (this.hasBackend()) {
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getBackend().hashCode();
        }
        if (this.hasHttp()) {
            hash = 37 * hash + 9;
            hash = 53 * hash + this.getHttp().hashCode();
        }
        if (this.hasQuota()) {
            hash = 37 * hash + 10;
            hash = 53 * hash + this.getQuota().hashCode();
        }
        if (this.hasAuthentication()) {
            hash = 37 * hash + 11;
            hash = 53 * hash + this.getAuthentication().hashCode();
        }
        if (this.hasContext()) {
            hash = 37 * hash + 12;
            hash = 53 * hash + this.getContext().hashCode();
        }
        if (this.hasUsage()) {
            hash = 37 * hash + 15;
            hash = 53 * hash + this.getUsage().hashCode();
        }
        if (this.getEndpointsCount() > 0) {
            hash = 37 * hash + 18;
            hash = 53 * hash + this.getEndpointsList().hashCode();
        }
        if (this.hasControl()) {
            hash = 37 * hash + 21;
            hash = 53 * hash + this.getControl().hashCode();
        }
        if (this.getLogsCount() > 0) {
            hash = 37 * hash + 23;
            hash = 53 * hash + this.getLogsList().hashCode();
        }
        if (this.getMetricsCount() > 0) {
            hash = 37 * hash + 24;
            hash = 53 * hash + this.getMetricsList().hashCode();
        }
        if (this.getMonitoredResourcesCount() > 0) {
            hash = 37 * hash + 25;
            hash = 53 * hash + this.getMonitoredResourcesList().hashCode();
        }
        if (this.hasBilling()) {
            hash = 37 * hash + 26;
            hash = 53 * hash + this.getBilling().hashCode();
        }
        if (this.hasLogging()) {
            hash = 37 * hash + 27;
            hash = 53 * hash + this.getLogging().hashCode();
        }
        if (this.hasMonitoring()) {
            hash = 37 * hash + 28;
            hash = 53 * hash + this.getMonitoring().hashCode();
        }
        if (this.hasSystemParameters()) {
            hash = 37 * hash + 29;
            hash = 53 * hash + this.getSystemParameters().hashCode();
        }
        if (this.hasSourceInfo()) {
            hash = 37 * hash + 37;
            hash = 53 * hash + this.getSourceInfo().hashCode();
        }
        if (this.hasPublishing()) {
            hash = 37 * hash + 45;
            hash = 53 * hash + this.getPublishing().hashCode();
        }
        if (this.hasConfigVersion()) {
            hash = 37 * hash + 20;
            hash = 53 * hash + this.getConfigVersion().hashCode();
        }
        this.memoizedHashCode = hash = 29 * hash + this.getUnknownFields().hashCode();
        return hash;
    }

    public static Service parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Service parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Service parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Service parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Service parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Service parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Service parseFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Service parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Service parseDelimitedFrom(InputStream input) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Service parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Service parseFrom(CodedInputStream input) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Service parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return Service.newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Service prototype) {
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

    public static Service getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Service> parser() {
        return PARSER;
    }

    public Parser<Service> getParserForType() {
        return PARSER;
    }

    @Override
    public Service getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    public static final class Builder
    extends GeneratedMessageV3.Builder<Builder>
    implements ServiceOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private Object title_ = "";
        private Object producerProjectId_ = "";
        private Object id_ = "";
        private List<Api> apis_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Api, Api.Builder, ApiOrBuilder> apisBuilder_;
        private List<Type> types_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Type, Type.Builder, TypeOrBuilder> typesBuilder_;
        private List<Enum> enums_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Enum, Enum.Builder, EnumOrBuilder> enumsBuilder_;
        private Documentation documentation_;
        private SingleFieldBuilderV3<Documentation, Documentation.Builder, DocumentationOrBuilder> documentationBuilder_;
        private Backend backend_;
        private SingleFieldBuilderV3<Backend, Backend.Builder, BackendOrBuilder> backendBuilder_;
        private Http http_;
        private SingleFieldBuilderV3<Http, Http.Builder, HttpOrBuilder> httpBuilder_;
        private Quota quota_;
        private SingleFieldBuilderV3<Quota, Quota.Builder, QuotaOrBuilder> quotaBuilder_;
        private Authentication authentication_;
        private SingleFieldBuilderV3<Authentication, Authentication.Builder, AuthenticationOrBuilder> authenticationBuilder_;
        private Context context_;
        private SingleFieldBuilderV3<Context, Context.Builder, ContextOrBuilder> contextBuilder_;
        private Usage usage_;
        private SingleFieldBuilderV3<Usage, Usage.Builder, UsageOrBuilder> usageBuilder_;
        private List<Endpoint> endpoints_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<Endpoint, Endpoint.Builder, EndpointOrBuilder> endpointsBuilder_;
        private Control control_;
        private SingleFieldBuilderV3<Control, Control.Builder, ControlOrBuilder> controlBuilder_;
        private List<LogDescriptor> logs_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<LogDescriptor, LogDescriptor.Builder, LogDescriptorOrBuilder> logsBuilder_;
        private List<MetricDescriptor> metrics_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MetricDescriptor, MetricDescriptor.Builder, MetricDescriptorOrBuilder> metricsBuilder_;
        private List<MonitoredResourceDescriptor> monitoredResources_ = Collections.emptyList();
        private RepeatedFieldBuilderV3<MonitoredResourceDescriptor, MonitoredResourceDescriptor.Builder, MonitoredResourceDescriptorOrBuilder> monitoredResourcesBuilder_;
        private Billing billing_;
        private SingleFieldBuilderV3<Billing, Billing.Builder, BillingOrBuilder> billingBuilder_;
        private Logging logging_;
        private SingleFieldBuilderV3<Logging, Logging.Builder, LoggingOrBuilder> loggingBuilder_;
        private Monitoring monitoring_;
        private SingleFieldBuilderV3<Monitoring, Monitoring.Builder, MonitoringOrBuilder> monitoringBuilder_;
        private SystemParameters systemParameters_;
        private SingleFieldBuilderV3<SystemParameters, SystemParameters.Builder, SystemParametersOrBuilder> systemParametersBuilder_;
        private SourceInfo sourceInfo_;
        private SingleFieldBuilderV3<SourceInfo, SourceInfo.Builder, SourceInfoOrBuilder> sourceInfoBuilder_;
        private Publishing publishing_;
        private SingleFieldBuilderV3<Publishing, Publishing.Builder, PublishingOrBuilder> publishingBuilder_;
        private UInt32Value configVersion_;
        private SingleFieldBuilderV3<UInt32Value, UInt32Value.Builder, UInt32ValueOrBuilder> configVersionBuilder_;

        public static final Descriptors.Descriptor getDescriptor() {
            return ServiceProto.internal_static_google_api_Service_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ServiceProto.internal_static_google_api_Service_fieldAccessorTable.ensureFieldAccessorsInitialized(Service.class, Builder.class);
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
            this.name_ = "";
            this.title_ = "";
            this.producerProjectId_ = "";
            this.id_ = "";
            if (this.apisBuilder_ == null) {
                this.apis_ = Collections.emptyList();
            } else {
                this.apis_ = null;
                this.apisBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFEF;
            if (this.typesBuilder_ == null) {
                this.types_ = Collections.emptyList();
            } else {
                this.types_ = null;
                this.typesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFDF;
            if (this.enumsBuilder_ == null) {
                this.enums_ = Collections.emptyList();
            } else {
                this.enums_ = null;
                this.enumsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFFFBF;
            this.documentation_ = null;
            if (this.documentationBuilder_ != null) {
                this.documentationBuilder_.dispose();
                this.documentationBuilder_ = null;
            }
            this.backend_ = null;
            if (this.backendBuilder_ != null) {
                this.backendBuilder_.dispose();
                this.backendBuilder_ = null;
            }
            this.http_ = null;
            if (this.httpBuilder_ != null) {
                this.httpBuilder_.dispose();
                this.httpBuilder_ = null;
            }
            this.quota_ = null;
            if (this.quotaBuilder_ != null) {
                this.quotaBuilder_.dispose();
                this.quotaBuilder_ = null;
            }
            this.authentication_ = null;
            if (this.authenticationBuilder_ != null) {
                this.authenticationBuilder_.dispose();
                this.authenticationBuilder_ = null;
            }
            this.context_ = null;
            if (this.contextBuilder_ != null) {
                this.contextBuilder_.dispose();
                this.contextBuilder_ = null;
            }
            this.usage_ = null;
            if (this.usageBuilder_ != null) {
                this.usageBuilder_.dispose();
                this.usageBuilder_ = null;
            }
            if (this.endpointsBuilder_ == null) {
                this.endpoints_ = Collections.emptyList();
            } else {
                this.endpoints_ = null;
                this.endpointsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFFBFFF;
            this.control_ = null;
            if (this.controlBuilder_ != null) {
                this.controlBuilder_.dispose();
                this.controlBuilder_ = null;
            }
            if (this.logsBuilder_ == null) {
                this.logs_ = Collections.emptyList();
            } else {
                this.logs_ = null;
                this.logsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFEFFFF;
            if (this.metricsBuilder_ == null) {
                this.metrics_ = Collections.emptyList();
            } else {
                this.metrics_ = null;
                this.metricsBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFDFFFF;
            if (this.monitoredResourcesBuilder_ == null) {
                this.monitoredResources_ = Collections.emptyList();
            } else {
                this.monitoredResources_ = null;
                this.monitoredResourcesBuilder_.clear();
            }
            this.bitField0_ &= 0xFFFBFFFF;
            this.billing_ = null;
            if (this.billingBuilder_ != null) {
                this.billingBuilder_.dispose();
                this.billingBuilder_ = null;
            }
            this.logging_ = null;
            if (this.loggingBuilder_ != null) {
                this.loggingBuilder_.dispose();
                this.loggingBuilder_ = null;
            }
            this.monitoring_ = null;
            if (this.monitoringBuilder_ != null) {
                this.monitoringBuilder_.dispose();
                this.monitoringBuilder_ = null;
            }
            this.systemParameters_ = null;
            if (this.systemParametersBuilder_ != null) {
                this.systemParametersBuilder_.dispose();
                this.systemParametersBuilder_ = null;
            }
            this.sourceInfo_ = null;
            if (this.sourceInfoBuilder_ != null) {
                this.sourceInfoBuilder_.dispose();
                this.sourceInfoBuilder_ = null;
            }
            this.publishing_ = null;
            if (this.publishingBuilder_ != null) {
                this.publishingBuilder_.dispose();
                this.publishingBuilder_ = null;
            }
            this.configVersion_ = null;
            if (this.configVersionBuilder_ != null) {
                this.configVersionBuilder_.dispose();
                this.configVersionBuilder_ = null;
            }
            return this;
        }

        @Override
        public Descriptors.Descriptor getDescriptorForType() {
            return ServiceProto.internal_static_google_api_Service_descriptor;
        }

        @Override
        public Service getDefaultInstanceForType() {
            return Service.getDefaultInstance();
        }

        @Override
        public Service build() {
            Service result = this.buildPartial();
            if (!result.isInitialized()) {
                throw Builder.newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Service buildPartial() {
            Service result = new Service(this);
            this.buildPartialRepeatedFields(result);
            if (this.bitField0_ != 0) {
                this.buildPartial0(result);
            }
            this.onBuilt();
            return result;
        }

        private void buildPartialRepeatedFields(Service result) {
            if (this.apisBuilder_ == null) {
                if ((this.bitField0_ & 0x10) != 0) {
                    this.apis_ = Collections.unmodifiableList(this.apis_);
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                result.apis_ = this.apis_;
            } else {
                result.apis_ = this.apisBuilder_.build();
            }
            if (this.typesBuilder_ == null) {
                if ((this.bitField0_ & 0x20) != 0) {
                    this.types_ = Collections.unmodifiableList(this.types_);
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result.types_ = this.types_;
            } else {
                result.types_ = this.typesBuilder_.build();
            }
            if (this.enumsBuilder_ == null) {
                if ((this.bitField0_ & 0x40) != 0) {
                    this.enums_ = Collections.unmodifiableList(this.enums_);
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                result.enums_ = this.enums_;
            } else {
                result.enums_ = this.enumsBuilder_.build();
            }
            if (this.endpointsBuilder_ == null) {
                if ((this.bitField0_ & 0x4000) != 0) {
                    this.endpoints_ = Collections.unmodifiableList(this.endpoints_);
                    this.bitField0_ &= 0xFFFFBFFF;
                }
                result.endpoints_ = this.endpoints_;
            } else {
                result.endpoints_ = this.endpointsBuilder_.build();
            }
            if (this.logsBuilder_ == null) {
                if ((this.bitField0_ & 0x10000) != 0) {
                    this.logs_ = Collections.unmodifiableList(this.logs_);
                    this.bitField0_ &= 0xFFFEFFFF;
                }
                result.logs_ = this.logs_;
            } else {
                result.logs_ = this.logsBuilder_.build();
            }
            if (this.metricsBuilder_ == null) {
                if ((this.bitField0_ & 0x20000) != 0) {
                    this.metrics_ = Collections.unmodifiableList(this.metrics_);
                    this.bitField0_ &= 0xFFFDFFFF;
                }
                result.metrics_ = this.metrics_;
            } else {
                result.metrics_ = this.metricsBuilder_.build();
            }
            if (this.monitoredResourcesBuilder_ == null) {
                if ((this.bitField0_ & 0x40000) != 0) {
                    this.monitoredResources_ = Collections.unmodifiableList(this.monitoredResources_);
                    this.bitField0_ &= 0xFFFBFFFF;
                }
                result.monitoredResources_ = this.monitoredResources_;
            } else {
                result.monitoredResources_ = this.monitoredResourcesBuilder_.build();
            }
        }

        private void buildPartial0(Service result) {
            int from_bitField0_ = this.bitField0_;
            if ((from_bitField0_ & 1) != 0) {
                result.name_ = this.name_;
            }
            if ((from_bitField0_ & 2) != 0) {
                result.title_ = this.title_;
            }
            if ((from_bitField0_ & 4) != 0) {
                result.producerProjectId_ = this.producerProjectId_;
            }
            if ((from_bitField0_ & 8) != 0) {
                result.id_ = this.id_;
            }
            if ((from_bitField0_ & 0x80) != 0) {
                result.documentation_ = this.documentationBuilder_ == null ? this.documentation_ : this.documentationBuilder_.build();
            }
            if ((from_bitField0_ & 0x100) != 0) {
                result.backend_ = this.backendBuilder_ == null ? this.backend_ : this.backendBuilder_.build();
            }
            if ((from_bitField0_ & 0x200) != 0) {
                result.http_ = this.httpBuilder_ == null ? this.http_ : this.httpBuilder_.build();
            }
            if ((from_bitField0_ & 0x400) != 0) {
                result.quota_ = this.quotaBuilder_ == null ? this.quota_ : this.quotaBuilder_.build();
            }
            if ((from_bitField0_ & 0x800) != 0) {
                result.authentication_ = this.authenticationBuilder_ == null ? this.authentication_ : this.authenticationBuilder_.build();
            }
            if ((from_bitField0_ & 0x1000) != 0) {
                result.context_ = this.contextBuilder_ == null ? this.context_ : this.contextBuilder_.build();
            }
            if ((from_bitField0_ & 0x2000) != 0) {
                result.usage_ = this.usageBuilder_ == null ? this.usage_ : this.usageBuilder_.build();
            }
            if ((from_bitField0_ & 0x8000) != 0) {
                result.control_ = this.controlBuilder_ == null ? this.control_ : this.controlBuilder_.build();
            }
            if ((from_bitField0_ & 0x80000) != 0) {
                result.billing_ = this.billingBuilder_ == null ? this.billing_ : this.billingBuilder_.build();
            }
            if ((from_bitField0_ & 0x100000) != 0) {
                result.logging_ = this.loggingBuilder_ == null ? this.logging_ : this.loggingBuilder_.build();
            }
            if ((from_bitField0_ & 0x200000) != 0) {
                result.monitoring_ = this.monitoringBuilder_ == null ? this.monitoring_ : this.monitoringBuilder_.build();
            }
            if ((from_bitField0_ & 0x400000) != 0) {
                result.systemParameters_ = this.systemParametersBuilder_ == null ? this.systemParameters_ : this.systemParametersBuilder_.build();
            }
            if ((from_bitField0_ & 0x800000) != 0) {
                result.sourceInfo_ = this.sourceInfoBuilder_ == null ? this.sourceInfo_ : this.sourceInfoBuilder_.build();
            }
            if ((from_bitField0_ & 0x1000000) != 0) {
                result.publishing_ = this.publishingBuilder_ == null ? this.publishing_ : this.publishingBuilder_.build();
            }
            if ((from_bitField0_ & 0x2000000) != 0) {
                result.configVersion_ = this.configVersionBuilder_ == null ? this.configVersion_ : this.configVersionBuilder_.build();
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
            if (other instanceof Service) {
                return this.mergeFrom((Service)other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Service other) {
            if (other == Service.getDefaultInstance()) {
                return this;
            }
            if (!other.getName().isEmpty()) {
                this.name_ = other.name_;
                this.bitField0_ |= 1;
                this.onChanged();
            }
            if (!other.getTitle().isEmpty()) {
                this.title_ = other.title_;
                this.bitField0_ |= 2;
                this.onChanged();
            }
            if (!other.getProducerProjectId().isEmpty()) {
                this.producerProjectId_ = other.producerProjectId_;
                this.bitField0_ |= 4;
                this.onChanged();
            }
            if (!other.getId().isEmpty()) {
                this.id_ = other.id_;
                this.bitField0_ |= 8;
                this.onChanged();
            }
            if (this.apisBuilder_ == null) {
                if (!other.apis_.isEmpty()) {
                    if (this.apis_.isEmpty()) {
                        this.apis_ = other.apis_;
                        this.bitField0_ &= 0xFFFFFFEF;
                    } else {
                        this.ensureApisIsMutable();
                        this.apis_.addAll(other.apis_);
                    }
                    this.onChanged();
                }
            } else if (!other.apis_.isEmpty()) {
                if (this.apisBuilder_.isEmpty()) {
                    this.apisBuilder_.dispose();
                    this.apisBuilder_ = null;
                    this.apis_ = other.apis_;
                    this.bitField0_ &= 0xFFFFFFEF;
                    this.apisBuilder_ = alwaysUseFieldBuilders ? this.getApisFieldBuilder() : null;
                } else {
                    this.apisBuilder_.addAllMessages(other.apis_);
                }
            }
            if (this.typesBuilder_ == null) {
                if (!other.types_.isEmpty()) {
                    if (this.types_.isEmpty()) {
                        this.types_ = other.types_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    } else {
                        this.ensureTypesIsMutable();
                        this.types_.addAll(other.types_);
                    }
                    this.onChanged();
                }
            } else if (!other.types_.isEmpty()) {
                if (this.typesBuilder_.isEmpty()) {
                    this.typesBuilder_.dispose();
                    this.typesBuilder_ = null;
                    this.types_ = other.types_;
                    this.bitField0_ &= 0xFFFFFFDF;
                    this.typesBuilder_ = alwaysUseFieldBuilders ? this.getTypesFieldBuilder() : null;
                } else {
                    this.typesBuilder_.addAllMessages(other.types_);
                }
            }
            if (this.enumsBuilder_ == null) {
                if (!other.enums_.isEmpty()) {
                    if (this.enums_.isEmpty()) {
                        this.enums_ = other.enums_;
                        this.bitField0_ &= 0xFFFFFFBF;
                    } else {
                        this.ensureEnumsIsMutable();
                        this.enums_.addAll(other.enums_);
                    }
                    this.onChanged();
                }
            } else if (!other.enums_.isEmpty()) {
                if (this.enumsBuilder_.isEmpty()) {
                    this.enumsBuilder_.dispose();
                    this.enumsBuilder_ = null;
                    this.enums_ = other.enums_;
                    this.bitField0_ &= 0xFFFFFFBF;
                    this.enumsBuilder_ = alwaysUseFieldBuilders ? this.getEnumsFieldBuilder() : null;
                } else {
                    this.enumsBuilder_.addAllMessages(other.enums_);
                }
            }
            if (other.hasDocumentation()) {
                this.mergeDocumentation(other.getDocumentation());
            }
            if (other.hasBackend()) {
                this.mergeBackend(other.getBackend());
            }
            if (other.hasHttp()) {
                this.mergeHttp(other.getHttp());
            }
            if (other.hasQuota()) {
                this.mergeQuota(other.getQuota());
            }
            if (other.hasAuthentication()) {
                this.mergeAuthentication(other.getAuthentication());
            }
            if (other.hasContext()) {
                this.mergeContext(other.getContext());
            }
            if (other.hasUsage()) {
                this.mergeUsage(other.getUsage());
            }
            if (this.endpointsBuilder_ == null) {
                if (!other.endpoints_.isEmpty()) {
                    if (this.endpoints_.isEmpty()) {
                        this.endpoints_ = other.endpoints_;
                        this.bitField0_ &= 0xFFFFBFFF;
                    } else {
                        this.ensureEndpointsIsMutable();
                        this.endpoints_.addAll(other.endpoints_);
                    }
                    this.onChanged();
                }
            } else if (!other.endpoints_.isEmpty()) {
                if (this.endpointsBuilder_.isEmpty()) {
                    this.endpointsBuilder_.dispose();
                    this.endpointsBuilder_ = null;
                    this.endpoints_ = other.endpoints_;
                    this.bitField0_ &= 0xFFFFBFFF;
                    this.endpointsBuilder_ = alwaysUseFieldBuilders ? this.getEndpointsFieldBuilder() : null;
                } else {
                    this.endpointsBuilder_.addAllMessages(other.endpoints_);
                }
            }
            if (other.hasControl()) {
                this.mergeControl(other.getControl());
            }
            if (this.logsBuilder_ == null) {
                if (!other.logs_.isEmpty()) {
                    if (this.logs_.isEmpty()) {
                        this.logs_ = other.logs_;
                        this.bitField0_ &= 0xFFFEFFFF;
                    } else {
                        this.ensureLogsIsMutable();
                        this.logs_.addAll(other.logs_);
                    }
                    this.onChanged();
                }
            } else if (!other.logs_.isEmpty()) {
                if (this.logsBuilder_.isEmpty()) {
                    this.logsBuilder_.dispose();
                    this.logsBuilder_ = null;
                    this.logs_ = other.logs_;
                    this.bitField0_ &= 0xFFFEFFFF;
                    this.logsBuilder_ = alwaysUseFieldBuilders ? this.getLogsFieldBuilder() : null;
                } else {
                    this.logsBuilder_.addAllMessages(other.logs_);
                }
            }
            if (this.metricsBuilder_ == null) {
                if (!other.metrics_.isEmpty()) {
                    if (this.metrics_.isEmpty()) {
                        this.metrics_ = other.metrics_;
                        this.bitField0_ &= 0xFFFDFFFF;
                    } else {
                        this.ensureMetricsIsMutable();
                        this.metrics_.addAll(other.metrics_);
                    }
                    this.onChanged();
                }
            } else if (!other.metrics_.isEmpty()) {
                if (this.metricsBuilder_.isEmpty()) {
                    this.metricsBuilder_.dispose();
                    this.metricsBuilder_ = null;
                    this.metrics_ = other.metrics_;
                    this.bitField0_ &= 0xFFFDFFFF;
                    this.metricsBuilder_ = alwaysUseFieldBuilders ? this.getMetricsFieldBuilder() : null;
                } else {
                    this.metricsBuilder_.addAllMessages(other.metrics_);
                }
            }
            if (this.monitoredResourcesBuilder_ == null) {
                if (!other.monitoredResources_.isEmpty()) {
                    if (this.monitoredResources_.isEmpty()) {
                        this.monitoredResources_ = other.monitoredResources_;
                        this.bitField0_ &= 0xFFFBFFFF;
                    } else {
                        this.ensureMonitoredResourcesIsMutable();
                        this.monitoredResources_.addAll(other.monitoredResources_);
                    }
                    this.onChanged();
                }
            } else if (!other.monitoredResources_.isEmpty()) {
                if (this.monitoredResourcesBuilder_.isEmpty()) {
                    this.monitoredResourcesBuilder_.dispose();
                    this.monitoredResourcesBuilder_ = null;
                    this.monitoredResources_ = other.monitoredResources_;
                    this.bitField0_ &= 0xFFFBFFFF;
                    this.monitoredResourcesBuilder_ = alwaysUseFieldBuilders ? this.getMonitoredResourcesFieldBuilder() : null;
                } else {
                    this.monitoredResourcesBuilder_.addAllMessages(other.monitoredResources_);
                }
            }
            if (other.hasBilling()) {
                this.mergeBilling(other.getBilling());
            }
            if (other.hasLogging()) {
                this.mergeLogging(other.getLogging());
            }
            if (other.hasMonitoring()) {
                this.mergeMonitoring(other.getMonitoring());
            }
            if (other.hasSystemParameters()) {
                this.mergeSystemParameters(other.getSystemParameters());
            }
            if (other.hasSourceInfo()) {
                this.mergeSourceInfo(other.getSourceInfo());
            }
            if (other.hasPublishing()) {
                this.mergePublishing(other.getPublishing());
            }
            if (other.hasConfigVersion()) {
                this.mergeConfigVersion(other.getConfigVersion());
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
                block34: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block34;
                        }
                        case 10: {
                            this.name_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 1;
                            continue block34;
                        }
                        case 18: {
                            this.title_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 2;
                            continue block34;
                        }
                        case 26: {
                            GeneratedMessageV3 m4 = input.readMessage(Api.parser(), extensionRegistry);
                            if (this.apisBuilder_ == null) {
                                this.ensureApisIsMutable();
                                this.apis_.add((Api)m4);
                                continue block34;
                            }
                            this.apisBuilder_.addMessage((Api)m4);
                            continue block34;
                        }
                        case 34: {
                            GeneratedMessageV3 m4 = input.readMessage(Type.parser(), extensionRegistry);
                            if (this.typesBuilder_ == null) {
                                this.ensureTypesIsMutable();
                                this.types_.add((Type)m4);
                                continue block34;
                            }
                            this.typesBuilder_.addMessage((Type)m4);
                            continue block34;
                        }
                        case 42: {
                            GeneratedMessageV3 m4 = input.readMessage(Enum.parser(), extensionRegistry);
                            if (this.enumsBuilder_ == null) {
                                this.ensureEnumsIsMutable();
                                this.enums_.add((Enum)m4);
                                continue block34;
                            }
                            this.enumsBuilder_.addMessage((Enum)m4);
                            continue block34;
                        }
                        case 50: {
                            input.readMessage(this.getDocumentationFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x80;
                            continue block34;
                        }
                        case 66: {
                            input.readMessage(this.getBackendFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x100;
                            continue block34;
                        }
                        case 74: {
                            input.readMessage(this.getHttpFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x200;
                            continue block34;
                        }
                        case 82: {
                            input.readMessage(this.getQuotaFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x400;
                            continue block34;
                        }
                        case 90: {
                            input.readMessage(this.getAuthenticationFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x800;
                            continue block34;
                        }
                        case 98: {
                            input.readMessage(this.getContextFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x1000;
                            continue block34;
                        }
                        case 122: {
                            input.readMessage(this.getUsageFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x2000;
                            continue block34;
                        }
                        case 146: {
                            GeneratedMessageV3 m4 = input.readMessage(Endpoint.parser(), extensionRegistry);
                            if (this.endpointsBuilder_ == null) {
                                this.ensureEndpointsIsMutable();
                                this.endpoints_.add((Endpoint)m4);
                                continue block34;
                            }
                            this.endpointsBuilder_.addMessage((Endpoint)m4);
                            continue block34;
                        }
                        case 162: {
                            input.readMessage(this.getConfigVersionFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x2000000;
                            continue block34;
                        }
                        case 170: {
                            input.readMessage(this.getControlFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x8000;
                            continue block34;
                        }
                        case 178: {
                            this.producerProjectId_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 4;
                            continue block34;
                        }
                        case 186: {
                            GeneratedMessageV3 m4 = input.readMessage(LogDescriptor.parser(), extensionRegistry);
                            if (this.logsBuilder_ == null) {
                                this.ensureLogsIsMutable();
                                this.logs_.add((LogDescriptor)m4);
                                continue block34;
                            }
                            this.logsBuilder_.addMessage((LogDescriptor)m4);
                            continue block34;
                        }
                        case 194: {
                            GeneratedMessageV3 m4 = input.readMessage(MetricDescriptor.parser(), extensionRegistry);
                            if (this.metricsBuilder_ == null) {
                                this.ensureMetricsIsMutable();
                                this.metrics_.add((MetricDescriptor)m4);
                                continue block34;
                            }
                            this.metricsBuilder_.addMessage((MetricDescriptor)m4);
                            continue block34;
                        }
                        case 202: {
                            GeneratedMessageV3 m4 = input.readMessage(MonitoredResourceDescriptor.parser(), extensionRegistry);
                            if (this.monitoredResourcesBuilder_ == null) {
                                this.ensureMonitoredResourcesIsMutable();
                                this.monitoredResources_.add((MonitoredResourceDescriptor)m4);
                                continue block34;
                            }
                            this.monitoredResourcesBuilder_.addMessage((MonitoredResourceDescriptor)m4);
                            continue block34;
                        }
                        case 210: {
                            input.readMessage(this.getBillingFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x80000;
                            continue block34;
                        }
                        case 218: {
                            input.readMessage(this.getLoggingFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x100000;
                            continue block34;
                        }
                        case 226: {
                            input.readMessage(this.getMonitoringFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x200000;
                            continue block34;
                        }
                        case 234: {
                            input.readMessage(this.getSystemParametersFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x400000;
                            continue block34;
                        }
                        case 266: {
                            this.id_ = input.readStringRequireUtf8();
                            this.bitField0_ |= 8;
                            continue block34;
                        }
                        case 298: {
                            input.readMessage(this.getSourceInfoFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x800000;
                            continue block34;
                        }
                        case 362: {
                            input.readMessage(this.getPublishingFieldBuilder().getBuilder(), extensionRegistry);
                            this.bitField0_ |= 0x1000000;
                            continue block34;
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
        public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.name_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.name_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = Service.getDefaultInstance().getName();
            this.bitField0_ &= 0xFFFFFFFE;
            this.onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Service.checkByteStringIsUtf8(value);
            this.name_ = value;
            this.bitField0_ |= 1;
            this.onChanged();
            return this;
        }

        @Override
        public String getTitle() {
            Object ref = this.title_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.title_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getTitleBytes() {
            Object ref = this.title_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.title_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setTitle(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.title_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        public Builder clearTitle() {
            this.title_ = Service.getDefaultInstance().getTitle();
            this.bitField0_ &= 0xFFFFFFFD;
            this.onChanged();
            return this;
        }

        public Builder setTitleBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Service.checkByteStringIsUtf8(value);
            this.title_ = value;
            this.bitField0_ |= 2;
            this.onChanged();
            return this;
        }

        @Override
        public String getProducerProjectId() {
            Object ref = this.producerProjectId_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.producerProjectId_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getProducerProjectIdBytes() {
            Object ref = this.producerProjectId_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.producerProjectId_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setProducerProjectId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.producerProjectId_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        public Builder clearProducerProjectId() {
            this.producerProjectId_ = Service.getDefaultInstance().getProducerProjectId();
            this.bitField0_ &= 0xFFFFFFFB;
            this.onChanged();
            return this;
        }

        public Builder setProducerProjectIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Service.checkByteStringIsUtf8(value);
            this.producerProjectId_ = value;
            this.bitField0_ |= 4;
            this.onChanged();
            return this;
        }

        @Override
        public String getId() {
            Object ref = this.id_;
            if (!(ref instanceof String)) {
                ByteString bs = (ByteString)ref;
                String s2 = bs.toStringUtf8();
                this.id_ = s2;
                return s2;
            }
            return (String)ref;
        }

        @Override
        public ByteString getIdBytes() {
            Object ref = this.id_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.id_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        public Builder setId(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.id_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        public Builder clearId() {
            this.id_ = Service.getDefaultInstance().getId();
            this.bitField0_ &= 0xFFFFFFF7;
            this.onChanged();
            return this;
        }

        public Builder setIdBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            Service.checkByteStringIsUtf8(value);
            this.id_ = value;
            this.bitField0_ |= 8;
            this.onChanged();
            return this;
        }

        private void ensureApisIsMutable() {
            if ((this.bitField0_ & 0x10) == 0) {
                this.apis_ = new ArrayList<Api>(this.apis_);
                this.bitField0_ |= 0x10;
            }
        }

        @Override
        public List<Api> getApisList() {
            if (this.apisBuilder_ == null) {
                return Collections.unmodifiableList(this.apis_);
            }
            return this.apisBuilder_.getMessageList();
        }

        @Override
        public int getApisCount() {
            if (this.apisBuilder_ == null) {
                return this.apis_.size();
            }
            return this.apisBuilder_.getCount();
        }

        @Override
        public Api getApis(int index) {
            if (this.apisBuilder_ == null) {
                return this.apis_.get(index);
            }
            return this.apisBuilder_.getMessage(index);
        }

        public Builder setApis(int index, Api value) {
            if (this.apisBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureApisIsMutable();
                this.apis_.set(index, value);
                this.onChanged();
            } else {
                this.apisBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setApis(int index, Api.Builder builderForValue) {
            if (this.apisBuilder_ == null) {
                this.ensureApisIsMutable();
                this.apis_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.apisBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addApis(Api value) {
            if (this.apisBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureApisIsMutable();
                this.apis_.add(value);
                this.onChanged();
            } else {
                this.apisBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addApis(int index, Api value) {
            if (this.apisBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureApisIsMutable();
                this.apis_.add(index, value);
                this.onChanged();
            } else {
                this.apisBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addApis(Api.Builder builderForValue) {
            if (this.apisBuilder_ == null) {
                this.ensureApisIsMutable();
                this.apis_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.apisBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addApis(int index, Api.Builder builderForValue) {
            if (this.apisBuilder_ == null) {
                this.ensureApisIsMutable();
                this.apis_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.apisBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllApis(Iterable<? extends Api> values2) {
            if (this.apisBuilder_ == null) {
                this.ensureApisIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.apis_);
                this.onChanged();
            } else {
                this.apisBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearApis() {
            if (this.apisBuilder_ == null) {
                this.apis_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFEF;
                this.onChanged();
            } else {
                this.apisBuilder_.clear();
            }
            return this;
        }

        public Builder removeApis(int index) {
            if (this.apisBuilder_ == null) {
                this.ensureApisIsMutable();
                this.apis_.remove(index);
                this.onChanged();
            } else {
                this.apisBuilder_.remove(index);
            }
            return this;
        }

        public Api.Builder getApisBuilder(int index) {
            return this.getApisFieldBuilder().getBuilder(index);
        }

        @Override
        public ApiOrBuilder getApisOrBuilder(int index) {
            if (this.apisBuilder_ == null) {
                return this.apis_.get(index);
            }
            return this.apisBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends ApiOrBuilder> getApisOrBuilderList() {
            if (this.apisBuilder_ != null) {
                return this.apisBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.apis_);
        }

        public Api.Builder addApisBuilder() {
            return this.getApisFieldBuilder().addBuilder(Api.getDefaultInstance());
        }

        public Api.Builder addApisBuilder(int index) {
            return this.getApisFieldBuilder().addBuilder(index, Api.getDefaultInstance());
        }

        public List<Api.Builder> getApisBuilderList() {
            return this.getApisFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Api, Api.Builder, ApiOrBuilder> getApisFieldBuilder() {
            if (this.apisBuilder_ == null) {
                this.apisBuilder_ = new RepeatedFieldBuilderV3(this.apis_, (this.bitField0_ & 0x10) != 0, this.getParentForChildren(), this.isClean());
                this.apis_ = null;
            }
            return this.apisBuilder_;
        }

        private void ensureTypesIsMutable() {
            if ((this.bitField0_ & 0x20) == 0) {
                this.types_ = new ArrayList<Type>(this.types_);
                this.bitField0_ |= 0x20;
            }
        }

        @Override
        public List<Type> getTypesList() {
            if (this.typesBuilder_ == null) {
                return Collections.unmodifiableList(this.types_);
            }
            return this.typesBuilder_.getMessageList();
        }

        @Override
        public int getTypesCount() {
            if (this.typesBuilder_ == null) {
                return this.types_.size();
            }
            return this.typesBuilder_.getCount();
        }

        @Override
        public Type getTypes(int index) {
            if (this.typesBuilder_ == null) {
                return this.types_.get(index);
            }
            return this.typesBuilder_.getMessage(index);
        }

        public Builder setTypes(int index, Type value) {
            if (this.typesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureTypesIsMutable();
                this.types_.set(index, value);
                this.onChanged();
            } else {
                this.typesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setTypes(int index, Type.Builder builderForValue) {
            if (this.typesBuilder_ == null) {
                this.ensureTypesIsMutable();
                this.types_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.typesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addTypes(Type value) {
            if (this.typesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureTypesIsMutable();
                this.types_.add(value);
                this.onChanged();
            } else {
                this.typesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addTypes(int index, Type value) {
            if (this.typesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureTypesIsMutable();
                this.types_.add(index, value);
                this.onChanged();
            } else {
                this.typesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addTypes(Type.Builder builderForValue) {
            if (this.typesBuilder_ == null) {
                this.ensureTypesIsMutable();
                this.types_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.typesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addTypes(int index, Type.Builder builderForValue) {
            if (this.typesBuilder_ == null) {
                this.ensureTypesIsMutable();
                this.types_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.typesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllTypes(Iterable<? extends Type> values2) {
            if (this.typesBuilder_ == null) {
                this.ensureTypesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.types_);
                this.onChanged();
            } else {
                this.typesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearTypes() {
            if (this.typesBuilder_ == null) {
                this.types_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
            } else {
                this.typesBuilder_.clear();
            }
            return this;
        }

        public Builder removeTypes(int index) {
            if (this.typesBuilder_ == null) {
                this.ensureTypesIsMutable();
                this.types_.remove(index);
                this.onChanged();
            } else {
                this.typesBuilder_.remove(index);
            }
            return this;
        }

        public Type.Builder getTypesBuilder(int index) {
            return this.getTypesFieldBuilder().getBuilder(index);
        }

        @Override
        public TypeOrBuilder getTypesOrBuilder(int index) {
            if (this.typesBuilder_ == null) {
                return this.types_.get(index);
            }
            return this.typesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends TypeOrBuilder> getTypesOrBuilderList() {
            if (this.typesBuilder_ != null) {
                return this.typesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.types_);
        }

        public Type.Builder addTypesBuilder() {
            return this.getTypesFieldBuilder().addBuilder(Type.getDefaultInstance());
        }

        public Type.Builder addTypesBuilder(int index) {
            return this.getTypesFieldBuilder().addBuilder(index, Type.getDefaultInstance());
        }

        public List<Type.Builder> getTypesBuilderList() {
            return this.getTypesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Type, Type.Builder, TypeOrBuilder> getTypesFieldBuilder() {
            if (this.typesBuilder_ == null) {
                this.typesBuilder_ = new RepeatedFieldBuilderV3(this.types_, (this.bitField0_ & 0x20) != 0, this.getParentForChildren(), this.isClean());
                this.types_ = null;
            }
            return this.typesBuilder_;
        }

        private void ensureEnumsIsMutable() {
            if ((this.bitField0_ & 0x40) == 0) {
                this.enums_ = new ArrayList<Enum>(this.enums_);
                this.bitField0_ |= 0x40;
            }
        }

        @Override
        public List<Enum> getEnumsList() {
            if (this.enumsBuilder_ == null) {
                return Collections.unmodifiableList(this.enums_);
            }
            return this.enumsBuilder_.getMessageList();
        }

        @Override
        public int getEnumsCount() {
            if (this.enumsBuilder_ == null) {
                return this.enums_.size();
            }
            return this.enumsBuilder_.getCount();
        }

        @Override
        public Enum getEnums(int index) {
            if (this.enumsBuilder_ == null) {
                return this.enums_.get(index);
            }
            return this.enumsBuilder_.getMessage(index);
        }

        public Builder setEnums(int index, Enum value) {
            if (this.enumsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEnumsIsMutable();
                this.enums_.set(index, value);
                this.onChanged();
            } else {
                this.enumsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setEnums(int index, Enum.Builder builderForValue) {
            if (this.enumsBuilder_ == null) {
                this.ensureEnumsIsMutable();
                this.enums_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.enumsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addEnums(Enum value) {
            if (this.enumsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEnumsIsMutable();
                this.enums_.add(value);
                this.onChanged();
            } else {
                this.enumsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addEnums(int index, Enum value) {
            if (this.enumsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEnumsIsMutable();
                this.enums_.add(index, value);
                this.onChanged();
            } else {
                this.enumsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addEnums(Enum.Builder builderForValue) {
            if (this.enumsBuilder_ == null) {
                this.ensureEnumsIsMutable();
                this.enums_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.enumsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addEnums(int index, Enum.Builder builderForValue) {
            if (this.enumsBuilder_ == null) {
                this.ensureEnumsIsMutable();
                this.enums_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.enumsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllEnums(Iterable<? extends Enum> values2) {
            if (this.enumsBuilder_ == null) {
                this.ensureEnumsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.enums_);
                this.onChanged();
            } else {
                this.enumsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearEnums() {
            if (this.enumsBuilder_ == null) {
                this.enums_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFBF;
                this.onChanged();
            } else {
                this.enumsBuilder_.clear();
            }
            return this;
        }

        public Builder removeEnums(int index) {
            if (this.enumsBuilder_ == null) {
                this.ensureEnumsIsMutable();
                this.enums_.remove(index);
                this.onChanged();
            } else {
                this.enumsBuilder_.remove(index);
            }
            return this;
        }

        public Enum.Builder getEnumsBuilder(int index) {
            return this.getEnumsFieldBuilder().getBuilder(index);
        }

        @Override
        public EnumOrBuilder getEnumsOrBuilder(int index) {
            if (this.enumsBuilder_ == null) {
                return this.enums_.get(index);
            }
            return this.enumsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends EnumOrBuilder> getEnumsOrBuilderList() {
            if (this.enumsBuilder_ != null) {
                return this.enumsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.enums_);
        }

        public Enum.Builder addEnumsBuilder() {
            return this.getEnumsFieldBuilder().addBuilder(Enum.getDefaultInstance());
        }

        public Enum.Builder addEnumsBuilder(int index) {
            return this.getEnumsFieldBuilder().addBuilder(index, Enum.getDefaultInstance());
        }

        public List<Enum.Builder> getEnumsBuilderList() {
            return this.getEnumsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Enum, Enum.Builder, EnumOrBuilder> getEnumsFieldBuilder() {
            if (this.enumsBuilder_ == null) {
                this.enumsBuilder_ = new RepeatedFieldBuilderV3(this.enums_, (this.bitField0_ & 0x40) != 0, this.getParentForChildren(), this.isClean());
                this.enums_ = null;
            }
            return this.enumsBuilder_;
        }

        @Override
        public boolean hasDocumentation() {
            return (this.bitField0_ & 0x80) != 0;
        }

        @Override
        public Documentation getDocumentation() {
            if (this.documentationBuilder_ == null) {
                return this.documentation_ == null ? Documentation.getDefaultInstance() : this.documentation_;
            }
            return this.documentationBuilder_.getMessage();
        }

        public Builder setDocumentation(Documentation value) {
            if (this.documentationBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.documentation_ = value;
            } else {
                this.documentationBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder setDocumentation(Documentation.Builder builderForValue) {
            if (this.documentationBuilder_ == null) {
                this.documentation_ = builderForValue.build();
            } else {
                this.documentationBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder mergeDocumentation(Documentation value) {
            if (this.documentationBuilder_ == null) {
                if ((this.bitField0_ & 0x80) != 0 && this.documentation_ != null && this.documentation_ != Documentation.getDefaultInstance()) {
                    this.getDocumentationBuilder().mergeFrom(value);
                } else {
                    this.documentation_ = value;
                }
            } else {
                this.documentationBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this;
        }

        public Builder clearDocumentation() {
            this.bitField0_ &= 0xFFFFFF7F;
            this.documentation_ = null;
            if (this.documentationBuilder_ != null) {
                this.documentationBuilder_.dispose();
                this.documentationBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Documentation.Builder getDocumentationBuilder() {
            this.bitField0_ |= 0x80;
            this.onChanged();
            return this.getDocumentationFieldBuilder().getBuilder();
        }

        @Override
        public DocumentationOrBuilder getDocumentationOrBuilder() {
            if (this.documentationBuilder_ != null) {
                return this.documentationBuilder_.getMessageOrBuilder();
            }
            return this.documentation_ == null ? Documentation.getDefaultInstance() : this.documentation_;
        }

        private SingleFieldBuilderV3<Documentation, Documentation.Builder, DocumentationOrBuilder> getDocumentationFieldBuilder() {
            if (this.documentationBuilder_ == null) {
                this.documentationBuilder_ = new SingleFieldBuilderV3(this.getDocumentation(), this.getParentForChildren(), this.isClean());
                this.documentation_ = null;
            }
            return this.documentationBuilder_;
        }

        @Override
        public boolean hasBackend() {
            return (this.bitField0_ & 0x100) != 0;
        }

        @Override
        public Backend getBackend() {
            if (this.backendBuilder_ == null) {
                return this.backend_ == null ? Backend.getDefaultInstance() : this.backend_;
            }
            return this.backendBuilder_.getMessage();
        }

        public Builder setBackend(Backend value) {
            if (this.backendBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.backend_ = value;
            } else {
                this.backendBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder setBackend(Backend.Builder builderForValue) {
            if (this.backendBuilder_ == null) {
                this.backend_ = builderForValue.build();
            } else {
                this.backendBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder mergeBackend(Backend value) {
            if (this.backendBuilder_ == null) {
                if ((this.bitField0_ & 0x100) != 0 && this.backend_ != null && this.backend_ != Backend.getDefaultInstance()) {
                    this.getBackendBuilder().mergeFrom(value);
                } else {
                    this.backend_ = value;
                }
            } else {
                this.backendBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this;
        }

        public Builder clearBackend() {
            this.bitField0_ &= 0xFFFFFEFF;
            this.backend_ = null;
            if (this.backendBuilder_ != null) {
                this.backendBuilder_.dispose();
                this.backendBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Backend.Builder getBackendBuilder() {
            this.bitField0_ |= 0x100;
            this.onChanged();
            return this.getBackendFieldBuilder().getBuilder();
        }

        @Override
        public BackendOrBuilder getBackendOrBuilder() {
            if (this.backendBuilder_ != null) {
                return this.backendBuilder_.getMessageOrBuilder();
            }
            return this.backend_ == null ? Backend.getDefaultInstance() : this.backend_;
        }

        private SingleFieldBuilderV3<Backend, Backend.Builder, BackendOrBuilder> getBackendFieldBuilder() {
            if (this.backendBuilder_ == null) {
                this.backendBuilder_ = new SingleFieldBuilderV3(this.getBackend(), this.getParentForChildren(), this.isClean());
                this.backend_ = null;
            }
            return this.backendBuilder_;
        }

        @Override
        public boolean hasHttp() {
            return (this.bitField0_ & 0x200) != 0;
        }

        @Override
        public Http getHttp() {
            if (this.httpBuilder_ == null) {
                return this.http_ == null ? Http.getDefaultInstance() : this.http_;
            }
            return this.httpBuilder_.getMessage();
        }

        public Builder setHttp(Http value) {
            if (this.httpBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.http_ = value;
            } else {
                this.httpBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder setHttp(Http.Builder builderForValue) {
            if (this.httpBuilder_ == null) {
                this.http_ = builderForValue.build();
            } else {
                this.httpBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder mergeHttp(Http value) {
            if (this.httpBuilder_ == null) {
                if ((this.bitField0_ & 0x200) != 0 && this.http_ != null && this.http_ != Http.getDefaultInstance()) {
                    this.getHttpBuilder().mergeFrom(value);
                } else {
                    this.http_ = value;
                }
            } else {
                this.httpBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this;
        }

        public Builder clearHttp() {
            this.bitField0_ &= 0xFFFFFDFF;
            this.http_ = null;
            if (this.httpBuilder_ != null) {
                this.httpBuilder_.dispose();
                this.httpBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Http.Builder getHttpBuilder() {
            this.bitField0_ |= 0x200;
            this.onChanged();
            return this.getHttpFieldBuilder().getBuilder();
        }

        @Override
        public HttpOrBuilder getHttpOrBuilder() {
            if (this.httpBuilder_ != null) {
                return this.httpBuilder_.getMessageOrBuilder();
            }
            return this.http_ == null ? Http.getDefaultInstance() : this.http_;
        }

        private SingleFieldBuilderV3<Http, Http.Builder, HttpOrBuilder> getHttpFieldBuilder() {
            if (this.httpBuilder_ == null) {
                this.httpBuilder_ = new SingleFieldBuilderV3(this.getHttp(), this.getParentForChildren(), this.isClean());
                this.http_ = null;
            }
            return this.httpBuilder_;
        }

        @Override
        public boolean hasQuota() {
            return (this.bitField0_ & 0x400) != 0;
        }

        @Override
        public Quota getQuota() {
            if (this.quotaBuilder_ == null) {
                return this.quota_ == null ? Quota.getDefaultInstance() : this.quota_;
            }
            return this.quotaBuilder_.getMessage();
        }

        public Builder setQuota(Quota value) {
            if (this.quotaBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.quota_ = value;
            } else {
                this.quotaBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder setQuota(Quota.Builder builderForValue) {
            if (this.quotaBuilder_ == null) {
                this.quota_ = builderForValue.build();
            } else {
                this.quotaBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder mergeQuota(Quota value) {
            if (this.quotaBuilder_ == null) {
                if ((this.bitField0_ & 0x400) != 0 && this.quota_ != null && this.quota_ != Quota.getDefaultInstance()) {
                    this.getQuotaBuilder().mergeFrom(value);
                } else {
                    this.quota_ = value;
                }
            } else {
                this.quotaBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this;
        }

        public Builder clearQuota() {
            this.bitField0_ &= 0xFFFFFBFF;
            this.quota_ = null;
            if (this.quotaBuilder_ != null) {
                this.quotaBuilder_.dispose();
                this.quotaBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Quota.Builder getQuotaBuilder() {
            this.bitField0_ |= 0x400;
            this.onChanged();
            return this.getQuotaFieldBuilder().getBuilder();
        }

        @Override
        public QuotaOrBuilder getQuotaOrBuilder() {
            if (this.quotaBuilder_ != null) {
                return this.quotaBuilder_.getMessageOrBuilder();
            }
            return this.quota_ == null ? Quota.getDefaultInstance() : this.quota_;
        }

        private SingleFieldBuilderV3<Quota, Quota.Builder, QuotaOrBuilder> getQuotaFieldBuilder() {
            if (this.quotaBuilder_ == null) {
                this.quotaBuilder_ = new SingleFieldBuilderV3(this.getQuota(), this.getParentForChildren(), this.isClean());
                this.quota_ = null;
            }
            return this.quotaBuilder_;
        }

        @Override
        public boolean hasAuthentication() {
            return (this.bitField0_ & 0x800) != 0;
        }

        @Override
        public Authentication getAuthentication() {
            if (this.authenticationBuilder_ == null) {
                return this.authentication_ == null ? Authentication.getDefaultInstance() : this.authentication_;
            }
            return this.authenticationBuilder_.getMessage();
        }

        public Builder setAuthentication(Authentication value) {
            if (this.authenticationBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.authentication_ = value;
            } else {
                this.authenticationBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder setAuthentication(Authentication.Builder builderForValue) {
            if (this.authenticationBuilder_ == null) {
                this.authentication_ = builderForValue.build();
            } else {
                this.authenticationBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder mergeAuthentication(Authentication value) {
            if (this.authenticationBuilder_ == null) {
                if ((this.bitField0_ & 0x800) != 0 && this.authentication_ != null && this.authentication_ != Authentication.getDefaultInstance()) {
                    this.getAuthenticationBuilder().mergeFrom(value);
                } else {
                    this.authentication_ = value;
                }
            } else {
                this.authenticationBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this;
        }

        public Builder clearAuthentication() {
            this.bitField0_ &= 0xFFFFF7FF;
            this.authentication_ = null;
            if (this.authenticationBuilder_ != null) {
                this.authenticationBuilder_.dispose();
                this.authenticationBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Authentication.Builder getAuthenticationBuilder() {
            this.bitField0_ |= 0x800;
            this.onChanged();
            return this.getAuthenticationFieldBuilder().getBuilder();
        }

        @Override
        public AuthenticationOrBuilder getAuthenticationOrBuilder() {
            if (this.authenticationBuilder_ != null) {
                return this.authenticationBuilder_.getMessageOrBuilder();
            }
            return this.authentication_ == null ? Authentication.getDefaultInstance() : this.authentication_;
        }

        private SingleFieldBuilderV3<Authentication, Authentication.Builder, AuthenticationOrBuilder> getAuthenticationFieldBuilder() {
            if (this.authenticationBuilder_ == null) {
                this.authenticationBuilder_ = new SingleFieldBuilderV3(this.getAuthentication(), this.getParentForChildren(), this.isClean());
                this.authentication_ = null;
            }
            return this.authenticationBuilder_;
        }

        @Override
        public boolean hasContext() {
            return (this.bitField0_ & 0x1000) != 0;
        }

        @Override
        public Context getContext() {
            if (this.contextBuilder_ == null) {
                return this.context_ == null ? Context.getDefaultInstance() : this.context_;
            }
            return this.contextBuilder_.getMessage();
        }

        public Builder setContext(Context value) {
            if (this.contextBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.context_ = value;
            } else {
                this.contextBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder setContext(Context.Builder builderForValue) {
            if (this.contextBuilder_ == null) {
                this.context_ = builderForValue.build();
            } else {
                this.contextBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder mergeContext(Context value) {
            if (this.contextBuilder_ == null) {
                if ((this.bitField0_ & 0x1000) != 0 && this.context_ != null && this.context_ != Context.getDefaultInstance()) {
                    this.getContextBuilder().mergeFrom(value);
                } else {
                    this.context_ = value;
                }
            } else {
                this.contextBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this;
        }

        public Builder clearContext() {
            this.bitField0_ &= 0xFFFFEFFF;
            this.context_ = null;
            if (this.contextBuilder_ != null) {
                this.contextBuilder_.dispose();
                this.contextBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Context.Builder getContextBuilder() {
            this.bitField0_ |= 0x1000;
            this.onChanged();
            return this.getContextFieldBuilder().getBuilder();
        }

        @Override
        public ContextOrBuilder getContextOrBuilder() {
            if (this.contextBuilder_ != null) {
                return this.contextBuilder_.getMessageOrBuilder();
            }
            return this.context_ == null ? Context.getDefaultInstance() : this.context_;
        }

        private SingleFieldBuilderV3<Context, Context.Builder, ContextOrBuilder> getContextFieldBuilder() {
            if (this.contextBuilder_ == null) {
                this.contextBuilder_ = new SingleFieldBuilderV3(this.getContext(), this.getParentForChildren(), this.isClean());
                this.context_ = null;
            }
            return this.contextBuilder_;
        }

        @Override
        public boolean hasUsage() {
            return (this.bitField0_ & 0x2000) != 0;
        }

        @Override
        public Usage getUsage() {
            if (this.usageBuilder_ == null) {
                return this.usage_ == null ? Usage.getDefaultInstance() : this.usage_;
            }
            return this.usageBuilder_.getMessage();
        }

        public Builder setUsage(Usage value) {
            if (this.usageBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.usage_ = value;
            } else {
                this.usageBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder setUsage(Usage.Builder builderForValue) {
            if (this.usageBuilder_ == null) {
                this.usage_ = builderForValue.build();
            } else {
                this.usageBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder mergeUsage(Usage value) {
            if (this.usageBuilder_ == null) {
                if ((this.bitField0_ & 0x2000) != 0 && this.usage_ != null && this.usage_ != Usage.getDefaultInstance()) {
                    this.getUsageBuilder().mergeFrom(value);
                } else {
                    this.usage_ = value;
                }
            } else {
                this.usageBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this;
        }

        public Builder clearUsage() {
            this.bitField0_ &= 0xFFFFDFFF;
            this.usage_ = null;
            if (this.usageBuilder_ != null) {
                this.usageBuilder_.dispose();
                this.usageBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Usage.Builder getUsageBuilder() {
            this.bitField0_ |= 0x2000;
            this.onChanged();
            return this.getUsageFieldBuilder().getBuilder();
        }

        @Override
        public UsageOrBuilder getUsageOrBuilder() {
            if (this.usageBuilder_ != null) {
                return this.usageBuilder_.getMessageOrBuilder();
            }
            return this.usage_ == null ? Usage.getDefaultInstance() : this.usage_;
        }

        private SingleFieldBuilderV3<Usage, Usage.Builder, UsageOrBuilder> getUsageFieldBuilder() {
            if (this.usageBuilder_ == null) {
                this.usageBuilder_ = new SingleFieldBuilderV3(this.getUsage(), this.getParentForChildren(), this.isClean());
                this.usage_ = null;
            }
            return this.usageBuilder_;
        }

        private void ensureEndpointsIsMutable() {
            if ((this.bitField0_ & 0x4000) == 0) {
                this.endpoints_ = new ArrayList<Endpoint>(this.endpoints_);
                this.bitField0_ |= 0x4000;
            }
        }

        @Override
        public List<Endpoint> getEndpointsList() {
            if (this.endpointsBuilder_ == null) {
                return Collections.unmodifiableList(this.endpoints_);
            }
            return this.endpointsBuilder_.getMessageList();
        }

        @Override
        public int getEndpointsCount() {
            if (this.endpointsBuilder_ == null) {
                return this.endpoints_.size();
            }
            return this.endpointsBuilder_.getCount();
        }

        @Override
        public Endpoint getEndpoints(int index) {
            if (this.endpointsBuilder_ == null) {
                return this.endpoints_.get(index);
            }
            return this.endpointsBuilder_.getMessage(index);
        }

        public Builder setEndpoints(int index, Endpoint value) {
            if (this.endpointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEndpointsIsMutable();
                this.endpoints_.set(index, value);
                this.onChanged();
            } else {
                this.endpointsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setEndpoints(int index, Endpoint.Builder builderForValue) {
            if (this.endpointsBuilder_ == null) {
                this.ensureEndpointsIsMutable();
                this.endpoints_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.endpointsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addEndpoints(Endpoint value) {
            if (this.endpointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEndpointsIsMutable();
                this.endpoints_.add(value);
                this.onChanged();
            } else {
                this.endpointsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addEndpoints(int index, Endpoint value) {
            if (this.endpointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureEndpointsIsMutable();
                this.endpoints_.add(index, value);
                this.onChanged();
            } else {
                this.endpointsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addEndpoints(Endpoint.Builder builderForValue) {
            if (this.endpointsBuilder_ == null) {
                this.ensureEndpointsIsMutable();
                this.endpoints_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.endpointsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addEndpoints(int index, Endpoint.Builder builderForValue) {
            if (this.endpointsBuilder_ == null) {
                this.ensureEndpointsIsMutable();
                this.endpoints_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.endpointsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllEndpoints(Iterable<? extends Endpoint> values2) {
            if (this.endpointsBuilder_ == null) {
                this.ensureEndpointsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.endpoints_);
                this.onChanged();
            } else {
                this.endpointsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearEndpoints() {
            if (this.endpointsBuilder_ == null) {
                this.endpoints_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFBFFF;
                this.onChanged();
            } else {
                this.endpointsBuilder_.clear();
            }
            return this;
        }

        public Builder removeEndpoints(int index) {
            if (this.endpointsBuilder_ == null) {
                this.ensureEndpointsIsMutable();
                this.endpoints_.remove(index);
                this.onChanged();
            } else {
                this.endpointsBuilder_.remove(index);
            }
            return this;
        }

        public Endpoint.Builder getEndpointsBuilder(int index) {
            return this.getEndpointsFieldBuilder().getBuilder(index);
        }

        @Override
        public EndpointOrBuilder getEndpointsOrBuilder(int index) {
            if (this.endpointsBuilder_ == null) {
                return this.endpoints_.get(index);
            }
            return this.endpointsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends EndpointOrBuilder> getEndpointsOrBuilderList() {
            if (this.endpointsBuilder_ != null) {
                return this.endpointsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.endpoints_);
        }

        public Endpoint.Builder addEndpointsBuilder() {
            return this.getEndpointsFieldBuilder().addBuilder(Endpoint.getDefaultInstance());
        }

        public Endpoint.Builder addEndpointsBuilder(int index) {
            return this.getEndpointsFieldBuilder().addBuilder(index, Endpoint.getDefaultInstance());
        }

        public List<Endpoint.Builder> getEndpointsBuilderList() {
            return this.getEndpointsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Endpoint, Endpoint.Builder, EndpointOrBuilder> getEndpointsFieldBuilder() {
            if (this.endpointsBuilder_ == null) {
                this.endpointsBuilder_ = new RepeatedFieldBuilderV3(this.endpoints_, (this.bitField0_ & 0x4000) != 0, this.getParentForChildren(), this.isClean());
                this.endpoints_ = null;
            }
            return this.endpointsBuilder_;
        }

        @Override
        public boolean hasControl() {
            return (this.bitField0_ & 0x8000) != 0;
        }

        @Override
        public Control getControl() {
            if (this.controlBuilder_ == null) {
                return this.control_ == null ? Control.getDefaultInstance() : this.control_;
            }
            return this.controlBuilder_.getMessage();
        }

        public Builder setControl(Control value) {
            if (this.controlBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.control_ = value;
            } else {
                this.controlBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x8000;
            this.onChanged();
            return this;
        }

        public Builder setControl(Control.Builder builderForValue) {
            if (this.controlBuilder_ == null) {
                this.control_ = builderForValue.build();
            } else {
                this.controlBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x8000;
            this.onChanged();
            return this;
        }

        public Builder mergeControl(Control value) {
            if (this.controlBuilder_ == null) {
                if ((this.bitField0_ & 0x8000) != 0 && this.control_ != null && this.control_ != Control.getDefaultInstance()) {
                    this.getControlBuilder().mergeFrom(value);
                } else {
                    this.control_ = value;
                }
            } else {
                this.controlBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x8000;
            this.onChanged();
            return this;
        }

        public Builder clearControl() {
            this.bitField0_ &= 0xFFFF7FFF;
            this.control_ = null;
            if (this.controlBuilder_ != null) {
                this.controlBuilder_.dispose();
                this.controlBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Control.Builder getControlBuilder() {
            this.bitField0_ |= 0x8000;
            this.onChanged();
            return this.getControlFieldBuilder().getBuilder();
        }

        @Override
        public ControlOrBuilder getControlOrBuilder() {
            if (this.controlBuilder_ != null) {
                return this.controlBuilder_.getMessageOrBuilder();
            }
            return this.control_ == null ? Control.getDefaultInstance() : this.control_;
        }

        private SingleFieldBuilderV3<Control, Control.Builder, ControlOrBuilder> getControlFieldBuilder() {
            if (this.controlBuilder_ == null) {
                this.controlBuilder_ = new SingleFieldBuilderV3(this.getControl(), this.getParentForChildren(), this.isClean());
                this.control_ = null;
            }
            return this.controlBuilder_;
        }

        private void ensureLogsIsMutable() {
            if ((this.bitField0_ & 0x10000) == 0) {
                this.logs_ = new ArrayList<LogDescriptor>(this.logs_);
                this.bitField0_ |= 0x10000;
            }
        }

        @Override
        public List<LogDescriptor> getLogsList() {
            if (this.logsBuilder_ == null) {
                return Collections.unmodifiableList(this.logs_);
            }
            return this.logsBuilder_.getMessageList();
        }

        @Override
        public int getLogsCount() {
            if (this.logsBuilder_ == null) {
                return this.logs_.size();
            }
            return this.logsBuilder_.getCount();
        }

        @Override
        public LogDescriptor getLogs(int index) {
            if (this.logsBuilder_ == null) {
                return this.logs_.get(index);
            }
            return this.logsBuilder_.getMessage(index);
        }

        public Builder setLogs(int index, LogDescriptor value) {
            if (this.logsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLogsIsMutable();
                this.logs_.set(index, value);
                this.onChanged();
            } else {
                this.logsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setLogs(int index, LogDescriptor.Builder builderForValue) {
            if (this.logsBuilder_ == null) {
                this.ensureLogsIsMutable();
                this.logs_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.logsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addLogs(LogDescriptor value) {
            if (this.logsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLogsIsMutable();
                this.logs_.add(value);
                this.onChanged();
            } else {
                this.logsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addLogs(int index, LogDescriptor value) {
            if (this.logsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureLogsIsMutable();
                this.logs_.add(index, value);
                this.onChanged();
            } else {
                this.logsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addLogs(LogDescriptor.Builder builderForValue) {
            if (this.logsBuilder_ == null) {
                this.ensureLogsIsMutable();
                this.logs_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.logsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addLogs(int index, LogDescriptor.Builder builderForValue) {
            if (this.logsBuilder_ == null) {
                this.ensureLogsIsMutable();
                this.logs_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.logsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllLogs(Iterable<? extends LogDescriptor> values2) {
            if (this.logsBuilder_ == null) {
                this.ensureLogsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.logs_);
                this.onChanged();
            } else {
                this.logsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearLogs() {
            if (this.logsBuilder_ == null) {
                this.logs_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFEFFFF;
                this.onChanged();
            } else {
                this.logsBuilder_.clear();
            }
            return this;
        }

        public Builder removeLogs(int index) {
            if (this.logsBuilder_ == null) {
                this.ensureLogsIsMutable();
                this.logs_.remove(index);
                this.onChanged();
            } else {
                this.logsBuilder_.remove(index);
            }
            return this;
        }

        public LogDescriptor.Builder getLogsBuilder(int index) {
            return this.getLogsFieldBuilder().getBuilder(index);
        }

        @Override
        public LogDescriptorOrBuilder getLogsOrBuilder(int index) {
            if (this.logsBuilder_ == null) {
                return this.logs_.get(index);
            }
            return this.logsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends LogDescriptorOrBuilder> getLogsOrBuilderList() {
            if (this.logsBuilder_ != null) {
                return this.logsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.logs_);
        }

        public LogDescriptor.Builder addLogsBuilder() {
            return this.getLogsFieldBuilder().addBuilder(LogDescriptor.getDefaultInstance());
        }

        public LogDescriptor.Builder addLogsBuilder(int index) {
            return this.getLogsFieldBuilder().addBuilder(index, LogDescriptor.getDefaultInstance());
        }

        public List<LogDescriptor.Builder> getLogsBuilderList() {
            return this.getLogsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<LogDescriptor, LogDescriptor.Builder, LogDescriptorOrBuilder> getLogsFieldBuilder() {
            if (this.logsBuilder_ == null) {
                this.logsBuilder_ = new RepeatedFieldBuilderV3(this.logs_, (this.bitField0_ & 0x10000) != 0, this.getParentForChildren(), this.isClean());
                this.logs_ = null;
            }
            return this.logsBuilder_;
        }

        private void ensureMetricsIsMutable() {
            if ((this.bitField0_ & 0x20000) == 0) {
                this.metrics_ = new ArrayList<MetricDescriptor>(this.metrics_);
                this.bitField0_ |= 0x20000;
            }
        }

        @Override
        public List<MetricDescriptor> getMetricsList() {
            if (this.metricsBuilder_ == null) {
                return Collections.unmodifiableList(this.metrics_);
            }
            return this.metricsBuilder_.getMessageList();
        }

        @Override
        public int getMetricsCount() {
            if (this.metricsBuilder_ == null) {
                return this.metrics_.size();
            }
            return this.metricsBuilder_.getCount();
        }

        @Override
        public MetricDescriptor getMetrics(int index) {
            if (this.metricsBuilder_ == null) {
                return this.metrics_.get(index);
            }
            return this.metricsBuilder_.getMessage(index);
        }

        public Builder setMetrics(int index, MetricDescriptor value) {
            if (this.metricsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricsIsMutable();
                this.metrics_.set(index, value);
                this.onChanged();
            } else {
                this.metricsBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setMetrics(int index, MetricDescriptor.Builder builderForValue) {
            if (this.metricsBuilder_ == null) {
                this.ensureMetricsIsMutable();
                this.metrics_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.metricsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMetrics(MetricDescriptor value) {
            if (this.metricsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricsIsMutable();
                this.metrics_.add(value);
                this.onChanged();
            } else {
                this.metricsBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addMetrics(int index, MetricDescriptor value) {
            if (this.metricsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMetricsIsMutable();
                this.metrics_.add(index, value);
                this.onChanged();
            } else {
                this.metricsBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addMetrics(MetricDescriptor.Builder builderForValue) {
            if (this.metricsBuilder_ == null) {
                this.ensureMetricsIsMutable();
                this.metrics_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.metricsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMetrics(int index, MetricDescriptor.Builder builderForValue) {
            if (this.metricsBuilder_ == null) {
                this.ensureMetricsIsMutable();
                this.metrics_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.metricsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMetrics(Iterable<? extends MetricDescriptor> values2) {
            if (this.metricsBuilder_ == null) {
                this.ensureMetricsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.metrics_);
                this.onChanged();
            } else {
                this.metricsBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearMetrics() {
            if (this.metricsBuilder_ == null) {
                this.metrics_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFDFFFF;
                this.onChanged();
            } else {
                this.metricsBuilder_.clear();
            }
            return this;
        }

        public Builder removeMetrics(int index) {
            if (this.metricsBuilder_ == null) {
                this.ensureMetricsIsMutable();
                this.metrics_.remove(index);
                this.onChanged();
            } else {
                this.metricsBuilder_.remove(index);
            }
            return this;
        }

        public MetricDescriptor.Builder getMetricsBuilder(int index) {
            return this.getMetricsFieldBuilder().getBuilder(index);
        }

        @Override
        public MetricDescriptorOrBuilder getMetricsOrBuilder(int index) {
            if (this.metricsBuilder_ == null) {
                return this.metrics_.get(index);
            }
            return this.metricsBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MetricDescriptorOrBuilder> getMetricsOrBuilderList() {
            if (this.metricsBuilder_ != null) {
                return this.metricsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.metrics_);
        }

        public MetricDescriptor.Builder addMetricsBuilder() {
            return this.getMetricsFieldBuilder().addBuilder(MetricDescriptor.getDefaultInstance());
        }

        public MetricDescriptor.Builder addMetricsBuilder(int index) {
            return this.getMetricsFieldBuilder().addBuilder(index, MetricDescriptor.getDefaultInstance());
        }

        public List<MetricDescriptor.Builder> getMetricsBuilderList() {
            return this.getMetricsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MetricDescriptor, MetricDescriptor.Builder, MetricDescriptorOrBuilder> getMetricsFieldBuilder() {
            if (this.metricsBuilder_ == null) {
                this.metricsBuilder_ = new RepeatedFieldBuilderV3(this.metrics_, (this.bitField0_ & 0x20000) != 0, this.getParentForChildren(), this.isClean());
                this.metrics_ = null;
            }
            return this.metricsBuilder_;
        }

        private void ensureMonitoredResourcesIsMutable() {
            if ((this.bitField0_ & 0x40000) == 0) {
                this.monitoredResources_ = new ArrayList<MonitoredResourceDescriptor>(this.monitoredResources_);
                this.bitField0_ |= 0x40000;
            }
        }

        @Override
        public List<MonitoredResourceDescriptor> getMonitoredResourcesList() {
            if (this.monitoredResourcesBuilder_ == null) {
                return Collections.unmodifiableList(this.monitoredResources_);
            }
            return this.monitoredResourcesBuilder_.getMessageList();
        }

        @Override
        public int getMonitoredResourcesCount() {
            if (this.monitoredResourcesBuilder_ == null) {
                return this.monitoredResources_.size();
            }
            return this.monitoredResourcesBuilder_.getCount();
        }

        @Override
        public MonitoredResourceDescriptor getMonitoredResources(int index) {
            if (this.monitoredResourcesBuilder_ == null) {
                return this.monitoredResources_.get(index);
            }
            return this.monitoredResourcesBuilder_.getMessage(index);
        }

        public Builder setMonitoredResources(int index, MonitoredResourceDescriptor value) {
            if (this.monitoredResourcesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.set(index, value);
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.setMessage(index, value);
            }
            return this;
        }

        public Builder setMonitoredResources(int index, MonitoredResourceDescriptor.Builder builderForValue) {
            if (this.monitoredResourcesBuilder_ == null) {
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.set(index, builderForValue.build());
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMonitoredResources(MonitoredResourceDescriptor value) {
            if (this.monitoredResourcesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.add(value);
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.addMessage(value);
            }
            return this;
        }

        public Builder addMonitoredResources(int index, MonitoredResourceDescriptor value) {
            if (this.monitoredResourcesBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.add(index, value);
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.addMessage(index, value);
            }
            return this;
        }

        public Builder addMonitoredResources(MonitoredResourceDescriptor.Builder builderForValue) {
            if (this.monitoredResourcesBuilder_ == null) {
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.add(builderForValue.build());
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMonitoredResources(int index, MonitoredResourceDescriptor.Builder builderForValue) {
            if (this.monitoredResourcesBuilder_ == null) {
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.add(index, builderForValue.build());
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMonitoredResources(Iterable<? extends MonitoredResourceDescriptor> values2) {
            if (this.monitoredResourcesBuilder_ == null) {
                this.ensureMonitoredResourcesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.monitoredResources_);
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.addAllMessages(values2);
            }
            return this;
        }

        public Builder clearMonitoredResources() {
            if (this.monitoredResourcesBuilder_ == null) {
                this.monitoredResources_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFBFFFF;
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.clear();
            }
            return this;
        }

        public Builder removeMonitoredResources(int index) {
            if (this.monitoredResourcesBuilder_ == null) {
                this.ensureMonitoredResourcesIsMutable();
                this.monitoredResources_.remove(index);
                this.onChanged();
            } else {
                this.monitoredResourcesBuilder_.remove(index);
            }
            return this;
        }

        public MonitoredResourceDescriptor.Builder getMonitoredResourcesBuilder(int index) {
            return this.getMonitoredResourcesFieldBuilder().getBuilder(index);
        }

        @Override
        public MonitoredResourceDescriptorOrBuilder getMonitoredResourcesOrBuilder(int index) {
            if (this.monitoredResourcesBuilder_ == null) {
                return this.monitoredResources_.get(index);
            }
            return this.monitoredResourcesBuilder_.getMessageOrBuilder(index);
        }

        @Override
        public List<? extends MonitoredResourceDescriptorOrBuilder> getMonitoredResourcesOrBuilderList() {
            if (this.monitoredResourcesBuilder_ != null) {
                return this.monitoredResourcesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.monitoredResources_);
        }

        public MonitoredResourceDescriptor.Builder addMonitoredResourcesBuilder() {
            return this.getMonitoredResourcesFieldBuilder().addBuilder(MonitoredResourceDescriptor.getDefaultInstance());
        }

        public MonitoredResourceDescriptor.Builder addMonitoredResourcesBuilder(int index) {
            return this.getMonitoredResourcesFieldBuilder().addBuilder(index, MonitoredResourceDescriptor.getDefaultInstance());
        }

        public List<MonitoredResourceDescriptor.Builder> getMonitoredResourcesBuilderList() {
            return this.getMonitoredResourcesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<MonitoredResourceDescriptor, MonitoredResourceDescriptor.Builder, MonitoredResourceDescriptorOrBuilder> getMonitoredResourcesFieldBuilder() {
            if (this.monitoredResourcesBuilder_ == null) {
                this.monitoredResourcesBuilder_ = new RepeatedFieldBuilderV3(this.monitoredResources_, (this.bitField0_ & 0x40000) != 0, this.getParentForChildren(), this.isClean());
                this.monitoredResources_ = null;
            }
            return this.monitoredResourcesBuilder_;
        }

        @Override
        public boolean hasBilling() {
            return (this.bitField0_ & 0x80000) != 0;
        }

        @Override
        public Billing getBilling() {
            if (this.billingBuilder_ == null) {
                return this.billing_ == null ? Billing.getDefaultInstance() : this.billing_;
            }
            return this.billingBuilder_.getMessage();
        }

        public Builder setBilling(Billing value) {
            if (this.billingBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.billing_ = value;
            } else {
                this.billingBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x80000;
            this.onChanged();
            return this;
        }

        public Builder setBilling(Billing.Builder builderForValue) {
            if (this.billingBuilder_ == null) {
                this.billing_ = builderForValue.build();
            } else {
                this.billingBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x80000;
            this.onChanged();
            return this;
        }

        public Builder mergeBilling(Billing value) {
            if (this.billingBuilder_ == null) {
                if ((this.bitField0_ & 0x80000) != 0 && this.billing_ != null && this.billing_ != Billing.getDefaultInstance()) {
                    this.getBillingBuilder().mergeFrom(value);
                } else {
                    this.billing_ = value;
                }
            } else {
                this.billingBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x80000;
            this.onChanged();
            return this;
        }

        public Builder clearBilling() {
            this.bitField0_ &= 0xFFF7FFFF;
            this.billing_ = null;
            if (this.billingBuilder_ != null) {
                this.billingBuilder_.dispose();
                this.billingBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Billing.Builder getBillingBuilder() {
            this.bitField0_ |= 0x80000;
            this.onChanged();
            return this.getBillingFieldBuilder().getBuilder();
        }

        @Override
        public BillingOrBuilder getBillingOrBuilder() {
            if (this.billingBuilder_ != null) {
                return this.billingBuilder_.getMessageOrBuilder();
            }
            return this.billing_ == null ? Billing.getDefaultInstance() : this.billing_;
        }

        private SingleFieldBuilderV3<Billing, Billing.Builder, BillingOrBuilder> getBillingFieldBuilder() {
            if (this.billingBuilder_ == null) {
                this.billingBuilder_ = new SingleFieldBuilderV3(this.getBilling(), this.getParentForChildren(), this.isClean());
                this.billing_ = null;
            }
            return this.billingBuilder_;
        }

        @Override
        public boolean hasLogging() {
            return (this.bitField0_ & 0x100000) != 0;
        }

        @Override
        public Logging getLogging() {
            if (this.loggingBuilder_ == null) {
                return this.logging_ == null ? Logging.getDefaultInstance() : this.logging_;
            }
            return this.loggingBuilder_.getMessage();
        }

        public Builder setLogging(Logging value) {
            if (this.loggingBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.logging_ = value;
            } else {
                this.loggingBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x100000;
            this.onChanged();
            return this;
        }

        public Builder setLogging(Logging.Builder builderForValue) {
            if (this.loggingBuilder_ == null) {
                this.logging_ = builderForValue.build();
            } else {
                this.loggingBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x100000;
            this.onChanged();
            return this;
        }

        public Builder mergeLogging(Logging value) {
            if (this.loggingBuilder_ == null) {
                if ((this.bitField0_ & 0x100000) != 0 && this.logging_ != null && this.logging_ != Logging.getDefaultInstance()) {
                    this.getLoggingBuilder().mergeFrom(value);
                } else {
                    this.logging_ = value;
                }
            } else {
                this.loggingBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x100000;
            this.onChanged();
            return this;
        }

        public Builder clearLogging() {
            this.bitField0_ &= 0xFFEFFFFF;
            this.logging_ = null;
            if (this.loggingBuilder_ != null) {
                this.loggingBuilder_.dispose();
                this.loggingBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Logging.Builder getLoggingBuilder() {
            this.bitField0_ |= 0x100000;
            this.onChanged();
            return this.getLoggingFieldBuilder().getBuilder();
        }

        @Override
        public LoggingOrBuilder getLoggingOrBuilder() {
            if (this.loggingBuilder_ != null) {
                return this.loggingBuilder_.getMessageOrBuilder();
            }
            return this.logging_ == null ? Logging.getDefaultInstance() : this.logging_;
        }

        private SingleFieldBuilderV3<Logging, Logging.Builder, LoggingOrBuilder> getLoggingFieldBuilder() {
            if (this.loggingBuilder_ == null) {
                this.loggingBuilder_ = new SingleFieldBuilderV3(this.getLogging(), this.getParentForChildren(), this.isClean());
                this.logging_ = null;
            }
            return this.loggingBuilder_;
        }

        @Override
        public boolean hasMonitoring() {
            return (this.bitField0_ & 0x200000) != 0;
        }

        @Override
        public Monitoring getMonitoring() {
            if (this.monitoringBuilder_ == null) {
                return this.monitoring_ == null ? Monitoring.getDefaultInstance() : this.monitoring_;
            }
            return this.monitoringBuilder_.getMessage();
        }

        public Builder setMonitoring(Monitoring value) {
            if (this.monitoringBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.monitoring_ = value;
            } else {
                this.monitoringBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x200000;
            this.onChanged();
            return this;
        }

        public Builder setMonitoring(Monitoring.Builder builderForValue) {
            if (this.monitoringBuilder_ == null) {
                this.monitoring_ = builderForValue.build();
            } else {
                this.monitoringBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x200000;
            this.onChanged();
            return this;
        }

        public Builder mergeMonitoring(Monitoring value) {
            if (this.monitoringBuilder_ == null) {
                if ((this.bitField0_ & 0x200000) != 0 && this.monitoring_ != null && this.monitoring_ != Monitoring.getDefaultInstance()) {
                    this.getMonitoringBuilder().mergeFrom(value);
                } else {
                    this.monitoring_ = value;
                }
            } else {
                this.monitoringBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x200000;
            this.onChanged();
            return this;
        }

        public Builder clearMonitoring() {
            this.bitField0_ &= 0xFFDFFFFF;
            this.monitoring_ = null;
            if (this.monitoringBuilder_ != null) {
                this.monitoringBuilder_.dispose();
                this.monitoringBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Monitoring.Builder getMonitoringBuilder() {
            this.bitField0_ |= 0x200000;
            this.onChanged();
            return this.getMonitoringFieldBuilder().getBuilder();
        }

        @Override
        public MonitoringOrBuilder getMonitoringOrBuilder() {
            if (this.monitoringBuilder_ != null) {
                return this.monitoringBuilder_.getMessageOrBuilder();
            }
            return this.monitoring_ == null ? Monitoring.getDefaultInstance() : this.monitoring_;
        }

        private SingleFieldBuilderV3<Monitoring, Monitoring.Builder, MonitoringOrBuilder> getMonitoringFieldBuilder() {
            if (this.monitoringBuilder_ == null) {
                this.monitoringBuilder_ = new SingleFieldBuilderV3(this.getMonitoring(), this.getParentForChildren(), this.isClean());
                this.monitoring_ = null;
            }
            return this.monitoringBuilder_;
        }

        @Override
        public boolean hasSystemParameters() {
            return (this.bitField0_ & 0x400000) != 0;
        }

        @Override
        public SystemParameters getSystemParameters() {
            if (this.systemParametersBuilder_ == null) {
                return this.systemParameters_ == null ? SystemParameters.getDefaultInstance() : this.systemParameters_;
            }
            return this.systemParametersBuilder_.getMessage();
        }

        public Builder setSystemParameters(SystemParameters value) {
            if (this.systemParametersBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.systemParameters_ = value;
            } else {
                this.systemParametersBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x400000;
            this.onChanged();
            return this;
        }

        public Builder setSystemParameters(SystemParameters.Builder builderForValue) {
            if (this.systemParametersBuilder_ == null) {
                this.systemParameters_ = builderForValue.build();
            } else {
                this.systemParametersBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x400000;
            this.onChanged();
            return this;
        }

        public Builder mergeSystemParameters(SystemParameters value) {
            if (this.systemParametersBuilder_ == null) {
                if ((this.bitField0_ & 0x400000) != 0 && this.systemParameters_ != null && this.systemParameters_ != SystemParameters.getDefaultInstance()) {
                    this.getSystemParametersBuilder().mergeFrom(value);
                } else {
                    this.systemParameters_ = value;
                }
            } else {
                this.systemParametersBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x400000;
            this.onChanged();
            return this;
        }

        public Builder clearSystemParameters() {
            this.bitField0_ &= 0xFFBFFFFF;
            this.systemParameters_ = null;
            if (this.systemParametersBuilder_ != null) {
                this.systemParametersBuilder_.dispose();
                this.systemParametersBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public SystemParameters.Builder getSystemParametersBuilder() {
            this.bitField0_ |= 0x400000;
            this.onChanged();
            return this.getSystemParametersFieldBuilder().getBuilder();
        }

        @Override
        public SystemParametersOrBuilder getSystemParametersOrBuilder() {
            if (this.systemParametersBuilder_ != null) {
                return this.systemParametersBuilder_.getMessageOrBuilder();
            }
            return this.systemParameters_ == null ? SystemParameters.getDefaultInstance() : this.systemParameters_;
        }

        private SingleFieldBuilderV3<SystemParameters, SystemParameters.Builder, SystemParametersOrBuilder> getSystemParametersFieldBuilder() {
            if (this.systemParametersBuilder_ == null) {
                this.systemParametersBuilder_ = new SingleFieldBuilderV3(this.getSystemParameters(), this.getParentForChildren(), this.isClean());
                this.systemParameters_ = null;
            }
            return this.systemParametersBuilder_;
        }

        @Override
        public boolean hasSourceInfo() {
            return (this.bitField0_ & 0x800000) != 0;
        }

        @Override
        public SourceInfo getSourceInfo() {
            if (this.sourceInfoBuilder_ == null) {
                return this.sourceInfo_ == null ? SourceInfo.getDefaultInstance() : this.sourceInfo_;
            }
            return this.sourceInfoBuilder_.getMessage();
        }

        public Builder setSourceInfo(SourceInfo value) {
            if (this.sourceInfoBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.sourceInfo_ = value;
            } else {
                this.sourceInfoBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x800000;
            this.onChanged();
            return this;
        }

        public Builder setSourceInfo(SourceInfo.Builder builderForValue) {
            if (this.sourceInfoBuilder_ == null) {
                this.sourceInfo_ = builderForValue.build();
            } else {
                this.sourceInfoBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x800000;
            this.onChanged();
            return this;
        }

        public Builder mergeSourceInfo(SourceInfo value) {
            if (this.sourceInfoBuilder_ == null) {
                if ((this.bitField0_ & 0x800000) != 0 && this.sourceInfo_ != null && this.sourceInfo_ != SourceInfo.getDefaultInstance()) {
                    this.getSourceInfoBuilder().mergeFrom(value);
                } else {
                    this.sourceInfo_ = value;
                }
            } else {
                this.sourceInfoBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x800000;
            this.onChanged();
            return this;
        }

        public Builder clearSourceInfo() {
            this.bitField0_ &= 0xFF7FFFFF;
            this.sourceInfo_ = null;
            if (this.sourceInfoBuilder_ != null) {
                this.sourceInfoBuilder_.dispose();
                this.sourceInfoBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public SourceInfo.Builder getSourceInfoBuilder() {
            this.bitField0_ |= 0x800000;
            this.onChanged();
            return this.getSourceInfoFieldBuilder().getBuilder();
        }

        @Override
        public SourceInfoOrBuilder getSourceInfoOrBuilder() {
            if (this.sourceInfoBuilder_ != null) {
                return this.sourceInfoBuilder_.getMessageOrBuilder();
            }
            return this.sourceInfo_ == null ? SourceInfo.getDefaultInstance() : this.sourceInfo_;
        }

        private SingleFieldBuilderV3<SourceInfo, SourceInfo.Builder, SourceInfoOrBuilder> getSourceInfoFieldBuilder() {
            if (this.sourceInfoBuilder_ == null) {
                this.sourceInfoBuilder_ = new SingleFieldBuilderV3(this.getSourceInfo(), this.getParentForChildren(), this.isClean());
                this.sourceInfo_ = null;
            }
            return this.sourceInfoBuilder_;
        }

        @Override
        public boolean hasPublishing() {
            return (this.bitField0_ & 0x1000000) != 0;
        }

        @Override
        public Publishing getPublishing() {
            if (this.publishingBuilder_ == null) {
                return this.publishing_ == null ? Publishing.getDefaultInstance() : this.publishing_;
            }
            return this.publishingBuilder_.getMessage();
        }

        public Builder setPublishing(Publishing value) {
            if (this.publishingBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.publishing_ = value;
            } else {
                this.publishingBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x1000000;
            this.onChanged();
            return this;
        }

        public Builder setPublishing(Publishing.Builder builderForValue) {
            if (this.publishingBuilder_ == null) {
                this.publishing_ = builderForValue.build();
            } else {
                this.publishingBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x1000000;
            this.onChanged();
            return this;
        }

        public Builder mergePublishing(Publishing value) {
            if (this.publishingBuilder_ == null) {
                if ((this.bitField0_ & 0x1000000) != 0 && this.publishing_ != null && this.publishing_ != Publishing.getDefaultInstance()) {
                    this.getPublishingBuilder().mergeFrom(value);
                } else {
                    this.publishing_ = value;
                }
            } else {
                this.publishingBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x1000000;
            this.onChanged();
            return this;
        }

        public Builder clearPublishing() {
            this.bitField0_ &= 0xFEFFFFFF;
            this.publishing_ = null;
            if (this.publishingBuilder_ != null) {
                this.publishingBuilder_.dispose();
                this.publishingBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public Publishing.Builder getPublishingBuilder() {
            this.bitField0_ |= 0x1000000;
            this.onChanged();
            return this.getPublishingFieldBuilder().getBuilder();
        }

        @Override
        public PublishingOrBuilder getPublishingOrBuilder() {
            if (this.publishingBuilder_ != null) {
                return this.publishingBuilder_.getMessageOrBuilder();
            }
            return this.publishing_ == null ? Publishing.getDefaultInstance() : this.publishing_;
        }

        private SingleFieldBuilderV3<Publishing, Publishing.Builder, PublishingOrBuilder> getPublishingFieldBuilder() {
            if (this.publishingBuilder_ == null) {
                this.publishingBuilder_ = new SingleFieldBuilderV3(this.getPublishing(), this.getParentForChildren(), this.isClean());
                this.publishing_ = null;
            }
            return this.publishingBuilder_;
        }

        @Override
        public boolean hasConfigVersion() {
            return (this.bitField0_ & 0x2000000) != 0;
        }

        @Override
        public UInt32Value getConfigVersion() {
            if (this.configVersionBuilder_ == null) {
                return this.configVersion_ == null ? UInt32Value.getDefaultInstance() : this.configVersion_;
            }
            return this.configVersionBuilder_.getMessage();
        }

        public Builder setConfigVersion(UInt32Value value) {
            if (this.configVersionBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.configVersion_ = value;
            } else {
                this.configVersionBuilder_.setMessage(value);
            }
            this.bitField0_ |= 0x2000000;
            this.onChanged();
            return this;
        }

        public Builder setConfigVersion(UInt32Value.Builder builderForValue) {
            if (this.configVersionBuilder_ == null) {
                this.configVersion_ = builderForValue.build();
            } else {
                this.configVersionBuilder_.setMessage(builderForValue.build());
            }
            this.bitField0_ |= 0x2000000;
            this.onChanged();
            return this;
        }

        public Builder mergeConfigVersion(UInt32Value value) {
            if (this.configVersionBuilder_ == null) {
                if ((this.bitField0_ & 0x2000000) != 0 && this.configVersion_ != null && this.configVersion_ != UInt32Value.getDefaultInstance()) {
                    this.getConfigVersionBuilder().mergeFrom(value);
                } else {
                    this.configVersion_ = value;
                }
            } else {
                this.configVersionBuilder_.mergeFrom(value);
            }
            this.bitField0_ |= 0x2000000;
            this.onChanged();
            return this;
        }

        public Builder clearConfigVersion() {
            this.bitField0_ &= 0xFDFFFFFF;
            this.configVersion_ = null;
            if (this.configVersionBuilder_ != null) {
                this.configVersionBuilder_.dispose();
                this.configVersionBuilder_ = null;
            }
            this.onChanged();
            return this;
        }

        public UInt32Value.Builder getConfigVersionBuilder() {
            this.bitField0_ |= 0x2000000;
            this.onChanged();
            return this.getConfigVersionFieldBuilder().getBuilder();
        }

        @Override
        public UInt32ValueOrBuilder getConfigVersionOrBuilder() {
            if (this.configVersionBuilder_ != null) {
                return this.configVersionBuilder_.getMessageOrBuilder();
            }
            return this.configVersion_ == null ? UInt32Value.getDefaultInstance() : this.configVersion_;
        }

        private SingleFieldBuilderV3<UInt32Value, UInt32Value.Builder, UInt32ValueOrBuilder> getConfigVersionFieldBuilder() {
            if (this.configVersionBuilder_ == null) {
                this.configVersionBuilder_ = new SingleFieldBuilderV3(this.getConfigVersion(), this.getParentForChildren(), this.isClean());
                this.configVersion_ = null;
            }
            return this.configVersionBuilder_;
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

