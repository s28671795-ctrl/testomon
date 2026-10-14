/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"currentTime", "terminationTime", "notificationMessage"})
@XmlRootElement(name="PullMessagesResponse")
public class PullMessagesResponse {
    @XmlElement(name="CurrentTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar currentTime;
    @XmlElement(name="TerminationTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar terminationTime;
    @XmlElement(name="NotificationMessage", namespace="http://docs.oasis-open.org/wsn/b-2")
    protected List<NotificationMessageHolderType> notificationMessage;

    public XMLGregorianCalendar getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(XMLGregorianCalendar value) {
        this.currentTime = value;
    }

    public XMLGregorianCalendar getTerminationTime() {
        return this.terminationTime;
    }

    public void setTerminationTime(XMLGregorianCalendar value) {
        this.terminationTime = value;
    }

    public List<NotificationMessageHolderType> getNotificationMessage() {
        if (this.notificationMessage == null) {
            this.notificationMessage = new ArrayList<NotificationMessageHolderType>();
        }
        return this.notificationMessage;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

