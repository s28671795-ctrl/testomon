/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.feature.transform.AbstractXSLTInterceptor;
import org.apache.cxf.feature.transform.XSLTUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.DelegatingXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;

public class XSLTOutInterceptor
extends AbstractXSLTInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(XSLTOutInterceptor.class);

    public XSLTOutInterceptor(String xsltPath) {
        super("pre-stream", StaxOutInterceptor.class, null, xsltPath);
    }

    public XSLTOutInterceptor(String phase, Class<?> before, Class<?> after, String xsltPath) {
        super(phase, before, after, xsltPath);
    }

    @Override
    public void handleMessage(Message message) {
        if (this.checkContextProperty(message)) {
            return;
        }
        XMLStreamWriter xWriter = message.getContent(XMLStreamWriter.class);
        if (xWriter != null) {
            this.transformXWriter(message, xWriter);
        } else {
            OutputStream out = message.getContent(OutputStream.class);
            if (out != null) {
                this.transformOS(message, out);
            } else {
                Writer writer = message.getContent(Writer.class);
                if (writer != null) {
                    this.transformWriter(message, writer);
                }
            }
        }
    }

    protected void transformXWriter(Message message, XMLStreamWriter xWriter) {
        CachedWriter writer = new CachedWriter();
        XMLStreamWriter delegate = StaxUtils.createXMLStreamWriter(writer);
        XSLTStreamWriter wrapper = new XSLTStreamWriter(this.getXSLTTemplate(), writer, delegate, xWriter);
        message.setContent(XMLStreamWriter.class, wrapper);
        message.put("disable.outputstream.optimization", Boolean.TRUE);
    }

    protected void transformOS(Message message, OutputStream out) {
        CachedOutputStream wrapper = new CachedOutputStream();
        XSLTCachedOutputStreamCallback callback = new XSLTCachedOutputStreamCallback(this.getXSLTTemplate(), out);
        wrapper.registerCallback(callback);
        message.setContent(OutputStream.class, wrapper);
    }

    protected void transformWriter(Message message, Writer writer) {
        XSLTCachedWriter wrapper = new XSLTCachedWriter(this.getXSLTTemplate(), writer);
        message.setContent(Writer.class, wrapper);
    }

    public static class XSLTCachedWriter
    extends CachedWriter {
        private final Templates xsltTemplate;
        private final Writer origWriter;

        public XSLTCachedWriter(Templates xsltTemplate, Writer origWriter) {
            this.xsltTemplate = xsltTemplate;
            this.origWriter = origWriter;
        }

        @Override
        protected void doClose() {
            Reader transformedReader = null;
            try {
                transformedReader = XSLTUtils.transform(this.xsltTemplate, this.getReader());
                IOUtils.copyAndCloseInput(transformedReader, this.origWriter, 4096);
            }
            catch (IOException e) {
                throw new Fault("READER_COPY", LOG, (Throwable)e, e.getMessage());
            }
            finally {
                try {
                    this.origWriter.close();
                }
                catch (IOException e) {
                    LOG.warning("Cannot close stream after transformation: " + e.getMessage());
                }
            }
        }
    }

    public static class XSLTCachedOutputStreamCallback
    implements CachedOutputStreamCallback {
        private final Templates xsltTemplate;
        private final OutputStream origStream;

        public XSLTCachedOutputStreamCallback(Templates xsltTemplate, OutputStream origStream) {
            this.xsltTemplate = xsltTemplate;
            this.origStream = origStream;
        }

        @Override
        public void onFlush(CachedOutputStream wrapper) {
        }

        @Override
        public void onClose(CachedOutputStream wrapper) {
            Exception exceptionOnClose = null;
            try {
                InputStream transformedStream = XSLTUtils.transform(this.xsltTemplate, wrapper.getInputStream());
                IOUtils.copyAndCloseInput(transformedStream, this.origStream);
            }
            catch (IOException e) {
                throw new Fault("STREAM_COPY", LOG, (Throwable)e, e.getMessage());
            }
            finally {
                try {
                    this.origStream.close();
                }
                catch (Exception e) {
                    exceptionOnClose = e;
                }
            }
            if (exceptionOnClose == null) {
                return;
            }
            throw new Fault(exceptionOnClose);
        }
    }

    public static class XSLTStreamWriter
    extends DelegatingXMLStreamWriter {
        private final Templates xsltTemplate;
        private final CachedWriter cachedWriter;
        private final XMLStreamWriter origXWriter;

        public XSLTStreamWriter(Templates xsltTemplate, CachedWriter cachedWriter, XMLStreamWriter delegateXWriter, XMLStreamWriter origXWriter) {
            super(delegateXWriter);
            this.xsltTemplate = xsltTemplate;
            this.cachedWriter = cachedWriter;
            this.origXWriter = origXWriter;
        }

        @Override
        public void close() {
            Reader transformedReader = null;
            try {
                super.flush();
                transformedReader = XSLTUtils.transform(this.xsltTemplate, this.cachedWriter.getReader());
                StaxUtils.copy((Source)new StreamSource(transformedReader), this.origXWriter);
            }
            catch (XMLStreamException e) {
                throw new Fault("STAX_COPY", LOG, (Throwable)e, e.getMessage());
            }
            catch (IOException e) {
                throw new Fault("GET_CACHED_INPUT_STREAM", LOG, (Throwable)e, e.getMessage());
            }
            finally {
                try {
                    if (transformedReader != null) {
                        transformedReader.close();
                    }
                    this.cachedWriter.close();
                    StaxUtils.close(this.origXWriter);
                    super.close();
                }
                catch (Exception e) {
                    LOG.warning("Cannot close stream after transformation: " + e.getMessage());
                }
            }
        }
    }
}

