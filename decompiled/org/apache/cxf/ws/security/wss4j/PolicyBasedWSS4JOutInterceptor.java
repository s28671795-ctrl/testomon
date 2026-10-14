/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Provider;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AsymmetricBindingHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.SymmetricBindingHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.TransportBindingHandler;
import org.apache.neethi.Policy;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.SymmetricBinding;
import org.apache.wss4j.policy.model.TransportBinding;
import org.w3c.dom.Element;

public class PolicyBasedWSS4JOutInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    public static final String SECURITY_PROCESSED = PolicyBasedWSS4JOutInterceptor.class.getName() + ".DONE";
    public static final PolicyBasedWSS4JOutInterceptor INSTANCE = new PolicyBasedWSS4JOutInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyBasedWSS4JOutInterceptor.class);
    private PolicyBasedWSS4JOutInterceptorInternal ending;
    private SAAJOutInterceptor saajOut = new SAAJOutInterceptor();

    public PolicyBasedWSS4JOutInterceptor() {
        super("pre-protocol");
        this.getAfter().add(SAAJOutInterceptor.class.getName());
        this.ending = this.createEndingInterceptor();
    }

    @Override
    public void handleMessage(SoapMessage mc) throws Fault {
        boolean enableStax = MessageUtils.getContextualBoolean(mc, "ws-security.enable.streaming");
        if (!enableStax) {
            if (mc.getContent(SOAPMessage.class) == null) {
                this.saajOut.handleMessage(mc);
            }
            mc.put(SECURITY_PROCESSED, (Object)Boolean.TRUE);
            mc.getInterceptorChain().add(this.ending);
        }
    }

    @Override
    public void handleFault(SoapMessage message) {
        this.saajOut.handleFault(message);
    }

    public final PolicyBasedWSS4JOutInterceptorInternal createEndingInterceptor() {
        return new PolicyBasedWSS4JOutInterceptorInternal();
    }

    public final class PolicyBasedWSS4JOutInterceptorInternal
    implements PhaseInterceptor<SoapMessage> {
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            Provider provider = message.getExchange().get(Provider.class);
            boolean useCustomProvider = provider != null && ThreadLocalSecurityProvider.isInstalled();
            try {
                if (useCustomProvider) {
                    ThreadLocalSecurityProvider.setProvider(provider);
                }
                this.handleMessageInternal(message);
            }
            finally {
                if (useCustomProvider) {
                    ThreadLocalSecurityProvider.unsetProvider();
                }
            }
        }

        private void handleMessageInternal(SoapMessage message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim == null) {
                return;
            }
            SOAPMessage saaj = message.getContent(SOAPMessage.class);
            boolean mustUnderstand = MessageUtils.getContextualBoolean(message, "ws-security.must-understand", true);
            String actor = (String)message.getContextualProperty("ws-security.actor");
            AbstractBinding binding = PolicyUtils.getSecurityBinding(aim);
            if (binding == null && PolicyBasedWSS4JOutInterceptor.this.isRequestor(message)) {
                Policy policy = new Policy();
                binding = new TransportBinding(SPConstants.SPVersion.SP11, policy);
            }
            if (binding != null) {
                String symSignatureAlgorithm;
                WSSecHeader secHeader = new WSSecHeader(actor, mustUnderstand, saaj.getSOAPPart());
                Element el = null;
                try {
                    el = secHeader.insertSecurityHeader();
                }
                catch (WSSecurityException e) {
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("SECURITY_FAILED", LOG, new Object[0]), (Throwable)e, message.getVersion().getSender());
                }
                try {
                    SAAJUtils.getHeader(saaj).removeChild(el);
                    SAAJUtils.getHeader(saaj).appendChild(el);
                }
                catch (SOAPException e) {
                    // empty catch block
                }
                WSSConfig config = (WSSConfig)message.getContextualProperty(WSSConfig.class.getName());
                if (config == null) {
                    config = WSSConfig.getNewInstance();
                }
                this.translateProperties(message);
                String asymSignatureAlgorithm = (String)message.getContextualProperty("ws-security.asymmetric.signature.algorithm");
                if (asymSignatureAlgorithm != null && binding.getAlgorithmSuite() != null) {
                    binding.getAlgorithmSuite().setAsymmetricSignature(asymSignatureAlgorithm);
                }
                if ((symSignatureAlgorithm = (String)message.getContextualProperty("ws-security.symmetric.signature.algorithm")) != null && binding.getAlgorithmSuite() != null) {
                    binding.getAlgorithmSuite().setSymmetricSignature(symSignatureAlgorithm);
                }
                try {
                    if (binding instanceof TransportBinding) {
                        new TransportBindingHandler(config, (TransportBinding)binding, saaj, secHeader, aim, message).handleBinding();
                    } else if (binding instanceof SymmetricBinding) {
                        new SymmetricBindingHandler(config, (SymmetricBinding)binding, saaj, secHeader, aim, message).handleBinding();
                    } else {
                        new AsymmetricBindingHandler(config, (AsymmetricBinding)binding, saaj, secHeader, aim, message).handleBinding();
                    }
                }
                catch (SOAPException e) {
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("SECURITY_FAILED", LOG, new Object[0]), (Throwable)e, message.getVersion().getSender());
                }
                if (el.getFirstChild() == null) {
                    el.getParentNode().removeChild(el);
                }
            }
        }

        @Override
        public Set<String> getAfter() {
            return Collections.emptySet();
        }

        @Override
        public Set<String> getBefore() {
            return Collections.emptySet();
        }

        @Override
        public String getId() {
            return PolicyBasedWSS4JOutInterceptorInternal.class.getName();
        }

        @Override
        public String getPhase() {
            return "post-protocol";
        }

        @Override
        public void handleFault(SoapMessage message) {
        }

        @Override
        public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
            return null;
        }

        private void translateProperties(SoapMessage msg) {
            String bspCompliant = (String)msg.getContextualProperty("ws-security.is-bsp-compliant");
            if (bspCompliant != null) {
                msg.put("isBSPCompliant", (Object)bspCompliant);
            }
        }
    }
}

