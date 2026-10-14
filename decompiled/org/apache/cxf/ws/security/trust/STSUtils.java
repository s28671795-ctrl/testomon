/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.HashMap;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.databinding.source.SourceDataBinding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.ServiceImpl;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.VersionTransformer;
import org.apache.cxf.ws.security.trust.STSAuthParams;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.TrustException;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.model.IssuedToken;
import org.w3c.dom.Element;

public final class STSUtils {
    public static final String WST_NS_05_02 = "http://schemas.xmlsoap.org/ws/2005/02/trust";
    public static final String WST_NS_05_12 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
    public static final String WST_NS_08_02 = "http://docs.oasis-open.org/ws-sx/ws-trust/200802";
    public static final String SCT_NS_05_02 = "http://schemas.xmlsoap.org/ws/2005/02/sc";
    public static final String SCT_NS_05_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
    public static final String TOKEN_TYPE_SCT_05_02 = "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
    public static final String TOKEN_TYPE_SCT_05_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct";
    private static final String TOKEN_TYPE_SAML_2_0 = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0";
    private static final QName STS_SERVICE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "SecurityTokenService");
    private static final Logger LOG = LogUtils.getL7dLogger(STSUtils.class);

    private STSUtils() {
    }

    public static String getTokenTypeSCT(String trustNs) {
        if (WST_NS_05_02.equals(trustNs)) {
            return TOKEN_TYPE_SCT_05_02;
        }
        return TOKEN_TYPE_SCT_05_12;
    }

    public static STSClient getClient(Message message, String type) {
        return STSUtils.getClientWithIssuer(message, type, null);
    }

    public static STSClient getClient(Message message, String type, IssuedToken itok) {
        if (itok != null) {
            return STSUtils.getClientWithIssuer(message, type, itok.getIssuer());
        }
        return STSUtils.getClientWithIssuer(message, type, null);
    }

    public static STSClient getClientWithIssuer(Message message, String type, Element issuer) {
        STSClient client = (STSClient)SecurityUtils.getSecurityPropertyValue("security.sts.client", message);
        if (client == null) {
            client = STSUtils.createSTSClient(message, type);
            Bus bus = message.getExchange().getBus();
            bus.getExtension(Configurer.class).configureBean("default.sts-client", client);
            if (client.getBeanName() != null) {
                bus.getExtension(Configurer.class).configureBean(client.getBeanName(), client);
            }
        }
        boolean preferWSMex = SecurityUtils.getSecurityPropertyBoolean("security.sts.prefer-wsmex", message, false);
        if (issuer != null && (preferWSMex || client.getLocation() == null && client.getWsdlLocation() == null)) {
            EndpointReferenceType epr = null;
            try {
                epr = VersionTransformer.parseEndpointReference(issuer);
            }
            catch (JAXBException e) {
                throw new IllegalArgumentException(e);
            }
            if (preferWSMex && STSUtils.findMEXLocation(epr) != null) {
                STSClient wsMexClient = (STSClient)SecurityUtils.getSecurityPropertyValue("security.sts.client.wsmex", message);
                if (wsMexClient == null) {
                    wsMexClient = STSUtils.createSTSClient(message, type);
                }
                wsMexClient.configureViaEPR(epr, false);
                STSUtils.checkForRecursiveCall(wsMexClient, message);
                return wsMexClient;
            }
            if (STSUtils.configureViaEPR(client, epr)) {
                boolean useEPRWSAAddrAsMEXLocation = Boolean.valueOf((String)SecurityUtils.getSecurityPropertyValue("security.sts.disable-wsmex-call-using-epr-address", message)) == false;
                client.configureViaEPR(epr, useEPRWSAAddrAsMEXLocation);
                STSUtils.checkForRecursiveCall(client, message);
                return client;
            }
        }
        STSUtils.checkForRecursiveCall(client, message);
        return client;
    }

    private static void checkForRecursiveCall(STSClient client, Message message) {
        EndpointInfo endpointInfo;
        boolean checkForRecursiveCall = SecurityUtils.getSecurityPropertyBoolean("security.sts.check.for.recursive.call", message, true);
        if (checkForRecursiveCall && (endpointInfo = message.getExchange().getEndpoint().getEndpointInfo()).getName().equals(client.getEndpointQName()) && endpointInfo.getService().getName().equals(client.getServiceQName())) {
            throw new TrustException("ISSUED_TOKEN_POLICY_ERR", LOG);
        }
    }

    public static boolean configureViaEPR(STSClient client, EndpointReferenceType epr) {
        return epr != null && client.getLocation() == null && client.getWsdlLocation() == null;
    }

    private static STSClient createSTSClient(Message message, String type) {
        type = type == null ? "" : "." + type + "-client";
        STSClient client = new STSClient(message.getExchange().getBus());
        Endpoint ep = message.getExchange().getEndpoint();
        client.setEndpointName(ep.getEndpointInfo().getName().toString() + type);
        client.setBeanName(ep.getEndpointInfo().getName().toString() + type);
        if (SecurityUtils.getSecurityPropertyBoolean("security.sts.client-soap12-binding", message, false)) {
            client.setSoap12();
        }
        return client;
    }

    public static STSClient createSTSClient(STSAuthParams authParams, String stsWsdlLocation, Bus bus) {
        STSClient basicStsClient = new STSClient(bus);
        basicStsClient.setWsdlLocation(stsWsdlLocation);
        basicStsClient.setServiceName(STS_SERVICE_NAME.toString());
        basicStsClient.setEndpointName(authParams.getAuthMode().getEndpointName().toString());
        if (authParams.getAuthMode().getKeyType() != null) {
            basicStsClient.setKeyType(authParams.getAuthMode().getKeyType());
        } else {
            basicStsClient.setSendKeyType(false);
        }
        basicStsClient.setTokenType(TOKEN_TYPE_SAML_2_0);
        basicStsClient.setAllowRenewingAfterExpiry(true);
        basicStsClient.setEnableLifetime(true);
        HashMap<String, Object> props = new HashMap<String, Object>();
        if (authParams.getUserName() != null) {
            props.put("security.username", authParams.getUserName());
        }
        props.put("security.callback-handler", authParams.getCallbackHandler());
        if (authParams.getKeystoreProperties() != null) {
            props.put("security.encryption.username", authParams.getAlias());
            props.put("security.encryption.properties", authParams.getKeystoreProperties());
            props.put("security.signature.properties", authParams.getKeystoreProperties());
            props.put("security.sts.token.username", authParams.getAlias());
            props.put("security.sts.token.properties", authParams.getKeystoreProperties());
            props.put("security.sts.token.usecert", "true");
        }
        basicStsClient.setProperties(props);
        return basicStsClient;
    }

    public static String findMEXLocation(EndpointReferenceType ref) {
        if (ref.getMetadata() != null && ref.getMetadata().getAny() != null) {
            for (Object any : ref.getMetadata().getAny()) {
                String addr;
                if (!(any instanceof Element) || (addr = STSUtils.findMEXLocation((Element)any)) == null) continue;
                return addr;
            }
        }
        return null;
    }

    public static String findMEXLocation(Element ref) {
        Element el = DOMUtils.getFirstElement(ref);
        while (el != null) {
            if ("Address".equals(el.getLocalName()) && VersionTransformer.isSupported(el.getNamespaceURI()) && "MetadataReference".equals(ref.getLocalName())) {
                return DOMUtils.getContent(el);
            }
            String ad = STSUtils.findMEXLocation(el);
            if (ad != null) {
                return ad;
            }
            el = DOMUtils.getNextElement(el);
        }
        return null;
    }

    public static Endpoint createSTSEndpoint(Bus bus, String namespace, String transportId, String location, String soapVersion, Policy policy, QName epName) throws BusException, EndpointException {
        return STSUtils.createSTSEndpoint(bus, namespace, transportId, location, soapVersion, policy, epName, false);
    }

    public static Endpoint createSCEndpoint(Bus bus, String namespace, String transportId, String location, String soapVersion, Policy policy) throws BusException, EndpointException {
        return STSUtils.createSTSEndpoint(bus, namespace, transportId, location, soapVersion, policy, null, true);
    }

    private static Endpoint createSTSEndpoint(Bus bus, String namespace, String transportId, String location, String soapVersion, Policy policy, QName epName, boolean sc) throws BusException, EndpointException {
        BindingOperationInfo boi;
        SoapOperationInfo soi;
        ServiceImpl service2 = null;
        String ns = namespace + "/wsdl";
        ServiceInfo si = new ServiceInfo();
        QName iName = new QName(ns, sc ? "SecureConversationTokenService" : "SecurityTokenService");
        si.setName(iName);
        InterfaceInfo ii = new InterfaceInfo(si, iName);
        OperationInfo ioi = STSUtils.addIssueOperation(ii, namespace, ns);
        OperationInfo coi = STSUtils.addCancelOperation(ii, namespace, ns);
        OperationInfo roi = STSUtils.addRenewOperation(ii, namespace, ns);
        si.setInterface(ii);
        service2 = new ServiceImpl(si);
        BindingFactoryManager bfm = bus.getExtension(BindingFactoryManager.class);
        BindingFactory bindingFactory = bfm.getBindingFactory(soapVersion);
        BindingInfo bi = bindingFactory.createBindingInfo(service2, soapVersion, null);
        si.addBinding(bi);
        if (transportId == null) {
            ConduitInitiatorManager cim = bus.getExtension(ConduitInitiatorManager.class);
            ConduitInitiator ci = cim.getConduitInitiatorForUri(location);
            transportId = ci.getTransportIds().get(0);
        }
        EndpointInfo ei = new EndpointInfo(si, transportId);
        ei.setBinding(bi);
        ei.setName(epName == null ? iName : epName);
        ei.setAddress(location);
        si.addEndpoint(ei);
        if (policy != null) {
            ei.addExtensor(policy);
        }
        if ((soi = (boi = bi.getOperation(ioi)).getExtensor(SoapOperationInfo.class)) == null) {
            soi = new SoapOperationInfo();
            boi.addExtensor(soi);
        }
        soi.setAction(namespace + (sc ? "/RST/SCT" : "/RST/Issue"));
        boi = bi.getOperation(coi);
        soi = boi.getExtensor(SoapOperationInfo.class);
        if (soi == null) {
            soi = new SoapOperationInfo();
            boi.addExtensor(soi);
        }
        soi.setAction(namespace + (sc ? "/RST/SCT/Cancel" : "/RST/Cancel"));
        boi = bi.getOperation(roi);
        soi = boi.getExtensor(SoapOperationInfo.class);
        if (soi == null) {
            soi = new SoapOperationInfo();
            boi.addExtensor(soi);
        }
        soi.setAction(namespace + (sc ? "/RST/SCT/Renew" : "/RST/Renew"));
        service2.setDataBinding(new SourceDataBinding());
        return new EndpointImpl(bus, (Service)service2, ei);
    }

    private static OperationInfo addIssueOperation(InterfaceInfo ii, String namespace, String servNamespace) {
        OperationInfo oi = ii.addOperation(new QName(servNamespace, "RequestSecurityToken"));
        MessageInfo mii = oi.createMessage(new QName(servNamespace, "RequestSecurityTokenMsg"), MessageInfo.Type.INPUT);
        oi.setInput("RequestSecurityTokenMsg", mii);
        MessagePartInfo mpi = mii.addMessagePart("request");
        mpi.setElementQName(new QName(namespace, "RequestSecurityToken"));
        MessageInfo mio = oi.createMessage(new QName(servNamespace, "RequestSecurityTokenResponseMsg"), MessageInfo.Type.OUTPUT);
        oi.setOutput("RequestSecurityTokenResponseMsg", mio);
        mpi = mio.addMessagePart("response");
        if (WST_NS_05_02.equals(namespace)) {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponse"));
        } else {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponseCollection"));
        }
        return oi;
    }

    private static OperationInfo addCancelOperation(InterfaceInfo ii, String namespace, String servNamespace) {
        OperationInfo oi = ii.addOperation(new QName(servNamespace, "CancelSecurityToken"));
        MessageInfo mii = oi.createMessage(new QName(servNamespace, "CancelSecurityTokenMsg"), MessageInfo.Type.INPUT);
        oi.setInput("CancelSecurityTokenMsg", mii);
        MessagePartInfo mpi = mii.addMessagePart("request");
        mpi.setElementQName(new QName(namespace, "RequestSecurityToken"));
        MessageInfo mio = oi.createMessage(new QName(servNamespace, "CancelSecurityTokenResponseMsg"), MessageInfo.Type.OUTPUT);
        oi.setOutput("CancelSecurityTokenResponseMsg", mio);
        mpi = mio.addMessagePart("response");
        if (WST_NS_05_02.equals(namespace)) {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponse"));
        } else {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponseCollection"));
        }
        return oi;
    }

    private static OperationInfo addRenewOperation(InterfaceInfo ii, String namespace, String servNamespace) {
        OperationInfo oi = ii.addOperation(new QName(servNamespace, "RenewSecurityToken"));
        MessageInfo mii = oi.createMessage(new QName(servNamespace, "RenewSecurityTokenMsg"), MessageInfo.Type.INPUT);
        oi.setInput("RenewSecurityTokenMsg", mii);
        MessagePartInfo mpi = mii.addMessagePart("request");
        mpi.setElementQName(new QName(namespace, "RequestSecurityToken"));
        MessageInfo mio = oi.createMessage(new QName(servNamespace, "RenewSecurityTokenResponseMsg"), MessageInfo.Type.OUTPUT);
        oi.setOutput("RenewSecurityTokenResponseMsg", mio);
        mpi = mio.addMessagePart("response");
        if (WST_NS_05_02.equals(namespace)) {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponse"));
        } else {
            mpi.setElementQName(new QName(namespace, "RequestSecurityTokenResponseCollection"));
        }
        return oi;
    }
}

