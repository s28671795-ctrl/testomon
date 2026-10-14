/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust.delegation;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.trust.delegation.DelegationCallback;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WSSUsernameCallbackHandler
implements CallbackHandler {
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; ++i) {
            if (callbacks[i] instanceof DelegationCallback) {
                DelegationCallback callback = (DelegationCallback)callbacks[i];
                Message message = callback.getCurrentMessage();
                String username = (String)SecurityUtils.getSecurityPropertyValue("security.username", message);
                if (username == null) continue;
                Node contentNode = message.getContent(Node.class);
                Document doc = null;
                doc = contentNode != null ? contentNode.getOwnerDocument() : DOMUtils.getEmptyDocument();
                UsernameToken usernameToken = this.createWSSEUsernameToken(username, doc);
                callback.setToken(usernameToken.getElement());
                continue;
            }
            throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
        }
    }

    private UsernameToken createWSSEUsernameToken(String username, Document doc) {
        UsernameToken usernameToken = new UsernameToken(true, doc, null);
        usernameToken.setName(username);
        usernameToken.addWSUNamespace();
        usernameToken.addWSSENamespace();
        usernameToken.setID("id-" + username);
        return usernameToken;
    }
}

