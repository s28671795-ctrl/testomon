/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;

@NoJSR250Annotations
@Deprecated
public class LoggingOutInterceptor
extends AbstractLoggingInterceptor {
    private static final Logger LOG = LogUtils.getLogger(LoggingOutInterceptor.class);
    private static final String LOG_SETUP = LoggingOutInterceptor.class.getName() + ".log-setup";

    public LoggingOutInterceptor(String phase) {
        super(phase);
        this.addBefore(StaxOutInterceptor.class.getName());
    }

    public LoggingOutInterceptor() {
        this("pre-stream");
    }

    public LoggingOutInterceptor(int lim) {
        this();
        this.limit = lim;
    }

    public LoggingOutInterceptor(PrintWriter w) {
        this();
        this.writer = w;
    }

    @Override
    public void handleMessage(Message message) {
        boolean hasLogged;
        OutputStream os = message.getContent(OutputStream.class);
        Writer iowriter = message.getContent(Writer.class);
        if (os == null && iowriter == null) {
            return;
        }
        Logger logger2 = this.getMessageLogger(message);
        if (logger2 != null && (logger2.isLoggable(Level.INFO) || this.writer != null) && !(hasLogged = message.containsKey(LOG_SETUP))) {
            message.put(LOG_SETUP, Boolean.TRUE);
            if (os != null) {
                CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
                if (this.threshold > 0L) {
                    newOut.setThreshold(this.threshold);
                }
                if (this.limit > 0) {
                    newOut.setCacheLimit(this.limit);
                }
                message.setContent(OutputStream.class, newOut);
                newOut.registerCallback(new LoggingCallback(logger2, message, os));
            } else {
                message.setContent(Writer.class, new LogWriter(logger2, message, iowriter));
            }
        }
    }

    private LoggingMessage setupBuffer(Message message) {
        Object headers;
        String ct;
        String address;
        String httpMethod;
        String encoding;
        String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
        if (id == null) {
            id = LoggingMessage.nextId();
            message.getExchange().put(LoggingMessage.ID_KEY, id);
        }
        LoggingMessage buffer = new LoggingMessage("Outbound Message\n---------------------------", id);
        Integer responseCode = (Integer)message.get(Message.RESPONSE_CODE);
        if (responseCode != null) {
            buffer.getResponseCode().append(responseCode);
        }
        if ((encoding = (String)message.get(Message.ENCODING)) != null) {
            buffer.getEncoding().append(encoding);
        }
        if ((httpMethod = (String)message.get("org.apache.cxf.request.method")) != null) {
            buffer.getHttpMethod().append(httpMethod);
        }
        if ((address = (String)message.get(Message.ENDPOINT_ADDRESS)) != null) {
            buffer.getAddress().append(address);
            String uri = (String)message.get("org.apache.cxf.request.uri");
            if (uri != null && !address.startsWith(uri)) {
                if (!address.endsWith("/") && !uri.startsWith("/")) {
                    buffer.getAddress().append('/');
                }
                buffer.getAddress().append(uri);
            }
        }
        if ((ct = (String)message.get("Content-Type")) != null) {
            buffer.getContentType().append(ct);
        }
        if ((headers = message.get(Message.PROTOCOL_HEADERS)) != null) {
            buffer.getHeader().append(headers);
        }
        return buffer;
    }

    protected String formatLoggingMessage(LoggingMessage buffer) {
        return buffer.toString();
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    class LoggingCallback
    implements CachedOutputStreamCallback {
        private final Message message;
        private final OutputStream origStream;
        private final Logger logger;
        private final int lim;

        LoggingCallback(Logger logger2, Message msg, OutputStream os) {
            this.logger = logger2;
            this.message = msg;
            this.origStream = os;
            this.lim = LoggingOutInterceptor.this.limit == -1 ? Integer.MAX_VALUE : LoggingOutInterceptor.this.limit;
        }

        @Override
        public void onFlush(CachedOutputStream cos) {
        }

        @Override
        public void onClose(CachedOutputStream cos) {
            LoggingMessage buffer = LoggingOutInterceptor.this.setupBuffer(this.message);
            String ct = (String)this.message.get("Content-Type");
            if (!LoggingOutInterceptor.this.isShowBinaryContent() && LoggingOutInterceptor.this.isBinaryContent(ct)) {
                buffer.getMessage().append("--- Binary Content ---").append('\n');
                LoggingOutInterceptor.this.log(this.logger, LoggingOutInterceptor.this.formatLoggingMessage(buffer));
                return;
            }
            if (!LoggingOutInterceptor.this.isShowMultipartContent() && LoggingOutInterceptor.this.isMultipartContent(ct)) {
                buffer.getMessage().append("--- Multipart Content ---").append('\n');
                LoggingOutInterceptor.this.log(this.logger, LoggingOutInterceptor.this.formatLoggingMessage(buffer));
                return;
            }
            boolean truncated = false;
            if (cos.getTempFile() == null) {
                if (cos.size() >= (long)this.lim) {
                    buffer.getMessage().append("(message truncated to " + this.lim + " bytes)\n");
                    truncated = true;
                }
            } else {
                buffer.getMessage().append("Outbound Message (saved to tmp file):\n");
                buffer.getMessage().append("Filename: " + cos.getTempFile().getAbsolutePath() + "\n");
                if (cos.size() >= (long)this.lim) {
                    buffer.getMessage().append("(message truncated to " + this.lim + " bytes)\n");
                    truncated = true;
                }
            }
            try {
                String encoding = (String)this.message.get(Message.ENCODING);
                LoggingOutInterceptor.this.writePayload(buffer.getPayload(), cos, encoding, ct, truncated);
            }
            catch (Exception exception) {
                // empty catch block
            }
            LoggingOutInterceptor.this.log(this.logger, LoggingOutInterceptor.this.formatLoggingMessage(buffer));
            try {
                cos.lockOutputStream();
                cos.resetOut(null, false);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.message.setContent(OutputStream.class, this.origStream);
        }
    }

    private class LogWriter
    extends FilterWriter {
        StringWriter out2;
        int count;
        Logger logger;
        Message message;
        final int lim;

        LogWriter(Logger logger2, Message message, Writer writer) {
            super(writer);
            this.logger = logger2;
            this.message = message;
            if (!(writer instanceof StringWriter)) {
                this.out2 = new StringWriter();
            }
            this.lim = LoggingOutInterceptor.this.limit == -1 ? Integer.MAX_VALUE : LoggingOutInterceptor.this.limit;
        }

        @Override
        public void write(int c) throws IOException {
            super.write(c);
            if (this.out2 != null && this.count < this.lim) {
                this.out2.write(c);
            }
            ++this.count;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            super.write(cbuf, off, len);
            if (this.out2 != null && this.count < this.lim) {
                this.out2.write(cbuf, off, len);
            }
            this.count += len;
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            super.write(str, off, len);
            if (this.out2 != null && this.count < this.lim) {
                this.out2.write(str, off, len);
            }
            this.count += len;
        }

        @Override
        public void close() throws IOException {
            StringWriter w2;
            LoggingMessage buffer = LoggingOutInterceptor.this.setupBuffer(this.message);
            if (this.count >= this.lim) {
                buffer.getMessage().append("(message truncated to " + this.lim + " bytes)\n");
            }
            if ((w2 = this.out2) == null) {
                w2 = (StringWriter)this.out;
            }
            String ct = (String)this.message.get("Content-Type");
            try {
                LoggingOutInterceptor.this.writePayload(buffer.getPayload(), w2, ct);
            }
            catch (Exception exception) {
                // empty catch block
            }
            LoggingOutInterceptor.this.log(this.logger, LoggingOutInterceptor.this.formatLoggingMessage(buffer));
            this.message.setContent(Writer.class, this.out);
            super.close();
        }
    }
}

