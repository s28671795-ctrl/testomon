/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.w3c.dom.Element;

public class CustomTokenAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        CallbackHandler callbackHandler = reqData.getCallbackHandler();
        if (callbackHandler == null) {
            callbackHandler = handler.getPasswordCallbackHandler(reqData);
        }
        if (callbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCallback");
        }
        WSPasswordCallback wsPasswordCallback = new WSPasswordCallback(reqData.getUsername(), 7);
        try {
            callbackHandler.handle(new Callback[]{wsPasswordCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"WSHandler: password callback failed"});
        }
        Element customToken = wsPasswordCallback.getCustomToken();
        if (customToken == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "resourceNotFound", new Object[]{"CustomToken"});
        }
        try {
            Element securityHeader = reqData.getSecHeader().getSecurityHeaderElement();
            customToken = (Element)securityHeader.getOwnerDocument().importNode(customToken, true);
            securityHeader.appendChild(customToken);
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Error appending custom token"});
        }
    }
}

