/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptedAssertionProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptedAssertionProcessor.class);

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData request) throws WSSecurityException {
        String typeStr;
        Processor proc;
        QName el;
        LOG.debug("Found EncryptedAssertion element");
        Element encryptedDataElement = XMLUtils.getDirectChildElement(elem, "EncryptedData", "http://www.w3.org/2001/04/xmlenc#");
        if (encryptedDataElement == null) {
            return Collections.emptyList();
        }
        LinkedList<WSSecurityEngineResult> completeResults = new LinkedList<WSSecurityEngineResult>();
        for (Object currentChild = elem.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            if (1 != currentChild.getNodeType() || !"EncryptedKey".equals(currentChild.getLocalName()) || !"http://www.w3.org/2001/04/xmlenc#".equals(currentChild.getNamespaceURI())) continue;
            el = new QName(((Element)currentChild).getNamespaceURI(), ((Element)currentChild).getLocalName());
            proc = request.getWssConfig().getProcessor(el);
            if (proc == null) continue;
            completeResults.addAll(proc.handleToken((Element)currentChild, request));
        }
        if (!completeResults.isEmpty()) {
            for (WSSecurityEngineResult r : completeResults) {
                List dataRefs = (List)r.get("data-ref-uris");
                if (dataRefs == null) continue;
                for (WSDataRef dataRef : dataRefs) {
                    if (!WSConstants.SAML_TOKEN.equals(dataRef.getName()) && !WSConstants.SAML2_TOKEN.equals(dataRef.getName())) continue;
                    Element decryptedElem = dataRef.getProtectedElement();
                    QName el2 = new QName(decryptedElem.getNamespaceURI(), decryptedElem.getLocalName());
                    Processor proc2 = request.getWssConfig().getProcessor(el2);
                    if (proc2 == null) continue;
                    LOG.debug("Processing decrypted element with: {}", (Object)proc2.getClass().getName());
                    List<WSSecurityEngineResult> results = proc2.handleToken(decryptedElem, request);
                    completeResults.addAll(0, results);
                    return completeResults;
                }
            }
        }
        if ((typeStr = encryptedDataElement.getAttributeNS(null, "Type")) != null && !"http://www.w3.org/2001/04/xmlenc#Element".equals(typeStr)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "badElement", new Object[]{"Element", typeStr});
        }
        el = new QName(encryptedDataElement.getNamespaceURI(), encryptedDataElement.getLocalName());
        proc = request.getWssConfig().getProcessor(el);
        if (proc != null) {
            LOG.debug("Processing decrypted element with: {}", (Object)proc.getClass().getName());
            return proc.handleToken(encryptedDataElement, request);
        }
        return Collections.emptyList();
    }
}

