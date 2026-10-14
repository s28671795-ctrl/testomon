/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire;

import org.apache.activemq.ActiveMQConnectionMetaData;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.wireformat.WireFormat;
import org.apache.activemq.wireformat.WireFormatFactory;

public class OpenWireFormatFactory
implements WireFormatFactory {
    private int version = 12;
    private boolean stackTraceEnabled = true;
    private boolean tcpNoDelayEnabled = true;
    private boolean cacheEnabled = true;
    private boolean tightEncodingEnabled = true;
    private boolean sizePrefixDisabled;
    private long maxInactivityDuration = 30000L;
    private long maxInactivityDurationInitalDelay = 10000L;
    private int cacheSize = 1024;
    private long maxFrameSize = Long.MAX_VALUE;
    private String host = null;
    private String providerName = "ActiveMQ";
    private String providerVersion = ActiveMQConnectionMetaData.PROVIDER_VERSION;
    private String platformDetails = "Java";
    private boolean includePlatformDetails = false;

    @Override
    public WireFormat createWireFormat() {
        WireFormatInfo info = new WireFormatInfo();
        info.setVersion(this.version);
        try {
            info.setStackTraceEnabled(this.stackTraceEnabled);
            info.setCacheEnabled(this.cacheEnabled);
            info.setTcpNoDelayEnabled(this.tcpNoDelayEnabled);
            info.setTightEncodingEnabled(this.tightEncodingEnabled);
            info.setSizePrefixDisabled(this.sizePrefixDisabled);
            info.setMaxInactivityDuration(this.maxInactivityDuration);
            info.setMaxInactivityDurationInitalDelay(this.maxInactivityDurationInitalDelay);
            info.setCacheSize(this.cacheSize);
            info.setMaxFrameSize(this.maxFrameSize);
            if (this.host != null) {
                info.setHost(this.host);
            }
            info.setProviderName(this.providerName);
            info.setProviderVersion(this.providerVersion);
            if (this.includePlatformDetails) {
                this.platformDetails = ActiveMQConnectionMetaData.PLATFORM_DETAILS;
            }
            info.setPlatformDetails(this.platformDetails);
        }
        catch (Exception e) {
            IllegalStateException ise = new IllegalStateException("Could not configure WireFormatInfo");
            ise.initCause(e);
            throw ise;
        }
        OpenWireFormat f = new OpenWireFormat(this.version);
        f.setMaxFrameSize(this.maxFrameSize);
        f.setPreferedWireFormatInfo(info);
        return f;
    }

    public boolean isStackTraceEnabled() {
        return this.stackTraceEnabled;
    }

    public void setStackTraceEnabled(boolean stackTraceEnabled) {
        this.stackTraceEnabled = stackTraceEnabled;
    }

    public boolean isTcpNoDelayEnabled() {
        return this.tcpNoDelayEnabled;
    }

    public void setTcpNoDelayEnabled(boolean tcpNoDelayEnabled) {
        this.tcpNoDelayEnabled = tcpNoDelayEnabled;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public boolean isTightEncodingEnabled() {
        return this.tightEncodingEnabled;
    }

    public void setTightEncodingEnabled(boolean tightEncodingEnabled) {
        this.tightEncodingEnabled = tightEncodingEnabled;
    }

    public boolean isSizePrefixDisabled() {
        return this.sizePrefixDisabled;
    }

    public void setSizePrefixDisabled(boolean sizePrefixDisabled) {
        this.sizePrefixDisabled = sizePrefixDisabled;
    }

    public long getMaxInactivityDuration() {
        return this.maxInactivityDuration;
    }

    public void setMaxInactivityDuration(long maxInactivityDuration) {
        this.maxInactivityDuration = maxInactivityDuration;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public long getMaxInactivityDurationInitalDelay() {
        return this.maxInactivityDurationInitalDelay;
    }

    public void setMaxInactivityDurationInitalDelay(long maxInactivityDurationInitalDelay) {
        this.maxInactivityDurationInitalDelay = maxInactivityDurationInitalDelay;
    }

    public long getMaxFrameSize() {
        return this.maxFrameSize;
    }

    public void setMaxFrameSize(long maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderVersion() {
        return this.providerVersion;
    }

    public void setProviderVersion(String providerVersion) {
        this.providerVersion = providerVersion;
    }

    public String getPlatformDetails() {
        return this.platformDetails;
    }

    public void setPlatformDetails(String platformDetails) {
        this.platformDetails = platformDetails;
    }

    public boolean isIncludePlatformDetails() {
        return this.includePlatformDetails;
    }

    public void setIncludePlatformDetails(boolean includePlatformDetails) {
        this.includePlatformDetails = includePlatformDetails;
    }
}

