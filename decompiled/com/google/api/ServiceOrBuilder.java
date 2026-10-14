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
import com.google.api.SourceInfo;
import com.google.api.SourceInfoOrBuilder;
import com.google.api.SystemParameters;
import com.google.api.SystemParametersOrBuilder;
import com.google.api.Usage;
import com.google.api.UsageOrBuilder;
import com.google.protobuf.Api;
import com.google.protobuf.ApiOrBuilder;
import com.google.protobuf.ByteString;
import com.google.protobuf.Enum;
import com.google.protobuf.EnumOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Type;
import com.google.protobuf.TypeOrBuilder;
import com.google.protobuf.UInt32Value;
import com.google.protobuf.UInt32ValueOrBuilder;
import java.util.List;

public interface ServiceOrBuilder
extends MessageOrBuilder {
    public String getName();

    public ByteString getNameBytes();

    public String getTitle();

    public ByteString getTitleBytes();

    public String getProducerProjectId();

    public ByteString getProducerProjectIdBytes();

    public String getId();

    public ByteString getIdBytes();

    public List<Api> getApisList();

    public Api getApis(int var1);

    public int getApisCount();

    public List<? extends ApiOrBuilder> getApisOrBuilderList();

    public ApiOrBuilder getApisOrBuilder(int var1);

    public List<Type> getTypesList();

    public Type getTypes(int var1);

    public int getTypesCount();

    public List<? extends TypeOrBuilder> getTypesOrBuilderList();

    public TypeOrBuilder getTypesOrBuilder(int var1);

    public List<Enum> getEnumsList();

    public Enum getEnums(int var1);

    public int getEnumsCount();

    public List<? extends EnumOrBuilder> getEnumsOrBuilderList();

    public EnumOrBuilder getEnumsOrBuilder(int var1);

    public boolean hasDocumentation();

    public Documentation getDocumentation();

    public DocumentationOrBuilder getDocumentationOrBuilder();

    public boolean hasBackend();

    public Backend getBackend();

    public BackendOrBuilder getBackendOrBuilder();

    public boolean hasHttp();

    public Http getHttp();

    public HttpOrBuilder getHttpOrBuilder();

    public boolean hasQuota();

    public Quota getQuota();

    public QuotaOrBuilder getQuotaOrBuilder();

    public boolean hasAuthentication();

    public Authentication getAuthentication();

    public AuthenticationOrBuilder getAuthenticationOrBuilder();

    public boolean hasContext();

    public Context getContext();

    public ContextOrBuilder getContextOrBuilder();

    public boolean hasUsage();

    public Usage getUsage();

    public UsageOrBuilder getUsageOrBuilder();

    public List<Endpoint> getEndpointsList();

    public Endpoint getEndpoints(int var1);

    public int getEndpointsCount();

    public List<? extends EndpointOrBuilder> getEndpointsOrBuilderList();

    public EndpointOrBuilder getEndpointsOrBuilder(int var1);

    public boolean hasControl();

    public Control getControl();

    public ControlOrBuilder getControlOrBuilder();

    public List<LogDescriptor> getLogsList();

    public LogDescriptor getLogs(int var1);

    public int getLogsCount();

    public List<? extends LogDescriptorOrBuilder> getLogsOrBuilderList();

    public LogDescriptorOrBuilder getLogsOrBuilder(int var1);

    public List<MetricDescriptor> getMetricsList();

    public MetricDescriptor getMetrics(int var1);

    public int getMetricsCount();

    public List<? extends MetricDescriptorOrBuilder> getMetricsOrBuilderList();

    public MetricDescriptorOrBuilder getMetricsOrBuilder(int var1);

    public List<MonitoredResourceDescriptor> getMonitoredResourcesList();

    public MonitoredResourceDescriptor getMonitoredResources(int var1);

    public int getMonitoredResourcesCount();

    public List<? extends MonitoredResourceDescriptorOrBuilder> getMonitoredResourcesOrBuilderList();

    public MonitoredResourceDescriptorOrBuilder getMonitoredResourcesOrBuilder(int var1);

    public boolean hasBilling();

    public Billing getBilling();

    public BillingOrBuilder getBillingOrBuilder();

    public boolean hasLogging();

    public Logging getLogging();

    public LoggingOrBuilder getLoggingOrBuilder();

    public boolean hasMonitoring();

    public Monitoring getMonitoring();

    public MonitoringOrBuilder getMonitoringOrBuilder();

    public boolean hasSystemParameters();

    public SystemParameters getSystemParameters();

    public SystemParametersOrBuilder getSystemParametersOrBuilder();

    public boolean hasSourceInfo();

    public SourceInfo getSourceInfo();

    public SourceInfoOrBuilder getSourceInfoOrBuilder();

    public boolean hasPublishing();

    public Publishing getPublishing();

    public PublishingOrBuilder getPublishingOrBuilder();

    public boolean hasConfigVersion();

    public UInt32Value getConfigVersion();

    public UInt32ValueOrBuilder getConfigVersionOrBuilder();
}

