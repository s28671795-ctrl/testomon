/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.engine;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.WSProviderConfig;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.WSCurrentTimeSource;
import org.apache.wss4j.common.util.WSTimeSource;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WsuIdAllocator;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.action.CustomTokenAction;
import org.apache.wss4j.dom.action.EncryptionAction;
import org.apache.wss4j.dom.action.EncryptionDerivedAction;
import org.apache.wss4j.dom.action.SAMLTokenSignedAction;
import org.apache.wss4j.dom.action.SAMLTokenUnsignedAction;
import org.apache.wss4j.dom.action.SignatureAction;
import org.apache.wss4j.dom.action.SignatureConfirmationAction;
import org.apache.wss4j.dom.action.SignatureDerivedAction;
import org.apache.wss4j.dom.action.TimestampAction;
import org.apache.wss4j.dom.action.UsernameTokenAction;
import org.apache.wss4j.dom.action.UsernameTokenSignedAction;
import org.apache.wss4j.dom.processor.BinarySecurityTokenProcessor;
import org.apache.wss4j.dom.processor.DerivedKeyTokenProcessor;
import org.apache.wss4j.dom.processor.EncryptedAssertionProcessor;
import org.apache.wss4j.dom.processor.EncryptedDataProcessor;
import org.apache.wss4j.dom.processor.EncryptedKeyProcessor;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.processor.ReferenceListProcessor;
import org.apache.wss4j.dom.processor.SAMLTokenProcessor;
import org.apache.wss4j.dom.processor.SecurityContextTokenProcessor;
import org.apache.wss4j.dom.processor.SignatureConfirmationProcessor;
import org.apache.wss4j.dom.processor.SignatureProcessor;
import org.apache.wss4j.dom.processor.TimestampProcessor;
import org.apache.wss4j.dom.processor.UsernameTokenProcessor;
import org.apache.wss4j.dom.resolvers.ResolverAttachment;
import org.apache.wss4j.dom.transform.AttachmentCiphertextTransform;
import org.apache.wss4j.dom.transform.AttachmentCompleteSignatureTransformProvider;
import org.apache.wss4j.dom.transform.AttachmentContentSignatureTransformProvider;
import org.apache.wss4j.dom.transform.STRTransformProvider;
import org.apache.wss4j.dom.validate.SamlAssertionValidator;
import org.apache.wss4j.dom.validate.SignatureTrustValidator;
import org.apache.wss4j.dom.validate.TimestampValidator;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;
import org.apache.wss4j.dom.validate.Validator;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WSSConfig {
    public static final DatatypeFactory DATATYPE_FACTORY;
    private static final Logger LOG;
    private static final Map<Integer, Class<?>> DEFAULT_ACTIONS;
    private static final Map<QName, Class<?>> DEFAULT_PROCESSORS;
    private static final Map<QName, Class<?>> DEFAULT_VALIDATORS;
    private static boolean addJceProviders;
    private static boolean staticallyInitialized;
    private WSTimeSource currentTime;
    public static final WsuIdAllocator DEFAULT_ID_ALLOCATOR;
    protected WsuIdAllocator idAllocator = DEFAULT_ID_ALLOCATOR;
    private final Map<Integer, Object> actionMap = new HashMap(DEFAULT_ACTIONS);
    private final Map<QName, Object> processorMap = new HashMap(DEFAULT_PROCESSORS);
    private final Map<QName, Object> validatorMap = new HashMap(DEFAULT_VALIDATORS);

    private WSSConfig() {
    }

    public static synchronized void init() {
        if (!staticallyInitialized) {
            if (addJceProviders) {
                AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                    @Override
                    public Boolean run() {
                        Security.removeProvider("STRTransform");
                        WSProviderConfig.appendJceProvider("STRTransform", new STRTransformProvider());
                        Security.removeProvider("AttachmentContentSignatureTransform");
                        WSProviderConfig.appendJceProvider("AttachmentContentSignatureTransform", new AttachmentContentSignatureTransformProvider());
                        Security.removeProvider("AttachmentCompleteSignatureTransform");
                        WSProviderConfig.appendJceProvider("AttachmentCompleteSignatureTransform", new AttachmentCompleteSignatureTransformProvider());
                        return true;
                    }
                });
            }
            WSProviderConfig.init();
            staticallyInitialized = true;
        }
    }

    public static synchronized void cleanUp() {
        if (staticallyInitialized) {
            if (addJceProviders) {
                Security.removeProvider("STRTransform");
                Security.removeProvider("AttachmentContentSignatureTransform");
                Security.removeProvider("AttachmentCompleteSignatureTransform");
            }
            WSProviderConfig.cleanUp();
            staticallyInitialized = false;
        }
    }

    public static WSSConfig getNewInstance() {
        WSSConfig.init();
        return new WSSConfig();
    }

    public WsuIdAllocator getIdAllocator() {
        return this.idAllocator;
    }

    public void setIdAllocator(WsuIdAllocator idAllocator) {
        this.idAllocator = idAllocator;
    }

    public Class<?> setAction(int code, Action action) {
        Object result = this.actionMap.put(code, action);
        if (result instanceof Class) {
            return (Class)result;
        }
        if (result instanceof Action) {
            return result.getClass();
        }
        return null;
    }

    public Class<?> setAction(int code, Class<?> clazz) {
        Class<?> result = this.actionMap.put(code, clazz);
        if (result instanceof Class) {
            return result;
        }
        if (result instanceof Action) {
            return result.getClass();
        }
        return null;
    }

    public Action getAction(int action) throws WSSecurityException {
        Object actionObject = this.actionMap.get(action);
        if (actionObject instanceof Class) {
            try {
                return (Action)((Class)actionObject).newInstance();
            }
            catch (Exception ex) {
                LOG.debug(ex.getMessage(), ex);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "unableToLoadClass", new Object[]{((Class)actionObject).getName()});
            }
        }
        if (actionObject instanceof Action) {
            return (Action)actionObject;
        }
        return null;
    }

    public Class<?> setProcessor(QName el, Processor processor) {
        Object result = this.processorMap.put(el, processor);
        if (result instanceof Class) {
            return (Class)result;
        }
        if (result instanceof Processor) {
            return result.getClass();
        }
        return null;
    }

    public Class<?> setProcessor(QName el, Class<?> clazz) {
        Class<?> result = this.processorMap.put(el, clazz);
        if (result instanceof Class) {
            return result;
        }
        if (result instanceof Processor) {
            return result.getClass();
        }
        return null;
    }

    public Class<?> setValidator(QName el, Validator validator) {
        Object result = this.validatorMap.put(el, validator);
        if (result instanceof Class) {
            return (Class)result;
        }
        if (result instanceof Validator) {
            return result.getClass();
        }
        return null;
    }

    public Class<?> setValidator(QName el, Class<?> clazz) {
        Class<?> result = this.validatorMap.put(el, clazz);
        if (result instanceof Class) {
            return result;
        }
        if (result instanceof Validator) {
            return result.getClass();
        }
        return null;
    }

    public Validator getValidator(QName el) throws WSSecurityException {
        Object validatorObject = this.validatorMap.get(el);
        if (validatorObject instanceof Class) {
            try {
                return (Validator)((Class)validatorObject).newInstance();
            }
            catch (Exception ex) {
                LOG.debug(ex.getMessage(), ex);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "unableToLoadClass", new Object[]{((Class)validatorObject).getName()});
            }
        }
        if (validatorObject instanceof Validator) {
            return (Validator)validatorObject;
        }
        return null;
    }

    public Processor getProcessor(QName el) throws WSSecurityException {
        Object processorObject = this.processorMap.get(el);
        if (processorObject instanceof Class) {
            try {
                return (Processor)((Class)processorObject).newInstance();
            }
            catch (Exception ex) {
                LOG.debug(ex.getMessage(), ex);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "unableToLoadClass", new Object[]{((Class)processorObject).getName()});
            }
        }
        if (processorObject instanceof Processor) {
            return (Processor)processorObject;
        }
        return null;
    }

    public WSTimeSource getCurrentTime() {
        if (this.currentTime != null) {
            return this.currentTime;
        }
        return new WSCurrentTimeSource();
    }

    public void setCurrentTime(WSTimeSource currentTime) {
        this.currentTime = currentTime;
    }

    public static boolean isAddJceProviders() {
        return addJceProviders;
    }

    public static void setAddJceProviders(boolean addJceProviders) {
        WSSConfig.addJceProviders = addJceProviders;
        WSProviderConfig.setAddJceProviders(addJceProviders);
    }

    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        }
        catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        LOG = LoggerFactory.getLogger(WSSConfig.class);
        HashMap<Serializable, Class> tmp = new HashMap<Serializable, Class>();
        try {
            tmp.put(Integer.valueOf(1), UsernameTokenAction.class);
            tmp.put(Integer.valueOf(8192), UsernameTokenAction.class);
            tmp.put(Integer.valueOf(4), EncryptionAction.class);
            tmp.put(Integer.valueOf(2), SignatureAction.class);
            tmp.put(Integer.valueOf(32768), SignatureDerivedAction.class);
            tmp.put(Integer.valueOf(65536), EncryptionDerivedAction.class);
            tmp.put(Integer.valueOf(16), SAMLTokenSignedAction.class);
            tmp.put(Integer.valueOf(8), SAMLTokenUnsignedAction.class);
            tmp.put(Integer.valueOf(32), TimestampAction.class);
            tmp.put(Integer.valueOf(64), UsernameTokenSignedAction.class);
            tmp.put(Integer.valueOf(128), SignatureConfirmationAction.class);
            tmp.put(Integer.valueOf(16384), CustomTokenAction.class);
        }
        catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        DEFAULT_ACTIONS = Collections.unmodifiableMap(tmp);
        tmp = new HashMap();
        try {
            tmp.put(WSConstants.SAML_TOKEN, SAMLTokenProcessor.class);
            tmp.put(WSConstants.SAML2_TOKEN, SAMLTokenProcessor.class);
            tmp.put(WSConstants.ENCRYPTED_ASSERTION, EncryptedAssertionProcessor.class);
            tmp.put(WSConstants.ENCRYPTED_KEY, EncryptedKeyProcessor.class);
            tmp.put(WSConstants.SIGNATURE, SignatureProcessor.class);
            tmp.put(WSConstants.TIMESTAMP, TimestampProcessor.class);
            tmp.put(WSConstants.USERNAME_TOKEN, UsernameTokenProcessor.class);
            tmp.put(WSConstants.REFERENCE_LIST, ReferenceListProcessor.class);
            tmp.put(WSConstants.SIGNATURE_CONFIRMATION, SignatureConfirmationProcessor.class);
            tmp.put(WSConstants.DERIVED_KEY_TOKEN_05_02, DerivedKeyTokenProcessor.class);
            tmp.put(WSConstants.DERIVED_KEY_TOKEN_05_12, (Class)tmp.get(WSConstants.DERIVED_KEY_TOKEN_05_02));
            tmp.put(WSConstants.SECURITY_CONTEXT_TOKEN_05_02, SecurityContextTokenProcessor.class);
            tmp.put(WSConstants.SECURITY_CONTEXT_TOKEN_05_12, (Class)tmp.get(WSConstants.SECURITY_CONTEXT_TOKEN_05_02));
            tmp.put(WSConstants.BINARY_TOKEN, BinarySecurityTokenProcessor.class);
            tmp.put(WSConstants.ENCRYPTED_DATA, EncryptedDataProcessor.class);
        }
        catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        DEFAULT_PROCESSORS = Collections.unmodifiableMap(tmp);
        tmp = new HashMap();
        try {
            tmp.put(WSConstants.SAML_TOKEN, SamlAssertionValidator.class);
            tmp.put(WSConstants.SAML2_TOKEN, SamlAssertionValidator.class);
            tmp.put(WSConstants.SIGNATURE, SignatureTrustValidator.class);
            tmp.put(WSConstants.TIMESTAMP, TimestampValidator.class);
            tmp.put(WSConstants.USERNAME_TOKEN, UsernameTokenValidator.class);
        }
        catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
        }
        DEFAULT_VALIDATORS = Collections.unmodifiableMap(tmp);
        addJceProviders = true;
        staticallyInitialized = false;
        DEFAULT_ID_ALLOCATOR = new WsuIdAllocator(){

            @Override
            public String createId(String prefix, Object o) {
                if (prefix == null) {
                    return IDGenerator.generateID("_");
                }
                return IDGenerator.generateID(prefix);
            }

            @Override
            public String createSecureId(String prefix, Object o) {
                return IDGenerator.generateID(prefix);
            }
        };
        try {
            Transform.register("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform", AttachmentCiphertextTransform.class);
        }
        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }
        ResourceResolver.register(new ResolverAttachment(), false);
    }
}

