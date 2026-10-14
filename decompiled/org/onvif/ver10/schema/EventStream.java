/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.onvif.ver10.schema.EventStreamExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EventStream", propOrder={"notificationMessageOrExtension"})
public class EventStream {
    @XmlElements(value={@XmlElement(name="NotificationMessage", namespace="http://docs.oasis-open.org/wsn/b-2", type=NotificationMessageHolderType.class), @XmlElement(name="Extension", type=EventStreamExtension.class)})
    protected List<Object> notificationMessageOrExtension;

    public List<Object> getNotificationMessageOrExtension() {
        if (this.notificationMessageOrExtension == null) {
            this.notificationMessageOrExtension = new ArrayList<Object>();
        }
        return this.notificationMessageOrExtension;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

