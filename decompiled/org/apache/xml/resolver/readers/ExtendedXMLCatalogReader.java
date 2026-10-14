/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.readers;

import java.util.Vector;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogEntry;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.Resolver;
import org.apache.xml.resolver.readers.OASISXMLCatalogReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExtendedXMLCatalogReader
extends OASISXMLCatalogReader {
    public static final String extendedNamespaceName = "http://nwalsh.com/xcatalog/1.0";

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        block17: {
            boolean inExtension = this.inExtensionNamespace();
            super.startElement(namespaceURI, localName, qName, atts);
            int entryType = -1;
            Vector<String> entryArgs = new Vector<String>();
            if (namespaceURI != null && extendedNamespaceName.equals(namespaceURI) && !inExtension) {
                if (atts.getValue("xml:base") != null) {
                    block16: {
                        String baseURI = atts.getValue("xml:base");
                        entryType = Catalog.BASE;
                        entryArgs.add(baseURI);
                        this.baseURIStack.push(baseURI);
                        this.debug.message(4, "xml:base", baseURI);
                        try {
                            CatalogEntry ce = new CatalogEntry(entryType, entryArgs);
                            this.catalog.addEntry(ce);
                        }
                        catch (CatalogException cex) {
                            if (cex.getExceptionType() == 3) {
                                this.debug.message(1, "Invalid catalog entry type", localName);
                            }
                            if (cex.getExceptionType() != 2) break block16;
                            this.debug.message(1, "Invalid catalog entry (base)", localName);
                        }
                    }
                    entryType = -1;
                    entryArgs = new Vector();
                } else {
                    this.baseURIStack.push(this.baseURIStack.peek());
                }
                if (localName.equals("uriSuffix")) {
                    if (this.checkAttributes(atts, "suffix", "uri")) {
                        entryType = Resolver.URISUFFIX;
                        entryArgs.add(atts.getValue("suffix"));
                        entryArgs.add(atts.getValue("uri"));
                        this.debug.message(4, "uriSuffix", atts.getValue("suffix"), atts.getValue("uri"));
                    }
                } else if (localName.equals("systemSuffix")) {
                    if (this.checkAttributes(atts, "suffix", "uri")) {
                        entryType = Resolver.SYSTEMSUFFIX;
                        entryArgs.add(atts.getValue("suffix"));
                        entryArgs.add(atts.getValue("uri"));
                        this.debug.message(4, "systemSuffix", atts.getValue("suffix"), atts.getValue("uri"));
                    }
                } else {
                    this.debug.message(1, "Invalid catalog entry type", localName);
                }
                if (entryType >= 0) {
                    try {
                        CatalogEntry ce = new CatalogEntry(entryType, entryArgs);
                        this.catalog.addEntry(ce);
                    }
                    catch (CatalogException cex) {
                        if (cex.getExceptionType() == 3) {
                            this.debug.message(1, "Invalid catalog entry type", localName);
                        }
                        if (cex.getExceptionType() != 2) break block17;
                        this.debug.message(1, "Invalid catalog entry", localName);
                    }
                }
            }
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        block5: {
            super.endElement(namespaceURI, localName, qName);
            boolean inExtension = this.inExtensionNamespace();
            int entryType = -1;
            Vector<String> entryArgs = new Vector<String>();
            if (namespaceURI != null && extendedNamespaceName.equals(namespaceURI) && !inExtension) {
                String popURI = (String)this.baseURIStack.pop();
                String baseURI = (String)this.baseURIStack.peek();
                if (!baseURI.equals(popURI)) {
                    entryType = Catalog.BASE;
                    entryArgs.add(baseURI);
                    this.debug.message(4, "(reset) xml:base", baseURI);
                    try {
                        CatalogEntry ce = new CatalogEntry(entryType, entryArgs);
                        this.catalog.addEntry(ce);
                    }
                    catch (CatalogException cex) {
                        if (cex.getExceptionType() == 3) {
                            this.debug.message(1, "Invalid catalog entry type", localName);
                        }
                        if (cex.getExceptionType() != 2) break block5;
                        this.debug.message(1, "Invalid catalog entry (rbase)", localName);
                    }
                }
            }
        }
    }
}

