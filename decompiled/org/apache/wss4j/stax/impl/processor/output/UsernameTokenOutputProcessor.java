/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.common.util.UsernameTokenUtil;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.TimestampOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.impl.securityToken.OutboundUsernameSecurityToken;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.XMLUtils;

public class UsernameTokenOutputProcessor
extends AbstractOutputProcessor {
    public UsernameTokenOutputProcessor() throws XMLSecurityException {
        this.addAfterProcessor(TimestampOutputProcessor.class.getName());
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
        this.addBeforeProcessor(EncryptOutputProcessor.class.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        try {
            CallbackHandler callbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getCallbackHandler();
            WSSConstants.UsernameTokenPasswordType usernameTokenPasswordType = ((WSSSecurityProperties)this.getSecurityProperties()).getUsernameTokenPasswordType();
            if (callbackHandler == null && WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE != usernameTokenPasswordType) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCallback");
            }
            String password = null;
            if (callbackHandler != null) {
                WSPasswordCallback pwCb = new WSPasswordCallback(((WSSSecurityProperties)this.getSecurityProperties()).getTokenUser(), 2);
                WSSUtils.doPasswordCallback(callbackHandler, pwCb);
                password = pwCb.getPassword();
            }
            if (password == null && WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE != usernameTokenPasswordType) {
                Object[] args2 = new Object[]{((WSSSecurityProperties)this.getSecurityProperties()).getTokenUser()};
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noPassword", args2);
            }
            final String wsuId = IDGenerator.generateID(null);
            boolean useDerivedKeyForMAC = ((WSSSecurityProperties)this.getSecurityProperties()).isUseDerivedKeyForMAC();
            int derivedIterations = ((WSSSecurityProperties)this.getSecurityProperties()).getDerivedKeyIterations();
            byte[] salt = null;
            if (WSSConstants.USERNAMETOKEN_SIGNED.equals(this.getAction())) {
                salt = UsernameTokenUtil.generateSalt(useDerivedKeyForMAC);
            }
            byte[] nonceValue = null;
            if (usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST || ((WSSSecurityProperties)this.getSecurityProperties()).isAddUsernameTokenNonce()) {
                nonceValue = WSSConstants.generateBytes(16);
            }
            String createdStr = "";
            if (usernameTokenPasswordType == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST || ((WSSSecurityProperties)this.getSecurityProperties()).isAddUsernameTokenCreated()) {
                Instant created = Instant.now();
                createdStr = created.atZone(ZoneOffset.UTC).format(DateUtil.getDateTimeFormatter(true));
            }
            UsernameTokenOutputProcessor outputProcessor = this;
            final OutboundUsernameSecurityToken usernameSecurityToken = new OutboundUsernameSecurityToken(((WSSSecurityProperties)this.getSecurityProperties()).getTokenUser(), password, createdStr, nonceValue, wsuId, salt, derivedIterations);
            usernameSecurityToken.setProcessor(outputProcessor);
            SecurityTokenProvider<OutboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<OutboundSecurityToken>(){

                @Override
                public OutboundSecurityToken getSecurityToken() throws WSSecurityException {
                    return usernameSecurityToken;
                }

                @Override
                public String getId() {
                    return wsuId;
                }
            };
            if (WSSConstants.USERNAMETOKEN_SIGNED.equals(this.getAction())) {
                outputProcessorChain.getSecurityContext().registerSecurityTokenProvider(wsuId, securityTokenProvider);
                outputProcessorChain.getSecurityContext().put("PROP_USE_THIS_TOKEN_ID_FOR_SIGNATURE", wsuId);
            }
            FinalUsernameTokenOutputProcessor finalUsernameTokenOutputProcessor = new FinalUsernameTokenOutputProcessor(wsuId, nonceValue, password, createdStr, salt, derivedIterations, this.getAction());
            finalUsernameTokenOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
            finalUsernameTokenOutputProcessor.setAction(this.getAction());
            finalUsernameTokenOutputProcessor.init(outputProcessorChain);
        }
        finally {
            outputProcessorChain.removeProcessor(this);
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    static class FinalUsernameTokenOutputProcessor
    extends AbstractOutputProcessor {
        private String wsuId;
        private byte[] nonceValue;
        private String password;
        private String created;
        private byte[] salt;
        private int iterations;
        private XMLSecurityConstants.Action action;

        FinalUsernameTokenOutputProcessor(String wsuId, byte[] nonceValue, String password, String created, byte[] salt, int iterations, XMLSecurityConstants.Action action) throws XMLSecurityException {
            this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
            this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
            this.wsuId = wsuId;
            this.nonceValue = nonceValue;
            this.password = password;
            this.created = created;
            this.salt = salt;
            this.iterations = iterations;
            this.action = action;
        }

        @Override
        public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            outputProcessorChain.processEvent(xmlSecEvent);
            if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                QName headerElementName = WSSConstants.TAG_WSSE_USERNAME_TOKEN;
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, this.wsuId));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, false, attributes);
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_USERNAME, false, null);
                this.createCharactersAndOutputAsEvent(subOutputProcessorChain, ((WSSSecurityProperties)this.getSecurityProperties()).getTokenUser());
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_USERNAME);
                if (((WSSSecurityProperties)this.getSecurityProperties()).getUsernameTokenPasswordType() != WSSConstants.UsernameTokenPasswordType.PASSWORD_NONE && !WSSConstants.USERNAMETOKEN_SIGNED.equals(this.action)) {
                    attributes = new ArrayList(1);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Type, ((WSSSecurityProperties)this.getSecurityProperties()).getUsernameTokenPasswordType() == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST ? WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST.getNamespace() : WSSConstants.UsernameTokenPasswordType.PASSWORD_TEXT.getNamespace()));
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_PASSWORD, false, attributes);
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, ((WSSSecurityProperties)this.getSecurityProperties()).getUsernameTokenPasswordType() == WSSConstants.UsernameTokenPasswordType.PASSWORD_DIGEST ? WSSUtils.doPasswordDigest(this.nonceValue, this.created, this.password) : this.password);
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_PASSWORD);
                }
                if (this.salt != null) {
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE11_SALT, true, null);
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(this.salt));
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE11_SALT);
                    if (this.iterations > 0) {
                        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE11_ITERATION, true, null);
                        this.createCharactersAndOutputAsEvent(subOutputProcessorChain, "" + this.iterations);
                        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE11_ITERATION);
                    }
                }
                if (this.nonceValue != null && !WSSConstants.USERNAMETOKEN_SIGNED.equals(this.action)) {
                    attributes = new ArrayList(1);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_ENCODING_TYPE, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary"));
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_NONCE, false, attributes);
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, XMLUtils.encodeToString(this.nonceValue));
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_NONCE);
                }
                if (!"".equals(this.created)) {
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_CREATED, false, null);
                    this.createCharactersAndOutputAsEvent(subOutputProcessorChain, this.created);
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSU_CREATED);
                }
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
                outputProcessorChain.removeProcessor(this);
            }
        }
    }
}

