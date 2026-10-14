/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.RelayOutputOptionsExtension;
import org.onvif.ver10.schema.RelayMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RelayOutputOptions", propOrder={"mode", "delayTimes", "discrete", "extension"})
public class RelayOutputOptions {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<RelayMode> mode;
    @XmlList
    @XmlElement(name="DelayTimes", type=Float.class)
    protected List<Float> delayTimes;
    @XmlElement(name="Discrete")
    protected Boolean discrete;
    @XmlElement(name="Extension")
    protected RelayOutputOptionsExtension extension;
    @XmlAttribute(name="token", required=true)
    protected String token;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<RelayMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<RelayMode>();
        }
        return this.mode;
    }

    public List<Float> getDelayTimes() {
        if (this.delayTimes == null) {
            this.delayTimes = new ArrayList<Float>();
        }
        return this.delayTimes;
    }

    public Boolean getDiscrete() {
        return this.discrete;
    }

    public void setDiscrete(Boolean value) {
        this.discrete = value;
    }

    public RelayOutputOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RelayOutputOptionsExtension value) {
        this.extension = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

