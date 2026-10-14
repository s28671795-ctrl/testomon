/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.DefaultDocumentLoader
 */
package org.apache.cxf.bus.spring;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.springframework.beans.factory.xml.DefaultDocumentLoader;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

class TunedDocumentLoader
extends DefaultDocumentLoader {
    private static final Logger LOG = LogUtils.getL7dLogger(TunedDocumentLoader.class);
    private static boolean hasFastInfoSet;
    private SAXParserFactory saxParserFactory;
    private SAXParserFactory nsasaxParserFactory;

    TunedDocumentLoader() {
        try {
            Class<?> cls = ClassLoaderUtils.loadClass("com.ctc.wstx.sax.WstxSAXParserFactory", TunedDocumentLoader.class);
            this.saxParserFactory = (SAXParserFactory)cls.newInstance();
            this.nsasaxParserFactory = (SAXParserFactory)cls.newInstance();
        }
        catch (Throwable e) {
            this.saxParserFactory = SAXParserFactory.newInstance();
            this.nsasaxParserFactory = SAXParserFactory.newInstance();
        }
        try {
            this.nsasaxParserFactory.setFeature("http://xml.org/sax/features/namespaces", true);
            this.nsasaxParserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            this.saxParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            this.nsasaxParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            this.saxParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            this.nsasaxParserFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static boolean hasFastInfoSet() {
        return hasFastInfoSet;
    }

    public Document loadDocument(InputSource inputSource, EntityResolver entityResolver, ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
        if (validationMode == 0) {
            SAXParserFactory parserFactory = namespaceAware ? this.nsasaxParserFactory : this.saxParserFactory;
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setEntityResolver(entityResolver);
            reader.setErrorHandler(errorHandler);
            SAXSource saxSource = new SAXSource(reader, inputSource);
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
            StaxUtils.copy((Source)saxSource, (XMLStreamWriter)writer);
            return writer.getDocument();
        }
        return super.loadDocument(inputSource, entityResolver, errorHandler, validationMode, namespaceAware);
    }

    protected DocumentBuilderFactory createDocumentBuilderFactory(int validationMode, boolean namespaceAware) throws ParserConfigurationException {
        DocumentBuilderFactory factory2 = super.createDocumentBuilderFactory(validationMode, namespaceAware);
        try {
            factory2.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return factory2;
    }

    static Document loadFastinfosetDocument(URL url2) throws IOException, ParserConfigurationException, XMLStreamException {
        InputStream is = url2.openStream();
        BufferedInputStream in = new BufferedInputStream(is);
        StAXDocumentParser staxReader = new StAXDocumentParser(in);
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        StaxUtils.copy((XMLStreamReader)staxReader, (XMLStreamWriter)writer);
        ((InputStream)in).close();
        return writer.getDocument();
    }

    static {
        try {
            ClassLoaderUtils.loadClass("com.sun.xml.fastinfoset.stax.StAXDocumentParser", TunedDocumentLoader.class);
            hasFastInfoSet = true;
        }
        catch (Throwable e) {
            LOG.fine("FastInfoset not found on classpath. Disabling context load optimizations.");
            hasFastInfoSet = false;
        }
    }
}

