/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.InputStream;
import java.io.Reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.simpleframework.xml.stream.DocumentReader;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.Provider;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentProvider
implements Provider {
    private final DocumentBuilderFactory factory;

    public DocumentProvider() {
        this(DocumentProvider.safeDocumentBuilderFactory());
    }

    public DocumentProvider(DocumentBuilderFactory factory2) {
        this.factory = factory2;
    }

    @Override
    public EventReader provide(InputStream source2) throws Exception {
        return this.provide(new InputSource(source2));
    }

    @Override
    public EventReader provide(Reader source2) throws Exception {
        return this.provide(new InputSource(source2));
    }

    private EventReader provide(InputSource source2) throws Exception {
        DocumentBuilder builder = this.factory.newDocumentBuilder();
        Document document = builder.parse(source2);
        return new DocumentReader(document);
    }

    private static void setFeature(DocumentBuilderFactory factory2, String feature, boolean value) {
        try {
            factory2.setFeature(feature, value);
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException("The required security feature is not supported by your XML parser: " + feature, e);
        }
    }

    public static DocumentBuilderFactory safeDocumentBuilderFactory() {
        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
        DocumentProvider.setFeature(factory2, "http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentProvider.setFeature(factory2, "http://xml.org/sax/features/external-general-entities", false);
        DocumentProvider.setFeature(factory2, "http://xml.org/sax/features/external-parameter-entities", false);
        DocumentProvider.setFeature(factory2, "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory2.setXIncludeAware(false);
        factory2.setExpandEntityReferences(false);
        factory2.setNamespaceAware(true);
        return factory2;
    }
}

