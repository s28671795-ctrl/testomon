/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.resolver.tools;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.ResolvingXMLFilter;

public class ResolvingXMLReader
extends ResolvingXMLFilter {
    public static boolean namespaceAware = true;
    public static boolean validating = false;

    public ResolvingXMLReader() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(namespaceAware);
        spf.setValidating(validating);
        try {
            SAXParser parser = spf.newSAXParser();
            this.setParent(parser.getXMLReader());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResolvingXMLReader(CatalogManager manager) {
        super(manager);
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(namespaceAware);
        spf.setValidating(validating);
        try {
            SAXParser parser = spf.newSAXParser();
            this.setParent(parser.getXMLReader());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

