/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecUsernameToken;

public class UsernameTokenAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        String username = reqData.getUsername();
        String password = null;
        if (reqData.getPwType() != null) {
            CallbackHandler callbackHandler = handler.getPasswordCallbackHandler(reqData);
            WSPasswordCallback passwordCallback = handler.getPasswordCB(reqData.getUsername(), 1, callbackHandler, reqData);
            username = passwordCallback.getIdentifier();
            password = passwordCallback.getPassword();
        }
        if (username == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noUser");
        }
        WSSecUsernameToken builder = new WSSecUsernameToken(reqData.getSecHeader());
        builder.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        builder.setPrecisionInMilliSeconds(reqData.isPrecisionInMilliSeconds());
        builder.setWsTimeSource(reqData.getWssConfig().getCurrentTime());
        builder.setPasswordType(reqData.getPwType());
        builder.setPasswordsAreEncoded(reqData.isEncodePasswords());
        builder.setUserInfo(username, password);
        builder.setWsDocInfo(reqData.getWsDocInfo());
        builder.setExpandXopInclude(reqData.isExpandXopInclude());
        if (reqData.isAddUsernameTokenNonce()) {
            builder.addNonce();
        }
        if (reqData.isAddUsernameTokenCreated()) {
            builder.addCreated();
        }
        builder.build();
    }
}

