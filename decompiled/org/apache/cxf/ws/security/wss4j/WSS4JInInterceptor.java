/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import java.security.Provider;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JInterceptor;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.CXFCallbackLookup;
import org.apache.cxf.ws.security.wss4j.CXFRequestData;
import org.apache.cxf.ws.security.wss4j.DefaultWSS4JSecurityContextCreator;
import org.apache.cxf.ws.security.wss4j.DelegatingCallbackHandler;
import org.apache.cxf.ws.security.wss4j.StaxSerializer;
import org.apache.cxf.ws.security.wss4j.TokenStoreCallbackHandler;
import org.apache.cxf.ws.security.wss4j.WSS4JSecurityContextCreator;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngine;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.dom.validate.NoOpValidator;
import org.apache.wss4j.dom.validate.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSS4JInInterceptor
extends AbstractWSS4JInterceptor {
    public static final String SAML_ROLE_ATTRIBUTENAME_DEFAULT = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
    public static final String PROCESSOR_MAP = "wss4j.processor.map";
    public static final String VALIDATOR_MAP = "wss4j.validator.map";
    public static final String SECURITY_PROCESSED = WSS4JInInterceptor.class.getName() + ".DONE";
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JInInterceptor.class);
    private boolean ignoreActions;
    private WSSConfig defaultConfig;

    public WSS4JInInterceptor() {
        this.setPhase("pre-protocol");
        this.getAfter().add(SAAJInInterceptor.class.getName());
        this.getAfter().add("org.apache.cxf.ws.addressing.soap.MAPCodec");
    }

    public WSS4JInInterceptor(boolean ignore) {
        this();
        this.ignoreActions = ignore;
    }

    public WSS4JInInterceptor(Map<String, Object> properties) {
        this();
        Map validatorMap;
        this.setProperties(properties);
        WSSConfig config = WSSConfig.getNewInstance();
        Map processorMap = CastUtils.cast((Map)properties.get(PROCESSOR_MAP));
        if (processorMap != null) {
            for (Map.Entry entry : processorMap.entrySet()) {
                Object val = entry.getValue();
                if (val instanceof Class) {
                    config.setProcessor((QName)entry.getKey(), (Class)val);
                    continue;
                }
                if (val instanceof Processor) {
                    config.setProcessor((QName)entry.getKey(), (Processor)val);
                    continue;
                }
                if (val != null) continue;
                config.setProcessor((QName)entry.getKey(), (Class)null);
            }
        }
        if ((validatorMap = CastUtils.cast((Map)properties.get(VALIDATOR_MAP))) == null) {
            validatorMap = CastUtils.cast((Map)properties.get("validatorMap"));
        }
        if (validatorMap != null) {
            for (Map.Entry entry : validatorMap.entrySet()) {
                Object val = entry.getValue();
                if (val instanceof Class) {
                    config.setValidator((QName)entry.getKey(), (Class)val);
                    continue;
                }
                if (!(val instanceof Validator)) continue;
                config.setValidator((QName)entry.getKey(), (Validator)val);
            }
        }
        this.defaultConfig = config;
    }

    public void setIgnoreActions(boolean i) {
        this.ignoreActions = i;
    }

    private SOAPMessage getSOAPMessage(SoapMessage msg) {
        SAAJInInterceptor.INSTANCE.handleMessage(msg);
        return msg.getContent(SOAPMessage.class);
    }

    @Override
    public Object getProperty(Object msgContext, String key) {
        Object result = super.getProperty(msgContext, key);
        if (result == null && "_sendSignatureValues_".equals(key) && this.isRequestor((SoapMessage)msgContext)) {
            result = ((SoapMessage)msgContext).getExchange().getOutMessage().get(key);
        }
        return result;
    }

    public final boolean isGET(SoapMessage message) {
        String method2 = (String)message.get("org.apache.cxf.request.method");
        return "GET".equals(method2) && message.getContent(XMLStreamReader.class) == null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        if (msg.containsKey(SECURITY_PROCESSED) || this.isGET(msg) || msg.getExchange() == null) {
            return;
        }
        Provider provider = msg.getExchange().get(Provider.class);
        boolean useCustomProvider = provider != null && ThreadLocalSecurityProvider.isInstalled();
        try {
            if (useCustomProvider) {
                ThreadLocalSecurityProvider.setProvider(provider);
            }
            this.handleMessageInternal(msg);
        }
        finally {
            if (useCustomProvider) {
                ThreadLocalSecurityProvider.unsetProvider();
            }
        }
    }

    private void handleMessageInternal(SoapMessage msg) throws Fault {
        WSSecurityEngine engine;
        boolean utWithCallbacks = MessageUtils.getContextualBoolean(msg, "ws-security.validate.token", true);
        this.translateProperties(msg);
        CXFRequestData reqData = new CXFRequestData();
        WSSConfig config = (WSSConfig)msg.getContextualProperty(WSSConfig.class.getName());
        if (config != null) {
            engine = new WSSecurityEngine();
            engine.setWssConfig(config);
        } else {
            engine = this.getSecurityEngine(utWithCallbacks);
            if (engine == null) {
                engine = new WSSecurityEngine();
            }
            config = engine.getWssConfig();
        }
        reqData.setWssConfig(config);
        reqData.setEncryptionSerializer(new StaxSerializer());
        reqData.setAudienceRestrictions(SAMLUtils.getAudienceRestrictions(msg, true));
        SOAPMessage doc = this.getSOAPMessage(msg);
        boolean doDebug = LOG.isLoggable(Level.FINE);
        SoapVersion version = msg.getVersion();
        if (doDebug) {
            LOG.fine("WSS4JInInterceptor: enter handleMessage()");
        }
        try {
            reqData.setMsgContext(msg);
            reqData.setAttachmentCallbackHandler(new AttachmentCallbackHandler(msg));
            this.setAlgorithmSuites(msg, reqData);
            reqData.setCallbackHandler(this.getCallback(reqData, utWithCallbacks));
            this.computeAction(msg, reqData);
            String action = this.getAction(msg, version);
            List<Integer> actions = WSSecurityUtil.decodeAction(action);
            String actor = (String)this.getOption("actor");
            if (actor == null) {
                actor = (String)msg.getContextualProperty("ws-security.actor");
            }
            reqData.setActor(actor);
            this.configureReplayCaches(reqData, actions, msg);
            TLSSessionInfo tlsInfo = msg.get(TLSSessionInfo.class);
            if (tlsInfo != null) {
                Certificate[] tlsCerts = tlsInfo.getPeerCertificates();
                reqData.setTlsCerts(tlsCerts);
            }
            this.doReceiverAction(actions, reqData);
            if (this.getString("expandXOPIncludeForSignature", msg) == null && this.getString("expandXOPInclude", msg) == null) {
                reqData.setExpandXopInclude(AttachmentUtil.isMtomEnabled(msg));
            }
            boolean enableRevocation = reqData.isRevocationEnabled() || PropertyUtils.isTrue(SecurityUtils.getSecurityPropertyValue("security.enableRevocation", msg));
            reqData.setEnableRevocation(enableRevocation);
            SOAPBody soapBody = SAAJUtils.getBody(doc);
            if (soapBody != null) {
                engine.setCallbackLookup(new CXFCallbackLookup(soapBody.getOwnerDocument(), soapBody));
            }
            Element elem = WSSecurityUtil.getSecurityHeader(doc.getSOAPHeader(), actor, version.getVersion() != 1.1);
            elem = (Element)DOMUtils.getDomElement(elem);
            Node originalNode = null;
            if (elem != null) {
                originalNode = elem.cloneNode(true);
            }
            WSHandlerResult wsResult = engine.processSecurityHeader(elem, (RequestData)reqData);
            this.importNewDomToSAAJ(doc, elem, originalNode, wsResult);
            Element header = SAAJUtils.getHeader(doc);
            Element body2 = SAAJUtils.getBody(doc);
            header = (Element)DOMUtils.getDomElement(header);
            body2 = (Element)DOMUtils.getDomElement(body2);
            if (wsResult.getResults() != null && !wsResult.getResults().isEmpty()) {
                if (reqData.isEnableSignatureConfirmation()) {
                    this.checkSignatureConfirmation(reqData, wsResult);
                }
                this.checkActions(msg, wsResult.getResults(), actions);
                this.doResults(msg, actor, header, body2, wsResult, utWithCallbacks);
            } else if (doc.getSOAPPart().getEnvelope().getBody().hasFault() && this.isRequestor(msg)) {
                LOG.warning("The request is a SOAP Fault, but it is not secured");
                this.doResults(msg, actor, header, body2, wsResult, utWithCallbacks);
            } else {
                this.checkActions(msg, wsResult.getResults(), actions);
                this.doResults(msg, actor, header, body2, wsResult, utWithCallbacks);
            }
            if (SAAJUtils.getBody(doc) != null) {
                this.advanceBody(msg, body2);
            }
            SAAJInInterceptor.replaceHeaders(doc, msg);
            if (doDebug) {
                LOG.fine("WSS4JInInterceptor: exit handleMessage()");
            }
            msg.put(SECURITY_PROCESSED, (Object)Boolean.TRUE);
        }
        catch (WSSecurityException e) {
            throw WSS4JUtils.createSoapFault(msg, version, e);
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new Message("STAX_EX", LOG, new Object[0]), (Throwable)e, version.getSender());
        }
        catch (SOAPException e) {
            throw new SoapFault(new Message("SAAJ_EX", LOG, new Object[0]), (Throwable)e, version.getSender());
        }
        finally {
            reqData = null;
        }
    }

    private void importNewDomToSAAJ(SOAPMessage doc, Element elem, Node originalNode, WSHandlerResult wsResult) throws SOAPException {
        if (DOMUtils.isJava9SAAJ() && originalNode != null && !originalNode.isEqualNode(elem)) {
            Node node = null;
            Document document = null;
            SOAPBody body2 = SAAJUtils.getBody(doc);
            if (body2 != null) {
                document = body2.getOwnerDocument();
            }
            if (elem != null && elem.getOwnerDocument() != null && elem.getOwnerDocument().getDocumentElement() != null) {
                node = elem.getOwnerDocument().getDocumentElement().getFirstChild().getNextSibling().getFirstChild();
            }
            if (document != null && node != null) {
                Node newNode = null;
                try {
                    newNode = DOMUtils.getDomElement(document.importNode(node, true));
                    elem.getOwnerDocument().getDocumentElement().getFirstChild().getNextSibling().replaceChild(newNode, node);
                    List<WSSecurityEngineResult> encryptResults = wsResult.getActionResults().get(4);
                    if (encryptResults != null) {
                        for (WSSecurityEngineResult result : wsResult.getActionResults().get(4)) {
                            List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
                            for (WSDataRef dataRef : dataRefs) {
                                if (dataRef.getProtectedElement() != node) continue;
                                dataRef.setProtectedElement((Element)newNode);
                            }
                        }
                    }
                    ArrayList signedResults = new ArrayList();
                    if (wsResult.getActionResults().containsKey(2)) {
                        signedResults.addAll(wsResult.getActionResults().get(2));
                    }
                    if (wsResult.getActionResults().containsKey(64)) {
                        signedResults.addAll(wsResult.getActionResults().get(64));
                    }
                    if (wsResult.getActionResults().containsKey(16)) {
                        signedResults.addAll(wsResult.getActionResults().get(16));
                    }
                    for (WSSecurityEngineResult result : signedResults) {
                        List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
                        for (WSDataRef dataRef : dataRefs) {
                            if (dataRef.getProtectedElement() != node) continue;
                            dataRef.setProtectedElement((Element)newNode);
                        }
                    }
                }
                catch (Exception ex) {
                    LOG.log(Level.FINE, "Something wrong during importNewDomToSAAJ", ex);
                }
            }
        }
    }

    protected void checkActions(SoapMessage msg, List<WSSecurityEngineResult> wsResult, List<Integer> actions) throws WSSecurityException {
        if (this.ignoreActions) {
            return;
        }
        if (!this.checkReceiverResultsAnyOrder(wsResult, actions)) {
            LOG.warning("Security processing failed (actions mismatch)");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        String signatureParts = (String)this.getProperty(msg, "signatureParts");
        if (signatureParts != null) {
            String warning = "To enforce that particular elements were signed you must either use WS-SecurityPolicy, or else use the CryptoCoverageChecker or DefaultCryptoCoverageChecker";
            LOG.warning(warning);
        }
    }

    protected void computeAction(SoapMessage msg, RequestData reqData) throws WSSecurityException {
        Crypto sigCrypto;
        Crypto encCrypto = (Crypto)SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", msg);
        if (encCrypto != null) {
            reqData.setDecCrypto(encCrypto);
        }
        if ((sigCrypto = (Crypto)SecurityUtils.getSecurityPropertyValue("security.signature.crypto", msg)) != null) {
            reqData.setSigVerCrypto(sigCrypto);
        }
    }

    protected void configureReplayCaches(RequestData reqData, List<Integer> actions, SoapMessage msg) throws WSSecurityException {
        if (this.isNonceCacheRequired(actions, msg)) {
            ReplayCache nonceCache = this.getReplayCache(msg, "ws-security.enable.nonce.cache", "ws-security.nonce.cache.instance");
            reqData.setNonceReplayCache(nonceCache);
        }
        if (this.isTimestampCacheRequired(actions, msg)) {
            ReplayCache timestampCache = this.getReplayCache(msg, "ws-security.enable.timestamp.cache", "ws-security.timestamp.cache.instance");
            reqData.setTimestampReplayCache(timestampCache);
        }
        if (this.isSamlCacheRequired(actions, msg)) {
            ReplayCache samlCache = this.getReplayCache(msg, "ws-security.enable.saml.cache", "ws-security.saml.cache.instance");
            reqData.setSamlOneTimeUseReplayCache(samlCache);
        }
    }

    protected boolean isNonceCacheRequired(List<Integer> actions, SoapMessage msg) {
        return actions.contains(1) || actions.contains(8192);
    }

    protected boolean isTimestampCacheRequired(List<Integer> actions, SoapMessage msg) {
        return actions.contains(32);
    }

    protected boolean isSamlCacheRequired(List<Integer> actions, SoapMessage msg) {
        return actions.contains(8) || actions.contains(16);
    }

    protected void setAlgorithmSuites(SoapMessage message, RequestData data) throws WSSecurityException {
        super.decodeAlgorithmSuite(data);
    }

    protected void doResults(SoapMessage msg, String actor, Element soapHeader, Element soapBody, WSHandlerResult wsResult, boolean utWithCallbacks) throws SOAPException, XMLStreamException, WSSecurityException {
        List results = CastUtils.cast((List)msg.get("RECV_RESULTS"));
        if (results == null) {
            results = new LinkedList();
            msg.put("RECV_RESULTS", (Object)results);
        }
        results.add(0, wsResult);
        WSS4JSecurityContextCreator contextCreator = (WSS4JSecurityContextCreator)SecurityUtils.getSecurityPropertyValue("ws-security.security.context.creator", msg);
        if (contextCreator != null) {
            contextCreator.createSecurityContext(msg, wsResult);
        } else {
            new DefaultWSS4JSecurityContextCreator().createSecurityContext(msg, wsResult);
        }
    }

    protected void advanceBody(SoapMessage msg, Node body2) throws SOAPException, XMLStreamException, WSSecurityException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(new DOMSource(body2));
        int evt = reader.next();
        if (reader.hasNext() && evt != 2) {
            reader.next();
        }
        msg.setContent(XMLStreamReader.class, reader);
    }

    private String getAction(SoapMessage msg, SoapVersion version) {
        String action = (String)this.getOption("action");
        if (action == null) {
            action = (String)msg.get("action");
        }
        if (action == null && !this.ignoreActions) {
            LOG.warning("No security action was defined!");
            throw new SoapFault("No security action was defined!", version.getReceiver());
        }
        return action;
    }

    protected CallbackHandler getCallback(RequestData reqData, boolean utWithCallbacks) throws WSSecurityException {
        if (!utWithCallbacks) {
            CallbackHandler pwdCallback = null;
            try {
                pwdCallback = this.getCallback(reqData);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return new DelegatingCallbackHandler(pwdCallback);
        }
        return this.getCallback(reqData);
    }

    protected CallbackHandler getCallback(RequestData reqData) throws WSSecurityException {
        Endpoint ep;
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", (SoapMessage)reqData.getMsgContext());
        CallbackHandler cbHandler = null;
        try {
            cbHandler = SecurityUtils.getCallbackHandler(o);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        if (cbHandler == null) {
            try {
                cbHandler = this.getPasswordCallbackHandler(reqData);
            }
            catch (WSSecurityException sec) {
                Endpoint ep2 = ((SoapMessage)reqData.getMsgContext()).getExchange().getEndpoint();
                if (ep2 != null && ep2.getEndpointInfo() != null) {
                    TokenStore store = TokenStoreUtils.getTokenStore((SoapMessage)reqData.getMsgContext());
                    return new TokenStoreCallbackHandler(null, store);
                }
                throw sec;
            }
        }
        if (cbHandler == null) {
            final String signatureUser = (String)SecurityUtils.getSecurityPropertyValue("security.signature.username", (SoapMessage)reqData.getMsgContext());
            final String password = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", (SoapMessage)reqData.getMsgContext());
            if (!StringUtils.isEmpty(signatureUser) && !StringUtils.isEmpty(password)) {
                cbHandler = new CallbackHandler(){

                    @Override
                    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                        for (Callback c : callbacks) {
                            WSPasswordCallback pwCallback = (WSPasswordCallback)c;
                            if (1 != pwCallback.getUsage() || !signatureUser.equals(pwCallback.getIdentifier())) continue;
                            pwCallback.setPassword(password);
                        }
                    }
                };
            }
        }
        if ((ep = ((SoapMessage)reqData.getMsgContext()).getExchange().getEndpoint()) != null && ep.getEndpointInfo() != null) {
            TokenStore store = TokenStoreUtils.getTokenStore((SoapMessage)reqData.getMsgContext());
            return new TokenStoreCallbackHandler(cbHandler, store);
        }
        return cbHandler;
    }

    protected WSSecurityEngine getSecurityEngine(boolean utWithCallbacks) {
        if (!utWithCallbacks) {
            WSSConfig config = WSSConfig.getNewInstance();
            config.setValidator(WSConstants.USERNAME_TOKEN, new NoOpValidator());
            WSSecurityEngine ret = new WSSecurityEngine();
            ret.setWssConfig(config);
            return ret;
        }
        if (this.defaultConfig != null) {
            WSSecurityEngine engine = new WSSecurityEngine();
            engine.setWssConfig(this.defaultConfig);
            return engine;
        }
        return null;
    }

    protected ReplayCache getReplayCache(SoapMessage message, String booleanKey, String instanceKey) {
        return WSS4JUtils.getReplayCache(message, booleanKey, instanceKey);
    }
}

