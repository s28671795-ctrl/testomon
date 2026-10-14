/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.transformer;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public class TransformIdentity
implements Transformer {
    private static XMLOutputFactory xmlOutputFactory;
    private static XMLInputFactory xmlInputFactory;
    private OutputStream outputStream;
    private XMLEventWriter xmlEventWriterForOutputStream;
    private Transformer transformer;
    private ChildOutputMethod childOutputMethod;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static XMLOutputFactory getXmlOutputFactory() {
        Class<TransformIdentity> clazz = TransformIdentity.class;
        synchronized (TransformIdentity.class) {
            if (xmlOutputFactory == null) {
                xmlOutputFactory = XMLOutputFactory.newInstance();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return xmlOutputFactory;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static XMLInputFactory getXmlInputFactory() {
        Class<TransformIdentity> clazz = TransformIdentity.class;
        synchronized (TransformIdentity.class) {
            if (xmlInputFactory == null) {
                xmlInputFactory = XMLInputFactory.newInstance();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return xmlInputFactory;
        }
    }

    @Override
    public void setOutputStream(OutputStream outputStream2) throws XMLSecurityException {
        this.outputStream = outputStream2;
    }

    protected OutputStream getOutputStream() {
        return this.outputStream;
    }

    protected XMLEventWriter getXmlEventWriterForOutputStream() throws XMLStreamException {
        if (this.xmlEventWriterForOutputStream != null) {
            return this.xmlEventWriterForOutputStream;
        }
        if (this.outputStream != null) {
            this.xmlEventWriterForOutputStream = TransformIdentity.getXmlOutputFactory().createXMLEventWriter(new FilterOutputStream(this.outputStream){

                @Override
                public void close() throws IOException {
                    super.flush();
                }
            });
            return this.xmlEventWriterForOutputStream;
        }
        return null;
    }

    @Override
    public void setTransformer(Transformer transformer) throws XMLSecurityException {
        this.transformer = transformer;
    }

    protected Transformer getTransformer() {
        return this.transformer;
    }

    @Override
    public void setProperties(Map<String, Object> properties) throws XMLSecurityException {
        throw new UnsupportedOperationException("no properties supported");
    }

    @Override
    public XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod forInput) {
        switch (forInput) {
            case XMLSecEvent: {
                return XMLSecurityConstants.TransformMethod.XMLSecEvent;
            }
            case InputStream: {
                return XMLSecurityConstants.TransformMethod.InputStream;
            }
        }
        throw new IllegalArgumentException("Unsupported class " + forInput.name());
    }

    @Override
    public void transform(XMLSecEvent xmlSecEvent) throws XMLStreamException {
        if (this.getXmlEventWriterForOutputStream() != null) {
            this.getXmlEventWriterForOutputStream().add(xmlSecEvent);
        } else {
            if (this.childOutputMethod == null) {
                XMLSecurityConstants.TransformMethod preferredChildTransformMethod = this.getTransformer().getPreferredTransformMethod(XMLSecurityConstants.TransformMethod.XMLSecEvent);
                switch (preferredChildTransformMethod) {
                    case XMLSecEvent: {
                        this.childOutputMethod = new ChildOutputMethod(){

                            @Override
                            public void transform(Object object) throws XMLStreamException {
                                TransformIdentity.this.getTransformer().transform((XMLSecEvent)object);
                            }

                            @Override
                            public void doFinal() throws XMLStreamException {
                                TransformIdentity.this.getTransformer().doFinal();
                            }
                        };
                        break;
                    }
                    case InputStream: {
                        this.childOutputMethod = new ChildOutputMethod(){
                            private UnsyncByteArrayOutputStream baos;
                            private XMLEventWriter xmlEventWriter;

                            @Override
                            public void transform(Object object) throws XMLStreamException {
                                if (this.xmlEventWriter == null) {
                                    this.baos = new UnsyncByteArrayOutputStream();
                                    this.xmlEventWriter = TransformIdentity.getXmlOutputFactory().createXMLEventWriter(this.baos);
                                }
                                this.xmlEventWriter.add((XMLSecEvent)object);
                            }

                            @Override
                            public void doFinal() throws XMLStreamException {
                                this.xmlEventWriter.close();
                                try (UnsyncByteArrayInputStream is = new UnsyncByteArrayInputStream(this.baos.toByteArray());){
                                    TransformIdentity.this.getTransformer().transform(is);
                                    TransformIdentity.this.getTransformer().doFinal();
                                }
                                catch (IOException ex) {
                                    throw new XMLStreamException(ex);
                                }
                            }
                        };
                    }
                }
            }
            if (this.childOutputMethod != null) {
                this.childOutputMethod.transform(xmlSecEvent);
            }
        }
    }

    @Override
    public void transform(final InputStream inputStream2) throws XMLStreamException {
        if (this.getOutputStream() != null) {
            try {
                XMLSecurityUtils.copy(inputStream2, this.getOutputStream());
            }
            catch (IOException e) {
                throw new XMLStreamException(e);
            }
        } else {
            if (this.childOutputMethod == null) {
                XMLSecurityConstants.TransformMethod preferredChildTransformMethod = this.getTransformer().getPreferredTransformMethod(XMLSecurityConstants.TransformMethod.InputStream);
                switch (preferredChildTransformMethod) {
                    case XMLSecEvent: {
                        this.childOutputMethod = new ChildOutputMethod(){
                            private XMLEventReaderInputProcessor xmlEventReaderInputProcessor;

                            @Override
                            public void transform(Object object) throws XMLStreamException {
                                if (this.xmlEventReaderInputProcessor == null) {
                                    this.xmlEventReaderInputProcessor = new XMLEventReaderInputProcessor(null, TransformIdentity.getXmlInputFactory().createXMLStreamReader(inputStream2));
                                }
                                try {
                                    XMLSecEvent xmlSecEvent;
                                    do {
                                        xmlSecEvent = this.xmlEventReaderInputProcessor.processNextEvent(null);
                                        TransformIdentity.this.getTransformer().transform(xmlSecEvent);
                                    } while (xmlSecEvent.getEventType() != 8);
                                }
                                catch (XMLSecurityException e) {
                                    throw new XMLStreamException(e);
                                }
                            }

                            @Override
                            public void doFinal() throws XMLStreamException {
                                TransformIdentity.this.getTransformer().doFinal();
                            }
                        };
                        break;
                    }
                    case InputStream: {
                        this.childOutputMethod = new ChildOutputMethod(){

                            @Override
                            public void transform(Object object) throws XMLStreamException {
                                TransformIdentity.this.getTransformer().transform(inputStream2);
                            }

                            @Override
                            public void doFinal() throws XMLStreamException {
                                TransformIdentity.this.getTransformer().doFinal();
                            }
                        };
                    }
                }
            }
            if (this.childOutputMethod != null) {
                this.childOutputMethod.transform(inputStream2);
            }
        }
    }

    @Override
    public void doFinal() throws XMLStreamException {
        if (this.xmlEventWriterForOutputStream != null) {
            this.xmlEventWriterForOutputStream.close();
        }
        if (this.childOutputMethod != null) {
            this.childOutputMethod.doFinal();
        } else if (this.transformer != null) {
            this.transformer.doFinal();
        }
    }

    static interface ChildOutputMethod {
        public void transform(Object var1) throws XMLStreamException;

        public void doFinal() throws XMLStreamException;
    }
}

