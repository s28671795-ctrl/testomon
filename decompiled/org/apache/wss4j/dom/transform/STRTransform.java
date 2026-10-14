/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.transform.STRTransformUtil;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class STRTransform
extends TransformService {
    public static final String TRANSFORM_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform";
    public static final String TRANSFORM_WS_DOC_INFO = "transform_ws_doc_info";
    private TransformParameterSpec params;
    private Element transformElement;
    private static final Logger LOG = LoggerFactory.getLogger(STRTransform.class);

    @Override
    public final AlgorithmParameterSpec getParameterSpec() {
        return this.params;
    }

    @Override
    public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
        this.params = params;
    }

    @Override
    public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
        if (context != null && !(context instanceof DOMCryptoContext)) {
            throw new ClassCastException("context must be of type DOMCryptoContext");
        }
        if (!(parent instanceof DOMStructure)) {
            throw new ClassCastException("parent must be of type DOMStructure");
        }
        this.transformElement = (Element)((DOMStructure)parent).getNode();
    }

    @Override
    public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
        if (context != null && !(context instanceof DOMCryptoContext)) {
            throw new ClassCastException("context must be of type DOMCryptoContext");
        }
        if (!(parent instanceof DOMStructure)) {
            throw new ClassCastException("parent must be of type DOMStructure");
        }
        Element transformElement2 = (Element)((DOMStructure)parent).getNode();
        STRTransform.appendChild(transformElement2, this.transformElement);
        this.transformElement = transformElement2;
    }

    @Override
    public Data transform(Data data, XMLCryptoContext xc) throws TransformException {
        if (data == null) {
            throw new NullPointerException("data must not be null");
        }
        return this.transformIt(data, xc, null);
    }

    @Override
    public Data transform(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
        if (data == null) {
            throw new NullPointerException("data must not be null");
        }
        if (os == null) {
            throw new NullPointerException("output stream must not be null");
        }
        return this.transformIt(data, xc, os);
    }

    private Data transformIt(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
        String canonAlgo = null;
        Element transformParams = XMLUtils.getDirectChildElement(this.transformElement, "TransformationParameters", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        if (transformParams != null) {
            Element canonElem = XMLUtils.getDirectChildElement(transformParams, "CanonicalizationMethod", "http://www.w3.org/2000/09/xmldsig#");
            canonAlgo = canonElem.getAttributeNS(null, "Algorithm");
        }
        try {
            String type;
            Document doc;
            Element dereferencedToken;
            Element str = null;
            if (data instanceof NodeSetData) {
                NodeSetData nodeSetData = (NodeSetData)data;
                for (Node node : nodeSetData) {
                    if (!(node instanceof Element) || !"SecurityTokenReference".equals(node.getLocalName())) continue;
                    str = (Element)node;
                    break;
                }
            } else {
                try {
                    XMLSignatureInput xmlSignatureInput = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
                    str = (Element)xmlSignatureInput.getSubNode();
                }
                catch (Exception ex) {
                    throw new TransformException(ex);
                }
            }
            if (str == null) {
                throw new TransformException("No SecurityTokenReference found");
            }
            SecurityTokenReference secRef = new SecurityTokenReference(str, new BSPEnforcer());
            Canonicalizer canon = Canonicalizer.getInstance(canonAlgo);
            byte[] buf = null;
            Object wsDocInfoObject = xc.getProperty(TRANSFORM_WS_DOC_INFO);
            WSDocInfo wsDocInfo = null;
            if (wsDocInfoObject instanceof WSDocInfo) {
                wsDocInfo = (WSDocInfo)wsDocInfoObject;
            }
            if (wsDocInfo == null) {
                LOG.debug("STRTransform: no WSDocInfo found");
            }
            if ((dereferencedToken = STRTransformUtil.dereferenceSTR(doc = str.getOwnerDocument(), secRef, wsDocInfo)) != null && ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(type = dereferencedToken.getAttributeNS(null, "ValueType")) || PKIPathSecurity.getType().equals(type))) {
                XMLUtils.setNamespace(dereferencedToken, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
                XMLUtils.setNamespace(dereferencedToken, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu");
            }
            buf = canon.canonicalizeSubtree(dereferencedToken, "#default", true);
            if (LOG.isDebugEnabled()) {
                LOG.debug("after c14n: " + new String(buf, StandardCharsets.UTF_8));
            }
            if (os != null) {
                os.write(buf);
                return null;
            }
            return new OctetStreamData(new ByteArrayInputStream(buf));
        }
        catch (Exception ex) {
            throw new TransformException(ex);
        }
    }

    @Override
    public final boolean isFeatureSupported(String feature) {
        if (feature == null) {
            throw new NullPointerException();
        }
        return false;
    }

    private static void appendChild(Node parent, Node child) {
        Document ownerDoc = null;
        ownerDoc = parent.getNodeType() == 9 ? (Document)parent : parent.getOwnerDocument();
        if (child.getOwnerDocument() != ownerDoc) {
            parent.appendChild(ownerDoc.importNode(child, true));
        } else {
            parent.appendChild(child);
        }
    }
}

