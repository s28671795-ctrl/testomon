/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;
import javax.xml.ws.WebFault;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapBinding;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.endpoint.ClientLifeCycleManager;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.apache.cxf.ws.addressing.Names;
import org.apache.cxf.ws.addressing.WSAContextUtils;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.cxf.ws.addressing.impl.DefaultMessageIdCache;
import org.apache.cxf.ws.addressing.impl.InternalContextUtils;
import org.apache.cxf.ws.addressing.policy.MetadataConstants;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;

public class MAPAggregatorImpl
extends MAPAggregator {
    private static final Logger LOG = LogUtils.getL7dLogger(MAPAggregator.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private static final ClientLifeCycleListener DECOUPLED_DEST_CLEANER = new ClientLifeCycleListener(){

        @Override
        public void clientCreated(Client client) {
        }

        @Override
        public void clientDestroyed(Client client) {
            Destination dest = client.getEndpoint().getEndpointInfo().getProperty(MAPAggregator.DECOUPLED_DESTINATION, Destination.class);
            if (dest != null) {
                dest.setMessageObserver(null);
                dest.shutdown();
            }
        }
    };

    public MAPAggregatorImpl() {
        this.messageIdCache = new DefaultMessageIdCache();
    }

    public MAPAggregatorImpl(MAPAggregator mag) {
        this.addressingRequired = mag.isAddressingRequired();
        this.messageIdCache = mag.getMessageIdCache();
        if (this.messageIdCache == null) {
            this.messageIdCache = new DefaultMessageIdCache();
        }
        this.usingAddressingAdvisory = mag.isUsingAddressingAdvisory();
        this.allowDuplicates = mag.allowDuplicates();
        this.addressingResponses = mag.getAddressingResponses();
    }

    @Override
    public void handleMessage(Message message) {
        if (!MessageUtils.getContextualBoolean(message, ADDRESSING_DISABLED, false)) {
            this.mediate(message, ContextUtils.isFault(message));
        } else {
            QName[] types;
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (null == aim) {
                return;
            }
            for (QName type : types = new QName[]{MetadataConstants.ADDRESSING_ASSERTION_QNAME, MetadataConstants.USING_ADDRESSING_2004_QNAME, MetadataConstants.USING_ADDRESSING_2005_QNAME, MetadataConstants.USING_ADDRESSING_2006_QNAME, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME_0705, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME_0705}) {
                this.assertAssertion(aim, type);
            }
        }
    }

    @Override
    public void handleFault(Message message) {
        message.put(MAPAggregator.class.getName(), this);
    }

    private boolean usingAddressing(Message message) {
        boolean ret = true;
        if (ContextUtils.isRequestor(message)) {
            if (this.hasUsingAddressing(message) || this.hasAddressingAssertion(message) || this.hasUsingAddressingAssertion(message)) {
                return true;
            }
            if (!this.usingAddressingAdvisory || !WSAContextUtils.retrieveUsingAddressing(message)) {
                ret = false;
            }
        } else {
            ret = this.getMAPs(message, false, false) != null;
        }
        return ret;
    }

    private boolean hasUsingAddressing(Message message) {
        boolean ret = false;
        Endpoint endpoint = message.getExchange().getEndpoint();
        if (null != endpoint) {
            Boolean b = (Boolean)endpoint.get(USING_ADDRESSING);
            if (null == b) {
                EndpointInfo endpointInfo = endpoint.getEndpointInfo();
                List<ExtensibilityElement> endpointExts = endpointInfo != null ? endpointInfo.getExtensors(ExtensibilityElement.class) : null;
                List<ExtensibilityElement> bindingExts = endpointInfo != null && endpointInfo.getBinding() != null ? endpointInfo.getBinding().getExtensors(ExtensibilityElement.class) : null;
                List<ExtensibilityElement> serviceExts = endpointInfo != null && endpointInfo.getService() != null ? endpointInfo.getService().getExtensors(ExtensibilityElement.class) : null;
                ret = this.hasUsingAddressing(endpointExts) || this.hasUsingAddressing(bindingExts) || this.hasUsingAddressing(serviceExts);
                b = ret ? Boolean.TRUE : Boolean.FALSE;
                endpoint.put(USING_ADDRESSING, b);
            } else {
                ret = b;
            }
        }
        return ret;
    }

    private boolean hasAddressingAssertion(Message message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return false;
        }
        return null != aim.get(MetadataConstants.ADDRESSING_ASSERTION_QNAME);
    }

    private boolean hasUsingAddressingAssertion(Message message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return false;
        }
        if (null != aim.get(MetadataConstants.USING_ADDRESSING_2004_QNAME)) {
            return true;
        }
        if (null != aim.get(MetadataConstants.USING_ADDRESSING_2005_QNAME)) {
            return true;
        }
        return null != aim.get(MetadataConstants.USING_ADDRESSING_2006_QNAME);
    }

    private WSAddressingFeature getWSAddressingFeature(Message message) {
        Endpoint endpoint;
        if (message.getExchange() != null && message.getExchange().getEndpoint() != null && (endpoint = message.getExchange().getEndpoint()).getActiveFeatures() != null) {
            for (Feature feature : endpoint.getActiveFeatures()) {
                if (!(feature instanceof WSAddressingFeature)) continue;
                return (WSAddressingFeature)feature;
            }
        }
        return null;
    }

    private void assertAddressing(Message message) {
        QName[] types;
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return;
        }
        for (QName type : types = new QName[]{MetadataConstants.ADDRESSING_ASSERTION_QNAME, MetadataConstants.USING_ADDRESSING_2004_QNAME, MetadataConstants.USING_ADDRESSING_2005_QNAME, MetadataConstants.USING_ADDRESSING_2006_QNAME}) {
            this.assertAssertion(aim, type);
            if (!type.equals(MetadataConstants.ADDRESSING_ASSERTION_QNAME)) continue;
            this.assertAssertion(aim, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME);
            this.assertAssertion(aim, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME);
        }
    }

    private void assertAddressing(Message message, EndpointReferenceType replyTo, EndpointReferenceType faultTo) {
        QName[] types;
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return;
        }
        if (faultTo == null) {
            faultTo = replyTo;
        }
        boolean anonReply = ContextUtils.isGenericAddress(replyTo);
        boolean anonFault = ContextUtils.isGenericAddress(faultTo);
        boolean onlyAnonymous = anonReply && anonFault;
        boolean hasAnonymous = anonReply || anonFault;
        for (QName type : types = new QName[]{MetadataConstants.ADDRESSING_ASSERTION_QNAME, MetadataConstants.USING_ADDRESSING_2004_QNAME, MetadataConstants.USING_ADDRESSING_2005_QNAME, MetadataConstants.USING_ADDRESSING_2006_QNAME}) {
            this.assertAssertion(aim, type);
            if (!type.equals(MetadataConstants.ADDRESSING_ASSERTION_QNAME)) continue;
            if (onlyAnonymous) {
                this.assertAssertion(aim, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME);
                continue;
            }
            if (hasAnonymous) continue;
            this.assertAssertion(aim, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME);
        }
        if (!MessageUtils.isRequestor(message) && !MessageUtils.isOutbound(message)) {
            boolean hasNonAnon;
            Collection<AssertionInfo> aicNonAnon = aim.getAssertionInfo(MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME);
            Collection<AssertionInfo> aicNonAnon2 = aim.getAssertionInfo(MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME_0705);
            Collection<AssertionInfo> aicAnon = aim.getAssertionInfo(MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME);
            Collection<AssertionInfo> aicAnon2 = aim.getAssertionInfo(MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME_0705);
            boolean hasAnon = aicAnon != null && !aicAnon.isEmpty() || aicAnon2 != null && !aicAnon2.isEmpty();
            boolean bl = hasNonAnon = aicNonAnon != null && !aicNonAnon.isEmpty() || aicNonAnon2 != null && !aicNonAnon2.isEmpty();
            if (hasAnonymous && hasNonAnon && !hasAnon) {
                message.put(FaultMode.class, FaultMode.UNCHECKED_APPLICATION_FAULT);
                if (this.isSOAP12(message)) {
                    SoapFault soap12Fault = new SoapFault("Found anonymous address but non-anonymous required", Soap12.getInstance().getSender());
                    soap12Fault.addSubCode(new QName("http://www.w3.org/2005/08/addressing", "OnlyNonAnonymousAddressSupported"));
                    throw soap12Fault;
                }
                throw new SoapFault("Found anonymous address but non-anonymous required", new QName("http://www.w3.org/2005/08/addressing", "OnlyNonAnonymousAddressSupported"));
            }
            if (!onlyAnonymous && !hasNonAnon && hasAnon) {
                message.put(FaultMode.class, FaultMode.UNCHECKED_APPLICATION_FAULT);
                if (this.isSOAP12(message)) {
                    SoapFault soap12Fault = new SoapFault("Found non-anonymous address but only anonymous supported", Soap12.getInstance().getSender());
                    soap12Fault.addSubCode(new QName("http://www.w3.org/2005/08/addressing", "OnlyAnonymousAddressSupported"));
                    throw soap12Fault;
                }
                throw new SoapFault("Found non-anonymous address but only anonymous supported", new QName("http://www.w3.org/2005/08/addressing", "OnlyAnonymousAddressSupported"));
            }
        }
    }

    private void assertAssertion(AssertionInfoMap aim, QName type) {
        Collection<AssertionInfo> aic = aim.getAssertionInfo(type);
        for (AssertionInfo ai : aic) {
            ai.setAsserted(true);
        }
    }

    private boolean hasUsingAddressing(List<ExtensibilityElement> exts) {
        boolean found = false;
        if (exts != null) {
            Iterator<ExtensibilityElement> extensionElements = exts.iterator();
            while (extensionElements.hasNext() && !found) {
                ExtensibilityElement ext = extensionElements.next();
                found = Names.WSAW_USING_ADDRESSING_QNAME.equals(ext.getElementType());
            }
        }
        return found;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean mediate(Message message, boolean isFault) {
        boolean continueProcessing = true;
        if (ContextUtils.isOutbound(message)) {
            if (this.usingAddressing(message)) {
                this.aggregate(message, isFault);
            }
            AddressingProperties theMaps = ContextUtils.retrieveMAPs(message, false, ContextUtils.isOutbound(message));
            if (this.isAddressingRequired() && ContextUtils.isRequestor(message)) {
                theMaps.setRequired(true);
            }
            if (null == theMaps) return continueProcessing;
            if (ContextUtils.isRequestor(message)) {
                this.assertAddressing(message, theMaps.getReplyTo(), theMaps.getFaultTo());
                return continueProcessing;
            }
            this.checkReplyTo(message, theMaps);
            return continueProcessing;
        }
        if (!ContextUtils.isRequestor(message)) {
            AddressingProperties maps = this.getMAPs(message, false, false);
            if (maps != null) {
                this.checkAddressingResponses(maps.getReplyTo(), maps.getFaultTo());
                this.assertAddressing(message, maps.getReplyTo(), maps.getFaultTo());
            }
            boolean isOneway = message.getExchange().isOneWay();
            if (null == maps && !this.addressingRequired) {
                return false;
            }
            continueProcessing = this.validateIncomingMAPs(maps, message);
            if (maps != null) {
                AddressingProperties theMaps = ContextUtils.retrieveMAPs(message, false, ContextUtils.isOutbound(message));
                if (null != theMaps) {
                    this.assertAddressing(message, theMaps.getReplyTo(), theMaps.getFaultTo());
                }
                if (isOneway || !ContextUtils.isGenericAddress(maps.getReplyTo())) {
                    InternalContextUtils.rebaseResponse(maps.getReplyTo(), maps, message);
                }
                if (!isOneway) {
                    if (ContextUtils.isNoneAddress(maps.getReplyTo())) {
                        LOG.warning("Detected NONE value in ReplyTo WSA header for request-respone MEP");
                    } else {
                        ContextUtils.propogateReceivedMAPs(maps, message.getExchange());
                    }
                }
            }
            if (continueProcessing) {
                message.put(FaultMode.class, FaultMode.LOGICAL_RUNTIME_FAULT);
                return continueProcessing;
            }
            if (!this.isSOAP12(message)) throw new SoapFault(ContextUtils.retrieveMAPFaultReason(message), new QName("http://www.w3.org/2005/08/addressing", ContextUtils.retrieveMAPFaultName(message)));
            SoapFault soap12Fault = new SoapFault(ContextUtils.retrieveMAPFaultReason(message), Soap12.getInstance().getSender());
            soap12Fault.setSubCode(new QName("http://www.w3.org/2005/08/addressing", ContextUtils.retrieveMAPFaultName(message)));
            throw soap12Fault;
        }
        AddressingProperties theMaps = ContextUtils.retrieveMAPs(message, false, ContextUtils.isOutbound(message));
        if (null != theMaps) {
            this.assertAddressing(message, theMaps.getReplyTo(), theMaps.getFaultTo());
        }
        if (!ContextUtils.isOutbound(message) && ContextUtils.isRequestor(message) && this.getWSAddressingFeature(message) != null && !this.getWSAddressingFeature(message).isAddressingRequired()) {
            this.assertAddressing(message);
        }
        if (null == theMaps && !ContextUtils.isOutbound(message) && ContextUtils.isRequestor(message) && this.getWSAddressingFeature(message) != null && this.getWSAddressingFeature(message).isAddressingRequired()) {
            boolean missingWsaHeader = false;
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim == null || aim.isEmpty()) {
                missingWsaHeader = true;
            }
            if (aim != null && !aim.isEmpty()) {
                QName[] types;
                missingWsaHeader = true;
                for (QName type : types = new QName[]{MetadataConstants.ADDRESSING_ASSERTION_QNAME, MetadataConstants.USING_ADDRESSING_2004_QNAME, MetadataConstants.USING_ADDRESSING_2005_QNAME, MetadataConstants.USING_ADDRESSING_2006_QNAME}) {
                    for (AssertionInfo assertInfo : aim.getAssertionInfo(type)) {
                        if (!assertInfo.isAsserted()) continue;
                        missingWsaHeader = false;
                    }
                }
            }
            if (missingWsaHeader) {
                throw new SoapFault("MISSING_ACTION_MESSAGE", BUNDLE, new QName("http://www.w3.org/2005/08/addressing", "MessageAddressingHeaderRequired"));
            }
        }
        if (!MessageUtils.isPartialResponse(message)) return continueProcessing;
        if (message.getExchange().getOutMessage() == null) return continueProcessing;
        MessageInfo min2 = message.get(MessageInfo.class);
        MessageInfo mout = message.getExchange().getOutMessage().get(MessageInfo.class);
        if (min2 == null) return continueProcessing;
        if (mout == null) return continueProcessing;
        if (min2.getOperation() != mout.getOperation()) return continueProcessing;
        if (message.getContent(List.class) == null) return continueProcessing;
        message.remove("org.apache.cxf.partial.response");
        return continueProcessing;
    }

    private void checkAddressingResponses(EndpointReferenceType replyTo, EndpointReferenceType faultTo) {
        boolean isAnonymous;
        if (this.addressingResponses == WSAddressingFeature.AddressingResponses.ALL) {
            return;
        }
        boolean passed = false;
        boolean anonReply = ContextUtils.isGenericAddress(replyTo);
        boolean anonFault = ContextUtils.isGenericAddress(faultTo);
        boolean bl = isAnonymous = anonReply && anonFault;
        if (WSAddressingFeature.AddressingResponses.ANONYMOUS == this.addressingResponses && isAnonymous) {
            passed = true;
        } else if (WSAddressingFeature.AddressingResponses.NON_ANONYMOUS == this.addressingResponses && (!anonReply && !anonFault && faultTo.getAddress() != null || !anonReply && faultTo == null)) {
            passed = true;
        }
        if (!passed) {
            String reason = BUNDLE.getString("INVALID_ADDRESSING_PROPERTY_MESSAGE");
            QName detail = WSAddressingFeature.AddressingResponses.ANONYMOUS == this.addressingResponses ? Names.ONLY_ANONYMOUS_ADDRESS_SUPPORTED_QNAME : Names.ONLY_NONANONYMOUS_ADDRESS_SUPPORTED_QNAME;
            throw new SoapFault(reason, detail);
        }
    }

    private void aggregate(Message message, boolean isFault) {
        boolean isRequestor = ContextUtils.isRequestor(message);
        AddressingProperties maps = this.assembleGeneric(message);
        this.addRoleSpecific(maps, message, isRequestor, isFault);
        ContextUtils.storeMAPs(maps, message, true, isRequestor);
    }

    private AddressingProperties assembleGeneric(Message message) {
        AddressingProperties maps = this.getMAPs(message, true, true);
        if (maps.getMessageID() == null) {
            String messageID = ContextUtils.generateUUID();
            maps.setMessageID(ContextUtils.getAttributedURI(messageID));
        }
        if (ContextUtils.hasEmptyAction(maps)) {
            maps.setAction(InternalContextUtils.getAction(message));
            if (ContextUtils.hasEmptyAction(maps) && ContextUtils.isOutbound(message)) {
                maps.setAction(ContextUtils.getAttributedURI(this.getActionUri(message, true)));
            }
        }
        return maps;
    }

    private String getActionFromInputMessage(OperationInfo operation) {
        String inputAction;
        MessageInfo inputMessage = operation.getInput();
        if (inputMessage.getExtensionAttributes() != null && !StringUtils.isEmpty(inputAction = InternalContextUtils.getAction(inputMessage))) {
            return inputAction;
        }
        return null;
    }

    private String getActionFromOutputMessage(OperationInfo operation) {
        String outputAction;
        MessageInfo outputMessage = operation.getOutput();
        if (outputMessage != null && outputMessage.getExtensionAttributes() != null && !StringUtils.isEmpty(outputAction = InternalContextUtils.getAction(outputMessage))) {
            return outputAction;
        }
        return null;
    }

    private boolean isSameFault(FaultInfo faultInfo, String faultName) {
        if (faultInfo.getName() == null || faultName == null) {
            return false;
        }
        String faultInfoName = faultInfo.getName().getLocalPart();
        return faultInfoName.equals(faultName) || faultInfoName.equals(StringUtils.uncapitalize(faultName));
    }

    private String getActionBaseUri(OperationInfo operation) {
        String interfaceName = operation.getInterface().getName().getLocalPart();
        return this.addPath(operation.getName().getNamespaceURI(), interfaceName);
    }

    private String getActionFromFaultMessage(OperationInfo operation, String faultName) {
        if (operation.getFaults() != null) {
            for (FaultInfo faultInfo : operation.getFaults()) {
                String faultAction;
                if (!this.isSameFault(faultInfo, faultName)) continue;
                if (faultInfo.getExtensionAttributes() != null && !StringUtils.isEmpty(faultAction = InternalContextUtils.getAction(faultInfo))) {
                    return faultAction;
                }
                return this.addPath(this.addPath(this.addPath(this.getActionBaseUri(operation), operation.getName().getLocalPart()), "Fault"), faultInfo.getFaultName().getLocalPart());
            }
        }
        return this.addPath(this.addPath(this.addPath(this.getActionBaseUri(operation), operation.getName().getLocalPart()), "Fault"), faultName);
    }

    private String getFaultNameFromMessage(Message message) {
        WebFault t2;
        Exception e = message.getContent(Exception.class);
        Throwable cause = e.getCause();
        if (cause == null) {
            cause = e;
        }
        if (e instanceof Fault && (t2 = cause.getClass().getAnnotation(WebFault.class)) != null) {
            return t2.name();
        }
        return cause.getClass().getSimpleName();
    }

    protected String getActionUri(Message message, boolean checkMessage) {
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop == null || Boolean.TRUE.equals(bop.getProperty("operation.is.synthetic"))) {
            return null;
        }
        OperationInfo op = bop.getOperationInfo();
        if (op.isUnwrapped()) {
            op = ((UnwrappedOperationInfo)op).getWrappedOperation();
        }
        String actionUri = null;
        if (checkMessage && (actionUri = (String)message.get(ContextUtils.ACTION)) == null) {
            actionUri = (String)message.get("SOAPAction");
        }
        if (actionUri != null) {
            return actionUri;
        }
        String opNamespace = this.getActionBaseUri(op);
        boolean inbound = !ContextUtils.isOutbound(message);
        boolean requestor = ContextUtils.isRequestor(message);
        boolean inMsg = requestor ^ inbound;
        if (ContextUtils.isFault(message)) {
            String faultName = this.getFaultNameFromMessage(message);
            actionUri = this.getActionFromFaultMessage(op, faultName);
        } else if (inMsg) {
            String explicitAction = this.getActionFromInputMessage(op);
            if (StringUtils.isEmpty(explicitAction)) {
                SoapOperationInfo soi = InternalContextUtils.getSoapOperationInfo(bop);
                String string = explicitAction = soi == null ? null : soi.getAction();
            }
            actionUri = !StringUtils.isEmpty(explicitAction) ? explicitAction : (null == op.getInputName() ? this.addPath(opNamespace, op.getName().getLocalPart() + "Request") : this.addPath(opNamespace, op.getInputName()));
        } else {
            String explicitAction = this.getActionFromOutputMessage(op);
            actionUri = explicitAction != null ? explicitAction : (null == op.getOutputName() ? this.addPath(opNamespace, op.getName().getLocalPart() + "Response") : this.addPath(opNamespace, op.getOutputName()));
        }
        return actionUri;
    }

    private String getDelimiter(String uri) {
        if (uri.startsWith("urn")) {
            return ":";
        }
        return "/";
    }

    private String addPath(String uri, String path) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(uri);
        String delimiter = this.getDelimiter(uri);
        if (!uri.endsWith(delimiter) && !path.startsWith(delimiter)) {
            buffer.append(delimiter);
        }
        buffer.append(path);
        return buffer.toString();
    }

    private void addRoleSpecific(AddressingProperties maps, Message message, boolean isRequestor, boolean isFault) {
        if (isRequestor) {
            EndpointReferenceType replyTo;
            Exchange exchange = message.getExchange();
            boolean isOneway = exchange.isOneWay();
            boolean isOutbound = ContextUtils.isOutbound(message);
            if (maps.getTo() == null) {
                EndpointReferenceType reference;
                Conduit conduit = null;
                if (isOutbound) {
                    conduit = ContextUtils.getConduit(null, message);
                }
                String s2 = (String)message.get(Message.ENDPOINT_ADDRESS);
                EndpointReferenceType endpointReferenceType = reference = conduit != null ? conduit.getTarget() : ContextUtils.getNoneEndpointReference();
                if (conduit != null && !StringUtils.isEmpty(s2) && !reference.getAddress().getValue().equals(s2)) {
                    EndpointReferenceType ref = new EndpointReferenceType();
                    AttributedURIType tp = new AttributedURIType();
                    tp.setValue(s2);
                    ref.setAddress(tp);
                    ref.setMetadata(reference.getMetadata());
                    ref.setReferenceParameters(reference.getReferenceParameters());
                    ref.getOtherAttributes().putAll(reference.getOtherAttributes());
                    reference = ref;
                }
                maps.setTo(reference);
            }
            if (ContextUtils.isGenericAddress(replyTo = maps.getReplyTo())) {
                if ((replyTo = this.getReplyTo(message, replyTo)) == null || isOneway && (replyTo == null || replyTo.getAddress() == null || !"http://www.w3.org/2005/08/addressing/none".equals(replyTo.getAddress().getValue()))) {
                    AttributedURIType address = ContextUtils.getAttributedURI(isOneway ? "http://www.w3.org/2005/08/addressing/none" : "http://www.w3.org/2005/08/addressing/anonymous");
                    replyTo = ContextUtils.WSA_OBJECT_FACTORY.createEndpointReferenceType();
                    replyTo.setAddress(address);
                }
                maps.setReplyTo(replyTo);
            }
            if (maps.getFaultTo() == null) {
                maps.setFaultTo(maps.getReplyTo());
            } else if (maps.getFaultTo().getAddress() == null) {
                maps.setFaultTo(null);
            }
        } else {
            AddressingProperties inMAPs = this.getMAPs(message, false, false);
            maps.exposeAs(inMAPs.getNamespaceURI());
            if (isFault && inMAPs.getFaultTo() != null) {
                maps.setTo(inMAPs.getFaultTo());
            } else if (maps.getTo() == null && inMAPs.getReplyTo() != null) {
                maps.setTo(inMAPs.getReplyTo());
            }
            if (inMAPs.getMessageID() != null && !Boolean.TRUE.equals(message.get("org.apache.cxf.partial.response"))) {
                String inMessageID = inMAPs.getMessageID().getValue();
                maps.setRelatesTo(ContextUtils.getRelatesTo(inMessageID));
            } else {
                maps.setRelatesTo(ContextUtils.getRelatesTo("http://www.w3.org/2005/08/addressing/unspecified"));
            }
            if (isFault && maps.getAction() == null) {
                maps.setAction(ContextUtils.getAttributedURI("http://www.w3.org/2005/08/addressing/fault"));
            }
            if (isFault && !ContextUtils.isGenericAddress(inMAPs.getFaultTo())) {
                Message m4 = message.getExchange().getInFaultMessage();
                if (m4 == null) {
                    m4 = message;
                }
                InternalContextUtils.rebaseResponse(inMAPs.getFaultTo(), inMAPs, m4);
                Destination destination = InternalContextUtils.createDecoupledDestination(m4.getExchange(), inMAPs.getFaultTo());
                m4.getExchange().setDestination(destination);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private EndpointReferenceType getReplyTo(Message message, EndpointReferenceType originalReplyTo) {
        Exchange exchange = message.getExchange();
        Endpoint info = exchange.getEndpoint();
        if (info == null) {
            return originalReplyTo;
        }
        Endpoint endpoint = info;
        synchronized (endpoint) {
            EndpointInfo ei = info.getEndpointInfo();
            Destination dest = ei.getProperty(DECOUPLED_DESTINATION, Destination.class);
            if (dest == null && (dest = this.createDecoupledDestination(message)) != null) {
                info.getEndpointInfo().setProperty(DECOUPLED_DESTINATION, dest);
            }
            if (dest != null) {
                String debase;
                String replyTo = dest.getAddress().getAddress().getValue();
                if (replyTo.startsWith("/") && (debase = (String)message.getContextualProperty("org.apache.cxf.ws.addressing.decoupled.endpoint.base")) != null) {
                    return EndpointReferenceUtils.getEndpointReference(debase + replyTo);
                }
                return dest.getAddress();
            }
        }
        return originalReplyTo;
    }

    private Destination createDecoupledDestination(Message message) {
        String replyToAddress = (String)message.getContextualProperty("org.apache.cxf.ws.addressing.replyto");
        if (replyToAddress != null) {
            return this.setUpDecoupledDestination(message.getExchange().getBus(), replyToAddress, message);
        }
        return null;
    }

    private Destination setUpDecoupledDestination(Bus bus, String replyToAddress, Message message) {
        EndpointReferenceType reference = EndpointReferenceUtils.getEndpointReference(replyToAddress);
        if (reference != null) {
            String decoupledAddress = reference.getAddress().getValue();
            LOG.info("creating decoupled endpoint: " + decoupledAddress);
            try {
                Destination dest = this.getDestination(bus, replyToAddress, message);
                bus.getExtension(ClientLifeCycleManager.class).registerListener(DECOUPLED_DEST_CLEANER);
                return dest;
            }
            catch (Exception e) {
                LOG.log(Level.WARNING, "decoupled endpoint creation failed: ", e);
            }
        }
        return null;
    }

    private Destination getDestination(Bus bus, String address, Message message) throws IOException {
        Destination destination = null;
        DestinationFactoryManager factoryManager = bus.getExtension(DestinationFactoryManager.class);
        DestinationFactory factory2 = factoryManager.getDestinationFactoryForUri(address);
        if (factory2 != null) {
            Endpoint ep = message.getExchange().getEndpoint();
            EndpointInfo ei = new EndpointInfo();
            ei.setName(new QName(ep.getEndpointInfo().getName().getNamespaceURI(), ep.getEndpointInfo().getName().getLocalPart() + ".decoupled"));
            ei.setAddress(address);
            destination = factory2.getDestination(ei, bus);
            Conduit conduit = ContextUtils.getConduit(null, message);
            if (conduit != null) {
                MessageObserver ob = conduit.getMessageObserver();
                ob = new InterposedMessageObserver(bus, ob);
                destination.setMessageObserver(ob);
            }
        }
        return destination;
    }

    private AddressingProperties getMAPs(Message message, boolean isProviderContext, boolean isOutbound) {
        AddressingProperties maps = null;
        maps = ContextUtils.retrieveMAPs(message, isProviderContext, isOutbound);
        LOG.log(Level.FINE, "MAPs retrieved from message {0}", maps);
        if (maps == null && isProviderContext) {
            maps = new AddressingProperties();
            this.setupNamespace(maps, message);
        }
        return maps;
    }

    private void setupNamespace(AddressingProperties maps, Message message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            String ns = (String)message.getContextualProperty(MAPAggregator.ADDRESSING_NAMESPACE);
            if (ns != null) {
                maps.exposeAs(ns);
            }
            return;
        }
        Collection<AssertionInfo> aic = aim.getAssertionInfo(MetadataConstants.USING_ADDRESSING_2004_QNAME);
        if (aic != null && !aic.isEmpty()) {
            maps.exposeAs("http://schemas.xmlsoap.org/ws/2004/08/addressing");
        }
    }

    private boolean validateIncomingMAPs(AddressingProperties maps, Message message) {
        boolean valid = true;
        if (maps != null) {
            String reason;
            String sa = SoapActionInInterceptor.getSoapAction(message);
            String s1 = this.getActionUri(message, false);
            if (maps.getAction() == null || maps.getAction().getValue() == null) {
                String reason2 = BUNDLE.getString("MISSING_ACTION_MESSAGE");
                ContextUtils.storeMAPFaultName("MessageAddressingHeaderRequired", message);
                ContextUtils.storeMAPFaultReason(reason2, message);
                valid = false;
            }
            if (!StringUtils.isEmpty(sa) && valid && !PropertyUtils.isTrue(message.get(MAPAggregator.ACTION_VERIFIED))) {
                String action;
                if (sa.startsWith("\"")) {
                    sa = sa.substring(1, sa.lastIndexOf(34));
                }
                String string = action = maps.getAction() == null ? "" : maps.getAction().getValue();
                if (!StringUtils.isEmpty(sa) && !sa.equals(action)) {
                    reason = BUNDLE.getString("INVALID_ADDRESSING_PROPERTY_MESSAGE");
                    ContextUtils.storeMAPFaultName("ActionMismatch", message);
                    ContextUtils.storeMAPFaultReason(reason, message);
                    valid = false;
                } else if (!(StringUtils.isEmpty(s1) || action.equals(s1) || action.equals(s1 + "Request") || s1.equals(action + "Request"))) {
                    reason = BundleUtils.getFormattedString(BUNDLE, "ACTION_NOT_SUPPORTED_MSG", action);
                    ContextUtils.storeMAPFaultName("ActionNotSupported", message);
                    ContextUtils.storeMAPFaultReason(reason, message);
                    valid = false;
                }
            }
            AttributedURIType messageID = maps.getMessageID();
            if (!message.getExchange().isOneWay() && (messageID == null || messageID.getValue() == null) && valid) {
                reason = BUNDLE.getString("MISSING_ACTION_MESSAGE");
                ContextUtils.storeMAPFaultName("MessageAddressingHeaderRequired", message);
                ContextUtils.storeMAPFaultReason(reason, message);
                valid = false;
            }
            if (!this.allowDuplicates && messageID != null && messageID.getValue() != null && !this.messageIdCache.checkUniquenessAndCacheId(messageID.getValue())) {
                LOG.log(Level.WARNING, "DUPLICATE_MESSAGE_ID_MSG", messageID.getValue());
                if (valid) {
                    reason = BUNDLE.getString("DUPLICATE_MESSAGE_ID_MSG");
                    String l7dReason = MessageFormat.format(reason, messageID.getValue());
                    ContextUtils.storeMAPFaultName("DuplicateMessageID", message);
                    ContextUtils.storeMAPFaultReason(l7dReason, message);
                }
                valid = false;
            }
        } else if (this.usingAddressingAdvisory) {
            String reason = BUNDLE.getString("MISSING_ACTION_MESSAGE");
            ContextUtils.storeMAPFaultName("MessageAddressingHeaderRequired", message);
            ContextUtils.storeMAPFaultReason(reason, message);
            valid = false;
        }
        if ("InvalidCardinality".equals(ContextUtils.retrieveMAPFaultName(message))) {
            valid = false;
        }
        return valid;
    }

    private void checkReplyTo(Message message, AddressingProperties maps) {
        if (!message.getExchange().isOneWay() && !MessageUtils.isPartialResponse(message) && ContextUtils.isNoneAddress(maps.getReplyTo())) {
            String reason = MessageFormat.format(BUNDLE.getString("REPLYTO_NOT_SUPPORTED_MSG"), maps.getReplyTo().getAddress().getValue());
            throw new SoapFault(reason, new QName("http://www.w3.org/2005/08/addressing", "http://www.w3.org/2005/08/addressing/none"));
        }
    }

    private boolean isSOAP12(Message message) {
        SoapBinding binding;
        return message.getExchange().getBinding() instanceof SoapBinding && (binding = (SoapBinding)message.getExchange().getBinding()).getSoapVersion() == Soap12.getInstance();
    }

    protected static class InterposedMessageObserver
    implements MessageObserver {
        Bus bus;
        MessageObserver observer;

        public InterposedMessageObserver(Bus b, MessageObserver o) {
            this.bus = b;
            this.observer = o;
        }

        @Override
        public void onMessage(Message inMessage) {
            inMessage.setExchange(new ExchangeImpl());
            inMessage.getExchange().put(Bus.class, this.bus);
            inMessage.put("decoupled.channel.message", Boolean.TRUE);
            inMessage.put(Message.RESPONSE_CODE, 200);
            inMessage.remove("org.apache.cxf.async.post.response.dispatch");
            this.updateResponseCode(inMessage);
            try {
                InputStream in = inMessage.getContent(InputStream.class);
                if (in != null) {
                    CachedOutputStream cos = new CachedOutputStream();
                    IOUtils.copy(in, cos);
                    inMessage.setContent(InputStream.class, cos.getInputStream());
                }
                this.observer.onMessage(inMessage);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void updateResponseCode(Message message) {
            Object o = message.get("HTTP.RESPONSE");
            if (o != null) {
                try {
                    o.getClass().getMethod("setStatus", Integer.TYPE).invoke(o, 202);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
    }
}

