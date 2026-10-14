/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.cxf.transports.http_jetty.configuration.JettyHTTPServerEngineConfigType;
import org.apache.cxf.transports.http_jetty.configuration.TLSServerParametersIdentifiedType;
import org.apache.cxf.transports.http_jetty.configuration.ThreadingParametersIdentifiedType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="JettyHTTPServerEngineFactoryConfigType", propOrder={"identifiedTLSServerParameters", "identifiedThreadingParameters", "engine"})
public class JettyHTTPServerEngineFactoryConfigType {
    protected List<TLSServerParametersIdentifiedType> identifiedTLSServerParameters;
    protected List<ThreadingParametersIdentifiedType> identifiedThreadingParameters;
    protected List<JettyHTTPServerEngineConfigType> engine;
    @XmlAttribute(name="bus")
    protected String bus;
    @XmlAttribute(name="id")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String id;

    public List<TLSServerParametersIdentifiedType> getIdentifiedTLSServerParameters() {
        if (this.identifiedTLSServerParameters == null) {
            this.identifiedTLSServerParameters = new ArrayList<TLSServerParametersIdentifiedType>();
        }
        return this.identifiedTLSServerParameters;
    }

    public List<ThreadingParametersIdentifiedType> getIdentifiedThreadingParameters() {
        if (this.identifiedThreadingParameters == null) {
            this.identifiedThreadingParameters = new ArrayList<ThreadingParametersIdentifiedType>();
        }
        return this.identifiedThreadingParameters;
    }

    public List<JettyHTTPServerEngineConfigType> getEngine() {
        if (this.engine == null) {
            this.engine = new ArrayList<JettyHTTPServerEngineConfigType>();
        }
        return this.engine;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public void setBus(String value) {
        this.bus = value;
    }

    public void unsetBus() {
        this.bus = null;
    }

    public String getBus() {
        if (null == this.bus) {
            return "cxf";
        }
        return this.bus;
    }
}

