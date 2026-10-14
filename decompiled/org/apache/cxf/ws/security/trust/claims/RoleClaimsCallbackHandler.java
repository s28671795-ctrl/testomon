/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust.claims;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.security.trust.claims.ClaimsCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RoleClaimsCallbackHandler
implements CallbackHandler {
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; ++i) {
            if (!(callbacks[i] instanceof ClaimsCallback)) {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
            ClaimsCallback callback = (ClaimsCallback)callbacks[i];
            callback.setClaims(this.createClaims());
        }
    }

    private Element createClaims() {
        Document doc = DOMUtils.getEmptyDocument();
        Element claimsElement = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Claims");
        claimsElement.setAttributeNS(null, "Dialect", "http://schemas.xmlsoap.org/ws/2005/05/identity");
        Element claimType = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "ClaimType");
        claimType.setAttributeNS(null, "Uri", "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role");
        claimsElement.appendChild(claimType);
        return claimsElement;
    }
}

