/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"notificationMessage", "any"})
@XmlRootElement(name="Notify")
public class Notify {
    @XmlElement(name="NotificationMessage", required=true)
    protected List<NotificationMessageHolderType> notificationMessage;
    @XmlAnyElement(lax=true)
    protected List<Object> any;

    public List<NotificationMessageHolderType> getNotificationMessage() {
        if (this.notificationMessage == null) {
            this.notificationMessage = new ArrayList<NotificationMessageHolderType>();
        }
        return this.notificationMessage;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

