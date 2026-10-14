/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import org.apache.wss4j.common.crypto.WSProviderConfig;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.OpenSAMLBootstrap;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.provider.MapBasedConfiguration;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLConfigurationException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.config.SAMLConfiguration;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.Signer;
import org.opensaml.xmlsec.signature.support.SignerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

public final class OpenSAMLUtil {
    private static final Logger LOG = LoggerFactory.getLogger(OpenSAMLUtil.class);
    private static XMLObjectProviderRegistry providerRegistry;
    private static XMLObjectBuilderFactory builderFactory;
    private static MarshallerFactory marshallerFactory;
    private static UnmarshallerFactory unmarshallerFactory;
    private static boolean samlEngineInitialized;

    private OpenSAMLUtil() {
    }

    public static synchronized void initSamlEngine() {
        OpenSAMLUtil.initSamlEngine(true);
    }

    public static synchronized void initSamlEngine(boolean includeXacml) {
        if (!samlEngineInitialized) {
            LOG.debug("Initializing the opensaml2 library...");
            WSProviderConfig.init();
            MapBasedConfiguration configuration = new MapBasedConfiguration();
            ConfigurationService.setConfiguration(configuration);
            providerRegistry = new XMLObjectProviderRegistry();
            configuration.register(XMLObjectProviderRegistry.class, providerRegistry, "default");
            try {
                OpenSAMLBootstrap.bootstrap(includeXacml);
                SAMLConfiguration samlConfiguration = new SAMLConfiguration();
                configuration.register(SAMLConfiguration.class, samlConfiguration, "default");
                builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
                marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();
                unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
                try {
                    OpenSAMLUtil.configureParserPool();
                }
                catch (Throwable t2) {
                    LOG.warn("Unable to bootstrap the parser pool part of the opensaml library - some SAML operations may fail", t2);
                }
                samlEngineInitialized = true;
                LOG.debug("opensaml3 library bootstrap complete");
            }
            catch (XMLConfigurationException ex) {
                LOG.error("Unable to bootstrap the opensaml3 library - all SAML operations will fail", ex);
            }
        }
    }

    private static void configureParserPool() throws Throwable {
        BasicParserPool pp = new BasicParserPool();
        pp.setMaxPoolSize(50);
        pp.initialize();
        providerRegistry.setParserPool(pp);
    }

    public static ParserPool getParserPool() {
        return providerRegistry.getParserPool();
    }

    public static XMLObject fromDom(Element root) throws WSSecurityException {
        if (root == null) {
            LOG.debug("Attempting to unmarshal a null element!");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Error unmarshalling a SAML assertion"});
        }
        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(root);
        if (unmarshaller == null) {
            LOG.debug("Unable to find an unmarshaller for element: " + root.getLocalName());
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Error unmarshalling a SAML assertion"});
        }
        try {
            return unmarshaller.unmarshall(root);
        }
        catch (UnmarshallingException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error unmarshalling a SAML assertion"});
        }
    }

    public static Element toDom(XMLObject xmlObject, Document doc) throws WSSecurityException {
        return OpenSAMLUtil.toDom(xmlObject, doc, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Element toDom(XMLObject xmlObject, Document doc, boolean signObject) throws WSSecurityException {
        Marshaller marshaller = marshallerFactory.getMarshaller(xmlObject);
        Element element = null;
        DocumentFragment frag = doc == null ? null : doc.createDocumentFragment();
        try {
            if (frag != null) {
                while (doc.getFirstChild() != null) {
                    frag.appendChild(doc.removeChild(doc.getFirstChild()));
                }
            }
            try {
                element = doc == null ? marshaller.marshall(xmlObject) : marshaller.marshall(xmlObject, doc);
            }
            catch (MarshallingException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error marshalling a SAML assertion"});
            }
            if (signObject) {
                OpenSAMLUtil.signXMLObject(xmlObject);
            }
        }
        finally {
            if (frag != null) {
                while (doc.getFirstChild() != null) {
                    doc.removeChild(doc.getFirstChild());
                }
                doc.appendChild(frag);
            }
        }
        return element;
    }

    private static void signXMLObject(XMLObject xmlObject) throws WSSecurityException {
        if (xmlObject instanceof Response) {
            Response response = (Response)xmlObject;
            if (response.getAssertions() != null) {
                for (Assertion assertion : response.getAssertions()) {
                    OpenSAMLUtil.signObject(assertion.getSignature());
                }
            }
            OpenSAMLUtil.signObject(response.getSignature());
        } else if (xmlObject instanceof org.opensaml.saml.saml2.core.Response) {
            org.opensaml.saml.saml2.core.Response response = (org.opensaml.saml.saml2.core.Response)xmlObject;
            if (response.getAssertions() != null) {
                for (org.opensaml.saml.saml2.core.Assertion assertion : response.getAssertions()) {
                    OpenSAMLUtil.signObject(assertion.getSignature());
                }
            }
            OpenSAMLUtil.signObject(response.getSignature());
        } else if (xmlObject instanceof SignableSAMLObject) {
            OpenSAMLUtil.signObject(((SignableSAMLObject)xmlObject).getSignature());
        }
    }

    private static void signObject(Signature signature) throws WSSecurityException {
        if (signature != null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(SignerProvider.class.getClassLoader());
                Signer.signObject(signature);
            }
            catch (SignatureException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error signing a SAML assertion"});
            }
            finally {
                Thread.currentThread().setContextClassLoader(loader);
            }
        }
    }

    public static Signature buildSignature() {
        QName qName = Signature.DEFAULT_ELEMENT_NAME;
        XMLObjectBuilder<?> builder = builderFactory.getBuilder(qName);
        if (builder == null) {
            LOG.error("Unable to retrieve builder for object QName " + qName);
            return null;
        }
        return (Signature)builder.buildObject(qName.getNamespaceURI(), qName.getLocalPart(), qName.getPrefix());
    }

    public static boolean isMethodSenderVouches(String confirmMethod) {
        return confirmMethod != null && confirmMethod.startsWith("urn:oasis:names:tc:SAML:") && confirmMethod.endsWith(":cm:sender-vouches");
    }

    public static boolean isMethodHolderOfKey(String confirmMethod) {
        return confirmMethod != null && confirmMethod.startsWith("urn:oasis:names:tc:SAML:") && confirmMethod.endsWith(":cm:holder-of-key");
    }

    static {
        samlEngineInitialized = false;
    }
}

