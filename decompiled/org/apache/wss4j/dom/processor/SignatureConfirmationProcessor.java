/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.util.Collections;
import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.SignatureConfirmation;
import org.apache.wss4j.dom.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SignatureConfirmationProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureConfirmationProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        LOG.debug("Found SignatureConfirmation list element");
        SignatureConfirmation sigConf = new SignatureConfirmation(elem, data.getBSPEnforcer());
        WSSecurityEngineResult result = new WSSecurityEngineResult(128, sigConf);
        String tokenId = sigConf.getID();
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        data.getWsDocInfo().addResult(result);
        data.getWsDocInfo().addTokenElement(elem);
        return Collections.singletonList(result);
    }
}

