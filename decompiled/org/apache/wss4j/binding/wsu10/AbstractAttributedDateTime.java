/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wsu10;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class AbstractAttributedDateTime {
    @XmlTransient
    private ZonedDateTime zonedDateTime;

    public abstract String getValue();

    public ZonedDateTime getAsZonedDateTime() throws DateTimeParseException {
        if (this.zonedDateTime == null && this.getValue() != null) {
            this.zonedDateTime = ZonedDateTime.parse(this.getValue());
        }
        return this.zonedDateTime;
    }
}

