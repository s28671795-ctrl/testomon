/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.helpers.BootstrapResolver;
import org.apache.xml.resolver.helpers.Debug;
import org.apache.xml.resolver.readers.CatalogReader;
import org.apache.xml.resolver.readers.SAXCatalogParser;
import org.apache.xml.resolver.readers.SAXParserHandler;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXCatalogReader
implements CatalogReader,
ContentHandler,
DocumentHandler {
    protected SAXParserFactory parserFactory = null;
    protected String parserClass = null;
    protected Hashtable namespaceMap = new Hashtable();
    private SAXCatalogParser saxParser = null;
    private boolean abandonHope = false;
    private ClassLoader loader = null;
    private Catalog catalog;
    protected Debug debug;

    public void setParserFactory(SAXParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    public void setParserClass(String parserClass) {
        this.parserClass = parserClass;
    }

    public SAXParserFactory getParserFactory() {
        return this.parserFactory;
    }

    public String getParserClass() {
        return this.parserClass;
    }

    public void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public SAXCatalogReader() {
        this.debug = CatalogManager.getStaticManager().debug;
        this.parserFactory = null;
        this.parserClass = null;
    }

    public SAXCatalogReader(SAXParserFactory parserFactory) {
        this.debug = CatalogManager.getStaticManager().debug;
        this.parserFactory = parserFactory;
    }

    public SAXCatalogReader(String parserClass) {
        this.debug = CatalogManager.getStaticManager().debug;
        this.parserClass = parserClass;
    }

    public void setCatalogParser(String namespaceURI, String rootElement, String parserClass) {
        namespaceURI = namespaceURI != null ? namespaceURI.trim() : "";
        this.namespaceMap.put("{" + namespaceURI + "}" + rootElement, parserClass);
    }

    public String getCatalogParser(String namespaceURI, String rootElement) {
        namespaceURI = namespaceURI != null ? namespaceURI.trim() : "";
        return (String)this.namespaceMap.get("{" + namespaceURI + "}" + rootElement);
    }

    public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLException, IOException, CatalogException {
        URL url2 = null;
        try {
            url2 = new URL(fileUrl);
        }
        catch (MalformedURLException e) {
            url2 = new URL("file:///" + fileUrl);
        }
        this.debug = catalog.getCatalogManager().debug;
        try {
            URLConnection urlCon = url2.openConnection();
            this.readCatalog(catalog, urlCon.getInputStream());
        }
        catch (FileNotFoundException e) {
            catalog.getCatalogManager().debug.message(1, "Failed to load catalog, file not found", url2.toString());
        }
    }

    public void readCatalog(Catalog catalog, InputStream is) throws IOException, CatalogException {
        if (this.parserFactory == null && this.parserClass == null) {
            this.debug.message(1, "Cannot read SAX catalog without a parser");
            throw new CatalogException(6);
        }
        this.debug = catalog.getCatalogManager().debug;
        BootstrapResolver bResolver = catalog.getCatalogManager().getBootstrapResolver();
        this.catalog = catalog;
        try {
            if (this.parserFactory != null) {
                SAXParser parser = this.parserFactory.newSAXParser();
                SAXParserHandler spHandler = new SAXParserHandler();
                spHandler.setContentHandler(this);
                if (bResolver != null) {
                    spHandler.setEntityResolver(bResolver);
                }
                parser.parse(new InputSource(is), (DefaultHandler)spHandler);
            } else {
                Parser parser = (Parser)Class.forName(this.parserClass, true, this.loader != null ? this.loader : this.getClass().getClassLoader()).newInstance();
                parser.setDocumentHandler(this);
                if (bResolver != null) {
                    parser.setEntityResolver(bResolver);
                }
                parser.parse(new InputSource(is));
            }
        }
        catch (ClassNotFoundException cnfe) {
            throw new CatalogException(6);
        }
        catch (IllegalAccessException iae) {
            throw new CatalogException(6);
        }
        catch (InstantiationException ie) {
            throw new CatalogException(6);
        }
        catch (ParserConfigurationException pce) {
            throw new CatalogException(5);
        }
        catch (SAXException se) {
            Exception e = se.getException();
            UnknownHostException uhe = new UnknownHostException();
            FileNotFoundException fnfe = new FileNotFoundException();
            if (e != null) {
                if (e.getClass() == uhe.getClass()) {
                    throw new CatalogException(7, e.toString());
                }
                if (e.getClass() == fnfe.getClass()) {
                    throw new CatalogException(7, e.toString());
                }
            }
            throw new CatalogException(se);
        }
    }

    public void setDocumentLocator(Locator locator) {
        if (this.saxParser != null) {
            this.saxParser.setDocumentLocator(locator);
        }
    }

    public void startDocument() throws SAXException {
        this.saxParser = null;
        this.abandonHope = false;
    }

    public void endDocument() throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.endDocument();
        }
    }

    public void startElement(String name, AttributeList atts) throws SAXException {
        if (this.abandonHope) {
            return;
        }
        if (this.saxParser == null) {
            String localName;
            String prefix = "";
            if (name.indexOf(58) > 0) {
                prefix = name.substring(0, name.indexOf(58));
            }
            if ((localName = name).indexOf(58) > 0) {
                localName = localName.substring(localName.indexOf(58) + 1);
            }
            String namespaceURI = null;
            namespaceURI = prefix.equals("") ? atts.getValue("xmlns") : atts.getValue("xmlns:" + prefix);
            String saxParserClass = this.getCatalogParser(namespaceURI, localName);
            if (saxParserClass == null) {
                this.abandonHope = true;
                if (namespaceURI == null) {
                    this.debug.message(2, "No Catalog parser for " + name);
                } else {
                    this.debug.message(2, "No Catalog parser for {" + namespaceURI + "}" + name);
                }
                return;
            }
            try {
                this.saxParser = (SAXCatalogParser)Class.forName(saxParserClass, true, this.loader != null ? this.loader : this.getClass().getClassLoader()).newInstance();
                this.saxParser.setCatalog(this.catalog);
                this.saxParser.startDocument();
                this.saxParser.startElement(name, atts);
            }
            catch (ClassNotFoundException cnfe) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, cnfe.toString());
            }
            catch (InstantiationException ie) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, ie.toString());
            }
            catch (IllegalAccessException iae) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, iae.toString());
            }
            catch (ClassCastException cce) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, cce.toString());
            }
        } else {
            this.saxParser.startElement(name, atts);
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (this.abandonHope) {
            return;
        }
        if (this.saxParser == null) {
            String saxParserClass = this.getCatalogParser(namespaceURI, localName);
            if (saxParserClass == null) {
                this.abandonHope = true;
                if (namespaceURI == null) {
                    this.debug.message(2, "No Catalog parser for " + localName);
                } else {
                    this.debug.message(2, "No Catalog parser for {" + namespaceURI + "}" + localName);
                }
                return;
            }
            try {
                this.saxParser = (SAXCatalogParser)Class.forName(saxParserClass, true, this.loader != null ? this.loader : this.getClass().getClassLoader()).newInstance();
                this.saxParser.setCatalog(this.catalog);
                this.saxParser.startDocument();
                this.saxParser.startElement(namespaceURI, localName, qName, atts);
            }
            catch (ClassNotFoundException cnfe) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, cnfe.toString());
            }
            catch (InstantiationException ie) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, ie.toString());
            }
            catch (IllegalAccessException iae) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, iae.toString());
            }
            catch (ClassCastException cce) {
                this.saxParser = null;
                this.abandonHope = true;
                this.debug.message(2, cce.toString());
            }
        } else {
            this.saxParser.startElement(namespaceURI, localName, qName, atts);
        }
    }

    public void endElement(String name) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.endElement(name);
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.endElement(namespaceURI, localName, qName);
        }
    }

    public void characters(char[] ch, int start2, int length) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.characters(ch, start2, length);
        }
    }

    public void ignorableWhitespace(char[] ch, int start2, int length) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.ignorableWhitespace(ch, start2, length);
        }
    }

    public void processingInstruction(String target, String data) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.processingInstruction(target, data);
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.startPrefixMapping(prefix, uri);
        }
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.endPrefixMapping(prefix);
        }
    }

    public void skippedEntity(String name) throws SAXException {
        if (this.saxParser != null) {
            this.saxParser.skippedEntity(name);
        }
    }
}

