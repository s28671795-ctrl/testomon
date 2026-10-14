/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.ArrayList;
import java.util.List;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Layout;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class LayoutAssertionState
extends AssertionState
implements Assertable {
    private List<SecurityEventConstants.Event> occuredEvents = new ArrayList<SecurityEventConstants.Event>();

    public LayoutAssertionState(AbstractSecurityAssertion assertion, boolean asserted) {
        super(assertion, asserted);
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.USERNAME_TOKEN, WSSecurityEventConstants.ISSUED_TOKEN, SecurityEventConstants.X509Token, WSSecurityEventConstants.KERBEROS_TOKEN, WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN, WSSecurityEventConstants.SAML_TOKEN, WSSecurityEventConstants.REL_TOKEN, WSSecurityEventConstants.HTTPS_TOKEN, SecurityEventConstants.KeyValueToken, WSSecurityEventConstants.TIMESTAMP};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        Layout layout = (Layout)this.getAssertion();
        switch (layout.getLayoutType()) {
            case Strict: {
                break;
            }
            case Lax: {
                break;
            }
            case LaxTsFirst: {
                if (!this.occuredEvents.isEmpty() || WSSecurityEventConstants.TIMESTAMP.equals(securityEvent.getSecurityEventType())) break;
                this.setAsserted(false);
                this.setErrorMessage("Policy enforces " + (Object)((Object)layout.getLayoutType()) + " but " + securityEvent.getSecurityEventType() + " occured first");
                break;
            }
            case LaxTsLast: {
                if (!this.occuredEvents.contains(WSSecurityEventConstants.TIMESTAMP)) break;
                this.setAsserted(false);
                this.setErrorMessage("Policy enforces " + (Object)((Object)layout.getLayoutType()) + " but " + securityEvent.getSecurityEventType() + " occured last");
            }
        }
        this.occuredEvents.add(securityEvent.getSecurityEventType());
        return this.isAsserted();
    }
}

