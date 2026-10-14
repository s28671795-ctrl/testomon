/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInEndingInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;

public class StaxInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxInInterceptor.class);
    private static Map<Object, XMLInputFactory> factories = new HashMap<Object, XMLInputFactory>();

    public StaxInInterceptor() {
        super("post-stream");
    }

    public StaxInInterceptor(String phase) {
        super(phase);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handleMessage(Message message) {
        XMLStreamReader xreader;
        Map<String, List<String>> m4;
        if (this.isGET(message) || message.getContent(XMLStreamReader.class) != null) {
            LOG.fine("StaxInInterceptor skipped.");
            return;
        }
        InputStream is = message.getContent(InputStream.class);
        Reader reader = null;
        if (is == null && (reader = message.getContent(Reader.class)) == null) {
            return;
        }
        String contentType = (String)message.get("Content-Type");
        if (contentType != null && contentType.contains("text/html") && MessageUtils.isRequestor(message)) {
            StringBuilder htmlMessage = new StringBuilder(1024);
            try {
                if (reader == null) {
                    reader = new InputStreamReader(is, (String)message.get(Message.ENCODING));
                }
                char[] s2 = new char[1024];
                int i = reader.read(s2);
                while (htmlMessage.length() < 64536 && i > 0) {
                    htmlMessage.append(s2, 0, i);
                    i = reader.read(s2);
                }
            }
            catch (IOException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("INVALID_HTML_RESPONSETYPE", LOG, "(none)"));
            }
            throw new Fault(new org.apache.cxf.common.i18n.Message("INVALID_HTML_RESPONSETYPE", LOG, htmlMessage.length() == 0 ? "(none)" : htmlMessage));
        }
        if (contentType == null && (m4 = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS))) != null) {
            List<String> contentLen = HttpHeaderHelper.getHeader(m4, "Content-Length");
            List<String> contentTE = HttpHeaderHelper.getHeader(m4, "Content-Transfer-Encoding");
            List<String> transferEncoding = HttpHeaderHelper.getHeader(m4, "Transfer-Encoding");
            if ((StringUtils.isEmpty(contentLen) || "0".equals(contentLen.get(0))) && StringUtils.isEmpty(contentTE) && (StringUtils.isEmpty(transferEncoding) || !"chunked".equalsIgnoreCase(transferEncoding.get(0)))) {
                return;
            }
        }
        String encoding = (String)message.get(Message.ENCODING);
        try {
            XMLInputFactory factory2 = StaxInInterceptor.getXMLInputFactory(message);
            if (factory2 == null) {
                xreader = reader != null ? StaxUtils.createXMLStreamReader(reader) : StaxUtils.createXMLStreamReader(is, encoding);
            } else if (PropertyUtils.isTrue(message.getContextualProperty(Message.THREAD_SAFE_STAX_FACTORIES))) {
                xreader = reader != null ? factory2.createXMLStreamReader(reader) : factory2.createXMLStreamReader(is, encoding);
            } else {
                XMLInputFactory xMLInputFactory = factory2;
                synchronized (xMLInputFactory) {
                    xreader = reader != null ? factory2.createXMLStreamReader(reader) : factory2.createXMLStreamReader(is, encoding);
                }
            }
            xreader = StaxUtils.configureReader(xreader, message);
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STREAM_CREATE_EXC", LOG, encoding), (Throwable)e);
        }
        message.setContent(XMLStreamReader.class, xreader);
        message.getInterceptorChain().add(StaxInEndingInterceptor.INSTANCE);
    }

    public static XMLInputFactory getXMLInputFactory(Message m4) {
        Object o = m4.getContextualProperty(XMLInputFactory.class.getName());
        if (o instanceof XMLInputFactory) {
            return (XMLInputFactory)o;
        }
        if (o != null) {
            XMLInputFactory xif = factories.get(o);
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
                    throw new Fault(new org.apache.cxf.common.i18n.Message("INVALID_INPUT_FACTORY", LOG, o));
                }
                try {
                    xif = (XMLInputFactory)cls.newInstance();
                    factories.put(o, xif);
                }
                catch (IllegalAccessException | InstantiationException e) {
                    throw new Fault(e);
                }
            }
            return xif;
        }
        return null;
    }
}

