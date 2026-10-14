/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.cxf.ws.mex.MetadataExchange
 *  org.apache.cxf.ws.mex.model._2004_09.Metadata
 *  org.apache.cxf.ws.mex.model._2004_09.MetadataSection
 */
package org.apache.cxf.ws.security.trust;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.wsdl.Definition;
import javax.wsdl.Types;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.databinding.source.SourceDataBinding;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.VersionTransformer;
import org.apache.cxf.ws.mex.MetadataExchange;
import org.apache.cxf.ws.mex.model._2004_09.Metadata;
import org.apache.cxf.ws.mex.model._2004_09.MetadataSection;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.attachment.reference.RemoteReferenceResolver;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.DefaultSymmetricBinding;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.cxf.ws.security.trust.TrustException;
import org.apache.cxf.ws.security.trust.claims.ClaimsCallback;
import org.apache.cxf.ws.security.trust.delegation.DelegationCallback;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.WSDLServiceFactory;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyRegistry;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.derivedKey.P_SHA1;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.Reference;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.EncryptedKeyProcessor;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.dom.util.X509Util;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.Trust10;
import org.apache.wss4j.policy.model.Trust13;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.keyvalues.DSAKeyValue;
import org.apache.xml.security.keys.content.keyvalues.RSAKeyValue;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AbstractSTSClient
implements Configurable,
InterceptorProvider {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractSTSClient.class);
    protected Bus bus;
    protected String name = "default.sts-client";
    protected Client client;
    protected String location;
    protected String wsdlLocation;
    protected QName serviceName;
    protected QName endpointName;
    protected Policy policy;
    protected String soapVersion = "http://schemas.xmlsoap.org/soap/";
    protected int keySize = 256;
    protected boolean requiresEntropy = true;
    protected Element template;
    protected Object customContent;
    protected Object claims;
    protected CallbackHandler claimsCallbackHandler;
    protected AlgorithmSuite algorithmSuite;
    protected String namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    protected String addressingNamespace = "http://www.w3.org/2005/08/addressing";
    protected String wspNamespace = "http://www.w3.org/ns/ws-policy";
    protected Object onBehalfOf;
    protected boolean enableAppliesTo = true;
    protected boolean useCertificateForConfirmationKeyInfo;
    protected boolean isSecureConv;
    protected boolean isSpnego;
    protected boolean enableLifetime;
    protected int ttl = 300;
    protected boolean sendRenewing = true;
    protected boolean allowRenewing = true;
    protected boolean allowRenewingAfterExpiry;
    protected Object actAs;
    protected String tokenType;
    protected String keyType;
    protected boolean sendKeyType = true;
    protected Message message;
    protected String context;
    protected X509Certificate useKeyCertificate;
    protected Map<String, Object> ctx = new HashMap<String, Object>();
    protected List<Interceptor<? extends Message>> in = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    protected List<Interceptor<? extends Message>> out = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    protected List<Interceptor<? extends Message>> outFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    protected List<Interceptor<? extends Message>> inFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    protected List<Feature> features;
    protected TLSClientParameters tlsClientParameters;

    public AbstractSTSClient(Bus b) {
        this.bus = b;
    }

    @Override
    public String getBeanName() {
        return this.name;
    }

    public void setBeanName(String s2) {
        this.name = s2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setEnableLifetime(boolean enableLifetime) {
        this.enableLifetime = enableLifetime;
    }

    public void setSendRenewing(boolean sendRenewing) {
        this.sendRenewing = sendRenewing;
    }

    public void setTlsClientParameters(TLSClientParameters tlsClientParameters) {
        this.tlsClientParameters = tlsClientParameters;
    }

    public void setPolicy(Object newPolicy) {
        if (newPolicy instanceof Policy) {
            this.setPolicyInternal((Policy)newPolicy);
        } else if (newPolicy instanceof Element) {
            this.setPolicyInternal((Element)newPolicy);
        } else if (newPolicy instanceof String) {
            this.setPolicyInternal((String)newPolicy);
        } else {
            throw new IllegalArgumentException("Unsupported policy object.  Type must be org.apache.neethi.Policy or org.w3c.dom.Element.");
        }
    }

    public void setSoap12() {
        this.soapVersion = "http://schemas.xmlsoap.org/wsdl/soap12/";
    }

    public void setSoap11() {
        this.soapVersion = "http://schemas.xmlsoap.org/soap/";
    }

    public void setSoap11(boolean b) {
        if (b) {
            this.setSoap11();
        } else {
            this.setSoap12();
        }
    }

    public void setAddressingNamespace(String ad) {
        this.addressingNamespace = ad;
    }

    public void setTrust(Trust10 trust) {
        if (trust != null) {
            this.namespace = trust instanceof Trust13 ? "http://docs.oasis-open.org/ws-sx/ws-trust/200512" : "http://schemas.xmlsoap.org/ws/2005/02/trust";
            this.requiresEntropy = trust.isRequireClientEntropy();
        }
    }

    public boolean isRequiresEntropy() {
        return this.requiresEntropy;
    }

    public void setRequiresEntropy(boolean requiresEntropy) {
        this.requiresEntropy = requiresEntropy;
    }

    public boolean isSecureConv() {
        return this.isSecureConv;
    }

    public void setSecureConv(boolean secureConv) {
        this.isSecureConv = secureConv;
    }

    public boolean isSpnego() {
        return this.isSpnego;
    }

    public void setSpnego(boolean spnego) {
        this.isSpnego = spnego;
    }

    public boolean isAllowRenewing() {
        return this.allowRenewing;
    }

    public void setAllowRenewing(boolean allowRenewing) {
        this.allowRenewing = allowRenewing;
    }

    public boolean isAllowRenewingAfterExpiry() {
        return this.allowRenewingAfterExpiry;
    }

    public void setAllowRenewingAfterExpiry(boolean allowRenewingAfterExpiry) {
        this.allowRenewingAfterExpiry = allowRenewingAfterExpiry;
    }

    public boolean isEnableAppliesTo() {
        return this.enableAppliesTo;
    }

    public void setEnableAppliesTo(boolean enableAppliesTo) {
        this.enableAppliesTo = enableAppliesTo;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setAlgorithmSuite(AlgorithmSuite ag) {
        this.algorithmSuite = ag;
    }

    public Map<String, Object> getRequestContext() {
        return this.ctx;
    }

    public void setProperties(Map<String, Object> p) {
        this.ctx.putAll(p);
    }

    public Map<String, Object> getProperties() {
        return this.ctx;
    }

    public void setWsdlLocation(String wsdl) {
        this.wsdlLocation = wsdl;
    }

    public String getWsdlLocation() {
        return this.wsdlLocation;
    }

    public void setServiceName(String qn) {
        this.serviceName = QName.valueOf(qn);
    }

    public void setEndpointName(String qn) {
        this.endpointName = QName.valueOf(qn);
    }

    public void setServiceQName(QName qn) {
        this.serviceName = qn;
    }

    public QName getServiceQName() {
        return this.serviceName;
    }

    public void setEndpointQName(QName qn) {
        this.endpointName = qn;
    }

    public QName getEndpointQName() {
        return this.endpointName;
    }

    public void setActAs(Object actAs) {
        this.actAs = actAs;
    }

    public void setCustomContent(Object customContent) {
        this.customContent = customContent;
    }

    public void setKeySize(int i) {
        this.keySize = i;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setSendKeyType(boolean sendKeyType) {
        this.sendKeyType = sendKeyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    @Deprecated
    public void setOnBehalfOfElement(Element onBehalfOfElement) {
        this.onBehalfOf = onBehalfOfElement;
    }

    public void setOnBehalfOf(Object onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    public void setUseCertificateForConfirmationKeyInfo(boolean useCertificate) {
        this.useCertificateForConfirmationKeyInfo = useCertificate;
    }

    public boolean isUseCertificateForConfirmationKeyInfo() {
        return this.useCertificateForConfirmationKeyInfo;
    }

    protected void setPolicyInternal(Policy newPolicy) {
        this.policy = newPolicy;
        if (this.algorithmSuite == null) {
            Iterator<List<Assertion>> i = this.policy.getAlternatives();
            while (i.hasNext() && this.algorithmSuite == null) {
                List<PolicyComponent> p = CastUtils.cast(i.next());
                for (PolicyComponent p2 : p) {
                    if (!(p2 instanceof AbstractBinding)) continue;
                    this.algorithmSuite = ((AbstractBinding)p2).getAlgorithmSuite();
                }
            }
        }
    }

    protected void setPolicyInternal(Element newPolicy) {
        this.setPolicyInternal(this.bus.getExtension(PolicyBuilder.class).getPolicy(newPolicy));
    }

    protected void setPolicyInternal(String policyReference) {
        PolicyBuilder builder = this.bus.getExtension(PolicyBuilder.class);
        RemoteReferenceResolver resolver2 = new RemoteReferenceResolver(null, builder);
        PolicyRegistry registry = this.bus.getExtension(PolicyEngine.class).getRegistry();
        Policy resolved = registry.lookup(policyReference);
        if (null != resolved) {
            this.setPolicyInternal(resolved);
        } else {
            this.setPolicyInternal(resolver2.resolveReference(policyReference));
        }
    }

    public Client getClient() throws BusException, EndpointException {
        if (this.client == null) {
            this.createClient();
        }
        return this.client;
    }

    public void configureViaEPR(EndpointReferenceType ref, boolean useEPRWSAAddrAsMEXLocation) {
        String wsdlLoc;
        QName sName;
        if (this.client != null) {
            return;
        }
        this.location = EndpointReferenceUtils.getAddress(ref);
        if (this.location != null) {
            this.location = this.location.trim();
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("EPR address: " + this.location);
        }
        if ((sName = EndpointReferenceUtils.getServiceName(ref, this.bus)) != null) {
            this.serviceName = sName;
            QName epName = EndpointReferenceUtils.getPortQName(ref, this.bus);
            if (epName != null) {
                this.endpointName = epName;
            }
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("EPR endpoint: " + this.serviceName + " " + this.endpointName);
            }
        }
        if ((wsdlLoc = EndpointReferenceUtils.getWSDLLocation(ref)) != null) {
            this.wsdlLocation = wsdlLoc;
        }
        String mexLoc = this.findMEXLocation(ref, useEPRWSAAddrAsMEXLocation);
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("WS-MEX location: " + mexLoc);
        }
        if (mexLoc != null) {
            try {
                JaxWsProxyFactoryBean proxyFac = new JaxWsProxyFactoryBean();
                proxyFac.setBindingId(this.soapVersion);
                proxyFac.setAddress(mexLoc);
                MetadataExchange exc = proxyFac.create(MetadataExchange.class);
                Metadata metadata = exc.get2004();
                Definition definition = null;
                ArrayList<Schema> schemas = new ArrayList<Schema>();
                for (MetadataSection s2 : metadata.getMetadataSection()) {
                    if ("http://schemas.xmlsoap.org/wsdl/".equals(s2.getDialect())) {
                        definition = this.bus.getExtension(WSDLManager.class).getDefinition((Element)s2.getAny());
                        continue;
                    }
                    if (!"http://www.w3.org/2001/XMLSchema".equals(s2.getDialect())) continue;
                    Element schemaElement = (Element)s2.getAny();
                    if (schemaElement == null) {
                        String schemaLocation = s2.getLocation();
                        LOG.info("XSD schema location: " + schemaLocation);
                        schemaElement = this.downloadSchema(schemaLocation);
                    }
                    QName schemaName = new QName(schemaElement.getNamespaceURI(), schemaElement.getLocalName());
                    WSDLManager wsdlManager = this.bus.getExtension(WSDLManager.class);
                    ExtensibilityElement exElement = wsdlManager.getExtensionRegistry().createExtension(Types.class, schemaName);
                    ((Schema)exElement).setElement(schemaElement);
                    schemas.add((Schema)exElement);
                }
                if (definition != null) {
                    EndpointInfo ei;
                    for (Schema schema : schemas) {
                        definition.getTypes().addExtensibilityElement(schema);
                    }
                    WSDLServiceFactory factory2 = new WSDLServiceFactory(this.bus, definition);
                    SourceDataBinding dataBinding = new SourceDataBinding();
                    factory2.setDataBinding(dataBinding);
                    Service service2 = factory2.create();
                    service2.setDataBinding(dataBinding);
                    List<ServiceInfo> services = service2.getServiceInfos();
                    String anonymousAddress = "http://www.w3.org/2005/08/addressing/anonymous";
                    if (!anonymousAddress.equals(this.location)) {
                        for (ServiceInfo serv : services) {
                            for (EndpointInfo ei2 : serv.getEndpoints()) {
                                if (!ei2.getAddress().equals(this.location)) continue;
                                this.endpointName = ei2.getName();
                                this.serviceName = serv.getName();
                                LOG.fine("Matched endpoint to location");
                            }
                        }
                    }
                    if ((ei = service2.getEndpointInfo(this.endpointName)) == null && anonymousAddress.equals(this.location) && !services.isEmpty() && !services.get(0).getEndpoints().isEmpty()) {
                        LOG.fine("Anonymous location so taking first endpoint");
                        this.serviceName = services.get(0).getName();
                        this.endpointName = services.get(0).getEndpoints().iterator().next().getName();
                        ei = service2.getEndpointInfo(this.endpointName);
                    }
                    if (ei == null) {
                        throw new TrustException(LOG, "ADDRESS_NOT_MATCHED", this.location);
                    }
                    if (this.location != null && !anonymousAddress.equals(this.location)) {
                        ei.setAddress(this.location);
                    }
                    EndpointImpl endpoint = new EndpointImpl(this.bus, service2, ei);
                    this.client = new ClientImpl(this.bus, endpoint);
                }
            }
            catch (Exception ex) {
                throw new TrustException("WS_MEX_ERROR", ex, LOG);
            }
        }
    }

    private Element downloadSchema(String schemaLocation) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        Document document = documentBuilder.parse(schemaLocation);
        return document.getDocumentElement();
    }

    protected String findMEXLocation(EndpointReferenceType ref, boolean useEPRWSAAddrAsMEXLocation) {
        if (ref.getMetadata() != null && ref.getMetadata().getAny() != null) {
            for (Object any : ref.getMetadata().getAny()) {
                String addr;
                if (!(any instanceof Element) || (addr = this.findMEXLocation((Element)any)) == null) continue;
                return addr;
            }
        }
        return useEPRWSAAddrAsMEXLocation ? EndpointReferenceUtils.getAddress(ref) : null;
    }

    protected String findMEXLocation(Element ref) {
        Element el = DOMUtils.getFirstElement(ref);
        while (el != null) {
            if ("Address".equals(el.getLocalName()) && VersionTransformer.isSupported(el.getNamespaceURI()) && "MetadataReference".equals(ref.getLocalName())) {
                return DOMUtils.getContent(el);
            }
            String ad = this.findMEXLocation(el);
            if (ad != null) {
                return ad;
            }
            el = DOMUtils.getNextElement(el);
        }
        return null;
    }

    protected void createClient() throws BusException, EndpointException {
        if (this.client != null) {
            return;
        }
        if (this.wsdlLocation != null) {
            WSDLServiceFactory factory2 = new WSDLServiceFactory(this.bus, this.wsdlLocation, this.serviceName);
            SourceDataBinding dataBinding = new SourceDataBinding();
            factory2.setDataBinding(dataBinding);
            Service service2 = factory2.create();
            service2.setDataBinding(dataBinding);
            EndpointInfo ei = service2.getEndpointInfo(this.endpointName);
            EndpointImpl endpoint = new EndpointImpl(this.bus, service2, ei);
            this.client = new ClientImpl(this.bus, endpoint);
        } else if (this.location != null) {
            Endpoint endpoint = STSUtils.createSTSEndpoint(this.bus, this.namespace, null, this.location, this.soapVersion, this.policy, this.endpointName);
            this.client = new ClientImpl(this.bus, endpoint);
        } else {
            throw new TrustException(LOG, "NO_LOCATION", new Object[0]);
        }
        this.client.getInFaultInterceptors().addAll(this.inFault);
        this.client.getInInterceptors().addAll(this.in);
        this.client.getOutInterceptors().addAll(this.out);
        this.client.getOutFaultInterceptors().addAll(this.outFault);
        if (this.tlsClientParameters != null) {
            HTTPConduit http = (HTTPConduit)this.client.getConduit();
            http.setTlsClientParameters(this.tlsClientParameters);
        }
        this.in = null;
        this.out = null;
        this.inFault = null;
        this.outFault = null;
        if (this.features != null) {
            for (Feature f : this.features) {
                f.initialize(this.client, this.bus);
            }
        }
    }

    protected BindingOperationInfo findOperation(String suffix) {
        BindingInfo bi = this.client.getEndpoint().getBinding().getBindingInfo();
        for (BindingOperationInfo boi : bi.getOperations()) {
            String wsamAction;
            SoapOperationInfo soi = boi.getExtensor(SoapOperationInfo.class);
            String soapAction = soi != null ? soi.getAction() : null;
            Object o = boi.getOperationInfo().getInput().getExtensionAttribute(new QName("http://www.w3.org/2007/05/addressing/metadata", "Action"));
            if (o instanceof QName) {
                o = ((QName)o).getLocalPart();
            }
            String string = wsamAction = o == null ? null : o.toString();
            if ((soapAction == null || !soapAction.endsWith(suffix)) && (wsamAction == null || !wsamAction.endsWith(suffix))) continue;
            PolicyEngine pe = this.bus.getExtension(PolicyEngine.class);
            Conduit conduit = this.client.getConduit();
            EffectivePolicy effectivePolicy = pe.getEffectiveClientRequestPolicy(this.client.getEndpoint().getEndpointInfo(), boi, conduit, PhaseInterceptorChain.getCurrentMessage());
            this.setPolicyInternal(effectivePolicy.getPolicy());
            return boi;
        }
        for (BindingOperationInfo boi : bi.getOperations()) {
            if (!suffix.endsWith(boi.getName().getLocalPart())) continue;
            return boi;
        }
        for (BindingOperationInfo boi : bi.getOperations()) {
            MessagePartInfo mpi;
            if (boi.getInput().getMessageInfo().getMessagePartsNumber() <= 0 || !"RequestSecurityToken".equals((mpi = boi.getInput().getMessageInfo().getFirstMessagePart()).getConcreteName().getLocalPart())) continue;
            return boi;
        }
        return null;
    }

    protected STSResponse issue(String appliesTo, String action, String requestType, String binaryExchange) throws Exception {
        Element customElement;
        Element actAsSecurityToken;
        this.createClient();
        BindingOperationInfo boi = this.findOperation("/RST/Issue");
        this.client.getRequestContext().putAll(this.ctx);
        if (action != null) {
            this.client.getRequestContext().put("SOAPAction", action);
        } else if (this.isSecureConv) {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/SCT");
        } else {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/Issue");
        }
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        writer.writeStartElement("wst", "RequestSecurityToken", this.namespace);
        writer.writeNamespace("wst", this.namespace);
        if (this.context != null) {
            writer.writeAttribute(null, "Context", this.context);
        }
        boolean wroteKeySize = false;
        String keyTypeTemplate = null;
        String sptt = null;
        if (this.template != null && DOMUtils.getFirstElement(this.template) != null) {
            if (this.useSecondaryParameters()) {
                writer.writeStartElement("wst", "SecondaryParameters", this.namespace);
            }
            Element tl = DOMUtils.getFirstElement(this.template);
            while (tl != null) {
                StaxUtils.copy(tl, (XMLStreamWriter)writer);
                if ("KeyType".equals(tl.getLocalName())) {
                    keyTypeTemplate = DOMUtils.getContent(tl);
                } else if ("KeySize".equals(tl.getLocalName())) {
                    wroteKeySize = true;
                    this.keySize = Integer.parseInt(DOMUtils.getContent(tl));
                } else if ("TokenType".equals(tl.getLocalName())) {
                    sptt = DOMUtils.getContent(tl);
                }
                tl = DOMUtils.getNextElement(tl);
            }
            if (this.useSecondaryParameters()) {
                writer.writeEndElement();
            }
        }
        if (this.isSpnego) {
            this.tokenType = STSUtils.getTokenTypeSCT(this.namespace);
            this.sendKeyType = false;
        }
        if (sptt == null) {
            this.addTokenType(writer);
        }
        this.addRequestType(requestType, writer);
        if (this.enableAppliesTo) {
            this.addAppliesTo(writer, appliesTo);
        }
        this.addClaims(writer);
        if (this.isSecureConv || this.enableLifetime) {
            this.addLifetime(writer);
        }
        this.writeRenewalSemantics(writer);
        Element onBehalfOfToken = this.getOnBehalfOfToken();
        if (onBehalfOfToken != null) {
            writer.writeStartElement("wst", "OnBehalfOf", this.namespace);
            StaxUtils.copy(onBehalfOfToken, (XMLStreamWriter)writer);
            writer.writeEndElement();
        }
        if (keyTypeTemplate == null) {
            keyTypeTemplate = this.writeKeyType(writer, this.keyType);
        }
        byte[] requestorEntropy = null;
        X509Certificate cert = null;
        Crypto crypto = null;
        if (this.keySize <= 0) {
            this.keySize = 256;
        }
        if (keyTypeTemplate != null && keyTypeTemplate.endsWith("SymmetricKey")) {
            requestorEntropy = this.writeElementsForRSTSymmetricKey(writer, wroteKeySize);
        } else if (keyTypeTemplate != null && keyTypeTemplate.endsWith("PublicKey")) {
            if (this.useKeyCertificate != null) {
                cert = this.useKeyCertificate;
            } else {
                crypto = this.createCrypto(false);
                cert = this.getCert(crypto);
            }
            this.writeElementsForRSTPublicKey(writer, cert);
        } else if (this.isSpnego || this.isSecureConv) {
            this.addKeySize(this.keySize, writer);
        }
        if (binaryExchange != null) {
            this.addBinaryExchange(binaryExchange, writer);
        }
        if ((actAsSecurityToken = this.getActAsToken()) != null) {
            writer.writeStartElement("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "ActAs");
            StaxUtils.copy(actAsSecurityToken, (XMLStreamWriter)writer);
            writer.writeEndElement();
        }
        if ((customElement = this.getCustomContent()) != null) {
            StaxUtils.copy(customElement, (XMLStreamWriter)writer);
        }
        writer.writeEndElement();
        Object[] obj = this.client.invoke(boi, new DOMSource(writer.getDocument().getDocumentElement()));
        Collection attachments = (Collection)this.client.getResponseContext().get(Message.ATTACHMENTS);
        return new STSResponse((DOMSource)obj[0], requestorEntropy, cert, crypto, attachments);
    }

    public Element getOnBehalfOfToken() throws Exception {
        return this.getDelegationSecurityToken(this.onBehalfOf);
    }

    public Element getActAsToken() throws Exception {
        return this.getDelegationSecurityToken(this.actAs);
    }

    public Element getCustomContent() throws Exception {
        if (this.customContent != null) {
            boolean isString = this.customContent instanceof String;
            boolean isElement = this.customContent instanceof Element;
            if (isString) {
                Document doc = StaxUtils.read(new StringReader((String)this.customContent));
                return doc.getDocumentElement();
            }
            if (isElement) {
                return (Element)this.customContent;
            }
        }
        return null;
    }

    protected Element getDelegationSecurityToken(Object delegationObject) throws Exception {
        if (delegationObject != null) {
            boolean isString = delegationObject instanceof String;
            boolean isElement = delegationObject instanceof Element;
            boolean isCallbackHandler = delegationObject instanceof CallbackHandler;
            if (isString) {
                Document doc = StaxUtils.read(new StringReader((String)delegationObject));
                return doc.getDocumentElement();
            }
            if (isElement) {
                return (Element)delegationObject;
            }
            if (isCallbackHandler) {
                DelegationCallback callback = new DelegationCallback(this.message);
                ((CallbackHandler)delegationObject).handle(new Callback[]{callback});
                return callback.getToken();
            }
        }
        return null;
    }

    protected byte[] writeElementsForRSTSymmetricKey(W3CDOMStreamWriter writer, boolean wroteKeySize) throws Exception {
        byte[] requestorEntropy = null;
        if (!wroteKeySize) {
            this.addKeySize(this.keySize, writer);
        }
        if (this.requiresEntropy) {
            writer.writeStartElement("wst", "Entropy", this.namespace);
            writer.writeStartElement("wst", "BinarySecret", this.namespace);
            writer.writeAttribute("Type", this.namespace + "/Nonce");
            if (this.algorithmSuite == null) {
                requestorEntropy = WSSecurityUtil.generateNonce(this.keySize / 8);
            } else {
                AlgorithmSuite.AlgorithmSuiteType algType = this.algorithmSuite.getAlgorithmSuiteType();
                requestorEntropy = WSSecurityUtil.generateNonce(algType.getMaximumSymmetricKeyLength() / 8);
            }
            writer.writeCharacters(XMLUtils.encodeToString(requestorEntropy));
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeStartElement("wst", "ComputedKeyAlgorithm", this.namespace);
            writer.writeCharacters(this.namespace + "/CK/PSHA1");
            writer.writeEndElement();
        }
        return requestorEntropy;
    }

    protected void writeElementsForRSTPublicKey(W3CDOMStreamWriter writer, X509Certificate cert) throws Exception {
        writer.writeStartElement("wst", "UseKey", this.namespace);
        writer.writeStartElement("ds", "KeyInfo", "http://www.w3.org/2000/09/xmldsig#");
        writer.writeNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");
        boolean useCert = this.useCertificateForConfirmationKeyInfo;
        String useCertStr = (String)this.getProperty("security.sts.token.usecert");
        if (useCertStr != null) {
            useCert = Boolean.parseBoolean(useCertStr);
        }
        if (useCert) {
            X509Data certElem = new X509Data(writer.getDocument());
            certElem.addCertificate(cert);
            writer.getCurrentNode().appendChild(certElem.getElement());
        } else {
            writer.writeStartElement("ds", "KeyValue", "http://www.w3.org/2000/09/xmldsig#");
            PublicKey key = cert.getPublicKey();
            String pubKeyAlgo = key.getAlgorithm();
            if ("DSA".equalsIgnoreCase(pubKeyAlgo)) {
                DSAKeyValue dsaKeyValue = new DSAKeyValue(writer.getDocument(), key);
                writer.getCurrentNode().appendChild(dsaKeyValue.getElement());
            } else if ("RSA".equalsIgnoreCase(pubKeyAlgo)) {
                RSAKeyValue rsaKeyValue = new RSAKeyValue(writer.getDocument(), key);
                writer.getCurrentNode().appendChild(rsaKeyValue.getElement());
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndElement();
    }

    protected void addBinaryExchange(String binaryExchange, W3CDOMStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("wst", "BinaryExchange", this.namespace);
        writer.writeAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
        writer.writeAttribute("ValueType", this.namespace + "/spnego");
        writer.writeCharacters(binaryExchange);
        writer.writeEndElement();
    }

    protected void addKeySize(int keysize, W3CDOMStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("wst", "KeySize", this.namespace);
        writer.writeCharacters(Integer.toString(keysize));
        writer.writeEndElement();
    }

    protected void addRequestType(String requestType, W3CDOMStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("wst", "RequestType", this.namespace);
        writer.writeCharacters(this.namespace + requestType);
        writer.writeEndElement();
    }

    protected Element getDocumentElement(DOMSource ds) {
        Node nd = ds.getNode();
        if (nd instanceof Document) {
            nd = ((Document)nd).getDocumentElement();
        }
        return (Element)nd;
    }

    public STSResponse renew(SecurityToken tok) throws Exception {
        this.createClient();
        BindingOperationInfo boi = this.findOperation("/RST/Renew");
        this.client.getRequestContext().putAll(this.ctx);
        this.client.getRequestContext().remove("ws-security.token.id");
        if (this.isSecureConv) {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/SCT/Renew");
        } else {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/Renew");
        }
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        writer.writeStartElement("wst", "RequestSecurityToken", this.namespace);
        writer.writeNamespace("wst", this.namespace);
        if (this.context != null) {
            writer.writeAttribute(null, "Context", this.context);
        }
        String sptt = null;
        if (this.template != null && DOMUtils.getFirstElement(this.template) != null) {
            if (this.useSecondaryParameters()) {
                writer.writeStartElement("wst", "SecondaryParameters", this.namespace);
            }
            Element tl = DOMUtils.getFirstElement(this.template);
            while (tl != null) {
                StaxUtils.copy(tl, (XMLStreamWriter)writer);
                if ("TokenType".equals(tl.getLocalName())) {
                    sptt = DOMUtils.getContent(tl);
                }
                tl = DOMUtils.getNextElement(tl);
            }
            if (this.useSecondaryParameters()) {
                writer.writeEndElement();
            }
        }
        if (this.isSpnego) {
            this.tokenType = STSUtils.getTokenTypeSCT(this.namespace);
        }
        if (sptt == null) {
            this.addTokenType(writer);
        }
        this.addRequestType("/Renew", writer);
        if (this.enableAppliesTo) {
            this.addAppliesTo(writer, tok.getIssuerAddress());
        }
        if (this.isSecureConv || this.enableLifetime) {
            this.addLifetime(writer);
        }
        writer.writeStartElement("wst", "RenewTarget", this.namespace);
        StaxUtils.copy(tok.getToken(), (XMLStreamWriter)writer);
        writer.writeEndElement();
        this.writeRenewalSemantics(writer);
        writer.writeEndElement();
        Object[] obj = this.client.invoke(boi, new DOMSource(writer.getDocument().getDocumentElement()));
        Collection attachments = (Collection)this.client.getResponseContext().get(Message.ATTACHMENTS);
        return new STSResponse((DOMSource)obj[0], null, null, null, attachments);
    }

    protected PrimitiveAssertion getAddressingAssertion() {
        String ns = "http://schemas.xmlsoap.org/ws/2004/08/addressing/policy";
        String local = "UsingAddressing";
        if ("http://www.w3.org/2005/08/addressing".equals(this.addressingNamespace)) {
            ns = "http://www.w3.org/2007/02/addressing/metadata";
            local = "Addressing";
        }
        return new PrimitiveAssertion(new QName(ns, local), true);
    }

    protected STSResponse validate(SecurityToken tok, String tokentype) throws Exception {
        String keyTypeTemplate;
        this.createClient();
        if (tokentype == null) {
            tokentype = this.tokenType;
        }
        if (tokentype == null) {
            tokentype = this.namespace + "/RSTR/Status";
        }
        Policy validatePolicy = new Policy();
        ExactlyOne one = new ExactlyOne();
        validatePolicy.addPolicyComponent(one);
        All all = new All();
        one.addPolicyComponent(all);
        all.addAssertion(this.getAddressingAssertion());
        this.client.getRequestContext().clear();
        this.client.getRequestContext().putAll(this.ctx);
        this.client.getRequestContext().put("ws-security.token", tok);
        BindingOperationInfo boi = this.findOperation("/RST/Validate");
        if (boi == null) {
            boi = this.findOperation("/RST/Issue");
            this.client.getRequestContext().put("org.apache.cxf.ws.policy.override", validatePolicy);
        }
        this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/Validate");
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        writer.writeStartElement("wst", "RequestSecurityToken", this.namespace);
        writer.writeNamespace("wst", this.namespace);
        writer.writeStartElement("wst", "TokenType", this.namespace);
        writer.writeCharacters(tokentype);
        writer.writeEndElement();
        writer.writeStartElement("wst", "RequestType", this.namespace);
        writer.writeCharacters(this.namespace + "/Validate");
        writer.writeEndElement();
        if (tokentype.endsWith("/RSTR/Status")) {
            this.addClaims(writer);
            writer.writeStartElement("wst", "ValidateTarget", this.namespace);
            Element el = tok.getToken();
            if (el != null) {
                StaxUtils.copy(el, (XMLStreamWriter)writer);
            }
            writer.writeEndElement();
            writer.writeEndElement();
            Object[] o = this.client.invoke(boi, new DOMSource(writer.getDocument().getDocumentElement()));
            Collection attachments = (Collection)this.client.getResponseContext().get(Message.ATTACHMENTS);
            return new STSResponse((DOMSource)o[0], null, null, null, attachments);
        }
        if (this.enableLifetime) {
            this.addLifetime(writer);
        }
        if ((keyTypeTemplate = this.keyType) == null) {
            keyTypeTemplate = this.namespace + "/Bearer";
        }
        keyTypeTemplate = this.writeKeyType(writer, keyTypeTemplate);
        byte[] requestorEntropy = null;
        X509Certificate cert = null;
        Crypto crypto = null;
        if (this.keySize <= 0) {
            this.keySize = 256;
        }
        if (keyTypeTemplate != null && keyTypeTemplate.endsWith("SymmetricKey")) {
            requestorEntropy = this.writeElementsForRSTSymmetricKey(writer, false);
        } else if (keyTypeTemplate != null && keyTypeTemplate.endsWith("PublicKey")) {
            if (this.useKeyCertificate != null) {
                cert = this.useKeyCertificate;
            } else {
                crypto = this.createCrypto(false);
                cert = this.getCert(crypto);
            }
            this.writeElementsForRSTPublicKey(writer, cert);
        }
        this.writeRenewalSemantics(writer);
        this.addClaims(writer);
        writer.writeStartElement("wst", "ValidateTarget", this.namespace);
        Element el = tok.getToken();
        StaxUtils.copy(el, (XMLStreamWriter)writer);
        writer.writeEndElement();
        writer.writeEndElement();
        Object[] o = this.client.invoke(boi, new DOMSource(writer.getDocument().getDocumentElement()));
        Collection attachments = (Collection)this.client.getResponseContext().get(Message.ATTACHMENTS);
        return new STSResponse((DOMSource)o[0], requestorEntropy, cert, crypto, attachments);
    }

    private void writeRenewalSemantics(XMLStreamWriter writer) throws XMLStreamException {
        if (this.sendRenewing) {
            writer.writeStartElement("wst", "Renewing", this.namespace);
            if (!this.allowRenewing) {
                writer.writeAttribute(null, "Allow", "false");
            }
            if (this.allowRenewing && this.allowRenewingAfterExpiry) {
                writer.writeAttribute(null, "OK", "true");
            }
            writer.writeEndElement();
        }
    }

    protected STSResponse cancel(SecurityToken token) throws Exception {
        this.createClient();
        this.client.getRequestContext().clear();
        this.client.getRequestContext().putAll(this.ctx);
        this.client.getRequestContext().put("ws-security.token", token);
        BindingOperationInfo boi = this.findOperation("/RST/Cancel");
        boolean attachTokenDirectly = true;
        if (boi == null) {
            attachTokenDirectly = false;
            boi = this.findOperation("/RST/Issue");
            Policy cancelPolicy = new Policy();
            ExactlyOne one = new ExactlyOne();
            cancelPolicy.addPolicyComponent(one);
            All all = new All();
            one.addPolicyComponent(all);
            all.addAssertion(this.getAddressingAssertion());
            final SecureConversationToken secureConversationToken = new SecureConversationToken(SPConstants.SPVersion.SP12, SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT, null, null, null, null);
            secureConversationToken.setOptional(true);
            DefaultSymmetricBinding binding = new DefaultSymmetricBinding(SPConstants.SPVersion.SP12, new Policy());
            all.addAssertion(binding);
            all.addAssertion(this.getAddressingAssertion());
            class InternalProtectionToken
            extends ProtectionToken {
                InternalProtectionToken(SPConstants.SPVersion version, Policy nestedPolicy) {
                    super(version, nestedPolicy);
                    super.setToken(secureConversationToken);
                }
            }
            binding.setProtectionToken(new InternalProtectionToken(SPConstants.SPVersion.SP12, new Policy()));
            binding.setIncludeTimestamp(true);
            binding.setOnlySignEntireHeadersAndBody(true);
            binding.setProtectTokens(false);
            String addrNamespace = this.addressingNamespace;
            if (addrNamespace == null) {
                addrNamespace = "http://www.w3.org/2005/08/addressing";
            }
            ArrayList<Header> headers = new ArrayList<Header>();
            headers.add(new Header("To", addrNamespace));
            headers.add(new Header("From", addrNamespace));
            headers.add(new Header("FaultTo", addrNamespace));
            headers.add(new Header("ReplyTo", addrNamespace));
            headers.add(new Header("Action", addrNamespace));
            headers.add(new Header("MessageID", addrNamespace));
            headers.add(new Header("RelatesTo", addrNamespace));
            SignedParts parts = new SignedParts(SPConstants.SPVersion.SP12, true, null, headers, false);
            parts.setOptional(true);
            all.addPolicyComponent(parts);
            this.client.getRequestContext().put("org.apache.cxf.ws.policy.override", cancelPolicy);
        }
        if (this.isSecureConv) {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/SCT/Cancel");
        } else {
            this.client.getRequestContext().put("SOAPAction", this.namespace + "/RST/Cancel");
        }
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        writer.writeStartElement("wst", "RequestSecurityToken", this.namespace);
        writer.writeNamespace("wst", this.namespace);
        writer.writeStartElement("wst", "RequestType", this.namespace);
        writer.writeCharacters(this.namespace + "/Cancel");
        writer.writeEndElement();
        writer.writeStartElement("wst", "CancelTarget", this.namespace);
        Element el = null;
        if (attachTokenDirectly) {
            el = token.getToken();
        } else {
            el = token.getUnattachedReference();
            if (el == null) {
                el = token.getAttachedReference();
            }
        }
        StaxUtils.copy(el, (XMLStreamWriter)writer);
        writer.writeEndElement();
        writer.writeEndElement();
        Object[] obj = this.client.invoke(boi, new DOMSource(writer.getDocument().getDocumentElement()));
        return new STSResponse((DOMSource)obj[0], null);
    }

    protected boolean useSecondaryParameters() {
        return !"http://schemas.xmlsoap.org/ws/2005/02/trust".equals(this.namespace);
    }

    protected String writeKeyType(W3CDOMStreamWriter writer, String keyTypeToWrite) throws XMLStreamException {
        if (this.isSecureConv) {
            if (keyTypeToWrite == null) {
                writer.writeStartElement("wst", "TokenType", this.namespace);
                writer.writeCharacters(STSUtils.getTokenTypeSCT(this.namespace));
                writer.writeEndElement();
                keyTypeToWrite = this.namespace + "/SymmetricKey";
            }
        } else if (keyTypeToWrite == null && this.sendKeyType) {
            writer.writeStartElement("wst", "KeyType", this.namespace);
            writer.writeCharacters(this.namespace + "/SymmetricKey");
            writer.writeEndElement();
            keyTypeToWrite = this.namespace + "/SymmetricKey";
        } else if (keyTypeToWrite != null) {
            writer.writeStartElement("wst", "KeyType", this.namespace);
            writer.writeCharacters(keyTypeToWrite);
            writer.writeEndElement();
        }
        return keyTypeToWrite;
    }

    protected X509Certificate getCert(Crypto crypto) throws Exception {
        if (crypto == null) {
            throw new Fault("No Crypto token properties are available to retrieve a certificate", LOG);
        }
        String alias = (String)this.getProperty("security.sts.token.username");
        if (alias == null) {
            alias = crypto.getDefaultX509Identifier();
        }
        if (alias == null) {
            throw new Fault("No alias specified for retrieving PublicKey", LOG);
        }
        CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
        cryptoType.setAlias(alias);
        X509Certificate[] certs = crypto.getX509Certificates(cryptoType);
        if (certs == null || certs.length == 0) {
            throw new Fault("Could not get X509Certificate for alias " + alias, LOG);
        }
        return certs[0];
    }

    protected void addLifetime(XMLStreamWriter writer) throws XMLStreamException {
        Instant created = Instant.now();
        Instant expires = created.plusSeconds(this.ttl);
        writer.writeStartElement("wst", "Lifetime", this.namespace);
        writer.writeNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeStartElement("wsu", "Created", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeCharacters(created.atZone(ZoneOffset.UTC).format(DateUtil.getDateTimeFormatter(true)));
        writer.writeEndElement();
        writer.writeStartElement("wsu", "Expires", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        writer.writeCharacters(expires.atZone(ZoneOffset.UTC).format(DateUtil.getDateTimeFormatter(true)));
        writer.writeEndElement();
        writer.writeEndElement();
    }

    protected void addAppliesTo(XMLStreamWriter writer, String appliesTo) throws XMLStreamException {
        if (appliesTo != null && this.addressingNamespace != null) {
            String policyNS = this.wspNamespace;
            if (policyNS == null) {
                policyNS = "http://schemas.xmlsoap.org/ws/2004/09/policy";
            }
            writer.writeStartElement("wsp", "AppliesTo", policyNS);
            writer.writeNamespace("wsp", policyNS);
            writer.writeStartElement("wsa", "EndpointReference", this.addressingNamespace);
            writer.writeNamespace("wsa", this.addressingNamespace);
            writer.writeStartElement("wsa", "Address", this.addressingNamespace);
            writer.writeCharacters(appliesTo);
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    protected void addTokenType(XMLStreamWriter writer) throws XMLStreamException {
        if (this.tokenType != null) {
            writer.writeStartElement("wst", "TokenType", this.namespace);
            writer.writeCharacters(this.tokenType);
            writer.writeEndElement();
        }
    }

    protected void addClaims(XMLStreamWriter writer) throws Exception {
        Object claimsToSerialize = this.claims;
        if (claimsToSerialize == null && this.claimsCallbackHandler != null) {
            ClaimsCallback callback = new ClaimsCallback(this.message);
            this.claimsCallbackHandler.handle(new Callback[]{callback});
            claimsToSerialize = callback.getClaims();
        }
        if (claimsToSerialize instanceof Element) {
            StaxUtils.copy((Element)claimsToSerialize, writer);
        } else if (claimsToSerialize instanceof ClaimCollection) {
            ClaimCollection claimCollection = (ClaimCollection)this.claims;
            claimCollection.serialize(writer, "wst", this.namespace);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected SecurityToken createSecurityToken(Element el, byte[] requestorEntropy) throws WSSecurityException, Base64DecodingException {
        if ("RequestSecurityTokenResponseCollection".equals(el.getLocalName())) {
            el = DOMUtils.getFirstElement(el);
        }
        if (!"RequestSecurityTokenResponse".equals(el.getLocalName())) {
            throw new Fault("Unexpected element " + el.getLocalName(), LOG);
        }
        el = DOMUtils.getFirstElement(el);
        Element rst = null;
        Element rar = null;
        Element rur = null;
        Element rpt = null;
        Element lte = null;
        Element entropy = null;
        String tt = null;
        String retKeySize = null;
        while (el != null) {
            String ln = el.getLocalName();
            if (this.namespace.equals(el.getNamespaceURI())) {
                if ("Lifetime".equals(ln)) {
                    lte = el;
                } else if ("RequestedSecurityToken".equals(ln)) {
                    rst = DOMUtils.getFirstElement(el);
                } else if ("RequestedAttachedReference".equals(ln)) {
                    rar = DOMUtils.getFirstElement(el);
                } else if ("RequestedUnattachedReference".equals(ln)) {
                    rur = DOMUtils.getFirstElement(el);
                } else if ("RequestedProofToken".equals(ln)) {
                    rpt = el;
                } else if ("Entropy".equals(ln)) {
                    entropy = el;
                } else if ("TokenType".equals(ln)) {
                    tt = DOMUtils.getContent(el);
                } else if ("KeySize".equals(ln)) {
                    retKeySize = DOMUtils.getContent(el);
                }
            }
            el = DOMUtils.getNextElement(el);
        }
        Element rstDec = rst;
        String id = this.findID(rar, rur, rstDec);
        if (StringUtils.isEmpty(id)) {
            LOG.fine("No ID extracted from token, so just making one up");
            id = WSSConfig.getNewInstance().getIdAllocator().createSecureId("_", null);
        }
        SecurityToken token = new SecurityToken(id, rstDec, lte);
        token.setAttachedReference(rar);
        token.setUnattachedReference(rur);
        token.setIssuerAddress(this.location);
        token.setTokenType(tt);
        byte[] secret = null;
        if (rpt != null) {
            Element child = DOMUtils.getFirstElement(rpt);
            QName childQname = DOMUtils.getElementQName(child);
            if (childQname.equals(new QName(this.namespace, "BinarySecret"))) {
                String b64Secret = DOMUtils.getContent(child);
                secret = XMLUtils.decode(b64Secret);
            } else if (childQname.equals(new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey"))) {
                secret = this.decryptKey(child);
            } else if (childQname.equals(new QName(this.namespace, "ComputedKey"))) {
                Element computedKeyChild = entropy == null ? null : DOMUtils.getFirstElement(entropy);
                byte[] serviceEntr = null;
                if (computedKeyChild != null) {
                    QName computedKeyChildQName = DOMUtils.getElementQName(computedKeyChild);
                    if (computedKeyChildQName.equals(new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey"))) {
                        serviceEntr = this.decryptKey(computedKeyChild);
                    } else if (computedKeyChildQName.equals(new QName(this.namespace, "BinarySecret"))) {
                        String content = DOMUtils.getContent(computedKeyChild);
                        serviceEntr = XMLUtils.decode(content);
                    }
                }
                if (serviceEntr == null) throw new TrustException("NO_ENTROPY", LOG);
                P_SHA1 psha1 = new P_SHA1();
                int length = 0;
                if (retKeySize != null) {
                    try {
                        length = Integer.parseInt(retKeySize);
                    }
                    catch (NumberFormatException numberFormatException) {}
                } else {
                    length = this.keySize;
                }
                if (length <= 0) {
                    length = 256;
                }
                try {
                    secret = psha1.createKey(requestorEntropy, serviceEntr, 0, length / 8);
                }
                catch (WSSecurityException e) {
                    throw new TrustException("DERIVED_KEY_ERROR", e, LOG);
                }
            }
        } else if (requestorEntropy != null) {
            secret = requestorEntropy;
        }
        token.setSecret(secret);
        return token;
    }

    protected byte[] decryptKey(Element child) throws TrustException, WSSecurityException, Base64DecodingException {
        String encryptionAlgorithm = X509Util.getEncAlgo(child);
        if (encryptionAlgorithm != null && encryptionAlgorithm.endsWith("spnego#GSS_Wrap")) {
            Element tmpE = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(child, "CipherData", "http://www.w3.org/2001/04/xmlenc#");
            byte[] cipherValue = null;
            if (tmpE != null && (tmpE = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(tmpE, "CipherValue", "http://www.w3.org/2001/04/xmlenc#")) != null) {
                String content = DOMUtils.getContent(tmpE);
                cipherValue = XMLUtils.decode(content);
            }
            if (cipherValue == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noCipher");
            }
            return cipherValue;
        }
        try {
            EncryptedKeyProcessor proc = new EncryptedKeyProcessor();
            WSDocInfo docInfo = new WSDocInfo(child.getOwnerDocument());
            RequestData data = new RequestData();
            data.setWssConfig(WSSConfig.getNewInstance());
            data.setDecCrypto(this.createCrypto(true));
            data.setCallbackHandler(this.createHandler());
            data.setWsDocInfo(docInfo);
            List<WSSecurityEngineResult> result = proc.handleToken(child, data);
            return (byte[])result.get(0).get("secret");
        }
        catch (IOException e) {
            throw new TrustException("ENCRYPTED_KEY_ERROR", e, LOG);
        }
    }

    protected CallbackHandler createHandler() {
        Object o = this.getProperty("security.callback-handler");
        try {
            return SecurityUtils.getCallbackHandler(o);
        }
        catch (Exception e) {
            throw new Fault(e);
        }
    }

    protected Object getProperty(String s2) {
        String key = s2;
        Object o = this.ctx.get(key);
        if (o == null) {
            o = this.client.getEndpoint().getEndpointInfo().getProperty(key);
        }
        if (o == null) {
            o = this.client.getEndpoint().getEndpointInfo().getBinding().getProperty(key);
        }
        if (o == null) {
            o = this.client.getEndpoint().getService().get(key);
        }
        key = "ws-" + s2;
        if (o == null) {
            o = this.ctx.get(key);
        }
        if (o == null) {
            o = this.client.getEndpoint().getEndpointInfo().getProperty(key);
        }
        if (o == null) {
            o = this.client.getEndpoint().getEndpointInfo().getBinding().getProperty(key);
        }
        if (o == null) {
            o = this.client.getEndpoint().getService().get(key);
        }
        return o;
    }

    protected Crypto createCrypto(boolean decrypt) throws IOException, WSSecurityException {
        URL propsURL;
        Crypto crypto = (Crypto)this.getProperty("security.sts.token.crypto" + (decrypt ? ".decrypt" : ""));
        if (crypto != null) {
            return crypto;
        }
        Object o = this.getProperty("security.sts.token.properties" + (decrypt ? ".decrypt" : ""));
        Properties properties = WSS4JUtils.getProps(o, propsURL = SecurityUtils.loadResource(this.message, o));
        if (properties != null) {
            PasswordEncryptor passwordEncryptor = WSS4JUtils.getPasswordEncryptor(this.message);
            return CryptoFactory.getInstance(properties, this.getClass().getClassLoader(), passwordEncryptor);
        }
        if (decrypt) {
            return this.createCrypto(false);
        }
        return null;
    }

    protected String findID(Element rar, Element rur, Element rst) {
        Element child;
        QName elName;
        String id = null;
        if (rst != null) {
            elName = DOMUtils.getElementQName(rst);
            if (elName.equals(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) && rst.hasAttributeNS(null, "AssertionID")) {
                id = rst.getAttributeNS(null, "AssertionID");
            } else if (elName.equals(new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion")) && rst.hasAttributeNS(null, "ID")) {
                id = rst.getAttributeNS(null, "ID");
            }
            if (id == null || "".equals(id)) {
                id = this.getIDFromSTR(rst);
            }
        }
        if ((id == null || "".equals(id)) && rar != null) {
            id = this.getIDFromSTR(rar);
        }
        if ((id == null || "".equals(id)) && rur != null) {
            id = this.getIDFromSTR(rur);
        }
        if ((id == null || "".equals(id)) && rst != null && ((id = rst.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id")) == null || "".equals(id)) && (elName = DOMUtils.getElementQName(rst)).equals(new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion")) && (child = DOMUtils.getFirstElement(rst)) != null) {
            id = child.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        }
        return id;
    }

    protected String getIDFromSTR(Element el) {
        Element child = DOMUtils.getFirstElement(el);
        if (child == null) {
            return null;
        }
        QName elName = DOMUtils.getElementQName(child);
        if (elName.equals(new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo")) || elName.equals(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier"))) {
            return DOMUtils.getContent(child);
        }
        if (elName.equals(Reference.TOKEN)) {
            return child.getAttributeNS(null, "URI");
        }
        if (elName.equals(new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Identifier")) || elName.equals(new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Identifier"))) {
            return DOMUtils.getContent(child);
        }
        return null;
    }

    public void setTemplate(Element rstTemplate) {
        this.template = rstTemplate;
    }

    public void setClaims(Object rstClaims) {
        this.claims = rstClaims;
    }

    @Override
    public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        if (this.client != null) {
            return this.client.getOutFaultInterceptors();
        }
        return this.outFault;
    }

    @Override
    public List<Interceptor<? extends Message>> getInFaultInterceptors() {
        if (this.client != null) {
            return this.client.getInFaultInterceptors();
        }
        return this.inFault;
    }

    @Override
    public List<Interceptor<? extends Message>> getInInterceptors() {
        if (this.client != null) {
            return this.client.getInInterceptors();
        }
        return this.in;
    }

    @Override
    public List<Interceptor<? extends Message>> getOutInterceptors() {
        if (this.client != null) {
            return this.client.getOutInterceptors();
        }
        return this.out;
    }

    public void setInInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.getInInterceptors().addAll(interceptors);
    }

    public void setInFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.getInFaultInterceptors().addAll(interceptors);
    }

    public void setOutInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.getOutInterceptors().addAll(interceptors);
    }

    public void setOutFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.getOutFaultInterceptors().addAll(interceptors);
    }

    public void setFeatures(List<? extends Feature> f) {
        this.features = CastUtils.cast(f);
    }

    public List<Feature> getFeatures() {
        return this.features;
    }

    public CallbackHandler getClaimsCallbackHandler() {
        return this.claimsCallbackHandler;
    }

    public void setClaimsCallbackHandler(CallbackHandler claimsCallbackHandler) {
        this.claimsCallbackHandler = claimsCallbackHandler;
    }

    public String getWspNamespace() {
        return this.wspNamespace;
    }

    public void setWspNamespace(String wspNamespace) {
        this.wspNamespace = wspNamespace;
    }

    public X509Certificate getUseKeyCertificate() {
        return this.useKeyCertificate;
    }

    public void setUseKeyCertificate(X509Certificate useKeyCertificate) {
        this.useKeyCertificate = useKeyCertificate;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    protected static class STSResponse {
        private final DOMSource response;
        private final byte[] entropy;
        private final X509Certificate cert;
        private final Crypto crypto;
        private final Collection<Attachment> attachments;

        public STSResponse(DOMSource response, byte[] entropy) {
            this(response, entropy, null, null);
        }

        public STSResponse(DOMSource response, byte[] entropy, X509Certificate cert, Crypto crypto) {
            this(response, entropy, cert, crypto, null);
        }

        public STSResponse(DOMSource response, byte[] entropy, X509Certificate cert, Crypto crypto, Collection<Attachment> attachments) {
            this.response = response;
            this.entropy = entropy;
            this.cert = cert;
            this.crypto = crypto;
            this.attachments = attachments;
        }

        public DOMSource getResponse() {
            return this.response;
        }

        public byte[] getEntropy() {
            return this.entropy;
        }

        public X509Certificate getCert() {
            return this.cert;
        }

        public Crypto getCrypto() {
            return this.crypto;
        }

        public Collection<Attachment> getAttachments() {
            return this.attachments;
        }
    }
}

