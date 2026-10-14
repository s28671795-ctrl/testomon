/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.SecurityContextToken;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.w3c.dom.Element;

public class SecurityContextTokenProcessor
implements Processor {
    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        SecurityContextToken sct = new SecurityContextToken(elem);
        Validator validator = data.getValidator(new QName(elem.getNamespaceURI(), elem.getLocalName()));
        WSSecurityEngineResult result = new WSSecurityEngineResult(1024, sct);
        if (validator != null) {
            Credential credential = new Credential();
            credential.setSecurityContextToken(sct);
            Credential returnedCredential = validator.validate(credential, data);
            result.put("validated-token", Boolean.TRUE);
            String tokenId = sct.getID();
            if (!"".equals(tokenId)) {
                result.put("id", tokenId);
            }
            result.put("secret", returnedCredential.getSecretKey());
        } else {
            String id = sct.getID();
            id = XMLUtils.getIDFromReference(id);
            byte[] secret = null;
            try {
                secret = this.getSecret(data.getCallbackHandler(), sct.getIdentifier());
            }
            catch (WSSecurityException ex) {
                secret = this.getSecret(data.getCallbackHandler(), id);
            }
            if (secret == null || secret.length == 0) {
                secret = this.getSecret(data.getCallbackHandler(), id);
            }
            result.put("id", sct.getID());
            result.put("secret", secret);
        }
        data.getWsDocInfo().addTokenElement(elem);
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }

    private byte[] getSecret(CallbackHandler cb, String identifier) throws WSSecurityException {
        if (cb == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCallback");
        }
        WSPasswordCallback callback = new WSPasswordCallback(identifier, 6);
        try {
            Callback[] callbacks = new Callback[]{callback};
            cb.handle(callbacks);
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noKey", new Object[]{identifier});
        }
        return callback.getKey();
    }
}

