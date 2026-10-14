/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Provider;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JInterceptor;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.handler.HandlerAction;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.util.WSSecurityUtil;

public class WSS4JOutInterceptor
extends AbstractWSS4JInterceptor {
    public static final String WSS4J_ACTION_MAP = "wss4j.action.map";
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JOutInterceptor.class);
    private WSS4JOutInterceptorInternal ending;
    private SAAJOutInterceptor saajOut = new SAAJOutInterceptor();
    private boolean mtomEnabled;

    public WSS4JOutInterceptor() {
        this.setPhase("pre-protocol");
        this.getAfter().add(SAAJOutInterceptor.class.getName());
        this.ending = this.createEndingInterceptor();
    }

    public WSS4JOutInterceptor(Map<String, Object> props) {
        this();
        this.setProperties(props);
    }

    @Deprecated
    public boolean isAllowMTOM() {
        return this.mtomEnabled;
    }

    @Deprecated
    public void setAllowMTOM(boolean allowMTOM) {
        this.mtomEnabled = allowMTOM;
    }

    @Override
    public Object getProperty(Object msgContext, String key) {
        Object result = super.getProperty(msgContext, key);
        if (result == null && "RECV_RESULTS".equals(key) && !this.isRequestor((SoapMessage)msgContext)) {
            result = ((SoapMessage)msgContext).getExchange().getInMessage().get(key);
        }
        return result;
    }

    @Override
    public void handleMessage(SoapMessage mc) throws Fault {
        if (mc.getContent(SOAPMessage.class) == null) {
            this.saajOut.handleMessage(mc);
        }
        if (!WSS4JOutInterceptor.class.getName().equals(this.getId())) {
            Random random = new Random();
            int randomInt = random.nextInt();
            this.ending.setId(WSS4JOutInterceptorInternal.class.getName() + "_" + randomInt);
        }
        mc.getInterceptorChain().add(this.ending);
    }

    @Override
    public void handleFault(SoapMessage message) {
        this.saajOut.handleFault(message);
    }

    public final WSS4JOutInterceptorInternal createEndingInterceptor() {
        return new WSS4JOutInterceptorInternal();
    }

    final class WSS4JOutInterceptorInternal
    implements PhaseInterceptor<SoapMessage> {
        private String id = WSS4JOutInterceptorInternal.class.getName();

        WSS4JOutInterceptorInternal() {
        }

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

        private void handleMessageInternal(SoapMessage mc) throws Fault {
            boolean doDebug = LOG.isLoggable(Level.FINE);
            if (doDebug) {
                LOG.fine("WSS4JOutInterceptor: enter handleMessage()");
            }
            if (mc == null) {
                return;
            }
            SoapVersion version = mc.getVersion();
            RequestData reqData = new RequestData();
            try {
                SOAPMessage saaj;
                String username;
                WSSConfig config = WSSConfig.getNewInstance();
                reqData.setWssConfig(config);
                this.configureActions(mc, doDebug, version, config);
                List<Object> actions = CastUtils.cast((List)WSS4JOutInterceptor.this.getProperty(mc, "handlerActions"));
                if (actions == null) {
                    String action = WSS4JOutInterceptor.this.getString("action", mc);
                    if (action == null) {
                        throw new SoapFault(new org.apache.cxf.common.i18n.Message("NO_ACTION", LOG, new Object[0]), version.getReceiver());
                    }
                    actions = WSSecurityUtil.decodeHandlerAction(action, config);
                }
                if (actions.isEmpty()) {
                    return;
                }
                WSS4JOutInterceptor.this.translateProperties(mc);
                reqData.setMsgContext(mc);
                reqData.setAttachmentCallbackHandler(new AttachmentCallbackHandler(mc));
                if (WSS4JOutInterceptor.this.getString("expandXOPInclude", mc) == null) {
                    reqData.setExpandXopInclude(AttachmentUtil.isMtomEnabled(mc));
                }
                if (WSS4JOutInterceptor.this.getString("storeBytesInAttachment", mc) == null) {
                    reqData.setStoreBytesInAttachment(AttachmentUtil.isMtomEnabled(mc));
                }
                reqData.setUsername((String)WSS4JOutInterceptor.this.getOption("user"));
                if ((reqData.getUsername() == null || reqData.getUsername().isEmpty()) && (username = (String)WSS4JOutInterceptor.this.getProperty(reqData.getMsgContext(), "user")) != null) {
                    reqData.setUsername(username);
                }
                boolean userNameRequired = false;
                for (HandlerAction handlerAction : actions) {
                    if (handlerAction.getAction() != 2 && handlerAction.getAction() != 1 && handlerAction.getAction() != 8192 && handlerAction.getAction() != 64 || handlerAction.getActionToken() != null && handlerAction.getActionToken().getUser() != null) continue;
                    userNameRequired = true;
                    break;
                }
                if (userNameRequired && (reqData.getUsername() == null || reqData.getUsername().isEmpty()) && (String)WSS4JOutInterceptor.this.getOption("signatureUser") == null) {
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("NO_USERNAME", LOG, new Object[0]), version.getReceiver());
                }
                if (doDebug) {
                    LOG.fine("Actor: " + reqData.getActor());
                }
                if ((saaj = mc.getContent(SOAPMessage.class)) == null) {
                    LOG.warning("SAAJOutHandler must be enabled for WS-Security!");
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("NO_SAAJ_DOC", LOG, new Object[0]), version.getReceiver());
                }
                SOAPPart sOAPPart = saaj.getSOAPPart();
                WSS4JOutInterceptor.this.doSenderAction(sOAPPart, reqData, actions, Boolean.TRUE.equals(WSS4JOutInterceptor.this.getProperty(mc, "org.apache.cxf.client")));
                if (doDebug) {
                    LOG.fine("WSS4JOutInterceptor: exit handleMessage()");
                }
            }
            catch (WSSecurityException e) {
                throw new SoapFault(new org.apache.cxf.common.i18n.Message("SECURITY_FAILED", LOG, new Object[0]), (Throwable)e, version.getSender());
            }
            finally {
                reqData = null;
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
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getPhase() {
            return "post-protocol";
        }

        @Override
        public void handleFault(SoapMessage message) {
        }

        private void configureActions(SoapMessage mc, boolean doDebug, SoapVersion version, WSSConfig config) {
            Map actionMap = CastUtils.cast((Map)WSS4JOutInterceptor.this.getProperty(mc, WSS4JOutInterceptor.WSS4J_ACTION_MAP));
            if (actionMap != null && !actionMap.isEmpty()) {
                for (Map.Entry entry : actionMap.entrySet()) {
                    Class<?> removedAction;
                    block7: {
                        removedAction = null;
                        try {
                            if (entry.getValue() instanceof Class) {
                                removedAction = config.setAction((int)((Integer)entry.getKey()), (Class)entry.getValue());
                                break block7;
                            }
                            if (entry.getValue() instanceof Action) {
                                removedAction = config.setAction((int)((Integer)entry.getKey()), (Action)entry.getValue());
                                break block7;
                            }
                            throw new SoapFault(new org.apache.cxf.common.i18n.Message("BAD_ACTION", LOG, new Object[0]), version.getReceiver());
                        }
                        catch (ClassCastException e) {
                            throw new SoapFault(new org.apache.cxf.common.i18n.Message("BAD_ACTION", LOG, new Object[0]), version.getReceiver());
                        }
                    }
                    if (!doDebug) continue;
                    if (removedAction != null) {
                        LOG.fine("Replaced Action: " + removedAction.getName() + " with Action: " + entry.getValue() + " for ID: " + entry.getKey());
                        continue;
                    }
                    LOG.fine("Added Action: " + entry.getValue() + " with ID: " + entry.getKey());
                }
            }
        }

        @Override
        public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
            return null;
        }
    }
}

