/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import java.time.Instant;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;

public class TimestampSecurityEvent
extends SecurityEvent {
    private Instant created;
    private Instant expires;

    public TimestampSecurityEvent() {
        super(WSSecurityEventConstants.TIMESTAMP);
    }

    public Instant getCreated() {
        return this.created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpires() {
        return this.expires;
    }

    public void setExpires(Instant expires) {
        this.expires = expires;
    }
}

