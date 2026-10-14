/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.util.Collections;
import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class TimestampProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(TimestampProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        Validator validator;
        LOG.debug("Found Timestamp list element");
        Timestamp timestamp = new Timestamp(elem, data.getBSPEnforcer());
        Credential credential = new Credential();
        credential.setTimestamp(timestamp);
        WSSecurityEngineResult result = new WSSecurityEngineResult(32, timestamp);
        String tokenId = timestamp.getID();
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        if ((validator = data.getValidator(WSConstants.TIMESTAMP)) != null) {
            validator.validate(credential, data);
            result.put("validated-token", Boolean.TRUE);
        }
        data.getWsDocInfo().addTokenElement(elem);
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }
}

