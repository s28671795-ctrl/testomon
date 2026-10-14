/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInEndingInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.FragmentStreamReader;
import org.apache.cxf.staxutils.StaxSource;
import org.apache.cxf.staxutils.StaxStreamFilter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.staxutils.validation.WoodstoxValidationImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLStreamDataReader
implements DataReader<XMLStreamReader> {
    private static final Logger LOG = LogUtils.getL7dLogger(XMLStreamDataReader.class);
    private static final QName XOP = new QName("http://www.w3.org/2004/08/xop/include", "Include");
    private final Class<?> preferred;
    private Schema schema;
    private Message message;

    public XMLStreamDataReader() {
        this.preferred = null;
    }

    public XMLStreamDataReader(Class<?> cls) {
        this.preferred = cls;
    }

    @Override
    public Object read(MessagePartInfo part, XMLStreamReader input) {
        return this.read((QName)null, input, part.getTypeClass());
    }

    @Override
    public Object read(final QName name, XMLStreamReader input, Class<?> type) {
        if (type == null) {
            type = this.preferred;
        }
        if (Source.class.equals(type) && this.message != null) {
            String s2 = (String)this.message.getContextualProperty("source-preferred-format");
            if (StringUtils.isEmpty(s2)) {
                s2 = "sax";
            }
            if ("dom".equalsIgnoreCase(s2)) {
                type = DOMSource.class;
            } else if ("stream".equalsIgnoreCase(s2)) {
                type = StreamSource.class;
            } else if ("sax".equalsIgnoreCase(s2) || "cxf.stax".equalsIgnoreCase(s2)) {
                type = SAXSource.class;
            } else if ("stax".equals(s2)) {
                try {
                    type = ClassLoaderUtils.loadClass("javax.xml.transform.stax.StAXSource", this.getClass());
                }
                catch (ClassNotFoundException e) {
                    type = SAXSource.class;
                }
            } else {
                type = DOMSource.class;
            }
        }
        try {
            Element dom = null;
            if (this.schema != null) {
                dom = this.validate(input);
                input = StaxUtils.createXMLStreamReader(dom);
            }
            if (type != null) {
                Object o;
                Object retVal = null;
                if (SAXSource.class.isAssignableFrom(type) || StaxSource.class.isAssignableFrom(type)) {
                    retVal = new StaxSource(this.resetForStreaming(input));
                } else if (StreamSource.class.isAssignableFrom(type)) {
                    retVal = new StreamSource(this.getInputStream(input));
                } else if (XMLStreamReader.class.isAssignableFrom(type)) {
                    retVal = this.resetForStreaming(input);
                } else if (Element.class.isAssignableFrom(type)) {
                    retVal = dom == null ? this.read(input).getNode() : dom;
                } else if (Document.class.isAssignableFrom(type)) {
                    retVal = dom == null ? this.read(input).getNode() : dom;
                } else if (DataSource.class.isAssignableFrom(type)) {
                    final InputStream ins = this.getInputStream(input);
                    retVal = new DataSource(){

                        @Override
                        public String getContentType() {
                            return "text/xml";
                        }

                        @Override
                        public InputStream getInputStream() throws IOException {
                            return ins;
                        }

                        @Override
                        public String getName() {
                            return name.toString();
                        }

                        @Override
                        public OutputStream getOutputStream() throws IOException {
                            return null;
                        }
                    };
                } else if ("javax.xml.transform.stax.StAXSource".equals(type.getName()) && (o = this.createStaxSource(input = this.resetForStreaming(input), type)) != null) {
                    retVal = o;
                }
                if (retVal != null) {
                    return retVal;
                }
            }
            return dom == null ? this.read(input) : new DOMSource(dom);
        }
        catch (IOException e) {
            throw new Fault("COULD_NOT_READ_XML_STREAM", LOG, (Throwable)e);
        }
        catch (XMLStreamException e) {
            throw new Fault("COULD_NOT_READ_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private Object createStaxSource(XMLStreamReader input, Class<?> type) {
        try {
            return type.getConstructor(XMLStreamReader.class).newInstance(input);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private XMLStreamReader resetForStreaming(XMLStreamReader input) throws XMLStreamException {
        if (this.message != null) {
            if (this.message.getInterceptorChain() != null) {
                this.message.getInterceptorChain().remove(StaxInEndingInterceptor.INSTANCE);
                this.message.getInterceptorChain().add(new StaxInEndingInterceptor("post-invoke"));
            }
            this.message.removeContent(XMLStreamReader.class);
            final InputStream ins = this.message.getContent(InputStream.class);
            this.message.removeContent(InputStream.class);
            return new FragmentStreamReader(input, true){
                boolean closed;

                @Override
                public boolean hasNext() throws XMLStreamException {
                    boolean b = super.hasNext();
                    if (!b && !this.closed) {
                        this.close();
                    }
                    return b;
                }

                @Override
                public void close() throws XMLStreamException {
                    this.closed = true;
                    try {
                        super.close();
                    }
                    catch (XMLStreamException xMLStreamException) {
                        // empty catch block
                    }
                    if (ins != null) {
                        try {
                            ins.close();
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                }
            };
        }
        return input;
    }

    private Element validate(XMLStreamReader input) throws XMLStreamException, IOException {
        DOMSource ds = this.read(input);
        Element rootElement = ds.getNode() instanceof Document ? ((Document)ds.getNode()).getDocumentElement() : (Element)ds.getNode();
        WoodstoxValidationImpl impl = new WoodstoxValidationImpl();
        XMLStreamWriter nullWriter = null;
        if (impl.canValidate()) {
            nullWriter = StaxUtils.createXMLStreamWriter(new NUllOutputStream());
            impl.setupValidation(nullWriter, this.message.getExchange().getEndpoint(), this.message.getExchange().getService().getServiceInfos().get(0));
        }
        if (impl.canValidate()) {
            XMLStreamReader reader = StaxUtils.createXMLStreamReader(ds);
            XMLStreamReader filteredReader = StaxUtils.createFilteredReader(reader, new StaxStreamFilter(XOP));
            StaxUtils.copy(filteredReader, nullWriter);
        } else {
            LOG.fine("NO_MSV_AVAILABLE");
            Element newElement = rootElement;
            if (DOMUtils.hasElementWithName(rootElement, "http://www.w3.org/2004/08/xop/include", "Include")) {
                newElement = (Element)rootElement.cloneNode(true);
                List<Element> elems = DOMUtils.findAllElementsByTagNameNS(newElement, "http://www.w3.org/2004/08/xop/include", "Include");
                for (Element include : elems) {
                    Node parentNode = include.getParentNode();
                    parentNode.removeChild(include);
                    String cid = DOMUtils.getAttribute((Node)include, "href");
                    parentNode.setTextContent(DatatypeConverter.printBase64Binary(cid.getBytes()));
                }
            }
            try {
                this.schema.newValidator().validate(new DOMSource(newElement));
            }
            catch (SAXException e) {
                throw new XMLStreamException(e.getMessage(), e);
            }
        }
        return rootElement;
    }

    private InputStream getInputStream(XMLStreamReader input) throws XMLStreamException, IOException {
        try (CachedOutputStream out = new CachedOutputStream();){
            StaxUtils.copy(input, (OutputStream)out);
            InputStream inputStream2 = out.getInputStream();
            return inputStream2;
        }
    }

    public DOMSource read(XMLStreamReader reader) {
        try {
            XMLStreamReader reader2 = reader;
            if (reader2 instanceof DepthXMLStreamReader) {
                reader2 = ((DepthXMLStreamReader)reader2).getReader();
            }
            if (reader2 instanceof W3CDOMStreamReader) {
                W3CDOMStreamReader domreader = (W3CDOMStreamReader)reader2;
                DOMSource o = new DOMSource(domreader.getCurrentElement());
                domreader.consumeFrame();
                return o;
            }
            Document document = StaxUtils.read(reader);
            if (reader.hasNext()) {
                reader.next();
            }
            return new DOMSource(document);
        }
        catch (XMLStreamException e) {
            throw new Fault("COULD_NOT_READ_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void setSchema(Schema s2) {
        this.schema = s2;
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
    }

    @Override
    public void setProperty(String prop, Object value) {
        if (Message.class.getName().equals(prop)) {
            this.message = (Message)value;
        }
    }

    static class NUllOutputStream
    extends OutputStream {
        NUllOutputStream() {
        }

        @Override
        public void write(byte[] b, int off, int len) {
        }

        @Override
        public void write(int b) {
        }

        @Override
        public void write(byte[] b) throws IOException {
        }
    }
}

