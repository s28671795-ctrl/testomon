/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.helpers.Namespaces;
import org.apache.xml.resolver.readers.CatalogReader;
import org.apache.xml.resolver.readers.DOMCatalogParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DOMCatalogReader
implements CatalogReader {
    protected Hashtable namespaceMap = new Hashtable();

    public void setCatalogParser(String namespaceURI, String rootElement, String parserClass) {
        if (namespaceURI == null) {
            this.namespaceMap.put(rootElement, parserClass);
        } else {
            this.namespaceMap.put("{" + namespaceURI + "}" + rootElement, parserClass);
        }
    }

    public String getCatalogParser(String namespaceURI, String rootElement) {
        if (namespaceURI == null) {
            return (String)this.namespaceMap.get(rootElement);
        }
        return (String)this.namespaceMap.get("{" + namespaceURI + "}" + rootElement);
    }

    public void readCatalog(Catalog catalog, InputStream is) throws IOException, CatalogException {
        DocumentBuilderFactory factory2 = null;
        DocumentBuilder builder = null;
        factory2 = DocumentBuilderFactory.newInstance();
        factory2.setNamespaceAware(false);
        factory2.setValidating(false);
        try {
            builder = factory2.newDocumentBuilder();
        }
        catch (ParserConfigurationException pce) {
            throw new CatalogException(6);
        }
        Document doc = null;
        try {
            doc = builder.parse(is);
        }
        catch (SAXException se) {
            throw new CatalogException(5);
        }
        Element root = doc.getDocumentElement();
        String namespaceURI = Namespaces.getNamespaceURI(root);
        String localName = Namespaces.getLocalName(root);
        String domParserClass = this.getCatalogParser(namespaceURI, localName);
        if (domParserClass == null) {
            if (namespaceURI == null) {
                catalog.getCatalogManager().debug.message(1, "No Catalog parser for " + localName);
            } else {
                catalog.getCatalogManager().debug.message(1, "No Catalog parser for {" + namespaceURI + "}" + localName);
            }
            return;
        }
        DOMCatalogParser domParser = null;
        try {
            domParser = (DOMCatalogParser)Class.forName(domParserClass).newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            catalog.getCatalogManager().debug.message(1, "Cannot load XML Catalog Parser class", domParserClass);
            throw new CatalogException(6);
        }
        catch (InstantiationException ie) {
            catalog.getCatalogManager().debug.message(1, "Cannot instantiate XML Catalog Parser class", domParserClass);
            throw new CatalogException(6);
        }
        catch (IllegalAccessException iae) {
            catalog.getCatalogManager().debug.message(1, "Cannot access XML Catalog Parser class", domParserClass);
            throw new CatalogException(6);
        }
        catch (ClassCastException cce) {
            catalog.getCatalogManager().debug.message(1, "Cannot cast XML Catalog Parser class", domParserClass);
            throw new CatalogException(6);
        }
        Node node = root.getFirstChild();
        while (node != null) {
            domParser.parseCatalogEntry(catalog, node);
            node = node.getNextSibling();
        }
    }

    public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLException, IOException, CatalogException {
        URL url2 = new URL(fileUrl);
        URLConnection urlCon = url2.openConnection();
        this.readCatalog(catalog, urlCon.getInputStream());
    }
}

