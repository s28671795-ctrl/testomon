/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.bf_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;
import org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType;
import org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BaseFaultType", propOrder={"any", "timestamp", "originator", "errorCode", "description", "faultCause"})
@XmlSeeAlso(value={SubscribeCreationFailedFaultType.class, InvalidFilterFaultType.class, TopicExpressionDialectUnknownFaultType.class, InvalidTopicExpressionFaultType.class, TopicNotSupportedFaultType.class, MultipleTopicsSpecifiedFaultType.class, InvalidProducerPropertiesExpressionFaultType.class, InvalidMessageContentExpressionFaultType.class, UnrecognizedPolicyRequestFaultType.class, UnsupportedPolicyRequestFaultType.class, NotifyMessageNotSupportedFaultType.class, UnacceptableInitialTerminationTimeFaultType.class, NoCurrentMessageOnTopicFaultType.class, UnableToGetMessagesFaultType.class, UnableToDestroyPullPointFaultType.class, UnableToCreatePullPointFaultType.class, UnacceptableTerminationTimeFaultType.class, UnableToDestroySubscriptionFaultType.class, PauseFailedFaultType.class, ResumeFailedFaultType.class})
public class BaseFaultType {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Timestamp", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name="Originator")
    protected W3CEndpointReference originator;
    @XmlElement(name="ErrorCode")
    protected ErrorCode errorCode;
    @XmlElement(name="Description")
    protected List<Description> description;
    @XmlElement(name="FaultCause")
    protected FaultCause faultCause;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public XMLGregorianCalendar getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    public W3CEndpointReference getOriginator() {
        return this.originator;
    }

    public void setOriginator(W3CEndpointReference value) {
        this.originator = value;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(ErrorCode value) {
        this.errorCode = value;
    }

    public List<Description> getDescription() {
        if (this.description == null) {
            this.description = new ArrayList<Description>();
        }
        return this.description;
    }

    public FaultCause getFaultCause() {
        return this.faultCause;
    }

    public void setFaultCause(FaultCause value) {
        this.faultCause = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"content"})
    public static class ErrorCode {
        @XmlMixed
        @XmlAnyElement
        protected List<Object> content;
        @XmlAttribute(name="dialect", required=true)
        @XmlSchemaType(name="anyURI")
        protected String dialect;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        public List<Object> getContent() {
            if (this.content == null) {
                this.content = new ArrayList<Object>();
            }
            return this.content;
        }

        public String getDialect() {
            return this.dialect;
        }

        public void setDialect(String value) {
            this.dialect = value;
        }

        public Map<QName, String> getOtherAttributes() {
            return this.otherAttributes;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class FaultCause {
        @XmlAnyElement(lax=true)
        protected Object any;

        public Object getAny() {
            return this.any;
        }

        public void setAny(Object value) {
            this.any = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class Description {
        @XmlValue
        protected String value;
        @XmlAttribute(name="lang", namespace="http://www.w3.org/XML/1998/namespace")
        protected String lang;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLang() {
            return this.lang;
        }

        public void setLang(String value) {
            this.lang = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

