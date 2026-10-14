/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;

@Deprecated
public abstract class AbstractLoggingInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final int DEFAULT_LIMIT = 49152;
    protected static final String BINARY_CONTENT_MESSAGE = "--- Binary Content ---";
    protected static final String MULTIPART_CONTENT_MESSAGE = "--- Multipart Content ---";
    private static final String MULTIPART_CONTENT_MEDIA_TYPE = "multipart";
    private static final String LIVE_LOGGING_PROP = "org.apache.cxf.logging.enable";
    private static final List<String> BINARY_CONTENT_MEDIA_TYPES = Arrays.asList("application/octet-stream", "application/pdf", "image/png", "image/jpeg", "image/gif");
    protected int limit = 49152;
    protected long threshold = -1L;
    protected PrintWriter writer;
    protected boolean prettyLogging;
    private boolean showBinaryContent;
    private boolean showMultipartContent = true;
    private List<String> binaryContentMediaTypes = BINARY_CONTENT_MEDIA_TYPES;

    public AbstractLoggingInterceptor(String phase) {
        super(phase);
    }

    public AbstractLoggingInterceptor(String id, String phase) {
        super(id, phase);
    }

    protected static boolean isLoggingDisabledNow(Message message) {
        Object liveLoggingProp = message.getContextualProperty(LIVE_LOGGING_PROP);
        return liveLoggingProp != null && PropertyUtils.isFalse(liveLoggingProp);
    }

    protected abstract Logger getLogger();

    Logger getMessageLogger(Message message) {
        if (AbstractLoggingInterceptor.isLoggingDisabledNow(message)) {
            return null;
        }
        Endpoint ep = message.getExchange().getEndpoint();
        if (ep == null || ep.getEndpointInfo() == null) {
            return this.getLogger();
        }
        EndpointInfo endpoint = ep.getEndpointInfo();
        if (endpoint.getService() == null) {
            return this.getLogger();
        }
        Logger logger2 = endpoint.getProperty("MessageLogger", Logger.class);
        if (logger2 == null) {
            String serviceName = endpoint.getService().getName().getLocalPart();
            InterfaceInfo iface = endpoint.getService().getInterface();
            String portName = endpoint.getName().getLocalPart();
            String portTypeName = iface.getName().getLocalPart();
            String logName = "org.apache.cxf.services." + serviceName + "." + portName + "." + portTypeName;
            logger2 = LogUtils.getL7dLogger(this.getClass(), null, logName);
            endpoint.setProperty("MessageLogger", logger2);
        }
        return logger2;
    }

    public void setOutputLocation(String s2) {
        if (s2 == null || "<logger>".equals(s2)) {
            this.writer = null;
        } else if ("<stdout>".equals(s2)) {
            this.writer = new PrintWriter(System.out, true);
        } else if ("<stderr>".equals(s2)) {
            this.writer = new PrintWriter(System.err, true);
        } else {
            try {
                URI uri = new URI(s2);
                File file = new File(uri);
                this.writer = new PrintWriter((Writer)new FileWriter(file, true), true);
            }
            catch (Exception ex) {
                this.getLogger().log(Level.WARNING, "Error configuring log location " + s2, ex);
            }
        }
    }

    public void setPrintWriter(PrintWriter w) {
        this.writer = w;
    }

    public PrintWriter getPrintWriter() {
        return this.writer;
    }

    public void setLimit(int lim) {
        this.limit = lim;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setPrettyLogging(boolean flag) {
        this.prettyLogging = flag;
    }

    public boolean isPrettyLogging() {
        return this.prettyLogging;
    }

    public void setInMemThreshold(long t2) {
        this.threshold = t2;
    }

    public long getInMemThreshold() {
        return this.threshold;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void writePayload(StringBuilder builder, CachedOutputStream cos, String encoding, String contentType, boolean truncated) throws Exception {
        if (!truncated && this.isPrettyLogging() && contentType != null && contentType.contains("xml") && !contentType.toLowerCase().contains("multipart/related") && cos.size() > 0L) {
            StringWriter swriter = new StringWriter();
            XMLStreamWriter xwriter = StaxUtils.createXMLStreamWriter(swriter);
            xwriter = new PrettyPrintXMLStreamWriter(xwriter, 2);
            try (InputStream in2 = cos.getInputStream();){
                InputStreamReader inputStreamReader = StringUtils.isEmpty(encoding) ? new InputStreamReader(in2) : new InputStreamReader(in2, encoding);
                StaxUtils.copy((Source)new StreamSource(inputStreamReader), xwriter);
            }
            catch (XMLStreamException in2) {
            }
            finally {
                try {
                    xwriter.flush();
                    xwriter.close();
                }
                catch (XMLStreamException in2) {}
            }
            String result = swriter.toString();
            if (result.length() < this.limit || this.limit == -1) {
                builder.append(result);
            } else {
                builder.append(result.substring(0, this.limit));
            }
        } else if (StringUtils.isEmpty(encoding)) {
            cos.writeCacheTo(builder, this.limit);
        } else {
            cos.writeCacheTo(builder, encoding, this.limit);
        }
    }

    protected void writePayload(StringBuilder builder, StringWriter stringWriter, String contentType) throws Exception {
        StringBuffer buffer;
        if (this.isPrettyLogging() && contentType != null && contentType.contains("xml") && stringWriter.getBuffer().length() > 0) {
            try {
                this.writePrettyPayload(builder, stringWriter, contentType);
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((buffer = stringWriter.getBuffer()).length() > this.limit) {
            builder.append(buffer.subSequence(0, this.limit));
        } else {
            builder.append(buffer);
        }
    }

    protected void writePrettyPayload(StringBuilder builder, StringWriter stringWriter, String contentType) throws Exception {
        StringWriter swriter = new StringWriter();
        XMLStreamWriter xwriter = StaxUtils.createXMLStreamWriter(swriter);
        xwriter = new PrettyPrintXMLStreamWriter(xwriter, 2);
        StaxUtils.copy((Source)new StreamSource(new StringReader(stringWriter.getBuffer().toString())), xwriter);
        xwriter.close();
        String result = swriter.toString();
        if (result.length() < this.limit || this.limit == -1) {
            builder.append(swriter.toString());
        } else {
            builder.append(swriter.toString().substring(0, this.limit));
        }
    }

    protected String transform(String originalLogString) {
        return originalLogString;
    }

    protected void log(Logger logger2, String message) {
        message = this.transform(message);
        if (this.writer != null) {
            this.writer.println(message);
            this.writer.flush();
        } else if (logger2.isLoggable(Level.INFO)) {
            LogRecord lr = new LogRecord(Level.INFO, message);
            lr.setSourceClassName(logger2.getName());
            lr.setSourceMethodName(null);
            lr.setLoggerName(logger2.getName());
            logger2.log(lr);
        }
    }

    public void setShowBinaryContent(boolean showBinaryContent) {
        this.showBinaryContent = showBinaryContent;
    }

    public boolean isShowBinaryContent() {
        return this.showBinaryContent;
    }

    protected boolean isBinaryContent(String contentType) {
        return contentType != null && this.binaryContentMediaTypes.contains(contentType);
    }

    public boolean isShowMultipartContent() {
        return this.showMultipartContent;
    }

    public void setShowMultipartContent(boolean showMultipartContent) {
        this.showMultipartContent = showMultipartContent;
    }

    protected boolean isMultipartContent(String contentType) {
        return contentType != null && contentType.startsWith(MULTIPART_CONTENT_MEDIA_TYPE);
    }

    public List<String> getBinaryContentMediaTypes() {
        return this.binaryContentMediaTypes;
    }

    public void setBinaryContentMediaTypes(List<String> binaryContentMediaTypes) {
        this.binaryContentMediaTypes = binaryContentMediaTypes;
    }
}

