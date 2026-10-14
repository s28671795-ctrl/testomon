/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLConfigurationException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfigurator {
    @Nonnull
    @NotEmpty
    public static final String XMLTOOLING_CONFIG_NS = "http://www.opensaml.org/xmltooling-config";
    @Nonnull
    @NotEmpty
    public static final String XMLTOOLING_CONFIG_PREFIX = "xt";
    @Nonnull
    @NotEmpty
    public static final String XMLTOOLING_DEFAULT_OBJECT_PROVIDER = "DEFAULT";
    @Nonnull
    @NotEmpty
    public static final String XMLTOOLING_SCHEMA_LOCATION = "/schema/xmltooling-config.xsd";
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(XMLConfigurator.class);
    private BasicParserPool parserPool = new BasicParserPool();
    private Schema configurationSchema;
    @Nonnull
    private final XMLObjectProviderRegistry registry;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public XMLConfigurator() throws XMLConfigurationException {
        SchemaFactory factory2 = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        StreamSource schemaSource = new StreamSource(XMLConfigurator.class.getResourceAsStream(XMLTOOLING_SCHEMA_LOCATION));
        try {
            this.configurationSchema = factory2.newSchema(schemaSource);
            this.parserPool.setIgnoreComments(true);
            this.parserPool.setIgnoreElementContentWhitespace(true);
            this.parserPool.setSchema(this.configurationSchema);
            this.parserPool.initialize();
        }
        catch (SAXException e) {
            throw new XMLConfigurationException("Unable to read XMLTooling configuration schema", e);
        }
        catch (ComponentInitializationException e) {
            throw new XMLConfigurationException("Unable to initialize parser pool", e);
        }
        Class<ConfigurationService> clazz = ConfigurationService.class;
        synchronized (ConfigurationService.class) {
            XMLObjectProviderRegistry reg = ConfigurationService.get(XMLObjectProviderRegistry.class);
            if (reg == null) {
                this.log.debug("XMLObjectProviderRegistry did not exist in ConfigurationService, will be created");
                reg = new XMLObjectProviderRegistry();
                ConfigurationService.register(XMLObjectProviderRegistry.class, reg);
            }
            this.registry = reg;
            // ** MonitorExit[var3_5] (shouldn't be in output)
            return;
        }
    }

    public void load(@Nullable File configurationFile) throws XMLConfigurationException {
        if (configurationFile == null || !configurationFile.canRead()) {
            this.log.error("Unable to read configuration file {}", (Object)configurationFile);
            return;
        }
        try {
            if (configurationFile.isDirectory()) {
                File[] configurations = configurationFile.listFiles();
                for (int i = 0; i < configurations.length; ++i) {
                    this.log.debug("Parsing configuration file {}", (Object)configurations[i].getAbsolutePath());
                    this.load(new FileInputStream(configurations[i]));
                }
            } else {
                this.log.debug("Parsing configuration file {}", (Object)configurationFile.getAbsolutePath());
                this.load(new FileInputStream(configurationFile));
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            // empty catch block
        }
    }

    public void load(@Nonnull InputStream configurationStream) throws XMLConfigurationException {
        try {
            Document configuration = this.parserPool.parse(configurationStream);
            this.load(configuration);
        }
        catch (XMLParserException e) {
            this.log.error("Invalid configuration file", e);
            throw new XMLConfigurationException("Unable to create DocumentBuilder", e);
        }
    }

    public void load(@Nonnull Document configuration) throws XMLConfigurationException {
        Element root = Constraint.isNotNull(configuration.getDocumentElement(), "Document element cannot be null");
        this.log.debug("Loading configuration from XML Document");
        this.log.trace("{}", (Object)SerializeSupport.nodeToString(root));
        this.log.debug("Schema validating configuration Document");
        this.validateConfiguration(configuration);
        this.log.debug("Configuration document validated");
        this.load(root);
    }

    protected void load(@Nonnull Element configurationRoot) throws XMLConfigurationException {
        NodeList idAttributesNodes;
        NodeList objectProviders = configurationRoot.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "ObjectProviders");
        if (objectProviders.getLength() > 0) {
            this.log.debug("Preparing to load ObjectProviders");
            this.initializeObjectProviders((Element)objectProviders.item(0));
            this.log.debug("ObjectProviders load complete");
        }
        if ((idAttributesNodes = configurationRoot.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "IDAttributes")).getLength() > 0) {
            this.log.debug("Preparing to load IDAttributes");
            this.initializeIDAttributes((Element)idAttributesNodes.item(0));
            this.log.debug("IDAttributes load complete");
        }
    }

    protected void initializeObjectProviders(Element objectProviders) throws XMLConfigurationException {
        NodeList providerList = objectProviders.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "ObjectProvider");
        for (int i = 0; i < providerList.getLength(); ++i) {
            Element objectProvider = (Element)providerList.item(i);
            Attr qNameAttrib = objectProvider.getAttributeNodeNS(null, "qualifiedName");
            QName objectProviderName = AttributeSupport.getAttributeValueAsQName(qNameAttrib);
            this.log.debug("Initializing object provider {}", (Object)objectProviderName);
            try {
                Element configuration = (Element)objectProvider.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "BuilderClass").item(0);
                XMLObjectBuilder builder = (XMLObjectBuilder)this.createClassInstance(configuration);
                configuration = (Element)objectProvider.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "MarshallingClass").item(0);
                Marshaller marshaller = (Marshaller)this.createClassInstance(configuration);
                configuration = (Element)objectProvider.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "UnmarshallingClass").item(0);
                Unmarshaller unmarshaller = (Unmarshaller)this.createClassInstance(configuration);
                this.getRegistry().registerObjectProvider(objectProviderName, builder, marshaller, unmarshaller);
                this.log.debug("{} intialized and configuration cached", (Object)objectProviderName);
                continue;
            }
            catch (XMLConfigurationException e) {
                this.log.error("Error initializing object provier {}", (Object)objectProvider, (Object)e);
                this.getRegistry().deregisterObjectProvider(objectProviderName);
                throw e;
            }
        }
    }

    protected void initializeIDAttributes(Element idAttributesElement) throws XMLConfigurationException {
        NodeList idAttributeList = idAttributesElement.getElementsByTagNameNS(XMLTOOLING_CONFIG_NS, "IDAttribute");
        for (int i = 0; i < idAttributeList.getLength(); ++i) {
            Element idAttributeElement = (Element)idAttributeList.item(i);
            QName attributeQName = ElementSupport.getElementContentAsQName(idAttributeElement);
            if (attributeQName == null) {
                this.log.debug("IDAttribute element was empty, no registration performed");
                continue;
            }
            this.getRegistry().registerIDAttribute(attributeQName);
            this.log.debug("IDAttribute {} has been registered", (Object)attributeQName);
        }
    }

    protected Object createClassInstance(Element configuration) throws XMLConfigurationException {
        String className = StringSupport.trimOrNull(configuration.getAttributeNS(null, "className"));
        if (className == null) {
            return null;
        }
        try {
            this.log.trace("Creating instance of {}", (Object)className);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = classLoader.loadClass(className);
            Constructor<?> constructor = clazz.getConstructor(new Class[0]);
            return constructor.newInstance(new Object[0]);
        }
        catch (Exception e) {
            String errorMsg = "Cannot create instance of " + className;
            this.log.error(errorMsg, e);
            throw new XMLConfigurationException(errorMsg, e);
        }
    }

    protected void validateConfiguration(Document configuration) throws XMLConfigurationException {
        try {
            Validator schemaValidator = this.configurationSchema.newValidator();
            schemaValidator.validate(new DOMSource(configuration));
        }
        catch (IOException e) {
            String errorMsg = "Unable to read configuration file DOM";
            this.log.error(errorMsg, e);
            throw new XMLConfigurationException(errorMsg, e);
        }
        catch (SAXException e) {
            String errorMsg = "Configuration file does not validate against schema";
            this.log.error(errorMsg, e);
            throw new XMLConfigurationException(errorMsg, e);
        }
    }

    protected XMLObjectProviderRegistry getRegistry() {
        return this.registry;
    }
}

