/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ThreadingParametersIdentifiedType", propOrder={"threadingParameters"})
public class ThreadingParametersIdentifiedType {
    @XmlElement(required=true)
    protected ThreadingParametersType threadingParameters = new ThreadingParametersType();
    @XmlAttribute(name="id")
    protected String id;

    public ThreadingParametersType getThreadingParameters() {
        return this.threadingParameters;
    }

    public void setThreadingParameters(ThreadingParametersType value) {
        this.threadingParameters = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

