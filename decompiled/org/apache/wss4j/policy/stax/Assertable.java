/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax;

import org.apache.wss4j.common.WSSPolicyException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public interface Assertable {
    public SecurityEventConstants.Event[] getSecurityEventType();

    public boolean assertEvent(SecurityEvent var1) throws WSSPolicyException, XMLSecurityException;

    public boolean isHardFailure();

    public boolean isAsserted();

    public String getErrorMessage();

    public boolean isLogged();

    public void setLogged(boolean var1);
}

