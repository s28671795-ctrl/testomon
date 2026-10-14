/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature.transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.feature.transform.AbstractXSLTInterceptor;
import org.apache.cxf.feature.transform.XSLTUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;

public class XSLTInInterceptor
extends AbstractXSLTInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(XSLTInInterceptor.class);

    public XSLTInInterceptor(String xsltPath) {
        super("post-stream", StaxInInterceptor.class, null, xsltPath);
    }

    public XSLTInInterceptor(String phase, Class<?> before, Class<?> after, String xsltPath) {
        super(phase, before, after, xsltPath);
    }

    @Override
    public void handleMessage(Message message) {
        if (!this.isRequestor(message) && this.isGET(message) || this.checkContextProperty(message)) {
            return;
        }
        XMLStreamReader xReader = message.getContent(XMLStreamReader.class);
        if (xReader != null) {
            this.transformXReader(message, xReader);
        } else {
            InputStream is = message.getContent(InputStream.class);
            if (is != null) {
                this.transformIS(message, is);
            } else {
                Reader reader = message.getContent(Reader.class);
                if (reader != null) {
                    this.transformReader(message, reader);
                }
            }
        }
    }

    protected void transformXReader(Message message, XMLStreamReader xReader) {
        CachedOutputStream cachedOS = new CachedOutputStream();
        try {
            StaxUtils.copy(xReader, (OutputStream)cachedOS);
            InputStream transformedIS = XSLTUtils.transform(this.getXSLTTemplate(), cachedOS.getInputStream());
            XMLStreamReader transformedReader = StaxUtils.createXMLStreamReader(transformedIS);
            message.setContent(XMLStreamReader.class, transformedReader);
        }
        catch (XMLStreamException e) {
            throw new Fault("STAX_COPY", LOG, (Throwable)e, e.getMessage());
        }
        catch (IOException e) {
            throw new Fault("GET_CACHED_INPUT_STREAM", LOG, (Throwable)e, e.getMessage());
        }
        finally {
            try {
                StaxUtils.close(xReader);
            }
            catch (XMLStreamException ex) {
                throw new Fault(ex);
            }
            try {
                cachedOS.close();
            }
            catch (IOException e) {
                LOG.warning("Cannot close stream after transformation: " + e.getMessage());
            }
        }
    }

    protected void transformIS(Message message, InputStream is) {
        try (InputStream inputStream2 = is;){
            message.setContent(InputStream.class, XSLTUtils.transform(this.getXSLTTemplate(), inputStream2));
        }
        catch (IOException e) {
            LOG.warning("Cannot close stream after transformation: " + e.getMessage());
        }
    }

    protected void transformReader(Message message, Reader reader) {
        try (Reader r = reader;){
            message.setContent(Reader.class, XSLTUtils.transform(this.getXSLTTemplate(), r));
        }
        catch (IOException e) {
            LOG.warning("Cannot close stream after transformation: " + e.getMessage());
        }
    }
}

