/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.util.Collections;
import java.util.List;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.DerivedKeyToken;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.str.DerivedKeyTokenSTRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.w3c.dom.Element;

public class DerivedKeyTokenProcessor
implements Processor {
    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        DerivedKeyToken dkt = new DerivedKeyToken(elem, data.getBSPEnforcer());
        AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            algorithmSuiteValidator.checkDerivedKeyAlgorithm(dkt.getAlgorithm());
        }
        byte[] secret = null;
        Element secRefElement = dkt.getSecurityTokenReferenceElement();
        if (secRefElement == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "noReference");
        }
        STRParserParameters parameters = new STRParserParameters();
        parameters.setData(data);
        parameters.setStrElement(secRefElement);
        DerivedKeyTokenSTRParser strParser = new DerivedKeyTokenSTRParser();
        STRParserResult parserResult = strParser.parseSecurityTokenReference(parameters);
        secret = parserResult.getSecretKey();
        String tempNonce = dkt.getNonce();
        if (tempNonce == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Missing wsc:Nonce value"});
        }
        int length = dkt.getLength();
        byte[] keyBytes = dkt.deriveKey(length, secret);
        WSSecurityEngineResult result = new WSSecurityEngineResult(2048, null, keyBytes, null);
        data.getWsDocInfo().addTokenElement(elem);
        String tokenId = dkt.getID();
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        result.put("derived-key-token", dkt);
        result.put("secret", secret);
        result.put("token-element", dkt.getElement());
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }
}

