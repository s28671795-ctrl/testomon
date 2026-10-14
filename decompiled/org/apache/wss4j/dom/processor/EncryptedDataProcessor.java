/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.SecurityTokenRefSTRParser;
import org.apache.wss4j.dom.util.EncryptionUtils;
import org.apache.wss4j.dom.util.SignatureUtils;
import org.apache.wss4j.dom.util.X509Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptedDataProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptedDataProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        Element decryptedElem;
        byte[] symmKey;
        LOG.debug("Found EncryptedData element");
        String encryptedDataId = elem.getAttributeNS(null, "Id");
        Element kiElem = XMLUtils.getDirectChildElement(elem, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#");
        if (kiElem == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "noKeyinfo");
        }
        String symEncAlgo = X509Util.getEncAlgo(elem);
        EncryptedDataProcessor.checkBSPCompliance(symEncAlgo, data.getBSPEnforcer());
        Element secRefToken = XMLUtils.getDirectChildElement(kiElem, "SecurityTokenReference", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        Element encryptedKeyElement = XMLUtils.getDirectChildElement(kiElem, "EncryptedKey", "http://www.w3.org/2001/04/xmlenc#");
        Element retrievalMethodElement = XMLUtils.getDirectChildElement(kiElem, "RetrievalMethod", "http://www.w3.org/2000/09/xmldsig#");
        if (data.isRequireSignedEncryptedDataElements()) {
            List<WSSecurityEngineResult> signedResults = data.getWsDocInfo().getResultsByTag(2);
            SignatureUtils.verifySignedElement(elem, signedResults);
        }
        SecretKey key = null;
        List<WSSecurityEngineResult> encrKeyResults = null;
        Principal principal = null;
        if (secRefToken != null) {
            STRParserParameters parameters = new STRParserParameters();
            parameters.setData(data);
            parameters.setStrElement(secRefToken);
            if (symEncAlgo != null) {
                parameters.setDerivationKeyLength(KeyUtils.getKeyLength(symEncAlgo));
            }
            SecurityTokenRefSTRParser strParser = new SecurityTokenRefSTRParser();
            STRParserResult parserResult = strParser.parseSecurityTokenReference(parameters);
            byte[] secretKey = parserResult.getSecretKey();
            principal = parserResult.getPrincipal();
            key = KeyUtils.prepareSecretKey(symEncAlgo, secretKey);
            encrKeyResults = new ArrayList<WSSecurityEngineResult>();
        } else if (encryptedKeyElement != null && data.getWssConfig() != null) {
            WSSConfig wssConfig = data.getWssConfig();
            Processor encrKeyProc = wssConfig.getProcessor(WSConstants.ENCRYPTED_KEY);
            encrKeyResults = encrKeyProc.handleToken(encryptedKeyElement, data);
            symmKey = (byte[])encrKeyResults.get(0).get("secret");
            key = KeyUtils.prepareSecretKey(symEncAlgo, symmKey);
        } else if (retrievalMethodElement != null && "http://www.w3.org/2001/04/xmlenc#EncryptedKey".equals(retrievalMethodElement.getAttributeNS(null, "Type"))) {
            String uri = retrievalMethodElement.getAttributeNS(null, "URI");
            uri = XMLUtils.getIDFromReference(uri);
            WSSecurityEngineResult result = data.getWsDocInfo().getResult(uri);
            if (result != null) {
                symmKey = (byte[])result.get("secret");
                key = KeyUtils.prepareSecretKey(symEncAlgo, symmKey);
            }
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "noEncKey");
        }
        AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            if (principal instanceof WSDerivedKeyTokenPrincipal) {
                algorithmSuiteValidator.checkDerivedKeyAlgorithm(((WSDerivedKeyTokenPrincipal)principal).getAlgorithm());
                algorithmSuiteValidator.checkEncryptionDerivedKeyLength(((WSDerivedKeyTokenPrincipal)principal).getLength());
            }
            algorithmSuiteValidator.checkSymmetricKeyLength(key.getEncoded().length);
            algorithmSuiteValidator.checkSymmetricEncryptionAlgorithm(symEncAlgo);
        }
        WSDataRef dataRef = EncryptionUtils.decryptEncryptedData(elem.getOwnerDocument(), encryptedDataId, elem, key, symEncAlgo, data.getAttachmentCallbackHandler(), data.getEncryptionSerializer());
        WSSecurityEngineResult result = new WSSecurityEngineResult(4, Collections.singletonList(dataRef));
        if (!"".equals(encryptedDataId)) {
            result.put("id", encryptedDataId);
        }
        data.getWsDocInfo().addResult(result);
        data.getWsDocInfo().addTokenElement(elem);
        LinkedList<WSSecurityEngineResult> completeResults = new LinkedList<WSSecurityEngineResult>();
        if (encrKeyResults != null) {
            completeResults.addAll(encrKeyResults);
        }
        completeResults.add(result);
        WSSConfig wssConfig = data.getWssConfig();
        if (wssConfig != null && (decryptedElem = dataRef.getProtectedElement()) != null) {
            QName el = new QName(decryptedElem.getNamespaceURI(), decryptedElem.getLocalName());
            Processor proc = data.getWssConfig().getProcessor(el);
            if (proc != null) {
                LOG.debug("Processing decrypted element with: {}", (Object)proc.getClass().getName());
                List<WSSecurityEngineResult> results = proc.handleToken(decryptedElem, data);
                completeResults.addAll(0, results);
                return completeResults;
            }
        }
        return completeResults;
    }

    private static void checkBSPCompliance(String encAlgo, BSPEnforcer bspEnforcer) throws WSSecurityException {
        if (encAlgo == null) {
            bspEnforcer.handleBSPRule(BSPRule.R5601);
        }
        if (!("http://www.w3.org/2001/04/xmlenc#tripledes-cbc".equals(encAlgo) || "http://www.w3.org/2001/04/xmlenc#aes128-cbc".equals(encAlgo) || "http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(encAlgo) || "http://www.w3.org/2001/04/xmlenc#aes256-cbc".equals(encAlgo) || "http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(encAlgo))) {
            bspEnforcer.handleBSPRule(BSPRule.R5620);
        }
    }
}

