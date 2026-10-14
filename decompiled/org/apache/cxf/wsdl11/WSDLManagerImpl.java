/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Resource;
import javax.wsdl.BindingInput;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Output;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.mime.MIMEPart;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.CacheMap;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.service.model.ServiceSchemaInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.XMLStreamReaderWrapper;
import org.apache.cxf.wsdl.WSDLConstants;
import org.apache.cxf.wsdl.WSDLExtensionLoader;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.CatalogWSDLLocator;
import org.apache.cxf.wsdl11.ResourceManagerWSDLLocator;
import org.apache.cxf.wsdl11.SchemaSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

@NoJSR250Annotations(unlessNull={"bus"})
public class WSDLManagerImpl
implements WSDLManager {
    final ExtensionRegistry registry;
    final WSDLFactory factory;
    final Map<Object, Definition> definitionsMap;
    final Map<Object, ServiceSchemaInfo> schemaCacheMap;
    private boolean disableSchemaCache;
    private Bus bus;
    private XMLStreamReaderWrapper xmlStreamReaderWrapper;

    public WSDLManagerImpl() throws BusException {
        this(null);
    }

    private WSDLManagerImpl(Bus b) throws BusException {
        try {
            if (System.getSecurityManager() == null) {
                this.factory = WSDLFactory.newInstance();
            } else {
                try {
                    this.factory = AccessController.doPrivileged(WSDLFactory::newInstance);
                }
                catch (PrivilegedActionException paex) {
                    throw new BusException(paex);
                }
            }
            this.registry = this.factory.newPopulatedExtensionRegistry();
            this.registry.registerSerializer(Types.class, WSDLConstants.QNAME_SCHEMA, new SchemaSerializer());
            QName header = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "header");
            this.registry.registerDeserializer(MIMEPart.class, header, this.registry.queryDeserializer(BindingInput.class, header));
            this.registry.registerSerializer(MIMEPart.class, header, this.registry.querySerializer(BindingInput.class, header));
            Class<?> clazz = this.registry.createExtension(BindingInput.class, header).getClass();
            this.registry.mapExtensionTypes(MIMEPart.class, header, clazz);
            this.addExtensionAttributeTypes(this.registry);
        }
        catch (WSDLException e) {
            throw new BusException(e);
        }
        this.definitionsMap = new CacheMap<Object, Definition>();
        this.schemaCacheMap = new CacheMap<Object, ServiceSchemaInfo>();
        this.setBus(b);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.bus.setExtension(this, WSDLManager.class);
            ConfiguredBeanLocator loc = this.bus.getExtension(ConfiguredBeanLocator.class);
            if (loc != null) {
                loc.getBeansOfType(WSDLExtensionLoader.class);
            }
        }
    }

    @Override
    public WSDLFactory getWSDLFactory() {
        return this.factory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<Object, Definition> getDefinitions() {
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            return Collections.unmodifiableMap(this.definitionsMap);
        }
    }

    protected Bus getBus() {
        return this.bus;
    }

    @Override
    public ExtensionRegistry getExtensionRegistry() {
        return this.registry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Definition getDefinition(String url2) throws WSDLException {
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            if (this.definitionsMap.containsKey(url2)) {
                return this.definitionsMap.get(url2);
            }
        }
        Definition def = this.loadDefinition(url2);
        Map<Object, Definition> map2 = this.definitionsMap;
        synchronized (map2) {
            this.definitionsMap.put(url2, def);
        }
        return def;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Definition getDefinition(Element el) throws WSDLException {
        Definition def;
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            if (this.definitionsMap.containsKey(el)) {
                return this.definitionsMap.get(el);
            }
        }
        WSDLReader reader = this.factory.newWSDLReader();
        reader.setFeature("javax.wsdl.verbose", false);
        reader.setExtensionRegistry(this.registry);
        if (System.getSecurityManager() == null) {
            def = reader.readWSDL("", el);
        } else {
            try {
                def = AccessController.doPrivileged(() -> reader.readWSDL("", el));
            }
            catch (PrivilegedActionException paex) {
                throw new WSDLException("PARSER_ERROR", paex.getMessage(), paex);
            }
        }
        Map<Object, Definition> map2 = this.definitionsMap;
        synchronized (map2) {
            this.definitionsMap.put(el, def);
        }
        return def;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addDefinition(Object key, Definition wsdl) {
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            this.definitionsMap.put(key, wsdl);
        }
    }

    protected Definition loadDefinition(String url2) throws WSDLException {
        WSDLReader reader = this.factory.newWSDLReader();
        reader.setFeature("javax.wsdl.verbose", false);
        reader.setFeature("javax.wsdl.importDocuments", true);
        reader.setExtensionRegistry(this.registry);
        url2 = new String(url2);
        CatalogWSDLLocator catLocator = new CatalogWSDLLocator(url2, this.bus);
        ResourceManagerWSDLLocator wsdlLocator = new ResourceManagerWSDLLocator(url2, catLocator, this.bus);
        InputSource src = wsdlLocator.getBaseInputSource();
        Definition def = null;
        if (src.getByteStream() != null || src.getCharacterStream() != null) {
            Document doc;
            XMLStreamReader xmlReader = null;
            try {
                xmlReader = StaxUtils.createXMLStreamReader(src);
                if (this.xmlStreamReaderWrapper != null) {
                    xmlReader = this.xmlStreamReaderWrapper.wrap(xmlReader);
                }
                doc = StaxUtils.read(xmlReader, true);
                if (src.getSystemId() != null) {
                    try {
                        doc.setDocumentURI(new String(src.getSystemId()));
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            catch (Exception e) {
                throw new WSDLException("PARSER_ERROR", e.getMessage(), e);
            }
            finally {
                try {
                    StaxUtils.close(xmlReader);
                }
                catch (XMLStreamException ex) {
                    throw new WSDLException("PARSER_ERROR", ex.getMessage(), ex);
                }
            }
            if (System.getSecurityManager() == null) {
                def = reader.readWSDL(wsdlLocator, doc.getDocumentElement());
            } else {
                try {
                    def = AccessController.doPrivileged(() -> reader.readWSDL(wsdlLocator, doc.getDocumentElement()));
                }
                catch (PrivilegedActionException paex) {
                    throw new WSDLException("PARSER_ERROR", paex.getMessage(), paex);
                }
            }
        } else if (System.getSecurityManager() == null) {
            def = reader.readWSDL(wsdlLocator);
        } else {
            try {
                def = AccessController.doPrivileged(() -> reader.readWSDL(wsdlLocator));
            }
            catch (PrivilegedActionException paex) {
                throw new WSDLException("PARSER_ERROR", paex.getMessage(), paex);
            }
        }
        return def;
    }

    public void setXMLStreamReaderWrapper(XMLStreamReaderWrapper wrapper) {
        this.xmlStreamReaderWrapper = wrapper;
    }

    private void addExtensionAttributeTypes(ExtensionRegistry extreg) {
        QName qn = new QName("http://www.w3.org/2006/05/addressing/wsdl", "Action");
        extreg.registerExtensionAttributeType(Input.class, qn, 0);
        extreg.registerExtensionAttributeType(Output.class, qn, 0);
        extreg.registerExtensionAttributeType(Fault.class, qn, 0);
        qn = new QName("http://www.w3.org/2007/05/addressing/metadata", "Action");
        extreg.registerExtensionAttributeType(Input.class, qn, 0);
        extreg.registerExtensionAttributeType(Output.class, qn, 0);
        extreg.registerExtensionAttributeType(Fault.class, qn, 0);
        qn = new QName("http://www.w3.org/2005/02/addressing/wsdl", "Action");
        extreg.registerExtensionAttributeType(Input.class, qn, 0);
        extreg.registerExtensionAttributeType(Output.class, qn, 0);
        extreg.registerExtensionAttributeType(Fault.class, qn, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServiceSchemaInfo getSchemasForDefinition(Definition wsdl) {
        if (this.disableSchemaCache) {
            return null;
        }
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            for (Map.Entry<Object, Definition> e : this.definitionsMap.entrySet()) {
                ServiceSchemaInfo info;
                if (e.getValue() != wsdl || (info = this.schemaCacheMap.get(e.getKey())) == null) continue;
                return info;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void putSchemasForDefinition(Definition wsdl, ServiceSchemaInfo schemas) {
        if (!this.disableSchemaCache) {
            Map<Object, Definition> map = this.definitionsMap;
            synchronized (map) {
                for (Map.Entry<Object, Definition> e : this.definitionsMap.entrySet()) {
                    if (e.getValue() != wsdl) continue;
                    this.schemaCacheMap.put(e.getKey(), schemas);
                }
            }
        }
    }

    public boolean isDisableSchemaCache() {
        return this.disableSchemaCache;
    }

    public void setDisableSchemaCache(boolean disableSchemaCache) {
        this.disableSchemaCache = disableSchemaCache;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeDefinition(Definition wsdl) {
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            ArrayList<Object> keys2 = new ArrayList<Object>();
            for (Map.Entry<Object, Definition> entry : this.definitionsMap.entrySet()) {
                if (entry.getValue() != wsdl) continue;
                keys2.add(entry.getKey());
            }
            for (Map.Entry<Object, Definition> entry : keys2) {
                this.definitionsMap.remove(entry);
                this.schemaCacheMap.remove(entry);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeDefinition(String url2) {
        Map<Object, Definition> map = this.definitionsMap;
        synchronized (map) {
            Definition wsdl = this.definitionsMap.get(url2);
            if (wsdl != null) {
                ArrayList<Object> keys2 = new ArrayList<Object>();
                for (Map.Entry<Object, Definition> entry : this.definitionsMap.entrySet()) {
                    if (entry.getValue() != wsdl) continue;
                    keys2.add(entry.getKey());
                }
                for (Map.Entry<Object, Definition> entry : keys2) {
                    this.definitionsMap.remove(entry);
                    this.schemaCacheMap.remove(entry);
                }
            }
        }
    }
}

