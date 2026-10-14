/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.TLSServerParametersType;
import org.apache.cxf.transports.http_jetty.configuration.ParametersRefType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="JettyHTTPServerEngineConfigType", propOrder={"tlsServerParameters", "tlsServerParametersRef", "threadingParameters", "threadingParametersRef", "connector", "handlers", "sessionSupport", "sessionTimeout", "reuseAddress"})
public class JettyHTTPServerEngineConfigType {
    protected TLSServerParametersType tlsServerParameters = new TLSServerParametersType();
    protected ParametersRefType tlsServerParametersRef = new ParametersRefType();
    protected ThreadingParametersType threadingParameters = new ThreadingParametersType();
    protected ParametersRefType threadingParametersRef = new ParametersRefType();
    protected Object connector;
    protected Object handlers;
    protected Boolean sessionSupport;
    protected Integer sessionTimeout;
    protected Boolean reuseAddress;
    @XmlAttribute(name="port", required=true)
    protected Integer port;
    @XmlAttribute(name="host")
    protected String host;
    @XmlAttribute(name="continuationsEnabled")
    protected Boolean continuationsEnabled;
    @XmlAttribute(name="maxIdleTime")
    protected Integer maxIdleTime;
    @XmlAttribute(name="sendServerVersion")
    protected Boolean sendServerVersion;

    public TLSServerParametersType getTlsServerParameters() {
        return this.tlsServerParameters;
    }

    public void setTlsServerParameters(TLSServerParametersType value) {
        this.tlsServerParameters = value;
    }

    public ParametersRefType getTlsServerParametersRef() {
        return this.tlsServerParametersRef;
    }

    public void setTlsServerParametersRef(ParametersRefType value) {
        this.tlsServerParametersRef = value;
    }

    public ThreadingParametersType getThreadingParameters() {
        return this.threadingParameters;
    }

    public void setThreadingParameters(ThreadingParametersType value) {
        this.threadingParameters = value;
    }

    public ParametersRefType getThreadingParametersRef() {
        return this.threadingParametersRef;
    }

    public void setThreadingParametersRef(ParametersRefType value) {
        this.threadingParametersRef = value;
    }

    public Object getConnector() {
        return this.connector;
    }

    public void setConnector(Object value) {
        this.connector = value;
    }

    public Object getHandlers() {
        return this.handlers;
    }

    public void setHandlers(Object value) {
        this.handlers = value;
    }

    public Boolean isSessionSupport() {
        return this.sessionSupport;
    }

    public void setSessionSupport(Boolean value) {
        this.sessionSupport = value;
    }

    public Integer getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(Integer value) {
        this.sessionTimeout = value;
    }

    public Boolean isReuseAddress() {
        return this.reuseAddress;
    }

    public void setReuseAddress(Boolean value) {
        this.reuseAddress = value;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer value) {
        this.port = value;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String value) {
        this.host = value;
    }

    public Boolean isContinuationsEnabled() {
        return this.continuationsEnabled;
    }

    public void setContinuationsEnabled(Boolean value) {
        this.continuationsEnabled = value;
    }

    public Integer getMaxIdleTime() {
        return this.maxIdleTime;
    }

    public void setMaxIdleTime(Integer value) {
        this.maxIdleTime = value;
    }

    public Boolean isSendServerVersion() {
        return this.sendServerVersion;
    }

    public void setSendServerVersion(Boolean value) {
        this.sendServerVersion = value;
    }
}

