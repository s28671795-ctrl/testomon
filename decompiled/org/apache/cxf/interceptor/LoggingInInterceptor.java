/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;

@NoJSR250Annotations
@Deprecated
public class LoggingInInterceptor
extends AbstractLoggingInterceptor {
    private static final Logger LOG = LogUtils.getLogger(LoggingInInterceptor.class);

    public LoggingInInterceptor() {
        super("receive");
    }

    public LoggingInInterceptor(String phase) {
        super(phase);
    }

    public LoggingInInterceptor(String id, String phase) {
        super(id, phase);
    }

    public LoggingInInterceptor(int lim) {
        this();
        this.limit = lim;
    }

    public LoggingInInterceptor(String id, int lim) {
        this(id, "receive");
        this.limit = lim;
    }

    public LoggingInInterceptor(PrintWriter w) {
        this();
        this.writer = w;
    }

    public LoggingInInterceptor(String id, PrintWriter w) {
        this(id, "receive");
        this.writer = w;
    }

    @Override
    public void handleMessage(Message message) {
        Logger logger2 = this.getMessageLogger(message);
        if (logger2 != null && (this.writer != null || logger2.isLoggable(Level.INFO))) {
            this.logging(logger2, message);
        }
    }

    protected void logging(Logger logger2, Message message) {
        String uri;
        Object headers;
        String ct;
        String httpMethod;
        String encoding;
        Integer responseCode;
        if (message.containsKey(LoggingMessage.ID_KEY)) {
            return;
        }
        String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
        if (id == null) {
            id = LoggingMessage.nextId();
            message.getExchange().put(LoggingMessage.ID_KEY, id);
        }
        message.put(LoggingMessage.ID_KEY, id);
        LoggingMessage buffer = new LoggingMessage("Inbound Message\n----------------------------", id);
        if (!Boolean.TRUE.equals(message.get("decoupled.channel.message")) && (responseCode = (Integer)message.get(Message.RESPONSE_CODE)) != null) {
            buffer.getResponseCode().append(responseCode);
        }
        if ((encoding = (String)message.get(Message.ENCODING)) != null) {
            buffer.getEncoding().append(encoding);
        }
        if ((httpMethod = (String)message.get("org.apache.cxf.request.method")) != null) {
            buffer.getHttpMethod().append(httpMethod);
        }
        if ((ct = (String)message.get("Content-Type")) != null) {
            buffer.getContentType().append(ct);
        }
        if ((headers = message.get(Message.PROTOCOL_HEADERS)) != null) {
            buffer.getHeader().append(headers);
        }
        if ((uri = (String)message.get("org.apache.cxf.request.url")) == null) {
            String address = (String)message.get(Message.ENDPOINT_ADDRESS);
            uri = (String)message.get("org.apache.cxf.request.uri");
            if (uri != null && uri.startsWith("/")) {
                if (address != null && !address.startsWith(uri)) {
                    if (address.endsWith("/") && address.length() > 1) {
                        address = address.substring(0, address.length() - 1);
                    }
                    uri = address + uri;
                }
            } else {
                uri = address;
            }
        }
        if (uri != null) {
            buffer.getAddress().append(uri);
            String query = (String)message.get(Message.QUERY_STRING);
            if (query != null) {
                buffer.getAddress().append('?').append(query);
            }
        }
        if (!this.isShowBinaryContent() && this.isBinaryContent(ct)) {
            buffer.getMessage().append("--- Binary Content ---").append('\n');
            this.log(logger2, buffer.toString());
            return;
        }
        if (!this.isShowMultipartContent() && this.isMultipartContent(ct)) {
            buffer.getMessage().append("--- Multipart Content ---").append('\n');
            this.log(logger2, buffer.toString());
            return;
        }
        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            this.logInputStream(message, is, buffer, encoding, ct);
        } else {
            Reader reader = message.getContent(Reader.class);
            if (reader != null) {
                this.logReader(message, reader, buffer);
            }
        }
        this.log(logger2, this.formatLoggingMessage(buffer));
    }

    protected void logReader(Message message, Reader reader, LoggingMessage buffer) {
        try {
            CachedWriter writer = new CachedWriter();
            IOUtils.copyAndCloseInput(reader, writer);
            message.setContent(Reader.class, writer.getReader());
            if (writer.getTempFile() != null) {
                buffer.getMessage().append("\nMessage (saved to tmp file):\n");
                buffer.getMessage().append("Filename: " + writer.getTempFile().getAbsolutePath() + "\n");
            }
            if (writer.size() > (long)this.limit && this.limit != -1) {
                buffer.getMessage().append("(message truncated to " + this.limit + " bytes)\n");
            }
            writer.writeCacheTo(buffer.getPayload(), this.limit);
            writer.close();
        }
        catch (Exception e) {
            throw new Fault(e);
        }
    }

    protected void logInputStream(Message message, InputStream is, LoggingMessage buffer, String encoding, String ct) {
        CachedOutputStream bos = new CachedOutputStream();
        if (this.threshold > 0L) {
            bos.setThreshold(this.threshold);
        }
        try {
            InputStream bis = is instanceof DelegatingInputStream ? ((DelegatingInputStream)is).getInputStream() : is;
            IOUtils.copyAtLeast(bis, bos, this.limit == -1 ? Integer.MAX_VALUE : this.limit);
            bos.flush();
            bis = new SequenceInputStream(bos.getInputStream(), bis);
            if (is instanceof DelegatingInputStream) {
                ((DelegatingInputStream)is).setInputStream(bis);
            } else {
                message.setContent(InputStream.class, bis);
            }
            if (bos.getTempFile() != null) {
                buffer.getMessage().append("\nMessage (saved to tmp file):\n");
                buffer.getMessage().append("Filename: " + bos.getTempFile().getAbsolutePath() + "\n");
            }
            boolean truncated = false;
            if (bos.size() > (long)this.limit && this.limit != -1) {
                buffer.getMessage().append("(message truncated to " + this.limit + " bytes)\n");
                truncated = true;
            }
            this.writePayload(buffer.getPayload(), bos, encoding, ct, truncated);
            bos.close();
        }
        catch (Exception e) {
            throw new Fault(e);
        }
    }

    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        return loggingMessage.toString();
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}

