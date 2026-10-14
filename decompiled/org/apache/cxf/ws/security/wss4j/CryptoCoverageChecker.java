/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.w3c.dom.Element;

public class CryptoCoverageChecker
extends AbstractSoapInterceptor {
    protected List<XPathExpression> xPaths = new ArrayList<XPathExpression>();
    protected Map<String, String> prefixMap = new HashMap<String, String>();
    private boolean checkFaults = true;

    public CryptoCoverageChecker() {
        this((Map<String, String>)null, (List<XPathExpression>)null);
    }

    public CryptoCoverageChecker(Map<String, String> prefixes, List<XPathExpression> xPaths) {
        super("pre-protocol");
        this.addAfter(WSS4JInInterceptor.class.getName());
        this.setPrefixes(prefixes);
        this.setXPaths(xPaths);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        if (this.xPaths == null || this.xPaths.isEmpty()) {
            // empty if block
        }
        if (message.getContent(SOAPMessage.class) == null) {
            throw new SoapFault("Error obtaining SOAP document", Fault.FAULT_CODE_CLIENT);
        }
        Element documentElement = null;
        try {
            SOAPMessage saajDoc = message.getContent(SOAPMessage.class);
            SOAPEnvelope envelope = saajDoc.getSOAPPart().getEnvelope();
            if (!this.checkFaults && envelope.getBody().hasFault()) {
                return;
            }
            documentElement = envelope;
            documentElement = (Element)DOMUtils.getDomElement(documentElement);
        }
        catch (SOAPException e) {
            throw new SoapFault("Error obtaining SOAP document", Fault.FAULT_CODE_CLIENT);
        }
        HashSet<WSDataRef> signed = new HashSet<WSDataRef>();
        HashSet<WSDataRef> encrypted = new HashSet<WSDataRef>();
        List<WSHandlerResult> results = CastUtils.cast((List)message.get("RECV_RESULTS"));
        if (results != null) {
            for (WSHandlerResult wshr : results) {
                List<WSSecurityEngineResult> encryptedResults;
                List<WSSecurityEngineResult> signedResults = wshr.getActionResults().get(2);
                if (signedResults != null) {
                    for (WSSecurityEngineResult wSSecurityEngineResult : signedResults) {
                        List sl = CastUtils.cast((List)wSSecurityEngineResult.get("data-ref-uris"));
                        if (sl == null || sl.size() == 1 && ((WSDataRef)sl.get(0)).getName().equals(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"))) continue;
                        signed.addAll(sl);
                    }
                }
                if ((encryptedResults = wshr.getActionResults().get(4)) == null) continue;
                for (WSSecurityEngineResult encryptedResult : encryptedResults) {
                    List el = CastUtils.cast((List)encryptedResult.get("data-ref-uris"));
                    if (el == null) continue;
                    encrypted.addAll(el);
                }
            }
        }
        CryptoCoverageUtil.reconcileEncryptedSignedRefs(signed, encrypted);
        XPathFactory factory2 = XPathFactory.newInstance();
        try {
            factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        }
        catch (XPathFactoryConfigurationException wshr) {
            // empty catch block
        }
        XPath xpath = factory2.newXPath();
        if (this.prefixMap != null) {
            xpath.setNamespaceContext(new MapNamespaceContext(this.prefixMap));
        }
        for (XPathExpression xPathExpression : this.xPaths) {
            Object var10_16 = null;
            switch (xPathExpression.getType()) {
                case SIGNED: {
                    HashSet<WSDataRef> hashSet = signed;
                    break;
                }
                case ENCRYPTED: {
                    HashSet<WSDataRef> hashSet = encrypted;
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected crypto type: " + (Object)((Object)xPathExpression.getType()));
                }
            }
            try {
                void var10_19;
                CryptoCoverageUtil.checkCoverage(documentElement, (Collection<WSDataRef>)var10_19, xpath, Arrays.asList(xPathExpression.getXPath()), xPathExpression.getType(), xPathExpression.getScope());
            }
            catch (WSSecurityException e) {
                throw new SoapFault("No " + (Object)((Object)xPathExpression.getType()) + " element found matching XPath " + xPathExpression.getXPath(), Fault.FAULT_CODE_CLIENT);
            }
        }
    }

    public final void setXPaths(List<XPathExpression> xpaths) {
        this.xPaths.clear();
        if (xpaths != null) {
            this.xPaths.addAll(xpaths);
        }
    }

    public final void addXPaths(List<XPathExpression> xpaths) {
        if (xpaths != null) {
            this.xPaths.addAll(xpaths);
        }
    }

    public final void setPrefixes(Map<String, String> prefixes) {
        this.prefixMap.clear();
        if (prefixes != null) {
            this.prefixMap.putAll(prefixes);
        }
    }

    public final void addPrefixes(Map<String, String> prefixes) {
        if (prefixes != null) {
            this.prefixMap.putAll(prefixes);
        }
    }

    public boolean isCheckFaults() {
        return this.checkFaults;
    }

    public void setCheckFaults(boolean checkFaults) {
        this.checkFaults = checkFaults;
    }

    public static class XPathExpression {
        private final String xPath;
        private final CryptoCoverageUtil.CoverageType type;
        private final CryptoCoverageUtil.CoverageScope scope;

        public XPathExpression(String xPath, CryptoCoverageUtil.CoverageType type) {
            this(xPath, type, CryptoCoverageUtil.CoverageScope.ELEMENT);
        }

        public XPathExpression(String xPath, CryptoCoverageUtil.CoverageType type, CryptoCoverageUtil.CoverageScope scope) {
            if (xPath == null) {
                throw new NullPointerException("xPath cannot be null.");
            }
            if (type == null) {
                throw new NullPointerException("type cannot be null.");
            }
            this.xPath = xPath;
            this.type = type;
            this.scope = scope;
        }

        public String getXPath() {
            return this.xPath;
        }

        public CryptoCoverageUtil.CoverageType getType() {
            return this.type;
        }

        public CryptoCoverageUtil.CoverageScope getScope() {
            return this.scope;
        }

        public boolean equals(Object xpathObject) {
            if (!(xpathObject instanceof XPathExpression)) {
                return false;
            }
            if (xpathObject == this) {
                return true;
            }
            XPathExpression xpath = (XPathExpression)xpathObject;
            if (xpath.getScope() != this.getScope()) {
                return false;
            }
            if (xpath.getType() != this.getType()) {
                return false;
            }
            if (this.getXPath() == null && xpath.getXPath() != null) {
                return false;
            }
            return this.getXPath() == null || this.getXPath().equals(xpath.getXPath());
        }

        public int hashCode() {
            int result = 17;
            if (this.getXPath() != null) {
                result = 31 * result + this.getXPath().hashCode();
            }
            if (this.getType() != null) {
                result = 31 * result + this.getType().hashCode();
            }
            if (this.getScope() != null) {
                result = 31 * result + this.getScope().hashCode();
            }
            return result;
        }
    }
}

