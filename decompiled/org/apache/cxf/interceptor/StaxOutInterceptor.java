/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.interceptor.StaxOutEndingInterceptor;
import org.apache.cxf.io.AbstractWrappedOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;

public class StaxOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String OUTPUT_STREAM_HOLDER = StaxOutInterceptor.class.getName() + ".outputstream";
    public static final String WRITER_HOLDER = StaxOutInterceptor.class.getName() + ".writer";
    public static final String FORCE_START_DOCUMENT = "org.apache.cxf.stax.force-start-document";
    public static final StaxOutEndingInterceptor ENDING = new StaxOutEndingInterceptor(OUTPUT_STREAM_HOLDER, WRITER_HOLDER);
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(StaxOutInterceptor.class);
    private static Map<Object, XMLOutputFactory> factories = new HashMap<Object, XMLOutputFactory>();

    public StaxOutInterceptor() {
        super("pre-stream");
        this.addAfter(AttachmentOutInterceptor.class.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(Message message) {
        OutputStream os = message.getContent(OutputStream.class);
        XMLStreamWriter xwriter = message.getContent(XMLStreamWriter.class);
        Writer writer = null;
        if (os == null) {
            writer = message.getContent(Writer.class);
        }
        if (os == null && writer == null || xwriter != null) {
            return;
        }
        String encoding = this.getEncoding(message);
        try {
            XMLOutputFactory factory2 = StaxOutInterceptor.getXMLOutputFactory(message);
            if (factory2 == null) {
                if (writer == null) {
                    os = this.setupOutputStream(os);
                    xwriter = StaxUtils.createXMLStreamWriter(os, encoding);
                } else {
                    xwriter = StaxUtils.createXMLStreamWriter(writer);
                }
            } else if (PropertyUtils.isTrue(message.getContextualProperty(Message.THREAD_SAFE_STAX_FACTORIES))) {
                if (writer == null) {
                    os = this.setupOutputStream(os);
                    xwriter = factory2.createXMLStreamWriter(os, encoding);
                } else {
                    xwriter = factory2.createXMLStreamWriter(writer);
                }
            } else {
                XMLOutputFactory xMLOutputFactory = factory2;
                synchronized (xMLOutputFactory) {
                    if (writer == null) {
                        os = this.setupOutputStream(os);
                        xwriter = factory2.createXMLStreamWriter(os, encoding);
                    } else {
                        xwriter = factory2.createXMLStreamWriter(writer);
                    }
                }
            }
            if (MessageUtils.getContextualBoolean(message, FORCE_START_DOCUMENT, false)) {
                xwriter.writeStartDocument(encoding, "1.0");
                message.removeContent(OutputStream.class);
                message.put(OUTPUT_STREAM_HOLDER, os);
                message.removeContent(Writer.class);
                message.put(WRITER_HOLDER, writer);
            }
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STREAM_CREATE_EXC", BUNDLE, new Object[0]), (Throwable)e);
        }
        message.setContent(XMLStreamWriter.class, xwriter);
        message.getInterceptorChain().add(ENDING);
    }

    private OutputStream setupOutputStream(OutputStream os) {
        if (!(os instanceof AbstractWrappedOutputStream)) {
            os = new AbstractWrappedOutputStream(os){};
        }
        ((AbstractWrappedOutputStream)os).allowFlush(false);
        return os;
    }

    @Override
    public void handleFault(Message message) {
        Writer writer;
        super.handleFault(message);
        OutputStream os = (OutputStream)message.get(OUTPUT_STREAM_HOLDER);
        if (os != null) {
            message.setContent(OutputStream.class, os);
        }
        if ((writer = (Writer)message.get(WRITER_HOLDER)) != null) {
            message.setContent(Writer.class, writer);
        }
    }

    private String getEncoding(Message message) {
        Exchange ex = message.getExchange();
        String encoding = (String)message.get(Message.ENCODING);
        if (encoding == null && ex.getInMessage() != null) {
            encoding = (String)ex.getInMessage().get(Message.ENCODING);
            message.put(Message.ENCODING, encoding);
        }
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.name();
            message.put(Message.ENCODING, encoding);
        }
        return encoding;
    }

    public static XMLOutputFactory getXMLOutputFactory(Message m4) {
        Object o = m4.getContextualProperty(XMLOutputFactory.class.getName());
        if (o instanceof XMLOutputFactory) {
            m4.put("disable.outputstream.optimization", Boolean.TRUE);
            m4.put(FORCE_START_DOCUMENT, Boolean.TRUE);
            return (XMLOutputFactory)o;
        }
        if (o != null) {
            XMLOutputFactory xif = factories.get(o);
            if (xif == null) {
                Class<?> cls;
                if (o instanceof Class) {
                    cls = (Class<?>)o;
                } else if (o instanceof String) {
                    try {
                        cls = ClassLoaderUtils.loadClass((String)o, StaxInInterceptor.class);
                    }
                    catch (ClassNotFoundException e) {
                        throw new Fault(e);
                    }
                } else {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("INVALID_INPUT_FACTORY", BUNDLE, o));
                }
                try {
                    xif = (XMLOutputFactory)cls.newInstance();
                    factories.put(o, xif);
                }
                catch (IllegalAccessException | InstantiationException e) {
                    throw new Fault(e);
                }
            }
            m4.put("disable.outputstream.optimization", Boolean.TRUE);
            m4.put(FORCE_START_DOCUMENT, Boolean.TRUE);
            return xif;
        }
        return null;
    }
}

