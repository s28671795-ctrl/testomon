/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ThreadingParametersType")
public class ThreadingParametersType {
    @XmlAttribute(name="minThreads")
    protected Integer minThreads;
    @XmlAttribute(name="maxThreads")
    protected Integer maxThreads;
    @XmlAttribute(name="threadNamePrefix")
    protected String threadNamePrefix;

    public Integer getMinThreads() {
        return this.minThreads;
    }

    public void setMinThreads(Integer value) {
        this.minThreads = value;
    }

    public Integer getMaxThreads() {
        return this.maxThreads;
    }

    public void setMaxThreads(Integer value) {
        this.maxThreads = value;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }

    public void setThreadNamePrefix(String value) {
        this.threadNamePrefix = value;
    }
}

