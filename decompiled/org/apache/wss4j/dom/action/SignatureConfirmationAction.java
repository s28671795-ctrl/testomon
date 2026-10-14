/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import java.util.List;
import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.SignatureActionToken;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.WSSecSignatureConfirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureConfirmationAction
implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureConfirmationAction.class);

    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        LOG.debug("Perform Signature confirmation");
        List results = (List)handler.getProperty(reqData.getMsgContext(), "RECV_RESULTS");
        if (results == null || results.isEmpty()) {
            return;
        }
        WSSecSignatureConfirmation wsc = new WSSecSignatureConfirmation(reqData.getSecHeader());
        wsc.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        wsc.setWsDocInfo(reqData.getWsDocInfo());
        wsc.setExpandXopInclude(reqData.isExpandXopInclude());
        SignatureActionToken signatureToken = (SignatureActionToken)actionToken;
        if (signatureToken == null) {
            signatureToken = reqData.getSignatureToken();
        }
        List<WSEncryptionPart> signatureParts = signatureToken.getParts();
        boolean signatureAdded = false;
        for (WSHandlerResult wshResult : results) {
            List<WSSecurityEngineResult> resultList = wshResult.getResults();
            for (WSSecurityEngineResult result : resultList) {
                Integer resultAction = (Integer)result.get("action");
                if (resultAction == null || 2 != resultAction && 16 != resultAction && 64 != resultAction) continue;
                byte[] sigVal = (byte[])result.get("signature-value");
                wsc.build(sigVal);
                signatureParts.add(new WSEncryptionPart(wsc.getId()));
                signatureAdded = true;
            }
        }
        if (!signatureAdded) {
            wsc.build(null);
            signatureParts.add(new WSEncryptionPart(wsc.getId()));
        }
        handler.setProperty(reqData.getMsgContext(), "_sigConfDone_", "");
    }
}

