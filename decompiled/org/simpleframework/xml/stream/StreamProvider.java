/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.InputStream;
import java.io.Reader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.Provider;
import org.simpleframework.xml.stream.StreamReader;

public class StreamProvider
implements Provider {
    private final XMLInputFactory factory;

    public StreamProvider(XMLInputFactory factory2) {
        this.factory = factory2;
    }

    public StreamProvider() {
        this.factory = XMLInputFactory.newInstance();
        this.factory.setProperty("javax.xml.stream.supportDTD", false);
        this.factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        this.factory.setXMLResolver(new XMLResolver(){

            @Override
            public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
                throw new XMLStreamException("Entity resolution disabled for default stream provider constructor.");
            }
        });
    }

    @Override
    public EventReader provide(InputStream source2) throws Exception {
        return this.provide(this.factory.createXMLEventReader(source2));
    }

    @Override
    public EventReader provide(Reader source2) throws Exception {
        return this.provide(this.factory.createXMLEventReader(source2));
    }

    private EventReader provide(XMLEventReader source2) throws Exception {
        return new StreamReader(source2);
    }
}

