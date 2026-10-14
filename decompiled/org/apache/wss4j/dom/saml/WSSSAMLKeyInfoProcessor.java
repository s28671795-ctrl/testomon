/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.saml;

import java.security.Principal;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLKeyInfoProcessor;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.EncryptedKeyProcessor;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.SignatureSTRParser;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class WSSSAMLKeyInfoProcessor
implements SAMLKeyInfoProcessor {
    private static final String WST_NS = "http://schemas.xmlsoap.org/ws/2005/02/trust";
    private static final String WST_NS_05_12 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    private static final QName BINARY_SECRET = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "BinarySecret");
    private static final QName BINARY_SECRET_05_12 = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret");
    private RequestData data;

    public WSSSAMLKeyInfoProcessor(RequestData data) {
        this.data = data;
    }

    @Override
    public SAMLKeyInfo processSAMLKeyInfo(Element keyInfoElement) throws WSSecurityException {
        if (keyInfoElement == null) {
            return null;
        }
        for (Node node = keyInfoElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType()) continue;
            QName el = new QName(node.getNamespaceURI(), node.getLocalName());
            if (el.equals(WSConstants.ENCRYPTED_KEY)) {
                EncryptedKeyProcessor proc = new EncryptedKeyProcessor();
                List<WSSecurityEngineResult> result = proc.handleToken((Element)node, this.data, this.data.getSamlAlgorithmSuite());
                byte[] secret = (byte[])result.get(0).get("secret");
                return new SAMLKeyInfo(secret);
            }
            if (el.equals(BINARY_SECRET) || el.equals(BINARY_SECRET_05_12)) {
                Text txt = (Text)node.getFirstChild();
                return new SAMLKeyInfo(XMLUtils.decode(txt.getData()));
            }
            if (!SecurityTokenReference.STR_QNAME.equals(el)) continue;
            STRParserParameters parameters = new STRParserParameters();
            parameters.setData(this.data);
            parameters.setStrElement((Element)node);
            SignatureSTRParser strParser = new SignatureSTRParser();
            STRParserResult parserResult = strParser.parseSecurityTokenReference(parameters);
            SAMLKeyInfo samlKeyInfo = new SAMLKeyInfo(parserResult.getCertificates());
            samlKeyInfo.setPublicKey(parserResult.getPublicKey());
            samlKeyInfo.setSecret(parserResult.getSecretKey());
            Principal principal = parserResult.getPrincipal();
            AlgorithmSuite algorithmSuite = this.data.getSamlAlgorithmSuite();
            if (algorithmSuite != null && principal instanceof WSDerivedKeyTokenPrincipal) {
                AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
                algorithmSuiteValidator.checkDerivedKeyAlgorithm(((WSDerivedKeyTokenPrincipal)principal).getAlgorithm());
                algorithmSuiteValidator.checkSignatureDerivedKeyLength(((WSDerivedKeyTokenPrincipal)principal).getLength());
            }
            return samlKeyInfo;
        }
        return null;
    }
}

