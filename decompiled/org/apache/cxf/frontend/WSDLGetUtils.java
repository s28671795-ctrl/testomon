/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.schema.SchemaImport;
import javax.wsdl.extensions.schema.SchemaReference;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.Bus;
import org.apache.cxf.catalog.OASISCatalogManager;
import org.apache.cxf.catalog.OASISCatalogManagerHelper;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.URIParserUtil;
import org.apache.cxf.common.util.UrlUtils;
import org.apache.cxf.frontend.WSDLGetInterceptor;
import org.apache.cxf.frontend.WSDLQueryException;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.ResourceManagerWSDLLocator;
import org.apache.cxf.wsdl11.ServiceWSDLBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class WSDLGetUtils {
    public static final String AUTO_REWRITE_ADDRESS = "autoRewriteSoapAddress";
    public static final String AUTO_REWRITE_ADDRESS_ALL = "autoRewriteSoapAddressForAllServices";
    public static final String PUBLISHED_ENDPOINT_URL = "publishedEndpointUrl";
    public static final String WSDL_CREATE_IMPORTS = "org.apache.cxf.wsdl.create.imports";
    private static final String WSDLS_KEY = WSDLGetUtils.class.getName() + ".WSDLs";
    private static final String SCHEMAS_KEY = WSDLGetUtils.class.getName() + ".Schemas";
    private static final Logger LOG = LogUtils.getL7dLogger(WSDLGetInterceptor.class);

    public Set<String> getWSDLIds(Message message, String base, String ctxUri, EndpointInfo endpointInfo) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("wsdl", "");
        this.getDocument(message, base, params, ctxUri, endpointInfo);
        Map mp = CastUtils.cast((Map)endpointInfo.getService().getProperty(WSDLS_KEY));
        return mp.keySet();
    }

    public Map<String, String> getSchemaLocations(Message message, String base, String ctxUri, EndpointInfo endpointInfo) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("wsdl", "");
        this.getDocument(message, base, params, ctxUri, endpointInfo);
        Map mp = CastUtils.cast((Map)endpointInfo.getService().getProperty(SCHEMAS_KEY));
        HashMap<String, String> schemas = new HashMap<String, String>();
        for (Map.Entry ent : mp.entrySet()) {
            params.clear();
            params.put("xsd", (String)ent.getKey());
            Document doc = this.getDocument(message, base, params, ctxUri, endpointInfo);
            schemas.put(doc.getDocumentElement().getAttribute("targetNamespace"), this.buildUrl(base, ctxUri, "xsd=" + (String)ent.getKey()));
        }
        return schemas;
    }

    public Document getDocument(Message message, String base, Map<String, String> params, String ctxUri, EndpointInfo endpointInfo) {
        Document doc = null;
        try {
            Bus bus = message.getExchange().getBus();
            base = this.getPublishedEndpointURL(message, base, endpointInfo);
            Map<String, Definition> mp = this.getWSDLKeyDefinition(endpointInfo);
            Map<String, SchemaReference> smp = this.getSchemaKeySchemaReference(endpointInfo);
            this.updateWSDLKeyDefinition(bus, mp, message, smp, base, endpointInfo);
            if (params.containsKey("wsdl")) {
                String wsdl = URLDecoder.decode(params.get("wsdl"), "utf-8");
                doc = this.writeWSDLDocument(message, mp, smp, wsdl, base, endpointInfo);
            } else if (params.get("xsd") != null) {
                String xsd = URLDecoder.decode(params.get("xsd"), "utf-8");
                doc = this.readXSDDocument(bus, xsd, smp, base);
                this.updateDoc(doc, base, mp, smp, message, xsd);
            }
        }
        catch (WSDLQueryException wex) {
            throw wex;
        }
        catch (Exception wex) {
            LOG.log(Level.SEVERE, wex.getMessage(), wex);
            throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("COULD_NOT_PROVIDE_WSDL", LOG, base), (Throwable)wex);
        }
        return doc;
    }

    protected String mapUri(Bus bus, String base, Map<String, SchemaReference> smp, String loc, String xsd, String resolvedXsd) throws UnsupportedEncodingException {
        String key = loc;
        try {
            boolean absoluteLocUri = new URI(loc).isAbsolute();
            if (!absoluteLocUri && xsd != null) {
                String resolved;
                key = new URI(xsd).resolve(loc).toString();
                SchemaReference ref = smp.get(URLDecoder.decode(key, "utf-8"));
                if (ref == null && (resolved = this.resolveWithCatalogs(OASISCatalogManager.getCatalogManager(bus), key, base)) != null && smp.containsKey(URLDecoder.decode(resolved, "utf-8"))) {
                    return base + "?xsd=" + key.replace(" ", "%20");
                }
            } else if (!absoluteLocUri && xsd == null) {
                key = new URI(".").resolve(loc).toString();
            }
        }
        catch (URISyntaxException absoluteLocUri) {
            // empty catch block
        }
        SchemaReference ref = smp.get(URLDecoder.decode(key, "utf-8"));
        if (ref == null && resolvedXsd != null) {
            try {
                String key2 = new URI(resolvedXsd).resolve(loc).toString();
                SchemaReference ref2 = smp.get(URLDecoder.decode(key2, "utf-8"));
                if (ref2 == null) {
                    String resolved = this.resolveWithCatalogs(OASISCatalogManager.getCatalogManager(bus), key2, base);
                    if (resolved != null && smp.containsKey(URLDecoder.decode(resolved, "utf-8"))) {
                        ref = smp.get(URLDecoder.decode(resolved, "utf-8"));
                    }
                } else {
                    ref = smp.get(URLDecoder.decode(key2, "utf-8"));
                }
            }
            catch (URISyntaxException key2) {
                // empty catch block
            }
            if (ref != null) {
                int count = 1;
                while (smp.containsKey("_import" + count + ".xsd")) {
                    ++count;
                }
                key = "_import" + count + ".xsd";
                smp.put(key, ref);
            }
        }
        if (ref != null) {
            return base + "?xsd=" + key.replace(" ", "%20");
        }
        return null;
    }

    @Deprecated
    protected void updateDoc(Document doc, String base, Map<String, Definition> mp, Map<String, SchemaReference> smp, Message message, String xsd, String wsdl) {
        this.updateDoc(doc, base, mp, smp, message, xsd != null ? xsd : wsdl);
    }

    protected void updateDoc(Document doc, String base, Map<String, Definition> mp, Map<String, SchemaReference> smp, Message message, String xsdWsdlPar) {
        Bus bus = message.getExchange().getBus();
        List<Element> elementList = null;
        try {
            String sl;
            elementList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://www.w3.org/2001/XMLSchema", "import");
            for (Element el : elementList) {
                sl = el.getAttribute("schemaLocation");
                if ((sl = this.mapUri(bus, base, smp, sl, xsdWsdlPar, doc.getDocumentURI())) == null) continue;
                el.setAttribute("schemaLocation", sl);
            }
            elementList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://www.w3.org/2001/XMLSchema", "include");
            for (Element el : elementList) {
                sl = el.getAttribute("schemaLocation");
                if ((sl = this.mapUri(bus, base, smp, sl, xsdWsdlPar, doc.getDocumentURI())) == null) continue;
                el.setAttribute("schemaLocation", sl);
            }
            elementList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://www.w3.org/2001/XMLSchema", "redefine");
            for (Element el : elementList) {
                sl = el.getAttribute("schemaLocation");
                if ((sl = this.mapUri(bus, base, smp, sl, xsdWsdlPar, doc.getDocumentURI())) == null) continue;
                el.setAttribute("schemaLocation", sl);
            }
            elementList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://schemas.xmlsoap.org/wsdl/", "import");
            for (Element el : elementList) {
                sl = el.getAttribute("location");
                try {
                    sl = this.getLocationURI(sl, xsdWsdlPar);
                }
                catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
                if (!mp.containsKey(URLDecoder.decode(sl, "utf-8"))) continue;
                el.setAttribute("location", base + "?wsdl=" + sl.replace(" ", "%20"));
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("COULD_NOT_PROVIDE_WSDL", LOG, base), (Throwable)e);
        }
        boolean rewriteAllSoapAddress = MessageUtils.getContextualBoolean(message, AUTO_REWRITE_ADDRESS_ALL, false);
        if (rewriteAllSoapAddress) {
            List<Element> portList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://schemas.xmlsoap.org/wsdl/", "port");
            String basePath = (String)message.get("http.base.path");
            for (Element el : portList) {
                this.rewriteAddressProtocolHostPort(base, el, basePath, "http://schemas.xmlsoap.org/wsdl/soap/");
                this.rewriteAddressProtocolHostPort(base, el, basePath, "http://schemas.xmlsoap.org/wsdl/soap12/");
            }
        }
        if (MessageUtils.getContextualBoolean(message, AUTO_REWRITE_ADDRESS, true) || rewriteAllSoapAddress) {
            List<Element> serviceList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://schemas.xmlsoap.org/wsdl/", "service");
            for (Element serviceEl : serviceList) {
                String serviceName = serviceEl.getAttribute("name");
                if (!serviceName.equals(message.getExchange().getService().getName().getLocalPart())) continue;
                elementList = DOMUtils.findAllElementsByTagNameNS(doc.getDocumentElement(), "http://schemas.xmlsoap.org/wsdl/", "port");
                for (Element el : elementList) {
                    String name = el.getAttribute("name");
                    if (!name.equals(message.getExchange().getEndpoint().getEndpointInfo().getName().getLocalPart())) continue;
                    this.rewriteAddress(base, el, "http://schemas.xmlsoap.org/wsdl/soap/");
                    this.rewriteAddress(base, el, "http://schemas.xmlsoap.org/wsdl/soap12/");
                }
            }
        }
        try {
            doc.setXmlStandalone(true);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void rewriteAddress(String base, Element el, String soapNS) {
        List<Element> sadEls = DOMUtils.findAllElementsByTagNameNS(el, soapNS, "address");
        for (Element soapAddress : sadEls) {
            soapAddress.setAttribute("location", base);
        }
    }

    protected void rewriteAddressProtocolHostPort(String base, Element el, String httpBasePathProp, String soapNS) {
        List<Element> sadEls = DOMUtils.findAllElementsByTagNameNS(el, soapNS, "address");
        for (Element soapAddress : sadEls) {
            String location = soapAddress.getAttribute("location").trim();
            try {
                URI locUri = new URI(location);
                if (locUri.isAbsolute()) {
                    URL baseUrl = new URL(base);
                    StringBuilder sb = new StringBuilder(baseUrl.getProtocol());
                    sb.append("://").append(baseUrl.getHost());
                    int port = baseUrl.getPort();
                    if (port > 0) {
                        sb.append(':').append(port);
                    }
                    sb.append(locUri.getPath());
                    soapAddress.setAttribute("location", sb.toString());
                    continue;
                }
                if (httpBasePathProp == null) continue;
                soapAddress.setAttribute("location", httpBasePathProp + location);
            }
            catch (Exception exception) {}
        }
    }

    protected String resolveWithCatalogs(OASISCatalogManager catalogs, String start2, String base) {
        try {
            return new OASISCatalogManagerHelper().resolve(catalogs, start2, base);
        }
        catch (Exception exception) {
            return null;
        }
    }

    protected void updateDefinition(Bus bus, Definition def, Map<String, Definition> done, Map<String, SchemaReference> doneSchemas, String base, String docBase, String parentResolvedLocation) {
        OASISCatalogManager catalogs = OASISCatalogManager.getCatalogManager(bus);
        Collection<List> imports = CastUtils.cast(def.getImports().values());
        for (List lst : imports) {
            List<Import> impLst = CastUtils.cast(lst);
            for (Import imp : impLst) {
                String decodedStart;
                String start2 = imp.getLocationURI();
                try {
                    decodedStart = URLDecoder.decode(start2, "utf-8");
                }
                catch (UnsupportedEncodingException e) {
                    throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("COULD_NOT_PROVIDE_WSDL", LOG, start2), (Throwable)e);
                }
                String resolvedSchemaLocation = this.resolveWithCatalogs(catalogs, start2, base);
                if (resolvedSchemaLocation == null) {
                    try {
                        new URL(start2);
                    }
                    catch (MalformedURLException e) {
                        try {
                            start2 = this.getLocationURI(start2, docBase);
                            decodedStart = URLDecoder.decode(start2, "utf-8");
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (done.put(decodedStart, imp.getDefinition()) != null) continue;
                        if (imp.getDefinition() != null && imp.getDefinition().getDocumentBaseURI() != null) {
                            done.put(imp.getDefinition().getDocumentBaseURI(), imp.getDefinition());
                        }
                        this.updateDefinition(bus, imp.getDefinition(), done, doneSchemas, base, start2, null);
                    }
                    continue;
                }
                if (done.put(decodedStart, imp.getDefinition()) != null) continue;
                done.put(resolvedSchemaLocation, imp.getDefinition());
                if (imp.getDefinition() != null && imp.getDefinition().getDocumentBaseURI() != null) {
                    done.put(imp.getDefinition().getDocumentBaseURI(), imp.getDefinition());
                }
                this.updateDefinition(bus, imp.getDefinition(), done, doneSchemas, base, start2, resolvedSchemaLocation);
            }
        }
        Types types = def.getTypes();
        if (types != null) {
            for (ExtensibilityElement el : CastUtils.cast(types.getExtensibilityElements(), ExtensibilityElement.class)) {
                if (!(el instanceof Schema)) continue;
                this.updateSchemaImports(bus, (Schema)el, docBase, doneSchemas, base, parentResolvedLocation);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateWSDLPublishedEndpointAddress(Definition def, EndpointInfo endpointInfo) {
        Definition definition = def;
        synchronized (definition) {
            if (endpointInfo.getProperty(PUBLISHED_ENDPOINT_URL) != null) {
                String epurl = String.valueOf(endpointInfo.getProperty(PUBLISHED_ENDPOINT_URL));
                this.updatePublishedEndpointUrl(epurl, def, endpointInfo.getName());
            }
        }
    }

    protected void updatePublishedEndpointUrl(String publishingUrl, Definition def, QName name) {
        Collection<Service> services = CastUtils.cast(def.getAllServices().values());
        for (Service service2 : services) {
            Collection<Port> ports = CastUtils.cast(service2.getPorts().values());
            if (ports.isEmpty()) continue;
            if (name == null) {
                this.setSoapAddressLocationOn(ports.iterator().next(), publishingUrl);
                break;
            }
            for (Port port : ports) {
                if (!name.getLocalPart().equals(port.getName())) continue;
                this.setSoapAddressLocationOn(port, publishingUrl);
            }
        }
    }

    protected void setSoapAddressLocationOn(Port port, String url2) {
        List extensions = port.getExtensibilityElements();
        for (Object extension : extensions) {
            if (extension instanceof SOAP12Address) {
                ((SOAP12Address)extension).setLocationURI(url2);
                continue;
            }
            if (!(extension instanceof SOAPAddress)) continue;
            ((SOAPAddress)extension).setLocationURI(url2);
        }
    }

    protected void updateSchemaImports(Bus bus, Schema schema, String docBase, Map<String, SchemaReference> doneSchemas, String base, String parentResolved) {
        Collection<Object> imports = CastUtils.cast(schema.getImports().values());
        for (Object lst : imports) {
            List<SchemaImport> impLst = CastUtils.cast(lst);
            for (SchemaImport imp : impLst) {
                this.processSchemaReference(imp, bus, schema, docBase, doneSchemas, base, parentResolved);
            }
        }
        List<SchemaReference> includes = CastUtils.cast(schema.getIncludes());
        for (SchemaReference included : includes) {
            this.processSchemaReference(included, bus, schema, docBase, doneSchemas, base, parentResolved);
        }
        List<SchemaReference> redefines = CastUtils.cast(schema.getRedefines());
        for (SchemaReference included : redefines) {
            this.processSchemaReference(included, bus, schema, docBase, doneSchemas, base, parentResolved);
        }
    }

    private void processSchemaReference(SchemaReference schemaReference, Bus bus, Schema schema, String docBase, Map<String, SchemaReference> doneSchemas, String base, String parentResolved) {
        OASISCatalogManager catalogs = OASISCatalogManager.getCatalogManager(bus);
        String start2 = this.findSchemaLocation(doneSchemas, schemaReference, docBase);
        String origLocation = schemaReference.getSchemaLocationURI();
        if (start2 != null) {
            String decodedOrigLocation;
            String decodedStart;
            try {
                decodedStart = URLDecoder.decode(start2, "utf-8");
                decodedOrigLocation = URLDecoder.decode(origLocation, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("COULD_NOT_PROVIDE_WSDL", LOG, start2), (Throwable)e);
            }
            if (!doneSchemas.containsKey(decodedStart)) {
                String resolvedSchemaLocation = this.resolveWithCatalogs(catalogs, start2, base);
                if (resolvedSchemaLocation == null) {
                    resolvedSchemaLocation = this.resolveWithCatalogs(catalogs, schemaReference.getSchemaLocationURI(), base);
                }
                if (resolvedSchemaLocation == null) {
                    try {
                        new URL(start2);
                    }
                    catch (MalformedURLException e) {
                        doneSchemas.put(decodedStart, schemaReference);
                        doneSchemas.put(schemaReference.getReferencedSchema().getDocumentBaseURI(), schemaReference);
                        if (!doneSchemas.containsKey(decodedOrigLocation)) {
                            doneSchemas.put(decodedOrigLocation, schemaReference);
                        }
                        try {
                            if (!new URI(origLocation).isAbsolute() && parentResolved != null) {
                                resolvedSchemaLocation = this.resolveRelativePath(parentResolved, decodedOrigLocation);
                                doneSchemas.put(resolvedSchemaLocation, schemaReference);
                            }
                        }
                        catch (URISyntaxException uRISyntaxException) {
                            // empty catch block
                        }
                        this.updateSchemaImports(bus, schemaReference.getReferencedSchema(), start2, doneSchemas, base, resolvedSchemaLocation);
                    }
                } else if (doneSchemas.put(decodedStart, schemaReference) == null) {
                    doneSchemas.put(resolvedSchemaLocation, schemaReference);
                    String p = this.getAndSaveRelativeSchemaLocationIfCatalogResolved(doneSchemas, resolvedSchemaLocation, schema, schemaReference);
                    this.updateSchemaImports(bus, schemaReference.getReferencedSchema(), p, doneSchemas, base, resolvedSchemaLocation);
                }
            }
        }
    }

    private String getAndSaveRelativeSchemaLocationIfCatalogResolved(Map<String, SchemaReference> doneSchemas, String resolvedSchemaLocation, Schema currentSchema, SchemaReference schemaReference) {
        String path = null;
        for (Map.Entry<String, SchemaReference> entry : doneSchemas.entrySet()) {
            Schema rs = entry.getValue().getReferencedSchema();
            String k = entry.getKey();
            String rsURI = rs.getDocumentBaseURI();
            if (!currentSchema.equals(rs) || rsURI.equals(k)) continue;
            try {
                String p = URIParserUtil.relativize(rsURI, resolvedSchemaLocation);
                if (p == null) continue;
                path = new URI(k).resolve(p).toString();
                break;
            }
            catch (URISyntaxException uRISyntaxException) {
            }
        }
        if (path != null) {
            doneSchemas.put(path, schemaReference);
        }
        return path;
    }

    private String findSchemaLocation(Map<String, SchemaReference> doneSchemas, SchemaReference imp, String docBase) {
        String schemaLocationURI = imp.getSchemaLocationURI();
        if (docBase != null && schemaLocationURI != null) {
            try {
                schemaLocationURI = this.getLocationURI(schemaLocationURI, docBase);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (imp.getReferencedSchema() != null) {
            for (Map.Entry<String, SchemaReference> e : doneSchemas.entrySet()) {
                if (e.getValue().getReferencedSchema().getElement() != imp.getReferencedSchema().getElement() || !schemaLocationURI.equals(e.getKey())) continue;
                doneSchemas.put(schemaLocationURI, imp);
                imp.setSchemaLocationURI(e.getKey());
                return e.getKey();
            }
        }
        return schemaLocationURI;
    }

    private String resolveRelativePath(String parentUri, String relativePath) {
        String base = UrlUtils.getStem(parentUri);
        return base + '/' + relativePath;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Document writeWSDLDocument(Message message, Map<String, Definition> mp, Map<String, SchemaReference> smp, String wsdl, String base, EndpointInfo endpointInfo) throws WSDLException {
        Document doc;
        Bus bus = message.getExchange().getBus();
        Definition def = this.lookupDefinition(bus, mp, wsdl, base);
        String epurl = base;
        Definition definition = def;
        synchronized (definition) {
            epurl = this.getPublishableEndpointUrl(def, epurl, endpointInfo);
            WSDLWriter wsdlWriter = bus.getExtension(WSDLManager.class).getWSDLFactory().newWSDLWriter();
            def.setExtensionRegistry(bus.getExtension(WSDLManager.class).getExtensionRegistry());
            doc = wsdlWriter.getDocument(def);
        }
        this.updateDoc(doc, epurl, mp, smp, message, wsdl);
        return doc;
    }

    public String getPublishableEndpointUrl(Definition def, String epurl, EndpointInfo endpointInfo) {
        if (endpointInfo.getProperty(PUBLISHED_ENDPOINT_URL) != null) {
            epurl = String.valueOf(endpointInfo.getProperty(PUBLISHED_ENDPOINT_URL));
            this.updatePublishedEndpointUrl(epurl, def, endpointInfo.getName());
        }
        return epurl;
    }

    protected Document readXSDDocument(Bus bus, String xsd, Map<String, SchemaReference> smp, String base) throws XMLStreamException {
        ResourceManagerWSDLLocator rml;
        InputSource src;
        Document doc = null;
        SchemaReference si = this.lookupSchemaReference(bus, xsd, smp, base);
        String uri = si.getReferencedSchema().getDocumentBaseURI();
        uri = this.resolveWithCatalogs(OASISCatalogManager.getCatalogManager(bus), uri, si.getReferencedSchema().getDocumentBaseURI());
        if (uri == null) {
            uri = si.getReferencedSchema().getDocumentBaseURI();
        }
        doc = (src = (rml = new ResourceManagerWSDLLocator(uri, bus)).getBaseInputSource()).getByteStream() != null || src.getCharacterStream() != null ? StaxUtils.read(src) : StaxUtils.read(new DOMSource(si.getReferencedSchema().getElement().getOwnerDocument()));
        return doc;
    }

    protected void updateWSDLKeyDefinition(Bus bus, Map<String, Definition> mp, Message message, Map<String, SchemaReference> smp, String base, EndpointInfo endpointInfo) throws WSDLException {
        if (!mp.containsKey("")) {
            ServiceWSDLBuilder builder = new ServiceWSDLBuilder(bus, endpointInfo.getService());
            builder.setUseSchemaImports(MessageUtils.getContextualBoolean(message, WSDL_CREATE_IMPORTS, false));
            builder.setBaseFileName(endpointInfo.getService().getName().getLocalPart());
            Definition def = builder.build(new HashMap<String, SchemaInfo>());
            mp.put("", def);
            this.updateDefinition(bus, def, mp, smp, base, "", "");
        }
    }

    protected Map<String, SchemaReference> getSchemaKeySchemaReference(EndpointInfo endpointInfo) {
        Map<String, SchemaReference> smp = CastUtils.cast((Map)endpointInfo.getService().getProperty(SCHEMAS_KEY));
        if (smp == null) {
            endpointInfo.getService().setProperty(SCHEMAS_KEY, new ConcurrentHashMap(8, 0.75f, 4));
            smp = CastUtils.cast((Map)endpointInfo.getService().getProperty(SCHEMAS_KEY));
        }
        return smp;
    }

    protected Map<String, Definition> getWSDLKeyDefinition(EndpointInfo endpointInfo) {
        Map<String, Definition> mp = CastUtils.cast((Map)endpointInfo.getService().getProperty(WSDLS_KEY));
        if (mp == null) {
            endpointInfo.getService().setProperty(WSDLS_KEY, new ConcurrentHashMap(8, 0.75f, 4));
            mp = CastUtils.cast((Map)endpointInfo.getService().getProperty(WSDLS_KEY));
        }
        return mp;
    }

    protected String getPublishedEndpointURL(Message message, String base, EndpointInfo endpointInfo) {
        Object prop = message.getContextualProperty(PUBLISHED_ENDPOINT_URL);
        if (prop == null) {
            prop = endpointInfo.getProperty(PUBLISHED_ENDPOINT_URL);
        }
        if (prop != null) {
            base = String.valueOf(prop);
        }
        return base;
    }

    private Definition lookupDefinition(Bus bus, Map<String, Definition> mp, String wsdl, String base) {
        String wsdl2;
        Definition def = mp.get(wsdl);
        if (def == null && (wsdl2 = this.resolveWithCatalogs(OASISCatalogManager.getCatalogManager(bus), wsdl, base)) != null) {
            def = mp.get(wsdl2);
        }
        if (def == null) {
            throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("WSDL_NOT_FOUND", LOG, wsdl), null);
        }
        return def;
    }

    private SchemaReference lookupSchemaReference(Bus bus, String xsd, Map<String, SchemaReference> smp, String base) {
        String xsd2;
        SchemaReference si = smp.get(xsd);
        if (si == null && (xsd2 = this.resolveWithCatalogs(OASISCatalogManager.getCatalogManager(bus), xsd, base)) != null) {
            si = smp.get(xsd2);
        }
        if (si == null) {
            throw new WSDLQueryException(new org.apache.cxf.common.i18n.Message("SCHEMA_NOT_FOUND", LOG, xsd), null);
        }
        return si;
    }

    private String getLocationURI(String startLoc, String docBase) throws URISyntaxException {
        if (!new URI(startLoc).isAbsolute()) {
            startLoc = StringUtils.isEmpty(docBase) ? new URI(".").resolve(startLoc).toString() : new URI(docBase).resolve(startLoc).toString();
        }
        return startLoc;
    }

    private String buildUrl(String base, String ctxUri, String s2) {
        return base + ctxUri + "?" + s2;
    }
}

