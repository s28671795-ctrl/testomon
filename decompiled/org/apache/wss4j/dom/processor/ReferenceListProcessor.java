/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDataRef;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ReferenceListProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(ReferenceListProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        LOG.debug("Found reference list element");
        List<WSDataRef> dataRefs = this.handleReferenceList(elem, data);
        WSSecurityEngineResult result = new WSSecurityEngineResult(4, dataRefs);
        String tokenId = elem.getAttributeNS(null, "Id");
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        data.getWsDocInfo().addTokenElement(elem);
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }

    private List<WSDataRef> handleReferenceList(Element elem, RequestData data) throws WSSecurityException {
        ArrayList<WSDataRef> dataRefs = new ArrayList<WSDataRef>();
        for (Node node = elem.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType() || !"http://www.w3.org/2001/04/xmlenc#".equals(node.getNamespaceURI()) || !"DataReference".equals(node.getLocalName())) continue;
            String dataRefURI = ((Element)node).getAttributeNS(null, "URI");
            dataRefURI = XMLUtils.getIDFromReference(dataRefURI);
            if (data.getWsDocInfo().hasResult(4, dataRefURI)) continue;
            WSDataRef dataRef = this.decryptDataRefEmbedded(elem.getOwnerDocument(), dataRefURI, data);
            dataRefs.add(dataRef);
        }
        return dataRefs;
    }

    private WSDataRef decryptDataRefEmbedded(Document doc, String dataRefURI, RequestData data) throws WSSecurityException {
        LOG.debug("Found data reference: {}", (Object)dataRefURI);
        Element encryptedDataElement = EncryptionUtils.findEncryptedDataElement(doc, data.getWsDocInfo(), dataRefURI);
        if (encryptedDataElement != null && data.isRequireSignedEncryptedDataElements()) {
            List<WSSecurityEngineResult> signedResults = data.getWsDocInfo().getResultsByTag(2);
            SignatureUtils.verifySignedElement(encryptedDataElement, signedResults);
        }
        String symEncAlgo = X509Util.getEncAlgo(encryptedDataElement);
        Element keyInfoElement = XMLUtils.getDirectChildElement(encryptedDataElement, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#");
        if (keyInfoElement == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noKeyinfo");
        }
        ReferenceListProcessor.checkBSPCompliance(keyInfoElement, symEncAlgo, data.getBSPEnforcer());
        Element secRefToken = XMLUtils.getDirectChildElement(keyInfoElement, "SecurityTokenReference", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        SecretKey symmetricKey = null;
        Principal principal = null;
        if (secRefToken == null) {
            byte[] decryptedData = X509Util.getSecretKey(keyInfoElement, symEncAlgo, data.getCallbackHandler(), null);
            symmetricKey = KeyUtils.prepareSecretKey(symEncAlgo, decryptedData);
        } else {
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
            symmetricKey = KeyUtils.prepareSecretKey(symEncAlgo, secretKey);
        }
        AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            if (principal instanceof WSDerivedKeyTokenPrincipal) {
                algorithmSuiteValidator.checkDerivedKeyAlgorithm(((WSDerivedKeyTokenPrincipal)principal).getAlgorithm());
                algorithmSuiteValidator.checkEncryptionDerivedKeyLength(((WSDerivedKeyTokenPrincipal)principal).getLength());
            }
            algorithmSuiteValidator.checkSymmetricKeyLength(symmetricKey.getEncoded().length);
            algorithmSuiteValidator.checkSymmetricEncryptionAlgorithm(symEncAlgo);
        }
        return EncryptionUtils.decryptEncryptedData(doc, dataRefURI, encryptedDataElement, symmetricKey, symEncAlgo, data.getAttachmentCallbackHandler(), data.getEncryptionSerializer());
    }

    private static void checkBSPCompliance(Element keyInfoElement, String encAlgo, BSPEnforcer bspEnforcer) throws WSSecurityException {
        int result = 0;
        Element child = null;
        for (Node node = keyInfoElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType()) continue;
            ++result;
            child = (Element)node;
        }
        if (result != 1) {
            bspEnforcer.handleBSPRule(BSPRule.R5424);
        }
        if (child == null || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(child.getNamespaceURI()) || !"SecurityTokenReference".equals(child.getLocalName())) {
            bspEnforcer.handleBSPRule(BSPRule.R5426);
        }
        if (encAlgo == null) {
            bspEnforcer.handleBSPRule(BSPRule.R5601);
        }
        if (!("http://www.w3.org/2001/04/xmlenc#tripledes-cbc".equals(encAlgo) || "http://www.w3.org/2001/04/xmlenc#aes128-cbc".equals(encAlgo) || "http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(encAlgo) || "http://www.w3.org/2001/04/xmlenc#aes256-cbc".equals(encAlgo) || "http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(encAlgo))) {
            bspEnforcer.handleBSPRule(BSPRule.R5620);
        }
    }
}

